package webserver.entity;

import webserver.ContentType;
import webserver.HttpStatusCode;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String LOCATION_KEY = "Location";
    private static final String HTTP_PROTOCOL = "HTTP/1.1";

    private final HttpStatusCode httpStatusCode;
    private final Map<String, String> headers;
    private final byte[] body;

    private ResponseEntity(final HttpStatusCode httpStatusCode, final Map<String, String> headers, final byte[] body) {
        this.httpStatusCode = httpStatusCode;
        this.headers = headers;
        this.body = body;
    }

    private ResponseEntity(final HttpStatusCode httpStatusCode, final Map<String, String> headers) {
        this.httpStatusCode = httpStatusCode;
        this.headers = new HashMap<>(headers);
        this.body = new byte[0];
    }

    public static ResponseEntity of(final String path, final byte[] body) {
        return new ResponseEntity(HttpStatusCode.OK,
                Map.of(CONTENT_TYPE_KEY, ContentType.of(path) + ";charset=utf-8",
                        CONTENT_LENGTH_KEY, String.valueOf(body.length)),
                body);
    }

    public static ResponseEntity redirectResponseEntity(final String location) {
        return new ResponseEntity(HttpStatusCode.FOUND,
                Map.of(LOCATION_KEY, location));
    }

    public static ResponseEntity redirectResponseEntity(final String location, final Cookies cookies) {
        final ResponseEntity responseEntity = redirectResponseEntity(location);
        responseEntity.setCookie(cookies);
        return responseEntity;
    }

    public static ResponseEntity notFoundResponseEntity() {
        return new ResponseEntity(HttpStatusCode.NOT_FOUND,
                Map.of());
    }

    public static ResponseEntity internalServerErrorResponseEntity() {
        return new ResponseEntity(HttpStatusCode.INTERNAL_SERVER_ERROR,
                Map.of());
    }

    public void setCookie(final Cookies cookies) {
        headers.put(SET_COOKIE_KEY, cookies.toCookieString());
    }

    public String toResponseMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTTP_PROTOCOL + " ");
        stringBuilder.append(httpStatusCode.generateResponseLine());
        headers.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");
        stringBuilder.append(new String(body, StandardCharsets.ISO_8859_1));
        return stringBuilder.toString();
    }
}
