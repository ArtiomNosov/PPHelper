package ru.ntdv.proicis.graphql.input;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

import static ru.ntdv.proicis.constant.ValidationStrings.*;

@Data
@AllArgsConstructor
public
class TeamInput {

@Size(min = TITLE_SIZE_MIN, max = TITLE_SIZE_MAX, message = TITLE_SIZE_MESSAGE)
@NotNull(message = TITLE_NOTNULL_MESSAGE)
private String title;

@NotNull(message = PARTICIPANTS_NOTNULL_MESSAGE)
private Set<Long> participants;

@NotNull(message = PREFERTHEMES_NOTNULL_MESSAGE)
private List<Long> preferThemes;

@Min(value = 0, message = SEASONS_MIN_MESSAGE)
@NotNull(message = SEASONS_NOTNULL_MESSAGE)
private List<Long> seasons;

@AssertFalse(message = PARTICIPANTS_HAS_NULL_ID_MESSAGE)
private
boolean participantsHasNullId() {
    return hasNullValue(participants);
}

@AssertFalse(message = PREFERTHEMES_HAS_NULL_ID_MESSAGE)
private
boolean preferThemesHasNullId() {
    return hasNullValue(preferThemes);
}

@AssertFalse(message = SEASONS_HAS_NULL_ID_MESSAGE)
private
boolean seasonsHasNullId() {
    return hasNullValue(seasons);
}

@AssertFalse(message = PARTICIPANTS_HAS_NEGATIVE_ID_MESSAGE)
private
boolean participantsHasNegativeId() {
    return hasNegativeValue(participants);
}

@AssertFalse(message = PREFERTHEMES_HAS_NEGATIVE_ID_MESSAGE)
private
boolean preferThemesHasNegativeId() {
    return hasNegativeValue(preferThemes);
}

@AssertFalse(message = SEASONS_HAS_NEGATIVE_ID_MESSAGE)
private
boolean seasonsHasNegativeId() {
    return hasNegativeValue(seasons);
}
}
