package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ntdv.proicis.constant.UserState;
import ru.ntdv.proicis.graphql.input.CredentialsInput;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "credentials")
public
class Credentials implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
private User user;
private String login;
private String secret;
@ManyToMany(fetch = FetchType.EAGER)
private Set<UserRole> roles;

public
Credentials(final CredentialsInput credentialsInput, final User user, final Set<UserRole> roles) {
    this.user = user;
    this.login = credentialsInput.getLogin();
    this.secret = credentialsInput.getPassword();
    this.roles = roles;
}

public static
Credentials from(final Authentication authentication) throws BadCredentialsException {
    final var userDetails = authentication.getPrincipal();
    if (userDetails instanceof Credentials) {
        return (Credentials) userDetails;
    } else {
        throw new BadCredentialsException("Authentication details is not Credentials");
    }
}

@Override
public
Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
}

@Override
public
String getPassword() {
    return secret;
}

@Override
public
String getUsername() {
    return login;
}

@Override
public
boolean isAccountNonExpired() {
    return true;
}

@Override
public
boolean isAccountNonLocked() {
    return true;
}

@Override
public
boolean isCredentialsNonExpired() {
    return true;
}

@Override
public
boolean isEnabled() {
    final var state = user.getState();
    return state != UserState.Banned && state != UserState.Deleted;
}

public
boolean hasAnyRole(final UserRole.Role... roles) {
    return Arrays.stream(roles).anyMatch(role -> this.roles.contains(role.getUserRole()));
}
}
