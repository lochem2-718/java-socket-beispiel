package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private Verbindung serverVerbindung;

    public Client(InetAddress addresse) {
        Random r = new Random();
        boolean offenePortGefunden = false;
        Socket socket = new Socket();
        while (!offenePortGefunden) {
            offenePortGefunden = true;
            try {
                int port = r.nextInt(65_535 - 5000) + 5000;
                socket.bind(new InetSocketAddress(addresse, port));
            } catch (IOException e) {
                offenePortGefunden = false;
            }
        }
        try {
            serverVerbindung = new Verbindung(socket);
        } catch (IOException ioErr) {
            System.err.printf("Die Regierung lügt dir.\n" +
                    "Fehlern gibt's halt net!\n" +
                    "DIESES PROGRAMM IST DOCH PERFEKT!!!!\n" +
                    "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!!\n" +
                    "Ich konnte nicht mit dem main.Server verbinden\uD83D\uDE22.\n" +
                    "%s\n", ioErr.getMessage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // es sollte nicht passieren und ist darum mir vollkommen egal
            }
        }
    }

    public void ausfuehren() {
        try {
            int clientZahl;
            String nachricht = serverVerbindung.nachrichtLesen();
            if (nachricht.equals("Servus!")) {
                nachricht = serverVerbindung.nachrichtLesen();
                Pattern p =  Pattern.compile("(\\d+)");
                Matcher m = p.matcher(nachricht);
                if (m.find()) {
                    clientZahl = Integer.parseInt(m.group(1));
                    if (new Random().nextBoolean()) {
                        serverVerbindung.nachrichtSenden("Ich mag schildkröten :)\n");
                    } else {
                        serverVerbindung.nachrichtSenden("Servus allmächtiger Spielserver! Wie lautet Ihr Befehl?\n");
                    }
                    nachricht = serverVerbindung.nachrichtLesen();
                    System.out.printf("Server sagt \"%s\" zu Client %d\n", nachricht, clientZahl);
                }
            }
        } catch (IOException e) {
            while (serverVerbindung.istOffen()) {
                try {
                    serverVerbindung.schliessen();
                } catch (IOException egal) {
                    System.err.println("Server: Verdammt noch mal! sChLiEßeN!");
                }
            }
        }
    }
}
