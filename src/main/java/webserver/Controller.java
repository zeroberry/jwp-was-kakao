package webserver;

import service.UserService;
import utils.FileIoUtils;
import webserver.entity.RequestEntity;
import webserver.entity.RequestHeader;
import webserver.entity.ResponseEntity;

public class Controller {
    private final UserService userService;

    public Controller(final UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity service(final RequestEntity request) {
        if (request.getHeader().getMethod().equals("GET")) {
            return doGet(request.getHeader());
        }
        if (request.getHeader().getMethod().equals("POST")) {
            return doPost(request);
        }
        throw new IllegalArgumentException("지원하지 않는 메소드입니다.");
    }

    private ResponseEntity doGet(final RequestHeader requestHeader) {
        String path = requestHeader.getPath();
        if (path.equals("/user/create")) {
            userService.createUser(requestHeader.getQueryParameter());
            return ResponseEntity.redirectResponseEntity("/index.html");
        }

        try {
            byte[] staticFileData = FileIoUtils.loadFileFromClasspath(path);
            return ResponseEntity.of(path, staticFileData);
        } catch (Exception e) {
            return ResponseEntity.notFoundResponseEntity();
        }
    }
    
    private ResponseEntity doPost(final RequestEntity request) {
        String path = request.getHeader().getPath();
        if (path.equals("/user/create")) {
            userService.createUser(request.getBody().get());
            return ResponseEntity.redirectResponseEntity("/index.html");
        }
        throw new IllegalArgumentException("지원하지 않는 메소드입니다.");
    }
}
