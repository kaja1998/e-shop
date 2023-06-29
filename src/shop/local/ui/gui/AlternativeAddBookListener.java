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
import java.util.Vector;

public class AlternativeAddBookListener implements ActionListener {

    private Shop eshop;
    private JTextField nummerTextFeld = null;
    private JTextField titelTextFeld = null;
    private JList buecherListe = null;

    public AlternativeAddBookListener(Shop eshop, JTextField nummerTextFeld, JTextField titelTextFeld, JList buecherListe) {
        this.eshop = eshop;
        this.nummerTextFeld = nummerTextFeld;
        this.titelTextFeld = titelTextFeld;
        this.buecherListe = buecherListe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int nummer = Integer.parseInt(nummerTextFeld.getText());
            String titel = titelTextFeld.getText();
            eshop.insertArticle(titel, nummer, null);
            java.util.List<Article> articles = eshop.getAllArticles();
            buecherListe.setListData(new Vector(articles));
        } catch (ArticleAlreadyExistsException | NumberFormatException ex) {
            System.err.println("error: " + ex.getMessage());
        }
    }
}
