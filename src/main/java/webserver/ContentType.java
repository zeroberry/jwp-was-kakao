package webserver;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ContentType {

    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    ICO("ico", "image/x-icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    SVG("svg", "image/svg+xml"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    WOFF2("woff2", "font/woff2"),
    EOT("eot", "font/vnd.ms-fontobject"),
    DEFAULT("none", "text/plain");

    private static final Pattern EXTENSION_MATCHER = Pattern.compile("\\.([^./\\s]+)$");
    private static final int GROUP_INDEX = 1;

    private final String extension;
    private final String contentType;

    ContentType(final String extension, final String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static String of(final String filePath) {
        final String extension = extension(filePath);
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.extension.equals(extension))
                .findFirst()
                .orElse(DEFAULT)
                .contentType;
    }

    private static String extension(final String filePath) {
        final Matcher matcher = EXTENSION_MATCHER.matcher(filePath);
        if (matcher.find()) {
            return matcher.group(GROUP_INDEX);
        }
        return DEFAULT.extension;
    }
}
