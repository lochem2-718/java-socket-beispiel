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

    public void sendeNachricht(String nachricht) {
        if (!clientSocket.isClosed()) {
            aus.println(nachricht);
        }
    }

    public Optional<String> leseNachricht() {
        try {
            // Wir versuchen auf jeden Fall, Exceptions zu vermeiden.
            // Java Exceptions verbrauchen in vielen JREs viele Systemressourcen.
            if (!clientSocket.isClosed()) {
                return Optional.ofNullable(in.readLine());
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public boolean istOffen() {
        return istOffen;
    }

    public void schliesse() throws IOException {
        istOffen = false;
        in.close();
        aus.close();
        clientSocket.close();
    }
}
