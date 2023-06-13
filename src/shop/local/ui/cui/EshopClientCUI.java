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
	 * Methods for outputting the menus.
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
	 * Methods for processing the menu selections
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
	 * Methods for registering and logging in employees / customers, as well as logging out
	 */

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
		if (registerNow.equals("yes")) {
			//Erstelle Variable vom Typ Kunde und übergebe die Eingaben des Kunden an den Konstruktor
			Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);
			boolean customerAlreadyExists = eshop.checkCustomerExists(customer);

			if (!customerAlreadyExists) {
				try {
					eshop.registerCustomer(customer);
					System.out.println("Registration successful.");
				} catch (IOException e) {
					// TODO exception
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
	 * methods for the employee
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

	private void insertArticle() throws IOException {
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

		// Lese Art des Artikels (Massengutartikel oder Einzelartikel)
		System.out.print("Article type (bulk/single) > ");
		String articleType = readInput();

		Article article;

		if (articleType.equalsIgnoreCase("bulk")) {
			// Lese Packungsgröße
			System.out.print("Pack size > ");
			String packSizeString = readInput();
			int packSize = Integer.parseInt(packSizeString);

			// Erstelle Massengutartikel
			article = new BulkArticle(articleTitle, initialQuantity, price, packSize);
		} else {
			// Erstelle Einzelartikel
			article = new Article(articleTitle, initialQuantity, price);
		}

		// Speichere Artikel
		try {
			eshop.insertArticle(article, initialQuantity, loggedinUser);
			System.out.println("Article saved successfully");
		} catch (ArticleAlreadyExistsException e) {
			// TODO - doesn't work (probably because he's comparing the IDs, which of course are different)
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
	 * Methods for employee to output all swaps in and outs to console
	 */
	public void showHistory() throws IOException {
		System.out.println("Enter article number you want to see the history from: ");
		int articleID = Integer.parseInt(readInput());
		List<Event> eventsList = eshop.getEventsbyArticleOfLast30Days(articleID);
		for (Event e : eventsList) {
			System.out.println(e);
		}
	}

	/*
	 * methods for the customer
	 */

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

			// Input vom Benutzer entgegennehmen
			System.out.println("Enter article number: ");
			String articleNumberString = readInput();
			articleNumber = Integer.parseInt(articleNumberString);

			// Überprüfen, ob der Artikel tatsächlich im Bestand vorhanden ist
			Article article = eshop.searchByArticleNumber(articleNumber);
			if (article != null) {
				// Variable vom Typ ShoppingCart wird deklariert.
				// Mit dem Customer-Objekt wird die Methode getShoppingCart aufgerufen, um den Warenkorb des Kunden zurückzugeben
				ShoppingCart shoppingCart = customer.getShoppingCart();

				// Überprüfen, ob der Artikel ein BulkArticle ist
				if (article instanceof BulkArticle) {
					BulkArticle bulkArticle = (BulkArticle) article;
					int packSize = bulkArticle.getPackSize();

					// Ausgeben der Packgröße
					System.out.println("This article can only be purchased in packs of " + packSize + ".");

					// Eingabe der Packgröße bzw. der Menge entgegennehmen
					System.out.print("Enter the number of packs you wish to add: ");
					String packSizeQuantityString = readInput();
					int packSizeQuantity = Integer.parseInt(packSizeQuantityString);

					// Überprüfen, ob die eingegebene Packgröße bzw. Menge gültig ist
					if (packSizeQuantity >= 1) {
						int quantityToAdd = packSizeQuantity * packSize;

						// Überprüfen, ob die gewünschte Menge noch vorrätig ist
						int availableQuantity = article.getQuantityInStock();
						if (availableQuantity >= quantityToAdd) {
							shoppingCart.addArticle(article, quantityToAdd);
							System.out.println("Article/s were added successfully to the cart.");
							// Warenkorb ausgeben
							shoppingCart.read();
						} else {
							System.out.println("Could not put article into the Cart, because desired quantity must be not available.");
						}
					} else {
						System.out.println("Please input a positive number.");
					}
				} else {
					System.out.print("Enter quantity: ");
					String quantityString = readInput();
					quantity = Integer.parseInt(quantityString);
					// Einzelartikel
					// Überprüfen, ob die eingegebene Menge gültig ist
					if (quantity >= 1) {
						// Überprüfen, ob der Artikel noch vorrätig ist
						int availableQuantity = article.getQuantityInStock();
						if (availableQuantity >= quantity) {
							shoppingCart.addArticle(article, quantity);
							System.out.println("Article/s were added successfully to the cart.");
							// Warenkorb ausgeben
							shoppingCart.read();
						} else {
							System.out.println("Could not put article into the Cart, because desired quantity must be not available..");
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

			// Take input from the user
			System.out.println("Enter article number: ");
			String articleNumberString = readInput();
			articleNumber = Integer.parseInt(articleNumberString);

			// Check if the item is actually in stock
			Article article = eshop.searchByArticleNumber(articleNumber);
			if (article != null) {

				// Variable of type shoppingCart is declared. The getShoppingCart method is called with the Customer object, in which the customer's shopping cart is returned
				ShoppingCart shoppingCart = customer.getShoppingCart();

				// Check if the article is a BulkArticle
				if (article instanceof BulkArticle) {
					BulkArticle bulkArticle = (BulkArticle) article;
					int packSize = bulkArticle.getPackSize();

					// Print the pack size
					System.out.println("This article can only be purchased in packs of " + packSize + ".");

					// Accept input of pack size or quantity
					System.out.print("Enter the new number of packs you wish to have in your shopping cart: ");
					String newPackSizeQuantityString = readInput();
					int newPackSizeQuantity = Integer.parseInt(newPackSizeQuantityString);

					if (newPackSizeQuantity >= 1) {
						int quantityToChange = newPackSizeQuantity * packSize;

						// Check if the desired quantity is still in stock
						int availableQuantity = article.getQuantityInStock();
						if (availableQuantity >= quantityToChange) {
							String updateResult = shoppingCart.updateArticleQuantity(article, quantityToChange);
							if (updateResult != null) {
								System.out.println(updateResult);
							}

							// Check if the shopping cart is not empty and print the shopping cart
							if (!shoppingCart.getCartItems().isEmpty()) {
								shoppingCart.read();
							}
						} else {
							System.out.println("Could not change article quantity in the cart. Desired quantity is not available.");
						}
					} else {
						System.out.println("Please input a positive number for the number of packs.");
					}
				} else {
					System.out.print("Enter new quantity: ");
					String quantityString = readInput();
					newQuantity = Integer.parseInt(quantityString);

					// single item
					// Check if the amount entered is valid
					if (newQuantity >= 1) {
						// Check if the item is still in stock
						int availableQuantity = article.getQuantityInStock();
						if (availableQuantity >= newQuantity) {
							String updateResult = shoppingCart.updateArticleQuantity(article, newQuantity);
							if (updateResult != null) {
								System.out.println(updateResult);
							}
							// Check if the shopping cart is not empty and print the shopping cart
							if (!shoppingCart.getCartItems().isEmpty()) {
								shoppingCart.read();
							}
						} else {
							System.out.println("Could not change article quantity in the cart. Desired quantity is not available.");
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

	private void removeArticleFromCart() throws IOException {
		int articleNumber;

		if (loggedinUser instanceof Customer) {
			Customer customer = (Customer) loggedinUser;
			System.out.println("Enter article number: ");
			String articleNumberString = readInput();
			articleNumber = Integer.parseInt((articleNumberString));
			//check whether the item really exists in the shop
			Article article = eshop.searchByArticleNumber(articleNumber);
			if (article != null) {
				ShoppingCart shoppingCart = customer.getShoppingCart();
				//Delete item from shopping cart
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
		//make sure the logged in user is a customer.
		if (loggedinUser instanceof Customer) {
			//If true, then loggedinUser object is cast to a customer variable of type Customer.
			Customer customer = (Customer) loggedinUser;
			//Customer's shopping cart is retrieved. The returned value is stored in the shoppingCart variable.
			ShoppingCart shoppingCart = customer.getShoppingCart();
			//The buyArticles(shoppingCart) method is called to carry out the purchase of the items in the shopping cart.
			//The result is an invoice that is stored in the variable invoice.
			Invoice invoice = eshop.buyArticles(shoppingCart, loggedinUser);

			//print which articles couldn't be purchased
			//Checking if there are any items that could not be purchased by checking that invoice.getUnavailableItems() is not null and contains at least one item.
			if(invoice.getUnavailableItems() != null && invoice.getUnavailableItems().size() > 0) {
				System.out.println("Unfortunately some of the items you wished to purchase became unavailable:");
				//If this is the case, a loop is used to iterate over each unavailable item in the list invoice.getUnavailableItems()
				for (ShoppingCartItem item : invoice.getUnavailableItems()) {
					//The unavailable articles are printed on the console
					System.out.println(item.toString());
				}
			}

			// print which articles were purchased successfully
			//Then it checks if there are any items that were successfully purchased by checking that invoice.getPositions() is non-null and contains at least one item.
			if(invoice.getPositions() != null && invoice.getPositions().size() > 0) {
				System.out.println("You successfully purchased:");
				//With a loop, iterates over each successfully purchased item.
				for (ShoppingCartItem item : invoice.getPositions()) {
					//Articles are displayed on the console
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
		//make sure the logged in user is a customer.
		if (loggedinUser instanceof Customer) {
			//If true, then loggedinUser object is cast to a customer variable of type Customer.
			Customer customer = (Customer) loggedinUser;
			//Customer's shopping cart is retrieved. The returned value is stored in the shoppingCart variable.
			ShoppingCart shoppingCart = customer.getShoppingCart();
			//The buyArticles(shoppingCart) method is called to carry out the purchase of the items in the shopping cart.
			//The result is an invoice that is stored in the variable invoice.
			shoppingCart.deleteAll();
			System.out.println("All Articles were removed successfully from the cart."+ "\n");
		}
	}

	/*
	 * Methods of running the program
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
		//Variable of type "EshopClientCUI" is declared but not yet initialized!
		EshopClientCUI cui;
		try {
			//A new object of "EshopClientCUI" is created. The file and the string "ESHOP" are passed as parameters or only the file named "ESHOP" is passed
			cui = new EshopClientCUI("ESHOP");
			//The "run" method is called on the "cui" object to run the program
			cui.run();
			//If an error occurs during this, an "IOException" is thrown
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Error message "e.printStackTrace()" is output
			e.printStackTrace();
		}
	}
}
