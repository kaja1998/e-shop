package eshop.client.ui.gui.panels;

import eshop.client.ui.gui.frames.E_EmployeeFrontEnd;
import eshop.client.ui.gui.frames.L_LoginStart;
import eshop.common.entities.User;
import eshop.common.exceptions.LoginException;
import eshop.common.interfaces.ShopInterface;
import eshop.server.domain.Shop;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a panel for the Employee login.
 * It contains input fields for username and password, a login button and the logic for employee login.
 * If the login is successful, the main window is displayed for employees, otherwise an error message is issued.
 * @author Sund
 */

public class L_EmployeeLoginPanel extends JPanel {
    private User loggedinUser = null;
    private ShopInterface eshop;
    private L_LoginStart loginStart;
    private JButton employeeLoginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public L_EmployeeLoginPanel(ShopInterface shop, L_LoginStart loginStart) {
        this.loginStart = loginStart;
        this.eshop = shop;
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 5, 5);

        usernameField = new JTextField(10); // Breite auf 10 Zeichen festlegen
        passwordField = new JPasswordField(10); // Breite auf 10 Zeichen festlegen

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Username:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Password:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(passwordField, constraints);

        employeeLoginButton = new JButton("Login");

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        add(employeeLoginButton, constraints);
    }

    private void setupEvents() {
        employeeLoginButton.addActionListener(e -> LoginEmployee());
    }

    private void LoginEmployee() {
        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            loggedinUser = eshop.loginEmployee(userName, password);
            E_EmployeeFrontEnd ebe = new E_EmployeeFrontEnd(eshop, loggedinUser);
            ebe.setVisible(true);
            loginStart.dispose();

        } catch (LoginException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}
