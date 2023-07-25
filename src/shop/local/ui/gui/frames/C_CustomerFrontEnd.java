package shop.local.ui.gui.frames;

import shop.local.domain.Shop;
import shop.local.entities.Article;
import shop.local.entities.User;
import shop.local.ui.gui.WindowCloser;
import shop.local.ui.gui.panels.C_AddArticleToCartPanel;
import shop.local.ui.gui.panels.C_ArticlesTablePanel;
import shop.local.ui.gui.panels.C_SearchArticlesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class C_CustomerFrontEnd extends JFrame implements C_SearchArticlesPanel.SearchResultListener {
    private Shop eshop;
    private User user;
    private C_SearchArticlesPanel searchPanel;
    private C_AddArticleToCartPanel addToCartPanel;
    private C_ArticlesTablePanel ArticlesPanel;
    private C_CustomersCart customersCartPanel;

    public C_CustomerFrontEnd(Shop shop, User user) {
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
        this.addWindowListener(new WindowCloser(eshop));

        // Layout des Frames: BorderLayout
        this.setLayout(new BorderLayout());

        // North
        searchPanel = new C_SearchArticlesPanel(eshop, this);

        // West
        addToCartPanel = new C_AddArticleToCartPanel(eshop, user);

        // Center
        ArrayList<Article> articles = eshop.getAllArticlesWithoutInactive();

        // (wahlweise Anzeige als Liste oder Tabelle)
        ArticlesPanel = new C_ArticlesTablePanel(articles);
        JScrollPane scrollPane = new JScrollPane(ArticlesPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Articles"));

        // "Zusammenbau" in BorderLayout des Frames
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        add(addToCartPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        this.setSize(640, 480);
        setLocationRelativeTo(null); //Wo öffnet sich das fenster auf dem Bildschirm. Jetzt mittig
        this.setVisible(true);
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
        // Menüleiste anlegen ...
        JMenuBar mBar = new JMenuBar();

        JMenu LogoutMenu = new C_CustomerFrontEnd.LogoutMenu();
        mBar.add(LogoutMenu);

        JMenu ShoppingCartMenu = new C_CustomerFrontEnd.ShoppingCartMenu();
        mBar.add(ShoppingCartMenu);

        JMenu sortArticlesMenu = new C_CustomerFrontEnd.SortArticlesMenu();
        mBar.add(sortArticlesMenu);

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
            //System.out.println("Klick auf MenuItem " + e.getActionCommand());

            switch (e.getActionCommand()) {
                case "Logout":
                    // Aktion für den "Logout" Menüpunkt
                    user = null;

                    // Schließe das Shoppingcart-Fenster, falls geöffnet
                    if (customersCartPanel != null) {
                        customersCartPanel.dispose();
                    }

                    // Schließe das Fenster
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
                            L_LoginStart loginStart = new L_LoginStart();
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

    class ShoppingCartMenu extends JMenu implements ActionListener {

        public ShoppingCartMenu() {
            super("Shopping Cart");

            JMenuItem logoutItem = new JMenuItem("View Cart");
            logoutItem.addActionListener(this);
            add(logoutItem);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Klick auf MenuItem " + e.getActionCommand());

            switch (e.getActionCommand()) {
                case "View Cart":
                    // Aktion für den "Cart" Menüpunkt

                    customersCartPanel = new C_CustomersCart(C_CustomerFrontEnd.this, eshop, user);
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

            ArrayList<Article> articles = eshop.getAllArticlesWithoutInactive();

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
