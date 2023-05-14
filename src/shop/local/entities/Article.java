package shop.local.entities;

/**
 * Class for representing individual articles.
 * 
 * @author Sund
 */
public class Article {

	// Attributes describing an item
	private String articleTitle;
	private int number;					//id?
	private int quantityInStock;
	private boolean inStock;
	private double price;
	private static int idCounter = 0;

	public Article(int number, String articleTitle, int quantityInStock) {
		this.number = number;
		this.idCounter = number;
		this.articleTitle = articleTitle;
		this.quantityInStock = quantityInStock;
		this.inStock = quantityInStock > 0;
	}

	public Article(String articleTitle, int quantityInStock, double price) {
		this.idCounter = ++idCounter;
		this.number = idCounter;
		this.articleTitle = articleTitle;
		this.quantityInStock = quantityInStock;
		this.inStock = quantityInStock > 0;
		this.price = price;
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
		return ("Number: " + number + " / Article title: " + articleTitle + " / Quantity " + quantityInStock + " (" + availability + ")");
	}

	/**
	 * Overridden Object's default method.
	 * Method is used to compare two item objects based on their values,
	 * i.e. article designation and number.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public boolean equals(Object otherArticle) {
		if (otherArticle instanceof Article)
			return ((this.number == ((Article) otherArticle).number));
		else
			return false;
	}


	/*	@Override
	public boolean equals(Object otherArticle) {
*//*		if (otherArticle instanceof ShoppingCartItem && ((ShoppingCartItem) otherArticle).getArticle().number == ((Article) otherArticle).number) {
			return true;
		} else {
			if (otherArticle instanceof Article)
				return ((this.number == ((Article) otherArticle).number)
						&& (this.articleTitle.equals(((Article) otherArticle).articleTitle)));
			else
				return false;
		}*//*
		try {
			if (otherArticle instanceof ShoppingCartItem && ((ShoppingCartItem) otherArticle).getArticle().number == ((Article) otherArticle).number) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (otherArticle instanceof Article)
				return ((this.number == ((Article) otherArticle).number)
						&& (this.articleTitle.equals(((Article) otherArticle).articleTitle)));
			else
				return false;
		}
	}*/


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

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void increaseStock(int quantityToAdd) {
		// stock up
		this.quantityInStock += quantityToAdd;

		// check if article is inStock now
		if(this.quantityInStock > 0) {
			this.inStock = true;
		}
	}

	public boolean decreaseStock(int quantityToRetrieve) {
		// check if quantity can be retrieved
		if(quantityToRetrieve > this.quantityInStock) {
			return false;
		}

		// retrieve stock
		//Wenn die angegebene Menge kleiner oder gleich dem Lagerbestand ist, wird die Menge vom Lagerbestand abgezogen, um die Entnahme zu simulieren.
		this.quantityInStock -= quantityToRetrieve;

		// check if article is out of stock now
		if(this.quantityInStock < 0) {
			this.inStock = false;
		}

		// if we came until here, stock was decreased successfully
		return true;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
