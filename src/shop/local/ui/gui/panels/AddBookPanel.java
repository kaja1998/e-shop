package shop.local.ui.gui.panels;

//import bib.local.domain.Bibliothek;
//import bib.local.domain.exceptions.BuchExistiertBereitsException;
//import bib.local.entities.Buch;
import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.Article;
import shop.local.entities.User;

import javax.swing.*;
import java.awt.*;

// Wichtig: Das AddBookPanel _ist ein_ Panel und damit auch eine Component; 
// es kann daher in das Layout eines anderen Containers 
// (in unserer Anwendung des Frames) eingefügt werden.
public class AddBookPanel extends JPanel {

	// Über dieses Interface übermittelt das AddBookPanel
	// ein neu hinzugefügtes Buch an einen Empfänger.
	// In unserem Fall ist der Empfänger die BibGuiMitKomponenten,
	// die dieses Interface implementiert und auf ein neue hinzugefügtes
	// Buch reagiert, indem sie die Bücherliste aktualisiert.	
	public interface AddBookListener {
		public void onBookAdded(Article article);
	}

	
	private Shop eshop = null;
	private AddBookListener addBookListener = null;

	private JButton hinzufuegenButton;
	private JTextField nummerTextFeld = null;
	private JTextField titelTextFeld = null;

	public AddBookPanel(Shop shop, AddBookListener addBookListener) {
		eshop = shop;
		this.addBookListener = addBookListener;

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

		nummerTextFeld = new JTextField();
		titelTextFeld = new JTextField();
		add(new JLabel("Nummer:"));
		add(nummerTextFeld);
		add(new JLabel("Titel:"));
		add(titelTextFeld);

		// Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
		Dimension fillerMinSize = new Dimension(5, 20);
		Dimension fillerPrefSize = new Dimension(5, Short.MAX_VALUE);
		Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
		filler = new Box.Filler(fillerMinSize, fillerPrefSize, fillerMaxSize);
		add(filler);

		hinzufuegenButton = new JButton("Hinzufuegen");
		add(hinzufuegenButton);

		// Abstandhalter ("Filler") zwischen letztem Element und Rand
		add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

		// Rahmen definieren
		setBorder(BorderFactory.createTitledBorder("Einfügen"));
	}

	private void setupEvents() {
//		hinzufuegenButton.addActionListener(
//				new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent ae) {
//						System.out.println("Event: " + ae.getActionCommand());
//						buchEinfügen();
//					}
//				});
		hinzufuegenButton.addActionListener(e -> buchEinfügen());
	}

	private void buchEinfügen() {
		String nummer = nummerTextFeld.getText();
		String titel = titelTextFeld.getText();

		if (!nummer.isEmpty() && !titel.isEmpty()) {
			try {
				int nummerAlsInt = Integer.parseInt(nummer);
				eshop.insertArticle(article, nummerAlsInt, user);
				//Article article = eshop.fuegeBuchEin(titel, nummerAlsInt);
				nummerTextFeld.setText("");
				titelTextFeld.setText("");

				// Am Ende Listener, d.h. unseren Frame benachrichtigen:
				addBookListener.onBookAdded(article);
			} catch (NumberFormatException nfe) {
				System.err.println("Bitte eine Zahl eingeben.");
			} catch (ArticleAlreadyExistsException bebe) {
				System.err.println(bebe.getMessage());
			} 
		}
	}
}
