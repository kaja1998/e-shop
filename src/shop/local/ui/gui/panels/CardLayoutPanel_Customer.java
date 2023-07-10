package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutPanel_Customer extends JPanel {

    private Shop eshop;
    private User user;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ChangeArticleQuantityInCartPanel_Customer.ChangeCartItemQuantityListener changeCartItemQuantityListener;
    private RemoveArticleFromCartPanel_Customer.RemoveCartItemFromCartListener removeCartItemFromCartListener;
    private JComboBox<String> viewComboBox;

    public CardLayoutPanel_Customer(Shop shop, User user, ChangeArticleQuantityInCartPanel_Customer.ChangeCartItemQuantityListener changeCartItemQuantityListener, RemoveArticleFromCartPanel_Customer.RemoveCartItemFromCartListener removeCartItemFromCartListener)  {
        this.eshop = shop;
        this.user = user;
        this.changeCartItemQuantityListener = changeCartItemQuantityListener;
        this.removeCartItemFromCartListener = removeCartItemFromCartListener;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        viewComboBox = new JComboBox<>();
        setupUI();
    }

    private void setupUI() {

        // Ansicht "Change CartItem Quantity"
        ChangeArticleQuantityInCartPanel_Customer changeArticleQuantityPanel = new ChangeArticleQuantityInCartPanel_Customer(eshop, user, changeCartItemQuantityListener);
        cardPanel.add(changeArticleQuantityPanel, "change");
        cardLayout.addLayoutComponent(changeArticleQuantityPanel, "change");

        // Ansicht "Remove Article from Cart"
        RemoveArticleFromCartPanel_Customer removeArticleFromCartPanel = new RemoveArticleFromCartPanel_Customer(eshop, user, removeCartItemFromCartListener);
        cardPanel.add(removeArticleFromCartPanel, "remove");
        cardLayout.addLayoutComponent(removeArticleFromCartPanel, "remove");

        // Hinzufügen der Ansichten zum Karten-Panel
        cardPanel.add(changeArticleQuantityPanel, "change");
        cardPanel.add(removeArticleFromCartPanel, "remove");

        // Hinzufügen der Ansichten zum CardLayout
        cardLayout.addLayoutComponent(changeArticleQuantityPanel, "change");
        cardLayout.addLayoutComponent(removeArticleFromCartPanel, "remove");

        // Ansichten zur JComboBox hinzufügen
        viewComboBox.addItem("Change Quantity of Article");
        viewComboBox.addItem("Remove Article from Cart");

        // Standardansicht auswählen
        viewComboBox.setSelectedIndex(0);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Select action"));

        // ActionListener für die JComboBox, um die Ansicht zu wechseln
        viewComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedView = (String) viewComboBox.getSelectedItem();
                cardLayout.show(cardPanel, selectedView.toLowerCase());

                if (selectedView.equals("Change Quantity of Article")) {
                    cardLayout.show(cardPanel, "change");
                }
                if (selectedView.equals("Remove Article from Cart")) {
                    cardLayout.show(cardPanel, "remove");
                }
            }
        });

        // Layout des AddArticlePanel_Employee setzen
        viewComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Layout des AddArticlePanel_Employee setzen
        setLayout(new BorderLayout());
        add(viewComboBox, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }
}
