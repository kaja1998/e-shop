package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleInCartNotFoundException;
import shop.local.domain.exceptions.ArticleNotFoundException;
import shop.local.entities.Article;
import shop.local.entities.Customer;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RemoveArticleFromCartPanel_Customer extends JPanel{

    public interface RemoveCartItemFromCartListener {
        void updateCartItemsList();
    }

    private Shop eshop;
    private User user;
    private RemoveArticleFromCartPanel_Customer.RemoveCartItemFromCartListener removeCartItemFromCartListener;
    private JButton RemoveButton;
    private JTextField articleNumberTextFeld = null;


    public RemoveArticleFromCartPanel_Customer(Shop shop, User user, RemoveArticleFromCartPanel_Customer.RemoveCartItemFromCartListener removeCartItemFromCartListener) {
        this.eshop = shop;
        this.user = user;
        this.removeCartItemFromCartListener = removeCartItemFromCartListener;
        setupUI();
        setupEvents();
    }

    private void setupUI() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        add(Box.createRigidArea(new Dimension(5, 0)));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        Box.Filler filler = new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize);
        add(filler);

        articleNumberTextFeld = new JTextField();
        add(new JLabel("Article Nr.:"));
        add(articleNumberTextFeld);


        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
        add(filler);

        RemoveButton = new JButton("Remove");
        add(RemoveButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Rahmen definieren
        //setBorder(BorderFactory.createTitledBorder("Add to cart"));
    }

    private void setupEvents() {
        RemoveButton.addActionListener(e -> RemoveCartItem());
    }

    private void RemoveCartItem() {
        String articleNumberText = articleNumberTextFeld.getText();

        if (articleNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields", "Remove Article from Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int articleNumber;

        try {
            articleNumber = Integer.parseInt(articleNumberText);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Remove Article from Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Article article;
        try {
            article = eshop.searchByArticleNumber(articleNumber);
        } catch (ArticleNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Remove Article from Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            eshop.removeArticleFromCART((Customer) user, article);
            JOptionPane.showMessageDialog(this, "Article successfully removed from Cart", "Remove Article from Cart", JOptionPane.INFORMATION_MESSAGE);

            articleNumberTextFeld.setText("");

            // Am Ende Listener, d.h. Frame benachrichtigen:
            removeCartItemFromCartListener.updateCartItemsList();

        } catch (ArticleInCartNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Remove Article from Cart Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Something went wrong. Please try again.", "Remove Article from Cart Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
