package shop.local.ui.gui.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerLoginButtonPanel extends JPanel {

    private JButton CustomerLoginButtonPanel;

    public CustomerLoginButtonPanel() {
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        CustomerLoginButtonPanel = new JButton("Customer Login");
        add(CustomerLoginButtonPanel);
    }

    private void setupEvents() {
        CustomerLoginButtonPanel.addActionListener(new CustomerLoginButtonPanel.CustomerLoginListener());
    }

    class CustomerLoginListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Aktion, die beim Klicken des Buttons ausgeführt werden soll
            // TODO: Implementiere die Logik für Customer Login
        }
    }
}
