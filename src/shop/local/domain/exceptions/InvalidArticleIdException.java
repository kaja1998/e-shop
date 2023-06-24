package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class InvalidArticleIdException extends Exception {
    public InvalidArticleIdException(String message) {
        super(message);
    }
}
