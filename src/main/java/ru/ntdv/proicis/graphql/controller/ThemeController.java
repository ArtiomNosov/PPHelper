package ru.ntdv.proicis.graphql.controller;

import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import ru.ntdv.proicis.constant.ThemeState;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.Team;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.service.*;
import ru.ntdv.proicis.graphql.input.ThemeInput;
import ru.ntdv.proicis.graphql.model.Theme;

import java.nio.file.FileSystemException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public
class ThemeController {

private static final Log logger = LogFactory.getLog(ThemeController.class);
@Autowired
CredentialsService credentialsService;
@Autowired
UserService userService;
@Autowired
SeasonService seasonService;
@Autowired
private
ThemeService themeService;
@Autowired
private
TeamService teamService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
Theme getTheme(final Authentication authentication, @Argument final Long themeId)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    final ru.ntdv.proicis.crud.model.Theme theme = themeService.getTheme(themeId);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)
        || theme.getState() == ThemeState.Approved && credentials.hasAnyRole(UserRole.Role.Mentor, UserRole.Role.Participant)
        || theme.getState() != ThemeState.Banned && credentials.hasAnyRole(UserRole.Role.Mentor) &&
           theme.getMentors().contains(credentials.getUser())
        || theme.getState() != ThemeState.Banned && credentials.hasAnyRole(UserRole.Role.Mentor) &&
           theme.getAuthor().equals(credentials.getUser())) {
        return new Theme(theme);
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<Theme> getThemes() {
    return themeService.getAllThemes().stream().map(Theme::new).collect(Collectors.toSet());
}

@Secured({ "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
Set<Theme> getMyThemes(final Authentication authentication) throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    try {
        return getUserThemes(credentials, credentials.getId());
    } catch (final AccessDeniedException e) {
        logger.error(e);
        throw e;
    }
}

private
Set<Theme> getUserThemes(final Credentials authCredentials, final Long userId) throws AccessDeniedException {
    final User user;
    final Credentials credentials;
    if (authCredentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)) {
        user = userService.getUserById(userId);
        credentials = credentialsService.getByUser(user);
    } else {
        user = authCredentials.getUser();
        credentials = authCredentials;
    }

    if (credentials.hasAnyRole(UserRole.Role.Mentor)) {
        return themeService.getMentorThemes(user).stream().map(Theme::new).collect(Collectors.toSet());
    } else if (credentials.hasAnyRole(UserRole.Role.Participant)) {
        return teamService.getParticipantTeams(user).stream().map(Team::getChosenTheme)
                          .filter(Objects::nonNull).map(Theme::new).collect(Collectors.toSet());
    } else {
        throw new AccessDeniedException("Can not get themes for this user. Check user roles.");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<Theme> getUserThemes(@Argument final Long userId) {
    final var credentials = credentialsService.getById(userId);
    return getUserThemes(credentials, userId);
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@MutationMapping
public
Theme createTheme(final Authentication authentication, @Argument @Valid final ThemeInput themeInput)
throws AccessDeniedException, FileSystemException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator, UserRole.Role.Participant)) {
        return new Theme(themeService.createTheme(themeInput, credentials.getUser(), Set.of(),
                                                  themeInput.getSeasons().stream().map(seasonService::getSeason)
                                                            .collect(Collectors.toList())));
    } else if (credentials.hasAnyRole(UserRole.Role.Mentor)) {
        return new Theme(themeService.createTheme(themeInput, credentials.getUser(), Set.of(credentials.getUser()),
                                                  List.of(seasonService.getLastByRegisteringSeason())));
    } else {
        final var e = new AccessDeniedException("Can not create theme by this user. Check user roles.");
        logger.error(e);
        throw e;
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@MutationMapping
public
Theme updateTheme(final Authentication authentication, @Argument final Long themeId,
                  @Argument @Valid final ThemeInput themeInput)
throws AccessDeniedException, FileSystemException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)) {
        return new Theme(themeService.updateTheme(themeId, themeInput, credentials.getUser()));
    } else if (credentials.hasAnyRole(UserRole.Role.Mentor, UserRole.Role.Participant) &&
               themeService.getTheme(themeId).getAuthor().equals(credentials.getUser())) {
        return new Theme(themeService.updateTheme(themeId, themeInput, credentials.getUser()));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
boolean deleteTheme(@Argument final Long themeId) {
    themeService.deleteTheme(themeId);
    return true;
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Theme setAuthorToTheme(@Argument final Long themeId, @Argument final Long userId) {
    return new Theme(themeService.setAuthorToTheme(themeId, userId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor" })
@MutationMapping
public
Theme attachMentorsToTheme(final Authentication authentication, @Argument final Long themeId,
                           @Argument final Set<Long> mentorIds) throws AccessDeniedException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)
        || mentorIds.size() == 1 && mentorIds.contains(credentials.getId())) {
        return new Theme(themeService.attachMentorsToTheme(themeId, mentorIds));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor" })
@MutationMapping
public
Theme detachMentorsFromTheme(final Authentication authentication, @Argument final Long themeId,
                             @Argument final Set<Long> mentorIds) throws AccessDeniedException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)
        || mentorIds.size() == 1 && mentorIds.contains(credentials.getId())) {
        return new Theme(themeService.detachMentorsFromTheme(themeId, mentorIds));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
Theme changeThemeState(@Argument final Long themeId, @Argument @Valid final ThemeState state) {
    return new Theme(themeService.changeThemeState(themeId, state));
}
}
