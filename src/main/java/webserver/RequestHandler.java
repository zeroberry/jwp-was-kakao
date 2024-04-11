package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RequestParser;
import webserver.entity.RequestEntity;
import webserver.entity.ResponseEntity;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Controller controller;

    private final Socket connection;

    public RequestHandler(final Socket connectionSocket, final Controller controller) {
        this.connection = connectionSocket;
        this.controller = controller;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handle(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(final InputStream in, final OutputStream out) throws IOException {
        final DataOutputStream dos = new DataOutputStream(out);

        final ResponseEntity responseData = service(in);
        dos.writeBytes(responseData.toResponseMessage());
        dos.flush();
    }

    private ResponseEntity service(final InputStream in) {
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));

            final RequestEntity request = RequestParser.parse(br);
            return controller.service(request);
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgumentException : {}", e.getMessage());
            return ResponseEntity.notFoundResponseEntity();
        } catch (Exception e) {
            logger.error("Unknown Exception : {}", e.getMessage());
            return ResponseEntity.internalServerErrorResponseEntity();
        }
    }
}
