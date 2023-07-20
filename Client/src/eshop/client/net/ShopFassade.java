package eshop.client.net;

import eshop.common.entities.Article;
import eshop.common.entities.Customer;
import eshop.common.entities.Employee;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;
import eshop.common.interfaces.ShopInterface;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ShopFassade implements ShopInterface {

    // Datenstrukturen für die Kommunikation
    private Socket socket = null;
    private BufferedReader sin; // server-input stream
    private PrintStream sout; // server-output stream

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
        String message = sin.readLine();
        System.out.println(message);
    }

    /**
     * Methode, die eine Liste aller im Bestand befindlichen Artikel zurückgibt.
     *
     * @return Liste aller Artikel im Bestand vom Shop
     */
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> list = new ArrayList<Article>();

        // Kennzeichen für gewählte Aktion senden
        sout.println("a");

        // Antwort vom Server lesen und im info-Feld darstellen:
        String reply = "?";
        try {
            // Anzahl gefundener Artikel einlesen
            reply = sin.readLine();
            int anzahl = Integer.parseInt(reply);
            for (int i=0; i<anzahl; i++) {
                // Artikel vom Server lesen ...
                Article article = readArticleFromServer();
                // ... und in Liste eintragen
                list.add(article);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return list;
    }

    private Article readArticleFromServer() throws IOException {
        String reply;

        // Nummer vom Artikel i einlesen
        reply = sin.readLine();
        int nummer = Integer.parseInt(reply);

        // Titel vom Artikel i einlesen
        String articleTitel = sin.readLine();

        // Weitere Attribute des Artikels vom Server einlesen
        reply = sin.readLine();
        double price = Double.parseDouble(reply);

        reply = sin.readLine();
        int quantityInStock = Integer.parseInt(reply);

        // Neues Article-Objekt erzeugen und zurückgeben
        Article article = new Article(nummer, articleTitel, quantityInStock, price);
        return article;
    }

    public String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail, String username, String password, String registerNow) throws RegisterException {
        // Kennzeichen für gewählte Aktion senden
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
        String antwort = "Fehler";

        try {
            antwort = sin.readLine();
            if (antwort.equals("Erfolg")) {
                // message vom Server lesen ...
                String messageErfolgreich = sin.readLine();
                // ... und zurückgeben
                return messageErfolgreich;
            } else {
                // Fehler: Exception (re-)konstruieren
                String message = sin.readLine();
                throw new RegisterException(null ,null);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Customer loginCustomer(String username, String password) throws LoginException {
        // Kennzeichen für gewählte Aktion senden
        sout.println("lc");

        sout.println(username);
        sout.println(password);


        // Antwort vom Server lesen:
        String antwort = "Fehler";
        try {
            antwort = sin.readLine();
            if (antwort.equals("Erfolg")) {
                // Custumer vom Server lesen ...
                Customer customer = liesCustomerVomServer();
                // ... und zurückgeben
                return customer;
            } else {
                // Fehler: Exception (re-)konstruieren
                String message = sin.readLine();
                throw new LoginException(message);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private Customer liesCustomerVomServer() throws IOException {

        // Attribute des loggedinUser einzeln empfangen
        int id = Integer.parseInt(sin.readLine());
        String name = sin.readLine();
        String lastName = sin.readLine();
        String street = sin.readLine();
        int postalCode = Integer.parseInt(sin.readLine());
        String city = sin.readLine();
        String email = sin.readLine();
        String username = sin.readLine();
        String password = sin.readLine();

        // Neues Customer-Objekt erzeugen und zurückgeben
        Customer customer = new Customer(id, name, lastName, street, postalCode, city, email, username, password);
        return customer;
    }

    public Employee loginEmployee(String username, String password) throws LoginException {
        // Kennzeichen für gewählte Aktion senden
        sout.println("le");
        return null;
    }

}
