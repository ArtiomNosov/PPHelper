package ru.ntdv.proicis.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ntdv.proicis.buisness.model.Hardness;
import ru.ntdv.proicis.buisness.model.Skill;
import ru.ntdv.proicis.constant.ThemeState;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public
class Theme {
private Long id;

private String title;
private String description;

private UUID presentationSlide;
private Hardness hardness;

private Set<Skill> skills;
private User author;

private Set<User> mentors;

private ThemeState state;

private List<Season> seasons;

public
Theme(final ru.ntdv.proicis.crud.model.Theme dbTheme) {
    id = dbTheme.getId();
    title = dbTheme.getTitle();
    description = dbTheme.getDescription();
    presentationSlide = dbTheme.getPresentationSlide().getId();
    hardness = dbTheme.getHardness();
    skills = dbTheme.getSkills();
    author = new User(dbTheme.getAuthor());
    mentors = dbTheme.getMentors().stream().map(User::new).collect(Collectors.toSet());
    state = dbTheme.getState();
    seasons = dbTheme.getActiveInSeasons().stream().map(Season::new).collect(Collectors.toList());
}
}
