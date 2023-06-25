package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class LoginException extends Exception {
    private String username;

    /**
     * Constructor
     *
     * @param username         the username was not found
     * @param additionalMessage additional text for the error message
     */
    public LoginException(String username, String additionalMessage) {
        super("User with the username " + username + " does not exist" + additionalMessage);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
