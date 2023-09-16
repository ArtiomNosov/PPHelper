package ru.ntdv.proicis.integrations.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

public
class UnknownCommand implements Command {
public static final String UNKNOWN_MESSAGE = "Неизвестная команда. Напишите /help чтобы узнать существующие  команды.";
private final SendMessageService sendBotMessageService;

public
UnknownCommand(final SendMessageService sendBotMessageService) {
    this.sendBotMessageService = sendBotMessageService;
}

@Override
public
void execute(final Update update) {
    sendBotMessageService.sendMessage(update.getMessage().getChatId(), UNKNOWN_MESSAGE);
}
}
