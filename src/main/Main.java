package main;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !(args[0].equals("server") || args[0].equals("client"))) {
            System.err.println("ICH BRAUCH A ARGUMENT DU TROTTEL\nBin ich a client oder server?");
            System.exit(1);
        } else {
            try {
                if (args[0].equals("server")) {
                    new Server(12345).ausfuehren();
                } else {
                    new Client("localhost", 12345).ausfuehren();
                }
            } catch (IOException e) {
                Fehler.berichten(e.getLocalizedMessage());
            }
        }
    }
}