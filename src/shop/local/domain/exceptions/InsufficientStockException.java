package shop.local.domain.exceptions;

/**
 * Exception when a customer wants to add an article to his shoppingCart in an insufficient quantity or wants to change the quanity in his cart
 * @author Sund
 */
public class InsufficientStockException extends Throwable {

    private int availableQuantity;

    public InsufficientStockException(int availableQuantity, String additionalMessage) {
        super(buildErrorMessage(availableQuantity, additionalMessage));
        this.availableQuantity = availableQuantity;
    }

    private static String buildErrorMessage(int availableQuantity, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Insufficient stock for article. Available quantity: ").append(availableQuantity).append(". ");
        if (additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }
}
