package shop.local.domain.exceptions;

import shop.local.entities.ShoppingCartItem;

import java.util.List;

@SuppressWarnings("serial")
public class ArticleBuyingException extends Exception {

    private final List<ShoppingCartItem> shoppingCartItem;

    /**
     * Constructor
     *
     * @param shoppingCartItems the already existing articles
     * @param additionalMessage additional text for the error message
     */
    public ArticleBuyingException(List<ShoppingCartItem> shoppingCartItems, String additionalMessage) {
        super(buildErrorMessage(shoppingCartItems, additionalMessage));
        this.shoppingCartItem = shoppingCartItems;
    }

    private static String buildErrorMessage(List<ShoppingCartItem> shoppingCartItems, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        for (ShoppingCartItem shoppingCartItem: shoppingCartItems) {
            errorMessage.append("Error occurred while purchasing ").append(shoppingCartItem.getArticle().getArticleTitle()).append(" with number ").append(shoppingCartItem.getArticle().getNumber());
        }
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItem;
    }
}
