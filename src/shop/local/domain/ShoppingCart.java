package shop.local.domain;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.Article;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShoppingCart {

        //privates Attribut vom Typ ArrayList, heißt, dass es eine Liste von Article-Objekten speichert
        private ArrayList<ShoppingCartItem> cart;


        //Der erste Konstruktor public ShoppingCart() ist ein Standardkonstruktor ohne Parameter.
        //Beim Aufruf des Konstruktors wird automatisch eine neue ArrayList erstellt und der Variable cart zugewiesen.
        //Der Warenkorb ist zu Beginn leer und es können Artikel hinzugefügt werden.
        public ShoppingCart(){
                this.cart = new ArrayList<>();
        }

        //Wenn man einen bereits vorhandenen Warenkorb verwenden möchte. Man übergibt die entsprechende ArrayList<ShoppingCartItem>.
        //Der übergebene Warenkorb wird dann der Variable cart zugewiesen, und du kannst weiterhin Artikel hinzufügen, aktualisieren oder löschen
        public ShoppingCart(ArrayList<ShoppingCartItem> cart){
                this.cart = cart;
        }


        public void addArticle(Article article, int quantity) {
                ShoppingCartItem object = new ShoppingCartItem(article, quantity);
                cart.add(object);
        }

        public void read(){
                cart.toString();
                //Um den Inhalt des Warenkorbs zu lesen, kannst du System.out.println(cart.toString()) verwenden, um die Liste in der Konsole auszugeben
        }

        public void update(Article article, int newQuantity){
                //Wenn der übergebene Artikel Sinn macht / wirklich existiert
                if(article != null) {
                        //Befindet sich der Artikel im Warenkorb?
                        if (cart.contains(article)) {
                                //Wenn übergebene Menge gleich null soll der Artikel gelöscht werden
                                if (newQuantity == 0){
                                        deleteSingle(article);
                                //Wenn übergebene Menge > 0, dann gehe mit einer for-Schleife durch den Warenkorb Array
                                } else {
                                        for (int i = 0; i < cart.size(); i++){
                                                //Vergleicht die Artikel i im Warenkorb mit dem übergebenen Artikel
                                                if (cart.get(i).getArticle() == article){                       //lieber equals? cart.get(i).getArticle().equals(article)
                                                        //Wenn im Warenkorb ein Artikel gefunden wird, der wie der übergebene Artikel ist, dann nehmen den Artikel i und setzte seine Menge auf die, die der Nutzer übergeben hat
                                                        cart.get(i).setQuantity(newQuantity);
                                                        //Gehe raus aus der Methode
                                                        return;
                                                }
                                        }
                                }
                        //Artikel befindet sich nicht im Warenkorb
                        } else {
                                System.out.println("Article doesn't exist in your cart yet.");;
                        }
                }
        }

        public void deleteAll() {
                cart.clear();
        }

        public void deleteSingle(Article article) {
                if(article != null){
                        if (cart.contains(article)) {
                                for (int i = 0; i < cart.size(); i++){
                                        if (cart.get(i).getArticle() == article){
                                                cart.remove(i);
                                                return;
                                        }
                                }
                        } else {
                                System.out.println("Article doesn't exist in your cart yet.");;
                        }
                }
        }
}
