package ru.ntdv.proicis.buisness.model;

import lombok.Getter;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.integrations.mephi.StudentGroup;

@Getter
public
class Participant {
private final Long id;

private final Name name;
private final StudentGroup group;

public
Participant(final User user) throws IllegalArgumentException {
    id = user.getId();
    name = new Name(user);
    group = new StudentGroup(user);
}
}
