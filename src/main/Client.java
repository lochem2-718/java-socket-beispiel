package main;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private Verbindung serverVerbindung;

    public Client(String ipAdresse, int port) throws IOException {
        serverVerbindung = new Verbindung(new Socket(ipAdresse, port));
    }

    public void ausfuehren() {
        System.out.println("Client starten...");
        try {
            int clientZahl;
            String nachricht = serverVerbindung.nachrichtLesen();
            System.out.println("Client: Begrüßung");
            if (nachricht.equals("Servus!")) {
                nachricht = serverVerbindung.nachrichtLesen();
                // Nach ein Integerzahl in einem String suchen
                Pattern p = Pattern.compile("(\\d+)");
                Matcher m = p.matcher(nachricht);
                if (m.find()) {
                    clientZahl = Integer.parseInt(m.group(1));
                    System.out.printf("Ich bin Client %d\n", clientZahl);
                    if (new Random().nextBoolean()) {
                        serverVerbindung.nachrichtSenden("Ich mag Schildkröten :)\n");
                        System.out.println("Client: unhöflicher Begrüßung gesandt");
                    } else {
                        serverVerbindung.nachrichtSenden("Servus allmächtiger Spielserver! Wie lautet Ihr Befehl?\n");
                        System.out.println("Client: höflicher Begrüßung gesandt");
                    }
                    nachricht = serverVerbindung.nachrichtLesen();
                    System.out.printf("Server sagt \"%s\" zu Client %d\n", nachricht, clientZahl);
                }
            }
        } catch (IOException etwas) {
            Fehler.berichten(etwas.getLocalizedMessage());
        } finally {
            serverVerbindung.schliessen();
        }
    }
}

