package shop.local.entities;

import shop.local.domain.exceptions.StockDecreaseException;

/**
 * Class for representing individual articles.
 * @author Sund
 */
public class Article {

	// Attributes describing an item
	private String articleTitle;
	private int number;					//id
	private int quantityInStock;
	private boolean inStock;
	private double price;
	private static int idCounter = 0;
	private ArticleStatus status;

	//Konstruktor, wenn ich den Artikel anlege
	public Article(String articleTitle, int quantityInStock, double price) {
		this.idCounter = ++idCounter;
		this.number = idCounter;
		this.articleTitle = articleTitle;
		this.quantityInStock = quantityInStock;
		this.inStock = quantityInStock > 0;
		this.price = price;
		this.status = ArticleStatus.fromString("Active");
	}

	//Konstruktor, wenn Artikel aus Datei auslesen wird
	public Article(int number, String articleTitle, int quantityInStock, double price, ArticleStatus status) {
		this.idCounter = number;
		this.number = idCounter;
		this.articleTitle = articleTitle;
		this.quantityInStock = quantityInStock;
		this.inStock = quantityInStock > 0;
		this.price = price;
		this.status = status;
	}

	// --- Dienste der Artikel-Objekte ---

	/**
	 * Overridden Object's default method.
	 * Method is always called automatically when an item object is sent as a string
	 * is used (e.g. in println(book);)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String availability = inStock ? "inStock" : "soldOut";
		return ("Number: " + number + " / Price EUR " + price + " / Article title: " + articleTitle + " / Quantity " + quantityInStock + " (" + availability + ")");
	}

	public String articleString(){
		return number + "," + price + "," + articleTitle + "," + quantityInStock;
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
		if (otherArticle instanceof Article) {
			return this.articleTitle.equals(((Article) otherArticle).articleTitle);
		} else if (otherArticle instanceof ShoppingCartItem) {
			return this.articleTitle.equals(((ShoppingCartItem) otherArticle).getArticle().getArticleTitle());
		} else {
			return false;
		}
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


	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public double getPrice() {
		return price;
	}

	public ArticleStatus getStatus() {
		return status;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public void setStatus(ArticleStatus status) {
		this.status = status;
	}


	public void increaseStock(int quantityToAdd) {
		// stock up
		this.quantityInStock += quantityToAdd;

		// check if article is inStock now
		if(this.quantityInStock > 0) {
			this.inStock = true;
		}
	}

	public boolean decreaseStock(int quantityToRetrieve) throws StockDecreaseException {
		// check if quantity can be retrieved
		if(quantityToRetrieve > this.quantityInStock) {
			throw new StockDecreaseException("Insufficient quantity in stock.");
		}

		// retrieve stock
		//Wenn die angegebene Menge kleiner oder gleich dem Lagerbestand ist, wird die Menge vom Lagerbestand abgezogen, um die Entnahme zu simulieren.
		this.quantityInStock -= quantityToRetrieve;

		// check if article is out of stock now
		if(this.quantityInStock <= 0) {
			this.inStock = false;
		}

		// if we came until here, stock was decreased successfully
		return true;
	}

	public boolean decreaseStockWhileBuy(int quantityToRetrieve) {
		// check if quantity can be retrieved
		if(quantityToRetrieve > this.quantityInStock) {
			return false;
		}

		// retrieve stock
		//Wenn die angegebene Menge kleiner oder gleich dem Lagerbestand ist, wird die Menge vom Lagerbestand abgezogen, um die Entnahme zu simulieren.
		this.quantityInStock -= quantityToRetrieve;

		// check if article is out of stock now
		if(this.quantityInStock <= 0) {
			this.inStock = false;
		}

		// if we came until here, stock was decreased successfully
		return true;
	}
}
