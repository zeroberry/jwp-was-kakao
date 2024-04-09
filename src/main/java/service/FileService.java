package service;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.entity.RequestHeader;
import webserver.entity.ResponseEntity;

public class FileService {
    public ResponseEntity serveFile(final RequestHeader request) throws IOException, URISyntaxException {
        String path = request.getPath();
        byte[] staticFileData = FileIoUtils.loadFileFromClasspath(path);
        return ResponseEntity.of(path, staticFileData);
    }
}
