package shop.local.ui.gui.panels;

import shop.local.entities.ShoppingCartItem;
import shop.local.ui.gui.models.CartItemsTableModel_Customer;

import javax.swing.*;
import java.util.Collections;

public class CartItemsTablePanel_Customer extends JTable {

    public CartItemsTablePanel_Customer(java.util.List<ShoppingCartItem> cartItems) {
        super();

        // TableModel erzeugen ...
        CartItemsTableModel_Customer tableModel = new CartItemsTableModel_Customer(cartItems);
        // ... bei JTable "anmelden" und ...
        setModel(tableModel);
        // ... Daten an Model übergeben (für Sortierung o.ä.)
        updateCartItemsList(cartItems);
    }

    public void updateCartItemsList(java.util.List<ShoppingCartItem> cartItems) {

        // Sortierung (mit Lambda-Expression)
//		Collections.sort(articles, (a1, a2) -> a1.getTitel().compareTo(a2.getTitel()));	// Sortierung nach Titel
        Collections.sort(cartItems, (a1, a2) -> a1.getArticle().getNumber() - a2.getArticle().getNumber());	// Sortierung nach Nummer

        // TableModel von JTable holen und ...
        CartItemsTableModel_Customer tableModel = (CartItemsTableModel_Customer) getModel();
        // ... Inhalt aktualisieren
        tableModel.setCartItems(cartItems);
    }
}
