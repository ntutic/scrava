package ca.tutic.scrava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Templater {
    public static void newProject(String name, File targetDir, File templateDir) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(templateDir);

        Map<String, Object> model = new HashMap<>();
        model.put("projectName", name);

        targetDir = new File(targetDir, name);
        targetDir.mkdirs();

        try (Stream<Path> walk = Files.walk(templateDir.toPath())) {
            List<Path> paths = walk.filter(Files::isRegularFile).toList();
            for (Path path: paths) {
                makeTemplateFile(
                    cfg.getTemplate(templateDir.toPath().relativize(path).toString()), 
                    targetDir, 
                    model
                );
            }
        }
    }

    public static void makeTemplateFile(Template fromTemplate, File toDir, Map<String, Object> model) throws Exception {
        StringWriter stringWriter = new StringWriter();
        String toPath = fromTemplate.getName().split(".ftl")[0];
        File toFile = new File(toDir, toPath);
        toFile.getParentFile().mkdirs();
        
        try (FileWriter fileWriter = new FileWriter(toFile)) {
            fromTemplate.process(model, stringWriter);
            fileWriter.write(stringWriter.toString());
            System.out.println("Template processed and written to file successfully");
        }

    }
}
