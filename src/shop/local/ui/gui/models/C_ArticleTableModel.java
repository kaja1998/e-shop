package shop.local.ui.gui.models;

import shop.local.entities.Article;
import shop.local.entities.BulkArticle;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class C_ArticleTableModel extends AbstractTableModel {

    private ArrayList<Article> articles;
    private String[] spaltenNamen = { "Number", "Title", "Price", "Pack size"};


    public C_ArticleTableModel(ArrayList<Article> aktuelleArticles) {
        super();
        // Ich erstelle eine Kopie der Artikelliste,
        // damit beim Aktualisieren (siehe Methode setArticles())
        // keine unerwarteten Seiteneffekte entstehen.
        articles = new ArrayList<>();
        articles.addAll(aktuelleArticles);
    }

    public void setArticles(ArrayList<Article> currentArticle){
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
                if (chosenArticle instanceof BulkArticle) {
                    BulkArticle bulkArticle = (BulkArticle) chosenArticle;
                    return bulkArticle.getPackSize();
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
