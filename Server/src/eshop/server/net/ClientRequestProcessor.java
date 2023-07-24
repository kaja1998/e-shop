package eshop.server.net;

import eshop.common.entities.Article;
import eshop.common.entities.BulkArticle;
import eshop.common.entities.Customer;
import eshop.common.entities.Employee;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;
import eshop.server.domain.Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class for processing communication between a client and the server.
 *
 * @author: sund
 */

public class ClientRequestProcessor implements Runnable {

    // Shopverwaltungsobjekt
    private Shop eshop;

    // Datenstrukturen für die Kommunikation
    private Socket clientSocket;
    private BufferedReader in;
    private PrintStream out;

    /**
     * Constructor for the ClientRequestProcessor
     * Takes a Socket and a ShopInterface instance as parameters
     */
    public ClientRequestProcessor(Socket socket, Shop eshop) {
        this.eshop = eshop; // Die ShopInterface-Instanz speichern, um später auf die Shop-Logik zugreifen zu können
        clientSocket = socket; // Den Socket des Clients speichern

        try {
            // Initialisieren der I/O-Streams, um mit dem Client zu kommunizieren
            // BufferedReader wird verwendet, um Eingaben vom Client zu lesen
            // PrintStream wird verwendet, um Ausgaben an den Client zu senden
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            try {
                // Falls ein Fehler bei der Initialisierung der Streams auftritt, wird der Socket geschlossen
                clientSocket.close();
            } catch (IOException e2) {
            }
            System.err.println("Exception while delivering the stream: " + e);
            return;
        }

        // Ausgabe der Meldung, dass eine Verbindung mit dem Client hergestellt wurde
        System.out.println("Connected with " + clientSocket.getInetAddress()
                + ":" + clientSocket.getPort());
    }

    /**
     * Method of handling communication with the client according to the
     * specified communication protocol.
     */
    public void run() {

        String input = "";

        // Begrüßungsnachricht an den Client senden
        out.println("Server to client: I'm ready for your requests!");

        // Hauptschleife zur wiederholten Abwicklung der Kommunikation
        do {
            // Beginn der Benutzerinteraktion:
            // Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
            input = readStringInput("action");

            // Eingabe bearbeiten:
            if (input == null || input.equals("q")) {
                disconnect();
            } else if (input.equals("a")) {
                // Aktion "Artikel ausgeben" gewählt
                getAllArticles();
            } else if (input.equals("rc")) {
                // Aktion "Register Customer" gewählt
                registerCustomer();
            } else if (input.equals("lc")) {
                // Aktion "Login Customer" gewählt
                loginCustomer();
            } else if (input.equals("le")) {
                // Aktion "Login Employee" gewählt
                loginEmployee();
            }

        } while (!(input.equals("q")));

        // Verbindung wurde vom Client abgebrochen
        disconnect();
    }

    /**
     * Method handles the disconnection process with a client
     */
    private void disconnect() {
        try {
            String message = eshop.disconnect();
            out.println(message);
            clientSocket.close();

            System.out.println("Connection to " + clientSocket.getInetAddress()
                    + ":" + clientSocket.getPort() + " lost because of client quit.");
        } catch (Exception e) {
            out.println("Error while quit of connection.");
            System.out.println("---> Error while quit of connection: ");
            System.out.println(e.getMessage());
        }
    }



    /**
     * Method that sends all items to the client that are in the shop.
     *
     */
    private void getAllArticles() {
        ArrayList<Article> articles = null;
        articles = eshop.getAllArticles();

        sendArticlesToClient(articles);
    }

    private void sendArticlesToClient(ArrayList<Article> articles) {
        // Anzahl der gefundenen Artikel senden
        out.println(articles.size());
        for (Article article : articles) {
            if (article instanceof BulkArticle) {
                out.println("BulkArticle"); // Artikeltyp senden
                sentBulkArticleToClient((BulkArticle) article);
            } else {
                out.println("Article"); // Artikeltyp senden
                sentArticleToClient(article);
            }
        }
    }

    private void sentArticleToClient(Article article) {
        // Nummer des Artikels senden
        out.println(article.getNumber());
        // Titel des Artikels senden
        out.println(article.getArticleTitle());
        // Preis des Artikels senden
        out.println(article.getPrice());
        // Quantity des Artikels senden
        out.println(article.getQuantityInStock());
    }

    private void sentBulkArticleToClient(BulkArticle bulkArticle) {
        // Nummer des Artikels senden
        out.println(bulkArticle.getNumber());
        // Titel des Artikels senden
        out.println(bulkArticle.getArticleTitle());
        // Preis des Artikels senden
        out.println(bulkArticle.getPrice());
        // Quantity des Artikels senden
        out.println(bulkArticle.getQuantityInStock());
        // PackSize des Artikels senden
        out.println(bulkArticle.getPackSize());
    }



    /**
     * Method that sends the success message or error message to the client when a new customer wants to get registered..
     *
     */
    private void registerCustomer(){
        String name = readStringInput("name");
        String lastName = readStringInput("lastname");
        String street = readStringInput("street");
        int postalCode = readIntInput("postalCode");
        String city = readStringInput("city");
        String mail = readStringInput("mail");
        String username = readStringInput("username");
        String password = readStringInput("password");
        String registerNow = readStringInput("registerNow");

        try {
            String message = eshop.registerCustomer(name, lastName, street, postalCode, city, mail, username, password, registerNow);
            out.println("Success");
            out.println(message);
        } catch (RegisterException e) {
            out.println("Error");
            out.println("A User with this Name already exist. Please choose another one.");
        }
    }



    /**
     * Method that sends the loggedinuser (customer) to the client. If an error occurs an error message gets send.
     *
     */
    private void loginCustomer() {
        String username = readStringInput("username");
        String password = readStringInput("password");

        try {
            Customer loggedinUser = eshop.loginCustomer(username, password);
            out.println("Success");
            out.println(loggedinUser.getId());
            out.println(loggedinUser.getName());
            out.println(loggedinUser.getLastName());
            out.println(loggedinUser.getStreet());
            out.println(loggedinUser.getPostalCode());
            out.println(loggedinUser.getCity());
            out.println(loggedinUser.getEmail());
            out.println(loggedinUser.getUsername());
            out.println(loggedinUser.getPassword());
        } catch (LoginException e) {
            out.println("Error");
        }
    }



    /**
     * Method that sends the loggedinuser (employee) to the client. If an error occurs an error message gets send.
     *
     */
    private void loginEmployee(){
        String username = readStringInput("username");
        String password = readStringInput("password");

        try {
            Employee loggedinUser = eshop.loginEmployee(username, password);
            out.println("Success");
            out.println(loggedinUser.getId());
            out.println(loggedinUser.getName());
            out.println(loggedinUser.getLastName());
            out.println(loggedinUser.getUsername());
            out.println(loggedinUser.getPassword());
        } catch (LoginException ex) {
            out.println("Error");
        }
    }



    /**
     Methods for reading input from client
     */
    private String readStringInput(String field) {
        String input = null;

        try {
            input = in.readLine();
        } catch (IOException e) {
            System.out.println("--->Error reading from client (" + field + "): ");
            System.out.println(e.getMessage());
        }

        return input;
    }

    private int readIntInput(String field) {
        int input = 0;

        try {
            input = Integer.parseInt(in.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("--->Error reading from client (" + field + "): ");
            System.out.println(e.getMessage());
        }

        return input;
    }
}
