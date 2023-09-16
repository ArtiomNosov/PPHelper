package ru.ntdv.proicis.buisness.robot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ntdv.proicis.crud.service.SecretCodeService;

@Service
@Slf4j
public
class SecurityCodeRobot {
private final static RobotStateManager robotStateManager = RobotStateManager.getInstance();

@Autowired
private SecretCodeService secretCodeService;

@Scheduled(cron = "0 * * * * *")
@Async
@Transactional
public
void deleteExpired() {
    if (!robotStateManager.isEnabled(RobotStateManager.Robot.SECRETCODE_DELETE_EXPIRED)) return;

    secretCodeService.deleteAllExpired();

    if (!secretCodeService.hasUnexpired()) {
        robotStateManager.disable(RobotStateManager.Robot.SECRETCODE_DELETE_EXPIRED);
    }
}
}
