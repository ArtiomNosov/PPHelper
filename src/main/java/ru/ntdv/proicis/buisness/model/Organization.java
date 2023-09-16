package ru.ntdv.proicis.buisness.model;

import lombok.Getter;
import ru.ntdv.proicis.crud.model.User;

@Getter
public class Organization {
    private final String title;

    public Organization(final String title) throws IllegalArgumentException {
        if (title == null) throw new IllegalArgumentException("Название организации не должно быть пустым."); // todo Валидация организации
        this.title = title.trim();
        if (this.title.length() == 0) throw new IllegalArgumentException("Название организации не должно быть пустым.");
    }

    public Organization(final User user) {
        this(user.getOrganization());
    }
}
