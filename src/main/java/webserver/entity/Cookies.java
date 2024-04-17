package webserver.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_NAME_VALUE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";
    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;
    private static final String ROOT_PATH = "/";

    private final Map<String, String> cookies;
    private final String path;

    public Cookies(final Map<String, String> cookies, final String path) {
        this.cookies = new HashMap<>(cookies);
        this.path = path;
    }

    public static Cookies from(final String cookies) {
        final Map<String, String> cookieMap = new HashMap<>();
        if (cookies != null) {
            final String[] cookiePairs = cookies.split(COOKIE_DELIMITER);
            for (String cookiePair : cookiePairs) {
                final String[] pair = cookiePair.split(COOKIE_NAME_VALUE_DELIMITER);
                cookieMap.put(pair[COOKIE_KEY_INDEX], pair[COOKIE_VALUE_INDEX]);
            }
        }
        return new Cookies(cookieMap, ROOT_PATH);
    }

    public String getCookie(final String key) {
        return cookies.get(key);
    }

    public String toCookieString() {
        final String cookiesToString = cookies.entrySet().stream()
                .map(entry -> entry.getKey() + COOKIE_NAME_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER));
        return cookiesToString + "; Path=" + path;
    }
}
