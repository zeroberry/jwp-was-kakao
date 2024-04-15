package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private final Map<String, Object> values;

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
        this.values = new HashMap<>();
    }

    public void setAttribute(final String key, final Object value) {
        values.put(key, value);
    }

    public String getId() {
        return id;
    }
}
