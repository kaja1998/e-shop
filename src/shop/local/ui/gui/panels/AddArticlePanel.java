package shop.local.ui.gui.panels;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.Article;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Wichtig: Das AddArticlePanel _ist ein_ Panel und damit auch eine Component;
// es kann daher in das Layout eines anderen Containers 
// (in unserer Anwendung des Frames) eingefügt werden.
public class AddArticlePanel extends JPanel {

	// Über dieses Interface übermittelt das AddArticlePanel
	// ein neu hinzugefügten Article an einen Empfänger.
	// In unserem Fall ist der Empfänger EmployeeBackEnd,
	// die dieses Interface implementiert und auf ein neue hinzugefügtes
	// Article reagiert, indem es die Articleliste aktualisiert.
	public interface AddArticleListener {
		public void onArticleAdded(Article article);
	}

	
	private Shop eshop = null;
	private  User user;
	private AddArticleListener addArticleListener = null;
	private JButton hinzufuegenButton;
	private JTextField titelTextFeld = null;
	private JTextField priceTextFeld = null;
	private JTextField quanitityTextFeld = null;
	private JTextField articleTypeTextFeld = null;
	private JTextField packSizeTextFeld = null;

	public AddArticlePanel(Shop shop, AddArticleListener addArticleListener, User user) {
		eshop = shop;
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
		add(new JLabel("Title:"));
		add(titelTextFeld);
		add(new JLabel("Price:"));
		add(priceTextFeld);
		add(new JLabel("Initital quantity:"));
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
		setBorder(BorderFactory.createTitledBorder("Insert new Article"));
	}

	private void setupEvents() {
		hinzufuegenButton.addActionListener(e -> ArticleEinfügen());
	}

	private void ArticleEinfügen() {
		String titel = titelTextFeld.getText();
		String priceText = priceTextFeld.getText();
		String quantityText = quanitityTextFeld.getText();
		String articleType = articleTypeTextFeld.getText();
		String packSizeText = packSizeTextFeld.getText();

		if (!titel.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty() && !articleType.isEmpty() && !packSizeText.isEmpty()) {
			try {
				double price = Double.parseDouble(priceText);
				int quantity = Integer.parseInt(quantityText);
				int packSize = Integer.parseInt(packSizeText);

				Article article = eshop.insertArticle(titel, price, quantity, articleType, packSize, user);

				titelTextFeld.setText("");
				priceTextFeld.setText("");
				quanitityTextFeld.setText("");
				articleTypeTextFeld.setText("");
				packSizeTextFeld.setText("");

				// Am Ende Listener, d.h. unseren Frame benachrichtigen:
				addArticleListener.onArticleAdded(article);
			} catch (NumberFormatException nfe) {
				System.err.println("Please enter an integer as value.");
			} catch (ArticleAlreadyExistsException bebe) {
				System.err.println(bebe.getMessage());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
