package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import shop.local.domain.exceptions.ArticleAlreadyExistsException;
import shop.local.domain.Shop;
import shop.local.entities.ArticleList;
import shop.local.entities.Customer;
import shop.local.entities.User;


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
	Scanner scanner = new Scanner(System.in);							// Scanner registration

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
			Random random = new Random(System.currentTimeMillis());
			int customerId = random.nextInt(1, 10000);
			Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);
			customer.setId(customerId);
			boolean customerAlreadyExists = false;

			//Check if user already exists.
			//First I get the list of all customers from the shop and save it in an instance variable called customer list of type ArrayList<Customer>, which I can freely use in this (EshopClientCUI).
			ArrayList<Customer> customerList = eshop.getCustomers();

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
					eshop.writeData("ESHOP_K.txt", customer);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				eshop.addCustomer(customer);
				System.out.println("Registration successful.");
				}
			}
	}

	private void customerLogin() {
		System.out.print("Please enter your login data:");
		System.out.println("Username: ");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		User user = Customer.login(eshop.getCustomers(), username, password);
		eshop.setUser(user);
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Ausgabe des Menüs.
	 */
	private void printEmployeeMenue() {
		System.out.print("Befehle: \n  Output articles:  'a'");		// \n ist ein Absatz
		System.out.print("         \n  Output customers:  'b'");
		System.out.print("         \n  Delete article: 'd'");
		System.out.print("         \n  Insert article: 'e'");
		System.out.print("         \n  Search article:  'f'");
		System.out.print("         \n  Save data:  's'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Quit:        'q'");
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
	private void processInputFromEntryMenu(String line) throws IOException {
		switch (line) {
			case "cr":
				registerCustomer();
				break;
			case "cl":
				customerLogin();
				break;
			case "e":
				// TODO implement
				System.out.println("To be implemented");
				break;
			case "q":
				// TODO implement
				eshop.writeArticle();
		}
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
		switch (line) {
			case "a":
				articleList = eshop.getAllArticles();		//eshop ist ein Objekt der Klasse Shop
				printArticleList(articleList);
				break;
			case "d":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Artikelnummer > ");
				numberString = readInput();
				number = Integer.parseInt(numberString);
				System.out.print("Artikelbezeichnung  > ");
				articleTitle = readInput();
				eshop.deleteArticle(articleTitle, number);
				break;
			case "e":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Artikelnummer > ");
				numberString = readInput();
				number = Integer.parseInt(numberString);
				System.out.print("Artikelbezeichnung  > ");
				articleTitle = readInput();

				try {
					eshop.insertArticle(articleTitle, number);
					System.out.println("Einfügen ok");
				} catch (ArticleAlreadyExistsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Fehler beim Einfügen");
					e.printStackTrace();
				}
				break;
			case "f":
				System.out.print("Artikelbezeichnung  > ");
				articleTitle = readInput();
				articleList = eshop.searchByArticleTitle(articleTitle);
				printArticleList(articleList);
				break;
			case "s":
				eshop.writeArticle();
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

	/**
	 * Method of executing the main loop:
	 * - Print menu
	 * - Read the user's input
	 * - Process input and output result
	 */
	public void run() throws IOException {
		// Variables for console input
		String input = "";

		// Abfrage, ob Kunde oder Mitarbeiter
		/* printEntryMenu();
		input = readInput();
		processInputFromEntryMenu(input);
		*/
		do {
			printEntryMenu();
			try {
				input = readInput();
				processInputFromEntryMenu(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!input.equals("q"));

		/*
		// Main loop of the user interface
		do {
			printEmployeeMenue();
			try {
				input = readInput();
				processInputForEmployeeMenu(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!input.equals("q"));
		*/
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
