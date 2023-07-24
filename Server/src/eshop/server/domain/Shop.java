package eshop.server.domain;

import eshop.common.entities.*;
import eshop.common.exceptions.*;

import java.io.IOException;
import java.util.*;

/**
 * Class for managing a (very simple) library. Provides methods to return all
 * items in inventory, to search for articles, to insert new articles and to
 * save inventory.
 *
 * @author Sund
 */

public class Shop {
	// Prefix for names of files in which shop data is stored
	private String file = "";
	private ArticleAdministration articleAdministration;

	private CustomerAdministration customerAdministration;

	private EmployeeAdministration employeeAdministration;

	private EventAdministration eventAdministration;


	/**
	 * Constructor that reads the basic data (articles, customers etc.) from files
	 * (Initialization of the shop).
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


	/**
	 * Method when user leaves the shop
	 *
	 * @return String message
	 */
	public String disconnect(){
		return "Goodbye and hope to see you again!";
	}





	/**
	 * Methods for registering and logging in employees / customers
	 *
	 */


	/**
	 * Methods for a customer which wants to logg-in
	 *
	 * @param username of the customer
	 * @param password of the customer
	 * @return logged-in-User
	 * @throws LoginException
	 */
	public Customer loginCustomer(String username, String password) throws LoginException {
		return customerAdministration.login(username, password);
	}

	/**
	 * Methods for an Employee which wants to logg-in
	 *
	 * @param username of the Employee
	 * @param password of the Employee
	 * @return logged-in-User
	 * @throws LoginException
	 */
	public Employee loginEmployee(String username, String password) throws LoginException {
		return employeeAdministration.login(username, password);
	}

