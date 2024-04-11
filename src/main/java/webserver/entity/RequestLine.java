package webserver.entity;

import utils.FormDataParser;

import java.util.Collections;
import java.util.Map;

public class RequestLine {
    private static final String REQUEST_LINE_SPLITTER = " ";
    private static final String URL_SPLITTER = "\\?";
    private static final String EMPTY_STRING = "";
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAMETERS_INDEX = 1;

    private final HttpMethod method;
    private final String path;
    private final Map<String, String> queryParameters;

    public RequestLine(final String requestLine) {
        final String[] line = requestLine.split(REQUEST_LINE_SPLITTER);
        final String method = line[METHOD_INDEX];
        final String[] url = line[URL_INDEX].split(URL_SPLITTER);
        final String path = url[PATH_INDEX];
        final String queryParameters = url.length > 1 ? url[QUERY_PARAMETERS_INDEX] : EMPTY_STRING;

        this.method = HttpMethod.of(method);
        this.path = path;
        this.queryParameters = FormDataParser.parse(queryParameters);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
