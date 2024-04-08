package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import webserver.RequestHeader;

public class RequestHeaderParser {
    public static RequestHeader parse(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] requestLine = line.split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        
        Map<String, String> headers = new HashMap<>();
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(": ");
            headers.put(header[0], header[1]);
        }
        
        return new RequestHeader(method, path, headers);
    }
}
