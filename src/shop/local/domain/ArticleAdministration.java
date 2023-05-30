package shop.local.domain;

import java.io.IOException;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.*;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;


/**
 * Class for article administration
 * 
 * @author teschke
 */
public class ArticleAdministration {

	// Administration of articles in a linked list
	private ArticleList articleStock = new ArticleList();

	// Persistence api, responsible for the implementation of the file access
	private PersistenceManager persistenceManager = new FilePersistenceManager();
	
	/**
	 * Method for reading article data from a file
	 * 
	 * @param file File containing article data to be read
	 * @throws IOException
	 */
	public void readData(String file) throws IOException {
		// open PersistenceManager for reading access
		persistenceManager.openForReading(file);

		Article article;
		do {
			// Read article object
			article = persistenceManager.readArticle();
			if (article != null) {
				// Add article to list
				try {
					insert(article);
				} catch (ArticleAlreadyExistsException e1) {
					// Should not occur here
					// that's why the error won't be handled here
				}
			}
		} while (article != null);

		// Close persistence api
		persistenceManager.close();
	}

	/**
	 * Method to write the item data to a file.
	 *
	 * @param file File to write item inventory to
	 * @throws IOException
	 */
	public void writeData(String file, Article article) throws IOException  {
		// Open persistence manager for writes
		persistenceManager.openForWriting(file);
		persistenceManager.addArticle(article, this.articleStock);

		// Close the persistence interface again
		persistenceManager.close();
	}

	/**
	 * Method to write the item data to a file.
	 *
	 * @param file File to write item inventory to
	 * @throws IOException
	 */
	public void writeDataToRemoveArticle(String file, Article article) throws IOException  {
		// Open persistence manager for writes
		persistenceManager.openForWriting(file);
		persistenceManager.deleteArticle(article, this.articleStock);

		// Close the persistence interface again
		persistenceManager.close();
	}

	/**
	 * Method that inserts an item at the end of the item list.
	 * 
	 * @param article the item to be inserted
	 * @throws ArticleAlreadyExistsException if article already exists
	 */

	//Die Methode überprüft, ob der Artikel bereits im Artikelbestand vorhanden ist,
	//indem sie die contains-Methode auf dem articleStock-Objekt aufruft.
	//articleStock ist dabei eine Liste, die die Artikel enthält.
	public void insert(Article article) throws ArticleAlreadyExistsException {
		if (articleStock.contains(article)) {
			//Wenn der Artikel bereits existiert, wird eine ArticleAlreadyExistsException ausgelöst
			throw new ArticleAlreadyExistsException(article, " - in 'insert()'");
		}
		//Wenn der Artikel nicht im Artikelbestand vorhanden ist, wird er mithilfe der insert-Methode dem Artikelbestand hinzugefügt
		articleStock.insert(article);
	}

	/**
	 * Method of deleting an item from inventory.
	 * 
	 * @param article the item to be deleted
	 */
	public void delete(Article article) {
		articleStock = articleStock.delete(article);
	}

	/**
	 * Method that searches for articles by title. There will be a list of items
	 * returned, which contains all articles with an exact matching title.
	 * 
	 * @param title Title of the article we're looking for
	 * @return List of articles with searched title (may be empty)
	 */
	public ArticleList searchArticle(String title) {
		ArticleList searchResult = new ArticleList();
		ArticleList currentArticleList = articleStock;
		while (currentArticleList != null) {
			Article currentArticle = currentArticleList.getFirstArticle();
			if (currentArticle.getArticleTitle().equals(title)) {
				// Enter found item in search result
				searchResult.insert(currentArticle);
			}
			currentArticleList = currentArticleList.getRemainingArticles();
		}
		return searchResult;
	}

	/**
	 * Method that searches for articles by articleNumber. There will be one or none article returned.
	 *
	 * @param articleNumber Number of the article we're looking for
	 * @return Article with searched articleNumber (may be empty)
	 */
	public Article searchByArticleNumber(int articleNumber) {
		//Es gibt einen Variable "searchResult" vom Typ Article, welche zunächst auf "null" gesetzt ist
		Article searchResult = null;
		//Artikelliste in Variable speichern
		ArticleList currentArticleList = articleStock;
		//Dann wird eine Schleife ausgeführt, um durch die Artikel in der Liste zu iterieren
		while (articleStock != null) {
			Article currentArticle = currentArticleList.getFirstArticle();
			//In jeder Iteration wird der erste Artikel in der aktuellen Artikel-Liste "currentArticleList" ermittelt und mit der gesuchten Artikelnummer verglichen
			if (currentArticle.getNumber() == articleNumber) {
				// Enter found item in search result
				//Wenn die Nummer übereinstimmt, wird der Artikel in die Variable "searchResult" gespeichert und die Schleife wird mit break beendet
				searchResult = currentArticle;
				break;
			}
			//Andernfalls wird der nächste Artikel in der Liste durch Festlegen der Variable "currentArticleList" auf die Restliste erhalten und die Schleife wird fortgesetzt
			currentArticleList = currentArticleList.getRemainingArticles();
		}
		//Wenn die Schleife beendet wird, gibt die Methode "searchResult" zurück, die entweder null ist, wenn kein Artikel gefunden wurde, oder den Artikel
		return searchResult;
	}

	/**
	 * Method that increases an articles' stock
	 *
	 * @param article the article whose stock should be increased
	 * @param quantityToAdd number of articles that are to be added to stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public void increaseArticleStock(Article article, int quantityToAdd, String file) throws IOException {
		article.increaseStock(quantityToAdd);
		writeData(file, article);
	}

	/**
	 * Method that decreases an articles' stock
	 *
	 * @param article the article whose stock should be decreased
	 * @param quantityToRetrieve number of articles that are to be retrieved from stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public boolean decreaseArticleStock(Article article, int quantityToRetrieve, String file) throws IOException {
		boolean success = article.decreaseStock(quantityToRetrieve);
		if(success) {
			writeData(file, article);
			return true;
		}
		return false;
	}

	/**
	 * Method that returns a COPY of the item inventory.
	 * (A copy is a good idea if I send the recipient
	 * the data does not want to enable the original data
	 * to manipulate.)
	 * BUT ATTENTION: Those referenced in the copied article list
	 * have not been copied, i.e. original
	 * Article list and its copy refer to the same
	 * Article objects. Actually, the individual article objects would have to
	 * also be copied.
	 *
	 * @return list of all articles in article stock (copy)
	 */
	public ArticleList getArticleStock() {
		return new ArticleList(articleStock);
	}

	public Invoice buyArticles(ShoppingCart shoppingCart) throws IOException {
		// Object for the invoice
		Invoice invoice =  new Invoice();

		// Go through all items in the cart
		for (ShoppingCartItem item : shoppingCart.getCartItems()) {
			// check which article is in the cart and what quantity should be purchased
			Article article = item.getArticle();
			int quantity = item.getQuantity();

			// try to take articles stock and check if successful
			boolean success = decreaseArticleStock(article, quantity, "ESHOP_Article.txt");

			// add item to invoice
			if(success) {
				invoice.addPosition(item);
			} else {
				invoice.addUnavailableItems(item);
			}
		}

		// empty cart
		shoppingCart.deleteAll();

		return invoice;
	}

	public Article getArticleByID(int id){
		return null;
	}

	// TODO: More methods, e.g. for reading and removing items
	// ...
}
