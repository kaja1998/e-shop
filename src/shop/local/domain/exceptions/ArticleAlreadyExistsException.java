package shop.local.domain.exceptions;

import shop.local.entities.Article;

/**
 * Exception to show that an article already exists
 * @author Sund
 */
@SuppressWarnings("serial")
public class ArticleAlreadyExistsException extends Exception {

	private Article article;

	public ArticleAlreadyExistsException(Article newArticle, Article conflictingArticle, String additionalMessage) {
		super(buildErrorMessage(newArticle, conflictingArticle, additionalMessage));
		this.article = newArticle;
	}

	private static String buildErrorMessage(Article article,  Article conflictingArticle, String additionalMessage) {
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Article " + article.getArticleTitle() + " conflicts with article number " + conflictingArticle.getNumber() + " .");
		if (additionalMessage != null) {
			errorMessage.append(additionalMessage);
		}
		return errorMessage.toString();
	}

	public Article getArticle() {
		return article;
	}
}
