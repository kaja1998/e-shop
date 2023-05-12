package shop.local.domain;

import shop.local.entities.Article;
import java.util.ArrayList;

public class ShoppingCart {

        //privates Attribut vom Typ ArrayList, heißt, dass es eine Liste von Article-Objekten speichert
        private ArrayList<ShoppingCartItem> cart;

        //privates Attribut vom Typ Invoice
        private Invoice invoice;


        //Der erste Konstruktor public ShoppingCart() ist ein Standardkonstruktor ohne Parameter.
        //Beim Aufruf des Konstruktors wird automatisch eine neue ArrayList erstellt und der Variable cart zugewiesen.
        //Der Warenkorb ist zu Beginn leer und es können Artikel hinzugefügt werden.
        public ShoppingCart() {
                this.cart = new ArrayList<>();
        }

        //Wenn man einen bereits vorhandenen Warenkorb verwenden möchte. Man übergibt die entsprechende ArrayList<ShoppingCartItem>.
        //Der übergebene Warenkorb wird dann der Variable cart zugewiesen, und du kannst weiterhin Artikel hinzufügen, aktualisieren oder löschen
        public ShoppingCart(ArrayList<ShoppingCartItem> cart) {
                this.cart = cart;
        }

        public ArrayList<ShoppingCartItem> getCart() {
                return cart;
        }

        public void setCart(ArrayList<ShoppingCartItem> cart) {
                this.cart = cart;
        }

        public Invoice getInvoice() {
                return invoice;
        }

        public void setInvoice(Invoice invoice) {
                this.invoice = invoice;
        }

        public void addArticle(Article article, int quantity) {
                //neues Objekt ShoppingCartItem wird erstellt und mit den übergebenen Parametern article und quantity initialisiert
                ShoppingCartItem object = new ShoppingCartItem(article, quantity);
                //ShoppingCartItem-Objekt object wird zur ArrayListe cart hinzugefügt
                cart.add(object);
        }

        public void read() {
                System.out.println("In your shopping cart are the following items: ");
                //Mit einer Schleife wird durch die ArrayList cart iteriert. item ist dabei die aktuelle Iteration
                for (ShoppingCartItem item : cart) {
                        //Für jedes ShoppingCartItem wird die Menge, die Artikelnummer und der Name abgerufen und auf der Konsole ausgegeben
                        System.out.println(item.getQuantity() + "x " + item.getArticle().getNumber() + " " + item.getArticle().getArticleTitle());
                }
        }


        /*//Funktioniert nicht mit contains
        public void UpdateArticleQuantity(Article article, int newQuantity) {
                //Wenn der übergebene Artikel Sinn macht / wirklich existiert
                if (article != null) {
                        //Befindet sich der Artikel im Warenkorb?
                        if (cart.contains(article)) {
                                //Wenn übergebene Menge gleich null soll der Artikel gelöscht werden
                                if (newQuantity == 0) {
                                        deleteSingle(article);
                                        //Wenn übergebene Menge > 0, dann gehe mit einer for-Schleife durch den Warenkorb Array
                                } else {
                                        for (int i = 0; i < cart.size(); i++) {
                                                //Vergleicht die Artikel i im Warenkorb mit dem übergebenen Artikel
                                                if (cart.get(i).getArticle() == article) {                       //lieber equals? cart.get(i).getArticle().equals(article)
                                                        //Wenn im Warenkorb ein Artikel gefunden wird, der wie der übergebene Artikel ist, dann nehmen den Artikel i und setzte seine Menge auf die, die der Nutzer übergeben hat
                                                        cart.get(i).setQuantity(newQuantity);
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
        }*/

        //Funktioniert
        public void updateArticleQuantity(Article article, int newQuantity) {
                //Schleife durchläuft den Warenkorb (cart) und sucht nach dem entsprechenden Artikel
                for (ShoppingCartItem item : cart) {
                        if (newQuantity > 0) {
                                //Artikel werden verglichen
                                if (item.getArticle().equals(article)) {
                                        //Wenn Artikel gleich bzw. gefunden wurde, wird die Artikelmenge aktualisiert
                                        item.setQuantity(newQuantity);
                                        System.out.println("Article quantity updated successfully.");
                                        return;
                                }/*else {
                                        System.out.println("Article not found in the cart.");
                                }*/
                        }
                        if (newQuantity == 0) {
                                deleteSingleArticle(article);
                                //Wenn übergebene Menge > 0, dann gehe mit einer for-Schleife durch den Warenkorb Array
                        }
                }
        }


        /*//Funktioniert nicht mit contains
        public void deleteSingleArticle(Article article) {
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
                for (ShoppingCartItem item : cart) {
                        if (item.getArticle().equals(article)) {
                                cart.remove(item);
                                System.out.println("Article removed from the cart.");
                                return;
                        } /*else {
                                System.out.println("Article not found in the cart.");
                        }*/
                }
        }

        /*//AI Lösung - cart.removeIf(item ->) - was soll das Letzte mit dem Pfeil?
        public void deleteSingleArticle(Article article) {
                if (article != null) {
                        if (cart.contains(article)) {
                                cart.removeIf(item -> item.getArticle().equals(article));
                                System.out.println("Article removed from the cart.");
                        } else {
                                System.out.println("Article not found in the cart.");
                        }
                }
        }*/

        public void deleteAll() {
                cart.clear();
        }

}
