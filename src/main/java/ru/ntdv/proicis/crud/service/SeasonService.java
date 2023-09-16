package ru.ntdv.proicis.crud.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.crud.model.Season;
import ru.ntdv.proicis.crud.model.StageInfo;
import ru.ntdv.proicis.crud.repository.SeasonRepository;
import ru.ntdv.proicis.graphql.input.SeasonInput;

import java.util.List;

@Service
public
class SeasonService {
@Autowired
private SeasonRepository seasonRepository;

public
Season getSeason(final Long seasonId) throws EntityNotFoundException {
    return seasonRepository.findById(seasonId).orElseThrow(() -> new EntityNotFoundException("No such season found."));
}

public
List<Season> getSeasons() {
    return seasonRepository.findAll();
}

public
Season createSeason(final SeasonInput season) {
    return seasonRepository.save(new Season(season));
}

public
Season updateSeason(final Long seasonId, final SeasonInput season) throws EntityNotFoundException {
    final Season seasonToUpdate = seasonRepository.findById(seasonId)
                                                  .orElseThrow(() -> new EntityNotFoundException("No such season found."));
    seasonToUpdate.setTitle(season.getTitle());
    seasonToUpdate.setStart(season.getStart());
    seasonToUpdate.setEnd(season.getEnd());
    return seasonRepository.save(seasonToUpdate);
}

public
void deleteSeason(final Long seasonId) {
    seasonRepository.deleteById(seasonId);
}

public
Season addStageInfoToSeason(final Long seasonId, final StageInfo stageInfo) throws EntityNotFoundException {
    final Season season = seasonRepository.findById(seasonId)
                                          .orElseThrow(() -> new EntityNotFoundException("No such season found."));
    season.getStages().add(stageInfo);
    return seasonRepository.save(season);
}

public
Season removeStageInfoFromSeason(final Long seasonId, final Long stageInfoId) {
    final Season season = seasonRepository.findById(seasonId)
                                          .orElseThrow(() -> new EntityNotFoundException("No such season found."));
    season.getStages().removeIf(stageInfo -> stageInfo.getId().equals(stageInfoId));
    return seasonRepository.save(season);
}


public
Season getLastByRegisteringSeason() {
    // todo: find last created season with stageinfo with last registering stage
    return seasonRepository.findTopByOrderByIdDesc();
}
}
