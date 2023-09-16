package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.SecretCode;
import ru.ntdv.proicis.crud.model.User;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public
interface SecretCodeRepository extends JpaRepository<SecretCode, Long> {
void deleteAllByOwner(final User user);

boolean existsByExpiresAfter(final OffsetDateTime date);

void deleteAllByExpiresAfter(final OffsetDateTime date);

Optional<SecretCode> findFirstByCodeAndOwnerAndExpiresAfter(final String code, final User owner, final OffsetDateTime date);
}
