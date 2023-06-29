package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import shop.local.domain.exceptions.*;
import shop.local.domain.Shop;
import shop.local.entities.*;

/**
 * Very simple user interface class for the eshop. The user interface is based
 * on input and output on the command line, hence the name CUI (Command line
 * User Interface).
 *
 * @author Sund
 * @version 1
 */
public class EshopClientCUI {

	private static Shop eshop;
	private static BufferedReader in;
	private User loggedinUser = null;

	public static BufferedReader getIn() {
		return in;
	}

	public EshopClientCUI(String file) {
		// the shop administration handles the tasks that have nothing to do with
		// input/output
		//Datei existiert nicht exception
		try {
			eshop = new Shop(file);
			// Create Stream object for text input via console window
			in = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			System.out.println("File doesn't exist or could not be found.");
		}
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
		System.out.print("Commands: \n  Output articles:  'a'"); // \n ist ein Absatz
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
		System.out.print("Commands: \n  Output articles:  'a'"); // \n ist ein Absatz
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
	private boolean processInputFromEntryMenu(String line) {
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

	private void processInputForEmployeeMenu(String line) {
		// Get input
		switch (line) {
		// Output articles
		case "a":
			ArrayList<Article> articleList = eshop.getAllArticles();
			printArticleList(articleList);
			break;
		// Delete article:
		case "b":
			deleteArticle();
			break;
		// Insert article
		case "c":
			insertArticle();
			break;
		// Search article
		case "d":
			searchArticle();
			break;
		// Manage an article's inventory
		case "e":
			manageInventory();
			break;
		// create new employee
		case "f":
			registerEmployee();
			break;
		// show history
		case "g":
			showHistory();
			break;
		// logout
		case "l":
			logout();
			break;
		}
	}

	private void processInputForCustomerMenu(String line) {
		ArrayList<Article> articleList;
		// Get input
		switch (line) {
		// Output articles
		case "a":
			articleList = eshop.getAllArticles();
			printArticleList(articleList);
			break;
		// Add to SC
		case "b":
			addArticleToCart();
			break;
		// Change quantity in cart
		case "c":
			changeArticleQuantityInCart();
			break;
		// Remove from SC
		case "d":
			removeArticleFromCart();
			break;
		// View SC
		case "e":
			viewArticlesInCart();
			break;
		// Buy all in SC
		case "f":
			buyArticlesInCart();
			break;
		// clear cart
		case "g":
			deleteAllArticlesInCart();
			break;
		// Logout
		case "l":
			logout();
			break;
		}
	}

	private String readInput() throws IOException {
		// einlesen von Konsole
		return in.readLine();
	}

	private int readInt(String message, String errorMessage) throws IOException {
		int number = 0;
		boolean validInput = false;
		String numberString;

		while (!validInput) {
			try {
				System.out.print(message);
				numberString = readInput();
				if (!numberString.isEmpty()) {
					number = Integer.parseInt(numberString);
					validInput = true; // Break the loop if parsing succeeds
				} else {
					System.out.println("Fill in all fields.");
				}
			} catch (NumberFormatException e) {
				System.out.println(errorMessage);
			}
		}
		return number;
	}

	private double readDouble(String message, String errorMessage) throws IOException {
		double number = 0.0;
		boolean validInput = false;
		String numberString;

		while (!validInput) {
			try {
				System.out.print(message);
				numberString = readInput();
				if (!numberString.isEmpty()) {
					number = Double.parseDouble(numberString);
					validInput = true; // Break the loop if parsing succeeds
				} else {
					System.out.println("Fill in all fields.");
				}
			} catch (NumberFormatException e) {
				System.out.println(errorMessage);
			}
		}
		return number;
	}

	private String readString(String message) throws IOException {
		boolean validInput = false;
		String string = "";

		while (!validInput) {
				System.out.print(message);
				string = readInput();
				if (string.isEmpty()) {
					System.out.println("Invalid input. Fill in field.");
				} else {
					validInput = true;
				}
		}
		return string;
	}

	/*
	 * Methods for registering and logging in employees / customers, as well as
	 * logging out
	 */
	private void registerCustomer() {
		try {
			String name = readString("Your name: ");
			String lastName = readString("Your last name: ");
			String street = readString("Your street: ");
			int postalCode = readInt("Your postal code: ", "Invalid input. Please enter an integer value for the postal code.");
			String city = readString("Your city: ");
			String mail = readString("Your mail: ");
			String username = readString("Your username: ");
			String password = readString("Your password: ");
			String registerNow = readString("Register now 'yes' / 'no': ");

			String message = "";

			try {
				message = eshop.registerCustomer(name, lastName, street, postalCode, city, mail, username, password,
						registerNow);
				System.out.println(message);
			} catch (RegisterException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registerEmployee() {
		try {
			String name = readString("Name > ");
			String lastName = readString("Lastname > ");
			String userName = readString("Username > ");
			String password = readString("Password > ");

			String message = "";
			try {
				message = eshop.registerEmployee(name, lastName, userName, password);
				System.out.println(message);
			} catch (RegisterException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean customerLogin() {
		try {
			System.out.println("Please enter your login data:");
			String username = readString("Username > ");
			String password = readString("Password > ");

			try {
				loggedinUser = eshop.loginCustomer(username, password);
				System.out.println("You´re successfully logged in. Hello, Mr. / Mrs. " + loggedinUser.getLastName());
				return true;
			} catch (LoginException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean employeeLogin() {
		try {
			System.out.println("Please enter your login data:");
			String username = readString("Username > ");
			String password = readString("Password > ");

			try {
				loggedinUser = eshop.loginEmployee(username, password);
				System.out.println("You´re successfully logged in. Hello, Mr. / Mrs. " + loggedinUser.getLastName());
				return true;
			} catch (LoginException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void logout() {
		loggedinUser = null;
		System.out.println("\nYou got logged out successfully.\n");
	}

	/*
	 * methods for the employee
	 */
	private void deleteArticle() {
		try {
			int number = readInt("Article number > ", "Invalid input. Please enter an integer value for Article number.");

			try {
				eshop.deleteArticle(number, loggedinUser);
			} catch (ArticleNotFoundException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void searchArticle() {
		ArrayList<Article> articleList;
		try {
			String articleTitle = readString("Article title > ");

			try {
				articleList = eshop.searchByArticleTitle(articleTitle);
				printArticleList(articleList);
			} catch (ArticleNotFoundException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertArticle() {
		try {
			// Lese Title
			String articleTitle = readString("Article Title > ");

			// Lese quantity
			int initialQuantity = readInt("Initial quantity / stock > ", "Invalid input. Please enter an integer value for quantity.");
			while (initialQuantity <= 0) {
				System.out.println("Invalid input. Invalid input. Please enter a positive integer value for quantity.");
				initialQuantity = readInt("Initial quantity / stock > ", "Please enter a positive integer value for quantity.");
			}

			// Lese Preis
			double price = readDouble("Article price > ", "Invalid input. Please enter a double value for price.");

			// Lese Art des Artikels (Massengutartikel oder Einzelartikel)
			System.out.print("Article type (bulk/single) > ");
			String articleType = readInput();
			boolean validInput = false;

			while (!validInput) {
				if (articleType.equalsIgnoreCase("bulk") || articleType.equalsIgnoreCase("single")) {
					validInput = true; // Break the loop if input is valid
				} else {
					System.out.println("Invalid input. Please enter 'bulk' or 'single'.");
					System.out.print("Article type (bulk/single) > ");
					articleType = readInput();
				}
			}

			Article article;
			if (articleType.equalsIgnoreCase("bulk")) {
				// Lese Packungsgröße
				int packSize = readInt("Pack size > ", "Invalid input. Please enter an integer value for the pack size.");

				while (packSize <= 0) {
					System.out.println("Invalid input. Please enter a positive integer value for pack size.");
					packSize = readInt("Pack size > ", "Please enter a positive integer value for pack size.");
				}

				article = new BulkArticle(articleTitle, initialQuantity, price, packSize);
			} else {
				article = new Article(articleTitle, initialQuantity, price);
			}

			try {
				eshop.insertArticle(article, initialQuantity, loggedinUser);
				System.out.println("Article saved successfully");
			} catch (ArticleAlreadyExistsException e) {
				System.out.println("\n" + e.getMessage() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void manageInventory() {
		try {
			// Lese Artikelbezeichnung
			int number = readInt("Article number > ", "Invalid input. Please enter an integer value for Article number.");

			// Try to find article by number and gives it to the variable article
			Article article;
			try {
				article = eshop.searchByArticleNumber(number);
				System.out.println("Found article \n" + article.toString());
			} catch (ArticleNotFoundException e) {
				System.out.println("\n" + e.getMessage() + "\n");
				return;
			}

			//Get quantity change
			int stockChange = readInt("Please enter how many items you'd like to add (positive number) or to retrieve from stock (negative number): ", "Invalid input. Please enter an integer value.");

			// Try to change inventory
			if (stockChange < 0) {
				boolean success = true;
				try {
					success = eshop.decreaseArticleStock(article, (-1) * stockChange, "ESHOP_Article.txt", loggedinUser);
				} catch (StockDecreaseException s){
					System.out.println("\n" + s.getMessage() + "\n");
				}
				if (success) {
					System.out.println("Successfully decreased article's stock.");
				}
			} else {
				eshop.increaseArticleStock(article, stockChange, "ESHOP_Article.txt", loggedinUser);
				System.out.println("Successfully increased article's stock.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Methods for employee to output all swaps in and outs to console
	 */
	public void showHistory() {
		try {
			//TODO wenn funktioniert, exceptions einbauen und die untere Zeile einblenden.
			//int articleID = readInt("Enter article number you want to see the history from: ", "Invalid input. Please enter an integer value for article number.");
			System.out.println("Enter article number you want to see the history from: ");
			int articleID = Integer.parseInt(readInput());
			Map<Date, Integer> eventsList = eshop.getEventsbyArticleOfLast30Days(articleID);
			//int finalStockQuantityOfTheLast30Days = eshop.getOriginalStock(articleID, eventsList);
			System.out.println("For the article with the ID: " + articleID + ", the stock quantity in the last few days were as followed:");
			int sum = 0;
			try {
				sum = eshop.searchByArticleNumber(articleID).getQuantityInStock();
			} catch (ArticleNotFoundException a){
				System.out.println("\n" + a.getMessage() + "\n");
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			for (Date date : eventsList.keySet()) {
				sum -= eventsList.get(date);
				System.out.println(dateFormat.format(date) + ", Stock quantity: " + sum);
			}
			//System.out.println("Stock Quantity ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * methods for the customer
	 */

	private void printArticleList(ArrayList<Article> list) {
		for (Article article : list)
			System.out.println(article);
	}

	private void addArticleToCart() {
		try {
			if (loggedinUser instanceof Customer) {
				Customer customer = (Customer) loggedinUser;

				// Input vom Benutzer entgegennehmen
				int articleNumber = readInt("Enter article number: ", "Invalid input. Please enter an integer value for article number.");

				// Überprüfen, ob der Artikel tatsächlich im Bestand vorhanden ist
				Article article;
				try {
					article = eshop.searchByArticleNumber(articleNumber);
				} catch (ArticleNotFoundException e){
					System.out.println("\n" + e.getMessage() + "\n");
					return;
				}

				int quantity = readInt("Enter number of items you wish to add: ", "Please enter a positive integer value for the quantity.");

				while (quantity <= 0) {
					System.out.println("Invalid input. Please enter a positive integer value for the quantity.");
					quantity = readInt("Enter number of items you wish to add: ", "Please enter a positive integer value for the quantity.");
				}

				try {
					System.out.println(eshop.addArticleToCart(article, quantity, (Customer) loggedinUser));
				} catch (BulkArticleException | InsufficientStockException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeArticleQuantityInCart() {
		try {
			if (loggedinUser instanceof Customer) {
				Customer customer = (Customer) loggedinUser;

				// Take input from the user
				int articleNumber = readInt("Enter article number: ", "Invalid input. Please enter an integer value for article number.");

				// Check if the item is actually in stock
				Article article;

				try {
					article = eshop.searchByArticleNumber(articleNumber);
				} catch (ArticleNotFoundException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					return;
				}

				int newQuantity = readInt("Enter new quantity: ", "Invalid input. Please enter a positive integer value for the new quantity.");

				while (newQuantity < 0) {
					System.out.println("Invalid input. Please enter a positive integer value for the new quantity.");
					newQuantity = readInt("Enter new quantity: ", "Invalid input. Please enter a positive integer value for the new quantity.");
				}

				try {
					System.out.println(eshop.changeArticleQuantityInCart(newQuantity, article, (Customer) loggedinUser));
				} catch (ArticleInCartNotFoundException | BulkArticleException | InsufficientStockException a) {
					System.out.println("\n" + a.getMessage() + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void removeArticleFromCart() {
	    try {
			if (loggedinUser instanceof Customer) {
				Customer customer = (Customer) loggedinUser;
				int articleNumber = readInt("Enter article number: ", "Invalid input. Please enter an integer value for article number.");

				// check whether the item really exists in the shop
				Article article;
				try {
					article = eshop.searchByArticleNumber(articleNumber);
				} catch (ArticleNotFoundException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					return;
				}

				try {
					System.out.println(eshop.removeArticleFromCART(customer, article));
				} catch (ArticleInCartNotFoundException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void viewArticlesInCart() {
		if (loggedinUser instanceof Customer) {
			Customer customer = (Customer) loggedinUser;
			try {
				System.out.println(eshop.viewArticlesInCart((Customer) loggedinUser));
			} catch (EmptyCartException c) {
				System.out.println("\n" + c.getMessage() + "\n");
			}
		}
	}

	private void buyArticlesInCart() {
		try {
			if (loggedinUser instanceof Customer) {
				Customer customer = (Customer) loggedinUser;
				ShoppingCart shoppingCart = customer.getShoppingCart();
				// The buyArticles(shoppingCart) method is called to carry out the purchase of the items in the shopping cart.
				// The result is an invoice that is stored in the variable invoice.
				Invoice invoice = null;
				try {
					invoice = eshop.buyArticles(shoppingCart, loggedinUser);

					// print which articles couldn't be purchased
					articlesCouldntPurchase(invoice);
					// print which articles could be purchased
					articlePurchaseSuccessfully(invoice);

				} catch (EmptyCartException | ArticleBuyingException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void articlesCouldntPurchase(Invoice invoice) {
		if (invoice.getUnavailableItems() != null && invoice.getUnavailableItems().size() > 0) {
			System.out.println("Unfortunately some of the items you wished to purchase became unavailable:");
			// If this is the case, a loop is used to iterate over each unavailable item in
			// the list invoice.getUnavailableItems()
			for (ShoppingCartItem item : invoice.getUnavailableItems()) {
				// The unavailable articles are printed on the console
				System.out.println(item.toString());
			}
		}
	}

	public void articlePurchaseSuccessfully(Invoice invoice) {
		if (invoice.getPositions() != null && invoice.getPositions().size() > 0) {
			System.out.println("You successfully purchased:");
			// With a loop, iterates over each successfully purchased item.
			for (ShoppingCartItem item : invoice.getPositions()) {
				// Articles are displayed on the console
				System.out.println(item.toString());
			}
			// print date and total
			System.out.println("\nTotal: " + invoice.getTotal() + " EUR\n");
			System.out.println("Date: " + invoice.getFormattedDate() + " Uhr" + "\n");
			invoice.setCustomer((Customer) loggedinUser);
			System.out.println("Your delivery address: \n" + invoice.getCustomerAddress() + "\n");
			System.out.println("Please transfer the full amount to the following bank account: \nSpice Shop \nDE35 1511 0000 1998 1997 29 \nBIC: SCFBDE33 \n");
		}
	}

	private void deleteAllArticlesInCart() {
		if (loggedinUser instanceof Customer) {
			Customer customer = (Customer) loggedinUser;
			try {
				System.out.println(eshop.deleteAllArticlesInCart((Customer) loggedinUser));
			} catch (EmptyCartException e){
				System.out.println("\n" + e.getMessage() + "\n");
			}
		}
	}

	/*
	 * Methods of running the program
	 */
	public void run() {
		try {
			// Variables for console input
			printEntryMenu();
			String input = readInput();
			processInputFromEntryMenu(input);
			// boolean entryMenu = true;

			// Print general menu
			while (!"q".equals(input)) {
				if (loggedinUser != null) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Variable of type "EshopClientCUI" is declared but not yet initialized!
		EshopClientCUI cui;
		// A new object of "EshopClientCUI" is created. The file and the string "ESHOP"
		// are passed as parameters or only the file named "ESHOP" is passed
		cui = new EshopClientCUI("ESHOP");
		// The "run" method is called on the "cui" object to run the program
		cui.run();
		// If an error occurs during this, an "IOException" is thrown
	}
}
