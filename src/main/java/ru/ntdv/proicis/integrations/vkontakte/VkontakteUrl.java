package ru.ntdv.proicis.integrations.vkontakte;

import ru.ntdv.proicis.integrations.contract.Url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import static ru.ntdv.proicis.constant.ValidationStrings.URLVKONTAKTE_PATTERN_REGEXP;

public
class VkontakteUrl implements Url {
private static final Pattern pattern = Pattern.compile(URLVKONTAKTE_PATTERN_REGEXP);
private final URL url;

public
VkontakteUrl(final String stringUrl) throws IllegalArgumentException {
    url = getUrlFromString(stringUrl);
}

public static
URL getUrlFromString(final String stringUrl) throws IllegalArgumentException {
    final var matcher = pattern.matcher(stringUrl.trim());
    if (matcher.matches()) {
        try {
            return new URL("https://vk.com/" + matcher.group(matcher.groupCount()));
        } catch (final MalformedURLException forwarded1) {
            try {
                return new URL("https://vk.com/" + stringUrl);
            } catch (final MalformedURLException forwarded2) {
                throw new IllegalArgumentException("Неверная ссылка на профиль Вконтакте.");
            }
        }
    } else {
        throw new IllegalArgumentException("Неверный формат ссылки.");
    }
}

@Override
public
URL getLink() {
    return url;
}

@Override
public
String getLinkAsString() {
    return url.toString();
}
}
