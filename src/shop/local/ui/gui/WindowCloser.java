package shop.local.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowCloser extends WindowAdapter {

//    @Override
//    public void windowClosing(WindowEvent e) {
//        Window window = e.getWindow();
//        int result = JOptionPane.showConfirmDialog
//                        (window,
//                        "Do you really want to leave the shop?",
//                        "Exit shop?",
//                        JOptionPane.YES_NO_OPTION);
//        if (result == 0) {
//            window.setVisible(false);
//            window.dispose();
//            System.exit(0);
//        }
//    }

    @Override
    public void windowClosing(WindowEvent e) {
        Window window = e.getWindow(); // Das Fenster, das geschlossen wird
        int result = JOptionPane.showOptionDialog(
                window,
                "Do you really want to leave the shop?", // Text der Frage an den Benutzer
                "Exit shop?", // Titel des Dialogs
                JOptionPane.YES_NO_OPTION, // Ja/Nein-Optionen anzeigen
                JOptionPane.QUESTION_MESSAGE, // Symbol für eine Frage anzeigen
                null, // Kein benutzerdefiniertes Symbol verwenden
                new String[] { "Yes", "No" }, // Optionen, die dem Benutzer angezeigt werden
                "No"); // Standardmäßig "No" auswählen

        if (result == JOptionPane.YES_OPTION) { // Wenn der Benutzer "Yes" auswählt
            window.setVisible(false); // Das Fenster unsichtbar machen
            window.dispose(); // Das Fenster freigeben
            System.exit(0); // Die Anwendung beenden
        }
    }


}
