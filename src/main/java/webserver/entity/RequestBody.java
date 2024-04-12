package webserver.entity;

import utils.FormDataParser;

import java.util.Collections;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> requestBody;

    public RequestBody(final Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> get() {
        return Collections.unmodifiableMap(requestBody);
    }

    public String get(final String key) {
        return requestBody.get(key);
    }

    public static RequestBody fromBodyString(final String bodyString) {
        final Map<String, String> body = FormDataParser.parse(bodyString);
        return new RequestBody(body);
    }
}
