package ru.ntdv.proicis.integrations.telegram.command;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

@AllArgsConstructor
public
class StartCommand implements Command {
public final static String START_MESSAGE = "Привет! Это бот для уведомлений от сервиса проведения проектной практики ИИКС.\n" +
                                           "Для начала нужно привязать аккаунт: напишите /bind {временный код} {логин}\n" +
                                           "Нужна помощь? Напишите /help.";
private final SendMessageService sendMessageService;

@Override
public
void execute(final Update update) {
    sendMessageService.sendMessage(update.getMessage().getChatId(), START_MESSAGE);
}
}
