package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a panel with a map view for employees.
 * It allows the employee to switch between different views such as "Delete item", "Inventory management of an item", "Add item" and "Register new employee".
 * The panel contains a JComboBox for selecting the view and uses a CardLayout to display the appropriate views.
 * @author Sund
 */

public class CardLayoutPanel_Employee extends JPanel{

    private Shop eshop;
    private  User user;
    private AddArticlePanel_Employee.AddArticleListener addArticleListener;

    private DeleteArticlePanel_Employee.DeleteArticleListener deleteArticleListener;

    private ManageArticleStockPanel_Employee.ManageArticleListener manageArticleListener;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JComboBox<String> viewComboBox;

    public CardLayoutPanel_Employee(Shop shop, AddArticlePanel_Employee.AddArticleListener addArticleListener, DeleteArticlePanel_Employee.DeleteArticleListener deleteArticleListener, ManageArticleStockPanel_Employee.ManageArticleListener manageArticleListener, User user)  {
        this.eshop = shop;
        this.addArticleListener = addArticleListener;
        this.deleteArticleListener = deleteArticleListener;
        this.manageArticleListener = manageArticleListener;
        this.user = user;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        viewComboBox = new JComboBox<>();
        setupUI();
    }

    private void setupUI() {

        // Ansicht "Delete Article"
        DeleteArticlePanel_Employee deleteArticlePanelEmployee = new DeleteArticlePanel_Employee(eshop, deleteArticleListener, user);
        cardPanel.add(deleteArticlePanelEmployee, "delete");
        cardLayout.addLayoutComponent(deleteArticlePanelEmployee, "delete");

        // Ansicht "Manage Stock of an Article"
        ManageArticleStockPanel_Employee manageArticleStockPanelEmployee = new ManageArticleStockPanel_Employee(eshop, manageArticleListener, user);
        cardPanel.add(manageArticleStockPanelEmployee, "manage");
        cardLayout.addLayoutComponent(manageArticleStockPanelEmployee, "manage");

        // Ansicht "Add Article"
        AddArticlePanel_Employee addArticlePanelEmployee = new AddArticlePanel_Employee(eshop, addArticleListener, user);
        cardPanel.add(addArticlePanelEmployee, "add");
        cardLayout.addLayoutComponent(addArticlePanelEmployee, "add");

        // Ansicht "Register new Employee"
        RegisterNewEmployeePanel_Employee registerNewEmployeePanelEmployee = new RegisterNewEmployeePanel_Employee(eshop);
        cardPanel.add(registerNewEmployeePanelEmployee, "new");
        cardLayout.addLayoutComponent(registerNewEmployeePanelEmployee, "new");

        // Hinzufügen der Ansichten zum Karten-Panel
        cardPanel.add(deleteArticlePanelEmployee, "delete");
        cardPanel.add(manageArticleStockPanelEmployee, "manage");
        cardPanel.add(addArticlePanelEmployee, "add");
        cardPanel.add(registerNewEmployeePanelEmployee, "new");

        // Hinzufügen der Ansichten zum CardLayout
        cardLayout.addLayoutComponent(deleteArticlePanelEmployee, "delete");
        cardLayout.addLayoutComponent(manageArticleStockPanelEmployee, "manage");
        cardLayout.addLayoutComponent(addArticlePanelEmployee, "add");
        cardLayout.addLayoutComponent(registerNewEmployeePanelEmployee, "new");

        // Ansichten zur JComboBox hinzufügen
        viewComboBox.addItem("Delete Article");
        viewComboBox.addItem("Manage Stock");
        viewComboBox.addItem("Add Article");
        viewComboBox.addItem("Register new Employee");

        // Standardansicht auswählen
        viewComboBox.setSelectedIndex(0);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Select action"));

        // ActionListener für die JComboBox, um die Ansicht zu wechseln
        viewComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedView = (String) viewComboBox.getSelectedItem();
                cardLayout.show(cardPanel, selectedView.toLowerCase());

                if (selectedView.equals("Delete Article")) {
                    cardLayout.show(cardPanel, "delete");
                }
                if (selectedView.equals("Manage Stock")) {
                    cardLayout.show(cardPanel, "manage");
                }
                if (selectedView.equals("Add Article")) {
                    cardLayout.show(cardPanel, "add");
                }
                if (selectedView.equals("Register new Employee")) {
                    cardLayout.show(cardPanel, "new");
                }
            }
        });

        // Border für die ComboBox
        viewComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Panel Layout wird auf ein BorderLayout gesetzt und fügen die JComboBox am oberen Rand (Norden) und das cardPanel in der Mitte des Panels (Zentrum) ein
        setLayout(new BorderLayout());
        add(viewComboBox, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }
}
