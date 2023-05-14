package shop.local.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import shop.local.entities.Article;
import shop.local.entities.ArticleList;
import shop.local.entities.Customer;
import shop.local.entities.Employee;

/**
 * @author Sund
 *
 * Realization of an interface for persistent storage of
 * Data in files.
 * @see PersistenceManager
 */
public class FilePersistenceManager implements PersistenceManager {

	//Die Variable "reader" ist vom Typ "BufferedReader" und kann verwendet werden, um Zeichen aus einer Datei zu lesen.
	private BufferedReader reader = null;

	//Die Variable "writer" ist vom Typ "PrintWriter" und kann Zeichen in eine Datei schreiben.
	// Null bedeutet nur, dass die Variable auf keinen bestimmten Speicherbereich im Computer zeigt
	private PrintWriter writer = null;

	//Die erste Methode openForReading(String datei) öffnet eine Datei zum Lesen.
	//Der Parameter datei ist der Name der Datei, die geöffnet werden soll.
	//Die Methode erstellt einen BufferedReader, der den Inhalt der Datei lesen kann.
	//Wenn die angegebene Datei nicht gefunden wird, wird eine FileNotFoundException ausgelöst.
	public void openForReading(String dataSource) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(dataSource));
	}

	public void openForWriting(String dataSource) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(dataSource)));
	}

	//Die Methode close() scheint eine Methode zum Schließen eines Writer- und/oder Reader-Objekts zu sein, die in einem Feld writer bzw. reader gespeichert werden.
	public boolean close() {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}
		}

		return true;
	}

	/**
	 * Method for reading the article data from an external file.
	 * The availability attribute is in the data source (file) as "i" (in stock) or "o" (out of stock)
	 * stored encoded.
	 *
	 * @return article object, if fetch successful, false null
	 */
	public Article readArticle() throws IOException {
		// Read number convert from String to int
		String numberString = readRow();
		if (numberString == null) {
			return null;
		} else {
			String[] splitted = numberString.split(";");
			int id = Integer.parseInt(splitted[0]);
			String title = splitted[1];
			int quantityInStockNumber = Integer.parseInt(splitted[2]);
			double price = Double.parseDouble(splitted[3]);

			// create and return a new article object
			return new Article(id, title, quantityInStockNumber, price);
		}
	}

	/**
	 * Method for writing item data to an external data source.
	 *
	 * @param newArticle Article to save
	 * @return true if write is successful, false otherwise
	 */
	public boolean saveArticle(Article newArticle, ArticleList existingArticles) {
		// Write all existing articles to the file
		while (existingArticles != null) {
			Article currentArticle = existingArticles.getFirstArticle();

			// only write the articles that have not been edited to file
			if(currentArticle.getNumber() != newArticle.getNumber()) {
				this.writeArticleToFile(currentArticle);
			} else {
				// Write the new article to file
				this.writeArticleToFile(newArticle);
			}
			existingArticles = existingArticles.getRemainingArticles();
		}
		// return true, if everything worked
		return true;
	}

	public boolean writeArticleToFile(Article article) {
		String articleString = article.getNumber() + ";" + article.getArticleTitle() + ";" + article.getQuantityInStock() + ";" + article.getPrice();

		// Write number
		writeLine(articleString);
		return true;
	}

	public Customer loadCustomer() throws IOException {
		// Variables
		int customerId;

		// First, the customer number is read in as a string and converted into an integer
		String customerIdString = readRow();
		if (customerIdString != null) {
			customerId = Integer.parseInt(customerIdString);

		} else {
			//No more data OR if the customer number is no longer in the file, returns null.
			return null;
		}
		// Date like name, lastname, email, username, street, postal code and city are being read from the file and saved into variables
		// Read name
		String name = readRow();

		// Read lastname
		String lastName = readRow();

		// Read street
		String street = readRow();

		// Read postalCode
		String postalCodeString = readRow();
		int postalCodeInt = Integer.parseInt(postalCodeString);

		// Read city
		String city = readRow();

		// Read email
		String email = readRow();

		// Read username
		String username = readRow();

		// Read password
		String password = readRow();

		//A new customer object is created with the read data and returned.
		Customer customer = new Customer(name, lastName, street, postalCodeInt, city, email, username, password);
		return customer;
	}

	//The saveCustomer(customer k) method writes the data of a customer object to a file.
	//The first name, last name, email, user name, password, street, postal code and city of the customer are written one after the other.
	//The method returns true if the write was successful.
	public boolean saveCustomer(Customer newCustomer, List<Customer> existingCustomers) throws IOException {
		// Write all existing customers to the file
		for (Customer customer : existingCustomers) {
			this.writeCustomerToFile(customer);
		}

		// Write the new customers to file
		this.writeCustomerToFile(newCustomer);

		// return true, if everything worked
		return true;
	}

	public void writeCustomerToFile(Customer customer) throws IOException {
		writeLine(String.valueOf(customer.getId()));
		writeLine(customer.getName());
		writeLine(customer.getLastName());
		writeLine(customer.getStreet());
		writeLine(String.valueOf(customer.getPostalCode()));
		writeLine(customer.getCity());
		writeLine(customer.getEmail());
		writeLine(customer.getUsername());
		writeLine(customer.getPassword());
	}

	@Override
	public Employee loadEmployee() throws IOException {
		// Variables
		int employeeId;						//Greta

		// First, the employee number is read in as a string and converted into an integer
		String employeeIdString = readRow();
		if (employeeIdString != null) {
			employeeId = Integer.parseInt(employeeIdString);

		} else {
			//No more data OR if the employee number is no longer in the file, returns null.
			return null;
		}
		// Date like name, lastname, email, username, street, postal code and city are being read from the file and saved into variables
		// Read name
		String name = readRow();

		// Read lastname
		String lastName = readRow();

		// Read username
		String username = readRow();

		// Read password
		String password = readRow();

		//A new employee object is created with the read data and returned.
		return new Employee(name, lastName, username, password);
	}

	@Override
	public boolean saveEmployee(Employee newEmployee, List<Employee> existingEmployees) {
		// Write all existing employees to the file
		for (Employee employee : existingEmployees) {
			this.writeEmployeeToFile(employee);
		}

		// Write new employee to file
		this.writeEmployeeToFile(newEmployee);

		// return true, if everything worked
		return true;
	}

	public void writeEmployeeToFile(Employee employee) {
		writeLine(String.valueOf(employee.getId()));
		writeLine(employee.getName());
		writeLine(employee.getLastName());
		writeLine(employee.getUsername());
		writeLine(employee.getPassword());
	}

	/*
	 * Private helper methods
	 * the readRow() method reads a line from a file and returns it as a string.
	 */
	private String readRow() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	//The writeLine(String data) method writes a string to a file.
	private void writeLine(String data) {
		if (writer != null) {
			writer.println(data);
		}
	}


}
