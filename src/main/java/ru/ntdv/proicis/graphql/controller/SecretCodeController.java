package ru.ntdv.proicis.graphql.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.service.SecretCodeService;

@Validated
@Controller
@Slf4j
public
class SecretCodeController {
@Autowired
private SecretCodeService secretCodeService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Participant" })
@QueryMapping
public
String generateSecretCode(final Authentication authentication) {
    return secretCodeService.generateNewFor15Minutes(Credentials.from(authentication).getUser()).getCode();
}
}
