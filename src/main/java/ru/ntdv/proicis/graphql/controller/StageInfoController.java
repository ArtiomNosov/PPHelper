package ru.ntdv.proicis.graphql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import ru.ntdv.proicis.crud.service.StageInfoService;
import ru.ntdv.proicis.graphql.model.StageInfo;

import java.util.List;

@Controller
public
class StageInfoController {
@Autowired
private StageInfoService stageInfoService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Student" })
@QueryMapping
public
StageInfo getStageInfo(@Argument final Long stageInfoId) {
    return new StageInfo(stageInfoService.getStageInfo(stageInfoId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Student" })
@QueryMapping
public
List<StageInfo> getAllStageInfos() {
    return stageInfoService.getStageInfos().stream().map(StageInfo::new).toList();
}
}
