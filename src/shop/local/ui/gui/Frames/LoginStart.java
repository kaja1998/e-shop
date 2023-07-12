package shop.local.ui.gui.Frames;

import shop.local.domain.Shop;
import shop.local.ui.gui.WindowCloser;
import shop.local.ui.gui.panels.RegistrationCustomerPanel;
import shop.local.ui.gui.panels.LoginCustomerPanel;
import shop.local.ui.gui.panels.LoginEmployeePanel;

import javax.swing.*;
import java.io.IOException;

public class LoginStart extends JFrame {

    private Shop eshop;
    private RegistrationCustomerPanel registrationCustomerPanel;
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

            registrationCustomerPanel = new RegistrationCustomerPanel(eshop, this);
            tabbedPane.addTab("Customer Registration", registrationCustomerPanel);

            loginCustomerPanel = new LoginCustomerPanel(eshop, this);
            tabbedPane.addTab("Customer Login", loginCustomerPanel);

            loginEmployeePanel = new LoginEmployeePanel(eshop, this);
            tabbedPane.addTab("Employee Login", loginEmployeePanel);

            getContentPane().add(tabbedPane);

            // Setze Fenster-Größe und sichtbar
            setSize(450, 450);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public void openCustomerLogin() {
            JTabbedPane tabbedPane = (JTabbedPane) getContentPane().getComponent(0);
            tabbedPane.setSelectedComponent(loginCustomerPanel);
        }
    }