package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleInCartNotFoundException;
import shop.local.domain.exceptions.ArticleNotFoundException;
import shop.local.domain.exceptions.BulkArticleException;
import shop.local.domain.exceptions.InsufficientStockException;
import shop.local.entities.Article;
import shop.local.entities.Customer;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;

/**
 * Panel in which an article's quantity in a customers shopping cart can be changed
 * Creates the necessary text boxes, buttons, and the ActionListener when the button "changeQuantityButton" is clicked
 * @author Sund
 */

public class ChangeArticleQuantityInCartPanel_Customer extends JPanel {

    // Über dieses Interface benachrichtigt das ChangeArticleQuantityInCartPanel_Customer Panel das
    // CustomerFrontEnd, die CartItem Liste bitte zu aktualisieren
    public interface ChangeCartItemQuantityListener {
        void updateCartItemsList();
    }

    private Shop eshop;
    private User user;
    private ChangeCartItemQuantityListener changeCartItemQuantityListener;
    private JButton changeQuantityButton;
    private JTextField articleNumberTextField = null;
    private JTextField newQuantityTextField = null;


    public ChangeArticleQuantityInCartPanel_Customer(Shop shop, User user, ChangeCartItemQuantityListener changeCartItemQuantityListener) {
        this.eshop = shop;
        this.user = user;
        this.changeCartItemQuantityListener = changeCartItemQuantityListener;
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
        newQuantityTextField = new JTextField();
        add(new JLabel("Article Nr.:"));
        add(articleNumberTextField);
        add(new JLabel("New Quantity:"));
        add(newQuantityTextField);


        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
        add(filler);

        changeQuantityButton = new JButton("Change Quantity");
        add(changeQuantityButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Rahmen definieren
        //setBorder(BorderFactory.createTitledBorder("Add to cart"));
    }

    private void setupEvents() {
        changeQuantityButton.addActionListener(e -> ChangeQuantity());
    }

    private void ChangeQuantity() {
        String articleNumberText = articleNumberTextField.getText();
        String newQuantityText = newQuantityTextField.getText();

        if (articleNumberText.isEmpty() || newQuantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.", "Change Article Quantity in Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int articleNumber;
        int newQuantity;

        try {
            articleNumber = Integer.parseInt(articleNumberText);
            newQuantity = Integer.parseInt(newQuantityText);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Change Article Quantity in Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newQuantity < 0) {
            JOptionPane.showMessageDialog(this, "Please enter a positive integer value for Quantity.", "Change Article Quantity in Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Article article;
        try {
            article = eshop.searchByArticleNumber(articleNumber);
        } catch (ArticleNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Change Article Quantity in Cart Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            eshop.changeArticleQuantityInCart(newQuantity, article, (Customer) user);
            JOptionPane.showMessageDialog(this, "Quantity successfully changed", "Change Article Quantity in Cart", JOptionPane.INFORMATION_MESSAGE);

            articleNumberTextField.setText("");
            newQuantityTextField.setText("");

            // Am Ende Listener, d.h. Frame benachrichtigen:
            changeCartItemQuantityListener.updateCartItemsList();
        } catch (ArticleInCartNotFoundException | InsufficientStockException | BulkArticleException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Change Article Quantity in Cart Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
