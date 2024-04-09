package webserver;

import java.util.Map;

public class Response {
    private final int statusCode;
    private final Map<String, String> headers;
    private final byte[] body;

    public Response(final int statusCode, final Map<String, String> headers, final byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public byte[] toResponseMessage() {
        return body;
    }
}
