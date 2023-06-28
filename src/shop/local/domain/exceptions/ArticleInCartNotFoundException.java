package shop.local.domain.exceptions;

import shop.local.entities.Article;

public class ArticleInCartNotFoundException extends Exception {

    private Article article;

    public ArticleInCartNotFoundException(Article article, String additionalMessage) {
        super(buildErrorMessage(article, additionalMessage));
        this.article = article;
    }

    private static String buildErrorMessage(Article article, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Article with the name ").append(article.getArticleTitle()).append(" and number ").append(article.getNumber()).append(" is not in your cart. ");
        if(additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }
}
