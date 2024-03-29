package shop.local.domain.exceptions;

import shop.local.entities.Article;

/**
 * Exception when an article gets added to the shopping cart in an inappropriate quantity because it is a bulkArticle
 * Or when a customer wants to chance the quantity of an BulkArticle and doesn't remember the packSize etc.
 * @author Sund
 */
public class BulkArticleException extends Throwable {

    private Article article;
    private int packSize;

    public BulkArticleException(Article article, int packSize, String additionalMessage) {
        super(buildErrorMessage(article, packSize, additionalMessage));
        this.article = article;
        this.packSize = packSize;
    }

    private static String buildErrorMessage(Article article, int packSize, String additionalMessage) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Article with the name ").append(article.getArticleTitle()).append(" and number ").append(article.getNumber()).append(" can only be purchased in packs of ").append(packSize).append(". ");
        if (additionalMessage != null) {
            errorMessage.append(additionalMessage);
        }
        return errorMessage.toString();
    }

    // Getter for article
    public Article getArticle() {
        return article;
    }

    // Getter for packSize
    public int getPackSize() {
        return packSize;
    }

}
