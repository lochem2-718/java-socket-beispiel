package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.LinkedList;

public class Server {
    private final ServerSocket socket;
    private final LinkedList<Verbindung> clients = new LinkedList<>();

    public Server(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public void ausfuehren() {
        Date datum = new Date();
        long startZeit = datum.getTime();
        long aktuellZeit = startZeit;
        while (aktuellZeit - startZeit < 5000) {
            try {
                Verbindung clientVerbindung = new Verbindung(socket.accept());
                clients.add(clientVerbindung);
            } catch (IOException e) {
                System.err.printf("Die Regierung lügt dir.\n" +
                        "Fehlern gibt's halt net!\n" +
                        "DIESES PROGRAMM IST DOCH PERFEKT!!!!\n" +
                        "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!!\n" +
                        "Ich konnte keine neue main.Client main.Verbindung hinzufügen\uD83D\uDE22.\n" +
                        "%s\n", e.getMessage());
            }
            aktuellZeit = datum.getTime();
        }

        String nachricht;
        int clientZahl = 1;
        for (Verbindung clientVerbindung : clients) {
            try {
                clientVerbindung.nachrichtSenden("Servus!");
                clientVerbindung.nachrichtSenden(String.format("Du bist Client %d", clientZahl));
                clientZahl++;
                nachricht = clientVerbindung.nachrichtLesen();

                if (!nachricht.equals("Servus allmächtiger Spielserver! Wie lautet Ihr Befehl?")) {
                    clientVerbindung.nachrichtSenden("Was?! Du unhöflicher Sack!");
                } else {
                    clientVerbindung.nachrichtSenden("Du musst mir nicht so sprechen.");
                }
            } catch (IOException e) {
            }
        }

        for (Verbindung clientVerbindung : clients) {
            while (clientVerbindung.istOffen()) {
                try {
                    clientVerbindung.schliessen();
                } catch (IOException e) {
                    System.err.println("Server: Verdammt noch mal! sChLiEßeN!");
                }
            }
        }
    }
}