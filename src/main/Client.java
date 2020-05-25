import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

public class Client {
    private int port;
    private Verbindung serverVerbindung;

    public Client(InetAddress addresse) {
        Random r = new Random();
        boolean offenePortGefunden = false;
        Socket socket = new Socket();
        while (!offenePortGefunden) {
            offenePortGefunden = true;
            try {
                port = r.nextInt(65_535 - 5000) + 5000;
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
                    "Ich konnte nicht mit dem Server verbinden\uD83D\uDE22.\n" +
                    "%s\n", ioErr.getMessage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // es sollte nicht passieren und ist darum mir vollkommen egal
            }
        }
    }

    public void fuehreAus() {
        this.serverVerbindung.sendeNachricht("Ich mag schildkröten :)\n");
    }
}
