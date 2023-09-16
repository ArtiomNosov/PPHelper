package ru.ntdv.proicis.crud.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.crud.repository.CredentialsRepository;
import ru.ntdv.proicis.crud.repository.UserRoleRepository;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.model.UserRole;

import java.util.NoSuchElementException;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;

    public UserRole.Role getUserRole(final User user) throws NoSuchElementException {
        return UserRole.Role.getFrom(credentialsRepository.findByUser(user).getRoles().stream().findFirst().orElseThrow());
    }

    public UserRole findRoleById(final Long roleId) throws EntityNotFoundException {
        return userRoleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Права не найдены."));
    }

    public void persistRoles() {
        for (final UserRole.Role role : UserRole.Role.values()) {
            if (role.getUserRole() == null) {
                final UserRole userRole;
                if ((userRole = userRoleRepository.findByName(role.getName())) != null) role.setUserRole(userRole);
                else role.setUserRole(userRoleRepository.saveAndFlush(new UserRole(role.getName())));
            }
        }
    }
}
