package ru.ntdv.proicis.integrations.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

public
class NoCommand implements Command {
public static final String NO_MESSAGE = "Поддерживаются команды, начинающиеся со слеша (/).\n"
                                        + "Чтобы посмотреть список команд введите /help";
private final SendMessageService sendBotMessageService;

public
NoCommand(final SendMessageService sendBotMessageService) {
    this.sendBotMessageService = sendBotMessageService;
}

@Override
public
void execute(final Update update) {
    sendBotMessageService.sendMessage(update.getMessage().getChatId(), NO_MESSAGE);
}
}
