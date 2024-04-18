package webserver.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private static final String COOKIE_KEY = "Cookie";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String HEADER_LINE_DELIMITER = ": ";
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;
    private static final String ZERO = "0";

    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final Cookies cookies;

    public RequestHeader(final RequestLine requestLine, final Map<String, String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookies = parseCookies(headers);
    }

    public static RequestHeader fromHeaderString(final String requestLine, final List<String> headerLines) {
        final Map<String, String> headers = new HashMap<>();
        headerLines.forEach(headerLine -> {
            final String[] header = headerLine.split(HEADER_LINE_DELIMITER);
            headers.put(header[HEADER_KEY_INDEX], header[HEADER_VALUE_INDEX]);
        });
        return new RequestHeader(new RequestLine(requestLine), headers);
    }

    public Optional<String> getHeaderField(final String headerKey) {
        return Optional.ofNullable(headers.get(headerKey));
    }

    public int getContentLength() {
        return Integer.parseInt(getHeaderField(CONTENT_LENGTH_KEY).orElse(ZERO));
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

    private Cookies parseCookies(final Map<String, String> headers) {
        final String cookie = headers.get(COOKIE_KEY);
        return Cookies.from(cookie);
    }

    public Cookies getCookies() {
        return cookies;
    }
}
