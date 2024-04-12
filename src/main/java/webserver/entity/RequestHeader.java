package webserver.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private final RequestLine requestLine;
    private final Map<String, String> headers;

    public RequestHeader(final RequestLine requestLine, final Map<String, String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public static RequestHeader fromHeaderString(final String requestLine, final List<String> headerLines) {
        final Map<String, String> headers = new HashMap<>();
        headerLines.forEach(headerLine -> {
            final String[] header = headerLine.split(": ");
            headers.put(header[0], header[1]);
        });
        return new RequestHeader(new RequestLine(requestLine), headers);
    }

    public Optional<String> getHeaderField(final String headerKey) {
        return Optional.ofNullable(headers.get(headerKey));
    }

    public int getContentLength() {
        return Integer.parseInt(getHeaderField("Content-Length").orElse("0"));
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getQueryParameters() {
        return requestLine.getQueryParameters();
    }

    public Map<String,String> getCookies() {
        final Map<String, String> cookies = new HashMap<>();
        final String cookie = headers.get("Cookie");
        if (cookie == null) {
            return cookies;
        }
        final String[] cookiePairs = cookie.split("; ");
        for (String cookiePair : cookiePairs) {
            final String[] pair = cookiePair.split("=");
            cookies.put(pair[0], pair[1]);
        }
        return cookies;
    }
}
