package ru.ntdv.proicis.graphql.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import static ru.ntdv.proicis.constant.ValidationStrings.*;

@Data
@AllArgsConstructor
public
class ParticipantAttachmentInput {

@Min(value = 0, message = CODE_MIN_MESSAGE)
@NotNull(message = CODE_NOTNULL_MESSAGE)
private Long code;

@Size(min = SECONDNAME_SIZE_MIN, max = SECONDNAME_SIZE_MAX, message = SECONDNAME_SIZE_MESSAGE)
@Pattern(regexp = SECONDNAME_PATTERN_REGEXP, message = SECONDNAME_PATTERN_MESSAGE)
@NotNull(message = SECONDNAME_NOTNULL_MESSAGE)
private String secondName;
}
