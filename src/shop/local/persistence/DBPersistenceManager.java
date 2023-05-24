package shop.local.persistence;

import java.io.IOException;
import java.util.List;

import shop.local.entities.Article;
import shop.local.entities.ArticleList;
import shop.local.entities.Customer;
import shop.local.entities.Employee;

public class DBPersistenceManager implements PersistenceManager {

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
	public boolean addArticle(Article articleToAdd, ArticleList existingArticles) throws IOException {
		return false;
	}

	@Override
	public void openForReading(String dataSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openForWriting(String dataSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteArticle(Article article, ArticleList existingArticles) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteArticle(ArticleList existingArticles) throws IOException {
		// TODO Auto-generated method stub
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
	public void clearCustomerFile() {
	}
}
