package ru.ntdv.proicis.graphql.input;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ntdv.proicis.buisness.model.Hardness;
import ru.ntdv.proicis.buisness.model.Skill;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static ru.ntdv.proicis.constant.ValidationStrings.*;

@Data
@AllArgsConstructor
public
class ThemeInput {

@Size(min = TITLE_SIZE_MIN, max = TITLE_SIZE_MAX, message = TITLE_SIZE_MESSAGE)
@NotNull(message = TITLE_NOTNULL_MESSAGE)
private String title;

@Size(min = DESCRIPTION_SIZE_MIN, max = DESCRIPTION_SIZE_MAX, message = DESCRIPTION_SIZE_MESSAGE)
@NotNull(message = DESCRIPTION_NOTNULL_MESSAGE)
private String description;

//private MultipartFile presentationSlide;

@NotNull(message = PRESENTATIONSLIDE_NOTNULL_MESSAGE)
private UUID presentationSlide;

@NotNull(message = HARDNESS_NOTNULL_MESSAGE)
private Hardness hardness;

@Min(value = 0, message = SKILLS_MIN_MESSAGE)
@NotNull(message = SKILLS_NOTNULL_MESSAGE)
private Set<Skill> skills;

@Min(value = 0, message = SEASONS_MIN_MESSAGE)
@NotNull(message = SEASONS_NOTNULL_MESSAGE)
private List<Long> seasons;

@AssertFalse(message = SKILLS_HAS_NULL_ID_MESSAGE)
private
boolean skillsHasNullId() {
    return hasNullValue(skills);
}

@AssertFalse(message = SEASONS_HAS_NULL_ID_MESSAGE)
private
boolean seasonsHasNullId() {
    return hasNullValue(seasons);
}

@AssertFalse(message = SEASONS_HAS_NEGATIVE_ID_MESSAGE)
private
boolean seasonsHasNegativeId() {
    return hasNegativeValue(seasons);
}
}
