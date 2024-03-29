package shop.local.ui.gui.models;

import shop.local.entities.Article;
import shop.local.entities.BulkArticle;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class E_ArticleTableModel extends AbstractTableModel  {

    private List<Article> articles;
    private String[] spaltenNamen = { "Number", "Title", "Price", "Quantity", "Pack size", "Status"};

    
    public E_ArticleTableModel(List<Article> aktuelleArticles) {
    	super(); 
    	// Ich erstelle eine Kopie der Artikelliste,
    	// damit beim Aktualisieren (siehe Methode setArticles())
    	// keine unerwarteten Seiteneffekte entstehen.
    	articles = new ArrayList<>();
        articles.addAll(aktuelleArticles);
    }

    public void setArticles(List<Article> currentArticle){
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
                return chosenArticle.getPrice();
            case 3:
                return chosenArticle.getQuantityInStock();
            case 4:
                if (chosenArticle instanceof BulkArticle) {
                    BulkArticle bulkArticle = (BulkArticle) chosenArticle;
                    return bulkArticle.getPackSize();
                } else {
                    return null;
                }
            case 5:
                return chosenArticle.getStatus();
            default:
                return null;
        }
    }
}
