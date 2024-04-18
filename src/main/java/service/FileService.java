package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class FileService {

    private static final String STATIC_PREFIX = "./static/";
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String TEMPLATES_SUFFIX = "";
    private static final ClassPathTemplateLoader TEMPLATE_LOADER = new ClassPathTemplateLoader(TEMPLATES_PREFIX, TEMPLATES_SUFFIX);
    private static final Handlebars HANDLEBARS = new Handlebars(TEMPLATE_LOADER);

    public byte[] serveFile(final String path) throws IOException, URISyntaxException {
        final String extension = getFileExtension(path);
        if (extension.equals("html")) {
            return FileIoUtils.loadFileFromClasspath("." + TEMPLATES_PREFIX + "/" + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PREFIX + path);
    }

    public byte[] render(final String path, final Map<String, Object> data) throws IOException {
        final Template template = HANDLEBARS.compile(path);
        return template.apply(data).getBytes();
    }

    private String getFileExtension(final String path) {
        final int lastIndexOf = path.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return path.substring(lastIndexOf + 1);
    }
}
