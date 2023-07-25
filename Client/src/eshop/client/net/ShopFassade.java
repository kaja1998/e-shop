package eshop.client.net;

import eshop.common.entities.*;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;
import eshop.common.interfaces.ShopInterface;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/**
 * The class ShopFassade is a client-side implementation responsible for processing communication between the client and the server.
 * It establishes a connection to the server using sockets and sends and receives messages to/from the server.
 *
 * @author: sund
 */

public class ShopFassade implements ShopInterface {

    // Datenstrukturen für die Kommunikation
    private Socket socket = null;
    private BufferedReader sin; // server-input stream
    private PrintStream sout; // server-output stream


    /**
     * Creates a new instance of ShopFassade that connects to the specified host and port.
     * @throws IOException If an I/O error occurs while establishing the connection.
     */
    public ShopFassade(String host, int port) throws IOException {
        try {
            // Socket-Objekt fuer die Kommunikation mit Host/Port erstellen
            socket = new Socket(host, port);

            // Stream-Objekt fuer Text-I/O ueber Socket erzeugen
            InputStream is = socket.getInputStream();
            sin = new BufferedReader(new InputStreamReader(is));
            sout = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error while opening the Socket-Stream: " + e);
            // Wenn im "try"-Block Fehler auftreten, dann Socket schließen:
            if (socket != null)
                socket.close();
            System.err.println("Socket closed");
            System.exit(0);
        }

        // Verbindung erfolgreich hergestellt: IP-Adresse und Port ausgeben
        System.err.println("Connected: " + socket.getInetAddress() + ":"
                + socket.getPort());

        // Begrüßungsmeldung vom Server lesen
        String message = readStringInput("message");
        System.out.println(message);
    }



    /**
     * Method that returns a list of all items in inventory.
     * @return List of all items in stock of the shop
     */
    public ArrayList<Article> getAllArticles() {
        // Kennzeichen für gewählte Aktion senden
        sout.println("a");

        ArrayList<Article> list = new ArrayList<>();

        // Antwort vom Server lesen:
        // Anzahl gefundener Artikel einlesen
        String reply = readStringInput("reply");
        int anzahl = Integer.parseInt(reply);

        for (int i = 0; i < anzahl; i++) {
            // Artikeltyp vom Server einlesen (BulkArticle oder Article)
            String articleType = readStringInput("articleType");
            if (articleType.equals("BulkArticle")) {
                BulkArticle bulkArticle = (BulkArticle) readBulkArticleFromServer();
                // in Liste eintragen
                list.add(bulkArticle);
            } else if (articleType.equals("Article")) {
                Article article = readArticleFromServer();
                // in Liste eintragen
                list.add(article);
            } else {
                System.err.println("Got Unknown ArticleType: " + articleType);
            }
        }
        return list;
    }

    /**
     * Method that returns a list of all items in inventory without the ones who are INACTIVE
     * @return List of all items in stock of the shop
     */
    public ArrayList<Article> getAllArticlesWithoutInactive() {
        // Kennzeichen für gewählte Aktion senden
        sout.println("awi");

        ArrayList<Article> list = new ArrayList<>();

        // Antwort vom Server lesen:
        // Anzahl gefundener Artikel einlesen
        String reply = readStringInput("reply");
        int anzahl = Integer.parseInt(reply);

        for (int i = 0; i < anzahl; i++) {
            // Artikeltyp vom Server einlesen (BulkArticle oder Article)
            String articleType = readStringInput("articleType");
            if (articleType.equals("BulkArticle")) {
                BulkArticle bulkArticle = (BulkArticle) readBulkArticleFromServer();
                // in Liste eintragen
                list.add(bulkArticle);
            } else if (articleType.equals("Article")) {
                Article article = readArticleFromServer();
                // in Liste eintragen
                list.add(article);
            } else {
                System.err.println("Got Unknown ArticleType: " + articleType);
            }
        }
        return list;
    }

    /**
     * Method that reads articles from server
     * @return normal article to getAllArticles() methode
     */
    private Article readArticleFromServer() {
        // Nummer vom Artikel i einlesen
        int number = readIntInput("number");

        // Titel vom Artikel i einlesen
        String articleTitle = readStringInput("articleTitle");

        // price vom Artikel i einlesen
        double price = readDoubleInput("price");

        // quanity vom Artikel i einlesen
        int quantityInStock = readIntInput("quantityInStock");

        // articleStatus vom Artikel i einlesen
        String articleStatusString = readStringInput("articleStatusString");
        ArticleStatus articleStatus = ArticleStatus.fromString(articleStatusString);

        // Neues Article-Objekt erzeugen und zurückgeben
        Article article = new Article(number, articleTitle, quantityInStock, price, articleStatus);
        return article;
    }

    /**
     * Method that reads Bulkarticles from server
     * @return Bulkarticle to getAllArticles() methode
     */
    private Article readBulkArticleFromServer() {
        // Artikel vom Server einlesen
        Article article = readArticleFromServer();

        // PackSize vom BulkArticle einlesen
        int packSize = readIntInput("packSize");

        // Neues BulkArticle-Objekt erzeugen und zurückgeben
        BulkArticle bulkArticle = new BulkArticle(article.getNumber(), article.getArticleTitle(), article.getQuantityInStock(), article.getPrice(), packSize, article.getStatus());
        return bulkArticle;
    }


    

