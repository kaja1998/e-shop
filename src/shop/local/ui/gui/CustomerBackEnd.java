package shop.local.ui.gui;

import shop.local.domain.Shop;
import shop.local.entities.Article;
import shop.local.entities.User;
import shop.local.ui.gui.panels.AddArticlePanel;
import shop.local.ui.gui.panels.ArticlesTablePanel;
import shop.local.ui.gui.panels.SearchArticlesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CustomerBackEnd extends JFrame implements AddArticlePanel.AddArticleListener, SearchArticlesPanel.SearchResultListener {
    private Shop eshop;
    private User user;
    private SearchArticlesPanel searchPanel;
    private AddArticlePanel addPanel;
    private ArticlesTablePanel ArticlesPanel;

    public CustomerBackEnd(Shop shop, User user) {
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
        addPanel = new AddArticlePanel(eshop, this, user);

        // Center
        java.util.List<Article> articles = eshop.getAllArticles();
        // (wahlweise Anzeige als Liste oder Tabelle)
        ArticlesPanel = new ArticlesTablePanel(articles);
        JScrollPane scrollPane = new JScrollPane(ArticlesPanel);
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

    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erhält, wenn im AddArticlePanel ein Article eingefügt wurde.
     * (Als Reaktion soll die Articleliste aktualisiert werden.)
     * @see shop.local.ui.gui.panels.AddArticlePanel.AddArticleListener#onArticleAdded(shop.local.entities.Article)
     */
    @Override
    public void onArticleAdded(Article article) {
        // Ich lade hier einfach alle Article neu und lasse sie anzeigen
        java.util.List<Article> articles = eshop.getAllArticles();
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

        JMenu fileMenu = new CustomerBackEnd.FileMenu();
        mBar.add(fileMenu);

        JMenu helpMenu = new CustomerBackEnd.HelpMenu();
        mBar.add(helpMenu);

        JMenu LogoutMenu = new CustomerBackEnd.LogoutMenu();
        mBar.add(LogoutMenu);

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
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                case "Quit":
                    // Nur "this" ginge nicht, weil "this" auf das FileMenu-Objekt zeigt.
                    // "EmployeeBackEnd.this" zeigt auf das dieses (innere) FileMenu-Objekt
                    // umgebende Objekt der Klasse EmployeeBackEnd.
                    CustomerBackEnd.this.setVisible(false);
                    CustomerBackEnd.this.dispose();
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
            super("Help");										// Titel des Menüs

            // Nur zu Testzwecken: Menü mit Untermenü
            JMenu m = new JMenu("About"); 						// Untermenü mit dem Titel "About"
            JMenuItem mi = new JMenuItem("Programmers"); 		// Menüelement mit dem Titel "Programmers"
            mi.addActionListener(this); 							// ActionListener hinzufügen, um auf Klicks zu reagieren
            m.add(mi); 												// Menüelement zum Untermenü hinzufügen
            mi = new JMenuItem("Stuff"); 						// Ein weiteres Menüelement mit dem Titel "Stuff"
            mi.addActionListener(this); 							// ActionListener hinzufügen, um auf Klicks zu reagieren
            m.add(mi); 												// Menüelement zum Untermenü hinzufügen
            this.add(m); 											// Untermenü zum Hauptmenü hinzufügen
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Klick auf Menü '" + e.getActionCommand() + "'.");
            // Aktion, die bei einem Klick auf ein Menüelement ausgeführt wird
            // Hier wird einfach eine Meldung mit dem geklickten Menüelement ausgegeben
        }
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
                    // Hier kannst du den entsprechenden Code einfügen
                    break;
            }
        }
    }
}
