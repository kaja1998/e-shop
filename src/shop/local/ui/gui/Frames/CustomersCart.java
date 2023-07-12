package shop.local.ui.gui.Frames;

import shop.local.domain.Shop;
import shop.local.domain.exceptions.ArticleBuyingException;
import shop.local.domain.exceptions.EmptyCartException;
import shop.local.entities.*;
import shop.local.ui.gui.panels.CardLayoutPanel_Customer;
import shop.local.ui.gui.panels.CartItemsTablePanel_Customer;
import shop.local.ui.gui.panels.ChangeArticleQuantityInCartPanel_Customer;
import shop.local.ui.gui.panels.RemoveArticleFromCartPanel_Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CustomersCart extends JDialog implements ChangeArticleQuantityInCartPanel_Customer.ChangeCartItemQuantityListener, RemoveArticleFromCartPanel_Customer.RemoveCartItemFromCartListener {

    private Shop eshop;
    private User user;
    private CardLayoutPanel_Customer cardLayout;
    private CartItemsTablePanel_Customer CartItemsPanel;

    public CustomersCart(JFrame owner, Shop shop, User user) {
        super(owner, "Shopping Cart of " + user.getName(), true);
        this.user = user;
        this.eshop = shop;
        initialize();
    }

    private void initialize() {
        // Klick auf Kreuz / roten Kreis (Fenster schließen) behandeln lassen:
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Layout des Frames: BorderLayout
        this.setLayout(new BorderLayout());

        // West
        cardLayout = new CardLayoutPanel_Customer (eshop, user, this, this);

        // Center
        java.util.List<ShoppingCartItem> cartItems = eshop.getAllCartItems((Customer) user);
        // (wahlweise Anzeige als Liste oder Tabelle)
        CartItemsPanel = new CartItemsTablePanel_Customer(cartItems);
        JScrollPane scrollPane = new JScrollPane(CartItemsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Shopping Cart Items"));

        JButton clearCartButton = new JButton("Clear Cart");
        JButton buyButton = new JButton("Buy");
        setupEvents(clearCartButton, buyButton);

        // Panel für die Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearCartButton);
        buttonPanel.add(buyButton);

        // Panel für die Tabelle und die Buttons
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // "Zusammenbau" in BorderLayout des Frames
        add(cardLayout, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        this.setSize(540, 380);
        setLocationRelativeTo(null); //Wo öffnet sich das fenster auf dem Bildschirm. Jetzt mittig
        setResizable(false); // Fenster kann nicht in der Größe geändert werden;
        //setAlwaysOnTop(true); // Fenster bleibt im Vordergrund
        this.setVisible(true);
    }

    private void setupEvents(JButton clearCartButton, JButton buyButton) {
        // ActionListener für Clear Cart Button
        clearCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eshop.deleteAllArticlesInCart((Customer) user);
                } catch (EmptyCartException ex) {
                    JOptionPane.showMessageDialog(CustomersCart.this, ex.getMessage(), "Clear Cart Error", JOptionPane.ERROR_MESSAGE);
                }
                updateCartItemsList();
            }
        });

        // ActionListener für Buy Button
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Rechnung erstellen
                    Invoice invoice;
                    try {
                        invoice = eshop.buyArticles((Customer) user);
                    } catch (EmptyCartException ex) {
                        JOptionPane.showMessageDialog(CustomersCart.this, ex.getMessage(), "Buy Error", JOptionPane.ERROR_MESSAGE);
                        return; // Beende die Methode, wenn ein Fehler auftritt
                    } catch (ArticleBuyingException ex) {
                        JOptionPane.showMessageDialog(CustomersCart.this, ex.getMessage(), "Buy Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Rechnungsinformationen anzeigen
                    StringBuilder invoiceText = new StringBuilder();

                    invoiceText.append("Date: ").append(invoice.getFormattedDate()).append(" Uhr\n\n");

                    // Überprüfen, ob Artikel nicht gekauft werden konnten
                    if (invoice.getUnavailableItems() != null && invoice.getUnavailableItems().size() > 0) {
                        invoiceText.append("Unfortunately, the following items could not be purchased:\n");
                        for (ShoppingCartItem item : invoice.getUnavailableItems()) {
                            invoiceText.append(item.toString()).append("\n\n");
                        }
                        invoiceText.append("\n------\n\n");
                    }

                    invoiceText.append("You successfully purchased:\n");
                    for (ShoppingCartItem item : invoice.getPositions()) {
                        invoiceText.append(item.toString()).append("\n");
                    }

                    // Weitere Rechnungsinformationen anzeigen
                    invoiceText.append("\nTotal: ").append(invoice.getTotal()).append(" EUR\n");
                    invoiceText.append("--------------\n\n");

                    invoice.setCustomer((Customer) user);
                    invoiceText.append("Your delivery address:\n").append(invoice.getCustomerAddress()).append("\n\n");

                    invoiceText.append("Please transfer the full amount to the following bank account:\n");
                    invoiceText.append("Spice Shop\nDE35 1511 0000 1998 1997 29\nBIC: SCFBDE33");

                    // Rechnung ausgeben
                    JOptionPane.showMessageDialog(CustomersCart.this, invoiceText.toString(), "Invoice", JOptionPane.INFORMATION_MESSAGE);

                    // Warenkorb aktualisieren
                    updateCartItemsList();

                    // TODO: Artikelliste in CustomerBackEnd aktualisieren mit Interface?
                    //  Macht eventuell Sinn, wenn nun Artikel nicht mehr verfügbar sind oder neue hinzugekommen sind

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateCartItemsList() {
        // Ich lade hier einfach alle Article neu und lasse sie anzeigen
        List<ShoppingCartItem> cartItems = eshop.getAllCartItems((Customer) user);
        CartItemsPanel.updateCartItemsList(cartItems);
    }
}
