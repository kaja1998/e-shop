package shop.local.ui.gui;

import shop.local.domain.Shop;
import shop.local.ui.gui.panels.CustomerRegistrationButtonPanel;
import shop.local.ui.gui.panels.CustomerLoginButtonPanel;
import shop.local.ui.gui.panels.EmployeeLoginButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginStart extends JFrame {

        private  Shop eshop;
        private CustomerRegistrationButtonPanel CustomerRegistrationButtonPanel;
        private CustomerLoginButtonPanel CustomerLoginButtonPanel;
        private EmployeeLoginButtonPanel EmployeeLoginButtonPanel;

        public LoginStart() {
            super("Login");
            try {
                eshop = new Shop("ESHOP");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            initialize();
        }

        private void initialize() {
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowCloser());

            setLayout(new BorderLayout());

            // North
            CustomerRegistrationButtonPanel = new CustomerRegistrationButtonPanel();
            add(CustomerRegistrationButtonPanel, BorderLayout.NORTH);

            // Center
            CustomerLoginButtonPanel = new CustomerLoginButtonPanel();
            add(CustomerLoginButtonPanel, BorderLayout.CENTER);

            // South
            EmployeeLoginButtonPanel = new EmployeeLoginButtonPanel(this, eshop);
            add(EmployeeLoginButtonPanel, BorderLayout.SOUTH);

            // Setze Fenster-Größe und sichtbar
            setSize(300, 150);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }