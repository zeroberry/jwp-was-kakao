package webserver.entity;

import webserver.ContentType;
import webserver.HttpStatusCode;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {
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
                Map.of("Content-Type", ContentType.of(path) + ";charset=utf-8",
                        "Content-Length", String.valueOf(body.length)),
                body);
    }

    public static ResponseEntity redirectResponseEntity(final String location) {
        return new ResponseEntity(HttpStatusCode.FOUND,
                Map.of("Location", location));
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
        headers.put("Set-Cookie", cookies.toCookieString());
    }

    public String toResponseMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 ");
        stringBuilder.append(httpStatusCode.generateResponseLine());
        headers.forEach((key, value) -> stringBuilder.append(key).append(": ").append(value).append("\r\n"));
        stringBuilder.append("\r\n");
        stringBuilder.append(new String(body, StandardCharsets.ISO_8859_1));
        return stringBuilder.toString();
    }
}
