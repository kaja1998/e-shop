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

	//Die erste Methode openForReading(String datei) öffnet eine Datei zum Lesen.
	//Der Parameter datei ist der Name der Datei, die geöffnet werden soll.
	//Die Methode erstellt einen BufferedReader, der den Inhalt der Datei lesen kann.
	//Wenn die angegebene Datei nicht gefunden wird, wird eine FileNotFoundException ausgelöst.
	public void openForReading(String dataSource) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(dataSource));
	}

	public void openForWriting(String dataSource) throws IOException {
		//Das PrintWriter-Objekt wird der Variablen writer zugewiesen
		//new PrintWriter(new BufferedWriter(new FileWriter(dataSource))): Ein PrintWriter wird erstellt und erhält den BufferedWriter als Argument. Der PrintWriter stellt Methoden zum Schreiben von formatierten Daten bereit.
		//new BufferedWriter(new FileWriter(dataSource)): Ein BufferedWriter wird erstellt und erhält den FileWriter als Argument. Der BufferedWriter ermöglicht das effiziente Schreiben von Daten in den Speicher.
		//FileWriter wird erstellt, der die Datei für den Schreibvorgang öffnet. Der dataSource-Parameter gibt den Pfad oder Namen der Datei an, in die geschrieben werden soll.
		writer = new PrintWriter(new BufferedWriter(new FileWriter(dataSource)));
	}

	//Die Methode close() ist eine Methode zum Schließen eines Writer- und/oder Reader-Objekts, die in einem Feld writer bzw. reader gespeichert werden.
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
//	public boolean addArticle(Article newArticle, ArticleList existingArticles) {
//		// Write all existing articles to the file
//		while (existingArticles != null) {
//			Article currentArticle = existingArticles.getFirstArticle();
//
//			// only write the articles that have not been edited to file
//			if(currentArticle.getNumber() != newArticle.getNumber()) {
//				this.writeArticleToFile(currentArticle);
//			} else {
//				// Write the new article to file
//				this.writeArticleToFile(newArticle);
//			}
//			existingArticles = existingArticles.getRemainingArticles();
//		}
//		// return true, if everything worked
//		return true;
//	}

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
//	public boolean deleteArticle(Article articleToDelete, ArrayList<Article> existingArticles) {
//		// Write all existing articles to the file
//		while (existingArticles != null) {
//			// only write the articles that should be kept to file
//			// do not write the article that should be deleted to file
//			Article currentArticle = existingArticles.getFirstArticle();
//			if(currentArticle.getNumber() != articleToDelete.getNumber()) {
//				this.writeArticleToFile(currentArticle);
//			}
//			existingArticles = existingArticles.getRemainingArticles();
//		}
//		// return true, if everything worked
//		return true;
//	}

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
		String articleString = article.getNumber() + ";" + article.getArticleTitle() + ";" + article.getQuantityInStock() + ";" + article.getPrice();

		// Write number
		writeLine(articleString);
		return true;
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
