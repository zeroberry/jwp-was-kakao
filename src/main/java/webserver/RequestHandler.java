package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.RequestParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Controller controller;

    private Socket connection;

    public RequestHandler(Socket connectionSocket, Controller controller) {
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
        InputStreamReader ir = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(ir);
        
        DataOutputStream dos = new DataOutputStream(out); // 클라이언트로 보낼 것
        
        try {
            RequestEntity request = RequestParser.parse(br);
            byte[] body = controller.service(request);
            
            response200Header(dos, body.length); // 헤더를 채움 (meatadata)
            responseBody(dos, body); // body를 채움 (content, html)
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            response404Error(dos);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
    private void response404Error(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
