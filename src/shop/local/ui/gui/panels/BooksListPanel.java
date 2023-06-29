package shop.local.ui.gui.panels;

//import bib.local.entities.Buch;
import shop.local.entities.Article;

import javax.swing.*;
import java.util.Collections;
import java.util.Comparator;

// Wichtig: Das BooksListPanel _ist eine_ JList und damit eine Component;
// es kann daher in das Layout eines anderen Containers
// (in unserer Anwendung des Frames) eingefügt werden.
public class BooksListPanel extends JList<Article> {

	public BooksListPanel(java.util.List<Article> buecher) {
		super();

		// ListModel erzeugen ...
		DefaultListModel<Article> listModel = new DefaultListModel<>();
		// ... bei JList "anmelden" und ...
		setModel(listModel);
		// ... Daten an Model übergeben
		updateBooksList(buecher);
	}
	
	public void updateBooksList(java.util.List<Article> articles) {

		// Sortierung (mit Lambda-Expression)
		//Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
		Collections.sort(articles, (b1, b2) -> b1.getNumber() - b2.getNumber());	// Sortierung nach Nummer
		//keine Ahnung, ob richtig aber er meinte damit es klappt muss das so
		Collections.sort(articles, Comparator.comparingInt(Article::getNumber));

		// ListModel von JList holen und ...
		DefaultListModel<Article> listModel = (DefaultListModel<Article>) getModel();
		// ... Inhalt aktualisieren
		listModel.clear();
		listModel.addAll(articles);
	}
}
