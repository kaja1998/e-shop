package shop.local.domain.exceptions;

@SuppressWarnings("serial")
public class ArticleNotFoundException extends Exception {

    private String title;
    private int articleNumber;

    /**
     * Constructor
     *
     * @param title           the article that was not found
     * @param additionalMessage additional text for the error message
     */
    public ArticleNotFoundException(String title, String additionalMessage) {
        super("Article with the name " +  title + " does not exist" + additionalMessage);
        this.title = title;
    }

    /**
     * Constructor
     *
     * @param articleNumber the article that was not found
     * @param additionalMessage additional text for the error message
     */
    public ArticleNotFoundException(int articleNumber, String additionalMessage) {
        super("Article with the number " +  articleNumber + " does not exist" + additionalMessage);
        this.articleNumber = articleNumber;
    }

    public String getTitle() {
        return title;
    }
}
