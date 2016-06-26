package ua.taxi.utils.template;

import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

/**
 * Created by serhii on 26.04.16.
 */
public class TemplateEngineUtils {

    private static final Configuration cfg;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_24);
        cfg.setDefaultEncoding("UTF-8");
        try {
            URL resourceDirURL = TemplateEngineUtils.class.getResource("/resources/templates");
            String fileName = resourceDirURL.getFile();
            cfg.setDirectoryForTemplateLoading(
                    new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    /**
     * @param fileName relative name, begins from src/resources/templates/
     *                 example googlemapmarker.html
     */
    public static String merge(String fileName, Map proper) throws IOException, TemplateException {
        Template template = cfg.getTemplate(fileName, "UTF-8");
        try (StringWriter writer = new StringWriter()) {
            template.process(proper, writer);
            return writer.toString();
        }
    }


}
