package ru.ntdv.proicis.graphql.controller;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.service.CredentialsService;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.graphql.input.CredentialsInput;
import ru.ntdv.proicis.graphql.input.UserInput;
import ru.ntdv.proicis.graphql.model.User;

import javax.management.InstanceAlreadyExistsException;
import javax.security.auth.login.AccountException;

@Validated
@Controller
public
class CredentialsController {
private static final Log logger = LogFactory.getLog(CredentialsController.class);
@Autowired
private UserService userService;
@Autowired
private CredentialsService credentialsService;

@Secured({ "ROLE_Administrator" })
@MutationMapping
public
User registerAdmin(@Argument @Valid final CredentialsInput credentialsInput, @Argument @Valid final UserInput userInput)
throws AccountException {
    return register(credentialsInput, userInput, UserRole.Role.Administrator);
}

private
User register(final CredentialsInput credentialsInput, final UserInput userInput, final UserRole.Role role)
throws AccountException {
    try {
        return new User(userService.register(credentialsInput, userInput, role).getUser());
    } catch (final InstanceAlreadyExistsException e) {
        logger.info(e.getMessage(), e);
        throw new AccountException("Can not register the user.");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
User registerModerator(@Argument @Valid final CredentialsInput credentialsInput, @Argument @Valid final UserInput userInput)
throws AccountException {
    return register(credentialsInput, userInput, UserRole.Role.Moderator);
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
User registerMentor(@Argument @Valid final CredentialsInput credentialsInput, @Argument @Valid final UserInput userInput)
throws AccountException {
    return register(credentialsInput, userInput, UserRole.Role.Mentor);
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
User registerParticipant(@Argument @Valid final CredentialsInput credentialsInput,
                         @Argument @Valid final UserInput userInput) throws AccountException {
    return register(credentialsInput, userInput, UserRole.Role.Participant);
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@MutationMapping
public
String updateCredentials(@Argument @Valid final CredentialsInput oldCredentials,
                         @Argument @Valid final CredentialsInput newCredentials) throws UsernameNotFoundException {
    if (!oldCredentials.getLogin().equals(((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                                                                              .getPrincipal()).getUsername())) {
        throw new UsernameNotFoundException("You can not change credentials of another user.");
    } else if (oldCredentials.getLogin().equals(newCredentials.getLogin()) &&
               oldCredentials.getPassword().equals(newCredentials.getPassword())) {
        return oldCredentials.getLogin();
    } else {
        return credentialsService.update(checkCredentials(oldCredentials), newCredentials.getLogin(),
                                         newCredentials.getPassword()).getLogin();
    }
}

private
Credentials checkCredentials(final CredentialsInput credentialsInput) throws UsernameNotFoundException {
    return credentialsService.login(credentialsInput.getLogin(), credentialsInput.getPassword());
}
}
