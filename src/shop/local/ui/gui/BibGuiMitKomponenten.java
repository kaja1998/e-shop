package shop.local.ui.gui;

//import bib.local.domain.Bibliothek;
//import bib.local.entities.Buch;
//import bib.local.ui.gui.panels.AddBookPanel;
//import bib.local.ui.gui.panels.BooksTablePanel;
//import bib.local.ui.gui.panels.SearchBooksPanel;

import shop.local.domain.Shop;
import shop.local.entities.Article;
import shop.local.ui.gui.panels.AddBookPanel;
import shop.local.ui.gui.panels.BooksTablePanel;
import shop.local.ui.gui.panels.SearchBooksPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class BibGuiMitKomponenten extends JFrame implements AddBookPanel.AddBookListener, SearchBooksPanel.SearchResultListener {

	private  Shop eshop;

	private SearchBooksPanel searchPanel;
	private AddBookPanel addPanel;
//	private BooksListPanel booksPanel;
	private BooksTablePanel booksPanel;

	private JTextField nummerTextFeld = null;
	private JTextField titelTextFeld = null;
	private JTextField suchTextfeld = null;
	private JButton suchButton = null;
	private JButton hinzufuegenButton = null;
	private JList buecherListe = null;
	private JTable buecherTabelle = null;

	public BibGuiMitKomponenten(String titel) {
		super(titel);

		try {
			eshop = new Shop("ESHOP");

//			// Code für Umschaltung des Look-and-Feels:
//			// (Einfach mal ausprobieren!)
//			try {
//				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
////				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
////				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//				SwingUtilities.updateComponentTreeUI(this);
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (UnsupportedLookAndFeelException e) {
//				e.printStackTrace();
//			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		initialize();
	}

	private void initialize() {
		// Menü initialisieren
		setupMenu();

		// Klick auf Kreuz / roten Kreis (Fenster schließen) behandeln lassen:
		// A) Mittels Default Close Operation
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// B) Mittels WindowAdapter (für Sicherheitsabfrage)
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowCloser());

		// Layout des Frames: BorderLayout
		this.setLayout(new BorderLayout());

		// North
		searchPanel = new SearchBooksPanel(eshop, this);

		// West
		addPanel = new AddBookPanel(eshop, this);

		// Center
		List<Article> articles = eshop.getAllArticles();
		// (wahlweise Anzeige als Liste oder Tabelle)
//		booksPanel = new BooksListPanel(buecher);
		booksPanel = new BooksTablePanel(articles);
		JScrollPane scrollPane = new JScrollPane(booksPanel);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Articles"));

		// "Zusammenbau" in BorderLayout des Frames
		getContentPane().add(searchPanel, BorderLayout.NORTH);
		add(addPanel, BorderLayout.WEST);
		add(scrollPane, BorderLayout.CENTER);
		// Hinweis zu ContentPane oben:
		// Komponenten müssen in Swing der ContentPane hinzugefügt werden (siehe oben).
		// this.add() oder add() können aber auch direkt auf einem Container-Objekt
		// aufgerufen werden. Die Komponenten werden dann per Default der ContentPane
		// hinzugefügt.

		this.setSize(640, 480);
		this.setVisible(true);
	}


	public static void main(String[] args) {
		// Start der Anwendung (per anonymer Klasse)
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BibGuiMitKomponenten gui = new BibGuiMitKomponenten("Kaja's Spice Shop");
			}
		});
		
//		// Start der Anwendung (per Lambda-Expression)
//		SwingUtilities.invokeLater(() -> { BibGuiAusVL gui = new BibGuiAusVL("Bibliothek"); });
	}

	/*
	 * (non-Javadoc)
	 *
	 * Listener, der Benachrichtungen erhält, wenn im AddBookPanel ein Buch eingefügt wurde.
	 * (Als Reaktion soll die Bücherliste aktualisiert werden.)
	 * @see bib.local.ui.gui.panels.AddBookPanel.AddBookListener#onBookAdded(bib.local.entities.Buch)
	 */
	@Override
	public void onBookAdded(Article article) {
		// Ich lade hier einfach alle Bücher neu und lasse sie anzeigen
		List<Article> articles = eshop.getAllArticles();
		booksPanel.updateBooksList(articles);
	}

	/*
	 * (non-Javadoc)
	 *
	 * Listener, der Benachrichtungen erhält, wenn das SearchBooksPanel ein Suchergebnis bereitstellen möchte.
	 * (Als Reaktion soll die Bücherliste aktualisiert werden.)
	 * @see bib.local.ui.gui.swing.panels.SearchBooksPanel.SearchResultListener#onSearchResult(java.util.List)
	 */
	@Override
	public void onSearchResult(List<Article> articles) {
		booksPanel.updateBooksList(articles);
	}


	private void setupMenu() {
		// Menüleiste anlegen ...
		JMenuBar mBar = new JMenuBar();

		JMenu fileMenu = new FileMenu();
		mBar.add(fileMenu);

		JMenu helpMenu = new HelpMenu();
		mBar.add(helpMenu);

		// ... und beim Fenster anmelden
		this.setJMenuBar(mBar);
	}

	/*
	 * (non-Javadoc)
	 *
	 * Mitgliedsklasse für File-Menü
	 *
	 */
	class FileMenu extends JMenu implements ActionListener {

		public FileMenu() {
			super("File");

			JMenuItem saveItem = new JMenuItem("Save");
			saveItem.addActionListener(this);
			add(saveItem);

			addSeparator();

			JMenuItem quitItem = new JMenuItem("Quit");
			quitItem.addActionListener(this);
			add(quitItem);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Klick auf MenuItem " + e.getActionCommand());

			switch (e.getActionCommand()) {
				case "Save":
					try {
						eshop.writeArticleDataToAddArticle();
						//eshop.schreibeBuecher();
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
					break;
				case "Quit":
					// Nur "this" ginge nicht, weil "this" auf das FileMenu-Objekt zeigt.
					// "BibGuiAusVL.this" zeigt auf das dieses (innere) FileMenu-Objekt
					// umgebende Objekt der Klasse BibGuiAusVL.
					BibGuiMitKomponenten.this.setVisible(false);
					BibGuiMitKomponenten.this.dispose();
					System.exit(0);

			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * Mitgliedsklasse für Help-Menü
	 *
	 */
	class HelpMenu extends JMenu implements ActionListener {

		public HelpMenu() {
			super("Help");

			// Nur zu Testzwecken: Menü mit Untermenü
			JMenu m = new JMenu("About");
			JMenuItem mi = new JMenuItem("Programmers");
			mi.addActionListener(this);
			m.add(mi);
			mi = new JMenuItem("Stuff");
			mi.addActionListener(this);
			m.add(mi);
			this.add(m);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Klick auf Menü '" + e.getActionCommand() + "'.");
		}
	}
}
