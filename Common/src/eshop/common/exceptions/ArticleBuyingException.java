package eshop.common.exceptions;

/**
 * Exception when all articles in the shopping cart are no longer available and can't be purchased.
 * @author Sund
 */
@SuppressWarnings("serial")
public class ArticleBuyingException extends Exception {

    public ArticleBuyingException(String additionalMessage) {
        super(buildErrorMessage(additionalMessage));
    }

    private static String buildErrorMessage(String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Error occurred while purchasing. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }
}
