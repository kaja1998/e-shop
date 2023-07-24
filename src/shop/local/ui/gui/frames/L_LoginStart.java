package shop.local.ui.gui.frames;

import shop.local.domain.Shop;
import shop.local.ui.gui.WindowCloser;
import shop.local.ui.gui.panels.L_CustomerRegistrationPanel;
import shop.local.ui.gui.panels.L_CustomerLoginPanel;
import shop.local.ui.gui.panels.L_EmployeeLoginPanel;

import javax.swing.*;
import java.io.IOException;

public class L_LoginStart extends JFrame {

    private Shop eshop;
    private L_CustomerRegistrationPanel customerRegistrationPanel;
    private L_CustomerLoginPanel customerLoginPanel;
    private L_EmployeeLoginPanel employeeLoginPanel;


        public L_LoginStart() {
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
            this.addWindowListener(new WindowCloser(eshop));

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