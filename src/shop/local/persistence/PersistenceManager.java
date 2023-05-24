package shop.local.persistence;

import java.io.IOException;
import java.util.List;

import shop.local.entities.Article;
import shop.local.entities.ArticleList;
import shop.local.entities.Customer;
import shop.local.entities.Employee;

/**
 * @author Sund
 *
 * General interface for accessing a storage medium
 * (e.g. file or database) to store e.g
 * Article or customer data.
 *
 * The interface must be implemented by classes that have a
 * Want to implement persistence interface.
 */
public interface PersistenceManager {

	public void openForReading(String dataSource) throws IOException;

	public void openForWriting(String dataSource) throws IOException;
	
	public boolean close();

	/**
	 * Method for reading in the article data from an external data source.
	 *
	 * @return article object, if fetch successful, false null
	 */
	public Article readArticle() throws IOException;

	public boolean addArticle(Article articleToAdd, ArticleList existingArticles) throws IOException;

	public boolean deleteArticle(Article articleToDelete, ArticleList existingArticles) throws IOException;

	public boolean saveCustomer(Customer customer, List<Customer> existingCustomers) throws IOException;

	/**
	 * Method for reading in the customer data from an external data source.
	 *
	 * @return customer object, if successful, false null
	 */
	public Customer loadCustomer() throws IOException;

	/**
	 * Method for reading in the customer data from an external data source.
	 *
	 * @return customer object, if successful, false null
	 */
	public Employee loadEmployee() throws IOException;

	public boolean saveEmployee(Employee employee, List<Employee> existingEmployees) throws IOException;

	public void clearCustomerFile();
}
