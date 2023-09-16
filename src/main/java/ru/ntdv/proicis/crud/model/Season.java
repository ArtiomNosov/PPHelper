package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "season")
public
class Season {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String title;
@OneToMany(fetch = FetchType.EAGER)
@OrderColumn
private List<StageInfo> stages;

private OffsetDateTime start;
@Column(name = "stage_end")
private OffsetDateTime end;

public
Season(final ru.ntdv.proicis.graphql.input.SeasonInput season) {
    this.title = season.getTitle();
    this.stages = new LinkedList<>();
    this.start = season.getStart();
    this.end = season.getEnd();
}
}
