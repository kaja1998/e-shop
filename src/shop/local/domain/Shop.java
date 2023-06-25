package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.domain.exceptions.ArticleNotFoundException;
import shop.local.domain.exceptions.LoginException;
import shop.local.domain.exceptions.RegisterException;
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

	// Variable from parts management is declared. Can later be used to create an
	// object of this class
	private ArticleAdministration articleAdministration;

	// Customer administration variable is declared. Can later be used to create an
	// object of this class
	private CustomerAdministration customerAdministration;

	// Employee administration variable is declared. Can later be used to create an
	// object of this class
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

		// A new instance of the CustomerAdministration class is created and assigned to
		// the customerAdministration variable
		// Read customer profile from file
		customerAdministration = new CustomerAdministration();
		customerAdministration.readData(file + "_Customer.txt");

		// A new instance of the EmployeeAdministration class is created and assigned to
		// the employeeAdministration variable
		// Read Employee profile from file
		employeeAdministration = new EmployeeAdministration();
		employeeAdministration.readData(file + "_Employee.txt");

		// A new instance of the EventAdministration class is created and assigned to
		// the EventAdministration variable
		// Read Events from file
		eventAdministration = new EventAdministration();
		eventAdministration.readData(file + "_Events.txt", articleAdministration, employeeAdministration,
				customerAdministration);
		articleAdministration.setEventAdministration(eventAdministration);
	}

	public boolean checkCustomerExists(Customer customer) {
		// First I get the list of all customers from the shop and save it in an
		// instance variable called customer list of type ArrayList<Customer>, which I
		// can freely use in this (EshopClientCUI).
		List<Customer> customerList = customerAdministration.getCustomers();
		// Then I go through the list of all customers with a for loop.
		// The loop iterates through each item in the customerList and assigns it to the
		// variable k
		for (Customer k : customerList) {
			// In the body of the loop, each customer object k is then compared with the
			// customer object.
			// The expression customer.equals(k) performs an equality check between customer
			// and k
			// and returns true if the two objects are equal.
			if (customer.equals(k)) {
				return true;
			}
		}
		return false;
	}

	public void registerCustomer(Customer customer) throws IOException {
		writeCustomerData("ESHOP_Customer.txt", customer);
		addCustomer(customer);
	}

	public Customer loginCustomer(String username, String password) throws LoginException {
		return customerAdministration.login(username, password);
	}

	public Employee loginEmployee(String username, String password) throws LoginException {
		return employeeAdministration.login(username, password);
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
	 * @param article         Article
	 * @param quantityInStock Stock Quantity
	 * @param user            loggedInUser
	 * @return article object inserted in case of success
	 * @throws ArticleAlreadyExistsException if the article already exists
	 */
	public Article insertArticle(Article article, int quantityInStock, User user) 
			throws ArticleAlreadyExistsException, IOException {
		articleAdministration.insertArticle(article);
		writeArticleDataToAddArticle("ESHOP_Article.txt", article);
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
	 * Method for getting all items in the logged in user's shopping cart
	 *
	 * @return List of all items in the logged in user's shopping cart
	 */
	public List<ShoppingCartItem> getUsersShoppingCart(Customer customer) {
		return customerAdministration.getUsersShoppingCart(customer);
	}

	/**
	 * Method for purchasing articles in the shopping cart
	 *
	 * @param shoppingCart ShoppingCart of the customer
	 * @return Invoice with a list of successfully purchased articles, a list of
	 *         unavailable articles, date and total of purchase
	 */
	public Invoice buyArticles(ShoppingCart shoppingCart, User user) throws IOException {
		return articleAdministration.buyArticles(shoppingCart, user);
		// Ereignis für die Einlagerung in ArrayList schreiben
		// TODO Event implementieren
	}

	/**
	 * Method that increases an articles' stock
	 *
	 * @param article       the article whose stock should be increased
	 * @param quantityToAdd number of articles that are to be added to stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public void increaseArticleStock(Article article, int quantityToAdd, String articleFile, User user)
			throws IOException {
		articleAdministration.increaseArticleStock(article, quantityToAdd, articleFile);
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
	public boolean decreaseArticleStock(Article article, int quantityToRetrieve, String articleFile, User user)
			throws IOException {
		boolean bo = articleAdministration.decreaseArticleStock(article, quantityToRetrieve, articleFile);
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

	public void addEmployee(Employee employee) {
		employeeAdministration.addEmployee(employee);
	}

	public void writeArticleDataToAddArticle(String file, Article articleToAdd) throws IOException {
		articleAdministration.writeData(file, articleToAdd);
	}

	public void writeArticleDataToRemoveArticle(String file, Article articleToRemove) throws IOException {
		articleAdministration.writeDataToRemoveArticle(file, articleToRemove);
	}

	public void writeCustomerData(String file, Customer customer) throws IOException {
		customerAdministration.writeData(file, customer);
	}

	public void writeEmployeeData(String file, Employee employee) throws IOException {
		employeeAdministration.writeData(file, employee);
	}

	/**
	 * Method to add a customer to the list of all customers
	 */
	public void addCustomer(Customer customer) {
		customerAdministration.addCustomer(customer);
	}

	public List<Customer> getCustomers() {
		return customerAdministration.getCustomers();
	}

	public void setCustomers(ArrayList<Customer> kunden) {
		customerAdministration.setCustomers(kunden);
	}

	public ArrayList<Employee> getEmployees() {
		return employeeAdministration.getEmployees();
	}

	public void setEmployees(ArrayList<Employee> employees) {
		employeeAdministration.setEmployees(employees);
	}

	public void readData(String file) throws IOException {
		customerAdministration.readData(file);
	}

	public List<Event> getEvents() {
		return eventAdministration.getEvents();
	}

	public List<Event> getEventsbyArticleOfLast30Days(int articleID) {
		return eventAdministration.getEventsbyArticleOfLast30Days(articleID);
	}

	public String customerRegister(String name, String lastName, String street, int postalCode, String city, String mail,
			String username, String password, String registerNow) throws IOException, RegisterException {
		return customerAdministration.customerRegister(name, lastName, street, postalCode, city, mail, username, password,
				registerNow);
	}

	public String registerEmployee(String name, String lastname, String username, String password) throws IOException, RegisterException {
		return employeeAdministration.registerEmployee(name, lastname, username, password);
	}

	public void addArticle(Article article, String articleTitle, String articleType, int initialQuantity, double price,
			int packSize)  throws IOException{
		articleAdministration.addArticle(article, articleTitle, articleType, initialQuantity, price, packSize);
	}

//	public void changeInventory(int stockChange, Article article, User loggedinUser) throws IOException {
//
//		if (stockChange < 0) {
//			boolean success = decreaseArticleStock(article, (-1) * stockChange, "ESHOP_Article.txt",loggedinUser);
//			if (success) {
//				System.out.println("Successfully decreased article's stock.");
//			} else {
//				System.out.println(
//						"Could not decrease stock. Maybe you tried to retrieve more items than there are available?");
//			}
//		} else {
//			increaseArticleStock(article, stockChange, "ESHOP_Article.txt", loggedinUser);
//			System.out.println("Successfully increased article's stock.");
//		}
//	}
	
//	public void showHistory(int articleID, List<Event> eventsList)  throws IOException
//	{
//		System.out.println("For the article with the ID: " + articleID + ", the stock quantity in the last few days were as followed:");
//		for (Event e : eventsList) {
//			System.out.println(e.toStringHistory());
//		}
//	}
	
	
	
//	public void checkPackSizeQuantity(int packSizeQuantity, Article article, ShoppingCart shoppingCart, int packSize)  throws IOException
//	{
//		if (packSizeQuantity >= 1) {
//			int quantityToAdd = packSizeQuantity * packSize;
//
//			// Überprüfen, ob die gewünschte Menge noch vorrätig ist
//			int availableQuantity = article.getQuantityInStock();
//			if (availableQuantity >= quantityToAdd) {
//				shoppingCart.addArticle(article, quantityToAdd);
//				System.out.println("Article/s were added successfully to the cart.");
//				// Warenkorb ausgeben
//				shoppingCart.read();
//			} else {
//				System.out.println("Could not put article into the Cart, because desired quantity must be not available.");
//			}
//		} else {
//			System.out.println("Please input a positive number.");
//		}
//	}
	
//	public void checkQuantity(int quantity, ShoppingCart shoppingCart, Article article)  throws IOException
//	{
//		if (quantity >= 1) {
//			// Überprüfen, ob der Artikel noch vorrätig ist
//			int availableQuantity = article.getQuantityInStock();
//			if (availableQuantity >= quantity) {
//				shoppingCart.addArticle(article, quantity);
//				System.out.println("Article/s were added successfully to the cart.");
//				// Warenkorb ausgeben
//				shoppingCart.read();
//			} else {
//				System.out.println(
//						"Could not put article into the Cart, because desired quantity must be not available..");
//			}
//		} else {
//			System.out.println("Please input a positive number for quantity.");
//		}
//	}

//	public void checkArticleSizeQTY(int newPackSizeQuantity, int packSize, ShoppingCart shoppingCart, Article article)  throws IOException
//	{
//		if (newPackSizeQuantity >= 1) {
//			int quantityToChange = newPackSizeQuantity * packSize;
//
//			// Check if the desired quantity is still in stock
//			int availableQuantity = article.getQuantityInStock();
//			if (availableQuantity >= quantityToChange) {
//				String updateResult = shoppingCart.updateArticleQuantity(article, quantityToChange);
//				if (updateResult != null) {
//					System.out.println(updateResult);
//				}
//
//				// Check if the shopping cart is not empty and print the shopping cart
//				if (!shoppingCart.getCartItems().isEmpty()) {
//					shoppingCart.read();
//				}
//			} else {
//				System.out.println(
//						"Could not change article quantity in the cart. Desired quantity is not available.");
//			}
//		} else {
//			System.out.println("Please input a positive number for the number of packs.");
//		}
//	}
	
//	public void checkAmount(int newQuantity, Article article, ShoppingCart shoppingCart)  throws IOException
//	{
//		if (newQuantity >= 1) {
//			// Check if the item is still in stock
//			int availableQuantity = article.getQuantityInStock();
//			if (availableQuantity >= newQuantity) {
//				String updateResult = shoppingCart.updateArticleQuantity(article, newQuantity);
//				if (updateResult != null) {
//					System.out.println(updateResult);
//				}
//				// Check if the shopping cart is not empty and print the shopping cart
//				if (!shoppingCart.getCartItems().isEmpty()) {
//					shoppingCart.read();
//				}
//			} else {
//				System.out.println(
//						"Could not change article quantity in the cart. Desired quantity is not available.");
//			}
//		} else {
//			System.out.println("Please input a positive number for quantity.");
//		}
//	}
	
	public String removeArticleFromCART(Customer customer, Article article) throws IOException 
	{
			ShoppingCart shoppingCart = customer.getShoppingCart();
			// Delete item from shopping cart
			return shoppingCart.deleteSingleArticle(article);
	}
	
//	public void viewArticlesInCart(List<ShoppingCartItem> shoppingCartItems)  throws IOException
//	{
//		if (shoppingCartItems != null && shoppingCartItems.size() > 0) {
//			// Wenn beides der Fall ist, wird eine Schleife verwendet, um über jedes
//			// ShoppingCartItem in der Liste zu iterieren.
//			System.out.println("In your shopping cart are the following items:");
//			for (ShoppingCartItem item : shoppingCartItems) {
//				// Artikel wird/werden auf der Konsole ausgegeben.
//				System.out.println(item.toString());
//			}
//		} else {
//			System.out.println("There are no items in your cart yet.");
//		}
//	}
	
//	public void articlesCouldntPurchase(Invoice invoice)  throws IOException
//	{
//		if (invoice.getUnavailableItems() != null && invoice.getUnavailableItems().size() > 0) {
//			System.out.println("Unfortunately some of the items you wished to purchase became unavailable:");
//			// If this is the case, a loop is used to iterate over each unavailable item in
//			// the list invoice.getUnavailableItems()
//			for (ShoppingCartItem item : invoice.getUnavailableItems()) {
//				// The unavailable articles are printed on the console
//				System.out.println(item.toString());
//			}
//		}
//	}
	
//	public void articlePurchaseSuccessfully(Invoice invoice)  throws IOException
//	{
//		if (invoice.getPositions() != null && invoice.getPositions().size() > 0) {
//			System.out.println("You successfully purchased:");
//			// With a loop, iterates over each successfully purchased item.
//			for (ShoppingCartItem item : invoice.getPositions()) {
//				// Articles are displayed on the console
//				System.out.println(item.toString());
//			}
//		}
//	}
	
}
