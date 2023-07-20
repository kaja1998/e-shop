package eshop.common.entities;

/**
 *  Class for representing shoppingCartItems.
 * @author Sund
 */

public class ShoppingCartItem {

    private Article article;

    private int quantity;

    //Der Konstruktor initialisiert ein ShoppingCartItem-Objekt mit einem Article-Objekt und der entsprechenden Menge.
    public ShoppingCartItem(Article article, int quantity) {
        this.article = article;
        this.quantity = quantity;;
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

    public String toString() {
        return (quantity + "x " + article.getNumber() + " (" + article.getArticleTitle() + ")" + " " + article.getPrice() + " EUR");
    }

}
