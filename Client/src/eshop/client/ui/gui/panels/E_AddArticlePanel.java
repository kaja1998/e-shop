package eshop.client.ui.gui.panels;

import eshop.common.entities.User;
import eshop.common.exceptions.ArticleAlreadyExistsException;
import eshop.common.interfaces.ShopInterface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Panel in which a new article can be added to the shop's article inventory
 * Creates the necessary text boxes, buttons, and the ActionListener when the button is clicked
 * @author Sund
 */

public class E_AddArticlePanel extends JPanel {

	// Über dieses Interface benachrichtigt das E_AddArticlePanel das
	// EmployeeFrontEnd, die ArticleList bitte zu aktualisieren
	public interface AddArticleListener {
		void updateArticleList();
	}

	private ShopInterface eshop;
	private User user;
	private AddArticleListener addArticleListener;
	private JButton addButton;
	private JTextField titleTextField = null;
	private JTextField priceTextField = null;
	private JTextField quantityTextField = null;
	private JTextField articleTypeTextField = null;
	private JTextField packSizeTextField = null;

	public E_AddArticlePanel(ShopInterface shop, AddArticleListener addArticleListener, User user) {
		this.eshop = shop;
		this.addArticleListener = addArticleListener;
		this.user = user;
		setupUI();
//		setupEvents();
	}

	private void setupUI() {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Abstandhalter ("Filler") zwischen Rand und erstem Element
		Dimension borderMinSize = new Dimension(5, 10);
		Dimension borderPrefSize = new Dimension(5, 10);
		Dimension borderMaxSize = new Dimension(5, 10);
		Box.Filler filler = new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize);
		add(filler);

		titleTextField = new JTextField();
		priceTextField = new JTextField();
		quantityTextField = new JTextField();
		articleTypeTextField = new JTextField();
		packSizeTextField = new JTextField();
		add(new JLabel("Article Title:"));
		add(titleTextField);
		add(new JLabel("Price:"));
		add(priceTextField);
		add(new JLabel("Initital Quantity:"));
		add(quantityTextField);
		add(new JLabel("Type (bulk / single):"));
		add(articleTypeTextField);
		add(new JLabel("Pack size:"));
		add(packSizeTextField);

		// Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
		Dimension fillerMinSize = new Dimension(5, 20);
		Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
		Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
		filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
		add(filler);

		addButton = new JButton("Add");
		add(addButton);

		// Abstandhalter ("Filler") zwischen letztem Element und Rand
		add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

	}

//	private void setupEvents() {
//		addButton.addActionListener(e -> AddArticle());
//	}
//
//	private void AddArticle() {
//		String title = titleTextField.getText();
//		String priceText = priceTextField.getText();
//		String quantityText = quantityTextField.getText();
//		String articleType = articleTypeTextField.getText();
//		String packSizeText = packSizeTextField.getText();
//
//		//Eingaben dürfen nicht leer sein
//		if (title.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || articleType.isEmpty()) {
//			JOptionPane.showMessageDialog(this, "Fill in all fields.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//
//		// Überprüfen, ob articleType "single" oder "bulk" ist
//		if (!articleType.equals("single") && !articleType.equals("bulk")) {
//			JOptionPane.showMessageDialog(this, "Please enter 'single' or 'bulk' for article type", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//
//		try {
//			double price = Double.parseDouble(priceText);
//			int quantity = Integer.parseInt(quantityText);
//			int packSize = 2;
//
//			if (articleType.equals("bulk")) {
//				// Überprüfen, ob packSize eingegeben wurde
//				if (packSizeText.isEmpty()) {
//					JOptionPane.showMessageDialog(this, "Please enter an integer value for pack size", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				try {
//					packSize = Integer.parseInt(packSizeText);
//					if (packSize <= 1) {
//						JOptionPane.showMessageDialog(this, "Please enter a positive integer value greater than 1 for pack size.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//				} catch (NumberFormatException nfe) {
//					JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//			}
//
//			eshop.insertArticle(title, price, quantity, articleType, packSize, user);
//			JOptionPane.showMessageDialog(this, "Successfully added article", "Add Article", JOptionPane.INFORMATION_MESSAGE);
//
//			titleTextField.setText("");
//			priceTextField.setText("");
//			quantityTextField.setText("");
//			articleTypeTextField.setText("");
//			packSizeTextField.setText("");
//
//			// Am Ende Listener, d.h. Frame benachrichtigen:
//			addArticleListener.updateArticleList();
//		} catch (NumberFormatException nfe) {
//			JOptionPane.showMessageDialog(this, "Please enter an integer as value.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//		} catch (ArticleAlreadyExistsException bebe) {
//			JOptionPane.showMessageDialog(this, bebe.getMessage(), "Add Article Error", JOptionPane.ERROR_MESSAGE);
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(this, "Something went wrong.", "Add Article Error", JOptionPane.ERROR_MESSAGE);
//		}
//	}
}
