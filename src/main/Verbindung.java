package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Verbindung {
    private final Socket clientSocket;
    private final PrintWriter aus;
    private final BufferedReader in;
    private boolean istOffen = true;

    public Verbindung(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        clientSocket.setKeepAlive(true);
        aus = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void nachrichtSenden(String nachricht) {
        if (!clientSocket.isClosed()) {
            aus.println(nachricht + "\n");
        }
    }

    public String nachrichtLesen() throws IOException {
        Optional<String> nachricht = Optional.empty();
        while (nachricht.isEmpty() && !clientSocket.isClosed()) {
            try {
                nachricht = Optional.ofNullable(in.readLine());
                if(nachricht.isEmpty()) {
                    // Damit benutzt dieser busy-wait Lösung nicht so viel CPU Zyklen
                    Thread.sleep(5);
                }
            } catch (InterruptedException e) {
            }
        }
        if (nachricht.isEmpty()) {
            throw new IOException();
        }
        return nachricht.get();
    }

    public boolean istOffen() {
        return istOffen;
    }

    public void schliessen() throws IOException {
        istOffen = false;
        in.close();
        aus.close();
        clientSocket.close();
    }
}
