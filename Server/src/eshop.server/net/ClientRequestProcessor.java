package eshop.server.net;

import eshop.common.entities.Article;
import eshop.common.entities.User;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;
import eshop.common.interfaces.ShopInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * Class for processing communication between a client and the server.
 *
 * @author: sund
 */

public class ClientRequestProcessor implements Runnable {

    // Shopverwaltungsobjekt
    private ShopInterface eshop;

    // Datenstrukturen für die Kommunikation
    private Socket clientSocket;
    private BufferedReader in;
    private PrintStream out;

    public ClientRequestProcessor(Socket socket, ShopInterface eshop){

        this.eshop = eshop;
        clientSocket = socket;

        // I/O-Streams initialisieren und ClientRequestProcessor-Objekt als Thread starten:
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            try {
                clientSocket.close();
            } catch (IOException e2) {
            }
            System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
            return;
        }

        System.out.println("Verbunden mit " + clientSocket.getInetAddress()
                + ":" + clientSocket.getPort());
    }


    /**
     * Methode zur Abwicklung der Kommunikation mit dem Client gemäß dem
     * vorgebenen Kommunikationsprotokoll.
     */
    public void run() {

        String input = "";

        // Begrüßungsnachricht an den Client senden
        out.println("Server an Client: Bin bereit für Deine Anfragen!");

        // Hauptschleife zur wiederholten Abwicklung der Kommunikation
        do {
            // Beginn der Benutzerinteraktion:
            // Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
            try {
                input = in.readLine();
            } catch (Exception e) {
                System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
                System.out.println(e.getMessage());
                continue;
            }

            // Eingabe bearbeiten:
            if (input == null) {
                // input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
                // Einfach behandeln wie ein "quit"
                input = "q";
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

    private void getAllArticles() {
        // Die Arbeit soll wieder das Bibliotheksverwaltungsobjekt machen:
        List<Article> articles = null;
        articles = eshop.getAllArticles();

        sentArticlesToClient(articles);
    }

    private void sentArticlesToClient(List<Article> articles) {
        // Anzahl der gefundenen Artikel senden
        out.println(articles.size());
        for (Article article: articles) {
            sentArticleToClient(article);
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

    private void registerCustomer(){
        String input = null;

        // Article name
        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (name): ");
            System.out.println(e.getMessage());
        }
        String name = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (lastname): ");
            System.out.println(e.getMessage());
        }
        String lastName = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (street): ");
            System.out.println(e.getMessage());
        }
        String street = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (postalCode): ");
            System.out.println(e.getMessage());
        }
        int postalCode = Integer.parseInt(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (city): ");
            System.out.println(e.getMessage());
        }
        String city = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (mail): ");
            System.out.println(e.getMessage());
        }
        String mail = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (username): ");
            System.out.println(e.getMessage());
        }
        String username = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (password): ");
            System.out.println(e.getMessage());
        }
        String password = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (registerNow): ");
            System.out.println(e.getMessage());
        }
        String registerNow = new String(input);

        try {
            String message = eshop.registerCustomer(name, lastName, street, postalCode, city, mail, username, password, registerNow);
            out.println("Erfolg");
            out.println(message);
        } catch (RegisterException e) {
            out.println("Error");
            out.println(e.getMessage());
        }
    }

    private void loginCustomer(){
        String input = null;

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (username): ");
            System.out.println(e.getMessage());
        }
        String username = new String(input);

        try {
            input = in.readLine();
        } catch (Exception e) {
            System.out.println("--->Error reading from client (password): ");
            System.out.println(e.getMessage());
        }
        String password = new String(input);

        try {
            User loggedinUser = eshop.loginCustomer(username, password);
            out.println("Erfolg");
            out.println(loggedinUser);
        } catch (LoginException e) {
            out.println("Fehler");
            out.println(e.getMessage());
        }
    }

    private void loginEmployee(){

    }



    private void disconnect() {
        try {
            //out.println("Bye!");
            clientSocket.close();

            System.out.println("Connection to " + clientSocket.getInetAddress()
                    + ":" + clientSocket.getPort() + " lost because of client quit");
        } catch (Exception e) {
            System.out.println("---> Error while quit of connection: ");
            System.out.println(e.getMessage());
            out.println("Error");
        }
    }

}
