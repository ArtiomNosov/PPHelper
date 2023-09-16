package ru.ntdv.proicis.constant;

import java.util.Collection;
import java.util.Objects;

public
class ValidationStrings {
public static final int LOGIN_SIZE_MIN = 5;
public static final int LOGIN_SIZE_MAX = 32;
public static final String LOGIN_SIZE_MESSAGE =
        "Логин должен содержать не менее " + LOGIN_SIZE_MIN + " и не более " + LOGIN_SIZE_MAX + " символов.";
public static final String LOGIN_PATTERN_REGEXP = "^[\\S]{" + LOGIN_SIZE_MIN + "," + LOGIN_SIZE_MAX + "}$";
public static final String LOGIN_PATTERN_MESSAGE = "Логин не должен содержать пробельных символов.";
public static final String LOGIN_NOTNULL_MESSAGE = "Поле логин должно содержать значение.";

public static final int PASSWORD_SIZE_MIN = 6;
public static final int PASSWORD_SIZE_MAX = 32;
public static final String PASSWORD_SIZE_MESSAGE =
        "Пароль должен содержать не менее " + PASSWORD_SIZE_MIN + " и не более " + PASSWORD_SIZE_MAX + " символов.";
public static final String PASSWORD_PATTERN_REGEXP = "^[\\S]{" + PASSWORD_SIZE_MIN + "," + PASSWORD_SIZE_MAX + "}$";
public static final String PASSWORD_PATTERN_MESSAGE = "Пароль не должен содержать пробельных символов.";
public static final String PASSWORD_NOTNULL_MESSAGE = "Поле пароль должно содержать значение.";


public static final String CODE_MIN_MESSAGE = "Код не может быть отрицательным.";
public static final String CODE_NOTNULL_MESSAGE = "Поле код должно содержать значение.";


public static final int TITLE_SIZE_MIN = 0;
public static final int TITLE_SIZE_MAX = 120;
public static final String TITLE_SIZE_MESSAGE =
        TITLE_SIZE_MIN == 0 ?
        "Название должно содержать не более " + TITLE_SIZE_MAX + " символов." :
        "Название должно содержать не менее " + TITLE_SIZE_MIN + " и не более " + TITLE_SIZE_MAX + " символов.";

public static final String TITLE_NOTNULL_MESSAGE = "Поле название должно содержать значение.";
public static final String IS_TITLE_BLANK_MESSAGE = "Название не должно быть пустым.";
public static final String IS_END_AFTER_START_MESSAGE = "Дата окончания должна быть позже даты начала.";
public static final String START_NOTNULL_MESSAGE = "Поле дата начала должно содержать значение.";
public static final String END_NOTNULL_MESSAGE = "Поле дата окончания должно содержать значение.";


public static final String STAGE_NOTNULL_MESSAGE = "Поле этап должно содержать значение.";


public static final int FIRSTNAME_SIZE_MIN = 1;
public static final int FIRSTNAME_SIZE_MAX = 32;
public static final String FIRSTNAME_SIZE_MESSAGE =
        "Имя должно содержать не менее " + FIRSTNAME_SIZE_MIN + " и не более " + FIRSTNAME_SIZE_MAX + " символов.";
public static final String FIRSTNAME_PATTERN_REGEXP = "^[\\S]{" + FIRSTNAME_SIZE_MIN + "," + FIRSTNAME_SIZE_MAX + "}$";
public static final String FIRSTNAME_PATTERN_MESSAGE = "Имя не должно содержать пробельных символов.";
public static final String FIRSTNAME_NOTNULL_MESSAGE = "Поле имя должно содержать значение.";

public static final int SECONDNAME_SIZE_MIN = 1;
public static final int SECONDNAME_SIZE_MAX = 32;
public static final String SECONDNAME_SIZE_MESSAGE =
        "Фамилия должна содержать не менее " + SECONDNAME_SIZE_MIN + " и не более " + SECONDNAME_SIZE_MAX + " символов.";
public static final String SECONDNAME_PATTERN_REGEXP = "^[\\S]{" + SECONDNAME_SIZE_MIN + "," + SECONDNAME_SIZE_MAX + "}$";
public static final String SECONDNAME_PATTERN_MESSAGE = "Фамилия не должна содержать пробельных символов.";
public static final String SECONDNAME_NOTNULL_MESSAGE = "Поле фамилия должно содержать значение.";

public static final int THIRDNAME_SIZE_MIN = 0;
public static final int THIRDNAME_SIZE_MAX = 32;
public static final String THIRDNAME_SIZE_MESSAGE =
        THIRDNAME_SIZE_MIN == 0 ?
        "Отчество должно содержать не более " + THIRDNAME_SIZE_MAX + " символов." :
        "Отчество должно содержать не менее " + THIRDNAME_SIZE_MIN + " и не более " + THIRDNAME_SIZE_MAX + " символов.";
public static final String THIRDNAME_PATTERN_REGEXP = "^[\\S]{" + THIRDNAME_SIZE_MIN + "," + THIRDNAME_SIZE_MAX + "}$";
public static final String THIRDNAME_PATTERN_MESSAGE = "Отчество не должно содержать пробельных символов.";
public static final String THIRDNAME_NOTNULL_MESSAGE = "Поле отчество должно содержать значение.";

public static final int URLVKONTAKTE_SIZE_MIN = 0;
public static final int URLVKONTAKTE_SIZE_MAX = 120;
public static final String URLVKONTAKTE_SIZE_MESSAGE =
        URLVKONTAKTE_SIZE_MIN == 0 ?
        "Ссылка на страницу ВКонтакте должна содержать не более " + URLVKONTAKTE_SIZE_MAX + " символов." :
        "Ссылка на страницу ВКонтакте должна содержать не менее " + URLVKONTAKTE_SIZE_MIN + " и не более " +
        URLVKONTAKTE_SIZE_MAX + " символов.";
public static final String URLVKONTAKTE_PATTERN_REGEXP = "^$|((http://|https://)?vk\\.com/|@|(id)).+";
public static final String URLVKONTAKTE_PATTERN_MESSAGE =
        "Ссылка на страницу ВКонтакте должна быть в формате: https://vk.com/username или https://vk.com/id123456789 или @username или id123456789.";
public static final String URLVKONTAKTE_NOTNULL_MESSAGE = "Поле ссылка на страницу ВКонтакте должно содержать значение.";

public static final int URLTELEGRAM_SIZE_MIN = 0;
public static final int URLTELEGRAM_SIZE_MAX = 120;
public static final String URLTELEGRAM_SIZE_MESSAGE =
        URLTELEGRAM_SIZE_MIN == 0 ?
        "Ссылка на аккаунт Telegram должна содержать не более " + URLTELEGRAM_SIZE_MAX + " символов." :
        "Ссылка на аккаунт Telegram должна содержать не менее " + URLTELEGRAM_SIZE_MIN + " и не более " +
        URLTELEGRAM_SIZE_MAX + " символов.";
public static final String URLTELEGRAM_PATTERN_REGEXP = "((?<id>.+)\\.t.me)|((http://|https://)?t\\.me/|@).+";
public static final String URLTELEGRAM_PATTERN_MESSAGE =
        "Ссылка на аккаунт Telegram должна быть в формате: https://t.me/username или @username или username.t.me";
public static final String URLTELEGRAM_NOTNULL_MESSAGE = "Поле ссылка на аккаунт Telegram должно содержать значение.";

public static final int GROUP_SIZE_MIN = 0;
public static final int GROUP_SIZE_MAX = 9;
public static final String GROUP_SIZE_MESSAGE =
        GROUP_SIZE_MIN == 0 ?
        "Группа должна содержать не более " + GROUP_SIZE_MAX + " символов." :
        "Группа должна содержать не менее " + GROUP_SIZE_MIN + " и не более " + GROUP_SIZE_MAX + " символов.";
public static final String GROUP_PATTERN_REGEXP = "^[\\S]{" + GROUP_SIZE_MIN + "," + GROUP_SIZE_MAX + "}$";
public static final String GROUP_PATTERN_MESSAGE = "Группа не должна содержать пробельных символов.";
public static final String GROUP_NOTNULL_MESSAGE = "Поле группа должно содержать значение.";

public static final int ORGANIZATION_SIZE_MIN = 0;
public static final int ORGANIZATION_SIZE_MAX = 120;
public static final String ORGANIZATION_SIZE_MESSAGE =
        ORGANIZATION_SIZE_MIN == 0 ?
        "Организация должна содержать не более " + ORGANIZATION_SIZE_MAX + " символов." :
        "Организация должна содержать не менее " + ORGANIZATION_SIZE_MIN + " и не более " + ORGANIZATION_SIZE_MAX +
        " символов.";
public static final String ORGANIZATION_NOTNULL_MESSAGE = "Поле организация должно содержать значение.";

public static final String IS_GROUP_OR_ORGANIZATION_EXISTS_MESSAGE = "Должны быть заполнены или группа или организация.";

public static final String PARTICIPANTS_NOTNULL_MESSAGE = "Поле участники должно содержать значение.";
public static final String PREFERTHEMES_NOTNULL_MESSAGE = "Поле предпочитаемые темы должно содержать значение.";
public static final String SEASONS_MIN_MESSAGE = "Должен быть указан хотя бы один сезон.";
public static final String SEASONS_NOTNULL_MESSAGE = "Поле активные сезоны должно содержать значение.";
public static final String PARTICIPANTS_HAS_NULL_ID_MESSAGE = "ID участников не должно содержать null значений.";
public static final String PREFERTHEMES_HAS_NULL_ID_MESSAGE =
        "ID предпочитаемых тем не должно содержать null значений.";
public static final String SEASONS_HAS_NULL_ID_MESSAGE = "ID сезонов не должно содержать null значений.";
public static final String PARTICIPANTS_HAS_NEGATIVE_ID_MESSAGE = "ID участников не должно содержать отрицательных значений.";
public static final String PREFERTHEMES_HAS_NEGATIVE_ID_MESSAGE =
        "ID предпочитаемых тем не должно содержать отрицательных значений.";
public static final String SEASONS_HAS_NEGATIVE_ID_MESSAGE = "ID сезонов не должно содержать отрицательных значений.";


public static final int DESCRIPTION_SIZE_MIN = 0;
public static final int DESCRIPTION_SIZE_MAX = 2000;
public static final String DESCRIPTION_SIZE_MESSAGE =
        DESCRIPTION_SIZE_MIN == 0 ?
        "Описание должно содержать не более " + DESCRIPTION_SIZE_MAX + " символов." :
        "Описание должно содержать не менее " + DESCRIPTION_SIZE_MIN + " и не более " + DESCRIPTION_SIZE_MAX +
        " символов.";
public static final String DESCRIPTION_NOTNULL_MESSAGE = "Поле описание должно содержать значение.";
public static final String PRESENTATIONSLIDE_NOTNULL_MESSAGE = "Поле презентация должно содержать значение.";
public static final String HARDNESS_NOTNULL_MESSAGE = "Поле сложность должно содержать значение.";
public static final String SKILLS_MIN_MESSAGE = "Должен быть выбран хотя бы один навык.";
public static final String SKILLS_NOTNULL_MESSAGE = "Поле навыки должно содержать значение.";
public static final String SKILLS_HAS_NULL_ID_MESSAGE = "ID навыков не должно содержать null значений.";

public static
boolean hasNegativeValue(final Collection<Long> collection) {
    return collection.stream().anyMatch(l -> l < 0);
}

public static
boolean hasNullValue(final Collection<?> collection) {
    return collection.stream().anyMatch(Objects::isNull);
}
}
