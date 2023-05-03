package shop.local.persistence;

import java.io.IOException;
import java.util.List;

import shop.local.entities.Article;
import shop.local.entities.Customer;
import shop.local.entities.Employee;

public class DBPersistenceManager implements PersistenceManager {

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Article readArticle() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openForReading(String dataSource) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void openForWriting(String dataSource) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean saveArticle(Article article) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveCustomer(Customer customer, List<Customer> existingCustomers) throws IOException {
		return false;
	}

	@Override
	public Customer loadCustomer() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee loadEmployee() throws IOException {
		return null;
	}

	@Override
	public boolean saveEmployee(Employee employee, List<Employee> existingEmployees) throws IOException {
		return false;
	}
}
