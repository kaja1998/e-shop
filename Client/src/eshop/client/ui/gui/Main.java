package eshop.client.ui.gui;
import eshop.client.ui.gui.frames.L_LoginStart;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Main Method of the GUI
 * @author Sund
 */

public class Main {

    // Definition der Standardportnummer für den Server
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
            // Wenn mehr als 2 Argumente übergeben wurden, wird eine Fehlermeldung ausgegeben
            System.out.println("Call: java <classname> [<hostname> [<port>]]");
            System.exit(0);
        }
        switch (args.length) {
            case 0:
                try {
                    // Wenn keine Argumente übergeben wurden, wird die lokale IP-Adresse ermittelt
                    ia = InetAddress.getLocalHost();
                } catch (Exception e) {
                    // Bei einem Fehler wird eine Fehlermeldung ausgegeben und das Programm beendet
                    System.out.println("XXX InetAdress-Error: " + e);
                    System.exit(0);
                }
                // Host wird auf die lokale IP-Adresse gesetzt, da kein Argument angegeben wurde
                hostArg = ia.getHostName(); // host ist lokale Maschine
                portArg = DEFAULT_PORT; // Standardport wird gesetzt
                break;
            case 1:
                // Wenn ein Argument übergeben wurde, wird dieses als Host interpretiert
                portArg = DEFAULT_PORT; // Standardport wird gesetzt
                hostArg = args[0]; // Host wird auf das übergebene Argument gesetzt
                break;
            case 2:
                // Wenn zwei Argumente übergeben wurden, wird das erste als Host und das zweite als Port interpretiert
                hostArg = args[0]; // Host wird auf das erste Argument gesetzt
                try {
                    // Der übergebene Port wird als Integer-Wert interpretiert und gesetzt
                    portArg = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    // Bei einem Fehler (z. B. ungültiger Port) wird eine Fehlermeldung ausgegeben und das Programm beendet
                    System.out.println("Call: java EshopClientGUI [<hostname> [<port>]]");
                    System.exit(0);
                }
        }

        // Swing-UI auf dem GUI-Thread initialisieren
        final String host = hostArg; // Der Host wird final gesetzt, um ihn in der inneren Klasse verwenden zu können
        final int port = portArg; // Der Port wird final gesetzt, um ihn in der inneren Klasse verwenden zu können
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Ein neues L_LoginStart-Objekt wird erstellt und sichtbar gemacht
                    L_LoginStart loginStart = new L_LoginStart(host, port);
                    loginStart.setVisible(true);
                } catch (IOException e) {
                    // Fehler protokollieren
                    // Hier wird der Stacktrace in die Konsole geschrieben.
                    e.printStackTrace();

                    // Benutzer informieren
                    // Eine Fehlermeldung wird dem Benutzer über ein Dialogfenster angezeigt
                    JOptionPane.showMessageDialog(null, "There has been occurred an error.");
                }
            }
        });
    }
}

