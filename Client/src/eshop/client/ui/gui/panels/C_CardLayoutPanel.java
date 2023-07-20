package eshop.client.ui.gui.panels;

import eshop.common.entities.User;
import eshop.common.interfaces.ShopInterface;
import eshop.server.domain.Shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents a panel with a map view for customers.
 * It allows the customer to toggle between different views such as "Change the quantity of items in the cart" and "Remove an item from the cart".
 * The panel contains a JComboBox for selecting the view and uses a CardLayout to display the appropriate views.
 * @author Sund
 */

public class C_CardLayoutPanel extends JPanel {

    private ShopInterface eshop;
    private User user;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private C_ChangeArticleQuantityInCartPanel.ChangeCartItemQuantityListener changeCartItemQuantityListener;
    private C_RemoveArticleFromCartPanel.RemoveCartItemFromCartListener removeCartItemFromCartListener;
    private JComboBox<String> viewComboBox;

    public C_CardLayoutPanel(ShopInterface shop, User user, C_ChangeArticleQuantityInCartPanel.ChangeCartItemQuantityListener changeCartItemQuantityListener, C_RemoveArticleFromCartPanel.RemoveCartItemFromCartListener removeCartItemFromCartListener)  {
        this.eshop = shop;
        this.user = user;
        this.changeCartItemQuantityListener = changeCartItemQuantityListener;
        this.removeCartItemFromCartListener = removeCartItemFromCartListener;
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        viewComboBox = new JComboBox<>();
//        setupUI();
    }
//
//    private void setupUI() {
//
//        // Ansicht "Change CartItem Quantity"
//        C_ChangeArticleQuantityInCartPanel changeArticleQuantityPanel = new C_ChangeArticleQuantityInCartPanel(eshop, user, changeCartItemQuantityListener);
//        cardPanel.add(changeArticleQuantityPanel, "change");
//        cardLayout.addLayoutComponent(changeArticleQuantityPanel, "change");
//
//        // Ansicht "Remove Article from Cart"
//        C_RemoveArticleFromCartPanel removeArticleFromCartPanel = new C_RemoveArticleFromCartPanel(eshop, user, removeCartItemFromCartListener);
//        cardPanel.add(removeArticleFromCartPanel, "remove");
//        cardLayout.addLayoutComponent(removeArticleFromCartPanel, "remove");
//
//        // Hinzufügen der Ansichten zum Karten-Panel
//        cardPanel.add(changeArticleQuantityPanel, "change");
//        cardPanel.add(removeArticleFromCartPanel, "remove");
//
//        // Hinzufügen der Ansichten zum CardLayout
//        cardLayout.addLayoutComponent(changeArticleQuantityPanel, "change");
//        cardLayout.addLayoutComponent(removeArticleFromCartPanel, "remove");
//
//        // Ansichten zur JComboBox hinzufügen
//        viewComboBox.addItem("Change Quantity of Article");
//        viewComboBox.addItem("Remove Article from Cart");
//
//        // Standardansicht auswählen
//        viewComboBox.setSelectedIndex(0);
//
//        // Rahmen definieren
//        setBorder(BorderFactory.createTitledBorder("Select action"));
//
//        // ActionListener für die JComboBox, um die Ansicht zu wechseln
//        viewComboBox.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String selectedView = (String) viewComboBox.getSelectedItem();
//                cardLayout.show(cardPanel, selectedView.toLowerCase());
//
//                if (selectedView.equals("Change Quantity of Article")) {
//                    cardLayout.show(cardPanel, "change");
//                }
//                if (selectedView.equals("Remove Article from Cart")) {
//                    cardLayout.show(cardPanel, "remove");
//                }
//            }
//        });
//
//        // Border für die ComboBox
//        viewComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//        // Panel Layout wird auf ein BorderLayout gesetzt und fügen die JComboBox am oberen Rand (Norden) und das cardPanel in der Mitte des Panels (Zentrum) ein
//        setLayout(new BorderLayout());
//        add(viewComboBox, BorderLayout.NORTH);
//        add(cardPanel, BorderLayout.CENTER);
//    }
}
