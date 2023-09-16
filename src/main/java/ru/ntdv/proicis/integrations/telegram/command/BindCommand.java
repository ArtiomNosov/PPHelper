package ru.ntdv.proicis.integrations.telegram.command;

import lombok.AllArgsConstructor;
import lombok.val;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ntdv.proicis.crud.service.SecretCodeService;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

@AllArgsConstructor
public
class BindCommand implements Command {
private static final String LINK_ERROR_ARGSCOUNT_MESSAGE =
        "После команды /bind нужно указать код из личного кабинета и свой логин.\n" +
        "Пример: /bind 1234 Login";
private static final String LINK_ERROR_UNCHECKED_MESSAGE = "Неверный код или логин.\n" +
                                                           "Убедитесь, что код действителен или создайте новый и попробуйте " +
                                                           "еще раз.";
private static final String LINK_MESSAGE = "Телеграмм успешно прикреплен к аккаунту.";
private final SendMessageService sendMessageService;
private final SecretCodeService secretCodeService;

@Override
public
void execute(final Update update) {
    val message = update.getMessage();
    val chatId = message.getChatId();
    val text = message.getText().trim();
    val split = text.split(" ");
    if (split.length != 3) {
        sendMessageService.sendMessage(chatId, LINK_ERROR_ARGSCOUNT_MESSAGE);
    } else {
        val code = split[1];
        val login = split[2];
        if (secretCodeService.checkCodeAndLinkTelegram(code, login, chatId, message.getFrom().getUserName())) {
            sendMessageService.sendMessage(chatId, LINK_MESSAGE);
        } else {
            sendMessageService.sendMessage(chatId, LINK_ERROR_UNCHECKED_MESSAGE);
        }
    }
}
}
