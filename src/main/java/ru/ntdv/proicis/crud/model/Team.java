package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ntdv.proicis.constant.TeamState;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "team")
public
class Team {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String title;

@OneToOne(fetch = FetchType.EAGER)
private Theme chosenTheme;

@OneToOne(fetch = FetchType.EAGER)
private User chosenMentor;

@ManyToMany(fetch = FetchType.EAGER)
private Set<User> participants;

@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
@OrderColumn(name = "preferthemes_priority")
private List<Theme> preferThemes;

private TeamState state;

@OneToMany(fetch = FetchType.EAGER)
@OrderColumn(name = "activeinseasons_priority")
private List<Season> activeInSeasons;

public
Team(final String title, final Set<User> participants, final List<Theme> preferThemes, final List<Season> activeInSeasons) {
    this.title = title;
    this.preferThemes = preferThemes;
    this.participants = participants;
    this.state = TeamState.InProgress;
    this.activeInSeasons = activeInSeasons;
}

public
boolean isParticipant(final User user) {
    return participants.contains(user);
}

public
boolean isChoosenMentor(final User user) {
    return chosenMentor.equals(user);
}
}
