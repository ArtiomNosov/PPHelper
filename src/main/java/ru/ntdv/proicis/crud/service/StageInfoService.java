package ru.ntdv.proicis.crud.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.buisness.robot.RobotStateManager;
import ru.ntdv.proicis.constant.Stage;
import ru.ntdv.proicis.crud.model.StageInfo;
import ru.ntdv.proicis.crud.repository.StageInfoRepository;
import ru.ntdv.proicis.graphql.input.StageInfoInput;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public
class StageInfoService {
final static RobotStateManager robotStateManager = RobotStateManager.getInstance();
@Autowired
private StageInfoRepository stageInfoRepository;

public
StageInfo getStageInfo(final Long stageInfoId) {
    return stageInfoRepository.findById(stageInfoId)
                              .orElseThrow(() -> new EntityNotFoundException("No such stage info found."));
}

public
List<StageInfo> getStageInfos() {
    return stageInfoRepository.findAll();
}

public
StageInfo createStageInfo(final StageInfoInput stageInfo) {
    robotStateManager.enableFor(stageInfo.getStage());
    return stageInfoRepository.save(new StageInfo(stageInfo));
}

public
StageInfo handle(final StageInfo stageInfo) {
    stageInfo.handle();
    return stageInfoRepository.save(stageInfo);
}

public
List<StageInfo> getUnhandledAndEndedByStage(final Stage stage) {
    return stageInfoRepository.findAllByHandledIsFalseAndEndBeforeAndStageOrderByEndAsc(OffsetDateTime.now(), stage);
}

public
boolean hasUnhandledByStage(final Stage stage) {
    return stageInfoRepository.existsByHandledIsFalseAndStageIs(stage);
}
}
