package ru.ntdv.proicis.integrations.mephi;

import lombok.Getter;
import ru.ntdv.proicis.crud.model.User;

@Getter
public class StudentGroup {
    private final String group;

    public StudentGroup(final String group) throws IllegalArgumentException {
        if (group == null) throw new IllegalArgumentException("Номер группы не должен быть пустым."); // todo Валидация группы

        this.group = group.trim();

        if (this.group.length() == 0) throw new IllegalArgumentException("Номер группы не должен быть пустым.");
    }

    public StudentGroup(final User user) {
        this(user.getGroup());
    }
}
