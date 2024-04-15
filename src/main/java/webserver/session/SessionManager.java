package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

    public static void addSession(final HttpSession httpSession) {
        SESSIONS.put(httpSession.getId(), httpSession);
    }

    public static HttpSession getSession(final String jsessionid) {
        return SESSIONS.get(jsessionid);
    }
}
