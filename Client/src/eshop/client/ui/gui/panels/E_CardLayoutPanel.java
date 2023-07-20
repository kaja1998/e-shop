package eshop.client.ui.gui.panels;

import eshop.common.entities.User;
import eshop.common.interfaces.ShopInterface;

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

public class E_CardLayoutPanel extends JPanel{

    private ShopInterface eshop;
    private User user;
    private E_AddArticlePanel.AddArticleListener addArticleListener;

    private E_DeleteArticlePanel.DeleteArticleListener deleteArticleListener;

    private E_ManageArticleStockPanel.ManageArticleListener manageArticleListener;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JComboBox<String> viewComboBox;

    public E_CardLayoutPanel(ShopInterface shop, E_AddArticlePanel.AddArticleListener addArticleListener, E_DeleteArticlePanel.DeleteArticleListener deleteArticleListener, E_ManageArticleStockPanel.ManageArticleListener manageArticleListener, User user)  {
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
        E_DeleteArticlePanel deleteArticlePanelEmployee = new E_DeleteArticlePanel(eshop, deleteArticleListener, user);
        cardPanel.add(deleteArticlePanelEmployee, "delete");
        cardLayout.addLayoutComponent(deleteArticlePanelEmployee, "delete");

        // Ansicht "Manage Stock of an Article"
        E_ManageArticleStockPanel manageArticleStockPanelEmployee = new E_ManageArticleStockPanel(eshop, manageArticleListener, user);
        cardPanel.add(manageArticleStockPanelEmployee, "manage");
        cardLayout.addLayoutComponent(manageArticleStockPanelEmployee, "manage");

        // Ansicht "Add Article"
        E_AddArticlePanel addArticlePanelEmployee = new E_AddArticlePanel(eshop, addArticleListener, user);
        cardPanel.add(addArticlePanelEmployee, "add");
        cardLayout.addLayoutComponent(addArticlePanelEmployee, "add");

        // Ansicht "Register new Employee"
        E_RegisterNewEmployeePanel registerNewEmployeePanelEmployee = new E_RegisterNewEmployeePanel(eshop);
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
