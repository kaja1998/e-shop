package shop.local.ui.gui.models;

//import bib.local.entities.Buch;
import shop.local.entities.Article;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class BuecherTableModel extends AbstractTableModel  {

    private List<Article> articles;
    private String[] spaltenNamen = { "Nummer","Titel", "verfügbar" };

    
    public BuecherTableModel(List<Article> aktuelleBuecher) {
    	super(); 
    	// Ich erstelle eine Kopie der Bücherliste,
    	// damit beim Aktualisieren (siehe Methode setBooks())
    	// keine unerwarteten Seiteneffekte entstehen.
    	articles = new Vector<Article>();
        articles.addAll(aktuelleBuecher);
    }

    public void setBooks(List<Article> currentArticle){
        articles.clear();
        articles.addAll(currentArticle);
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
        return articles.size();
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
        Article chosenArticle = articles.get(row);
        switch (col) {
            case 0:
                return chosenArticle.getNumber();
            case 1:
                return chosenArticle.getArticleTitle();
            case 2:
                return chosenArticle.getQuantityInStock();
                //isVerfuegbar();
            default:
                return null;
        }
    }
}
