package ru.ntdv.proicis.graphql.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import static ru.ntdv.proicis.constant.ValidationStrings.*;

@Data
@AllArgsConstructor
public
class CredentialsInput {

@Size(min = LOGIN_SIZE_MIN, max = LOGIN_SIZE_MAX, message = LOGIN_SIZE_MESSAGE)
@Pattern(regexp = LOGIN_PATTERN_REGEXP, message = LOGIN_PATTERN_MESSAGE)
@NotNull(message = LOGIN_NOTNULL_MESSAGE)
private String login;

@Size(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX, message = PASSWORD_SIZE_MESSAGE)
@Pattern(regexp = PASSWORD_PATTERN_REGEXP, message = PASSWORD_PATTERN_MESSAGE)
@NotNull(message = PASSWORD_NOTNULL_MESSAGE)
private String password;
}
