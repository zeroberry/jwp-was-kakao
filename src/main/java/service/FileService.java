package service;

import utils.FileIoUtils;
import webserver.entity.RequestHeader;
import webserver.entity.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileService {
    public ResponseEntity serveFile(final RequestHeader request) throws IOException, URISyntaxException {
        final String path = request.getPath();
        final byte[] staticFileData = FileIoUtils.loadFileFromClasspath(path);
        return ResponseEntity.of(path, staticFileData);
    }
}
