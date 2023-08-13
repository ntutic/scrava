package ca.tutic.scrava.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Configs {

    public static Properties parseProperties() throws IOException {
        return parseProperties(System.getProperty("user.dir"));
    }

    public static Properties parseProperties(String dir) throws IOException {
        Properties props = new Properties();
        File file = Paths.get(dir, "scrava.properties").toFile();
        if (file.exists()) {
            try (FileInputStream stream = new FileInputStream(file)) {
                props.load(stream);
            } catch (IOException e) {
                throw e;
            }
        }
        return props;
    }
}
