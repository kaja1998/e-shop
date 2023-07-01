package shop.local.ui.gui;

import shop.local.domain.Shop;
import shop.local.ui.gui.panels.LoginEmployeeButtonPanel;

import javax.swing.*;
import java.awt.*;

public class EmployeeLogin extends JFrame {

    private JFrame EmployeeLoginFrame;
    private Shop eshop;

    private LoginEmployeeButtonPanel loginEmployeeButtonPanel;

    public EmployeeLogin(Shop shop) {
        super("Employee Login");
        eshop = shop;
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowCloser());

        setLayout(new BorderLayout());

        loginEmployeeButtonPanel = new LoginEmployeeButtonPanel(this, eshop);
        add(loginEmployeeButtonPanel, BorderLayout.CENTER);

        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
