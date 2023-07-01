package shop.local.ui.gui.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerRegistrationButtonPanel extends JPanel {

    private JButton CustomerRegistrationButton;

    public CustomerRegistrationButtonPanel() {
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        CustomerRegistrationButton = new JButton("Customer Registration");
        add(CustomerRegistrationButton);
    }

    private void setupEvents() {
        CustomerRegistrationButton.addActionListener(new CustomerRegistrationListener());
    }

    class CustomerRegistrationListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Aktion, die beim Klicken des Buttons ausgeführt werden soll
            // TODO: Implementiere die Logik für die Kundenregistrierung
        }
    }
}

