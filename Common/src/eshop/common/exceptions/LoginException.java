package eshop.common.exceptions;

/**
 * Exception when a User wants to loggin but the username or password is incorrect.
 * @author Sund
 */
@SuppressWarnings("serial")
public class LoginException extends Exception {

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
