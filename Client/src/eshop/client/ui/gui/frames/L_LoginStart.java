package eshop.client.ui.gui.frames;

import eshop.client.net.ShopFassade;
import eshop.client.ui.gui.panels.L_CustomerLoginPanel;
import eshop.client.ui.gui.panels.L_CustomerRegistrationPanel;
import eshop.common.interfaces.ShopInterface;
import eshop.client.ui.gui.WindowCloser;
import eshop.client.ui.gui.panels.L_EmployeeLoginPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class L_LoginStart extends JFrame {

    private ShopInterface eshop;
    private L_CustomerRegistrationPanel customerRegistrationPanel;
    private L_CustomerLoginPanel customerLoginPanel;
    private L_EmployeeLoginPanel employeeLoginPanel;


        public L_LoginStart(String host, int port) throws HeadlessException, IOException {
            super("Login");
            eshop = new ShopFassade(host, port);
            initialize();
        }

        private void initialize() {
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowCloser());

            JTabbedPane tabbedPane = new JTabbedPane();

            customerRegistrationPanel = new L_CustomerRegistrationPanel(eshop, this);
            tabbedPane.addTab("Customer Registration", customerRegistrationPanel);

            customerLoginPanel = new L_CustomerLoginPanel(eshop, this);
            tabbedPane.addTab("Customer Login", customerLoginPanel);

            employeeLoginPanel = new L_EmployeeLoginPanel(eshop, this);
            tabbedPane.addTab("Employee Login", employeeLoginPanel);

            getContentPane().add(tabbedPane);

            // Setze Fenster-Größe und sichtbar
            setSize(450, 450);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        public void openCustomerLogin() {
            JTabbedPane tabbedPane = (JTabbedPane) getContentPane().getComponent(0);
            tabbedPane.setSelectedComponent(customerLoginPanel);
        }
    }