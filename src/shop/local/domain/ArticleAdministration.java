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
	public void writeData(String file) throws IOException  {
		// Open persistence manager for writes
		persistenceManager.openForWriting(file);

		//Iterate through the ItemList and call saveItem() to write each item in the list to the file.
		ArticleList liste = articleStock;
		while (liste != null) {
			Article article = liste.getFirstArticle();
			if (article != null) {
				// Save
				persistenceManager.saveArticle(article);
			}
			liste = liste.getRemainingArticles();
		}

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
