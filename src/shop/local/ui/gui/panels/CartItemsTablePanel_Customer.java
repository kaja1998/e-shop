package shop.local.ui.gui.panels;

import shop.local.entities.ShoppingCartItem;
import shop.local.ui.gui.models.CartItemsTableModel_Customer;

import javax.swing.*;
import java.util.Collections;

/**
 * This class represents a panel that displays a table of cart items for customers.
 * It inherits from the JTable class and uses a specific TableModel to display and sort the cart items.
 * The "updateCartItemsList" method updates the displayed cart items in the table and sorts them by article number.
 * @author Sund
 */

public class CartItemsTablePanel_Customer extends JTable {

    public CartItemsTablePanel_Customer(java.util.List<ShoppingCartItem> cartItems) {
        super();

        // TableModel erzeugen ...
        CartItemsTableModel_Customer tableModel = new CartItemsTableModel_Customer(cartItems);
        // bei JTable "anmelden" und ...
        setModel(tableModel);
        // Daten an Model übergeben (für Sortierung o.ä.)
        updateCartItemsList(cartItems);
    }

    public void updateCartItemsList(java.util.List<ShoppingCartItem> cartItems) {

        // Sortierung nach Nummer
        Collections.sort(cartItems, (a1, a2) -> a1.getArticle().getNumber() - a2.getArticle().getNumber());

        // TableModel von JTable holen und ...
        CartItemsTableModel_Customer tableModel = (CartItemsTableModel_Customer) getModel();
        // Inhalt aktualisieren
        tableModel.setCartItems(cartItems);
    }
}
