package ru.ntdv.proicis.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ntdv.proicis.crud.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(final String role);
}
