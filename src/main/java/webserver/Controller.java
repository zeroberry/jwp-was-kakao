package webserver;

import dto.CreateUserDto;
import service.FileService;
import service.UserService;
import webserver.entity.RequestEntity;
import webserver.entity.ResponseEntity;

public class Controller {
    private static final String USER_CREATE_PATH = "/user/create";

    private final UserService userService;
    private final FileService fileService;

    public Controller(final UserService userService, final FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    public ResponseEntity service(final RequestEntity request) {
        if (request.pathEquals(USER_CREATE_PATH)) {
            return createUser(request);
        }

        try {
            byte[] file = fileService.serveFile(request.getHeader());
            return ResponseEntity.of(request.getHeader().getPath(), file);
        } catch (Exception e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }

    private ResponseEntity createUser(final RequestEntity request) {
        if (request.isGet()) {
            userService.createUser(new CreateUserDto(request.getHeader().getQueryParameter()));
            return ResponseEntity.redirectResponseEntity("/index.html");
        }
        if (request.isPost()) {
            userService.createUser(new CreateUserDto(request.getBody().get()));
            return ResponseEntity.redirectResponseEntity("/index.html");
        }

        throw new IllegalArgumentException("존재하지 않는 요청입니다.");
    }
}
