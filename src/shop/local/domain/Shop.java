package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.*;

/**
 * Class for managing a (very simple) library.
 * Provides methods to return all items in inventory,
 * to search for articles, to insert new articles
 * and to save inventory.
 *
 * @author Sun
 * @version 1 (managing items in a linked list)
 */
public class Shop {
	// Prefix for names of files in which shop data is stored
	private String file = "";

	//Variable from parts management is declared. Can later be used to create an object of this class
	private ArticleAdministration articleAdministration;

	//Customer administration variable is declared. Can later be used to create an object of this class
	private CustomerAdministration customerAdministration;

	//Employee administration variable is declared. Can later be used to create an object of this class
	private EmployeeAdministration employeeAdministration;


	/**
	 * Constructor that reads the basic data (articles, customers etc.) from files
	 * (Initialization of the shop).
	 * <p>
	 * File naming pattern:
	 * file+"_A.txt" is the file of the articles
	 * file+"_C.txt" is the file of the customers
	 *
	 * @param file prefix for files with basic data (articles, customers, authors)
	 * @throws IOException e.g. if one of the files does not exist.
	 */
	public Shop(String file) throws IOException {
		this.file = file;

		// A new instance of the ArticleAdministration class is created and assigned to the articleAdministration variable
		// Read item inventory from file
		articleAdministration = new ArticleAdministration();
		articleAdministration.readData(file + "_A.txt");

		// A new instance of the CustomerAdministration class is created and assigned to the customerAdministration variable
		// Read customer profile from file
		customerAdministration = new CustomerAdministration();
		customerAdministration.readData(file + "_C.txt");
		//customerAdministration.writeCustomerData(file+"_C.txt");

		// A new instance of the EmployeeAdministration class is created and assigned to the employeeAdministration variable
		// Read customer profile from file
		employeeAdministration = new EmployeeAdministration();
		employeeAdministration.readData(file+"_E.txt");
		//employeeAdministration.writeData(file+"_e.txt");
	}

	public Customer loginCustomer(String username, String password) {
		return customerAdministration.login(username, password);
	}

	public Employee loginEmployee(String username, String password) {
		return employeeAdministration.login(username, password);
	}

	/**
	 * Method that returns a list of all items in inventory.
	 *
	 * @return List of all items in the shop stock
	 */
	public ArticleList getAllArticles() {
		return articleAdministration.getArticleStock();
	}

	/**
	 * Method to search items by item name. There will be a list of items
	 * returned, which contains all items with an exact matching item description.
	 *
	 * @param articleTitle Article title of the searched article
	 * @return list of items found (may be empty)
	 */
	public ArticleList searchByArticleTitle(String articleTitle) {
		return articleAdministration.searchArticle(articleTitle);
	}

	/**
	 * Method to search articles by article number. There will be one or none article returned.
	 *
	 * @param articleNumber Article number of the searched article
	 * @return list of items found (may be empty)
	 */
	public Article searchByArticleNumber(int articleNumber) {
		return articleAdministration.searchByArticleNumber(articleNumber);
	}

	/**
	 * Method of adding a new item to stock.
	 * If the item is already in stock, the stock will not be changed.
	 *
	 * @param articleTitle Title of the article
	 * @param quantityInStock Stock Quantity
	 * @return article object inserted in case of success
	 * @throws ArticleAlreadyExistsException if the article already exists
	 */
	public Article insertArticle(String articleTitle, int quantityInStock) throws ArticleAlreadyExistsException {
		Article b = new Article(articleTitle, quantityInStock);
		articleAdministration.insert(b);
		return b;
	}

	/**
	 * Method of deleting an item from inventory.
	 * Only the first occurrence of the article will be deleted.
	 *
	 * @param articleTitle Title of the article
	 * @param number       Article number
	 */
	public void deleteArticle(String articleTitle, int number) {
		Article b = new Article(articleTitle, number);
		articleAdministration.delete(b);
	}

	/**
	 * Method that increases an articles' stock
	 *
	 * @param article the article whose stock should be increased
	 * @param quantityToAdd number of articles that are to be added to stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public void increaseArticleStock(Article article, int quantityToAdd) throws IOException {
		articleAdministration.increaseArticleStock(article, quantityToAdd);
	}

	/**
	 * Method that decreases an articles' stock
	 *
	 * @param article the article whose stock should be decreased
	 * @param quantityToRetrieve number of articles that are to be retrieved from stock
	 * @return Article with searched articleNumber (may be empty)
	 */
	public boolean decreaseArticleStock(Article article, int quantityToRetrieve) throws IOException {
		return articleAdministration.decreaseArticleStock(article, quantityToRetrieve);
	}

	public void addEmployee(Employee employee) {
		employeeAdministration.addEmployee(employee);
	}

	public void writeArticleData(String file, Article article) throws IOException {
		articleAdministration.writeData(file, article);
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


}
