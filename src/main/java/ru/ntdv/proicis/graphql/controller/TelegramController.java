package ru.ntdv.proicis.graphql.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.integrations.telegram.ProicisTelegramBot;

@Validated
@Controller
@Slf4j
public
class TelegramController {
@Autowired
private ProicisTelegramBot proicisTelegramBot;
@Autowired
private UserService userService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Boolean sendTelegramMessage(@Argument @Valid final Long userId, @Argument @Valid final String message) {
    return userService.findUserById(userId)
                      .filter(user -> proicisTelegramBot.sendMessage(user.getTelegramChatId(), message))
                      .isPresent();
}
}
