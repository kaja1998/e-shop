package eshop.server.persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eshop.common.entities.Article;
import eshop.common.entities.Customer;
import eshop.common.entities.Employee;
import eshop.common.entities.Event;
import eshop.server.domain.ArticleAdministration;
import eshop.server.domain.CustomerAdministration;
import eshop.server.domain.EmployeeAdministration;

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

	public boolean writeArticles(ArrayList<Article> existingArticles) throws IOException;

	public boolean deleteArticle(Article articleToDelete, ArrayList<Article> existingArticles) throws IOException;


	/**
	 * Method for reading in the customer data from an external data source.
	 *
	 * @return customer object, if successful, false null
	 */
	public Customer loadCustomer() throws IOException;

	public boolean saveCustomer(Customer customer, List<Customer> existingCustomers) throws IOException;


	/**
	 * Method for reading in the customer data from an external data source.
	 *
	 * @return customer object, if successful, false null
	 */
	public Employee loadEmployee() throws IOException;

	public boolean saveEmployee(Employee employee, List<Employee> existingEmployees) throws IOException;


	/**
	 * Method for reading in the Event data from an external data source.
	 *
	 * @return Event object, if successful, false null
	 */
	public Event loadEvent(ArticleAdministration articleAdministration, EmployeeAdministration employeeAdministration, CustomerAdministration customerAdministration) throws IOException;

	public boolean saveEvent(List<Event> events);

}
