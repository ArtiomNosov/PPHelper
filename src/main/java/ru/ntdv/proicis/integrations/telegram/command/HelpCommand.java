package ru.ntdv.proicis.integrations.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

public
class HelpCommand implements Command {
public static final String HELP_MESSAGE = "/start - Показать вступительное сообщение\n" +
                                          "/bind - Прикрепить телеграмм к учётной записи\n" +
                                          "/help - Получить информацию о доступных командах\n" +
                                          "/stop - Остановить бота и отвязать от учётной записи";
private final SendMessageService sendMessageService;

public
HelpCommand(final SendMessageService sendMessageService) {
    this.sendMessageService = sendMessageService;
}

@Override
public
void execute(final Update update) {
    sendMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
}
}
