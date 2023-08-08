package ca.tutic.scrava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.Properties;
import java.util.Scanner;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;

public class CLI {
    private static Engine engine; 

    public static void main(String[] args) throws IOException {
        Properties props = parseConfigs();
        engine = new Engine(props, new Scanner(System.in));

        String[] options;
        if (args.length == 0) {
            interactiveMode();
            return;
        } else if (args.length > 0) {
            throw new IllegalArgumentException("Missing command options");
        } else {
            options = Arrays.copyOfRange(args, 1, args.length);
        }

        switch (args[0].toLowerCase()) {
            case "crawl" -> crawl(options);
            case "startproject" -> startProject(options);
            case "genspider" -> genSpider(options);
        }
    }

    public static Properties parseConfigs() throws IOException {
        Properties props = new Properties();
        System.out.println(CLI.class.getProtectionDomain().getCodeSource().getLocation());
        File file = Paths.get(System.getProperty("user.dir"), "config.properties").toFile();
        if (!file.exists()) {

        }

        try (FileInputStream stream = new FileInputStream(file)) {
            props.load(stream);
        } catch (IOException e) {
            throw e;
        }

        return props;
    }

    public static void interactiveMode() {
        String command, args;
        while (true) {
            //command = this.scanner.re
        }
    }

    public static void crawl(String[] options) {
        String[] spiders = validatedSpiders(options);
        // TODO: parse flags and CLI opttions
            
        for (String spider: spiders) {
            //engine.runSpider(spider);
        }
    }

    private static String[] validatedSpiders(String[] spiders) {
        if (spiders.length == 0) {
            spiders = new String[]{"Example"};
        } else {
            for (int i = 0; i < spiders.length; i++) {
                spiders[i] = StringUtils.capitalize(spiders[i]);
            }
        }

        return spiders;
    }

    
    public static void startProject(String[] options) {

    }

    public static void genSpider(String[] options) {
        
    }
}
