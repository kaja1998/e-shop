package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.ui.gui.EmployeeLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeLoginButtonPanel extends JPanel{

    private JFrame loginStartFrame;
    private Shop eshop;
    private JButton EmployeeLoginButtonPanel;

    public EmployeeLoginButtonPanel(JFrame loginStartFrame, Shop shop) {
        this.loginStartFrame = loginStartFrame;
        eshop = shop;
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        EmployeeLoginButtonPanel = new JButton("Employee Login");
//      EmployeeLoginButtonPanel.setFont(new Font("Arial", Font.BOLD, 12));
//      EmployeeLoginButtonPanel.setPreferredSize(new Dimension(150, 40));
//      EmployeeLoginButtonPanel.setBackground(Color.WHITE);
//      EmployeeLoginButtonPanel.setForeground(Color.BLACK);
//      EmployeeLoginButtonPanel.setBorder(new LineBorder(Color.BLUE, 1));
//      Icon icon = new ImageIcon("path/to/icon.png");
//       EmployeeLoginButtonPanel.setIcon(icon);
        add(EmployeeLoginButtonPanel);
    }

    private void setupEvents() {
        EmployeeLoginButtonPanel.addActionListener(new EmployeeLoginButtonPanel.EmployeeLoginListener());
    }

    class EmployeeLoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Öffne Employee-Login-Dialog
            EmployeeLogin employeeLogin = new EmployeeLogin(eshop);
            employeeLogin.setVisible(true);
            loginStartFrame.dispose();
        }
    }
}
