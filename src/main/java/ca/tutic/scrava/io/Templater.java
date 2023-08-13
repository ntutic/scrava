package ca.tutic.scrava.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Templater {
    public static void create(String name, File template, File targetDir) throws IOException, FileNotFoundException {
        File templateDir;
        if (template.isDirectory()) {
            templateDir = template;
        } else {
            templateDir = template.getParentFile();
        }
        
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(templateDir);

        Map<String, Object> model = new HashMap<>();
        model.put("name", name);

        File targetProject = new File(targetDir, name);
        targetProject.mkdirs();

        List<Path> paths;
        if (template.isDirectory()) {
            paths = Files.walk(template.toPath())
                         .filter(Files::isRegularFile)
                         .toList();
        } else {
            paths = List.of(template.toPath());
        }
        paths.forEach(
            path -> {
                String templatePath = templateDir.toPath().relativize(path).toString();
                try {
                    makeTemplateFile(
                        cfg.getTemplate(templatePath),
                        targetProject,
                        model
                    );
                } catch (FileNotFoundException e) {
                    System.out.println("Couldn't find template file at " + templatePath);
                } catch (IOException e) {
                    System.out.println("Couldn't process template file at " + templatePath);
                }
            }
        );

        if (template.isDirectory()) {
            File[] files = template.listFiles(File::isDirectory);

            for (File file: files) {
                Path path = file.toPath();
                Path newDir;
                try {
                    newDir = targetProject.toPath().resolve(template.toPath().relativize(path));
                    Files.createDirectories(newDir);
                } catch (IOException e) {
                    System.out.println("Couldn't create folder");
                }

            }
        }
    }

    public static void makeTemplateFile(Template fromTemplate, File toDir, Map<String, Object> model) {
        StringWriter stringWriter = new StringWriter();
        String toPath = fromTemplate.getName().split(".ftl")[0];
        File toFile = new File(toDir, toPath);
        toFile.getParentFile().mkdirs();

        try (FileWriter fileWriter = new FileWriter(toFile)) {
            fromTemplate.process(model, stringWriter);
            fileWriter.write(stringWriter.toString());
        } catch (IOException | TemplateException e) {
            System.out.println("Couldn't process project template file: " + fromTemplate.getName());
        }
    }
}
