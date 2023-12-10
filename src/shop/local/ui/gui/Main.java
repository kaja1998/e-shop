package shop.local.ui.gui;
import shop.local.ui.gui.frames.L_LoginStart;

import javax.swing.SwingUtilities;

/**
 * Main Method of the GUI
 * @author Sund
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                L_LoginStart loginStart = new L_LoginStart();
                loginStart.setVisible(true);
            }
        });
    }
}

