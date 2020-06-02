package main;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final ServerSocket socket;

    public Server(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public void ausfuehren() {
        System.out.println("Server starten...");
        System.out.println("Server: warten auf Verbindungen...");
        int jeVerbundeneClients = 0;
        while (jeVerbundeneClients < 5) {
            try {
                Verbindung clientVerbindung = new Verbindung(socket.accept());
                jeVerbundeneClients++;
                new Thread(new ClientHandler(jeVerbundeneClients, clientVerbindung)).start();
            } catch (IOException e) {
                Fehler.berichten(e.getLocalizedMessage());
            }
        }
    }

    private class ClientHandler implements Runnable {
        int clientZahl;
        final Verbindung clientVerbindung;

        ClientHandler(int clientZahl, final Verbindung clientVerbindung) {
            this.clientZahl = clientZahl;
            this.clientVerbindung = clientVerbindung;
        }

        @Override
        public void run() {
            try {
                String nachricht;
                clientVerbindung.nachrichtSenden("Servus!");
                System.out.println("Server: Begrüßung");
                clientVerbindung.nachrichtSenden(String.format("Du bist Client %d", clientZahl));
                System.out.println("Server: Client Zahl ausgegeben");
                nachricht = clientVerbindung.nachrichtLesen();

                if (!nachricht.equals("Servus allmächtiger Spielserver! Wie lautet Ihr Befehl?")) {
                    System.out.println("Server: Client is unhöflich");
                    clientVerbindung.nachrichtSenden("Was?! Du unhöflicher Sack!");
                } else {
                    System.out.println("Server: Client is höflich");
                    clientVerbindung.nachrichtSenden("Du musst mir nicht so sprechen.");
                }
                clientVerbindung.schliessen();
            } catch (IOException etwas) {
                Fehler.berichten(etwas.getLocalizedMessage());
            }
        }
    }
}