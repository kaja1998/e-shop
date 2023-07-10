package shop.local.ui.gui.panels;

import shop.local.entities.Article;
import shop.local.ui.gui.models.ArticleTableModel_Customer;

import javax.swing.*;
import java.util.Collections;

public class ArticlesTablePanel_Customer  extends JTable {

    public ArticlesTablePanel_Customer(java.util.List<Article> articles) {
        super();

        // TableModel erzeugen ...
        ArticleTableModel_Customer tableModel = new ArticleTableModel_Customer(articles);
        // ... bei JTable "anmelden" und ...
        setModel(tableModel);
        // ... Daten an Model übergeben (für Sortierung o.ä.)
        updateArticlesList(articles);
    }

    public void updateArticlesList(java.util.List<Article> articles) {

        // Sortierung (mit Lambda-Expression)
//		Collections.sort(articles, (a1, a2) -> a1.getTitel().compareTo(a2.getTitel()));	// Sortierung nach Titel
        Collections.sort(articles, (a1, a2) -> a1.getNumber() - a2.getNumber());	// Sortierung nach Nummer

        // TableModel von JTable holen und ...
        ArticleTableModel_Customer tableModel = (ArticleTableModel_Customer) getModel();
        // ... Inhalt aktualisieren
        tableModel.setArticles(articles);
    }
}
