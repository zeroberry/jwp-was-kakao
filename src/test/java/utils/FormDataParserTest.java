package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class FormDataParserTest {
    @Test
    void 쿼리스트링을_파싱할_수_있다() {
        // given
        final String userId = URLEncoder.encode("cu", StandardCharsets.UTF_8);
        final String name = URLEncoder.encode("이=동&규", StandardCharsets.UTF_8);
        final String formDataString = "userId=" + userId + "&name=" + name;

        // when
        final Map<String, String> parsedData = FormDataParser.parse(formDataString);

        // then
        assertThat(parsedData)
                .isEqualTo(new HashMap<String, String>() {{
                    put("userId", "cu");
                    put("name", "이=동&규");
                }});
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 쿼리스트링이_없을때_쿼리파라미터_정보가_없다(final String input) {
        System.out.println("input: " + input);
        assertThat(FormDataParser.parse(input).keySet().isEmpty()).isTrue();
    }
}