    /**
     * Method that Sends flag for selected action (registerCustomer) and parameters of the new customer to the Server
     * Moreover it receives reply from Server
     * @return success message if success and if Error it throws RegisterException
     */
    public String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail, String username, String password, String registerNow) throws RegisterException {
        // Kennzeichen für gewählte Aktion und Parameter senden
        sout.println("rc");
        sout.println(name);
        sout.println(lastName);
        sout.println(street);
        sout.println(postalCode);
        sout.println(city);
        sout.println(mail);
        sout.println(username);
        sout.println(password);
        sout.println(registerNow);

        // Antwort vom Server lesen:
        String reply = readStringInput("reply");
        if (reply.equals("Success")) {
            String messageSuccess = readStringInput("messageSuccess");
            return messageSuccess;
        } else {
            // Error: Exception (re-)konstruieren
            String additionalMessage = readStringInput("messageError");
            //Customer erstellen, um diesen an die Exception zu übergeben
            Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);
            throw new RegisterException(customer, additionalMessage);
        }
    }



    /**
     * Method that Sends flag for selected action (loginCustomer) and parameters of the customer to the Server
     * Moreover it receives reply from Server
     * @return customer if success. If Error it throws LoginException
     */
    public Customer loginCustomer(String username, String password) throws LoginException {
        // Kennzeichen für gewählte Aktion und Parameter senden
        sout.println("lc");
        sout.println(username);
        sout.println(password);

        // Antwort vom Server lesen:
        String reply = readStringInput("reply");
        if (reply.equals("Success")) {
            Customer customer = liesCustomerVomServer();
            return customer;
        } else {
            // Error: Exception (re-)konstruieren
            throw new LoginException(null);
        }
    }

    /**
     * Method that reads a customer from server + Creates new customer to...
     *
     * @return customer to loginCustomer() Methode
     */
    private Customer liesCustomerVomServer() {
        // Attribute des loggedinUser einzeln empfangen
        int id = readIntInput("id");
        String name = readStringInput("name");
        String lastName = readStringInput("lastName");
        String street = readStringInput("street");
        int postalCode = readIntInput("postalCode");
        String city = readStringInput("city");
        String email = readStringInput("email");
        String username = readStringInput("username");
        String password = readStringInput("password");

        // Neues Customer-Objekt erzeugen und zurückgeben
        Customer customer = new Customer(id, name, lastName, street, postalCode, city, email, username, password);
        return customer;
    }



    /**
     * Method that Sends flag for selected action (loginEmployee) and parameters of the employee to the Server
     * Moreover it receives reply from Server
     * @return employee if success. If Error it throws LoginException
     */
    public Employee loginEmployee(String username, String password) throws LoginException {
        // Kennzeichen für gewählte Aktion und Parameter senden
        sout.println("le");
        sout.println(username);
        sout.println(password);

        // Antwort vom Server lesen:
        String reply = readStringInput("reply");
        if (reply.equals("Success")) {
            Employee employee = liesEmployeeVomServer();
            return employee;
        } else {
            // Error: Exception (re-)konstruieren
            throw new LoginException(null);
        }
    }

    /**
     * Method that reads an employee from server + Creates new employee to...
     *
     * @return employee to loginEmployee() Methode
     */
    private Employee liesEmployeeVomServer() {
        // Attribute des loggedinUser empfangen
        int id = readIntInput("id");
        String name = readStringInput("name");
        String lastName = readStringInput("lastName");
        String username = readStringInput("username");
        String password = readStringInput("password");

        // Neues Employee-Objekt erzeugen und zurückgeben
        Employee employee = new Employee(id, name, lastName, username, password);
        return employee;
    }



    /**
     * Method when leaving the Shop
     * @return a bye message, but only in the cui. In the GUI I didn't liked the implementation
     * In the GUI you find the methode in the windowCloser class
     */
    public String disconnect() {
        // Kennzeichen für gewählte Aktion senden
        sout.println("q");

        String reply = readStringInput("reply");
        return reply;
    }



    /**
     * Methods for reading input/reply from Server
     */
    private String readStringInput(String field) {
        String input = null;

        try {
            input = sin.readLine();
        } catch (IOException e) {
            System.out.println("--->Error reading from Server (" + field + "): ");
            System.out.println(e.getMessage());
        }

        return input;
    }

    private int readIntInput(String field) {
        int input = 0;

        try {
            input = Integer.parseInt(sin.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("--->Error reading from Server (" + field + "): ");
            System.out.println(e.getMessage());
        }

        return input;
    }

    private double readDoubleInput(String field) {
        double input = 0.0;

        try {
            String inputValue = sin.readLine();
            input = Double.parseDouble(inputValue);
        } catch (IOException | NumberFormatException e) {
            System.out.println("--->Error reading from Server (" + field + "): ");
            System.out.println(e.getMessage());
        }

        return input;
    }

}
