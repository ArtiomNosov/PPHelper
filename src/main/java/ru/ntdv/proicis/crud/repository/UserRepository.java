package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.User;

import java.util.Optional;

@Repository
public
interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findFirstByTelegramChatId(final Long chatId);
}
