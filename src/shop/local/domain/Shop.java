package shop.local.domain;

import java.io.IOException;
import java.util.*;

import shop.local.domain.exceptions.*;
import shop.local.entities.*;

/**
 * Class for managing a (very simple) library. Provides methods to return all
 * items in inventory, to search for articles, to insert new articles and to
 * save inventory.
 *
 * @author Sund
 */

public class Shop {
	// Prefix for names of files in which shop data is stored
	@SuppressWarnings("unused")
	private String file = "";
	private ArticleAdministration articleAdministration;

	private CustomerAdministration customerAdministration;

	private EmployeeAdministration employeeAdministration;

	private EventAdministration eventAdministration;

	/**
	 * Constructor that reads the basic data (articles, customers etc.) from files
	 * (Initialization of the shop).
	 * <p>
	 * File naming pattern: file+"_A.txt" is the file of the articles file+"_C.txt"
	 * is the file of the customers
	 *
	 * @param file prefix for files with basic data (articles, customers, authors)
	 * @throws IOException e.g. if one of the files does not exist.
	 */
	public Shop(String file) throws IOException {
		this.file = file;

		// A new instance of the ArticleAdministration class is created and assigned to
		// the articleAdministration variable
		// Read item inventory from file
		articleAdministration = new ArticleAdministration();
		articleAdministration.readData(file + "_Article.txt");

		customerAdministration = new CustomerAdministration();
		customerAdministration.readData(file + "_Customer.txt");

		employeeAdministration = new EmployeeAdministration();
		employeeAdministration.readData(file + "_Employee.txt");

		eventAdministration = new EventAdministration();
		eventAdministration.readData(file + "_Events.txt", articleAdministration, employeeAdministration,
				customerAdministration);
		articleAdministration.setEventAdministration(eventAdministration);
	}

	public Customer loginCustomer(String username, String password) throws LoginException {
		return customerAdministration.login(username, password);
	}

	public Employee loginEmployee(String username, String password) throws LoginException {
		return employeeAdministration.login(username, password);
	}

	public String logout(User user){
		user = null;
		return "You got logged out successfully.";
	}

	/**
	 * Method that returns a list of all items in inventory.
	 *
	 * @return List of all items in the shop stock
	 */
	public ArrayList<Article> getAllArticles() {
		return articleAdministration.getArticleStock();
	}

	/**
	 * Method for getting all items in the logged in user's shopping cart
	 *
	 * @return List of all items in the logged in user's shopping cart
	 */
	public List<ShoppingCartItem> getAllCartItems(Customer customer) {
		return customerAdministration.getUsersShoppingCart(customer);
	}

	/**
	 * Method to search items by item name. There will be a list of items returned,
	 * which contains all items with an exact matching item description.
	 *
	 * @param articleTitle Article title of the searched article
	 * @return list of items found (may be empty)
	 */
	public ArrayList<Article> searchByArticleTitle(String articleTitle) throws ArticleNotFoundException {
		return articleAdministration.searchArticle(articleTitle);
	}

	/**
	 * Method to search articles by article number. There will be one or none
	 * article returned.
	 *
	 * @param articleNumber Article number of the searched article
	 * @return list of items found (may be empty)
	 */
	public Article searchByArticleNumber(int articleNumber) throws ArticleNotFoundException{
		return articleAdministration.searchByArticleNumber(articleNumber);
	}

	/**
	 * Method of adding a new item to stock. If the item is already in stock, the
	 * stock will not be changed.
	 *
	 * @param quantityInStock Stock Quantity
	 * @param user            loggedInUser
	 * @return article object inserted in case of success
	 * @throws ArticleAlreadyExistsException if the article already exists
	 */
	public Article insertArticle(String articleTitle, double price, int quantityInStock, String articleType, int packSize, User user) throws ArticleAlreadyExistsException, IOException {
		Article article;
		if (articleType.equalsIgnoreCase("bulk")) {
			article = new BulkArticle(articleTitle, quantityInStock, price, packSize);
		} else {
			article = new Article(articleTitle, quantityInStock, price);
		}
		articleAdministration.insertArticle(article);
		writeArticleDataToAddArticle();
		// Ereignis für die Einlagerung in ArrayList schreiben
		Event event = new Event(Event.EventType.NEU, article, quantityInStock, user);
		eventAdministration.addEvent(event);
		// Ereignis für die Einlagerung in File schreiben
		eventAdministration.writeData("ESHOP_Events.txt");
		return article;
	}

