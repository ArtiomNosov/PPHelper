package ru.ntdv.proicis.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ntdv.proicis.constant.Stage;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public
class StageInfo {
private Long id;
private String title;
private Stage stage;
private OffsetDateTime start;
private OffsetDateTime end;

public
StageInfo(final ru.ntdv.proicis.crud.model.StageInfo stageInfo) {
    id = stageInfo.getId();
    title = stageInfo.getTitle();
    stage = stageInfo.getStage();
    start = stageInfo.getStart();
    end = stageInfo.getEnd();
}
}
