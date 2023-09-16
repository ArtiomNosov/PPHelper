package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
public
class UserRole implements GrantedAuthority {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;

public
UserRole(final String name) {
    this.name = name;
}

@Override
public
String getAuthority() {
    return getName();
}

@Override
public
int hashCode() {
    return this.name.hashCode();
}

@Override
public
boolean equals(Object obj) {
    if (!(obj instanceof UserRole)) {
        return false;
    } else {
        return this.name.equals(((UserRole) obj).getName());
    }
}

@Getter
public
enum Role {
    Administrator("Administrator"),
    Moderator("Moderator"),
    Mentor("Mentor"),
    Participant("Participant");

    private static final String prefix = "ROLE_";

    private final String name;
    @Setter
    private UserRole userRole;

    Role(final String name) {
        this.name = prefix + name;
    }

    public static
    Role getFrom(final UserRole userRole) {
        return Role.valueOf(userRole.getName().substring(prefix.length()));
    }
}
}
