package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

import service.UserService;
import utils.FileIoUtils;

public class Controller {
    private static final String TEMPLE_PREFIX = "./templates";
    private static final String HTML_SUFFIX = ".html";
    private static final String STATIC_PREFIX = "./static";
    
    private final UserService userService;
    
    public Controller(final UserService userService) {
        this.userService = userService;
    }
    
    public byte[] service(final RequestHeader requestHeader) {
        try {
            if (requestHeader.getMethod().equals("GET")) {
                return doGet(requestHeader);
            }
            throw new IllegalArgumentException("지원하지 않는 메소드입니다.");
        } catch (Exception e) {
            throw new IllegalArgumentException("요청 처리에 실패했습니다.");
        }
    }
    
    private byte[] doGet(final RequestHeader requestHeader) throws IOException, URISyntaxException {
        String path = requestHeader.getPath();
        if (path.equals("/user/create")) {
            userService.createUser(requestHeader.getQueryParameter());
            return new byte[0];
        }
        if (path.endsWith(HTML_SUFFIX)) {
            return FileIoUtils.loadFileFromClasspath(TEMPLE_PREFIX + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PREFIX + path);
    }
}
