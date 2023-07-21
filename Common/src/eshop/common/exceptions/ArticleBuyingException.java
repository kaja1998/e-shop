package eshop.common.exceptions;

@SuppressWarnings("serial")
public class ArticleBuyingException extends Exception {

    /**
     * Constructor
     * @param additionalMessage additional text for the error message
     */
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
