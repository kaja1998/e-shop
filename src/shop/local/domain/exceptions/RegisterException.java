package shop.local.domain.exceptions;

import shop.local.entities.ShoppingCartItem;
import shop.local.entities.User;

import java.util.List;

@SuppressWarnings("serial")
public class RegisterException extends Exception {
    private User user;

    /**
     * Constructor
     *
     * @param user           the user that was not found
     * @param additionalMessage additional text for the error message
     */
    public RegisterException(User user, String additionalMessage) {
        super(buildErrorMessage(user, additionalMessage));
        this.user = user;
    }

    private static String buildErrorMessage(User user, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error occurred while registering user with the username " + user.getUsername() + ". ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }

    public User getUser() {
        return user;
    }
}
