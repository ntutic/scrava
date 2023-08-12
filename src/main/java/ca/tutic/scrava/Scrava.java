package ca.tutic.scrava;

import java.io.IOException;
import java.net.URISyntaxException;

import ca.tutic.scrava.ui.CLI;

public class Scrava {
    public static void main(String[] args) throws Exception {
        CLI cmd = new CLI();
        cmd.parseCommand(args);
    }
}
