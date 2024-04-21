package webserver.entity;

import webserver.session.HttpSession;
import webserver.session.SessionManager;

import java.util.Map;
import java.util.Optional;

public class RequestEntity {
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;
    private final HttpSession httpSession;

    public RequestEntity(final RequestHeader requestHeader, final RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.httpSession = SessionManager.getSession(requestHeader.getSessionId());
    }

    public boolean isGet() {
        return requestHeader.getMethod().equals(HttpMethod.GET);
    }

    public boolean isPost() {
        return requestHeader.getMethod().equals(HttpMethod.POST);
    }

    public boolean pathEquals(final String path) {
        return requestHeader.getPath().equals(path);
    }

    public String getPath() {
        return requestHeader.getPath();
    }

    public Map<String, String> getQueryParameters() {
        return requestHeader.getQueryParameters();
    }

    public RequestHeader getHeader() {
        return requestHeader;
    }

    public RequestBody getBody() {
        return requestBody;
    }

    public String getBodyParameter(final String key) {
        return requestBody.get(key);
    }

    public String getCookieSessionId() {
        return requestHeader.getCookies().getSessionId();
    }

    public boolean isCookieLogined() {
        return requestHeader.getCookies().isLogined();
    }

    public Optional<HttpSession> getSession() {
        return Optional.ofNullable(httpSession);
    }
}
