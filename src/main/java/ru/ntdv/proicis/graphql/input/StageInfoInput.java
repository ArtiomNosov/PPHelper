package ru.ntdv.proicis.graphql.input;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ntdv.proicis.constant.Stage;

import java.time.OffsetDateTime;

import static ru.ntdv.proicis.constant.ValidationStrings.*;

@Data
@AllArgsConstructor
public
class StageInfoInput {

@Size(min = TITLE_SIZE_MIN, max = TITLE_SIZE_MAX, message = TITLE_SIZE_MESSAGE)
@NotNull(message = TITLE_NOTNULL_MESSAGE)
private String title;

@NotNull(message = STAGE_NOTNULL_MESSAGE)
private Stage stage;

@NotNull(message = START_NOTNULL_MESSAGE)
private OffsetDateTime start;

@NotNull(message = END_NOTNULL_MESSAGE)
private OffsetDateTime end;

@AssertFalse(message = IS_TITLE_BLANK_MESSAGE)
private
boolean isTitleBlank() {
    return title.isBlank();
}

@AssertTrue(message = IS_END_AFTER_START_MESSAGE)
private
boolean isEndAfterStart() {
    return end.isAfter(start);
}
}
