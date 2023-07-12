package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.LoginException;
import shop.local.entities.User;
import shop.local.ui.gui.Frames.EmployeeBackEnd;
import shop.local.ui.gui.Frames.LoginStart;

import javax.swing.*;
import java.awt.*;

public class LoginEmployeePanel extends JPanel {
    private User loggedinUser = null;
    private Shop eshop;
    private LoginStart loginStart;
    private JButton employeeLoginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginEmployeePanel(Shop shop, LoginStart loginStart) {
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
            JOptionPane.showMessageDialog(this, "Fill in all fields", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            loggedinUser = eshop.loginEmployee(userName, password);
            EmployeeBackEnd ebe = new EmployeeBackEnd(eshop, loggedinUser);
            ebe.setVisible(true);
            loginStart.dispose();

        } catch (LoginException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}
