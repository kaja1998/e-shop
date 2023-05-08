package shop.local.domain;

import shop.local.entities.Article;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShoppingCart {

        //privates Attribut vom Typ ArrayList, heißt, dass es eine Liste von Article-Objekten speichert
        private ArrayList<ShoppingCartItem> cart;


        public ShoppingCart(){
                this.cart = new ArrayList<>();
        }

        public ShoppingCart(ArrayList<ShoppingCartItem> cart){
                this.cart = cart;
        }


        public void addArticle(Article article, int quantity) {
                ShoppingCartItem object = new ShoppingCartItem(article, quantity);
                cart.add(object);
        }

        public void read(){
                cart.toString();
        }

        public void update(Article article, int newQuantity){
                if(article != null) {
                        if (cart.contains(article)) {
                                if (newQuantity == 0){
                                        deleteSingle(article);
                                } else {
                                        for (int i = 0; i < cart.size(); i++){
                                                if (cart.get(i).getArticle() == article){
                                                        cart.get(i).setQuantity(newQuantity);
                                                        return;
                                                }
                                        }
                                }
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
