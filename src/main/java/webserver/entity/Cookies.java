package webserver.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_NAME_VALUE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";

    final Map<String, String> cookies;
    final String path;

    public Cookies(final Map<String, String> cookies, final String path) {
        this.cookies = new HashMap<>(cookies);
        this.path = path;
    }

    public Cookies(final String cookies) {
        this.cookies = new HashMap<>();
        this.path = "/";
        if (cookies == null) {
            return;
        }

        final String[] cookiePairs = cookies.split(COOKIE_DELIMITER);
        for (String cookiePair : cookiePairs) {
            final String[] pair = cookiePair.split(COOKIE_NAME_VALUE_DELIMITER);
            this.cookies.put(pair[0], pair[1]);
        }
    }

    public String getCookie(final String key) {
        return cookies.get(key);
    }

    public String toCookieString() {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + COOKIE_NAME_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER)) + "; Path=" + path;
    }
}
