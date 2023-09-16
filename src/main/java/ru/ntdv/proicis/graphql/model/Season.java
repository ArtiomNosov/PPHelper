package ru.ntdv.proicis.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public
class Season {
private Long id;
private String title;
private List<StageInfo> stages;
private OffsetDateTime start;
private OffsetDateTime end;

public
Season(final ru.ntdv.proicis.crud.model.Season season) {
    id = season.getId();
    title = season.getTitle();
    stages = season.getStages().stream().map(StageInfo::new).collect(Collectors.toList());
    start = season.getStart();
    end = season.getEnd();
}
}