package ru.ntdv.proicis.crud.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.constant.UserState;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.model.UserRole;
import ru.ntdv.proicis.crud.repository.CredentialsRepository;
import ru.ntdv.proicis.crud.repository.UserRepository;
import ru.ntdv.proicis.graphql.input.CredentialsInput;
import ru.ntdv.proicis.graphql.input.UserInput;

import javax.management.InstanceAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public
class UserService {
@Autowired
private CredentialsRepository credentialsRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private PasswordEncoder passwordEncoder;

public
User getUserById(Long userId) throws EntityNotFoundException {
    return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
}

public
Credentials register(final CredentialsInput credentialsInput, final UserInput userInput, final UserRole.Role role)
throws InstanceAlreadyExistsException {
    if (credentialsRepository.findByLogin(credentialsInput.getLogin()) != null) {
        throw new InstanceAlreadyExistsException("User with this login already exists.");
    }

    credentialsInput.setPassword(passwordEncoder.encode(credentialsInput.getPassword()));

    final var user = userRepository.saveAndFlush(new User(userInput));
    return credentialsRepository.saveAndFlush(
            new Credentials(credentialsInput, user, Collections.singleton(role.getUserRole())));
}

public
Set<Credentials> registerAll(final Collection<Triple<CredentialsInput, UserInput, UserRole.Role>> data) {
    final var users = new LinkedList<User>();
    data.forEach(tuple -> users.add(new User(tuple.getMiddle())));
    final var usersIterator = userRepository.saveAllAndFlush(users).iterator();

    final var credentials = new LinkedList<Credentials>();
    data.forEach(tuple -> {
        tuple.getLeft().setPassword(passwordEncoder.encode(tuple.getLeft().getPassword()));
        credentials.add(
                new Credentials(tuple.getLeft(), usersIterator.next(), Collections.singleton(tuple.getRight().getUserRole())));
    });
    return new HashSet<>(credentialsRepository.saveAllAndFlush(credentials));
}

public
Set<User> getAllAdministrators() {
    return credentialsRepository
            .findAllByRolesContains(UserRole.Role.Administrator.getUserRole()).stream()
            .map(Credentials::getUser).collect(Collectors.toSet());
}

public
Set<User> getAllModerators() {
    return credentialsRepository.findAllByRolesContains(UserRole.Role.Moderator.getUserRole()).stream()
                                .map(Credentials::getUser).collect(Collectors.toSet());
}

public
Set<User> getAllMentors() {
    return credentialsRepository.findAllByRolesContains(UserRole.Role.Mentor.getUserRole()).stream()
                                .map(Credentials::getUser).collect(Collectors.toSet());
}

public
Set<User> getAllParticipants() {
    return credentialsRepository
            .findAllByRolesContains(UserRole.Role.Participant.getUserRole()).stream()
            .map(Credentials::getUser).collect(Collectors.toSet());
}

public
User getAdministrator(final Long userId) {
    return credentialsRepository.findFirstByUserIdAndRolesContains(userId, UserRole.Role.Administrator.getUserRole())
                                .getUser();
}

public
User getModerator(final Long userId) {
    return credentialsRepository.findFirstByUserIdAndRolesContains(userId, UserRole.Role.Moderator.getUserRole()).getUser();
}

public
User getMentor(final Long userId) {
    return credentialsRepository.findFirstByUserIdAndRolesContains(userId, UserRole.Role.Mentor.getUserRole()).getUser();
}

public
User getParticipant(final Long userId) {
    return credentialsRepository.findFirstByUserIdAndRolesContains(userId, UserRole.Role.Participant.getUserRole())
                                .getUser();
}

public
User setState(final Long userId, final UserState state) {
    final var user = findUserById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
    user.setState(state);
    return userRepository.saveAndFlush(user);
}

public
Optional<User> findUserById(final Long userId) {
    return userRepository.findById(userId);
}

public
void unlinkTelegramByChatId(final Long chatId) {
    userRepository.findFirstByTelegramChatId(chatId).ifPresent(user -> {
        user.setTelegramChatId(null);
        user.setTelegramUsername(null);
        userRepository.save(user);
    });
}

public
User getUserByLogin(final String login) {
    return credentialsRepository.findByLogin(login).getUser();
}
}
