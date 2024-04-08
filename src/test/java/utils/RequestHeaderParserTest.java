package utils;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import webserver.RequestHeader;

@SuppressWarnings("NonAsciiCharacters")
public class RequestHeaderParserTest {
	@Test
	void 요청_헤더를_받아서_파싱한다() throws IOException {
		// given
		final String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
		final StringReader stringReader =  new StringReader(request);
		final BufferedReader br = new BufferedReader(stringReader);
		
		// when
		final RequestHeader header = RequestHeaderParser.parse(br);
		
		// then
		Assertions.assertAll(
			() -> assertThat(header.getPath()).isEqualTo("/index.html"),
			() -> assertThat(header.getMethod()).isEqualTo("GET")
		);
	}
	
	@Test
	void 쿼리스트링을_파싱할_수_있다() throws IOException {
		// given
		final String userId = URLEncoder.encode("cu", StandardCharsets.UTF_8);
		final String name = URLEncoder.encode("이동규", StandardCharsets.UTF_8);
		final String request = "GET /user/create?userId=" + userId + "&name=" + name + " HTTP/1.1\n"
			+ "Host: localhost:8080\nConnection: keep-alive\n"
			+ "Accept: */*";
		final StringReader stringReader =  new StringReader(request);
		final BufferedReader br = new BufferedReader(stringReader);
		
		// when
		final RequestHeader header = RequestHeaderParser.parse(br);
		
		// then
		Assertions.assertAll(
			() -> assertThat(header.getQueryParameter().get("userId"))
				.isEqualTo(URLDecoder.decode(userId, StandardCharsets.UTF_8)),
			() -> assertThat(header.getQueryParameter().get("name"))
				.isEqualTo(URLDecoder.decode(name, StandardCharsets.UTF_8))
		);
	}
	
	@Test
	void 쿼리스트링이_없을때_쿼리파라미터_정보가_없다() throws IOException {
		// given
		final String request = "GET /user/create HTTP/1.1\n"
			+ "Host: localhost:8080\nConnection: keep-alive\n"
			+ "Accept: */*";
		final StringReader stringReader =  new StringReader(request);
		final BufferedReader br = new BufferedReader(stringReader);
		
		// when
		final RequestHeader header = RequestHeaderParser.parse(br);
		
		// then
		assertThat(header.getQueryParameter().keySet().isEmpty()).isTrue();
	}
}
