package shop.local.ui.gui.panels;

import shop.local.entities.Article;
import shop.local.ui.gui.models.C_ArticleTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a panel that displays a table of articles for customers.
 * It inherits from the JTable class and uses a specific TableModel to display and sort the articles.
 * The updateArticlesList method updates the displayed articles in the table and sorts them by article number.
 * @author Sund
 */

public class C_ArticlesTablePanel extends JTable {

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

        // Sortierung (mit Lambda-Expression)
//		Collections.sort(articles, (a1, a2) -> a1.getTitel().compareTo(a2.getTitel()));	// Sortierung nach Titel
        Collections.sort(articles, (a1, a2) -> a1.getNumber() - a2.getNumber());	// Sortierung nach Nummer

        // TableModel von JTable holen und ...
        C_ArticleTableModel tableModel = (C_ArticleTableModel) getModel();
        // Inhalt aktualisieren
        tableModel.setArticles(articles);
    }
}
