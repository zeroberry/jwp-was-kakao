package webserver.entity;

public class RequestEntity {
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public RequestEntity(final RequestHeader requestHeader, final RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public boolean isGet() {
        return requestHeader.getMethod().equals(HttpMethod.GET);
    }

    public boolean isPost() {
        return requestHeader.getMethod().equals(HttpMethod.POST);
    }

    public boolean pathEquals(final String path) {
        return requestHeader.getPath().equals(path);
    }

    public RequestHeader getHeader() {
        return requestHeader;
    }

    public RequestBody getBody() {
        return requestBody;
    }
}
