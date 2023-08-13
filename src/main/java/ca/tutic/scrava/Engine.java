package ca.tutic.scrava;

import java.nio.file.Path;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ca.tutic.scrava.io.Configs;
import ca.tutic.scrava.io.ProjectFiles;
import ca.tutic.scrava.io.Templater;

public class Engine {
    Properties props;

    public Engine() throws IOException {
        props = Configs.parseProperties();
    }

    public void startSpider(String[] spiders, Map<String, String> options) {

    }

    public void startProject(String name, Map<String, String> options) throws IOException, URISyntaxException, FileNotFoundException {
        options.putIfAbsent("path", System.getProperty("user.dir"));
        options.putIfAbsent("template", "default");

        if (props.containsKey("projectName")) {
            System.out.printf("Project \"%s\" already existing at target location %s%n", props.get("projectName"), options.get("path"));
            return;
        } 

        File templateDir = ProjectFiles.getResourceFile(
            options.get("template"),
            "templates/projects/",
            f -> f.exists() && f.isDirectory()
        );


        File targetDir = ProjectFiles.getResourceFile(
            options.get("path"),
            f -> f.exists() && f.isDirectory()            
        );

        Templater.create(name, templateDir, targetDir);
    }

    public void genSpider(String name, Map<String, String> options) throws IOException, URISyntaxException, FileNotFoundException {
        options.putIfAbsent("path", System.getProperty("user.dir"));
        options.putIfAbsent("template", "basic");

        if (!props.containsKey("projectName")) {
            System.out.printf("No project found at target location %s%n", options.get("path"));
            return;
        } 

        File templateFile = ProjectFiles.getResourceFile(
            options.get("template") + ".java.ftl",
            "templates/spiders/",
            f -> f.exists() && f.isFile()
        );


        File targetDir = ProjectFiles.getResourceFile(
            Path.of(options.get("path"), "spiders").toString(),
            f -> f.exists() && f.isDirectory()            
        );

        Templater.create(name, templateFile, targetDir);

    }

    private static String[] validatedSpiders(String[] spiders) {
        if (spiders.length == 0) {
            spiders = new String[] { "Example" };
        } else {
            for (int i = 0; i < spiders.length; i++) {
                spiders[i] = StringUtils.capitalize(spiders[i]);
            }
        }

        return spiders;
    }
}
