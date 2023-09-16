package ru.ntdv.proicis.buisness.model;

import lombok.Getter;
import ru.ntdv.proicis.crud.model.User;

@Getter
public
class Moderator {
private final Long id;

private final Name name;

public
Moderator(final User user) throws IllegalArgumentException {
    id = user.getId();
    name = new Name(user);
}
}
