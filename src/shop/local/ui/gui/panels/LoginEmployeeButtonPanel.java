package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.LoginException;
import shop.local.entities.User;
import shop.local.ui.gui.EmployeeBackEnd;

import javax.swing.*;
import java.awt.*;

public class LoginEmployeeButtonPanel extends JPanel {
    private User loggedinUser = null;
    private JFrame EmployeeLoginFrame;
    private Shop eshop;
    private JButton employeeLoginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginEmployeeButtonPanel(JFrame EmployeeLoginFrame, Shop shop) {
        this.EmployeeLoginFrame = EmployeeLoginFrame;
        eshop = shop;
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

        if (!userName.isEmpty() && !password.isEmpty()) {
            try {
                loggedinUser = eshop.loginEmployee(userName, password);
                EmployeeBackEnd ebe = new EmployeeBackEnd(eshop, loggedinUser);
                ebe.setVisible(true);
                EmployeeLoginFrame.dispose();

            } catch (LoginException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
            }
        }
    }
}
