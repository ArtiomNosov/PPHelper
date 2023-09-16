package ru.ntdv.proicis.graphql.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import ru.ntdv.proicis.crud.service.SeasonService;
import ru.ntdv.proicis.crud.service.StageInfoService;
import ru.ntdv.proicis.graphql.input.SeasonInput;
import ru.ntdv.proicis.graphql.input.StageInfoInput;
import ru.ntdv.proicis.graphql.model.Season;

import java.util.List;

@Controller
public
class SeasonController {
@Autowired
private SeasonService seasonController;
@Autowired
private StageInfoService stageInfoService;

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Student" })
@QueryMapping
public
Season getSeason(@Argument final Long seasonId) {
    return new Season(seasonController.getSeason(seasonId));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator", "ROLE_Mentor", "ROLE_Student" })
@QueryMapping
public
List<Season> getAllSeasons() {
    return seasonController.getSeasons().stream().map(Season::new).toList();
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Season createSeason(@Argument @Valid final SeasonInput seasonInput) {
    return new Season(seasonController.createSeason(seasonInput));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Season updateSeason(@Argument final Long seasonId, @Argument @Valid final SeasonInput seasonInput) {
    return new Season(seasonController.updateSeason(seasonId, seasonInput));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
boolean deleteSeason(@Argument final Long seasonId) {
    seasonController.deleteSeason(seasonId);
    return true;
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Season addStageInfoToSeason(@Argument final Long seasonId, @Argument @Valid final StageInfoInput stageInfoInput) {
    return new Season(seasonController.addStageInfoToSeason(seasonId, stageInfoService.createStageInfo(stageInfoInput)));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Season addCreatedStageInfoToSeason(@Argument final Long seasonId, @Argument final Long stageInfoId) {
    return new Season(seasonController.addStageInfoToSeason(seasonId, stageInfoService.getStageInfo(stageInfoId)));
}

@Secured({ "ROLE_Administrator", "ROLE_Moderator" })
@MutationMapping
public
Season removeStageInfoFromSeason(@Argument final Long seasonId, @Argument final Long stageInfoId) {
    return new Season(seasonController.removeStageInfoFromSeason(seasonId, stageInfoId));
}
}
