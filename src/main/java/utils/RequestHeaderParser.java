package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import webserver.RequestHeader;

public class RequestHeaderParser {
    public static RequestHeader parse(final BufferedReader br) throws IOException {
        String line = br.readLine();
        final String[] requestLine = line.split(" ");
        final String method = requestLine[0];
        final String[] url = requestLine[1].split("\\?");
        final String path = url[0];
        final String queryString = url.length > 1 ? url[1] : "";
        
        final Map<String, String> headers = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            final String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }
        
        return new RequestHeader(method, path, headers, parseQueryParameters(queryString));
    }
    
    private static Map<String, String> parseQueryParameters(final String query) {
        Map<String, String> queryParameters = new HashMap<>();
        
        if (query.isEmpty()) {
            return queryParameters;
        }
        
        final String[] parameters = query.split("&");
        Arrays.stream(parameters).forEach(parameter -> {
            final String[] keyValue = parameter.split("=");
            queryParameters.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8),
                URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
        });
        return queryParameters;
    }
}
