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
import org.springframework.validation.annotation.Validated;
import ru.ntdv.proicis.constant.TeamState;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.service.TeamService;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.graphql.input.ParticipantAttachmentInput;
import ru.ntdv.proicis.graphql.input.TeamInput;
import ru.ntdv.proicis.graphql.model.Team;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Validated
public
class TeamController {

private static final Log logger = LogFactory.getLog(TeamController.class);
@Autowired
private TeamService teamService;
@Autowired
private UserService userService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
Team getTeam(Authentication authentication, @Argument final Long teamId)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator, UserRole.Role.Mentor) ||
        teamService.getTeam(teamId).isParticipant(credentials.getUser())) {
        return new Team(teamService.getTeam(teamId));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@QueryMapping
public
Set<Team> getAllTeams() {
    return teamService.getAllTeams().stream().map(Team::new).collect(Collectors.toSet());
}

@Secured({ "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
Set<Team> getMyTeams(Authentication authentication) {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Mentor)) {
        return teamService.getMentorTeams(credentials.getUser()).stream().map(Team::new).collect(Collectors.toSet());
    } else if (credentials.hasAnyRole(UserRole.Role.Participant)) {
        return teamService.getParticipantTeams(credentials.getUser()).stream().map(Team::new).collect(Collectors.toSet());
    } else {
        final var e = new AccessDeniedException("Failed to get access to teams. Check roles of the user.");
        logger.error("Access denied", e);
        throw e;
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Participant" })
@MutationMapping
public
Team createTeam(Authentication authentication, @Argument @Valid final TeamInput teamInput)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)) {
        return new Team(teamService.createTeam(teamInput));
    } else if (!teamService.doesParticipantHasTeam(credentials.getUser())) {
        return new Team(teamService.createTeamWithoutParticipants(teamInput));
    } else {
        throw new AccessDeniedException("Can not create team because participant already has one");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Participant" })
@MutationMapping
public
Team updateTeam(Authentication authentication, @Argument final Long teamId, @Argument @Valid final TeamInput teamInput)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)) {
        return new Team(teamService.updateTeam(teamId, teamInput));
    } else if (teamService.getTeam(teamId).isParticipant(credentials.getUser())) {
        return new Team(teamService.updateTeamWithoutParticipants(teamId, teamInput));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Participant" })
@MutationMapping
public
boolean deleteTeam(final Authentication authentication, @Argument final Long teamId)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator) ||
        teamService.getTeam(teamId).isParticipant(credentials.getUser())) {
        teamService.deleteTeam(teamId);
        return true;
    } else {
        // throw new AccessDeniedException("Access denied");
        return false;
    }

}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Participant" })
@MutationMapping
public
Team addParticipantToTeam(final Authentication authentication, @Argument final Long teamId,
                          @Argument @Valid final ParticipantAttachmentInput participantInfo)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if ((credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator)
         || teamService.getTeam(teamId).isParticipant(credentials.getUser())
        ) && userService.findUserById(participantInfo.getCode()).orElseThrow().getSecondName()
                        .equals(participantInfo.getSecondName())) {
        return new Team(teamService.addParticipantToTeam(teamId, participantInfo.getCode()));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Participant" })
@MutationMapping
public
Team removeParticipantFromTeam(final Authentication authentication, @Argument final Long teamId,
                               @Argument final Long userId) throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator) ||
        (teamService.getTeam(teamId).isParticipant(credentials.getUser()) &&
         credentials.getUser().getId().equals(userId))) {
        return new Team(teamService.removeParticipantFromTeam(teamId, userId));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Participant" })
@MutationMapping
public
Team leaveTeam(final Authentication authentication, @Argument final Long teamId) {
    final var credentials = Credentials.from(authentication);
    final var team = teamService.getTeam(teamId);
    if (team == null) {
        throw new IllegalArgumentException("Team with id " + teamId + " not found");
    }
    if (credentials.hasAnyRole(UserRole.Role.Administrator) ||
        team.isParticipant(credentials.getUser())) { // todo Add check for Registering state
        return new Team(teamService.leaveTeam(team, credentials.getUser()));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor" })
@MutationMapping
public
Team setMentor(final Authentication authentication, @Argument final Long teamId, @Argument final Long userId)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator) ||
        teamService.getTeam(teamId).getChosenMentor() == null) {
        return new Team(teamService.setMentor(teamId, userId));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor" })
@MutationMapping
public
Team removeMentor(final Authentication authentication, @Argument final Long teamId)
throws AccessDeniedException, BadCredentialsException {
    final var credentials = Credentials.from(authentication);
    if (credentials.hasAnyRole(UserRole.Role.Administrator, UserRole.Role.Moderator) ||
        teamService.getTeam(teamId).isChoosenMentor(credentials.getUser())) {
        return new Team(teamService.removeMentor(teamId));
    } else {
        throw new AccessDeniedException("Access denied");
    }
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Team setTheme(@Argument final Long teamId, @Argument final Long themeId) {
    return new Team(teamService.setTheme(teamId, themeId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Team removeTheme(@Argument final Long teamId) {
    return new Team(teamService.removeTheme(teamId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Team changeTeamState(@Argument final Long teamId, @Argument @Valid final TeamState state) {
    return new Team(teamService.changeTeamState(teamId, state));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Team addSeasonToTheme(@Argument final Long teamId, @Argument final Long seasonId) {
    return new Team(teamService.addSeasonToTheme(teamId, seasonId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Team removeSeasonFromTheme(@Argument final Long teamId, @Argument final Long seasonId) {
    return new Team(teamService.removeSeasonFromTheme(teamId, seasonId));
}
}
