package shop.local.ui.gui;

import shop.local.domain.Shop;
import shop.local.ui.gui.panels.CustomerRegistrationButtonPanel;
import shop.local.ui.gui.panels.LoginCustomerPanel;
import shop.local.ui.gui.panels.LoginEmployeePanel;

import javax.swing.*;
import java.io.IOException;

public class LoginStart extends JFrame {

    private Shop eshop;
    private CustomerRegistrationButtonPanel customerRegistrationButtonPanel;
    private LoginCustomerPanel loginCustomerPanel;
    private LoginEmployeePanel loginEmployeePanel;


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

            JTabbedPane tabbedPane = new JTabbedPane();

            customerRegistrationButtonPanel = new CustomerRegistrationButtonPanel();
            tabbedPane.addTab("Customer Registration", customerRegistrationButtonPanel);

            loginCustomerPanel = new LoginCustomerPanel(eshop, this);
            tabbedPane.addTab("Customer Login", loginCustomerPanel);

            loginEmployeePanel = new LoginEmployeePanel(eshop, this);
            tabbedPane.addTab("Employee Login", loginEmployeePanel);

            getContentPane().add(tabbedPane);

            // Setze Fenster-Größe und sichtbar
            setSize(400, 200);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }