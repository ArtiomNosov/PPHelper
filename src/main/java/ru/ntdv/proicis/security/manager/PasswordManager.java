package ru.ntdv.proicis.security.manager;

import java.security.SecureRandom;

public
class PasswordManager {
final static String backpack = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz#$_+=";
final static SecureRandom random = new SecureRandom();

public static
String generateRandomPassword() {
    return generateRandomPassword(random.nextInt(9, 11));
}

private static
String generateRandomPassword(int len) {
    final StringBuilder password = new StringBuilder(len);
    for (int i = 0; i < len; i++) password.append(backpack.charAt(random.nextInt(backpack.length())));
    return password.toString();
}
}
