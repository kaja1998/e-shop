package shop.local.domain.exceptions;

/**
 * Exception when an article doesn't exist in the shop.
 * @author Sund
 */
@SuppressWarnings("serial")
public class ArticleNotFoundException extends Exception {

    private String title;
    private int articleNumber;


    public ArticleNotFoundException(String title, String additionalMessage) {
        super(buildErrorMessage(title, additionalMessage));
        this.title = title;
    }

    private static String buildErrorMessage(String title, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Article with the name " +  title + " does not exist. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }

    public ArticleNotFoundException(int articleNumber, String additionalMessage) {
        super(buildErrorMessage(articleNumber, additionalMessage));
        this.articleNumber = articleNumber;
    }

    private static String buildErrorMessage(int articleNumber, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Article with the number " +  articleNumber + " does not exist. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }



}
