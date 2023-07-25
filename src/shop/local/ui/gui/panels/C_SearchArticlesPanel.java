package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleNotFoundException;
import shop.local.entities.Article;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class C_SearchArticlesPanel extends JPanel {

    // Über dieses Interface übermittelt das E_SearchArticlesPanel
    // Suchergebnisse an einen Empfänger. Hier das E_EmployeeFrontEnd,
    // welches dieses Interface implementiert und auf ein neues
    // Suchergebnis reagiert, indem es die Articleliste aktualisiert.
    public interface SearchResultListener {
        void onSearchResult(ArrayList<Article> articles);
    }

    private Shop eshop;
    private JTextField searchTextField;
    private JButton searchButton = null;
    private C_SearchArticlesPanel.SearchResultListener searchResultListener;

    public C_SearchArticlesPanel(Shop shop, C_SearchArticlesPanel.SearchResultListener searchResultListener) {
        eshop = shop;
        this.searchResultListener = searchResultListener;
        setupUI();
        setupEvents();
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

    private void setupEvents() {
        searchButton.addActionListener(e -> SuchListener());
    }

    // Lokale Klasse für Reaktion auf Such-Klick
    private void SuchListener() {
        // Suchbegriff aus dem Textfeld lesen
        String suchbegriff = searchTextField.getText();

        // Liste für das Suchergebnis deklarieren
        ArrayList<Article> suchErgebnis;

        // Überprüfen, ob das Suchfeld leer ist
        if (suchbegriff.isEmpty()) {
            // Wenn das Suchfeld leer ist, alle Artikel abrufen
            suchErgebnis = eshop.getAllArticlesWithoutInactive();
        } else {
            // Wenn ein Suchbegriff eingegeben wurde
            try {
                // Versuche, Artikel basierend auf dem Titel zu suchen
                suchErgebnis = eshop.searchByArticleTitle(suchbegriff);
            } catch (ArticleNotFoundException e) {
                // Wenn ein Artikel nicht gefunden wird, zeige eine Fehlermeldung an
                JOptionPane.showMessageDialog(this, e.getMessage(), "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Das Suchergebnis an den searchResultListener übergeben
        searchResultListener.onSearchResult(suchErgebnis);
    }
}
