package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormDataParser {
    public static Map<String, String> parse(final String dataString) {
        Map<String, String> queryParameters = new HashMap<>();
        
        if (dataString == null || dataString.isEmpty()) {
            return queryParameters;
        }
        
        final String[] parameters = dataString.split("&");
        Arrays.stream(parameters).forEach(parameter -> {
            final String[] keyValue = parameter.split("=");
            queryParameters.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8),
                URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
        });
        return queryParameters;
    }
}
