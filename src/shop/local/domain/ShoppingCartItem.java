package shop.local.domain;

import shop.local.entities.Article;

public class ShoppingCartItem {

    private Article article;

    private int quantity;

    public ShoppingCartItem(Article article, int quantity) {
        this.article = article;
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
