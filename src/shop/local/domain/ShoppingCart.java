package shop.local.domain;

import shop.local.entities.Article;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShoppingCart {

        private ArrayList<Article> items;

        public ShoppingCart() {
                items = new ArrayList<>();
        }

        public void addArticle(Article article, int quantity) {
                items.add(article, quantity);
        }

        public void removeArticle(Article Article) {
            System.out.println("");
        }

        public void changequantity(Article Article, int newQuantity) {
        }

        public void clear() {
                items.clear();
        }

        public ArrayList<Article> getItems() {
                return items;
        }

}
