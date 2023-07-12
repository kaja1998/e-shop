package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleNotFoundException;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Panel in which an article in the shop can be deleted
 * Creates the necessary text boxes, buttons, and the ActionListener when the button "deleteButton" is clicked
 * @author Sund
 */

public class DeleteArticlePanel_Employee extends JPanel {

    public interface DeleteArticleListener {
        void updateArticleList();
    }

    private Shop eshop;
    private User user;
    private DeleteArticlePanel_Employee.DeleteArticleListener deleteArticleListener;
    private JButton deleteButton;
    private JTextField numberTextField = null;

    public DeleteArticlePanel_Employee(Shop shop, DeleteArticlePanel_Employee.DeleteArticleListener deleteArticleListener, User user) {
        this.eshop = shop;
        this.deleteArticleListener = deleteArticleListener;
        this.user = user;
        setupUI();
        setupEvents();
    }

    private void setupUI() {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        Box.Filler filler = new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize);
        add(filler);

        numberTextField = new JTextField();
        add(new JLabel("Article number:"));
        add(numberTextField);

        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
        add(filler);

        deleteButton = new JButton("Delete");
        add(deleteButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Rahmen definieren mit Titel
        //setBorder(BorderFactory.createTitledBorder("Delete Article"));
    }

    private void setupEvents() {
        deleteButton.addActionListener(e -> ArticleLöschen());
    }

    private void ArticleLöschen() {
        String articleNumberText = numberTextField.getText();

        if (articleNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.", "Delete Article Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int articleNumber = Integer.parseInt(articleNumberText);

            eshop.deleteArticle(articleNumber, user);
            JOptionPane.showMessageDialog(this, "Successfully deleted article with the number " + articleNumber + ".", "Add Article", JOptionPane.INFORMATION_MESSAGE);

            numberTextField.setText("");

            // Am Ende Listener, d.h. unseren Frame benachrichtigen:
            deleteArticleListener.updateArticleList();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Delete Article Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArticleNotFoundException anfe) {
            JOptionPane.showMessageDialog(this, anfe.getMessage(), "Delete Article Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Something went wrong.", "Delete Article Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
