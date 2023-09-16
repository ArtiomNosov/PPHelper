package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.Team;
import ru.ntdv.proicis.crud.model.User;

import java.util.Set;

@Repository
public
interface TeamRepository extends JpaRepository<Team, Long> {
Set<Team> findAllByParticipantsContains(final User participant);

Set<Team> findAllByChosenMentor(final User mentor);

boolean existsByParticipantsContains(final User participant);

}
