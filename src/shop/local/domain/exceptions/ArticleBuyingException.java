package shop.local.domain.exceptions;

import shop.local.entities.Article;

@SuppressWarnings("serial")
public class ArticleBuyingException extends Exception {
    private Article article;

    /**
     * Constructor
     *
     * @param article           the already existing article
     * @param additionalMessage additional text for the error message
     */
    public ArticleBuyingException(Article article, String additionalMessage) {
        super("Error occurred while purchasing " + article.getArticleTitle() + " with number " + article.getNumber() + additionalMessage);
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
