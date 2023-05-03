package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.entities.Article;
import shop.local.entities.ArticleList;
import shop.local.entities.Customer;
import shop.local.entities.User;

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

	// Logged in user
	User user;

	//Variable from parts management is declared. Can later be used to create an object of this class
	private ArticleAdministration administration;

	//Customer management variable is declared. Can later be used to create an object of this class
	private CustomerAdministration customerAdministration;

	/**
	 * Constructor that reads the basic data (articles, customers etc.) from files
	 * (Initialization of the shop).
	 *
	 * File naming pattern:
	 * file+"_B.txt" is the file of the articles
	 * file+"_K.txt" is the file of the customers
	 *
	 * @param file prefix for files with basic data (articles, customers, authors)
	 * @throws IOException e.g. if one of the files does not exist.
	 */
	public Shop(String file) throws IOException {
		this.file = file;

		// A new instance of the ArticleAdministration class is created and assigned to the articleAdministration variable
		// Read item inventory from file
		administration = new ArticleAdministration();
		administration.readData(file+"_B.txt");

		// A new instance of the CustomerAdministration class is created and assigned to the customerAdministration variable
		// Read customer profile from file
		customerAdministration = new CustomerAdministration();
		customerAdministration.readData(file+"_K.txt");
		//customerAdministration.writeData(file+"_K.txt");
	}


	/**
	 * Method that returns a list of all items in inventory.
	 *
	 * @return List of all items in the shop stock
	 */
	public ArticleList getAllArticles() {
		return administration.getArticleStock();
	}

	/**
	 * Method to search items by item name. There will be a list of items
	 * returned, which contains all items with an exact matching item description.
	 *
	 * @param articleTitle Article title of the searched article
	 * @return list of items found (may be empty)
	 */
	public ArticleList searchByArticleTitle(String articleTitle) {
		return administration.searchArticle(articleTitle);
	}

	/**
	 * Method of adding a new item to stock.
	 * If the item is already in stock, the stock will not be changed.
	 *
	 * @param articleTitle Title of the article
	 * @param number Article number
	 * @return article object inserted in case of success
	 * @throws ArticleAlreadyExistsException if the article already exists
	 */
	public Article insertArticle(String articleTitle, int number) throws ArticleAlreadyExistsException {
		Article b = new Article(articleTitle, number);
		administration.insert(b);
		return b;
	}

	/**
	 * Method of deleting an item from inventory.
	 * Only the first occurrence of the article will be deleted.
	 *
	 * @param articleTitle Title of the article
	 * @param number Article number
	 */
	public void deleteArticle(String articleTitle, int number) {
		Article b = new Article(articleTitle, number);
		administration.delete(b);
	}

	/**
	 * Method to save item inventory to a file.
	 *
	 * @throws IOException e.g. if file does not exist
	 */
	public void writeArticle() throws IOException {
		administration.writeData(file +"_B.txt");
	}

	public void writeData(String file, Customer customer) throws IOException {
		customerAdministration.writeData(file, customer);
	}

	/**
	 * Method to add a customer to the list of all customers
	 */
	public void addCustomer(Customer customer) {
		customerAdministration.addCustomer(customer);
	}

	public ArrayList<Customer> getCustomers() {
		return customerAdministration.getCustomers();
	}

	public void setCustomers(ArrayList<Customer> kunden) {
		customerAdministration.setCustomers(kunden);
	}

	public void readData(String file) throws IOException {
		customerAdministration.readData(file);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
