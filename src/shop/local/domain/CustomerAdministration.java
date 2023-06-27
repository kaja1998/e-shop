//Definiert eine neue Java-Package mit dem Namen "bib.local.domain".
//Eine Package in Java ist eine Möglichkeit, Klassen logisch zu organisieren und zu strukturieren.
package shop.local.domain;
import shop.local.domain.exceptions.LoginException;
import shop.local.domain.exceptions.RegisterException;
import shop.local.entities.Customer;
import shop.local.entities.Employee;
import shop.local.entities.ShoppingCartItem;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for customer administration
 * @author Sund
 */

public class CustomerAdministration {
	

        // Es wird eine private Instanzvariable (Variablen des Objekts) namens kunden deklariert. Die Instanzvariable ist vom Typ ArrayList<Kunde>.
        // Das bedeutet, dass sie eine ArrayList von Kunde-Objekten enthält. Der Liste können Elemente hinzugefügt oder entfernt werden.
        // Die Variable ist private und ist somit nur innerhalb der Klasse sichtbar.
        private ArrayList<Customer> customers = new ArrayList<>();

        // Persistence interface responsible for file access details
        private PersistenceManager persistenceManager = new FilePersistenceManager();

        /**
         * The readData method reads customer data from a file with the specified file name.
         *
         * @param file File containing item stock to be read
         * @throws IOException
         */
        public void readData(String file) throws IOException {
                //PersistenceManager object opens the PersistenceManager for reading using the openForReading method.
                persistenceManager.openForReading(file);

                Customer customer;
                do {
                        //Read customer object
                        //Calls the loadCustomer method of the PersistenceManager in a loop to read one customer at a time from the file
                        customer = persistenceManager.loadCustomer();
                        if (customer != null) {
                                //If a customer could be read in successfully, this is added to the customer list with the addKundeZunzu method.
                                //customers.add(customer);
                                addCustomer(customer);
                        }
                //The loop runs until the loadCustomer method returns null, indicating that there is no more data in the file.
                } while (customer != null);

                //Persistence interface is closed again
                persistenceManager.close();
        }

        public void writeData(String file, Customer customer) throws IOException  {
                // Open persistence manager for writes
                persistenceManager.openForWriting(file);
                persistenceManager.saveCustomer(customer, this.customers);

                // Close the persistence interface again
                persistenceManager.close();
        }

        public Customer login (String userName, String password) throws LoginException {
                //Die Methode durchläuft eine Schleife über eine Liste von Kundenobjekten.
                //In jeder Iteration wird überprüft, ob der Benutzername (userName) und das Passwort (password)
                //mit den entsprechenden Werten des aktuellen Kundenobjekts übereinstimmen.
                for (Customer user : customers) {
                        if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
                                return user;
                        }
                }
                throw new LoginException("User with the username " + userName + " does not exist.");
        }

        // Adds customer objects from file to ArrayList
        public void addCustomer(Customer customer) {
                this.customers.add(customer);
        }

        public List<ShoppingCartItem> getUsersShoppingCart(Customer customer) {
                return customer.getShoppingCart().getCartItems();
        }

        // Getter und Setter
        public List<Customer> getCustomers() {
                return customers;
        }

        public Customer getUserByID(int id){
                for (Customer customer : customers) {
                        if(id == customer.getId()){
                                return customer;
                        }
                }
                return null;
        }
        
        public String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail, String username, String password, String registerNow) throws RegisterException {
        	String message = "";
        	
    		//Check if registration wants to do
    		if (registerNow.equals("yes")) {
    			//Erstelle Variable vom Typ Kunde und übergebe die Eingaben des Kunden an den Konstruktor
    			Customer customer = new Customer(name, lastName, street, postalCode, city, mail, username, password);

                boolean customerAlreadyExists = false;
                for (Customer currentCustomer : customers) {
                    if (customer.equals(currentCustomer)) {
                        customerAlreadyExists = true;
                    }
                }

    			if (!customerAlreadyExists) {
    				try {
                        writeData("ESHOP_Customer.txt", customer);
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
                    addCustomer(customer);
                    message = "Registration successful.";
    			} else {
    				throw new RegisterException(customer, "A User with this Name already exist. Please choose another one.");
    			}
    		}
                return message;
        }
}
