package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.Season;

@Repository
public
interface SeasonRepository extends JpaRepository<Season, Long> {
Season findTopByOrderByIdDesc();
}