	/**
	 * Method of deleting an item from inventory. Only the first occurrence of the
	 * article will be deleted.
	 *
	 * @param number of the Article which should be deleted
	 */
	public void deleteArticle(int number, User user) throws IOException, ArticleNotFoundException {
		Article article = articleAdministration.searchByArticleNumber(number);
		articleAdministration.delete(article);
		writeArticleDataToRemoveArticle("ESHOP_Article.txt", article);
		// Ereignis für die Einlagerung in ArrayList schreiben
		Event event = new Event(Event.EventType.AUSLAGERUNG, article, 0, user);
		eventAdministration.addEvent(event);
		// Ereignis für die Einlagerung in File schreiben
		eventAdministration.writeData("ESHOP_Events.txt");

	}

	/**
	 * Method for purchasing articles in the shopping cart
	 *
	 * @return Invoice with a list of successfully purchased articles, a list of
	 *         unavailable articles, date and total of purchase
	 */
	public Invoice buyArticles(Customer customer) throws IOException, EmptyCartException, ArticleBuyingException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return articleAdministration.buyArticles(shoppingCart, customer);
	}

	/**
	 * Method that increases an articles' stock
	 *
	 * @param article       the article whose stock should be increased
	 * @param quantityToAdd number of articles that are to be added to stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public void increaseArticleStock(Article article, int quantityToAdd, User user) throws IOException {
		articleAdministration.increaseArticleStock(article, quantityToAdd, "ESHOP_Article.txt");
		// Ereignis für die Einlagerung in ArrayList schreiben
		Event event = new Event(Event.EventType.EINLAGERUNG, article, quantityToAdd, user);
		eventAdministration.addEvent(event);
		// Ereignis für die Einlagerung in File schreiben
		eventAdministration.writeData("ESHOP_Events.txt");
	}

	/**
	 * Method that decreases an articles' stock
	 *
	 * @param article            the article whose stock should be decreased
	 * @param quantityToRetrieve number of articles that are to be retrieved from
	 *                           stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public boolean decreaseArticleStock(Article article, int quantityToRetrieve, User user) throws IOException, StockDecreaseException {
		boolean bo = articleAdministration.decreaseArticleStock(article, quantityToRetrieve, "ESHOP_Article.txt");
		if (bo) {
			int quantity = -quantityToRetrieve;
			Event event = new Event(Event.EventType.AUSLAGERUNG, article, quantity, user);
			// Ereignis für die Auslagerung in ArrayList schreiben
			eventAdministration.addEvent(event);
			// Ereignis für die Einlagerung in File schreiben
			eventAdministration.writeData("ESHOP_Events.txt");
		}
		return bo;
	}

	public void writeArticleDataToAddArticle() throws IOException {
		articleAdministration.writeData("ESHOP_Article.txt");
	}

	public void writeArticleDataToRemoveArticle(String file, Article articleToRemove) throws IOException {
		articleAdministration.writeDataToRemoveArticle(file, articleToRemove);
	}

	public Map<Date, Integer> getEventsbyArticleOfLast30Days(int articleID) {
		Article article = articleAdministration.getArticleByID(articleID);
		return eventAdministration.getEventsbyArticleOfLast30Days(article);
	}

	public String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail,
			String username, String password, String registerNow) throws RegisterException {
		return customerAdministration.registerCustomer(name, lastName, street, postalCode, city, mail, username, password, registerNow);
	}

	public String registerEmployee(String name, String lastname, String username, String password) throws IOException, RegisterException {
		return employeeAdministration.registerEmployee(name, lastname, username, password);
	}

	public String addArticleToCart(Article article, int quantity, Customer customer) throws BulkArticleException, InsufficientStockException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		// Delete item from shopping cart
		return shoppingCart.addArticleToCart(article, quantity);
	}
	
	public String removeArticleFromCART(Customer customer, Article article) throws IOException, ArticleInCartNotFoundException {
			ShoppingCart shoppingCart = customer.getShoppingCart();
			// Delete item from shopping cart
			return shoppingCart.deleteSingleArticle(article);
	}

	public String changeArticleQuantityInCart(int newQuantity, Article article, Customer customer) throws ArticleInCartNotFoundException, BulkArticleException, InsufficientStockException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return shoppingCart.changeArticleQuantityInCart(newQuantity, article);
	}

	public String deleteAllArticlesInCart (Customer customer) throws EmptyCartException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return shoppingCart.deleteAllArticlesInCart();
	}

	public String viewArticlesInCart(Customer customer) throws EmptyCartException {
		List<ShoppingCartItem> shoppingCartItems = customerAdministration.getUsersShoppingCart(customer);

		if (shoppingCartItems != null && shoppingCartItems.size() > 0) {
			for (ShoppingCartItem item : shoppingCartItems) {
				//Artikel auf der Konsole ausgegeben.
				return "In your shopping cart are the following items: \n" + item.toString();
			}
		} else {
			throw new EmptyCartException(null);
		}
		return null;
	}
}
