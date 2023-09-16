package ru.ntdv.proicis.buisness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.ntdv.proicis.buisness.model.StorageProperties;
import ru.ntdv.proicis.crud.contract.FileAccessPolicy;
import ru.ntdv.proicis.crud.model.File;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.repository.FileRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Stream;

@Service
public
class FileSystemStorageService implements StorageService {
private final Path rootLocation;

@Autowired
private FileRepository fileRepository;

@Autowired
public
FileSystemStorageService(final StorageProperties properties) throws IOException {
    this.rootLocation = Paths.get(properties.getLocation());
    if (Files.notExists(this.rootLocation)) {
        Files.createDirectory(this.rootLocation);
    }
}

@Override
public
Path getRootPath() {
    return rootLocation;
}

@Override
public
void init() throws FileNotFoundException {
    try {
        Files.createDirectory(rootLocation);
    } catch (IOException e) {
        throw new FileNotFoundException("Could not initialize storage");
    }
}

@Override
public
File save(final MultipartFile file, final User owner, final FileAccessPolicy... fileAccessPolicy) throws FileSystemException {
    final File fileEntity = fileRepository.save(new File(file.getOriginalFilename(), owner, fileAccessPolicy));
    store(file, fileEntity.getId().toString());
    return fileEntity;
}

@Override
public
void store(final MultipartFile file, final String fileName) throws FileSystemException {
    try {
        if (file.isEmpty()) {
            throw new FileSystemException("Failed to store empty file " + file.getOriginalFilename());
        }
        Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
    } catch (final IOException e) {
        throw new FileSystemException(file.getOriginalFilename(), e.getMessage(), "Failed to store file");
    }
}

@Override
public
void store(final StringBuilder data, final String originalFileName, final String filename) throws FileSystemException {
    try {
        Files.write(this.rootLocation.resolve(filename), Collections.singleton(data));
    } catch (final IOException e) {
        throw new FileSystemException(originalFileName, e.getMessage(), "Failed to store file");
    }
}

@Override
public
Stream<Path> loadAll() throws FileSystemException {
    try {
        try (final Stream<Path> walk = Files.walk(this.rootLocation, 1)) {
            return walk.filter(path -> !path.equals(this.rootLocation))
                       .map(path -> this.rootLocation.relativize(path));
        }
    } catch (IOException e) {
        throw new FileSystemException("Failed to read stored files");
    }

}

@Override
public
Path load(final String filename) {
    return rootLocation.resolve(filename);
}

@Override
public
Resource loadAsResource(final String filename) throws FileNotFoundException {
    try {
        final Path file = load(filename);
        final Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Could not read file: " + filename);

        }
    } catch (final MalformedURLException e) {
        throw new FileNotFoundException("Could not read file: " + filename);
    }
}

@Override
public
void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
}
}