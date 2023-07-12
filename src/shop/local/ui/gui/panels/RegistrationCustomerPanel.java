package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.RegisterException;
import shop.local.ui.gui.Frames.LoginStart;

import javax.swing.*;
import java.awt.*;

public class RegistrationCustomerPanel extends JPanel {

    private Shop eshop;
    private LoginStart loginStart;
    private JButton registerButton;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField streetField;
    private JTextField postalCodeField;
    private JTextField cityField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox agreeCheckBox;


    public RegistrationCustomerPanel(Shop shop, LoginStart loginStart) {
        this.loginStart = loginStart;
        this.eshop = shop;
        setupUI();
        setupEvents();
    }

    private void setupUI() {

        // Check Box
        agreeCheckBox = new JCheckBox("I agree to the terms and conditions");

        // Register Button
        registerButton = new JButton("Register Now");

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Labels
        nameField = new JTextField(10);
        lastNameField = new JTextField(10);
        streetField = new JTextField(10);
        postalCodeField = new JTextField(10);
        cityField = new JTextField(10);
        emailField = new JTextField(10);
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(new JLabel("Name:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Last Name:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(lastNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Street and Nr.:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        add(streetField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("Postal Code:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        add(postalCodeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        add(new JLabel("City:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        add(cityField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        add(new JLabel("Email:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        add(new JLabel("Username:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        add(new JLabel("Password:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(agreeCheckBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 9;
        add(registerButton, constraints);
    }

    private void setupEvents() { registerButton.addActionListener(e -> registerCustomer());
    }

    private void registerCustomer() {
            // Perform registration logic here
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            String street = streetField.getText();
            String postalCodeString = postalCodeField.getText();
            String city = cityField.getText();
            String mail = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String registerNow = agreeCheckBox.isSelected() ? "yes" : "no"; // Get the value as "yes" or "no" based on the check box

        if (name.isEmpty() || lastName.isEmpty() || street.isEmpty() || postalCodeString.isEmpty() || city.isEmpty() || mail.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (agreeCheckBox.isSelected()) {
            try {
                int postalCode = Integer.parseInt(postalCodeString);
                eshop.registerCustomer(name, lastName, street, postalCode, city, mail, username, password, registerNow);

                // Felder leeren
                nameField.setText("");
                lastNameField.setText("");
                streetField.setText("");
                postalCodeField.setText("");
                cityField.setText("");
                emailField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                agreeCheckBox.setSelected(false);

                // Erfolgreiche Registrierungsnachricht anzeigen
                JOptionPane.showMessageDialog(this, "Successfully registered");

                // Öffne den Reiter "Customer Login"
                loginStart.openCustomerLogin();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid postal code. Please enter an integer value.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            } catch (RegisterException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Register Error", JOptionPane.ERROR_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

