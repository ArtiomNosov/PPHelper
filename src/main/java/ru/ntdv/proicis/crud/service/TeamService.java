package ru.ntdv.proicis.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.constant.TeamState;
import ru.ntdv.proicis.crud.model.Team;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.repository.SeasonRepository;
import ru.ntdv.proicis.crud.repository.TeamRepository;
import ru.ntdv.proicis.crud.repository.ThemeRepository;
import ru.ntdv.proicis.crud.repository.UserRepository;
import ru.ntdv.proicis.graphql.input.TeamInput;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public
class TeamService {
@Autowired
private TeamRepository teamRepository;
@Autowired
private ThemeRepository themeRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private SeasonRepository seasonRepository;

public
Team createTeamWithoutParticipants(final TeamInput teamInput) {
    return createTeam(new TeamInput(teamInput.getTitle(), Collections.emptySet(), teamInput.getPreferThemes(),
                                    teamInput.getSeasons()));
}

public
Team createTeam(final TeamInput teamInput) {
    return teamRepository.saveAndFlush(
            new Team(teamInput.getTitle(),
                     teamInput.getParticipants().stream().map(userId -> userRepository.findById(userId).orElseThrow(
                             () -> new NoSuchElementException("No such user found."))).collect(Collectors.toSet()),
                     teamInput.getPreferThemes().stream().map(themeId -> themeRepository.findById(themeId).orElseThrow(
                             () -> new NoSuchElementException("No such theme found."))).toList(),
                     teamInput.getSeasons().stream().map(seasonId -> seasonRepository.findById(seasonId).orElseThrow(
                             () -> new NoSuchElementException("No such season found."))).toList()));
}

public
boolean doesParticipantHasTeam(final User user) {
    return teamRepository.existsByParticipantsContains(user);
}

public
Set<Team> getParticipantTeams(final User participant) {
    return teamRepository.findAllByParticipantsContains(participant);
}

public
Set<Team> getMentorTeams(final User mentor) {
    return teamRepository.findAllByChosenMentor(mentor);
}

public
Team getTeam(final Long teamId) throws NoSuchElementException {
    return teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
}

public
Team updateTeamWithoutParticipants(final Long teamId, final TeamInput teamInput) throws NoSuchElementException {
    return updateTeam(teamId, new TeamInput(teamInput.getTitle(), Collections.emptySet(), teamInput.getPreferThemes(),
                                            teamInput.getSeasons()));
}

public
Team updateTeam(final Long teamId, final TeamInput teamInput) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    team.setTitle(teamInput.getTitle());
    team.setParticipants(teamInput.getParticipants().stream().map(userId -> userRepository.findById(userId).orElseThrow(
            () -> new NoSuchElementException("No such user found."))).collect(Collectors.toSet()));
    team.setPreferThemes(teamInput.getPreferThemes().stream().map(themeId -> themeRepository.findById(themeId).orElseThrow(
            () -> new NoSuchElementException("No such theme found."))).toList());
    team.setActiveInSeasons(teamInput.getSeasons().stream().map(seasonId -> seasonRepository.findById(seasonId).orElseThrow(
            () -> new NoSuchElementException("No such season found."))).toList());
    return teamRepository.saveAndFlush(team);
}

public
void deleteTeam(final Long teamId) {
    teamRepository.deleteById(teamId);
}

public
Team addParticipantToTeam(final Long teamId, final Long userId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    final var user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("No such user found."));
    team.getParticipants().add(user);
    return teamRepository.saveAndFlush(team);
}

public
Team removeParticipantFromTeam(final Long teamId, final Long userId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    final var user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("No such user found."));
    team.getParticipants().remove(user);
    return teamRepository.saveAndFlush(team);
}

public
Team setMentor(final Long teamId, final Long userId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    final var user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("No such user found."));
    team.setChosenMentor(user);
    return teamRepository.saveAndFlush(team);
}

public
Team removeMentor(final Long teamId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    team.setChosenMentor(null);
    return teamRepository.saveAndFlush(team);
}

public
Team setTheme(final Long teamId, final Long themeId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    final var theme = themeRepository.findById(themeId).orElseThrow(() -> new NoSuchElementException("No such theme found."));
    team.setChosenTheme(theme);
    return teamRepository.saveAndFlush(team);
}

public
Team removeTheme(final Long teamId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    team.setChosenTheme(null);
    return teamRepository.saveAndFlush(team);
}

public
Team changeTeamState(final Long teamId, final TeamState state) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    team.setState(state);
    return teamRepository.saveAndFlush(team);
}

public
List<Team> getAllTeams() {
    return teamRepository.findAll();
}

public
Team addSeasonToTheme(final Long teamId, final Long seasonId) throws NoSuchElementException {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    final var season =
            seasonRepository.findById(seasonId).orElseThrow(() -> new NoSuchElementException("No such season found."));
    team.getActiveInSeasons().add(season);
    return teamRepository.saveAndFlush(team);
}

public
Team removeSeasonFromTheme(final Long teamId, final Long seasonId) {
    final var team = teamRepository.findById(teamId).orElseThrow(() -> new NoSuchElementException("No such team found."));
    final var season =
            seasonRepository.findById(seasonId).orElseThrow(() -> new NoSuchElementException("No such season found."));
    team.getActiveInSeasons().remove(season);
    return teamRepository.saveAndFlush(team);
}

public
Team leaveTeam(final Team team, final User user) {
    try {
        team.getParticipants().remove(user);
    } catch (final UnsupportedOperationException unsupportedOperationException) {
        team.setParticipants(
                team.getParticipants().stream().filter(participant -> !participant.equals(user)).collect(Collectors.toSet()));
    }
    return teamRepository.saveAndFlush(team);
}
}
