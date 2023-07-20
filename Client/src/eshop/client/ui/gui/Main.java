package eshop.client.ui.gui;
import eshop.client.ui.gui.frames.L_LoginStart;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Main Method of the GUI
 * @author Sund
 */

public class Main {

    public static final int DEFAULT_PORT = 6779;

    public static void main(String[] args) {
        int portArg = 0;
        String hostArg = null;
        InetAddress ia = null;

        // ---
        // Hier werden die main-Parameter geprüft:
        // ---

        // Host- und Port-Argument einlesen, wenn angegeben
        if (args.length > 2) {
            System.out.println("Aufruf: java <Klassenname> [<hostname> [<port>]]");
            System.exit(0);
        }
        switch (args.length) {
            case 0:
                try {
                    ia = InetAddress.getLocalHost();
                } catch (Exception e) {
                    System.out.println("XXX InetAdress-Fehler: " + e);
                    System.exit(0);
                }
                hostArg = ia.getHostName(); // host ist lokale Maschine
                portArg = DEFAULT_PORT;
                break;
            case 1:
                portArg = DEFAULT_PORT;
                hostArg = args[0];
                break;
            case 2:
                hostArg = args[0];
                try {
                    portArg = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Aufruf: java BibClientGUI [<hostname> [<port>]]");
                    System.exit(0);
                }
        }

        // Swing-UI auf dem GUI-Thread initialisieren
        // (host und port müssen für Verwendung in inner class final sein)
        final String host = hostArg;
        final int port = portArg;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    L_LoginStart loginStart = new L_LoginStart(host, port);
                    loginStart.setVisible(true);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}

