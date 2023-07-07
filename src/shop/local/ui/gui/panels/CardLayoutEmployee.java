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
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JComboBox<String> viewComboBox;

    public CardLayoutEmployee(Shop shop, AddArticlePanel.AddArticleListener addArticleListener, DeleteArticlePanel.DeleteArticleListener deleteArticleListener, User user)  {
        eshop = shop;
        this.addArticleListener = addArticleListener;
        this.deleteArticleListener = deleteArticleListener;
        this.user = user;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        viewComboBox = new JComboBox<>();
        setupUI();
    }

    private void setupUI() {
//        // Ansicht "Delete Article"
//        JPanel deletePanel = new JPanel();
//        deletePanel.add(new JLabel("Delete Article"));
//        JTextField deleteTextField = new JTextField();
//        JButton deleteButton = new JButton("Delete");
//        deletePanel.add(deleteTextField);
//        deletePanel.add(deleteButton);

        DeleteArticlePanel deleteArticlePanel = new DeleteArticlePanel(eshop, deleteArticleListener, user);
        cardPanel.add(deleteArticlePanel, "delete");
        cardLayout.addLayoutComponent(deleteArticlePanel, "delete");

        // Ansicht "Manage Stock"
        JPanel stockPanel = new JPanel();
        stockPanel.add(new JLabel("Manage Stock"));
        JTextField stockTextField1 = new JTextField();
        JTextField stockTextField2 = new JTextField();
        JButton stockButton = new JButton("Manage");
        stockPanel.add(stockTextField1);
        stockPanel.add(stockTextField2);
        stockPanel.add(stockButton);

        // Ansicht "Add Article"
        AddArticlePanel addArticlePanel = new AddArticlePanel(eshop, addArticleListener, user);
        cardPanel.add(addArticlePanel, "add");
        cardLayout.addLayoutComponent(addArticlePanel, "add");

        // Hinzufügen der Ansichten zum Karten-Panel
        cardPanel.add(deleteArticlePanel, "delete");
        cardPanel.add(stockPanel, "stock");
        cardPanel.add(addArticlePanel, "add");

        // Hinzufügen der Ansichten zum CardLayout
        cardLayout.addLayoutComponent(deleteArticlePanel, "delete");
        cardLayout.addLayoutComponent(stockPanel, "stock");
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
                    cardLayout.show(cardPanel, "stock");
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
