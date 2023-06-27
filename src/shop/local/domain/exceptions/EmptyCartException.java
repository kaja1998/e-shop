package shop.local.domain.exceptions;

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
