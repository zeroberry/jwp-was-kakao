package webserver;

import dto.CreateUserDto;
import dto.LoginDto;
import model.User;
import service.FileService;
import service.UserService;
import webserver.entity.RequestEntity;
import webserver.entity.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

public class Controller {
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LOGIN_PATH = "/user/login";

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
        if (request.pathEquals(USER_LOGIN_PATH)) {
            return login(request);
        }

        try {
            return getResponseFile(request);
        } catch (Exception e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }

    private ResponseEntity getResponseFile(final RequestEntity request) throws IOException, URISyntaxException {
        if (request.pathEquals("/user/list.html")) {
            return getUserList(request);
        }
        // fallback
        if (request.getPath().endsWith(".html")) {
            final byte[] file = fileService.render(request.getPath(), Map.of());
            return ResponseEntity.of(request.getPath(), file);
        }
        final byte[] file = fileService.serveFile(request.getPath());
        return ResponseEntity.of(request.getPath(), file);
    }

    private ResponseEntity getUserList(final RequestEntity request) throws IOException {
        if (!userService.isLogin(request.getCookies().get("JSESSIONID"))) {
            return ResponseEntity.redirectResponseEntity("/user/login.html");
        }
        final Collection<User> users = userService.findAll();
        final byte[] file = fileService.render(request.getPath(), Map.of("users", users));
        return ResponseEntity.of(request.getPath(), file);
    }

    private ResponseEntity createUser(final RequestEntity request) {
        if (request.isGet()) {
            userService.createUser(new CreateUserDto(request.getQueryParameters()));
            return ResponseEntity.redirectResponseEntity("/index.html");
        }
        if (request.isPost()) {
            userService.createUser(new CreateUserDto(request.getBody().get()));
            return ResponseEntity.redirectResponseEntity("/index.html");
        }

        throw new IllegalArgumentException("존재하지 않는 요청입니다.");
    }

    private ResponseEntity login(final RequestEntity request) {
        if (request.isPost()) {
            try {
                String sessionId = userService.login(new LoginDto(request.getBody().get("userId"), request.getBody().get("password")));
                return ResponseEntity.redirectResponseEntity("/index.html", "JSESSIONID=" + sessionId + "; Path=/");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.redirectResponseEntity("/user/login_failed.html");
            }
        }

        throw new IllegalArgumentException("존재하지 않는 요청입니다.");
    }
}
