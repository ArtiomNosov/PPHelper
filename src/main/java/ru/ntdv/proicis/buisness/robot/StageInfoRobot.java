package ru.ntdv.proicis.buisness.robot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.constant.Stage;
import ru.ntdv.proicis.crud.model.StageInfo;
import ru.ntdv.proicis.crud.service.StageInfoService;

import java.util.stream.Collectors;

@Slf4j
@Service
public
class StageInfoRobot {
private final static RobotStateManager robotStateManager = RobotStateManager.getInstance();
@Autowired
private StageInfoService stageInfoService;

@Scheduled(cron = "0 * * * * *")
@Async
public
void handleRegisteringEnd() {
    if (!robotStateManager.isEnabled(RobotStateManager.Robot.STAGEINFO_REGISTERING_END)) return;

    final var stagesInfos = stageInfoService.getUnhandledAndEndedByStage(Stage.Registering);
    for (final StageInfo stagesInfo : stagesInfos) {
        stageInfoService.handle(stagesInfo);
    }
    log.info(stagesInfos.stream().map(StageInfo::getId).map(Object::toString).collect(Collectors.joining(", ")));
    log.info("Worked!");

    if (!stageInfoService.hasUnhandledByStage(Stage.Registering)) {
        robotStateManager.disable(RobotStateManager.Robot.STAGEINFO_REGISTERING_END);
    }
}
}
