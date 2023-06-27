package shop.local.entities;
import shop.local.domain.exceptions.ArticleInCartNotFoundException;
import shop.local.domain.exceptions.BulkArticleException;
import shop.local.domain.exceptions.InsufficientStockException;

import java.util.ArrayList;

/**
 * ShoppingCart class
 * @author Sund
 */

public class ShoppingCart {

        //privates Attribut vom Typ ArrayList, heißt, dass es eine Liste von Article-Objekten speichert
        private ArrayList<ShoppingCartItem> cartItems;

        //private Invoice invoice; // nicht speichern, nur beim Kauf erstellen


        //Der erste Konstruktor public ShoppingCart() ist ein Standardkonstruktor ohne Parameter.
        //Beim Aufruf des Konstruktors wird automatisch eine neue ArrayList erstellt und der Variable cart zugewiesen.
        //Der Warenkorb ist zu Beginn leer und es können Artikel hinzugefügt werden.
        public ShoppingCart() {
                this.cartItems = new ArrayList<>();
        }

        //Wenn man einen bereits vorhandenen Warenkorb verwenden möchte. Man übergibt die entsprechende ArrayList<ShoppingCartItem>.
        //Der übergebene Warenkorb wird dann der Variable cartItems zugewiesen, und du kannst weiterhin Artikel hinzufügen, aktualisieren oder löschen
        public ShoppingCart(ArrayList<ShoppingCartItem> cartItems) {
                this.cartItems = cartItems;
        }

        public ArrayList<ShoppingCartItem> getCartItems() {
                return cartItems;
        }

        @Override
        public String toString() {
                String string = "";
                for (ShoppingCartItem item : cartItems ) {
                        string += item.getArticle().articleString() + "," + item.getQuantity();
                }
                return string;
        }

        public String read() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("In your shopping cart are the following items:\n");

                for (ShoppingCartItem item : cartItems) {
                        stringBuilder.append(item.getQuantity())
                                .append("x ")
                                .append(item.getArticle().getNumber())
                                .append(" (")
                                .append(item.getArticle().getArticleTitle())
                                .append(") ")
                                .append(item.getArticle().getPrice())
                                .append(" EUR\n");
                }
                return stringBuilder.toString();
        }

        public String changeArticleQuantityInCart(int newQuantity, Article article) throws ArticleInCartNotFoundException, BulkArticleException, InsufficientStockException {
                if (article instanceof BulkArticle) {
                        BulkArticle bulkArticle = (BulkArticle) article;
                        int packSize = bulkArticle.getPackSize();
                        if (newQuantity % packSize != 0) {
                                throw new BulkArticleException(article, packSize, null);
                        }
                }
                // Check if the item is still in stock
                int availableQuantity = article.getQuantityInStock();
                if (availableQuantity >= newQuantity) {
                        String updateResult = updateArticleQuantity(article, newQuantity);
                        if (updateResult !=null) {
                                return updateResult;
                        }
                } else {
                        throw new InsufficientStockException(availableQuantity, null);
                }
                return null;
        }

		public String updateArticleQuantity(Article article, int newQuantity) throws ArticleInCartNotFoundException {
                // Befindet sich der Artikel im Warenkorb?
                if (cartItems.contains(article)) {
                        // Wenn übergebene Menge gleich null, soll der Artikel gelöscht werden
                        if (newQuantity == 0) {
                                deleteSingleArticle(article);
                                return "Article deleted from shopping cart. ";
                        } else {
                                for (ShoppingCartItem item : cartItems) {
                                        // Vergleicht die Artikel im Warenkorb mit dem übergebenen Artikel
                                        if (item.getArticle().equals(article)) {
                                                // Wenn im Warenkorb ein Artikel gefunden wird, der wie der übergebene Artikel ist
                                                // dann nehmen den Artikel item und setzt seine Menge auf die, die der Nutzer übergeben hat
                                                item.setQuantity(newQuantity);
                                                // Gehe raus aus der Methode
                                                return "Article quantity updated successfully.";
                                        }
                                }
                        }
                } else {
                        throw new ArticleInCartNotFoundException(article, null);
                }
                return null;
        }

        public String addArticleToCart(Article article, int quantity) throws BulkArticleException, InsufficientStockException {
                // Überprüfen, ob der Artikel ein BulkArticle ist
                if (article instanceof BulkArticle) {
                        BulkArticle bulkArticle = (BulkArticle) article;
                        int packSize = bulkArticle.getPackSize();
                        if (quantity % packSize != 0) {
                                throw new BulkArticleException(article, packSize, null);
                        }
                }
                // Überprüfen, ob die eingegebene Menge gültig ist
                if (quantity >= 1) {
                        // Überprüfen, ob der Artikel noch vorrätig ist
                        int availableQuantity = article.getQuantityInStock();
                        if (availableQuantity >= quantity) {
                                if (cartItems.contains(article)) {
                                        return addUpArticleQuantity(article, quantity);
                                } else {
                                        addArticle(article, quantity);
                                        return "Article/s were added successfully to the cart.";
                                        // Warenkorb ausgeben
                                        //read();
                                }
                        } else {
                                throw new InsufficientStockException(availableQuantity, null);
                        }
                } else {
                        return "Please input a positive number for quantity.";
                }
        }

        public void addArticle(Article article, int quantity) {
                //neues Objekt ShoppingCartItem wird erstellt und mit den übergebenen Parametern article und quantity initialisiert
                ShoppingCartItem object = new ShoppingCartItem(article, quantity);
                //ShoppingCartItem-Objekt object wird zur ArrayListe cart hinzugefügt
                cartItems.add(object);
        }

        //Wenn Artikel in den Warenkorb hinzugefügt werden, der Artikel aber schon im Warenkorb existiert, wird die quantity einfach nur auf den
        //schon bestehenden gleichen Artikel aufaddiert und dieser nicht ein zweites Mal hinzugefügt
        public String addUpArticleQuantity(Article article, int quantity) {
                for (ShoppingCartItem item : cartItems) {
                        if (item.getArticle().equals(article)) {
                                int newQuantity = item.getQuantity() + quantity;
                                item.setQuantity(newQuantity);
                                return "Article quantity was updated.";
                        }
                }
                return null;
        }

        public String deleteSingleArticle(Article article) throws ArticleInCartNotFoundException {
                for (ShoppingCartItem item : cartItems) {
                        if (item.getArticle().equals(article)) {
                                cartItems.remove(item);
                                return "Article removed from the cart.";
                        }
                }
                throw new ArticleInCartNotFoundException(article, null);
        }

        public String deleteAllArticlesInCart(){
                cartItems.clear();
                return "All Articles were removed successfully from the cart." + "\n";
        }
}
