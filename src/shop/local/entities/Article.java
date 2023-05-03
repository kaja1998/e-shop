package shop.local.entities;


/**
 * Class for representing individual articles.
 * 
 * @author Sund
 */
public class Article {

	// Attributes describing an item
	private String articleTitle;
	private int number;
	private boolean inStock;
	
	
	public Article(String articleTitle, int number) {
		this(articleTitle, number, true);
	}

	public Article(String articleTitle, int number, boolean inStock) {
		this.number = number;
		this.articleTitle = articleTitle;
		this.inStock = inStock;
	}
	
	// --- Dienste der Artikel-Objekte ---

	/**
	 * Overridden Object's default method.
	 * Method is always called automatically when an item object is sent as a string
	 * is used (e.g. in println(book);)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String availability = inStock ? "inStock" : "soldOut";
		return ("Number: " + number + " / Article title: " + articleTitle + " / " + availability);
	}

	/**
	 * Overridden Object's default method.
	 * Method is used to compare two item objects based on their values,
	 * i.e. article designation and number.
	 *
	 * @see java.lang.Object#toString()
	 */
	public boolean equals(Object otherArticle) {
		if (otherArticle instanceof Article)
			return ((this.number == ((Article) otherArticle).number)
					&& (this.articleTitle.equals(((Article) otherArticle).articleTitle)));
		else
			return false;
	}

	
	/*
	 * From here Accessor-Methoden
	 */
	public int getNumber() {
		return number;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public boolean isInStock() {
		return inStock;
	}
}
