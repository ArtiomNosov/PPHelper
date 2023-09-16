package ru.ntdv.proicis.graphql.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ntdv.proicis.buisness.model.File;
import ru.ntdv.proicis.buisness.service.StorageService;
import ru.ntdv.proicis.crud.contract.FileAccessPolicy;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.service.CredentialsService;
import ru.ntdv.proicis.crud.service.FileService;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.graphql.input.CredentialsInput;
import ru.ntdv.proicis.graphql.input.UserInput;
import ru.ntdv.proicis.security.manager.LoginManager;
import ru.ntdv.proicis.security.manager.PasswordManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
public
class FileController {
private static final Logger logger = LoggerFactory.getLogger(FileController.class);
private final StorageService storageService;
private final UserService userService;
private final CredentialsService credentialsService;
private final FileService fileService;

@Autowired
public
FileController(final FileService fileService, final CredentialsService credentialsService, final UserService userService,
               final StorageService storageService) {
    this.fileService = fileService;
    this.credentialsService = credentialsService;
    this.userService = userService;
    this.storageService = storageService;
}

@MutationMapping
@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
public
UUID importParticipants(final Authentication authentication, @Argument final MultipartFile file)
throws CsvValidationException {
    final var owner = Credentials.from(authentication).getUser();
    final var data = new LinkedList<Triple<CredentialsInput, UserInput, UserRole.Role>>();
    final var out = new StringBuilder();
    final var validator = Validation.buildDefaultValidatorFactory().getValidator();

    try (final CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
        String[] lineInArray;
        while ((lineInArray = reader.readNext()) != null) {
            final String firstName = lineInArray[0];
            final String secondName = lineInArray[1];
            final String thirdName = lineInArray[2];
            final String group = lineInArray[3];

            final String rawLogin = LoginManager.generateLogin(firstName, secondName, thirdName);
            final String login = rawLogin + credentialsService.getLatestPostfix(rawLogin);
            final String password = PasswordManager.generateRandomPassword();

            final var credentials = new CredentialsInput(login, password);
            final var user = new UserInput(firstName, secondName, thirdName, group, "");

            final var errors = validator.validate(user);
            if (!errors.isEmpty()) {
                final var exception = new ValidationException("Can not validate user input: " + errors);
                logger.error("User input validation error", exception);
                throw exception;
            }

            out.append(login).append(",").append(password).append("\n");
            data.add(new ImmutableTriple<>(credentials, user, UserRole.Role.Participant));
        }
    } catch (final IndexOutOfBoundsException | CsvValidationException | IOException e) {
        logger.error("CSV parsing error", e);
        throw new CsvValidationException("CSV parsing error");
    }

    try {
        userService.registerAll(data);
        final var name =
                "Register_participants_" + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) +
                ".csv";
        final var fileObject = fileService.save(new ru.ntdv.proicis.crud.model.File(name, owner,
                                                                                    Set.of(FileAccessPolicy.Administrators,
                                                                                           FileAccessPolicy.Moderators)));
        storageService.store(out, name, fileObject.getId().toString());
        return fileObject.getId();
    } catch (final FileSystemException e) {
        logger.error("Result saving error", e);
        throw new CsvValidationException("Can not save results of operation");
    }
}

@PostMapping("/files/upload")
public
UUID handleFileUpload(final Authentication authentication, @RequestParam MultipartFile file,
                      @RequestParam final List<FileAccessPolicy> policy) throws FileUploadException {
    try {
        return storageService.save(file, Credentials.from(authentication).getUser(), policy.toArray(new FileAccessPolicy[0]))
                             .getId();
    } catch (final FileSystemException e) {
        throw new FileUploadException("Can not upload file", e);
    }
}

@GetMapping(value = "/files/{file_name:.+}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
@ResponseBody
public
FileSystemResource getFile(final Authentication authentication, @PathVariable("file_name") String fileName,
                           final HttpServletResponse response) {
    final var file = new File(storageService.getRootPath(), fileService.getById(UUID.fromString(fileName)));
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getOriginalName());
    final var user = Credentials.from(authentication).getUser();
    return file.getBy(user);
}
}
