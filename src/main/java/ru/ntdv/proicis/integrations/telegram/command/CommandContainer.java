package ru.ntdv.proicis.integrations.telegram.command;

import ru.ntdv.proicis.crud.service.SecretCodeService;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.integrations.telegram.service.SendMessageService;

import java.util.Map;

public
class CommandContainer {
private final Map<String, Command> commandMap;
private final Command unknownCommand;

public
CommandContainer(final SendMessageService sendBotMessageService, final UserService userService,
                 final SecretCodeService secretCodeService) {
    commandMap = Map.of(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService),
                        CommandName.BIND.getCommandName(), new BindCommand(sendBotMessageService, secretCodeService),
                        CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService, userService),
                        CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService),
                        CommandName.NO.getCommandName(), new NoCommand(sendBotMessageService));
    unknownCommand = new UnknownCommand(sendBotMessageService);
}

public
Command retrieveCommand(final String commandIdentifier) {
    return commandMap.getOrDefault(commandIdentifier, unknownCommand);
}
}
