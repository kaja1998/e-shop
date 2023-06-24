package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class CustomNullPointerException extends NullPointerException {
    /**
     * Constructor
     *
     * @param message additional text for the error message
     */
    public CustomNullPointerException(String message) {
        super(message);
    }
}
