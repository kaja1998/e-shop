package shop.local.ui.gui;

import shop.local.domain.Shop;
import shop.local.entities.Article;
import shop.local.entities.User;
import shop.local.ui.gui.panels.AddArticleToCartPanel_Customer;
import shop.local.ui.gui.panels.ArticlesTablePanel_Customer;
import shop.local.ui.gui.panels.SearchArticlesPanel_Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class CustomerBackEnd extends JFrame implements SearchArticlesPanel_Employee.SearchResultListener {
    private Shop eshop;
    private User user;
    private SearchArticlesPanel_Employee searchPanel;
    private AddArticleToCartPanel_Customer addToCartPanel;
    private ArticlesTablePanel_Customer ArticlesPanel;
    private CustomersCart customersCartPanel;

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
        searchPanel = new SearchArticlesPanel_Employee(eshop, this);

        // West
        addToCartPanel = new AddArticleToCartPanel_Customer(eshop, user);

        //cardLayout = new CardLayoutPanel_Employee(eshop, this, this, this, user);

        // Center
        java.util.List<Article> articles = eshop.getAllArticles();
        // (wahlweise Anzeige als Liste oder Tabelle)
        ArticlesPanel = new ArticlesTablePanel_Customer(articles);
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
     * Listener, der Benachrichtungen erhält, wenn das SearchArticlesPanel_Employee ein Suchergebnis bereitstellen möchte.
     * (Als Reaktion soll die Articleliste aktualisiert werden.)
     * @see shop.local.ui.gui.swing.panels.SearchArticlesPanel_Employee.SearchResultListener#onSearchResult(java.util.List)
     */
    @Override
    public void onSearchResult(List<Article> articles) {
        ArticlesPanel.updateArticlesList(articles);
    }


    private void setupMenu() {
        // Menüleiste anlegen ...
        JMenuBar mBar = new JMenuBar();

        JMenu LogoutMenu = new CustomerBackEnd.LogoutMenu();
        mBar.add(LogoutMenu);

        JMenu ShoppingCartMenu = new CustomerBackEnd.ShoppingCartMenu();
        mBar.add(ShoppingCartMenu);

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
                    eshop.logout(user); // Aufruf der eshop.logout() Methode

                    // Schließe das Shoppingcart-Fenster, falls geöffnet
                    if (customersCartPanel != null) {
                        customersCartPanel.dispose();
                    }

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
                    customersCartPanel = new CustomersCart(eshop, user);
            }
        }
    }

}