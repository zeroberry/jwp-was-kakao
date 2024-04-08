package webserver;

import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private final String method;
    private final String path;
    private final Map<String, String> headers;
    
    public RequestHeader(String method, String path, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.headers = headers;
    }
    
    public String getMethod() {
        return method;
    }
    
    public String getPath() {
        return path;
    }
    
    public String get(String headerKey) {
        return Optional.ofNullable(headers.get(headerKey)).orElse("");
    }
}
