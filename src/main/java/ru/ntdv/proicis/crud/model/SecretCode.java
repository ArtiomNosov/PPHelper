package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "secret_code")
public
class SecretCode {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@NotNull
@NotBlank
private String code;

@OneToOne(fetch = FetchType.EAGER)
@NotNull
private User owner;

@NotNull
private OffsetDateTime expires;

public
SecretCode(final @NotNull String code, final @NotNull User owner, final @NotNull OffsetDateTime expires) {
    this.code = code;
    this.owner = owner;
    this.expires = expires;
}

public
boolean equals(final String secretCode, final User user) {
    return this.code.equals(secretCode) && OffsetDateTime.now().isBefore(expires) && this.owner.equals(user);
}
}
