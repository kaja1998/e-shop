package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutEmployee extends JPanel{

    private Shop eshop;
    private  User user;
    private AddArticlePanel.AddArticleListener addArticleListener;

    private DeleteArticlePanel.DeleteArticleListener deleteArticleListener;

    private ManageArticleStockPanel.ManageArticleListener manageArticleListener;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JComboBox<String> viewComboBox;

    public CardLayoutEmployee(Shop shop, AddArticlePanel.AddArticleListener addArticleListener, DeleteArticlePanel.DeleteArticleListener deleteArticleListener, ManageArticleStockPanel.ManageArticleListener manageArticleListener, User user)  {
        eshop = shop;
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
        DeleteArticlePanel deleteArticlePanel = new DeleteArticlePanel(eshop, deleteArticleListener, user);
        cardPanel.add(deleteArticlePanel, "delete");
        cardLayout.addLayoutComponent(deleteArticlePanel, "delete");

        // Ansicht "Manage Stock of an Article"
        ManageArticleStockPanel manageArticleStockPanel = new ManageArticleStockPanel(eshop, manageArticleListener, user);
        cardPanel.add(manageArticleStockPanel, "manage");
        cardLayout.addLayoutComponent(manageArticleStockPanel, "manage");

        // Ansicht "Add Article"
        AddArticlePanel addArticlePanel = new AddArticlePanel(eshop, addArticleListener, user);
        cardPanel.add(addArticlePanel, "add");
        cardLayout.addLayoutComponent(addArticlePanel, "add");

        // Hinzufügen der Ansichten zum Karten-Panel
        cardPanel.add(deleteArticlePanel, "delete");
        cardPanel.add(manageArticleStockPanel, "manage");
        cardPanel.add(addArticlePanel, "add");

        // Hinzufügen der Ansichten zum CardLayout
        cardLayout.addLayoutComponent(deleteArticlePanel, "delete");
        cardLayout.addLayoutComponent(manageArticleStockPanel, "manage");
        cardLayout.addLayoutComponent(addArticlePanel, "add");

        // Ansichten zur JComboBox hinzufügen
        viewComboBox.addItem("Delete Article");
        viewComboBox.addItem("Manage Stock");
        viewComboBox.addItem("Add Article");

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
            }
        });

        // Layout des AddArticlePanel setzen
        viewComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Layout des AddArticlePanel setzen
        setLayout(new BorderLayout());
        add(viewComboBox, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }
}
