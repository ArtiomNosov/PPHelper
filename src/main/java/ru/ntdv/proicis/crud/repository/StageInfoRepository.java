package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.constant.Stage;
import ru.ntdv.proicis.crud.model.StageInfo;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public
interface StageInfoRepository extends JpaRepository<StageInfo, Long> {
List<StageInfo> findAllByHandledIsFalseAndEndBeforeAndStageOrderByEndAsc(final OffsetDateTime timeEndPoint, final Stage stage);

boolean existsByHandledIsFalseAndStageIs(final Stage stage);
}
