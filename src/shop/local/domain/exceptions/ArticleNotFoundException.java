package shop.local.domain.exceptions;

import shop.local.entities.Article;

@SuppressWarnings("serial")
public class ArticleNotFoundException extends Exception {
    private Article article;

    /**
     * Constructor
     *
     * @param article           the article that was not found
     * @param additionalMessage additional text for the error message
     */
    public ArticleNotFoundException(Article article, String additionalMessage) {
        super("Article with the name " + article.getArticleTitle() + " and number " + article.getNumber() + " does not exist" + additionalMessage);
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
