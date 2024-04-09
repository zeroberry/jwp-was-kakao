package webserver;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import service.UserService;
import webserver.entity.RequestEntity;
import webserver.entity.RequestHeader;

@SuppressWarnings("NonAsciiCharacters")
public class ControllerTest {

    @ParameterizedTest(name = "path가 {0}일 경우")
    @ValueSource(strings = {"/index.html", "/css/styles.css"})
    void path에_맞는_파일을_응답한다(final String path) {
        // given
        final Controller controller = new Controller(new UserService());
        final RequestEntity request = new RequestEntity(new RequestHeader("GET", path, new HashMap<>(), new HashMap<>()), null);
        
        // when & then
        Assertions.assertDoesNotThrow(() -> controller.service(request));
    }
    
    @Test
    void 파일이_없을때_예외가_발생한다() {
        // given
        final Controller controller = new Controller(new UserService());
        final RequestEntity request = new RequestEntity(new RequestHeader("GET", "/index2.html", new HashMap<>(), new HashMap<>()), null);
        
        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> controller.service(request));
    }
}
