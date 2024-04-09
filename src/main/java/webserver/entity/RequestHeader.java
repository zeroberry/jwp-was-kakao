package webserver.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import utils.FormDataParser;

public class RequestHeader {
    private final HttpMethod method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String> queryParameters;
    
    public RequestHeader(final HttpMethod method,
        final String path,
        final Map<String, String> headers,
        final Map<String, String> queryParameters) {
        
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.queryParameters = queryParameters;
    }
    
    public static RequestHeader fromHeaderString(final String requestLine, final List<String> headerLines) {
        final String[] line = requestLine.split(" ");
        final String method = line[0];
        final String[] url = line[1].split("\\?");
        final String path = url[0];
        final String queryString = url.length > 1 ? url[1] : "";
        
        final Map<String, String> headers = new HashMap<>();
        headerLines.forEach(headerLine -> {
            final String[] header = headerLine.split(": ");
            headers.put(header[0], header[1]);
        });
        return new RequestHeader(HttpMethod.of(method), path, headers, FormDataParser.parse(queryString));
    }
    
    public Optional<String> getHeaderField(final String headerKey) {
        return Optional.ofNullable(headers.get(headerKey));
    }
    
    public HttpMethod getMethod() {
        return method;
    }
    
    public String getPath() {
        return path;
    }
    
    public Map<String, String> getQueryParameter() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
