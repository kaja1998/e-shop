package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.domain.Shop;
import shop.local.entities.*;


/**
 * Very simple user interface class for the eshop.
 * The user interface is based on input and output on the command line,
 * hence the name CUI (Command line User Interface).
 *
 * @author Sund
 * @version 1 (managing articles in a linked list)
 */
public class EshopClientCUI {

	private Shop eshop;
	private BufferedReader in;
	private User loggedinUser = null;
	private Scanner scanner = new Scanner(System.in);							// Scanner registration

	public EshopClientCUI(String file) throws IOException {
		// the shop administration handles the tasks that have nothing to do with input/output
		eshop = new Shop(file);

		// Create Stream object for text input via console window
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	private void printEntryMenu() {
		System.out.print("What would you like to do?");
		System.out.print("         \n  Login as a customer: 'cl'");
		System.out.print("         \n  Register as a customer: 'cr'");
		System.out.print("         \n  Login as employee: 'e'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Quit:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // output without nl
	}

	private void registerCustomer() {
		//The data from the file is read and added to the ArrayList of customers
		System.out.println("Your name: ");
		String name = scanner.nextLine();
		System.out.println("Your last name: ");
		String lastName = scanner.nextLine();
		System.out.println("Your street: ");
		String street = scanner.nextLine();
		System.out.println("Your postal code: ");
		int postalCode = Integer.parseInt(scanner.nextLine());
		System.out.println("Your city: ");
		String city = scanner.nextLine();
		System.out.println("Your mail: ");
		String mail = scanner.nextLine();
		System.out.println("Your username: ");
		String username = scanner.nextLine();
		System.out.println("Your password: ");
		String password = scanner.nextLine();
		System.out.println("Register now 'yes' / 'no': ");
		String registerNow = scanner.nextLine();

		//Check if registration wants to do
		if (registerNow.equals("yes")) {  //Wenn man Strings auf Gleichheit überprüfen möchten, sollten man den Operator "==" nicht verwenden. Der Operator "==" prüft, ob die beiden Variablen dieselbe Referenz auf dasselbe Objekt haben, was bei Strings oft nicht der Fall ist. Stattdessen sollte man die equals()-Methode verwenden, um Strings auf Gleichheit zu prüfen.
			//Erstelle Variable vom Typ Kunde und übergebe die Eingaben des Kunden an den Konstruktor
			Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);
			boolean customerAlreadyExists = false;

			//Check if user already exists.
			//First I get the list of all customers from the shop and save it in an instance variable called customer list of type ArrayList<Customer>, which I can freely use in this (EshopClientCUI).
			List<Customer> customerList = eshop.getCustomers();

			//Dann gehe ich mit einer for-Loop durch die Liste aller Kunden durch.
			//Die Schleife durchläuft jedes Element in der customerList und weist es der Variable k zu
			for (Customer k : customerList) {
				//In dem Body der Schleife wird dann jedes Kunde-Objekt k mit dem customer-Objekt verglichen.
				//Der Ausdruck customer.equals(k) führt eine Gleichheitsprüfung zwischen customer und k durch
				//und gibt true zurück, wenn die beiden Objekte gleich sind.
				if (customer.equals(k)) {
					// wenn es den Kunden schon gibt, System.out.println("User mit gleichem Namen existiert bereits.");
					System.out.println("User with this name already exists");
					customerAlreadyExists = true;
				}
			}
			if(!customerAlreadyExists) {
				//Wenn kein Kunde gefunden wird, dann kann der Kunde registriert werden.
				//Kunde wird zur Liste hinzugefügt, indem das Shop-Objekt die Methode in der Klasse KundenVerwaltung aufruft
				try {
					eshop.writeCustomerData("ESHOP_C.txt", customer);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				eshop.addCustomer(customer);
				System.out.println("Registration successful.");
				}
			}
	}

	private void registerEmployee() throws IOException {
		// Lese Daten für Name, Nachname, Benutzername und Passwort
		System.out.print("Name > ");
		String name = readInput();
		System.out.print("Lastname > ");
		String lastname = readInput();
		System.out.print("Username > ");
		String username = readInput();
		System.out.print("Password > ");
		String password = readInput();

		//Erstelle Variable vom Typ Employee und übergebe die Eingaben des Employee an den Konstruktor
		Employee employee = new Employee(name, lastname, username, password);

		// Prüfe, ob Employee bereits existiert
		//Gehe ich mit einer for-Loop durch die Liste aller Employees durch.
		//Die Schleife durchläuft jedes Element in der employeeList und weist es der Variable currentEmployee zu
		List<Employee> employees = eshop.getEmployees();
		boolean employeeAlreadyExists = false;
		for (Employee currentEmployee : employees) {
			//In dem Body der Schleife wird dann jedes Employee-Objekt currentEmployee mit dem employee-Objekt verglichen.
			if (employee.equals(currentEmployee)) {
				System.out.println("User with this name already exists");
				employeeAlreadyExists = true;
			}
		}

		if(!employeeAlreadyExists) {
			//Wenn kein Employee gefunden wird, dann kann der Employee registriert werden.
			//Employee wird zur Liste hinzugefügt, indem das Shop-Objekt die Methode in der Klasse EmployeeAdministration aufruft
			try {
				eshop.writeEmployeeData("ESHOP_E.txt", employee);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			eshop.addEmployee(employee);
			System.out.println("Registration successful.");
		}
	}

	private boolean customerLogin() {
		System.out.println("Please enter your login data:");
		System.out.println("Username: ");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		loggedinUser = eshop.loginCustomer(username, password);
		if (loggedinUser != null) {
			System.out.println("You´re successfully logged in. Hello, Mr. / Mrs. " + loggedinUser.getLastName());
			return true;
		} else {
			System.out.println("Incorrect Username oder password.");
			return false;
		}
	}

	private boolean employeeLogin() {
		System.out.println("Please enter your login data:");
		System.out.println("Username: ");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();

		loggedinUser = eshop.loginEmployee(username, password);
		if (loggedinUser != null) {
			System.out.println("You´re successfully logged in. Hello, Mr. / Mrs. " + loggedinUser.getLastName());
			return true;
		} else {
			System.out.println("Incorrect Username oder password.");
			return false;
		}
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Ausgabe des Menüs.
	 */
	private void printEmployeeMenu() {
		System.out.print("Commands: \n  Output articles:  'a'");		// \n ist ein Absatz
		System.out.print("          \n  Output customers:  'b'");
		System.out.print("          \n  Delete article: 'd'");
		System.out.print("          \n  Insert article: 'e'");
		System.out.print("          \n  Search article:  'f'");
		System.out.print("          \n  Manage an article's inventory:  'g'");
		System.out.print("          \n  Create new employee:  'n'");
		System.out.print("          \n  Save data:  's'");
		System.out.print("          \n  ---------------------");
		System.out.println("        \n  Quit:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	/* (non-Javadoc)
	 *
	 * Interne (private) Methode zur Ausgabe des Menüs.
	 */
	private void printCustomerMenu() {
		System.out.print("Commands: \n  Output articles:  'a'");		// \n ist ein Absatz
		System.out.print("          \n  Add article into shopping cart:  'b'");
		System.out.print("          \n  Remove article from shopping cart:  'c'");
		System.out.print("          \n  View shopping cart:  'd'");
		System.out.print("          \n  Buy articles:  'e'");
		System.out.print("          \n  Logout:  'f'");
		System.out.print("          \n  ---------------------");
		System.out.println("        \n  Quit:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zum Einlesen von Benutzereingaben.
	 */
	private String readInput() throws IOException {
		// einlesen von Konsole
		return in.readLine();
	}

	/* (non-Javadoc)
	 *
	 * Interne (private) Methode zur Verarbeitung von Eingaben
	 * und Ausgabe von Ergebnissen.
	 */
	private boolean processInputFromEntryMenu(String line) throws IOException {
		switch (line) {
			case "cr":
				registerCustomer();
				return true;
			case "cl":
				return !customerLogin();
			case "e":
				return !employeeLogin();
			case "q":
				return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Verarbeitung von Eingaben
	 * und Ausgabe von Ergebnissen.
	 */
	private void processInputForEmployeeMenu(String line) throws IOException {
		String numberString;
		int number;
		String articleTitle;
		ArticleList articleList;
		
		// Get input
		switch(line) {
			case "a":
				articleList = eshop.getAllArticles();		//eshop ist ein Objekt der Klasse Shop
				printArticleList(articleList);
				break;
			case "d":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Article number > ");
				numberString = readInput();
				number = Integer.parseInt(numberString);
				System.out.print("Article title  > ");
				articleTitle = readInput();
				eshop.deleteArticle(articleTitle, number);
				break;
			case "e":
				// Lese Artikelbezeichnung
				System.out.print("Article title  > ");
				articleTitle = readInput();

				// Lese Wert für initialen Artikelbestand
				System.out.print("Initial quantity / stock > ");
				String initialQuantityString = readInput();
				int initialQuantity = Integer.parseInt(initialQuantityString);

				// Speichere Artikel
				try {
					Article article = eshop.insertArticle(articleTitle, initialQuantity);
					eshop.writeArticleData("ESHOP_A.txt", article);
					System.out.println("Article saved successfully");
				} catch (ArticleAlreadyExistsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Error saving article");
					e.printStackTrace();
				}
				break;
			case "f":
				System.out.print("Article title > ");
				articleTitle = readInput();
				articleList = eshop.searchByArticleTitle(articleTitle);
				printArticleList(articleList);
				break;
			case "g":
				System.out.print("Article number > ");
				number = Integer.parseInt(readInput());
				manageInventory(number);
				break;
			case "n":
				registerEmployee();
				break;
			case "s":
				//eshop.writeArticleData();
		}
	}

	private void processInputForCustomerMenu(String line) throws IOException {
		ArticleList aList;
		// Get input
		switch(line) {
			//Output articles
			case "a":
				aList = eshop.getAllArticles();
				printArticleList(aList);
				break;
			//Add to SC
			case "b":
				//eshop.addArticle();
				break;
			//Remov from SC
			case "c":
				break;
			//View SC
			case "d":
				break;
			//Buy all in SC
			case "e":
				//Logout
			case "f":
				break;
		}
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zum Ausgeben von Artikellisten.
	 *
	 */
	private void printArticleList(ArticleList liste) {
		// Einfach nur Aufruf der toString()-Methode von ArtikelListe
		System.out.print(liste);
	}

	/* (non-Javadoc)
	 *
	 * Interne (private) Methode zum Ändern des Artikelbestandes
	 *
	 */
	private void manageInventory(int number) throws IOException {
		// Try to find article by number and gives it to the variable article
		Article article = eshop.searchByArticleNumber(number);

		// Check if article was found
		if(article != null) {
			System.out.println("Found article \n" + article.toString());
		} else {
			System.out.println("Article not found");
			return;
		}

		// Get quantity change
		System.out.println("Please enter how many items you'd like to add (positive number) or to retrieve from stock (negative number)");
		String stockChangeString = readInput();
		int stockChange = Integer.parseInt(stockChangeString);

		// Try to change inventory
		if(stockChange < 0) {
			boolean success = eshop.decreaseArticleStock(article, (-1)*stockChange);
			if(success) {
				eshop.writeArticleData("ESHOP_A.txt", article);
				System.out.println("Successfully decreased article's stock.");
			} else {
				System.out.println("Could not decrease stock. Maybe you tried to retrieve more items than there are available?");
			}
		} else {
			eshop.increaseArticleStock(article, stockChange);
			eshop.writeArticleData("ESHOP_A.txt", article);
			System.out.println("Successfully increased article's stock.");
		}
	}

	/**
	 * Method of executing the main loop:
	 * - Print menu
	 * - Read the user's input
	 * - Process input and output result
	 */
	public void run() throws IOException {
		// Variables for console input
		String input = "";
		boolean entryMenu = true;

		// Print general menu
		do {
			printEntryMenu();
			try {
				input = readInput();
				entryMenu = processInputFromEntryMenu(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (entryMenu);

		// print menu for employees
		if(this.loggedinUser instanceof Employee) {
			do {
				printEmployeeMenu();
				try {
					input = readInput();
					processInputForEmployeeMenu(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (!input.equals("q"));
		}

		// TODO
		// print menu for customers
		if(this.loggedinUser instanceof Customer) {
			do {
				printCustomerMenu();
				try {
					input = readInput();
					processInputForCustomerMenu(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (!input.equals("q"));
		}

		System.out.println("Finished");
	}

	
	/**
	 * The main-method...
	 */
	public static void main(String[] args) {
		//Variable vom Typ "EshopClientCUI" wird deklariert, aber noch nicht initialisiert!
		EshopClientCUI cui;
		try {
			//Ein neues Objekt von "EshopClientCUI" wird erzeugt. Dabei wird die Datei und der String "ESHOP" als Parameter übergeben oder es wird nur die Datei namens "ESHOP" übergeben
			cui = new EshopClientCUI("ESHOP");
			//Die "run"-Methode wird mit dem "cui"-Objekt aufgerufen, um das Programm auszuführen
			cui.run();
		//Wenn währenddessen ein Fehler auftritt, wird eine "IOException" geworfen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Fehlermeldung "e.printStackTrace()" wird ausgegeben
			e.printStackTrace();
		}
	}
}
