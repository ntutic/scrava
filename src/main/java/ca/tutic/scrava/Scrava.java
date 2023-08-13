package ca.tutic.scrava;

import ca.tutic.scrava.ui.CLI;

public class Scrava {
    public static void main(String[] args) throws Exception {
        CLI cmd = new CLI();
        cmd.parseCommand(args);
    }
}
