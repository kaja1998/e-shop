package shop.local.entities;

import java.util.ArrayList;

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
        //Der übergebene Warenkorb wird dann der Variable cart zugewiesen, und du kannst weiterhin Artikel hinzufügen, aktualisieren oder löschen
        public ShoppingCart(ArrayList<ShoppingCartItem> cartItems) {
                this.cartItems = cartItems;
        }

        public ArrayList<ShoppingCartItem> getCartItems() {
                return cartItems;
        }

        public void setCartItems(ArrayList<ShoppingCartItem> cartItems) {
                this.cartItems = cartItems;
        }

//        public Invoice getInvoice() {
//                return invoice;
//        }
//
//        public void setInvoice(Invoice invoice) {
//                this.invoice = invoice;
//        }

        public void addArticle(Article article, int quantity) {
                //neues Objekt ShoppingCartItem wird erstellt und mit den übergebenen Parametern article und quantity initialisiert
                ShoppingCartItem object = new ShoppingCartItem(article, quantity);
                //ShoppingCartItem-Objekt object wird zur ArrayListe cart hinzugefügt
                cartItems.add(object);
        }

        public boolean cartContainsArticle(Article article) {
                if (cartItems.contains(article)) {
                        return true;
                } else {
                        return false;
                }
        }

        //Wenn Artikel in den Warenkorb hinzugefügt werden, der Artikel aber schon im Warenkorb existiert, wird die quantity einfach nur auf den
        //schon bestehenden gleichen Artikel aufaddiert und dieser nicht ein zweites Mal hinzugefügt
        public void addUpArticleQuantity(Article article, int quantity){
                for (ShoppingCartItem item : cartItems) {
                        if (item.getArticle().equals(article)) {
                                item.setQuantity(item.getQuantity() + quantity);
                                return;
                        }
                }
        }

        public void read() {
                System.out.println("In your shopping cart are the following items: ");
                //Mit einer Schleife wird durch die ArrayList cart iteriert. item ist dabei die aktuelle Iteration
                for (ShoppingCartItem item : cartItems) {
                        //Für jedes ShoppingCartItem wird die Menge, die Artikelnummer, der Name abgerufen und auf der Konsole ausgegeben
                        System.out.println(item.getQuantity() + "x " + item.getArticle().getNumber() + " " + item.getArticle().getArticleTitle() + " " + item.getArticle().getPrice() + " €");
                }
        }


        //Funktioniert mit contains
        public void updateArticleQuantity(Article article, int newQuantity) {
                //Wenn der übergebene Artikel Sinn macht / wirklich existiert
                if (article != null) {
                        //Befindet sich der Artikel im Warenkorb?
                        if (cartItems.contains(article)) {
                                //Wenn übergebene Menge gleich null soll der Artikel gelöscht werden
                                if (newQuantity == 0) {
                                        deleteSingleArticle(article);
                                //Wenn übergebene Menge > 0, dann gehe mit einer for-Schleife durch den Warenkorb Array
                                } else {
                                        for (ShoppingCartItem item : cartItems) {
                                                //Vergleicht die Artikel im Warenkorb mit dem übergebenen Artikel
                                                if (item.getArticle().equals(article)) {
                                                        //Wenn im Warenkorb ein Artikel gefunden wird, der wie der übergebene Artikel ist
                                                        // dann nehmen den Artikel item und setzte seine Menge auf die, die der Nutzer übergeben hat
                                                        item.setQuantity(newQuantity);
                                                        //Gehe raus aus der Methode
                                                        return;
                                                }
                                        }
                                }
                        //Artikel befindet sich nicht im Warenkorb
                        } else {
                                System.out.println("Article doesn't exist in your cart yet.");
                        }
                }
        }

        //Funktioniert auch aber mit contains cooler
        /*public void updateArticleQuantity(Article article, int newQuantity) {
                //Schleife durchläuft den Warenkorb (cart) und sucht nach dem entsprechenden Artikel
                for (ShoppingCartItem item : cartItems) {
                        //Wenn übergebene Menge > 0, dann gehe mit einer for-Schleife durch den Warenkorb Array
                        if (newQuantity > 0) {
                                //Artikel werden verglichen
                                if (item.getArticle().equals(article)) {
                                        //Wenn Artikel gleich bzw. gefunden wurde, wird die Artikelmenge aktualisiert
                                        item.setQuantity(newQuantity);
                                        System.out.println("Article quantity updated successfully.");
                                        return;
                                }
                        }
                }
                if (newQuantity == 0) {
                        deleteSingleArticle(article);
                        return;
                }
                System.out.println("Article not found in the cart.");
        }*/


        //Funktioniert nicht mit contains
        /*public void deleteSingleArticle(Article article) {
                //Es wird überprüft, ob der Artikel eine gültige Referenz enthält und nicht den Wert null hat.
                if (article != null) {
                        //enthält der Warenkorb den Artikel?
                        if(cart.contains(article)) {
                                //Schleife iteriert über die Elemente im Warenkorb
                                for (int i = 0; i < cart.size(); i++) {
                                        //Stimmt das Article-Objekt des aktuellen Elements im Warenkorb mit dem übergebenen Artikel überein?
                                        if (cart.get(i).getArticle() == article) {
                                                //wenn ja, löschen
                                                cart.remove(i);
                                                System.out.println("Article removed from the cart.");
                                                return;
                                        }
                                }
                        //Wenn Artikel nicht im Warenkorb ist, wird "Article not found in the cart." ausgegeben.
                        } else {
                                System.out.println("Article not found in the cart.");
                        }
                }else {
                        System.out.println("Invalid input. Please try again.");
                }
        }*/

        //Funktioniert
        public void deleteSingleArticle(Article article) {
                for (ShoppingCartItem item : cartItems) {
                        if (item.getArticle().equals(article)) {
                                cartItems.remove(item);
                                System.out.println("Article removed from the cart.");
                                return;
                        }
                }
                System.out.println("Article not found in the cart.");
        }

        public void deleteAll() {
                cartItems.clear();
        }
}
