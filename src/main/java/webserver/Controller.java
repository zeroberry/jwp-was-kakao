package webserver;

import dto.CreateUserDto;
import dto.LoginDto;
import model.User;
import service.FileService;
import service.UserService;
import webserver.entity.Cookies;
import webserver.entity.RequestEntity;
import webserver.entity.ResponseEntity;
import webserver.session.HttpSession;
import webserver.session.SessionManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

public class Controller {
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LOGIN_PATH = "/user/login";
    private static final String USER_LOGIN_HTML_PATH = "/user/login.html";
    private static final String COOKIE_SESSION_KEY = "JSESSIONID";

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

        if(request.pathEquals(USER_LOGIN_HTML_PATH) && isLogin(request)) {
            return ResponseEntity.redirectResponseEntity("/index.html");
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
        if (!isLogin(request)) {
            return ResponseEntity.redirectResponseEntity("/user/login.html");
        }
        final Collection<User> users = userService.findAll();
        final byte[] file = fileService.render(request.getPath(), Map.of("users", users));
        return ResponseEntity.of(request.getPath(), file);
    }

    private ResponseEntity createUser(final RequestEntity request) {
        if (request.isGet()) {
            validateCreateUser(request.getQueryParameters());
            userService.createUser(CreateUserDto.of(request.getQueryParameters()));
            return ResponseEntity.redirectResponseEntity("/index.html");
        }
        if (request.isPost()) {
            validateCreateUser(request.getBody().get());
            userService.createUser(CreateUserDto.of(request.getBody().get()));
            return ResponseEntity.redirectResponseEntity("/index.html");
        }

        throw new IllegalArgumentException("존재하지 않는 요청입니다.");
    }

    private void validateCreateUser(final Map<String, String> values) {
        if (values.get("userId") == null ||
                values.get("password") == null ||
                values.get("name") == null ||
                values.get("email") == null) {
            throw new IllegalArgumentException("입력값이 올바르지 않습니다.");
        }
    }

    private ResponseEntity login(final RequestEntity request) {
        if (request.isPost()) {
            try {
                final User user = userService.login(new LoginDto(request.getBody().get("userId"), request.getBody().get("password")));
                final HttpSession httpSession = new HttpSession();
                httpSession.setAttribute("user", user);
                SessionManager.addSession(httpSession);

                ResponseEntity responseEntity = ResponseEntity.redirectResponseEntity("/index.html");
                responseEntity.setCookie(new Cookies(Map.of(COOKIE_SESSION_KEY, httpSession.getId()), "/"));
                return responseEntity;
            } catch (IllegalArgumentException e) {
                return ResponseEntity.redirectResponseEntity("/user/login_failed.html");
            }
        }

        throw new IllegalArgumentException("존재하지 않는 요청입니다.");
    }

    private boolean isLogin(final RequestEntity request) {
        return SessionManager.getSession(request.getCookies().getCookie(COOKIE_SESSION_KEY)) != null;
    }
}
