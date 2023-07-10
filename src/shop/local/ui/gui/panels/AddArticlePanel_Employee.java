package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddArticlePanel_Employee extends JPanel {

	public interface AddArticleListener {
		public void updateArticleList();
	}

	private Shop eshop;
	private  User user;
	private AddArticleListener addArticleListener;
	private JButton hinzufuegenButton;
	private JTextField titelTextFeld = null;
	private JTextField priceTextFeld = null;
	private JTextField quanitityTextFeld = null;
	private JTextField articleTypeTextFeld = null;
	private JTextField packSizeTextFeld = null;

	public AddArticlePanel_Employee(Shop shop, AddArticleListener addArticleListener, User user) {
		this.eshop = shop;
		this.addArticleListener = addArticleListener;
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

		titelTextFeld = new JTextField();
		priceTextFeld = new JTextField();
		quanitityTextFeld = new JTextField();
		articleTypeTextFeld = new JTextField();
		packSizeTextFeld = new JTextField();
		add(new JLabel("Article Title:"));
		add(titelTextFeld);
		add(new JLabel("Price:"));
		add(priceTextFeld);
		add(new JLabel("Initital Quantity:"));
		add(quanitityTextFeld);
		add(new JLabel("Type (bulk / single):"));
		add(articleTypeTextFeld);
		add(new JLabel("Pack size:"));
		add(packSizeTextFeld);

		// Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
		Dimension fillerMinSize = new Dimension(5, 20);
		Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
		Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
		filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
		add(filler);

		hinzufuegenButton = new JButton("Add");
		add(hinzufuegenButton);

		// Abstandhalter ("Filler") zwischen letztem Element und Rand
		add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

		// Rahmen definieren
		//setBorder(BorderFactory.createTitledBorder("Insert new Article"));
	}

	private void setupEvents() {
		hinzufuegenButton.addActionListener(e -> AddArticle());
	}

	private void AddArticle() {
		String titel = titelTextFeld.getText();
		String priceText = priceTextFeld.getText();
		String quantityText = quanitityTextFeld.getText();
		String articleType = articleTypeTextFeld.getText();
		String packSizeText = packSizeTextFeld.getText();

		if (titel.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || articleType.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Fill in all fields", "Add Article Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Überprüfen, ob articleType "single" oder "bulk" ist
		if (!articleType.equals("single") && !articleType.equals("bulk")) {
			JOptionPane.showMessageDialog(this, "Please enter 'single' or 'bulk' for article type", "Add Article Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			double price = Double.parseDouble(priceText);
			int quantity = Integer.parseInt(quantityText);
			int packSize = 2;

			if (articleType.equals("bulk")) {
				// Überprüfen, ob packSize eingegeben wurde
				if (packSizeText.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Please enter an integer value for pack size", "Add Article Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					packSize = Integer.parseInt(packSizeText);
					if (packSize <= 1) {
						JOptionPane.showMessageDialog(this, "Please enter a positive integer value greater than 1 for pack size.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			eshop.insertArticle(titel, price, quantity, articleType, packSize, user);
			JOptionPane.showMessageDialog(this, "Successfully added article", "Add Article", JOptionPane.INFORMATION_MESSAGE);

			titelTextFeld.setText("");
			priceTextFeld.setText("");
			quanitityTextFeld.setText("");
			articleTypeTextFeld.setText("");
			packSizeTextFeld.setText("");

			// Am Ende Listener, d.h. unseren Frame benachrichtigen:
			addArticleListener.updateArticleList();
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
		} catch (ArticleAlreadyExistsException bebe) {
			JOptionPane.showMessageDialog(this, bebe.getMessage(), "Add Article Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
