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

    public String toCookieString() {
        return cookies.entrySet().stream()
                .map(entry -> entry.getKey() + COOKIE_NAME_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER)) + "; Path=" + path;
    }
}
