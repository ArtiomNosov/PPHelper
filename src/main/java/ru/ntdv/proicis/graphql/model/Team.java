package ru.ntdv.proicis.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ntdv.proicis.constant.TeamState;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public
class Team {
private Long id;

private String title;
private Theme chosenTheme;
private User chosenMentor;

private Set<User> participants;
private List<Theme> preferThemes;
private TeamState state;
private List<Season> seasons;

public
Team(final ru.ntdv.proicis.crud.model.Team team) {
    id = team.getId();
    title = team.getTitle();
    chosenTheme = team.getChosenTheme() == null ? null : new Theme(team.getChosenTheme());
    chosenMentor = team.getChosenMentor() == null ? null : new User(team.getChosenMentor());
    participants = team.getParticipants().stream().map(User::new).collect(Collectors.toSet());
    preferThemes = team.getPreferThemes().stream().map(Theme::new).collect(Collectors.toList());
    state = team.getState();
    seasons = team.getActiveInSeasons().stream().map(Season::new).collect(Collectors.toList());
}
}
