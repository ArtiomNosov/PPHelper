package ru.ntdv.proicis.integrations.telegram.command;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

@AllArgsConstructor
public
class StopCommand implements Command {
public static final String STOP_MESSAGE = "Бот остановлен и отвязан от учетной записи сервиса.";
private final SendMessageService sendBotMessageService;
private final UserService userService;

@Override
public
void execute(final Update update) {
    final var chatId = update.getMessage().getChatId();
    userService.unlinkTelegramByChatId(chatId);
    sendBotMessageService.sendMessage(update.getMessage().getChatId(), STOP_MESSAGE);
}
}
