package eshop.server.persistence;
import eshop.common.entities.Article;
import eshop.common.entities.Customer;
import eshop.common.entities.Employee;
import eshop.common.entities.Event;
import eshop.server.domain.ArticleAdministration;
import eshop.server.domain.CustomerAdministration;
import eshop.server.domain.EmployeeAdministration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sund
 */

public class DBPersistenceManager implements PersistenceManager {


	@Override
	public void openForReading(String dataSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openForWriting(String dataSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public Article readArticle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeArticles(ArrayList<Article> existingArticles) throws IOException {
		return false;
	}



	@Override
	public boolean saveCustomer(Customer customer, List<Customer> existingCustomers) {
		return false;
	}


	@Override
	public Customer loadCustomer() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Employee loadEmployee() {
		return null;
	}

	@Override
	public boolean saveEmployee(Employee employee, List<Employee> existingEmployees) {
		return false;
	}



	@Override
	public Event loadEvent(ArticleAdministration articleAdministration, EmployeeAdministration employeeAdministration, CustomerAdministration customerAdministration) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean saveEvent(List<Event> events) {
		return false;
	}

}
