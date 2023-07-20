package eshop.client.ui.gui.panels;

import eshop.client.ui.gui.models.C_CartItemsTableModel;
import eshop.common.entities.ShoppingCartItem;

import javax.swing.*;
import java.util.Collections;

/**
 * This class represents a panel that displays a table of cart items for customers.
 * It inherits from the JTable class and uses a specific TableModel to display and sort the cart items.
 * The "updateCartItemsList" method updates the displayed cart items in the table and sorts them by article number.
 * @author Sund
 */

public class C_CartItemsTablePanel extends JTable {

    public C_CartItemsTablePanel(java.util.List<ShoppingCartItem> cartItems) {
        super();

        // TableModel erzeugen ...
        C_CartItemsTableModel tableModel = new C_CartItemsTableModel(cartItems);
        // bei JTable "anmelden" und ...
        setModel(tableModel);
        // Daten an Model übergeben (für Sortierung o.ä.)
        updateCartItemsList(cartItems);
    }

    public void updateCartItemsList(java.util.List<ShoppingCartItem> cartItems) {

        // Sortierung nach Nummer
        Collections.sort(cartItems, (a1, a2) -> a1.getArticle().getNumber() - a2.getArticle().getNumber());

        // TableModel von JTable holen und ...
        C_CartItemsTableModel tableModel = (C_CartItemsTableModel) getModel();
        // Inhalt aktualisieren
        tableModel.setCartItems(cartItems);
    }
}
