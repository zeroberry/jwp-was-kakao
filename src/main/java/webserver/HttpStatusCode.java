package webserver;

public enum HttpStatusCode {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");
    
    private final int code;
    private final String message;
    
    HttpStatusCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
    
    public String generateResponseLine() {
        return code + " " + message + "\r\n";
    }
}
