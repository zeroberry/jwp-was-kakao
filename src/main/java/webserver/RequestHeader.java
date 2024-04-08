package webserver;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String> queryParameters;
    
    public RequestHeader(final String method,
        final String path,
        final Map<String, String> headers,
        final Map<String, String> queryParameters) {
        
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.queryParameters = queryParameters;
    }
    
    public String getMethod() {
        return method;
    }
    
    public String getPath() {
        return path;
    }
    
    public String get(final String headerKey) {
        return Optional.ofNullable(headers.get(headerKey)).orElse("");
    }
    
    public Map<String, String> getQueryParameter() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