	/**
	 * Methods to register a new Customer
	 *
	 * @param name etc. of the new customer
	 * @param registerNow to make sure he really wants to register himself
	 * @return String of success
	 * @throws RegisterException
	 */
	public String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail,
								   String username, String password, String registerNow) throws RegisterException {
		return customerAdministration.registerCustomer(name, lastName, street, postalCode, city, mail, username, password, registerNow);
	}

	/**
	 * Methods to register a new Employee (only possible by other Employees)
	 *
	 * @param name etc. of the new Employee
	 * @return String of success
	 * @throws RegisterException
	 */
	public String registerEmployee(String name, String lastname, String username, String password) throws IOException, RegisterException {
		return employeeAdministration.registerEmployee(name, lastname, username, password);
	}




	/**
	 * Common used methods (customer & employee):
	 */


	/**
	 * Method that gets all stock articles.
	 * @return List of all items in the shop stock
	 */
	public ArrayList<Article> getAllArticles() {
		return articleAdministration.getArticleStock();
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
	 * Method to search articles by article number. There will be one or none article returned.
	 *
	 * @param articleNumber Article number of the searched article
	 * @return list of items found (may be empty)
	 */
	public Article searchByArticleNumber(int articleNumber) throws ArticleNotFoundException{
		return articleAdministration.searchByArticleNumber(articleNumber);
	}





	/**
	 * Methods for the employee:
	 */


	/**
	 * Method of adding a new item to stock. If the item is already in stock, the stock will not be changed.
	 *
	 * @param articleTitle the Title of the article
	 * @param price the price of the article
	 * @param quantityInStock the quantity of the article
	 * @param articleType the articleType of the article (Bulk, normal)
	 * @param packSize of the article if it is a bulkArticle
	 * @param user for writing him into the event file
	 * @return article object inserted in case of success
	 * @throws ArticleAlreadyExistsException, IOException
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

	public void writeArticleDataToAddArticle() throws IOException {
		articleAdministration.writeData("ESHOP_Article.txt");
	}


	/**
	 * Method of deleting an item from inventory. Only the first occurrence of the article will be deleted.
	 *
	 * @param number of the Article which should be deleted
	 * @param user for writing him into the event file
	 *
	 * Info @Teschke:
	 * Hier kam es bei den Events manchmal zu einer NUllPointerException und ich konnte den Fehler nicht finden, weshalb ich den Code für Events auskommentiert habe.
	 * Ich habe mich erst eingeloggt. Dann einen Artikel gelöscht und mich dann wieder ausgeloggt.
	 * Beim zweiten Mal einloggen habe ich dann einen anderen Artikel gelöscht und dann trat die Exception auf.
	 * Debugger hat mir bis zu Zeile 146 angezeigt dass er den Artikel findet...
	 */
	public void deleteArticle(int number, User user) throws IOException, ArticleNotFoundException {
		Article article = articleAdministration.searchByArticleNumber(number);
		articleAdministration.delete(article);
		writeArticleDataToRemoveArticle("ESHOP_Article.txt", article);
//		// Ereignis für die Einlagerung in ArrayList schreiben
//		Event event = new Event(Event.EventType.AUSLAGERUNG, article, 0, user);
//		eventAdministration.addEvent(event);
//		// Ereignis für die Einlagerung in File schreiben
//		eventAdministration.writeData("ESHOP_Events.txt");
	}

	/**
	 * Method that deletes an article from stock
	 *
	 * @param file ESHOP_Article.txt
	 * @param articleToRemove article to remove from stock
	 * @throws IOException
	 */
	public void writeArticleDataToRemoveArticle(String file, Article articleToRemove) throws IOException {
		articleAdministration.writeDataToRemoveArticle(file, articleToRemove);
	}


	/**
	 * Method that increases an articles' stock
	 *
	 * @param article the article whose stock should be increased
	 * @param quantityToAdd number of articles that are to be added to stock
	 * @param user for writing him into the event file
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
	 * @param article the article whose stock should be decreased
	 * @param quantityToRetrieve number of articles that are to be retrieved from stock
	 * @param user for writing him into the event file
	 * @return Boolean to declare that the process was successful
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


	/**
	 * Method to get all events in the last 30 days of a specific article
	 *
	 * @param articleID of the article which the employee likes the history from
	 * @return events of the article
	 */
	public Map<Date, Integer> getEventsbyArticleOfLast30Days(int articleID) {
		Article article = articleAdministration.getArticleByID(articleID);
		return eventAdministration.getEventsbyArticleOfLast30Days(article);
	}





	/**
	 * Methods for the customer:
	 */


	/**
	 * Method for getting all items in the logged in user's shopping cart
	 *
	 * @param customer for getting the customers shoppingCart
	 * @return List of all items in the logged in user's shopping cart
	 */
	public List<ShoppingCartItem> getAllCartItems(Customer customer) {
		return customerAdministration.getUsersShoppingCart(customer);
	}


	/**
	 * Method for adding an article the shopping cart
	 *
	 * @param article which article should be added to the cart
	 * @param quantity the quantity of the article
	 * @param customer for getting the customers shoppingCart
	 * @return String of success or that the quantity should be a positive number
	 * @throws BulkArticleException, InsufficientStockException
	 */
	public String addArticleToCart(Article article, int quantity, Customer customer) throws BulkArticleException, InsufficientStockException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return shoppingCart.addArticleToCart(article, quantity);
	}


	/**
	 * Method for removing an article from the shopping cart
	 *
	 * @param customer for getting the customers shoppingCart
	 * @param article which should be deleted from cart
	 * @return String Success-Message that the article was removed successfully
	 * @throws IOException, ArticleInCartNotFoundException
	 */
	public String removeArticleFromCART(Customer customer, Article article) throws IOException, ArticleInCartNotFoundException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return shoppingCart.deleteSingleArticle(article);
	}


	/**
	 * Method for updating an article's quantity in the shopping cart
	 *
	 * @param newQuantity the uses wishes to have in the cart
	 * @param article in the shopping Cart which quantity should be updated
	 * @param customer for getting the customers shoppingCart
	 * @return String Success-Message or that the article was deleted when quantity was = 0
	 * @throws ArticleInCartNotFoundException, BulkArticleException, InsufficientStockException
	 */
	public String changeArticleQuantityInCart(int newQuantity, Article article, Customer customer) throws ArticleInCartNotFoundException, BulkArticleException, InsufficientStockException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return shoppingCart.changeArticleQuantityInCart(newQuantity, article);
	}


	/**
	 * Method for viewing which articles are in the shopping cart
	 *
	 * @param customer for getting the customers shoppingCart
	 * @return String Success-Message
	 * @throws EmptyCartException
	 */
	public String deleteAllArticlesInCart (Customer customer) throws EmptyCartException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return shoppingCart.deleteAllArticlesInCart();
	}


	/**
	 * Method for viewing which articles are in the shopping cart
	 *
	 * @param customer for getting the customers shoppingCart
	 * @return a String that shows the cart Items
	 * @throws EmptyCartException
	 */
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


	/**
	 * Method for purchasing articles in the shopping cart
	 *
	 * @return Invoice with a list of successfully purchased articles, a list of unavailable articles, date and total of purchase
	 * @param customer for getting the customers shoppingCart
	 * @throws IOException, EmptyCartException, ArticleBuyingException
	 */
	public Invoice buyArticles(Customer customer) throws IOException, EmptyCartException, ArticleBuyingException {
		ShoppingCart shoppingCart = customer.getShoppingCart();
		return articleAdministration.buyArticles(shoppingCart, customer);
	}

}
