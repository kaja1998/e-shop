package eshop.common.exceptions;

/**
 * Exception when a customer wants to clear the cart but the cart has no cart items in it.
 * @author Sund
 */
public class EmptyCartException extends Exception {

    public EmptyCartException(String additionalMessage) {
        super(buildErrorMessage(additionalMessage));
    }

    private static String buildErrorMessage(String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Shopping cart is empty. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }
}
