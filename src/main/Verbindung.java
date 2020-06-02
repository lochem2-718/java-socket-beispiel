package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Verbindung {
    private final Socket socket;
    private final PrintWriter aus;
    private final BufferedReader in;
    private boolean istOffen = true;

    public Verbindung(Socket socket) throws IOException {
        this.socket = socket;
        socket.setKeepAlive(true);
        aus = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void nachrichtSenden(String nachricht) {
        if (!socket.isClosed()) {
            aus.printf("%s\n", nachricht);
        }
    }

    public String nachrichtLesen() throws IOException {
        char c;
        StringBuilder nachricht = new StringBuilder();
        while (!socket.isClosed() && (c = (char) in.read()) != '\n') {
            nachricht.append(c);
        }
        return nachricht.toString();
    }

    public boolean istOffen() {
        return istOffen;
    }

    public void schliessen() {
        istOffen = false;
        try {
            in.close();
            aus.close();
            socket.close();
        } catch (IOException e) {
            Fehler.berichten("Server: Verdammt noch mal! sChLiEßeN!");
        }
    }
}
