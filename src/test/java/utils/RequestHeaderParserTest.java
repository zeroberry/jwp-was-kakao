package utils;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import webserver.RequestHeader;

public class RequestHeaderParserTest {
	@Test
	void 요청_헤더를_받아서_파싱한다() throws IOException {
		// given
		String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
		StringReader stringReader =  new StringReader(request);
		BufferedReader br = new BufferedReader(stringReader);
		
		// when
		RequestHeader header = RequestHeaderParser.parse(br);
		
		// then
		Assertions.assertAll(
			() -> assertThat(header.getPath()).isEqualTo("/index.html"),
			() -> assertThat(header.getMethod()).isEqualTo("GET")
		);
	}
}
