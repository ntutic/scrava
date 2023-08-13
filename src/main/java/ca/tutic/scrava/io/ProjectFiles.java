package ca.tutic.scrava.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.function.Predicate;

import ca.tutic.scrava.Scrava;

public class ProjectFiles {
    
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

    /**
     * Finds file with fallthrough resource -> absolute path 
     * @param fileName
     * @return
     * @throws URISyntaxException
     */
    public static File getResourceFile(String fileName) throws URISyntaxException, FileNotFoundException {
        return getResourceFile(fileName, "", x -> true);
    }
    
    public static File getResourceFile(String fileName, Predicate<File> filters) throws URISyntaxException, FileNotFoundException {
        return getResourceFile(fileName, "", filters);
    }

    public static File getResourceFile(String fileName, String dir, Predicate<File> filters) throws URISyntaxException, FileNotFoundException {
        URL fileURL = Scrava.class.getClassLoader().getResource(dir + fileName);
        File file;

        // From resource
        if (fileURL != null) {
            file = new File(fileURL.toURI());

        // From absolute path
        } else {
            file = new File(dir + fileName);
        }

        if (!filters.test(file)) {
            throw new IllegalArgumentException("File " + file.getAbsolutePath() + " failed filters " + filters.toString());
        }
        return file;
    }
}
