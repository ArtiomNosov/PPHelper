package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.File;
import ru.ntdv.proicis.crud.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findAllByOriginalName(final String originalName);
    List<File> findAllByOwner(final User owner);
}
