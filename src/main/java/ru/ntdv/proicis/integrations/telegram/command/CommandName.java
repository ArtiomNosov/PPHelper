package ru.ntdv.proicis.integrations.telegram.command;

public
enum CommandName {
    START("/start"),
    BIND("/bind"),
    HELP("/help"),
    STOP("/stop"),
    NO("nocommand");

private final String commandName;

CommandName(String commandName) {
    this.commandName = commandName;
}

public
String getCommandName() {
    return commandName;
}
}
