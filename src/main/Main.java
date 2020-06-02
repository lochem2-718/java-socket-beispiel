package main;

import java.io.*;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !(args[0].equals("server") || args[0].equals("client"))) {
            System.err.println("ICH BRAUCH A ARGUMENT DU TROTTEL\nBin ich a client oder server?");
            System.exit(1);
        } else if (args[0].equals("server")) {
            try {
                new Server(12345).ausfuehren();
            } catch (IOException ioErr) {
                System.err.println(ioErr.toString());
            }
        } else {
            new Client("localhost", 12345).ausfuehren();
        }
    }
}