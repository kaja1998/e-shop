package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.RegisterException;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterNewEmployeePanel_Employee extends JPanel {

    private Shop eshop;

    private JTextField nameTextFeld = null;
    private JTextField lastNameTextFeld = null;
    private JTextField usernameTextFeld = null;
    private JTextField passwordTextFeld = null;

    private JButton registerButton;

    public RegisterNewEmployeePanel_Employee(Shop shop) {
        this.eshop = shop;
        setupUI();

        setupEvents();
    }

    private void setupUI() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        Box.Filler filler = new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize);
        add(filler);

        nameTextFeld = new JTextField();
        lastNameTextFeld = new JTextField();
        usernameTextFeld = new JTextField();
        passwordTextFeld = new JTextField();
        add(new JLabel("Name:"));
        add(nameTextFeld);
        add(new JLabel("Last Name:"));
        add(lastNameTextFeld);
        add(new JLabel("Username:"));
        add(usernameTextFeld);
        add(new JLabel("Password:"));
        add(passwordTextFeld);

        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
        add(filler);

        registerButton = new JButton("Register");
        add(registerButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Rahmen definieren
        //setBorder(BorderFactory.createTitledBorder("Insert new Article"));
    }

    private void setupEvents() {
        registerButton.addActionListener(e -> RegisterNewEmployee());
    }

    private void RegisterNewEmployee() {
        String name = nameTextFeld.getText();
        String lastname = lastNameTextFeld.getText();
        String username = usernameTextFeld.getText();
        String password = passwordTextFeld.getText();

        if (name.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields", "Register Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            eshop.registerEmployee(name, lastname, username, password);
            JOptionPane.showMessageDialog(this, "Registration successful.", "Register new Employee", JOptionPane.INFORMATION_MESSAGE);

            nameTextFeld.setText("");
            lastNameTextFeld.setText("");
            usernameTextFeld.setText("");
            passwordTextFeld.setText("");

        } catch (RegisterException r) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Register Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}