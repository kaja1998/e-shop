package shop.local.ui.gui;
import shop.local.ui.gui.Frames.LoginStart;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginStart loginStart = new LoginStart();
                loginStart.setVisible(true);
            }
        });
    }
}

