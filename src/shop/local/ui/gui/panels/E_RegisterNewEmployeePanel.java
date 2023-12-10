package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.RegisterException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Panel in which a new Employee can be registered by another employee
 * Creates the necessary text boxes, buttons, and the ActionListener when the button "registerButton" is clicked
 * @author Sund
 */

public class E_RegisterNewEmployeePanel extends JPanel {

    private Shop eshop;
    private JTextField nameTextField = null;
    private JTextField lastNameTextField = null;
    private JTextField usernameTextField = null;
    private JTextField passwordTextField = null;

    private JButton registerButton;

    public E_RegisterNewEmployeePanel(Shop shop) {
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

        nameTextField = new JTextField();
        lastNameTextField = new JTextField();
        usernameTextField = new JTextField();
        passwordTextField = new JTextField();
        add(new JLabel("Name:"));
        add(nameTextField);
        add(new JLabel("Last Name:"));
        add(lastNameTextField);
        add(new JLabel("Username:"));
        add(usernameTextField);
        add(new JLabel("Password:"));
        add(passwordTextField);

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
        String name = nameTextField.getText();
        String lastname = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (name.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.", "Register Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            eshop.registerEmployee(name, lastname, username, password);
            JOptionPane.showMessageDialog(this, "Registration successful.", "Register new Employee", JOptionPane.INFORMATION_MESSAGE);

            nameTextField.setText("");
            lastNameTextField.setText("");
            usernameTextField.setText("");
            passwordTextField.setText("");

        } catch (RegisterException r) {
            JOptionPane.showMessageDialog(this, r.getMessage(), "Register Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Something went wrong.", "Register Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}