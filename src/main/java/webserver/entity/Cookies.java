package webserver.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_SESSION_KEY = "JSESSIONID";
    private static final String COOKIE_NAME_VALUE_DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";
    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;
    private static final String ROOT_PATH = "/";
    private static final String LOGINED_KEY = "logined";
    private static final String LOGINED_VALUE = "true";

    private final Map<String, String> cookies;
    private final String path;

    public Cookies(final Map<String, String> cookies, final String path) {
        this.cookies = new HashMap<>(cookies);
        this.path = path;
    }

    public static Cookies empty() {
        return new Cookies(Map.of(), ROOT_PATH);
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

    public static Cookies sessionCookie(final String sessionId) {
        return new Cookies(Map.of(COOKIE_SESSION_KEY, sessionId,
                LOGINED_KEY, LOGINED_VALUE
        ), ROOT_PATH);
    }

    public String getCookie(final String key) {
        return cookies.get(key);
    }

    public String getSessionId() {
        return cookies.get(COOKIE_SESSION_KEY);
    }

    public boolean isLogined() {
        return "true".equals(cookies.get(LOGINED_KEY));
    }

    public List<String> toCookieString() {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + COOKIE_NAME_VALUE_DELIMITER + entry.getValue() + "; Path=" + path)
                .collect(Collectors.toList());
    }
}
