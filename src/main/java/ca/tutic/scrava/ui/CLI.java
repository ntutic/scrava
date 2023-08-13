package ca.tutic.scrava.ui;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ca.tutic.scrava.Engine;

public class CLI extends Engine {
    private static Map<String, Map<String, String>> flags = getFlags();

    public CLI() throws IOException {
        super();
    }

    public void parseCommand(String[] args) throws Exception {
        if (args.length == 0) {
            interactiveMode();
            return;
        } else if (args.length == 1) {
            throw new IllegalArgumentException("Missing command options");
        }

        String[] names = parseNames(args);
        Map<String, String> options = parseFlags(args);

        switch (args[0].toLowerCase()) {
            case "startproject" -> startProject(names[0], options);
            case "genspider" -> genSpider(names[0], options);
            case "crawl" -> startSpider(names, options);
        }
    }

    public static String[] parseNames(String[] args) {
        List<String> names = new ArrayList<>();
        for (int i = 1; i < args.length && !args[i].startsWith("-"); i++) {
            names.add(args[i]);
        }
        return names.toArray(new String[names.size()]);
    }

    public static Map<String, Map<String, String>> getFlags() {
        Map<String, Map<String, String>> flags = new HashMap<>();
        for (String command: new String[]{"startproject", "genspider", "crawl"}) {
            flags.put(command, new HashMap<String, String>());
        }

        flags.get("startproject").put("-p", "path");
        flags.get("startproject").put("-t", "template");

        flags.get("genspider").put("-d", "domain");
        flags.get("genspider").put("-t", "template");

        flags.get("crawl").put("-p", "pipeline");
        flags.get("crawl").put("-o", "output");
        flags.get("crawl").put("-db", "database");

        return flags;
    }

    public static Map<String, String> parseFlags(String[] args) {
        Map<String, String> options = new HashMap<>();
        String command = args[0];
        for (int i = 2; i < args.length; i++) {
            for (Entry<String, String> flagSet: flags.get(command).entrySet()) {
                if (args[i] == flagSet.getKey()) {
                    options.put(flagSet.getValue(), args[i + 1]);
                    i++;
                    break;
                }
            }
        }
        return options;
    }

    public void interactiveMode() throws Exception {
        System.out.println("Write command arguments: ");
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.nextLine().split(" ");
        scanner.close();
        parseCommand(args);
    }
}
