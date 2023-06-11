package shop.local.ui.cui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import shop.local.entities.Invoice;
import shop.local.entities.ShoppingCart;
import shop.local.entities.ShoppingCartItem;
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

	public EshopClientCUI(String file) throws IOException {
		//the shop administration handles the tasks that have nothing to do with input/output
		eshop = new Shop(file);

		// Create Stream object for text input via console window
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	/*
	 * Methoden zur Ausgabe der Menüs.
	 */
	private void printEntryMenu() {
		System.out.print("What would you like to do?");
		System.out.print("         \n  Login as a customer: 'cl'");
		System.out.print("         \n  Register as a customer: 'cr'");
		System.out.print("         \n  Login as employee: 'el'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Quit:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // output without nl
	}

	private void printEmployeeMenu() {
		System.out.print("Commands: \n  Output articles:  'a'");        // \n ist ein Absatz
		System.out.print("          \n  Delete article: 'b'");
		System.out.print("          \n  Insert article: 'c'");
		System.out.print("          \n  Search article:  'd'");
		System.out.print("          \n  Manage an article's inventory:  'e'");
		System.out.print("          \n  Create new employee:  'f'");
		System.out.print("          \n  Show history:  'g'");
		System.out.print("          \n  Logout:  'l'");
		System.out.print("          \n  ---------------------");
		System.out.println("        \n  Quit:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	private void printCustomerMenu() {
		System.out.print("Commands: \n  Output articles:  'a'");        // \n ist ein Absatz
		System.out.print("          \n  Add article into shopping cart:  'b'");
		System.out.print("          \n  Change quantity of an item in the shopping cart:  'c'");
		System.out.print("          \n  Remove article from shopping cart:  'd'");
		System.out.print("          \n  View shopping cart:  'e'");
		System.out.print("          \n  Buy articles:  'f'");
		System.out.print("          \n  clear complete cart:  'g'");
		System.out.print("          \n  Logout:  'l'");
		System.out.print("          \n  ---------------------");
		System.out.println("        \n  Quit:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	/*
	 * Methoden zur Verarbeitung der Menüauswahlen
	 */
	private boolean processInputFromEntryMenu(String line) throws IOException {
		switch (line) {
			case "cr":
				registerCustomer();
				return true;
			case "cl":
				return !customerLogin();
			case "el":
				return !employeeLogin();
			case "q":
				return false;
		}
		return false;
	}

	private void processInputForEmployeeMenu(String line) throws IOException {
		// Get input
		switch(line) {
			//Output articles
			case "a":
				ArrayList<Article> articleList = eshop.getAllArticles();
				printArticleList(articleList);
				break;
			//Delete article:
			case "b":
				deleteArticle();
				break;
			//Insert article
			case "c":
				insertArticle();
				break;
			//Search article
			case "d":
				searchArticle();
				break;
			//Manage an article's inventory
			case "e":
				manageInventory();
				break;
			//create new employee
			case "f":
				registerEmployee();
				break;
			//show history
			case "g":
				showHistory();
				break;
			//logout
			case "l":
				logout();
				break;
		}
	}

	private void processInputForCustomerMenu(String line) throws IOException {
		ArrayList<Article> articleList;
		// Get input
		switch(line) {
			//Output articles
			case "a":
				articleList = eshop.getAllArticles();
				printArticleList(articleList);
				break;
			//Add to SC
			case "b":
				addArticleToCart();
				break;
			//Change quantity in cart
			case "c":
				changeArticleQuantityInCart();
				break;
			//Remove from SC
			case "d":
				removeArticleFromCart();
				break;
			//View SC
			case "e":
				viewArticlesInCart();
				break;
			//Buy all in SC
			case "f":
				buyArticlesInCart();
				//clear cart
			case "g":
				deleteAllArticlesInCart();
				break;
			//Logout
			case "l":
				logout();
				break;
		}
	}

	private String readInput() throws IOException {
		// einlesen von Konsole
		return in.readLine();
	}

	/*
	 * Methoden zum Registrieren und Einloggen von Mitarbeitern / Kunden, sowie Logout
	 */
//	private void registerCustomer() throws IOException {
//		//The data from the file is read and added to the ArrayList of customers
//		System.out.println("Your name: ");
//		String name = readInput();
//		System.out.println("Your last name: ");
//		String lastName = readInput();
//		System.out.println("Your street: ");
//		String street = readInput();
//		System.out.println("Your postal code: ");
//		int postalCode = Integer.parseInt(readInput());
//		System.out.println("Your city: ");
//		String city = readInput();
//		System.out.println("Your mail: ");
//		String mail = readInput();
//		System.out.println("Your username: ");
//		String username = readInput();
//		System.out.println("Your password: ");
//		String password = readInput();
//		System.out.println("Register now 'yes' / 'no': ");
//		String registerNow = readInput();
//
//		//Check if registration wants to do
//		if (registerNow.equals("yes")) {  //Wenn man Strings auf Gleichheit überprüfen möchten, sollten man den Operator "==" nicht verwenden. Der Operator "==" prüft, ob die beiden Variablen dieselbe Referenz auf dasselbe Objekt haben, was bei Strings oft nicht der Fall ist. Stattdessen sollte man die equals()-Methode verwenden, um Strings auf Gleichheit zu prüfen.
//			//Erstelle Variable vom Typ Kunde und übergebe die Eingaben des Kunden an den Konstruktor
//			Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);
//			boolean customerAlreadyExists = false;
//
//			//Check if user already exists.
//			//First I get the list of all customers from the shop and save it in an instance variable called customer list of type ArrayList<Customer>, which I can freely use in this (EshopClientCUI).
//			List<Customer> customerList = eshop.getCustomers();
//
//			//Dann gehe ich mit einer for-Loop durch die Liste aller Kunden durch.
//			//Die Schleife durchläuft jedes Element in der customerList und weist es der Variable k zu
//			for (Customer k : customerList) {
//				//In dem Body der Schleife wird dann jedes Kunde-Objekt k mit dem customer-Objekt verglichen.
//				//Der Ausdruck customer.equals(k) führt eine Gleichheitsprüfung zwischen customer und k durch
//				//und gibt true zurück, wenn die beiden Objekte gleich sind.
//				if (customer.equals(k)) {
//					// wenn es den Kunden schon gibt, System.out.println("User mit gleichem Namen existiert bereits.");
//					System.out.println("User with this name already exists");
//					customerAlreadyExists = true;
//				}
//			}
//			if(!customerAlreadyExists) {
//				//Wenn kein Kunde gefunden wird, dann kann der Kunde registriert werden.
//				//Kunde wird zur Liste hinzugefügt, indem das Shop-Objekt die Methode in der Klasse KundenVerwaltung aufruft
//				try {
//					eshop.writeCustomerData("ESHOP_Customer.txt", customer);
//				} catch (IOException e) {
//					// TODO
//					e.printStackTrace();
//				}
//				eshop.addCustomer(customer);
//				System.out.println("Registration successful.");
//			}
//		}
//	}

	private void registerCustomer() throws IOException {
		//The data from the file is read and added to the ArrayList of customers
		System.out.println("Your name: ");
		String name = readInput();
		System.out.println("Your last name: ");
		String lastName = readInput();
		System.out.println("Your street: ");
		String street = readInput();
		System.out.println("Your postal code: ");
		int postalCode = Integer.parseInt(readInput());
		System.out.println("Your city: ");
		String city = readInput();
		System.out.println("Your mail: ");
		String mail = readInput();
		System.out.println("Your username: ");
		String username = readInput();
		System.out.println("Your password: ");
		String password = readInput();
		System.out.println("Register now 'yes' / 'no': ");
		String registerNow = readInput();

		//Check if registration wants to do
		if (registerNow.equals("yes")) {  //Wenn man Strings auf Gleichheit überprüfen möchten, sollten man den Operator "==" nicht verwenden. Der Operator "==" prüft, ob die beiden Variablen dieselbe Referenz auf dasselbe Objekt haben, was bei Strings oft nicht der Fall ist. Stattdessen sollte man die equals()-Methode verwenden, um Strings auf Gleichheit zu prüfen.
			//Erstelle Variable vom Typ Kunde und übergebe die Eingaben des Kunden an den Konstruktor
			Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);
			boolean customerAlreadyExists = eshop.checkCustomerExists(customer);

			if (!customerAlreadyExists) {
				try {
					eshop.registerCustomer(customer);
					System.out.println("Registration successful.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("User with this name already exists.");
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
		List<Employee> employees = eshop.getEmployees();
		boolean employeeAlreadyExists = false;
		//Gehe ich mit einer for-Loop durch die Liste aller Employees durch.
		//Die Schleife durchläuft jedes Element in der employeeList und weist es der Variable currentEmployee zu
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
				eshop.writeEmployeeData("ESHOP_Employee.txt", employee);
			} catch (IOException e) {
				// TODO
				e.printStackTrace();
			}
			eshop.addEmployee(employee);
			System.out.println("Registration successful.");
		}
	}

	private boolean customerLogin() throws IOException {
		System.out.println("Please enter your login data:");
		System.out.println("Username: ");
		String username = readInput();
		System.out.println("Password: ");
		String password = readInput();
		loggedinUser = eshop.loginCustomer(username, password);
		if (loggedinUser != null) {
			System.out.println("You´re successfully logged in. Hello, Mr. / Mrs. " + loggedinUser.getLastName());
			return true;
		} else {
			System.out.println("Incorrect Username oder password.");
			return false;
		}
	}

	private boolean employeeLogin() throws IOException {
		System.out.println("Please enter your login data:");
		System.out.println("Username: ");
		String username = readInput();
		System.out.println("Password: ");
		String password = readInput();

		loggedinUser = eshop.loginEmployee(username, password);
		if (loggedinUser != null) {
			System.out.println("You´re successfully logged in. Hello, Mr. / Mrs. " + loggedinUser.getLastName());
			return true;
		} else {
			System.out.println("Incorrect Username oder password.");
			return false;
		}
	}

	private void logout() throws IOException {
		loggedinUser = null;
		System.out.println("\nYou got logged out successfully.\n");
	}

	/*
	 * Methoden für den Mitarbeiter
	 */
	private void deleteArticle() throws IOException {
		// lies die notwendigen Parameter, einzeln pro Zeile
		System.out.print("Article number > ");
		String numberString = readInput();
		int number = Integer.parseInt(numberString);
		eshop.deleteArticle(number, loggedinUser);
	}

	private void searchArticle() throws IOException {
		ArrayList<Article> articleList;
		System.out.print("Article title > ");
		String articleTitle = readInput();
		articleList = eshop.searchByArticleTitle(articleTitle);
		printArticleList(articleList);
	}

	private void insertArticle () throws IOException {
		// Lese Artikelbezeichnung
		System.out.print("Article title  > ");
		String articleTitle = readInput();

		// Lese Wert für initialen Artikelbestand
		System.out.print("Initial quantity / stock > ");
		String initialQuantityString = readInput();
		int initialQuantity = Integer.parseInt(initialQuantityString);

		// Lese Preis
		System.out.print("Article price  > ");
		String priceString = readInput();
		double price = Double.parseDouble(priceString);

		// Speichere Artikel
		try {
			eshop.insertArticle(articleTitle, initialQuantity, price, loggedinUser);
			System.out.println("Article saved successfully");

		} catch (ArticleAlreadyExistsException e) {
			// TODO - funktioniert nicht (liegt wahrscheinlich daran, dass er die IDs vergleicht, die natürlich unterschiedlich sind)
			// Hier Fehlerbehandlung...
			System.out.println("Error saving article");
			e.printStackTrace();
		}
	}

	private void manageInventory() throws IOException {
		//Lese Artikelbezeichnung
		System.out.print("Article number > ");
		int number = Integer.parseInt(readInput());

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
			boolean success = eshop.decreaseArticleStock(article, (-1)*stockChange,"ESHOP_Article.txt", loggedinUser);
			if(success) {
				System.out.println("Successfully decreased article's stock.");
			} else {
				System.out.println("Could not decrease stock. Maybe you tried to retrieve more items than there are available?");
			}
		} else {
			eshop.increaseArticleStock(article, stockChange, "ESHOP_Article.txt", loggedinUser);
			System.out.println("Successfully increased article's stock.");
		}
	}

	/*
	 * Methoden für den Mitarbeiter, um alle Ein- und Auslagerungen auf Konsole auszugeben
	 */
	public void showHistory() {
		List<Event> eventsList = eshop.getEvents();
		for (Event e : eventsList) {
			System.out.println(e);
		}
	}

	/*
	 * Methoden für den Kunden
	 */
//	private void printArticleList(ArrayList<Article> liste) {
//		System.out.print(liste);
//	}

	private void printArticleList(ArrayList<Article> liste) {
		for (Article article : liste) {
			System.out.println(article);
		}
	}

	private void addArticleToCart() throws IOException {
		int articleNumber;
		int quantity;

		if (loggedinUser instanceof Customer) {
			Customer customer = (Customer) loggedinUser;

			//Input vom User entgegennehmen
			System.out.println("Enter article number: ");
			String articleNumberString = readInput();
			articleNumber = Integer.parseInt((articleNumberString));
			System.out.print("Enter quantity: ");
			String quantityString = readInput();
			quantity = Integer.parseInt(quantityString);

			//checken, ob es den Artikel wirklich gibt im Bestand
			Article article = eshop.searchByArticleNumber(articleNumber);
			if (article != null) {
				//Variable vom Typ shoppingCart wird deklariert.
				//Mit dem Customer-Objekt wird die Methode getShoppingCart aufgerufen in welcher der Warenkorb des Kunden zurückgegeben wird
				ShoppingCart shoppingCart = customer.getShoppingCart();
				//Methode in ShoppingCart wird aufgerufen, mit der geprüft wird, ob der Artikel bereits im Warenkorb existiert
				if (shoppingCart.cartContainsArticle(article)) {
					//Überprüfen, ob die eingegebene Menge gültig ist
					if (quantity >= 1) {
						for (ShoppingCartItem item : shoppingCart.getCartItems()) {
							if (item.getArticle().equals(article)) {
								//gucken, ob der Artikel noch vorrätig ist
								int availableQuantity = article.getQuantityInStock() - item.getQuantity();
								if (availableQuantity >= quantity) {
									shoppingCart.addUpArticleQuantity(article, quantity);
									System.out.println("Article quantity was updated successfully in the cart.");
									//Warenkorb ausgeben
									shoppingCart.read();
								} else { //Wenn nein, dann ausgeben, dass der Artikel out of stock ist
									System.out.println("Could not put article into the Cart, because desired quantity must be not available.");
								}
							}
						}
					} else {
						System.out.println("Please input a positive number for quantity.");
					}
					//wenn Artikel nicht im Warenkorb liegt
				} else {
					//Überprüfen, ob die eingegebene Menge gültig ist
					if (quantity >= 1) {
						//gucken, ob der Artikel noch vorrätig ist
						if (article.getQuantityInStock() >= quantity) {
							//Methode addArticle wird aufgerufen und akzeptiert angegebenen Parameter
							shoppingCart.addArticle(article, quantity);
							System.out.println("Article/s were added successfully into the cart.");
							//Warenkorb wird ausgegeben
							shoppingCart.read();
						} else { //Wenn nein, dann ausgeben, dass der Artikel out of stock ist
							System.out.println("Could not put article into the Cart, because it must be out of stock.");
						}
					} else {
						System.out.println("Please input a positive number for quantity.");
					}
				}
			} else {
				System.out.println("Article not found.");
			}
		}
	}

	private void changeArticleQuantityInCart() throws IOException {
		int articleNumber;
		int newQuantity;

		if (loggedinUser instanceof Customer) {
			Customer customer = (Customer) loggedinUser;

			//Input vom User entgegennehmen
			System.out.println("Enter article number: ");
			String articleNumberString = readInput();
			articleNumber = Integer.parseInt((articleNumberString));
			System.out.print("Enter new quantity: ");
			String quantityString = readInput();
			newQuantity = Integer.parseInt(quantityString);

			//checken, ob es den Artikel wirklich gibt im Shop
			Article article = eshop.searchByArticleNumber(articleNumber);
			if (article != null) {
				// Variable vom Typ shoppingCart wird deklariert. Mit dem Customer-Objekt wird die Methode getShoppingCart aufgerufen in welcher der Warenkorb des Kunden zurückgegeben wird
				ShoppingCart shoppingCart = customer.getShoppingCart();
				// Methode addArticle wird aufgerufen und akzeptiert angegebenen Parameter
				String updateResult = shoppingCart.updateArticleQuantity(article, newQuantity);
				if (updateResult != null) {
					System.out.println(updateResult);
				}
				//Warenkorb wird ausgegeben
				shoppingCart.read();
			} else {
				System.out.println("Article not found.");
			}
		}
	}

	private void removeArticleFromCart() throws IOException {
		int articleNumber;

		if (loggedinUser instanceof Customer) {
			Customer customer = (Customer) loggedinUser;
			System.out.println("Enter article number: ");
			String articleNumberString = readInput();
			articleNumber = Integer.parseInt((articleNumberString));
			//checken, ob es den Artikel wirklich gibt im Shop
			Article article = eshop.searchByArticleNumber(articleNumber);
			if (article != null) {
				ShoppingCart shoppingCart = customer.getShoppingCart();
				//Artikel aus dem Warenkorb löschen
				shoppingCart.deleteSingleArticle(article);
			} else {
				System.out.println("Article not found.");
			}
		}
	}

	private void viewArticlesInCart() {
		//sicherstellen, dass der eingeloggte Benutzer ein Customer ist
		if (loggedinUser instanceof Customer) {
			//Warenkorb des Kunden wird abgerufen und in lokaler Variable shoppingCartItems gespeichert.
			//Der Rückgabewert ist eine Liste von ShoppingCartItem-Objekten, die in der Variablen shoppingCartItems gespeichert wird.
			List<ShoppingCartItem> shoppingCartItems = eshop.getUsersShoppingCart((Customer) loggedinUser);
			//Danach wird überprüft, ob shoppingCartItems nicht null ist und mindestens ein Element enthält.
			if(shoppingCartItems != null && shoppingCartItems.size() > 0) {
				//Wenn beides der Fall ist, wird eine Schleife verwendet, um über jedes ShoppingCartItem in der Liste zu iterieren.
				System.out.println("In your shopping cart are the following items:");
				for (ShoppingCartItem item : shoppingCartItems) {
					//Artikel wird/werden auf der Konsole ausgegeben.
					System.out.println(item.toString());
				}
			} else {
				System.out.println("There are no items in your cart yet.");
			}
		}
	}

	private void buyArticlesInCart() throws IOException {
		////sicherstellen, dass der eingeloggte Benutzer ein Customer ist.
		if (loggedinUser instanceof Customer) {
			//Wenn erfüllt, dann wird loggedinUser-Objekt in eine Variable customer vom Typ Customer umgewandelt.
			Customer customer = (Customer) loggedinUser;
			//Warenkorb des Kunden wird abgerufen. Der zurückgegebene Wert wird in der Variable shoppingCart gespeichert.
			ShoppingCart shoppingCart = customer.getShoppingCart();
			//Methode buyArticles(shoppingCart) wird aufgerufen, um den Kauf der Artikel im Warenkorb durchzuführen.
			//Das Ergebnis ist eine Rechnung (Invoice), die in der Variable invoice gespeichert wird.
			Invoice invoice = eshop.buyArticles(shoppingCart, loggedinUser);

			//print which articles couldn't be purchased
			//Es wird überprüft, ob es Artikel gibt, die nicht gekauft werden konnten, indem überprüft wird, ob invoice.getUnavailableItems() nicht null ist und mindestens ein Element enthält.
			if(invoice.getUnavailableItems() != null && invoice.getUnavailableItems().size() > 0) {
				System.out.println("Unfortunately some of the items you wished to purchase became unavailable:");
				//Wenn dies der Fall ist, wird eine Schleife verwendet, um über jeden nicht verfügbaren Artikel in der Liste invoice.getUnavailableItems() zu iterieren
				for (ShoppingCartItem item : invoice.getUnavailableItems()) {
					//Die nicht verfügbaren Artikeln werden auf der Konsole ausgegeben
					System.out.println(item.toString());
				}
			}

			// print which articles were purchased successfully
			//Dann wird überprüft, ob es Artikel gibt, die erfolgreich gekauft wurden, indem überprüft wird, ob invoice.getPositions() nicht null ist und mindestens ein Element enthält.
			if(invoice.getPositions() != null && invoice.getPositions().size() > 0) {
				System.out.println("You successfully purchased:");
				//Mit Schleife wird über jeden erfolgreich gekauften Artikel iteriert.
				for (ShoppingCartItem item : invoice.getPositions()) {
					//Artikeln werden auf der Konsole ausgegeben
					System.out.println(item.toString());
				}
			}

			// print date and total
			System.out.println("Total: " + invoice.getTotal() + "\n");
			System.out.println("Date: " + invoice.getFormattedDate() + " Uhr"+ "\n");
			invoice.setCustomer((Customer) loggedinUser);
			System.out.println("Your delivery address: \n" + invoice.getCustomerAddress() + "\n");
			System.out.println("Please transfer the full amount to the following bank account: \nSpice Shop \nDE35 1511 0000 1998 1997 29 \nBIC: SCFBDE33 \n");
		}
	}

	private void deleteAllArticlesInCart() throws IOException {
		////sicherstellen, dass der eingeloggte Benutzer ein Customer ist.
		if (loggedinUser instanceof Customer) {
			//Wenn erfüllt, dann wird loggedinUser-Objekt in eine Variable customer vom Typ Customer umgewandelt.
			Customer customer = (Customer) loggedinUser;
			//Warenkorb des Kunden wird abgerufen. Der zurückgegebene Wert wird in der Variable shoppingCart gespeichert.
			ShoppingCart shoppingCart = customer.getShoppingCart();
			//Methode buyArticles(shoppingCart) wird aufgerufen, um den Kauf der Artikel im Warenkorb durchzuführen.
			//Das Ergebnis ist eine Rechnung (Invoice), die in der Variable invoice gespeichert wird.
			shoppingCart.deleteAll();
			System.out.println("All Articles were removed successfully from the cart."+ "\n");
		}
	}

	/*
	 * Methoden zur Ausführung des Programms
	 */
	public void run() throws IOException {
		// Variables for console input
		printEntryMenu();
		String input = readInput();
		processInputFromEntryMenu(input);
		//boolean entryMenu = true;

		// Print general menu
		while(!"q".equals(input)) {
			if(loggedinUser != null) {
				if (this.loggedinUser instanceof Employee) {
					printEmployeeMenu();
					try {
						input = readInput();
						processInputForEmployeeMenu(input);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// Print menu for customers
				if (this.loggedinUser instanceof Customer) {
					printCustomerMenu();
					try {
						input = readInput();
						processInputForCustomerMenu(input);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				printEntryMenu();
				input = readInput();
				processInputFromEntryMenu(input);
			}
		}
	}

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
