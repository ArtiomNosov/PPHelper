package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.Credentials;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.model.UserRole;

import java.util.Set;

@Repository
public
interface CredentialsRepository extends JpaRepository<Credentials, Long> {
Credentials findFirstByUserIdAndRolesContains(final Long userId, final UserRole role);

Credentials findByLogin(final String login);

Credentials findByUser(final User user);

Set<Credentials> findAllByRolesContains(final UserRole role);

Credentials findFirstByLoginStartsWithOrderByIdDesc(final String login);
}
