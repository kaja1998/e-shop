package eshop.common.exceptions;

@SuppressWarnings("serial")
public class LoginException extends Exception {

    /**
     * Constructor
     *
     * @param additionalMessage additional text for the error message
     */
    public LoginException(String additionalMessage) {
        super(buildErrorMessage(additionalMessage));
    }

    private static String buildErrorMessage(String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Incorrect Username or password. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }
}
