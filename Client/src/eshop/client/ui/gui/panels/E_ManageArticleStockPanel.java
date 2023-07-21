package eshop.client.ui.gui.panels;

import eshop.common.entities.User;
import eshop.common.interfaces.ShopInterface;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents an item inventory management panel for employees.
 * It contains input fields for the item number and the quantity to be added or removed, as well as an "manageButton" button.
 * When the button is clicked, the article inventory is updated accordingly and the manageArticleListener listener is notified to update the article list.
 * @author Sund
 */

public class E_ManageArticleStockPanel extends JPanel{

    // Über dieses Interface benachrichtigt das E_ManageArticleStockPanel das
    // EmployeeFrontEnd, die ArticleList bitte zu aktualisieren
    public interface ManageArticleListener {
        void updateArticleList();
    }

    private ShopInterface eshop;
    private User user;
    private E_ManageArticleStockPanel.ManageArticleListener manageArticleListener;
    private JButton manageButton;
    private JTextField articleNumberTextField = null;
    private JTextField quantityTextField = null;

    public E_ManageArticleStockPanel(ShopInterface shop, E_ManageArticleStockPanel.ManageArticleListener manageArticleListener, User user) {
        this.eshop = shop;
        this.manageArticleListener = manageArticleListener;
        this.user = user;
        setupUI();
//        setupEvents();
    }

    private void setupUI() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        Box.Filler filler = new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize);
        add(filler);

        articleNumberTextField = new JTextField();
        quantityTextField = new JTextField();
        add(new JLabel("Article number:"));
        add(articleNumberTextField);
        add(new JLabel("<html>Quantity to<br>add / retrieve (+ / -):</html>"));
        add(quantityTextField);

        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
        add(filler);

        manageButton = new JButton("Update quantity");
        //ManageButton.setMargin(new Insets(0, 0, 0, 1)); // Abstand nach rechts
        add(manageButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Rahmen definieren
        //setBorder(BorderFactory.createTitledBorder("Insert new Article"));
    }

//    private void setupEvents() {
//        manageButton.addActionListener(e -> ManageStock());
//    }
//
//    private void ManageStock() {
//        String articleNumberText = articleNumberTextField.getText();
//        String quantityText = quantityTextField.getText();
//
//        if (articleNumberText.isEmpty() || quantityText.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Fill in all fields.", "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        int articleNumber;
//        int stockChange;
//
//        try {
//            articleNumber = Integer.parseInt(articleNumberText);
//            stockChange = Integer.parseInt(quantityText);
//        } catch (NumberFormatException nfe) {
//            JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        Article article;
//        try {
//            article = eshop.searchByArticleNumber(articleNumber);
//        } catch (ArticleNotFoundException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage(), "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try {
//            if (stockChange < 0) {
//                try {
//                    eshop.decreaseArticleStock(article, (-1) * stockChange, user);
//                    JOptionPane.showMessageDialog(this, "Successfully decreased article's stock of article with number " + articleNumber + ".", "Manage Stock", JOptionPane.INFORMATION_MESSAGE);
//                } catch (StockDecreaseException s){
//                    JOptionPane.showMessageDialog(this, s.getMessage(), "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
//                }
//            } else {
//                eshop.increaseArticleStock(article, stockChange, user);
//                JOptionPane.showMessageDialog(this, "Successfully increased article's stock of article with number " + articleNumber + ".", "Manage Stock", JOptionPane.INFORMATION_MESSAGE);
//                //System.out.println("Successfully increased article's stock.");
//            }
//
//            articleNumberTextField.setText("");
//            quantityTextField.setText("");
//
//            // Am Ende Listener, d.h. Frame benachrichtigen:
//            manageArticleListener.updateArticleList();
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Something went wrong.", "Manage Stock Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
}
