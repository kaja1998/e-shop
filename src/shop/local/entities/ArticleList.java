package shop.local.entities;

/**
 * (Not quite) abstract data type for managing items in a list.
 * The list is recursive: It consists of an item (item) and a
 * (Remaining) list (next).
 *
 * @author Sund
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
	 *Die Methode fügt einen Artikel in eine verkettete Liste von Artikeln ein
	 * @param article the article to insert
	 */
	public void insert(Article article) {
		// List still empty, i.e. no first article?
		//Zunächst wird überprüft, ob die Liste noch leer ist, d.h. ob es sich um den ersten Artikel handelt
		//Wenn ja, wird der übergebene Artikel einfach als erster Artikel (this.article) in der Liste gespeichert.
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
		//Zuerst wird überprüft, ob das aktuelle Element (this.article) nicht null ist
		//und ob es mit dem otherArticle übereinstimmt, indem die equals-Methode des Artikels aufgerufen wird.
		if (this.article != null && this.article.equals(otherArticle)) {
			return true;
		//Wenn das aktuelle Element nicht mit dem gesuchten Artikel übereinstimmt, wird überprüft, ob es ein nächstes Element (next) gibt.
		//Wenn ja, wird die contains-Methode rekursiv für das nächste Element aufgerufen, um zu prüfen,
		//ob der Artikel in der restlichen verketteten Liste enthalten ist.
		} else {
			if (this.next != null) {
				return this.next.contains(otherArticle);
			}
		}
		//Wenn es kein nächstes Element gibt oder das aktuelle Element null ist, wird false zurückgegeben,
		//da der gesuchte Artikel nicht in der Liste gefunden wurde.
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
		//Leere Zeichenkette result wird initialisiert, die den endgültigen Text repräsentieren wird.
		String result = "";
		//Die Zeichenfolge endOfLine wird verwendet, um einen Zeilenumbruch zu erzeugen, der auf verschiedenen Betriebssystemen funktioniert.
		//Sie wird mithilfe der Methode System.getProperty("line.separator") abgerufen.
		String endOfLine = System.getProperty("line.separator");
		//Die Variable currentArticleList wird mit dem aktuellen Objekt (this) initialisiert.
		//Dadurch beginnt der Durchlauf der verketteten Liste am Anfang.
		ArticleList currentArticleList = this;
		//Es wird überprüft, ob die Liste leer ist, indem geprüft wird, ob das aktuelle Element (currentArticleList.article) null ist.
		if (currentArticleList.article == null)
			//Wenn dies der Fall ist, wird eine entsprechende Meldung zurückgegeben, die angibt, dass die Liste leer ist.
			return "List is empty." + endOfLine;
		//Wenn die Liste nicht leer ist, wird eine Schleife gestartet, die durch die verkettete Liste iteriert.
		else {
			while (currentArticleList != null) {
				//In jeder Iteration wird der aktuelle Artikel (currentArticleList.article) zur Zeichenkette result hinzugefügt,
				//gefolgt von endOfLine für den Zeilenumbruch.
				result += currentArticleList.article + endOfLine;
				//Dann wird das aktuelle Element auf das nächste Element (currentArticleList.next) gesetzt, um zur nächsten Iteration fortzufahren.
				currentArticleList = currentArticleList.next;
			} //Die Schleife wird so lange wiederholt, bis das Ende der Liste erreicht ist (wenn currentArticleList den Wert null hat).
		}
		//vollständige Zeichenkette result zurückgegeben, die den gesamten Inhalt der verketteten Liste repräsentiert.
		return result;
	}
}
