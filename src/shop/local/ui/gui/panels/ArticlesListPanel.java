package shop.local.ui.gui.panels;

import shop.local.entities.Article;

import javax.swing.*;
import java.util.Collections;
import java.util.Comparator;

// Wichtig: Das ArticlesListPanel _ist eine_ JList und damit eine Component;
// es kann daher in das Layout eines anderen Containers
// (in unserer Anwendung des Frames) eingefügt werden.
public class ArticlesListPanel extends JList<Article> {

	public ArticlesListPanel(java.util.List<Article> articles) {
		super();

		// ListModel erzeugen ...
		DefaultListModel<Article> listModel = new DefaultListModel<>();
		// ... bei JList "anmelden" und ...
		setModel(listModel);
		// ... Daten an Model übergeben
		updateArticleList(articles);
	}
	
	public void updateArticleList(java.util.List<Article> articles) {

		// Sortierung (mit Lambda-Expression)
		//Collections.sort(articles, (a1, a2) -> a1.getTitel().compareTo(a2.getTitel()));	// Sortierung nach Titel
		Collections.sort(articles, (a1, a2) -> a1.getNumber() - a2.getNumber());	// Sortierung nach Nummer
		//keine Ahnung, ob richtig aber er meinte damit es klappt muss das so
		Collections.sort(articles, Comparator.comparingInt(Article::getNumber));

		// ListModel von JList holen und ...
		DefaultListModel<Article> listModel = (DefaultListModel<Article>) getModel();
		// ... Inhalt aktualisieren
		listModel.clear();
		listModel.addAll(articles);
	}
}
