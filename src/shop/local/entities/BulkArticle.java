package shop.local.entities;

import shop.local.domain.exceptions.StockDecreaseException;

/**
 * Class for representing bulk articles.
 * @author Sund
 */

public class BulkArticle extends Article {
    private int packSize;

    public BulkArticle(String articleTitle, int quantityInStock, double price, int packSize) {
        super(articleTitle, quantityInStock, price);
        this.packSize = packSize;
    }

    public BulkArticle(int number, String articleTitle, int quantityInStock, double price, int packSize) {
        super(number, articleTitle, quantityInStock, price);
        this.packSize = packSize;
    }

    public int getPackSize() {
        return packSize;
    }

//    @Override
//    public void setQuantityInStock(int quantityInStock) {
//        if (quantityInStock % packSize != 0) {
//            throw new IllegalArgumentException("Quantity must be a multiple of pack size.");
//        }
//        super.setQuantityInStock(quantityInStock);
//    }
//
//    @Override
//    public boolean decreaseStock(int quantityToRetrieve) throws StockDecreaseException {
//        if (quantityToRetrieve % packSize != 0) {
//            return false;
//        }
//        return super.decreaseStock(quantityToRetrieve);
//    }

    @Override
    public String toString() {
        return super.toString() + " / Pack Size " + packSize;
    }
}