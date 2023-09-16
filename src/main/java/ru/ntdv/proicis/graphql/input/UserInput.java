package ru.ntdv.proicis.graphql.input;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import static ru.ntdv.proicis.constant.ValidationStrings.*;

@Data
@AllArgsConstructor
public
class UserInput {
@Size(min = FIRSTNAME_SIZE_MIN, max = FIRSTNAME_SIZE_MAX, message = FIRSTNAME_SIZE_MESSAGE)
@Pattern(regexp = FIRSTNAME_PATTERN_REGEXP, message = FIRSTNAME_PATTERN_MESSAGE)
@NotNull(message = FIRSTNAME_NOTNULL_MESSAGE)
private String firstName;

@Size(min = SECONDNAME_SIZE_MIN, max = SECONDNAME_SIZE_MAX, message = SECONDNAME_SIZE_MESSAGE)
@Pattern(regexp = SECONDNAME_PATTERN_REGEXP, message = SECONDNAME_PATTERN_MESSAGE)
@NotNull(message = SECONDNAME_NOTNULL_MESSAGE)
private String secondName;

@Size(min = THIRDNAME_SIZE_MIN, max = THIRDNAME_SIZE_MAX, message = THIRDNAME_SIZE_MESSAGE)
@Pattern(regexp = THIRDNAME_PATTERN_REGEXP, message = THIRDNAME_PATTERN_MESSAGE)
@NotNull(message = THIRDNAME_NOTNULL_MESSAGE)
private String thirdName;

//@Size(min = URLVKONTAKTE_SIZE_MIN, max = URLVKONTAKTE_SIZE_MAX, message = URLVKONTAKTE_SIZE_MESSAGE)
//@Pattern(regexp = URLVKONTAKTE_PATTERN_REGEXP, message = URLVKONTAKTE_PATTERN_MESSAGE)
//@NotNull(message = URLVKONTAKTE_NOTNULL_MESSAGE)
//private String urlVkontakte;

//@Size(min = URLTELEGRAM_SIZE_MIN, max = URLTELEGRAM_SIZE_MAX, message = URLTELEGRAM_SIZE_MESSAGE)
//@Pattern(regexp = URLTELEGRAM_PATTERN_REGEXP, message = URLTELEGRAM_PATTERN_MESSAGE)
//@NotNull(message = URLTELEGRAM_NOTNULL_MESSAGE)
//private String urlTelegram;

@Size(min = GROUP_SIZE_MIN, max = GROUP_SIZE_MAX, message = GROUP_SIZE_MESSAGE)
@Pattern(regexp = GROUP_PATTERN_REGEXP, message = GROUP_PATTERN_MESSAGE)
@NotNull(message = GROUP_NOTNULL_MESSAGE)
private String group;

@Size(min = ORGANIZATION_SIZE_MIN, max = ORGANIZATION_SIZE_MAX, message = ORGANIZATION_SIZE_MESSAGE)
@NotNull(message = ORGANIZATION_NOTNULL_MESSAGE)
private String organization;

@AssertTrue(message = IS_GROUP_OR_ORGANIZATION_EXISTS_MESSAGE)
private
boolean isGroupOrOrganizationExists() {
    return group.trim().length() != 0 || organization.trim().length() != 0;
}
}
