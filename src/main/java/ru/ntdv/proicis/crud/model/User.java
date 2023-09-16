package ru.ntdv.proicis.crud.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ntdv.proicis.constant.UserState;
import ru.ntdv.proicis.graphql.input.UserInput;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public
class User {
@Id
@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 1111, allocationSize = 1)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
private Long id;
private UserState state;
private String firstName;
private String secondName;
private String thirdName;

@Nullable
private Long telegramChatId;
@Nullable
private String telegramUsername;

@Column(name = "user_group")
private String group;
private String organization;

public
User(final UserInput userInput) {
    //state = UserState.Unconfirmed;
    state = UserState.Confirmed;
    firstName = userInput.getFirstName();
    secondName = userInput.getSecondName();
    thirdName = userInput.getThirdName();
    //urlTelegram = userInput.getUrlTelegram();
    group = userInput.getGroup();
    organization = userInput.getOrganization();
}
}
