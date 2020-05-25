import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server {
    private final int port;
    private final ServerSocket socket;
    private LinkedList<Verbindung> clients = new LinkedList<>();

    public Server(int port) throws IOException {
        this.port = port;
        this.socket = new ServerSocket(port);
    }

    public void fuehreAus() {
        while (true) {
            try {
                Verbindung clientVerbindung = new Verbindung(socket.accept());
                clients.add(clientVerbindung);
            } catch (IOException e) {
                System.err.printf("Die Regierung lügt dir.\n" +
                        "Fehlern gibt's halt net!\n" +
                        "DIESES PROGRAMM IST DOCH PERFEKT!!!!\n" +
                        "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!!\n" +
                        "Ich konnte keine neue Client Verbindung hinzufügen\uD83D\uDE22.\n" +
                        "%s\n", e.getMessage());
            }
        }
    }
}