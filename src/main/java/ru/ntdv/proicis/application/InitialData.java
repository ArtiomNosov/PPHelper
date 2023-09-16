package ru.ntdv.proicis.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.service.UserRoleService;
import ru.ntdv.proicis.crud.service.UserService;
import ru.ntdv.proicis.graphql.input.CredentialsInput;
import ru.ntdv.proicis.graphql.input.UserInput;

import javax.management.InstanceAlreadyExistsException;

@Component
@AllArgsConstructor
public
class InitialData implements ApplicationRunner {
@Autowired
private final UserService userService;
@Autowired
private final UserRoleService userRoleService;

public
void run(final ApplicationArguments args) {
    userRoleService.persistRoles();

    if (userService.findUserById((long) 1).isPresent()) return;
    final var adminUser = new UserInput("Имя", "Фамилия", "Отчество", "Б22-123", "МИФИ");
    final var credentials = new CredentialsInput("Admin", "TbJDt27H@3U");
    try {
        userService.register(credentials, adminUser, UserRole.Role.Administrator);
    } catch (final InstanceAlreadyExistsException e) {
        e.printStackTrace(System.err);
    }
}
}
