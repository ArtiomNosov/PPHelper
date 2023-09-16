package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ntdv.proicis.buisness.model.Hardness;
import ru.ntdv.proicis.buisness.model.Skill;
import ru.ntdv.proicis.constant.ThemeState;
import ru.ntdv.proicis.graphql.input.ThemeInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "theme")
public
class Theme {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String title;
private String description;

@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
private File presentationSlide;

private Hardness hardness;

@ElementCollection
private Set<Skill> skills;

@OneToOne
private User author;

@OneToMany(fetch = FetchType.EAGER)
private Set<User> mentors;

private ThemeState state;

@OneToMany(fetch = FetchType.EAGER)
@OrderColumn(name = "activeinseasons_priority")
private List<Season> activeInSeasons;

public
Theme(final ThemeInput themeInput, final File presentationSlide, final User author, final Set<User> mentors,
      final List<Season> activeInSeasons) {
    this.title = themeInput.getTitle();
    this.description = themeInput.getDescription();
    this.presentationSlide = presentationSlide;
    this.hardness = themeInput.getHardness();
    this.skills = themeInput.getSkills();
    this.author = author;
    this.mentors = mentors == null ? new HashSet<>() : mentors;
    this.state = ThemeState.Reviewing;
    this.activeInSeasons = activeInSeasons;
}
}
