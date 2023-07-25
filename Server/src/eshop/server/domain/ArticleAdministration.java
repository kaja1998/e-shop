package eshop.server.domain;

import java.io.IOException;

import eshop.common.entities.*;
import eshop.common.exceptions.*;
import eshop.server.persistence.FilePersistenceManager;
import eshop.server.persistence.PersistenceManager;

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
		Article conflictingArticle;
		for (Article articleInList : articles) {
			if(articleInList.equals(article)) {
				conflictingArticle = articleInList;
				throw new ArticleAlreadyExistsException(article, conflictingArticle, null);
			}
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

	public void delete(Article article) {
		int index = 0;
		for (Article articleInList : articles) {
			if(articleInList.getNumber() == article.getNumber()) {
				articleInList.setQuantityInStock(0);
				articleInList.setStatus(ArticleStatus.INACTIVE);
				articleInList.setInStock(false);
				articles.set(index, articleInList);
			}
			index++;
		}
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
		if (quantityToAdd > 0){
			article.setStatus(ArticleStatus.valueOf("ACTIVE"));
		}
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
					Event event = new Event(Event.EventType.KAUF, article, -quantity, user);
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

}
