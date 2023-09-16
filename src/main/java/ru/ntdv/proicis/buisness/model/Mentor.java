package ru.ntdv.proicis.buisness.model;

import lombok.Getter;
import ru.ntdv.proicis.crud.model.User;

@Getter
public
class Mentor {
private final Long id;

private final Name name;
private final Organization organization;

public
Mentor(final User user) throws IllegalArgumentException {
    id = user.getId();
    name = new Name(user);
    organization = new Organization(user);
}
}
