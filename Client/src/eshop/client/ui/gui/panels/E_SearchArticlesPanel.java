package eshop.client.ui.gui.panels;

import eshop.common.entities.Article;
import eshop.common.exceptions.ArticleNotFoundException;
import eshop.common.interfaces.ShopInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel in which you can search for an item from the shop
 * Creates the necessary text boxes, buttons, and the ActionListener when the button "Enter" is clicked
 * @author Sund
 */

public class E_SearchArticlesPanel extends JPanel {

	// Über dieses Interface übermittelt das E_SearchArticlesPanel
	// Suchergebnisse an einen Empfänger. Hier das E_EmployeeFrontEnd,
	// welches dieses Interface implementiert und auf ein neues
	// Suchergebnis reagiert, indem es die Articleliste aktualisiert.
	public interface SearchResultListener {
		void onSearchResult(ArrayList<Article> articles);
	}

	private ShopInterface eshop;
	private JTextField searchTextField;
	private JButton searchButton = null;
	private SearchResultListener searchResultListener;

	public E_SearchArticlesPanel(ShopInterface shop, SearchResultListener searchResultListener) {
		eshop = shop;
		this.searchResultListener = searchResultListener;
		setupUI();
//		setupEvents();
	}

	private void setupUI() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;	// Zeile 0

		JLabel searchLabel = new JLabel("Search term:");
		c.gridx = 0;    // Spalte 0
		c.weightx = 0.05;    // 20% der gesamten Breite
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 0, 0, 0); // Hier wird der Abstand festgelegt (10 Pixel rechts)
		gridBagLayout.setConstraints(searchLabel, c);
		this.add(searchLabel);

		searchTextField = new JTextField();
		searchTextField.setToolTipText("Enter search term here.");
		c.gridx = 1;	// Spalte 1
		c.weightx = 0.75;	// 60% der gesamten Breite
		c.insets = new Insets(0, 0, 0, 15); // Hier wird der Abstand festgelegt (10 Pixel links und rechts)
		gridBagLayout.setConstraints(searchTextField, c);
		this.add(searchTextField);

		searchButton = new JButton("Enter");
		c.gridx = 2;	// Spalte 2
		c.weightx = 0.2;	// 20% der gesamten Breite
		c.insets = new Insets(0, 0, 0, 10); // Hier wird der Abstand festgelegt (10 Pixel rechts)
		gridBagLayout.setConstraints(searchButton, c);
		this.add(searchButton);

		// Rahmen definieren
		setBorder(BorderFactory.createTitledBorder("Search"));
	}

//	private void setupEvents() {
//		searchButton.addActionListener(e -> SuchListener());
//	}
//
//	// Lokale Klasse für Reaktion auf Such-Klick
//	private void SuchListener() {
//		// Suchbegriff aus dem Textfeld lesen
//		String suchbegriff = searchTextField.getText();
//
//		// Liste für das Suchergebnis deklarieren
//		List<Article> suchErgebnis;
//
//		// Überprüfen, ob das Suchfeld leer ist
//		if (suchbegriff.isEmpty()) {
//			// Wenn das Suchfeld leer ist, alle Artikel abrufen
//			suchErgebnis = eshop.getAllArticles();
//		} else {
//			// Wenn ein Suchbegriff eingegeben wurde
//			try {
//				// Versuche, Artikel basierend auf dem Titel zu suchen
//				suchErgebnis = eshop.searchByArticleTitle(suchbegriff);
//			} catch (ArticleNotFoundException e) {
//				// Wenn ein Artikel nicht gefunden wird, zeige eine Fehlermeldung an
//				JOptionPane.showMessageDialog(this, e.getMessage(), "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//		}
//
//		// Das Suchergebnis an den searchResultListener übergeben
//		searchResultListener.onSearchResult(suchErgebnis);
//	}
}
