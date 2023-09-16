package ru.ntdv.proicis.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.repository.CredentialsRepository;

import java.util.NoSuchElementException;

@Service
public
class CredentialsService {
@Autowired
private CredentialsRepository credentialsRepository;
@Autowired
private PasswordEncoder passwordEncoder;

public
Credentials getById(final Long id) throws NoSuchElementException {
    return credentialsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No such credentials found."));
}

public
String getLatestPostfix(final String login) {
    final var latestCredentials = credentialsRepository.findFirstByLoginStartsWithOrderByIdDesc(login);
    if (latestCredentials == null) {
        return "";
    } else if (latestCredentials.getLogin().length() == login.length()) {
        return "1";
    } else {
        return String.valueOf(Long.parseLong(latestCredentials.getLogin().substring(login.length())) + 1);
    }
}

public
Credentials update(final Credentials credentials, final String login, final String password) {
    credentials.setLogin(login);
    credentials.setSecret(passwordEncoder.encode(password));
    return credentialsRepository.saveAndFlush(credentials);
}

public
Credentials login(final String login, final String password) throws UsernameNotFoundException {
    final var credentials = credentialsRepository.findByLogin(login);
    if (!passwordEncoder.matches(credentials.getPassword(), password)) {
        throw new UsernameNotFoundException("User not found.");
    }
    return credentials;
}

public
Credentials getByUser(final User user) {
    return credentialsRepository.findByUser(user);
}
}
