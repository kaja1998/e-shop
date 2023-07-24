package shop.local.domain.exceptions;

/**
 * Exception when an Employee wants to change an article's quantity but the quantity can't be retrieved
 * @author Sund
 */
public class StockDecreaseException extends Exception{

    public StockDecreaseException(String additionalMessage) {
        super(buildErrorMessage(additionalMessage));
    }

    private static String buildErrorMessage(String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Stock could not be decreased. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }

}
