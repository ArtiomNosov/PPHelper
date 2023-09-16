package ru.ntdv.proicis.crud.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.constant.ThemeState;
import ru.ntdv.proicis.crud.model.Theme;
import ru.ntdv.proicis.crud.model.User;

import java.util.Set;

@Repository
public
interface ThemeRepository extends JpaRepository<Theme, Long> {
Set<Theme> findAllByMentorsContains(User mentor);

void deleteById(@NonNull Long id);

@Modifying
@Query(value = "update Theme theme set author_id = :userId where id = cast(:themeId as bigint) returning * ",
       nativeQuery = true)
Theme setAuthorToTheme(@Param("themeId") final Long themeId, @Param("userId") final Long userId);

@Modifying
@Query(value = "update Theme theme set state = :state where id = cast(:themeId as bigint) returning * ",
       nativeQuery = true)
Theme changeThemeState(@Param("themeId") final Long themeId, @Param("state") final ThemeState state);
}
