package shop.local.domain;
import java.io.IOException;
import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.*;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import java.util.ArrayList;

	/**
	 * Class for article administration
	 * @author Sund
	 */
public class ArticleAdministration {

	private ArrayList<Article> articles = new ArrayList<>();

	// Persistence api, responsible for the implementation of the file access
	private PersistenceManager persistenceManager = new FilePersistenceManager();


	public void readData(String file) throws IOException {
		// open PersistenceManager for reading access
		persistenceManager.openForReading(file);

		Article article;
		do {
			// Read article object
			article = persistenceManager.readArticle();
			if (article != null) {
				try {
					insertArticle(article);
				} catch (ArticleAlreadyExistsException e) {
					throw new RuntimeException(e);
				}
			}
		} while (article != null);

		// Close persistence api
		persistenceManager.close();
	}

	public void insertArticle(Article article) throws ArticleAlreadyExistsException {
		if (articles.contains(article)) {
			//Wenn der Artikel bereits existiert, wird eine ArticleAlreadyExistsException ausgelöst
			throw new ArticleAlreadyExistsException(article, " - in 'insert()'");
		}
		//Wenn der Artikel nicht im Artikelbestand vorhanden ist, wird er der ArrayList hinzugefügt
		articles.add(article);
	}

	public void writeData(String file, Article article) throws IOException {
		// Open persistence manager for writes
		persistenceManager.openForWriting(file);
		persistenceManager.addArticles(article, this.articles);

		// Close the persistence interface again
		persistenceManager.close();
	}

	public void writeDataToRemoveArticle(String file, Article article) throws IOException {
		// Open persistence manager for writes
		persistenceManager.openForWriting(file);
		persistenceManager.deleteArticle(article, this.articles);

		// Close the persistence interface again
		persistenceManager.close();
	}

	public void delete(Article article) {
		articles.remove(article);
	}

	public ArrayList<Article> searchArticle(String title) {
		ArrayList<Article> searchResult = new ArrayList<>();
		for (Article currentArticle : articles) {
			if (currentArticle.getArticleTitle().equals(title)) {
				searchResult.add(currentArticle);
			}
		}
		return searchResult;
	}

	public Article searchByArticleNumber(int articleNumber) {
		Article searchResult = null;
		for (Article currentArticle : articles) {
			if (currentArticle.getNumber() == articleNumber) {
				searchResult = currentArticle;
				break;
			}
		}
		return searchResult;
	}


	public Article getArticleByID(int articleID) {
		for (Article currentArticle : articles) {
			if (currentArticle.getNumber() == articleID) {
				return currentArticle;
			}
		}
		return null; // Article not found
	}


	public void increaseArticleStock(Article article, int quantityToAdd, String file) throws IOException {
		article.increaseStock(quantityToAdd);
		writeData(file, article);
	}


	public boolean decreaseArticleStock(Article article, int quantityToRetrieve, String file) throws IOException {
		boolean success = article.decreaseStock(quantityToRetrieve);
		if (success) {
			writeData(file, article);
			return true;
		}
		return false;
	}

	public ArrayList<Article> getArticleStock() {
		return articles;
	}

	public Invoice buyArticles(ShoppingCart shoppingCart) throws IOException {
		// Object for the invoice
		Invoice invoice = new Invoice();

		// Go through all items in the cart
		for (ShoppingCartItem item : shoppingCart.getCartItems()) {
			// check which article is in the cart and what quantity should be purchased
			Article article = item.getArticle();
			int quantity = item.getQuantity();

			// try to take articles stock and check if successful
			boolean success = decreaseArticleStock(article, quantity, "ESHOP_Article.txt");

			// add item to invoice
			if (success) {
				invoice.addPosition(item);
			} else {
				invoice.addUnavailableItems(item);
			}
		}
		// empty cart
		shoppingCart.deleteAll();
		return invoice;
	}


	public String toString() {
		String result = "";
		String endOfLine = System.getProperty("line.separator");

		if (articles.isEmpty()) {
			return "List is empty." + endOfLine;
		} else {
			for (Article article : articles) {
				result += article + endOfLine;
			}
		}
		return result;
	}



}

