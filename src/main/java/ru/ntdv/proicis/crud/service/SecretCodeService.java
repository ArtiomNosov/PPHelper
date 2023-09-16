package ru.ntdv.proicis.crud.service;

import jakarta.annotation.Nullable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.buisness.robot.RobotStateManager;
import ru.ntdv.proicis.crud.model.SecretCode;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.repository.CredentialsRepository;
import ru.ntdv.proicis.crud.repository.SecretCodeRepository;
import ru.ntdv.proicis.crud.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.Random;

@Service
public
class SecretCodeService {
final static RobotStateManager robotStateManager = RobotStateManager.getInstance();
private static final Random random = new Random();
@Autowired
private SecretCodeRepository secretCodeRepository;
@Autowired
private CredentialsRepository credentialsRepository;
@Autowired
private UserRepository userRepository;

public
boolean hasUnexpired() {
    return secretCodeRepository.existsByExpiresAfter(OffsetDateTime.now());
}

public
void deleteAllExpired() {
    secretCodeRepository.deleteAllByExpiresAfter(OffsetDateTime.now());
}

public
SecretCode generateNewFor15Minutes(final User user) {
    secretCodeRepository.deleteAllByOwner(user);
    final var code = secretCodeRepository.save(new SecretCode(String.valueOf(random.nextInt(1000, 10000)),
                                                              user, OffsetDateTime.now().plusMinutes(15)));
    robotStateManager.enableFor(code);
    return code;
}

public
boolean checkCodeAndLinkTelegram(final String code, final String login, final Long chatId, @Nullable final String username) {
    val credentials = credentialsRepository.findByLogin(login);
    if (credentials == null) return false;
    val user = credentials.getUser();
    val secretCode = secretCodeRepository.findFirstByCodeAndOwnerAndExpiresAfter(code, user, OffsetDateTime.now());
    if (secretCode.isPresent()) {
        secretCodeRepository.delete(secretCode.get());
        user.setTelegramChatId(chatId);
        user.setTelegramUsername(username);
        userRepository.save(user);
        return true;
    } else {
        return false;
    }
}
}
