package ru.ntdv.proicis.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.repository.CredentialsRepository;

@Service
public
class CredentialsUserDetailsService implements UserDetailsService {
@Autowired
private CredentialsRepository credentialsRepository;


@Override
public
Credentials loadUserByUsername(final String login) throws UsernameNotFoundException {
    final var credentials = credentialsRepository.findByLogin(login);
    if (credentials == null) throw new UsernameNotFoundException("User not found.");
    return credentials;
}
}
