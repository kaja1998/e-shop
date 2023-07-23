package eshop.client.ui.gui.panels;

import eshop.client.ui.gui.models.C_ArticleTableModel;
import eshop.common.entities.Article;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class represents a panel that displays a table of articles for customers.
 * It inherits from the JTable class and uses a specific TableModel to display and sort the articles.
 * The updateArticlesList method updates the displayed articles in the table and sorts them by article number.
 * @author Sund
 */

public class C_ArticlesTablePanel extends JTable {

    private boolean sortByArticleNumber = true; // Variablen zur Steuerung der Sortierreihenfolge
    private boolean sortByArticleTitle = false;

    public C_ArticlesTablePanel(ArrayList<Article> articles) {
        super();

        // TableModel erzeugen ...
        C_ArticleTableModel tableModel = new C_ArticleTableModel(articles);
        // bei JTable "anmelden" und ...
        setModel(tableModel);
        // Daten an Model übergeben (für Sortierung o.ä.)
        updateArticlesList(articles);
    }

    public void updateArticlesList(ArrayList<Article> articles) {
        // Sortierung (kurz-Schreibweise)
        if (sortByArticleTitle) {
            Collections.sort(articles, Comparator.comparing(Article::getArticleTitle)); // Sortierung nach Artikelbezeichnung
        } else if (sortByArticleNumber) {
            Collections.sort(articles, Comparator.comparingInt(Article::getNumber)); // Sortierung nach Artikelnummer
        }

        // TableModel von JTable holen und ...
        C_ArticleTableModel tableModel = (C_ArticleTableModel) getModel();
        // Inhalt aktualisieren
        tableModel.setArticles(articles);
    }

    // Methode zum Ändern der Sortierreihenfolge
    public void toggleSortOrderByNumber() {
        sortByArticleNumber = true;
        sortByArticleTitle = false;
    }

    public void toggleSortOrderByTitle() {
        sortByArticleNumber = false;
        sortByArticleTitle = true;
    }
}
