package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleNotFoundException;
import shop.local.domain.exceptions.BulkArticleException;
import shop.local.domain.exceptions.InsufficientStockException;
import shop.local.entities.Article;
import shop.local.entities.Customer;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;

/**
 * Panel in which an article can be added to customers shopping cart
 * Creates the necessary text boxes, buttons, and the ActionListener when the button "addToCartButton" is clicked
 * @author Sund
 */

public class C_AddArticleToCartPanel extends JPanel {

    private Shop eshop;
    private User user;
    private JButton addToCartButton;
    private JTextField articleNumberTextField = null;
    private JTextField quantityTextField = null;


    public C_AddArticleToCartPanel(Shop shop, User user) {
        this.eshop = shop;
        this.user = user;
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

        articleNumberTextField = new JTextField();
        quantityTextField = new JTextField();
        add(new JLabel("Article Nr.:"));
        add(articleNumberTextField);
        add(new JLabel("Quantity:"));
        add(quantityTextField);


        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
        add(filler);

        addToCartButton = new JButton("Add to Cart");
        add(addToCartButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Add Article to Cart"));

        // Bevorzugte Größe des Panels anpassen
        Dimension preferredSize = new Dimension(170, getPreferredSize().height);
        setPreferredSize(preferredSize);
    }

    private void setupEvents() {
        addToCartButton.addActionListener(e -> AddArticleToCart());
    }

    private void AddArticleToCart() {
        String articleNumberText = articleNumberTextField.getText();
        String quantityText = quantityTextField.getText();

        if (articleNumberText.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.", "Add Article to Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int articleNumber;
        int quantity;

        try {
            articleNumber = Integer.parseInt(articleNumberText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Add Article to cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter a positive integer value greater than 1 for Quantity.", "Add Article to Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Article article;
        try {
            article = eshop.searchByArticleNumber(articleNumber);
        } catch (ArticleNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Add Article to Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            eshop.addArticleToCart(article, quantity, (Customer) user);
            JOptionPane.showMessageDialog(this, "Successfully added Article to Cart", "Add Article to Cart", JOptionPane.INFORMATION_MESSAGE);

            articleNumberTextField.setText("");
            quantityTextField.setText("");

        } catch (InsufficientStockException | BulkArticleException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Add Article to Cart Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
