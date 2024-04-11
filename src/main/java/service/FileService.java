package service;

import utils.FileIoUtils;
import webserver.entity.RequestHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileService {
    public byte[] serveFile(final RequestHeader request) throws IOException, URISyntaxException {
        final String path = request.getPath();
        return FileIoUtils.loadFileFromClasspath(path);
    }
}
