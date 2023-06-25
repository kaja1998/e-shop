package shop.local.domain.exceptions;

import shop.local.entities.User;

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
        super("Error occurred while registering user with the username " + user.getUsername() + additionalMessage);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
