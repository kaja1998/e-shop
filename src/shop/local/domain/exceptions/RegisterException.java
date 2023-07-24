package shop.local.domain.exceptions;
import shop.local.entities.User;

/**
 * Exception when a User wants to registrate himself, but the user already exists.
 * @author Sund
 */
@SuppressWarnings("serial")
public class RegisterException extends Exception {
    private User user;

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
