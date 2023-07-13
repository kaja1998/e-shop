package shop.local.ui.gui.panels;

import shop.local.entities.Article;
import shop.local.ui.gui.models.E_ArticleTableModel;

import javax.swing.*;
import java.util.Collections;

/**
 * This class represents a panel that displays a table of articles for Employees.
 * It inherits from the JTable class and uses a specific TableModel to display and sort the articles.
 * The updateArticlesList method updates the displayed articles in the table and sorts them by article number.
 * @author Sund
 */

public class E_ArticlesTablePanel extends JTable {

	public E_ArticlesTablePanel(java.util.List<Article> articles) {
		super();

		// TableModel erzeugen ...
		E_ArticleTableModel tableModel = new E_ArticleTableModel(articles);
		// bei JTable "anmelden" und ...
		setModel(tableModel);
		// Daten an Model übergeben (für Sortierung o.ä.)
		updateArticlesList(articles);
	}
	
	public void updateArticlesList(java.util.List<Article> articles) {

		// Sortierung (mit Lambda-Expression)
//		Collections.sort(articles, (a1, a2) -> a1.getTitel().compareTo(a2.getTitel()));	// Sortierung nach Titel
		Collections.sort(articles, (a1, a2) -> a1.getNumber() - a2.getNumber());	// Sortierung nach Nummer

		// TableModel von JTable holen und ...
		E_ArticleTableModel tableModel = (E_ArticleTableModel) getModel();
		// Inhalt aktualisieren
		tableModel.setArticles(articles);
	}
}
