package ca.tutic.scrava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import java.net.URL;
import java.nio.file.Path;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import ca.tutic.scrava.Templater;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import ca.tutic.scrava.ui.CLI;

public class Engine {
    Properties props;

    public Engine() throws IOException {
        props = parseProperties();
    }

    public static boolean isProjectDir() {
        return isProjectDir(System.getProperty("user.dir"));
    }

    public static boolean isProjectDir(String dir) {
        File file = Paths.get(dir, "scrava.properties").toFile();
        return file.exists();
    }

    public static void assertProjectDir() throws IllegalArgumentException {
        if (!isProjectDir()) {
            throw new IllegalArgumentException(
                    "Relocate to project folder or create new one with \"startproject [name]\".");
        }
    }



    public static Properties parseProperties() throws IOException {
        return parseProperties(System.getProperty("user.dir"));
    }

    public static Properties parseProperties(String dir) throws IOException {
        Properties props = new Properties();
        File file = Paths.get(dir, "config.properties").toFile();
        if (file.exists()) {
            try (FileInputStream stream = new FileInputStream(file)) {
                props.load(stream);
            } catch (IOException e) {
                throw e;
            }
        }
        return props;
    }

    public void startSpider(String[] spiders, Map<String, String> options) {

    }

    public void startProject(String name, Map<String, String> options) throws Exception {
        // Defaults
        if (!options.containsKey("path")) {options.put("path", System.getProperty("user.dir"));}
        if (!options.containsKey("template")) {options.put("template", "default");}
        
        if (isProjectDir(options.get("path"))) {
            System.out.println("Project already existing at target location");
            return;
        }

        File template;
        URL templateURL = getClass().getClassLoader().getResource("templates/projects/" + options.get("template"));
        if (templateURL != null) {
            template = new File(templateURL.toURI());
        } else {
            template = new File(options.get("template"));
        }

        if (!template.exists() || !template.isDirectory()) {
            System.out.println("Invalid template folder");
            return;
        }

        File target = new File(options.get("path"));
        if (!template.exists() || !template.isDirectory()) {
            System.out.println("Invalid target folder");
            return;
        }
        
        Templater.newProject(name, target, template);
    }

    public void genSpider(String name, Map<String, String> options) {

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
