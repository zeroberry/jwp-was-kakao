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
    private static final String USER_LIST_PATH = "/user/list";
    private static final String USER_LIST_HTML_PATH = "/user/list.html";
    private static final String INDEX_HTML_PATH = "/index.html";
    private static final String USER_LOGIN_FAILED_HTML_PATH = "/user/login_failed.html";

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

        if (request.pathEquals(USER_LOGIN_HTML_PATH) && isLogin(request)) {
            return ResponseEntity.redirectResponseEntity(INDEX_HTML_PATH);
        }

        try {
            return getResponseFile(request);
        } catch (Exception e) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
    }

    private ResponseEntity getResponseFile(final RequestEntity request) throws IOException, URISyntaxException {
        if (request.pathEquals(USER_LIST_PATH) || request.pathEquals(USER_LIST_HTML_PATH)) {
            return getUserList(request);
        }

        final byte[] file = fileService.serveFile(request.getPath());
        return ResponseEntity.of(request.getPath(), file);
    }

    private ResponseEntity getUserList(final RequestEntity request) throws IOException {
        if (!isLogin(request)) {
            return ResponseEntity.redirectResponseEntity(USER_LOGIN_HTML_PATH);
        }
        String path = request.getPath();
        if (path.equals(USER_LIST_PATH)) {
            path += ".html";
        }
        final Collection<User> users = userService.findAll();
        final byte[] file = fileService.render(path, Map.of("users", users));
        return ResponseEntity.of(path, file);
    }

    private ResponseEntity createUser(final RequestEntity request) {
        if (request.isGet()) {
            validateCreateUser(request.getQueryParameters());
            userService.createUser(CreateUserDto.of(request.getQueryParameters()));
            return ResponseEntity.redirectResponseEntity(INDEX_HTML_PATH);
        }
        if (request.isPost()) {
            validateCreateUser(request.getBody().get());
            userService.createUser(CreateUserDto.of(request.getBody().get()));
            return ResponseEntity.redirectResponseEntity(INDEX_HTML_PATH);
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
                final User user = userService.login(new LoginDto(request.getBodyParameter("userId"), request.getBodyParameter("password")));
                final HttpSession httpSession = new HttpSession();
                httpSession.setAttribute("user", user);
                SessionManager.addSession(httpSession);

                return ResponseEntity.redirectResponseEntity(
                        INDEX_HTML_PATH,
                        Cookies.sessionCookie(httpSession.getId())
                );
            } catch (IllegalArgumentException e) {
                return ResponseEntity.redirectResponseEntity(USER_LOGIN_FAILED_HTML_PATH);
            }
        }

        throw new IllegalArgumentException("존재하지 않는 요청입니다.");
    }

    private boolean isLogin(final RequestEntity request) {
        return SessionManager.getSession(request.getCookieSessionId()) != null;
    }
}
