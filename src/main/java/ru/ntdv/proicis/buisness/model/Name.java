package ru.ntdv.proicis.buisness.model;

import lombok.Getter;
import ru.ntdv.proicis.crud.model.User;

@Getter
public class Name {
    private final String firstName;
    private final String secondName;
    private final String thirdName;

    public String getFullName() {
        return secondName + " " + firstName + " " + thirdName;
    }

    public String getShortName() {
        return secondName + " " + firstName.charAt(0) + "." + thirdName.charAt(0) + ".";
    }

    public Name(final String firstName, final String secondName, final String thirdName) {
        if (firstName == null || secondName == null || thirdName == null)
            throw new IllegalArgumentException("Имя, фамилия и отчество не должны содержать null.");

        this.firstName = firstName.trim();
        this.secondName = secondName.trim();
        this.thirdName = thirdName.trim();

        if (this.firstName.length() + this.secondName.length() + this.thirdName.length() == 0)
            throw new IllegalArgumentException("Имя, фамилия и отчество не должны быть пустыми.");
    }

    public Name(final User user) {
        this(user.getFirstName(), user.getSecondName(), user.getThirdName());
    }
}
