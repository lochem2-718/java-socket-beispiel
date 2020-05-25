package main;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server(12345);
            server.ausfuehren();
        } catch (IOException ioErr) {
            System.err.println(ioErr.toString());
        }
    }
}