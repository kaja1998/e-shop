package shop.local.domain;

import java.io.IOException;

import shop.local.domain.exceptions.*;
import shop.local.entities.*;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import java.util.ArrayList;

/**
 * Class for article administration
 * 
 * @author Sund
 */
public class ArticleAdministration {

	private ArrayList<Article> articles;

	// Persistence api, responsible for the implementation of the file access
	private PersistenceManager persistenceManager;

	private EventAdministration eventAdministration;

	public ArticleAdministration() {
		this.articles = new ArrayList<>();
		this.persistenceManager = new FilePersistenceManager();
	}

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
			// Wenn der Artikel bereits existiert, wird eine ArticleAlreadyExistsException ausgelöst
			throw new ArticleAlreadyExistsException(article, null);
		}
		// Wenn der Artikel nicht im Artikelbestand vorhanden ist, wird er der ArrayList hinzugefügt
		articles.add(article);
	}

	public void writeData(String file) throws IOException {
		// Open persistence manager for writes
		persistenceManager.openForWriting(file);
		persistenceManager.writeArticles(this.articles);

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

	public ArrayList<Article> searchArticle(String title) throws ArticleNotFoundException {
		ArrayList<Article> searchResult = new ArrayList<>();
		for (Article currentArticle : articles) {
			if (currentArticle.getArticleTitle().equals(title)) {
				searchResult.add(currentArticle);
			}
		}

		if(searchResult.size() < 1) {
			throw new ArticleNotFoundException(title, "Please enter article title of an article which actually exists.");
		}

		return searchResult;
	}

	public Article searchByArticleNumber(int articleNumber) throws ArticleNotFoundException {
		Article searchResult = null;
		for (Article currentArticle : articles) {
			if (currentArticle.getNumber() == articleNumber) {
				searchResult = currentArticle;
				break;
			}
		}
		if(searchResult == null) {
			throw new ArticleNotFoundException(articleNumber, "Please enter article number which actually exists.");
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

	public void setEventAdministration(EventAdministration eventadministration) {
		this.eventAdministration = eventadministration;
	}

	public void increaseArticleStock(Article article, int quantityToAdd, String file) throws IOException {
		article.increaseStock(quantityToAdd);
		writeData(file);
	}

	public boolean decreaseArticleStock(Article article, int quantityToRetrieve, String file) throws IOException, StockDecreaseException {
		boolean success = article.decreaseStock(quantityToRetrieve);
		if (success) {
			writeData(file);
			return true;
		}
		return false;
	}

	public boolean decreaseArticleStockWhileBuy(Article article, int quantityToRetrieve, String file) throws IOException {
		boolean success = article.decreaseStockWhileBuy(quantityToRetrieve);
		if (success) {
			writeData(file);
			return true;
		}
		return false;
	}

	public ArrayList<Article> getArticleStock() {
		return articles;
	}

	public Invoice buyArticles(ShoppingCart shoppingCart, User user) throws IOException, EmptyCartException, ArticleBuyingException {
		// Object for the invoice
		Invoice invoice = new Invoice();

		// Go through all items in the cart
		if (shoppingCart != null && shoppingCart.getCartItems() != null && shoppingCart.getCartItems().size()>0) {
			for (ShoppingCartItem item : shoppingCart.getCartItems()) {
				// check which article is in the cart and what quantity should be purchased
				Article article = item.getArticle();

				int quantity = item.getQuantity();

				// try to take articles stock and check if successful
				boolean success = decreaseArticleStockWhileBuy(article, quantity, "ESHOP_Article.txt");

				// add item to invoice
				if (success) {
					invoice.addPosition(item);
					Event event = new Event(Event.EventType.KAUF, article, quantity, user);
					// Ereignis für die Auslagerung in ArrayList schreiben
					eventAdministration.addEvent(event);
				} else {
					invoice.addUnavailableItems(item);
				}
			}
			if (invoice.getUnavailableItems().size() >= shoppingCart.getCartItems().size()) {
				throw new ArticleBuyingException("All articles became unavailable. ");
			}
		} else {
			throw new EmptyCartException(null);
		}

		// empty cart
		shoppingCart.deleteAllArticlesInCart();

		// Ereignis für die Einlagerung in File schreiben
		eventAdministration.writeData("ESHOP_Events.txt");

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

//	public void addArticle(Article article, String articleTitle, String articleType, int initialQuantity, double price,
//			int packSize) throws IOException {
//		if (articleType.equalsIgnoreCase("bulk")) {
//			// Lese Packungsgröße
//			// Erstelle Massengutartikel
//			article = new BulkArticle(articleTitle, initialQuantity, price, packSize);
//		} else {
//			// Erstelle Einzelartikel
//			article = new Article(articleTitle, initialQuantity, price);
//		}
//	}

}
