package shop.local.domain.exceptions;

import shop.local.entities.Article;

/**
 * Exception to show that an article already exists
 * @author Sund
 */
@SuppressWarnings("serial")
public class ArticleAlreadyExistsException extends Exception {

	private Article article;
	
	/**
	 * Konstruktor
	 * 
	 * @param article the already existing article
	 * @param additionalMessage additional text for the error message
	 */
	public ArticleAlreadyExistsException(Article article, String additionalMessage) {
		super(buildErrorMessage(article, additionalMessage));
		this.article = article;
	}

	private static String buildErrorMessage(Article article, String additionalMessage) {
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Article with the name " + article.getArticleTitle() + " and number " +article.getNumber() + " already exists. ");
		if (additionalMessage != null) {
			errorMessage.append(additionalMessage);
		}
		return errorMessage.toString();
	}




	public Article getArticle() {
		return article;
	}
}
