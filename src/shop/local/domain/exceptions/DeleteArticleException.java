package shop.local.domain.exceptions;

import shop.local.entities.Article;

@SuppressWarnings("serial")
public class DeleteArticleException extends Exception {
    private Article article;

    /**
     * Constructor
     *
     * @param article           the already existing article
     * @param additionalMessage additional text for the error message
     */
    public DeleteArticleException(Article article, String additionalMessage) {
        super("Error occurred while deleting " + article.getArticleTitle() + " with number " + article.getNumber() + additionalMessage);
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
