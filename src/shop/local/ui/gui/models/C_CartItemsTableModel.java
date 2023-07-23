package shop.local.ui.gui.models;

import shop.local.entities.ShoppingCartItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class C_CartItemsTableModel extends AbstractTableModel {

    private List<ShoppingCartItem> cartItems;
    private String[] spaltenNamen = { "Number", "Title", "Price", "Quantity"};


    public C_CartItemsTableModel(List<ShoppingCartItem> currentCartItems) {
        super();
        // Ich erstelle eine Kopie der CartItemsliste,
        // damit beim Aktualisieren (siehe Methode setArticles())
        // keine unerwarteten Seiteneffekte entstehen.
        cartItems = new ArrayList<>();
        cartItems.addAll(currentCartItems);
    }

    public void setCartItems(List<ShoppingCartItem> currentCartItems){
        cartItems.clear();
        cartItems.addAll(currentCartItems);
        fireTableDataChanged();
    }

    /*
     * Ab hier überschriebene Methoden mit Informationen,
     * die eine JTable von jedem TableModel erwartet:
     * - Anzahl der Zeilen
     * - Anzahl der Spalten
     * - Benennung der Spalten
     * - Wert einer Zelle in Zeile / Spalte
     */

    @Override
    public int getRowCount() {
        return cartItems.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        ShoppingCartItem chosenCartItem = cartItems.get(row);
        switch (col) {
            case 0:
                return chosenCartItem.getArticle().getNumber();
            case 1:
                return chosenCartItem.getArticle().getArticleTitle();
            case 2:
                return chosenCartItem.getArticle().getPrice();
            case 3:
                return chosenCartItem.getQuantity();
            default:
                return null;
        }
    }
}
