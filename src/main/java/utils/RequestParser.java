package utils;

import webserver.entity.RequestBody;
import webserver.entity.RequestEntity;
import webserver.entity.RequestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    public static RequestEntity parse(final BufferedReader br) throws IOException {
        String requestLine = br.readLine();

        List<String> headerLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            headerLines.add(line);
        }

        RequestHeader requestHeader = RequestHeader.fromHeaderString(requestLine, headerLines);

        return new RequestEntity(
                RequestHeader.fromHeaderString(requestLine, headerLines),
                RequestBody.fromBodyString(IOUtils.readData(br, requestHeader.getContentLength()))
        );
    }
}
