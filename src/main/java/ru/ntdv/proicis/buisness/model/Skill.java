package ru.ntdv.proicis.buisness.model;

import lombok.Getter;

public enum Skill {
    Programming("Программирование");

    @Getter
    private final String title;

    Skill(final String title) {
        this.title = title;
    }
}
