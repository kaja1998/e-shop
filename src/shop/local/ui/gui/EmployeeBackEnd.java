package shop.local.ui.gui;

import shop.local.domain.Shop;
import shop.local.entities.Article;
import shop.local.entities.User;
import shop.local.ui.gui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class EmployeeBackEnd extends JFrame implements AddArticlePanel.AddArticleListener, DeleteArticlePanel.DeleteArticleListener, ManageArticleStockPanel.ManageArticleListener, SearchArticlesPanel.SearchResultListener {

	private Shop eshop;
	private User user;
	private SearchArticlesPanel searchPanel;
	private CardLayoutEmployee cardLayout;
	private ArticlesTablePanel ArticlesPanel;

	public EmployeeBackEnd(Shop shop, User user) {
		super("Kaja's Spice Shop");
		this.user = user;
		eshop = shop;
		initialize();
	}

	private void initialize() {
		// Menü initialisieren
		setupMenu();

		// Klick auf Kreuz / roten Kreis (Fenster schließen) behandeln lassen:
		// A) Mittels Default Close Operation
		// setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// B) Mittels WindowAdapter (für Sicherheitsabfrage)
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowCloser());

		// Layout des Frames: BorderLayout
		this.setLayout(new BorderLayout());

		// North
		searchPanel = new SearchArticlesPanel(eshop, this);

		// West
		cardLayout = new CardLayoutEmployee(eshop, this, this, this, user);

		// Center
		List<Article> articles = eshop.getAllArticles();
		// (wahlweise Anzeige als Liste oder Tabelle)
		ArticlesPanel = new ArticlesTablePanel(articles);
		JScrollPane scrollPane = new JScrollPane(ArticlesPanel);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Articles"));

		// "Zusammenbau" in BorderLayout des Frames
		getContentPane().add(searchPanel, BorderLayout.NORTH);
		add(cardLayout, BorderLayout.WEST);
		add(scrollPane, BorderLayout.CENTER);
		// Hinweis zu ContentPane oben:
		// Komponenten müssen in Swing der ContentPane hinzugefügt werden (siehe oben).
		// this.add() oder add() können aber auch direkt auf einem Container-Objekt
		// aufgerufen werden. Die Komponenten werden dann per Default der ContentPane
		// hinzugefügt.

		this.setSize(640, 480);
		setLocationRelativeTo(null); //Wo öffnet sich das fenster auf dem Bildschirm. Jetzt mittig
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * Listener, der Benachrichtungen erhält, wenn im AddArticlePanel ein Article eingefügt wurde.
	 * (Als Reaktion soll die Articleliste aktualisiert werden.)
	 * @see shop.local.ui.gui.panels.AddArticlePanel.AddArticleListener#updateArticleList(shop.local.entities.Article)
	 */
	@Override
	public void updateArticleList() {
		// Ich lade hier einfach alle Article neu und lasse sie anzeigen
		List<Article> articles = eshop.getAllArticles();
		ArticlesPanel.updateArticlesList(articles);
	}

	/*
	 * (non-Javadoc)
	 *
	 * Listener, der Benachrichtungen erhält, wenn das SearchArticlesPanel ein Suchergebnis bereitstellen möchte.
	 * (Als Reaktion soll die Articleliste aktualisiert werden.)
	 * @see shop.local.ui.gui.swing.panels.SearchArticlesPanel.SearchResultListener#onSearchResult(java.util.List)
	 */
	@Override
	public void onSearchResult(List<Article> articles) {
		ArticlesPanel.updateArticlesList(articles);
	}


	private void setupMenu() {
		// Menüleiste anlegen ...
		JMenuBar mBar = new JMenuBar();

		JMenu LogoutMenu = new LogoutMenu();
		mBar.add(LogoutMenu);

		// ... und beim Fenster anmelden
		this.setJMenuBar(mBar);
	}

	class LogoutMenu extends JMenu implements ActionListener {

		public LogoutMenu() {
			super("Logout");

			JMenuItem logoutItem = new JMenuItem("Logout");
			logoutItem.addActionListener(this);
			add(logoutItem);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Klick auf MenuItem " + e.getActionCommand());

			switch (e.getActionCommand()) {
				case "Logout":
					// Aktion für den "Logout" Menüpunkt
					eshop.logout(user); // Aufruf der eshop.logout() Methode

					// Schließe das EmployeeBackEnd-Fenster
					Window window = SwingUtilities.windowForComponent(this);
					if (window instanceof JFrame) {
						JFrame frame = (JFrame) window;
						frame.dispose();
					}

					// Erstelle das Erfolgsfenster
					JOptionPane pane = new JOptionPane("Successfully logged out", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					JDialog dialog = pane.createDialog("Logout");

					// Schließe das Erfolgsfenster nach 2 Sekunden
					Timer timer = new Timer(2000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent evt) {
							dialog.dispose();
							// Öffne das LoginStart-Fenster
							LoginStart loginStart = new LoginStart();
							loginStart.setVisible(true);
						}
					});
					timer.setRepeats(false); // Der Timer wird nur einmal ausgeführt
					timer.start();

					// Füge einen WindowListener hinzu, um den Timer zu stoppen, wenn das Fenster manuell geschlossen wird
					dialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							timer.stop();
						}
					});

					// Zeige das Erfolgsfenster an
					dialog.setVisible(true);
					break;
			}
		}
	}
}