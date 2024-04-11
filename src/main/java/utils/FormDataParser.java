package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormDataParser {
    public static Map<String, String> parse(final String dataString) {
        final Map<String, String> queryParameters = new HashMap<>();

        if (dataString == null || dataString.isEmpty()) {
            return queryParameters;
        }

        final String[] parameters = dataString.split("&");
        Arrays.stream(parameters).forEach(parameter -> {
            final String[] keyValue = parameter.split("=");
            queryParameters.put(decode(keyValue[0]), decode(keyValue[1]));
        });
        return queryParameters;
    }

    private static String decode(final String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
