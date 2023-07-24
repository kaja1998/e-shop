package eshop.client.ui.gui.frames;

import eshop.client.ui.gui.panels.*;
import eshop.common.entities.Article;
import eshop.common.entities.User;
import eshop.client.ui.gui.WindowCloser;
import eshop.common.interfaces.ShopInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class E_EmployeeFrontEnd extends JFrame implements E_AddArticlePanel.AddArticleListener, E_DeleteArticlePanel.DeleteArticleListener, E_ManageArticleStockPanel.ManageArticleListener, E_SearchArticlesPanel.SearchResultListener {

	private ShopInterface eshop;
	private User user;
	private String host;
	private int port;
	private E_SearchArticlesPanel searchPanel;
	private E_CardLayoutPanel cardLayout;
	private E_ArticlesTablePanel ArticlesPanel;

	public E_EmployeeFrontEnd(ShopInterface shop, User user,String host, int port) {
		super("Employee BackEnd of Kaja's Spice Shop");
		this.user = user;
		this.eshop = shop;
		this.host = host;
		this.port = port;
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
		this.addWindowListener(new WindowCloser(eshop));

		// Layout des Frames: BorderLayout
		this.setLayout(new BorderLayout());

		// North
		searchPanel = new E_SearchArticlesPanel(eshop, this);

		// West
		cardLayout = new E_CardLayoutPanel(eshop, this, this, this, user);

		// Center
		ArrayList<Article> articles = eshop.getAllArticles();
		// (wahlweise Anzeige als Liste oder Tabelle)
		ArticlesPanel = new E_ArticlesTablePanel(articles);
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
	 * Listener, der Benachrichtungen erhält, wenn im E_AddArticlePanel ein Article eingefügt wurde.
	 * (Als Reaktion soll die Articleliste aktualisiert werden.)
	 * @see shop.local.ui.gui.panels.E_AddArticlePanel.AddArticleListener#updateArticleList(shop.local.entities.Article)
	 */
	@Override
	public void updateArticleList() {
		// Ich lade hier einfach alle Article neu und lasse sie anzeigen
		ArrayList<Article> articles = eshop.getAllArticles();
		ArticlesPanel.updateArticlesList(articles);
	}

	/*
	 * (non-Javadoc)
	 *
	 * Listener, der Benachrichtungen erhält, wenn das E_SearchArticlesPanel ein Suchergebnis bereitstellen möchte.
	 * (Als Reaktion soll die Articleliste aktualisiert werden.)
	 * @see shop.local.ui.gui.swing.panels.E_SearchArticlesPanel.SearchResultListener#onSearchResult(java.util.List)
	 */
	@Override
	public void onSearchResult(ArrayList<Article> articles) {
		ArticlesPanel.updateArticlesList(articles);
	}


	private void setupMenu() {
		// Menüleiste anlegen
		JMenuBar mBar = new JMenuBar();

		JMenu LogoutMenu = new LogoutMenu();
		mBar.add(LogoutMenu);

		JMenu sortArticlesMenu = new SortArticlesMenu();
		mBar.add(sortArticlesMenu);

		// beim Fenster anmelden
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
			//System.out.println("Klick auf MenuItem " + e.getActionCommand());

			switch (e.getActionCommand()) {
				case "Logout":
					// Aktion für den "Logout" Menüpunkt
					user = null;

					// Schließe das E_EmployeeFrontEnd-Fenster
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
							// Öffne das L_LoginStart-Fenster
							L_LoginStart loginStart = null;
							try {
								loginStart = new L_LoginStart(host, port);
							} catch (IOException ex) {
								// Fehler protokollieren
								// Hier wird der Stacktrace in die Konsole geschrieben.
								ex.printStackTrace();

								// Benutzer informieren
								JOptionPane.showMessageDialog(null, "There has been occurred an error. Please try again or contact support.");
							}
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

	class SortArticlesMenu extends JMenu implements ActionListener {

		public SortArticlesMenu() {
			super("Sort Articles");

			JMenuItem sortByNumberItem = new JMenuItem("Sort by Number");
			JMenuItem sortByTitleItem = new JMenuItem("Sort by Title");
			sortByNumberItem.addActionListener(this);
			sortByTitleItem.addActionListener(this);

			add(sortByNumberItem);
			add(sortByTitleItem);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			ArrayList<Article> articles = eshop.getAllArticles();

			switch (e.getActionCommand()) {
				case "Sort by Number":
					ArticlesPanel.toggleSortOrderByNumber();
					ArticlesPanel.updateArticlesList(articles);
					break;
				case "Sort by Title":
					ArticlesPanel.toggleSortOrderByTitle();
					ArticlesPanel.updateArticlesList(articles);
					break;
			}
		}
	}
}