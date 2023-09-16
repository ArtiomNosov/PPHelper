package ru.ntdv.proicis.security.manager;

import com.ibm.icu.text.Transliterator;

public
class LoginManager {
private static final String _cyrillicToLatin = "Any-Latin; NFD; [^\\p{Alnum}] Remove";
private static final Transliterator cyryllicToLatin = Transliterator.getInstance(_cyrillicToLatin);

public static
String generateLogin(final String firstName, final String secondName, final String thirdName) {
    return (cyryllicToLatin.transform(firstName.substring(0, 1) + thirdName.charAt(0))
            + "." + cyryllicToLatin.transform(secondName))
            .replace("ʹ", "");
}
}
