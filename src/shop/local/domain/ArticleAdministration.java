package shop.local.domain;

import java.io.IOException;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import shop.local.entities.Article;
import shop.local.entities.ArticleList;


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
		persistenceManager.saveArticle(article, this.articleStock);

		// Close the persistence interface again
		persistenceManager.close();
	}

	/**
	 * Method that inserts an item at the end of the item list.
	 * 
	 * @param article the item to be inserted
	 * @throws ArticleAlreadyExistsException if article already exists
	 */
	public void insert(Article article) throws ArticleAlreadyExistsException {
		if (articleStock.contains(article)) {
			throw new ArticleAlreadyExistsException(article, " - in 'insert()'");
		}
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
	public void increaseArticleStock(Article article, int quantityToAdd) throws IOException {
		article.increaseStock(quantityToAdd);
	}

	/**
	 * Method that decreases an articles' stock
	 *
	 * @param article the article whose stock should be decreased
	 * @param quantityToRetrieve number of articles that are to be retrieved from stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public boolean decreaseArticleStock(Article article, int quantityToRetrieve) throws IOException {
		return article.decreaseStock(quantityToRetrieve);
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

	// TODO: More methods, e.g. for reading and removing items
	// ...
}
