package ru.ntdv.proicis.graphql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import ru.ntdv.proicis.constant.UserState;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.graphql.model.User;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public
class UserController {
@Autowired
private UserService userService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<User> getAllAdministrators() {
    return userService.getAllAdministrators().stream().map(User::new).collect(Collectors.toSet());
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<User> getAllModerators() {
    return userService.getAllModerators().stream().map(User::new).collect(Collectors.toSet());
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<User> getAllMentors() {
    return userService.getAllMentors().stream().map(User::new).collect(Collectors.toSet());
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<User> getAllParticipants() {
    return userService.getAllParticipants().stream().map(User::new).collect(Collectors.toSet());
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
User getAdministrator(@Argument final Long userId) {
    return new User(userService.getAdministrator(userId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
User getModerator(@Argument final Long userId) {
    return new User(userService.getModerator(userId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
User getMentor(@Argument final Long userId) {
    return new User(userService.getMentor(userId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
User getParticipant(@Argument final Long userId) {
    return new User(userService.getParticipant(userId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
User getMe(final Authentication authentication) {
    return new User(Credentials.from(authentication).getUser());
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
UserRole.Role getMyRole(final Authentication authentication) throws RoleInfoNotFoundException {
    return UserRole.Role.getFrom(Credentials.from(authentication).getRoles().stream().findAny()
                                            .orElseThrow(() -> new RoleInfoNotFoundException("Role not found")));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
User unconfirmUser(@Argument final Long userId) {
    return new User(userService.setState(userId, UserState.Unconfirmed));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
User confirmUser(@Argument final Long userId) {
    return new User(userService.setState(userId, UserState.Confirmed));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
User banUser(@Argument final Long userId) {
    return new User(userService.setState(userId, UserState.Banned));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
boolean deleteUser(@Argument final Long userId) {
    new User(userService.setState(userId, UserState.Deleted));
    return true;
}
}
