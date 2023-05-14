package shop.local.entities;

/**
 * (Not quite) abstract data type for managing items in a list.
 * The list is recursive: It consists of an item (item) and a
 * (Remaining) list (next).
 *
 * @author Sun
 * @version 1 (linked list)
 */
public class ArticleList {

	// das Listenelement
	private Article article = null;

	// reference to rest of list.
	// The rest of the list is an ItemList again,
	// where the one element (namely the above article) is shorter!
	private ArticleList next = null;

	/**
	 * Default constructor
	 * (required because second constructor exists)
	 */
	public ArticleList() {
		// empty default constructor
	}

	/**
	 * Copy constructor:
	 * Creates a new item list as a copy of another list.
	 * Important: only the ItemList object with its links will be used
	 * copied; the article objects contained are not copied.
	 */
	public ArticleList(ArticleList original) {
		while (original != null) {
			Article article = original.getFirstArticle();
			if (article != null) {
				this.insert(article);
				original = original.getRemainingArticles();
			} 
		}
	}

	/**
	 * Method that returns the first item in the list.
	 */
	public Article getFirstArticle() {
		return article;
	}

	/**
	 * Method that returns the list of remaining items (i.e. not including the first item).
	 */
	public ArticleList getRemainingArticles() {
		return next;
	}

	/**
	 * Method that inserts an item at the end of the item list.
	 *
	 * @param article the article to insert
	 */
	public void insert(Article article) {
		// List still empty, i.e. no first article?
		if (this.article == null) {
			this.article = article;
		}
		else {
			// Are we at the end of the list?
			if (next == null) {
				// Yes: then create a new remainder list with an item as an element
				next = new ArticleList();			//Object Next is created and a default/empty constructor
			}
			// Insert item into existing remaining list (recursive call!)
			next.insert(article);				// Object next calls method again and passes "article" as parameter
		}
	}

	/**
	 * Method that checks if an item already exists in the item list.
	 *
	 * @param otherArticle the article you are looking for
	 */
	public boolean contains(Article otherArticle) {
		if (this.article != null && this.article.equals(otherArticle)) {
			return true;
		} else {
			if (this.next != null) {
				return this.next.contains(otherArticle);
			}
		}
		return false;
	}

	/**
	 * Method that returns an ArticleList without the passed item.
	 *
	 * @param articleToDelete the article to delete
	 */
	public ArticleList delete(Article articleToDelete) {
		if (this.article == null) {
			return this;
		}

		if (this.article.equals(articleToDelete)) {
			return next;
		} else {
			if (next != null) {
				next = next.delete(articleToDelete);
			}
			return this;
		}
	}

	/**
	 * Overridden Object's default method.
	 * Method is called automatically whenever an ItemList object is sent as a String
	 * is used (e.g. in println(...);)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "";
		// The line break is carried through on different operating systems
		// different character strings (so-called escape sequences) reached,
		// under Windows e.g. "\n"
		String endOfLine = System.getProperty("line.separator");
		ArticleList currentArticleList = this;
		if (currentArticleList.article == null)
			return "List is empty." + endOfLine;
		else {
			while (currentArticleList != null) {
				result += currentArticleList.article + endOfLine;
				currentArticleList = currentArticleList.next;
			}
		}
		return result;
	}
}
