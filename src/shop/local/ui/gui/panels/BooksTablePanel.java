package shop.local.ui.gui.panels;

//import bib.local.entities.Buch;
//import bib.local.ui.gui.models.BuecherTableModel;

import shop.local.entities.Article;
import shop.local.ui.gui.models.BuecherTableModel;

import javax.swing.*;
import java.util.Collections;

public class BooksTablePanel extends JTable {

	public BooksTablePanel(java.util.List<Article> articles) {
		super();

		// TableModel erzeugen ...
		BuecherTableModel tableModel = new BuecherTableModel(articles);
		// ... bei JTable "anmelden" und ...
		setModel(tableModel);
		// ... Daten an Model übergeben (für Sortierung o.ä.)
		updateBooksList(articles);
	}
	
	public void updateBooksList(java.util.List<Article> articles) {

		// Sortierung (mit Lambda-Expression)
//		Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
		Collections.sort(articles, (a1, a2) -> a1.getNumber() - a2.getNumber());	// Sortierung nach Nummer

		// TableModel von JTable holen und ...
		BuecherTableModel tableModel = (BuecherTableModel) getModel();
		// ... Inhalt aktualisieren
		tableModel.setBooks(articles);
	}
}
