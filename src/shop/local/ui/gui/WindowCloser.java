package shop.local.ui.gui;

import shop.local.domain.Shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class is a WindowAdapter that handles closing a window.
 * When the window is closed, a dialog will appear asking the user if they really want to exit the shop.
 * Depending on the user's selection, the window is either closed and the application exited, or the window remains open.
 * @author Sund
 */

public class WindowCloser extends WindowAdapter {
    private Shop eshop;

    public WindowCloser(Shop eshop) {
        this.eshop =eshop;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Window window = e.getWindow();                              // Das Fenster, das geschlossen wird
        int result = JOptionPane.showOptionDialog(
                window,
                "Do you really want to leave the shop?",    // Text der Frage an den Benutzer
                "Exit shop?",                                       // Titel des Dialogs
                JOptionPane.YES_NO_OPTION,                          // Ja/Nein-Optionen anzeigen
                JOptionPane.QUESTION_MESSAGE,                       // Symbol für eine Frage anzeigen
                null,                                               // Kein benutzerdefiniertes Symbol verwenden
                new String[] { "Yes", "No" },                       // Optionen, die dem Benutzer angezeigt werden
                "No");                                              // Standardmäßig "No" auswählen

        if (result == JOptionPane.YES_OPTION) {                     // Wenn der Benutzer "Yes" auswählt
            window.setVisible(false);                               // Das Fenster unsichtbar machen
            window.dispose();                                       // Das Fenster freigeben
            eshop.disconnect();
            System.exit(0);                                   // Die Anwendung beenden
        }
    }
}
