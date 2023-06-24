package shop.local.persistence;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import shop.local.domain.ArticleAdministration;
import shop.local.domain.CustomerAdministration;
import shop.local.domain.EmployeeAdministration;
import shop.local.entities.*;

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

	//The first method openForReading(String file) opens a file for reading.
	//The file parameter is the name of the file to open.
	//The method creates a BufferedReader that can read the contents of the file.
	//If the specified file is not found, a FileNotFoundException is thrown.
	public void openForReading(String dataSource) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(dataSource));
	}

	public void openForWriting(String dataSource) throws IOException {
		//The PrintWriter object is assigned to the writer variable
		//new PrintWriter(new BufferedWriter(new FileWriter(dataSource))): A PrintWriter is created and takes the BufferedWriter as an argument. The PrintWriter provides methods for writing formatted data.
		//new BufferedWriter(new FileWriter(dataSource)): A BufferedWriter is created and takes the FileWriter as an argument. The BufferedWriter allows data to be written to memory efficiently.
		//FileWriter is created, which opens the file for writing. The dataSource parameter specifies the path or name of the file to write to.
		writer = new PrintWriter(new BufferedWriter(new FileWriter(dataSource)));
	}

	//The close() method is a method for closing a writer and/or reader object stored in a writer or reader field, respectively.
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

			if (splitted.length > 4) {
				// It's a BulkArticle, read the pack size
				int packSize = Integer.parseInt(splitted[4]);
				return new BulkArticle(id, title, quantityInStockNumber, price, packSize);
			} else {
				// It's a regular Article
				return new Article(id, title, quantityInStockNumber, price);
			}
		}
	}

	/**
	 * Method for writing item data to an external data source.
	 *
	 * @param newArticle Article to save
	 * @return true if write is successful, false otherwise
	 */
	public boolean addArticles(Article newArticle, ArrayList<Article> existingArticles) {
		// Write all existing articles to the file
		for (Article article : existingArticles){
			this.writeArticleToFile(article);
		}
		// Write the new article to file
		//this.writeArticleToFile(newArticle);
		// return true, if everything worked
		return true;
	}

	/**
	 * Method for writing item data to an external data source.
	 *
	 * @return true if write is successful, false otherwise
	 */
	public boolean deleteArticle(Article articleToDelete, ArrayList<Article> existingArticles) {
		//Erstelle eine neue ArrayList für die Artikel die behalten werden sollen
		ArrayList<Article> articlesToKeep = new ArrayList<>();

		// check all existing articles in List
		for (Article currentArticle : existingArticles) {
			// check if the article who should be deleted is in list
			if (currentArticle.getNumber() != articleToDelete.getNumber()) {
				articlesToKeep.add(currentArticle);
			}
		}
		//weite articles to keep in file
		for(Article article : articlesToKeep) {
			this.writeArticleToFile(article);
		}
		return true;
	}


	public boolean writeArticleToFile(Article article) {
		if (article instanceof BulkArticle){
			String bulkArticleString = article.getNumber() + ";" + article.getArticleTitle() + ";" + article.getQuantityInStock() + ";" + article.getPrice()  + ";" + ((BulkArticle) article).getPackSize();
			writeLine(bulkArticleString);
			return true;
		} else {
			String articleString = article.getNumber() + ";" + article.getArticleTitle() + ";" + article.getQuantityInStock() + ";" + article.getPrice();
			writeLine(articleString);
			return true;
		}
	}

	public Customer loadCustomer() throws IOException {
		// Read number convert from String to int
		String numberString = readRow();
		if (numberString == null) {
			return null;
		} else {
			String[] splitted = numberString.split(";");
			int id = Integer.parseInt(splitted[0]);
			String name = splitted[1];
			String lastName = splitted[2];
			String street = splitted[3];
			int postalCode = Integer.parseInt(splitted[4]);
			String city = splitted[5];
			String email = splitted[6];
			String username = splitted[7];
			String password = splitted[8];

			// create and return a new article object
			return new Customer(id, name, lastName, street, postalCode, city, email, username, password);
		}
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

	public boolean writeCustomerToFile(Customer customer) throws IOException {

		String customerString = customer.getId() + ";" + customer.getName() + ";" + customer.getLastName() + ";" + customer.getStreet() + ";" + customer.getPostalCode() + ";" + customer.getCity() + ";" + customer.getEmail() + ";" + customer.getUsername() + ";" + customer.getPassword();
		// Write number
		writeLine(customerString);
		return true;
	}

	@Override
	public Employee loadEmployee() throws IOException {
		// Read number convert from String to int
		String numberString = readRow();
		if (numberString == null) {
			return null;
		} else {
			String[] splitted = numberString.split(";");
			int id = Integer.parseInt(splitted[0]);
			String name = splitted[1];
			String lastName = splitted[2];
			String username = splitted[3];
			String password = splitted[4];

			// create and return a new article object
			return new Employee(id, name, lastName, username, password);
		}
	}

	@Override
	public boolean saveEmployee(Employee newEmployee, List<Employee> existingEmployees) {
		// Write all existing employees + the new one to the file
		for (Employee employee : existingEmployees) {
			this.writeEmployeeToFile(employee);
		}

		// Write new employee to file
		this.writeEmployeeToFile(newEmployee);

		// return true, if everything worked
		return true;
	}

	public boolean writeEmployeeToFile(Employee employee) {

		String employeeString = employee.getId() + ";" + employee.getName() + ";" + employee.getLastName() + ";" + employee.getUsername() + ";" + employee.getPassword();
		// Write number
		writeLine(employeeString);
		return true;
	}


	@Override
	public Event loadEvent( ArticleAdministration articleAdministration, EmployeeAdministration employeeAdministration, CustomerAdministration customerAdministration) throws IOException {
		// Read number convert from String to int
		String numberString = readRow();
		if (numberString == null) {
			return null;
		} else {
			String[] splitted = numberString.split(";");
			int userId = Integer.parseInt(splitted[0]);
			int articleId = Integer.parseInt(splitted[1]);
			int quantity = Integer.parseInt(splitted[2]);
			String date = splitted[3];
			int eventTypeOrdinal = Integer.parseInt(splitted[4]);
			Event.EventType type = Event.EventType.values()[eventTypeOrdinal];

			User user;
			if (employeeAdministration.getUserByID(userId) != null){
				user = employeeAdministration.getUserByID(userId);
			} else {
				user = customerAdministration.getUserByID(userId);
			}
			Article article = articleAdministration.getArticleByID(articleId);

			// create and return a new article object
			return new Event(type, user, article, quantity, date);
		}
	}


	public boolean saveEvent(List<Event> existingEvents) {
		// Write all existing Events to the file
		for (Event event : existingEvents) {
			this.writeEventToFile(event);
		}
		// return true, if everything worked
		return true;
	}

	public boolean writeEventToFile(Event event) {
		// Write number
		writeLine(event.toFileString());
		return true;
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
