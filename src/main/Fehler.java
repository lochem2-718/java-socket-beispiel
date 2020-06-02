package main;

public class Fehler {
    public static void berichten(String fehlerNachricht) {
        System.err.printf("Die Regierung lügt dir.\n" +
                "Fehlern gibt's halt net!\n" +
                "DIESES PROGRAMM IST DOCH PERFEKT!!!!\n" +
                "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!!\n" +
                "\uD83D\uDE22".repeat(22) + "\n" +
                "%s\n", fehlerNachricht);
    }
}
