package shop.local.ui.gui;

//import bib.local.domain.Bibliothek;
//import bib.local.domain.exceptions.BuchExistiertBereitsException;
//import bib.local.entities.Buch;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.Article;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class AlternativeAddBookListener implements ActionListener {

    private Shop eshop;
    private JTextField titleTextFeld = null;

    private JTextField priceTextFeld = null;

    private JTextField quanitityTextFeld = null;

    private JTextField articleTypeTextFeld = null;

    private JTextField packSizeTextFeld = null;

    private JList buecherListe = null;

    public AlternativeAddBookListener(Shop eshop, JTextField titleTextFeld, JTextField priceTextFeld, JTextField quanitityTextFeld, JTextField articleTypeTextFeld, JTextField packSizeTextFeld, JList buecherListe) {
        this.eshop = eshop;
        this.titleTextFeld = titleTextFeld;
        this.priceTextFeld = priceTextFeld;
        this.quanitityTextFeld = quanitityTextFeld;
        this.articleTypeTextFeld = articleTypeTextFeld;
        this.packSizeTextFeld = packSizeTextFeld;
        this.buecherListe = buecherListe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String title = titleTextFeld.getText();
            double price = Double.parseDouble(priceTextFeld.getText());
            int quantity = Integer.parseInt(quanitityTextFeld.getText());
            String articleType = titleTextFeld.getText();
            int packSize = Integer.parseInt(packSizeTextFeld.getText());
            eshop.insertArticle(title, price, quantity, articleType, packSize, null);
            java.util.List<Article> articles = eshop.getAllArticles();
            buecherListe.setListData(new Vector(articles));
        } catch (ArticleAlreadyExistsException | NumberFormatException | IOException ex) {
            System.err.println("error: " + ex.getMessage());
        }
    }
}
