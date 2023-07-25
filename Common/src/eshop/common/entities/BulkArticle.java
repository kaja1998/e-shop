package eshop.common.entities;

/**
 * Class for representing bulk articles.
 * @author Sund
 */

public class BulkArticle extends Article {
    private int packSize;

    //Wenn ein neuer BulkArticle angelegt wird
    public BulkArticle(String articleTitle, int quantityInStock, double price, int packSize) {
        super(articleTitle, quantityInStock, price);
        this.packSize = packSize;
    }

    //Wenn er aus der Datei gelesen wird
    public BulkArticle(int number, String articleTitle, int quantityInStock, double price, int packSize, ArticleStatus status) {
        super(number, articleTitle, quantityInStock, price, status);
        this.packSize = packSize;
    }

    public int getPackSize() {
        return packSize;
    }

    @Override
    public String toString() {
        return super.toString() + " / Pack Size " + packSize;
    }
}