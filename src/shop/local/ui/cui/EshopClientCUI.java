package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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

	public static Shop getEshop() {
		return eshop;
	}

	public static BufferedReader getIn() {
		return in;
	}

	public EshopClientCUI(String file) throws IOException {
		// the shop administration handles the tasks that have nothing to do with
		// input/output
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

	/*
	 * Methods for registering and logging in employees / customers, as well as
	 * logging out
	 */

	private void registerCustomer() {
		try {
			System.out.println("Your name: ");
			String name = readInput();
			System.out.println("Your last name: ");
			String lastName = readInput();
			System.out.println("Your street: ");
			String street = readInput();
			System.out.print("Your postal code: ");
		    String postalCodeString = readInput();
		    int postalCode = 0;
		    boolean validInput = false;

		    while (!validInput) {
		        try {
		            postalCode = Integer.parseInt(postalCodeString);
		            validInput = true; // Break the loop if parsing succeeds
		        } catch (NumberFormatException e) {
		            System.out.println("Invalid input. Please enter an integer value for the postal code.");
		            System.out.print("Your postal code: ");
		            postalCodeString = readInput();
		        }
		    }
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
			System.out.print("Name > ");
			String name = readInput();
			System.out.print("Lastname > ");
			String lastname = readInput();
			System.out.print("Username > ");
			String username = readInput();
			System.out.print("Password > ");
			String password = readInput();

			String message = "";
			try {
				message = eshop.registerEmployee(name, lastname, username, password);
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
			System.out.println("Username: ");
			String username = readInput();
			System.out.println("Password: ");
			String password = readInput();

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
			System.out.println("Username: ");
			String username = readInput();
			System.out.println("Password: ");
			String password = readInput();

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
			System.out.print("Article number > ");
			String numberString = readInput();
			int number = 0;
			boolean validInput = false;

			while (!validInput) {
				try {
					number = Integer.parseInt(numberString);
					validInput = true; // Break the loop if parsing succeeds
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter an integer value for the article number.");
					System.out.print("Article number > ");
					numberString = readInput();
				}
			}
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
			while (true) {
				System.out.print("Article title > ");
				String articleTitle = readInput();

				if (articleTitle.trim().isEmpty() || articleTitle.matches(".*\\d+.*")) {
					System.out.println("Invalid input. Please enter a valid article title.");
					continue; // Starte die Schleife erneut, um eine gültige Eingabe zu erhalten
				}

				try {
					articleList = eshop.searchByArticleTitle(articleTitle);
					printArticleList(articleList);
					break; // Beende die Schleife, wenn die Suche erfolgreich war
				} catch (ArticleNotFoundException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					break; // Beende die Schleife, wenn der Artikel nicht gefunden wurde
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insertArticle() {
		try {
			// Lese Artikelbezeichnung
			System.out.print("Article title  > ");
			String articleTitle = readInput();

			// Lese Wert für initialen Artikelbestand
			System.out.print("Initial quantity / stock > ");
			int initialQuantity = 0;
			boolean validInput = false;

			while (!validInput) {
				try {
					String initialQuantityString = readInput();
					initialQuantity = Integer.parseInt(initialQuantityString);
					validInput = true; // Break the loop if parsing succeeds
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter an integer value.");
				}
			}

			// Lese Preis
			System.out.print("Article price > ");
			String priceString = readInput();
			double price = 0.0;
			validInput = false;

			while (!validInput) {
				try {
					price = Double.parseDouble(priceString);
					validInput = true; // Break the loop if parsing succeeds
				} catch (NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid number.");
					System.out.print("Article price > ");
					priceString = readInput();
				}
			}

			// Lese Art des Artikels (Massengutartikel oder Einzelartikel)
			System.out.print("Article type (bulk/single) > ");
			String articleType = readInput();
			boolean validInputT = false;

			while (!validInputT) {
				if (articleType.equalsIgnoreCase("bulk") || articleType.equalsIgnoreCase("single")) {
					validInputT = true; // Break the loop if input is valid
				} else {
					System.out.println("Invalid input. Please enter 'bulk' or 'single'.");
					System.out.print("Article type (bulk/single) > ");
					articleType = readInput();
				}
			}

			Article article;
			if (articleType.equalsIgnoreCase("bulk")) {
				// Lese Packungsgröße
				System.out.print("Pack size > ");
				String packSizeString = readInput();
				int packSize = 0;
				boolean validInputSize = false;
				while (!validInputSize) {
					try {
						packSize = Integer.parseInt(packSizeString);
						validInputSize = true; // Break the loop if parsing succeeds
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter an integer value for the pack size.");
						System.out.print("Pack size > ");
						packSizeString = readInput();
					}
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
			 int number = 0;
				boolean validInput = false;

				while (!validInput) {
					System.out.print("Article number > ");
					try {
						number = Integer.parseInt(readInput());
						validInput = true; // Input is valid, exit the loop
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please provide an integer value.");
					}
				}

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
			System.out.println("Please enter how many items you'd like to add (positive number) or to retrieve from stock (negative number): ");
			String stockChangeString = readInput();
			int stockChange = Integer.parseInt(stockChangeString);

			// Try to change inventory
			if (stockChange < 0) {
				boolean success = eshop.decreaseArticleStock(article, (-1) * stockChange, "ESHOP_Article.txt",
						loggedinUser);
				if (success) {
					System.out.println("Successfully decreased article's stock.");
				} else {
					System.out.println(
							"Could not decrease stock. Maybe you tried to retrieve more items than there are available?");
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
			System.out.println("Enter article number you want to see the history from: ");
			int articleID = Integer.parseInt(readInput());
			HashMap<String, Integer> eventsList = eshop.getEventsbyArticleOfLast30Days(articleID);
			//int finalStockQuantityOfTheLast30Days = eshop.getOriginalStock(articleID, eventsList);
			System.out.println("For the article with the ID: " + articleID + ", the stock quantity in the last few days were as followed:");
			int sum = 0;
			try {
				sum = eshop.searchByArticleNumber(articleID).getQuantityInStock();
			} catch (ArticleNotFoundException a){
				System.out.println("\n" + a.getMessage() + "\n");
			}
			for (String date : eventsList.keySet()) {
				sum -= eventsList.get(date);
				System.out.println(date + ", Stock quantity: " + sum);
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
	    int articleNumber = 0;
		int quantity = 0;
		try {
			if (loggedinUser instanceof Customer) {
				Customer customer = (Customer) loggedinUser;

				// Input vom Benutzer entgegennehmen
				 System.out.print("Enter article number: ");
				    String articleNumberString = readInput();
				    boolean validInput = false;

				    while (!validInput) {
				        try {
				            articleNumber = Integer.parseInt(articleNumberString);
				            validInput = true; // Break the loop if parsing succeeds
				        } catch (NumberFormatException e) {
				            System.out.println("Invalid input. Please enter an integer value for the article number.");
				            System.out.print("Enter article number: ");
				            articleNumberString = readInput();
				        }
				    }

				// Überprüfen, ob der Artikel tatsächlich im Bestand vorhanden ist
				Article article;
				try {
					article = eshop.searchByArticleNumber(articleNumber);
				} catch (ArticleNotFoundException e){
					System.out.println("\n" + e.getMessage() + "\n");
					return;
				}

				System.out.print("Enter number of items you wish to add: ");
				String quantityString = readInput();
				validInput = false;

				while (!validInput) {
					try {
						quantity = Integer.parseInt(quantityString);
						validInput = true; // Break the loop if parsing succeeds
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter an integer value.");
						System.out.print("Enter number of items you wish to add: ");
						quantityString = readInput();
					}
				}

				try {
					System.out.println(eshop.addArticleToCart(article, quantity, (Customer) loggedinUser));
				} catch (BulkArticleException b) {
					System.out.println("\n" + b.getMessage() + "\n");
				} catch (InsufficientStockException i) {
					System.out.println("\n" + i.getMessage() + "\n");
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
				System.out.print("Enter article number: ");
				String articleNumberString = readInput();
				int articleNumber = 0;
				boolean validInput = false;

				while (!validInput) {
					try {
						articleNumber = Integer.parseInt(articleNumberString);
						validInput = true; // Break the loop if parsing succeeds
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter an integer value for the article number.");
						System.out.print("Enter article number: ");
						articleNumberString = readInput();
					}
				}

				// Check if the item is actually in stock
				Article article;

				try {
					article = eshop.searchByArticleNumber(articleNumber);
				} catch (ArticleNotFoundException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					return;
				}

				System.out.print("Enter new quantity: ");
				String newQuantityNumberString = readInput();
				int newQuantity = 0;
				boolean validInput2 = false;

				while (!validInput2) {
					try {
						newQuantity = Integer.parseInt(newQuantityNumberString);
						if (newQuantity >= 0) {
							validInput2 = true; // Break the loop if parsing succeeds and input is valid
						} else {
							System.out.println("Invalid input. Please enter a non-negative integer value for the new quantity.");
							System.out.print("Enter new quantity: ");
							newQuantityNumberString = readInput();
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter an integer value for the new quantity.");
						System.out.print("Enter new quantity: ");
						newQuantityNumberString = readInput();
					}
				}
				try {
					System.out.println(eshop.changeArticleQuantityInCart(newQuantity, article, (Customer) loggedinUser));
				} catch (ArticleInCartNotFoundException a) {
					System.out.println("\n" + a.getMessage() + "\n");
				} catch (BulkArticleException b) {
					System.out.println("\n" + b.getMessage() + "\n");
				} catch (InsufficientStockException i) {
					System.out.println("\n" + i.getMessage() + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void removeArticleFromCart() {
	    try {
			int articleNumber = 0;

			if (loggedinUser instanceof Customer) {
				Customer customer = (Customer) loggedinUser;
				System.out.print("Enter article number: ");
				String articleNumberString = readInput();
				boolean validInput = false;

				while (!validInput) {
					try {
						articleNumber = Integer.parseInt(articleNumberString);
						validInput = true; // Break the loop if parsing succeeds
					} catch (NumberFormatException e) {
						System.out.println("Invalid input. Please enter an integer value for the article number.");
						System.out.print("Enter article number: ");
						articleNumberString = readInput();
					}
				}
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
				// The buyArticles(shoppingCart) method is called to carry out the purchase of
				// the items in the shopping cart.
				// The result is an invoice that is stored in the variable invoice.
				Invoice invoice = null;
				try {
					invoice = eshop.buyArticles(shoppingCart, loggedinUser);
				} catch (EmptyCartException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				} catch (ArticleBuyingException a) {
					System.out.println("\n" + a.getMessage() + "\n");
				}

				// print which articles couldn't be purchased
				articlesCouldntPurchase(invoice);
				// print which articles could be purchased
				articlePurchaseSuccessfully(invoice);
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

			System.out.println(eshop.deleteAllArticlesInCart((Customer) loggedinUser));
		}
	}

	/*
	 * Methods of running the program
	 */
	public void run() throws Exception {
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
//						e.printStackTrace();
						System.out.println("Not Good");
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

	public static void main(String[] args) throws Exception {
		// Variable of type "EshopClientCUI" is declared but not yet initialized!
		EshopClientCUI cui;
		try {
			// A new object of "EshopClientCUI" is created. The file and the string "ESHOP"
			// are passed as parameters or only the file named "ESHOP" is passed
			cui = new EshopClientCUI("ESHOP");
			// The "run" method is called on the "cui" object to run the program
			cui.run();
			// If an error occurs during this, an "IOException" is thrown
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
