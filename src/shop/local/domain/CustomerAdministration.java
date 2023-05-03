//Definiert eine neue Java-Package mit dem Namen "bib.local.domain".
//Eine Package in Java ist eine Möglichkeit, Klassen logisch zu organisieren und zu strukturieren.
package shop.local.domain;

//Dieses Code-Snippet importiert die Klasse Kunde aus dem Package bib.local.entities.
//Nachdem der Import erfolgt ist, kann man Objekte der Klasse Kunde in der aktuellen Klasse erzeugen und auf deren Methoden und Eigenschaften zugreifen.
//Ohne den Import müsste man jedes Mal den vollständigen Klassennamen angeben, um die Klasse zu verwenden.
import shop.local.entities.Customer;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import java.io.IOException;
//Importiert die Klasse ArrayList aus dem Paket java.util.
//Durch das Importieren dieser Klasse können Instanzen von ArrayList erstellt und alle Methoden und Eigenschaften dieser Klasse verwendet werden,
//ohne den vollständigen Klassennamen jedes Mal schreiben zu müssen.
import java.util.ArrayList;

public class CustomerAdministration {

        // Es wird eine private Instanzvariable (Variablen des Objekts) namens kunden deklariert. Die Instanzvariable ist vom Typ ArrayList<Kunde>.
        // Das bedeutet, dass sie eine ArrayList von Kunde-Objekten enthält. Der Liste können Elemente hinzugefügt oder entfernt werden.
        // Die Variable ist private und ist somit nur innerhalb der Klasse sichtbar.
        private ArrayList<Customer> customers = new ArrayList<>();

        // Persistence interface responsible for file access details
        private PersistenceManager pm = new FilePersistenceManager();

        /**
         * The readData method reads customer data from a file with the specified file name.
         *
         * @param file File containing item stock to be read
         * @throws IOException
         */
        public void readData(String file) throws IOException {
                //PersistenceManager object opens the PersistenceManager for reading using the openForReading method.
                pm.openForReading(file);

                Customer customer;
                do {
                        //Read customer object
                        //Calls the loadCustomer method of the PersistenceManager in a loop to read one customer at a time from the file
                        customer = pm.loadCustomer();
                        if (customer != null) {
                                //If a customer could be read in successfully, this is added to the customer list with the addKundeZunzu method.
                                //customers.add(customer);
                                addCustomer(customer);
                        }
                //The loop runs until the loadCustomer method returns null, indicating that there is no more data in the file.
                } while (customer != null);

                //Persistence interface is closed again
                pm.close();
        }

        public void writeData(String file, Customer customer) throws IOException  {
                // Open persistence manager for writes
                pm.openForWriting(file);
                pm.saveCustomer(customer, this.customers);

                // Close the persistence interface again
                pm.close();
        }

        // Adds customer objects from file to ArrayList
        public void addCustomer(Customer customer) {
                this.customers.add(customer);
        }

        // Getter und Setter
        public ArrayList<Customer> getCustomers() {
                return customers;
        }

        public void setCustomers(ArrayList<Customer> customers) {
                this.customers = customers;
        }
}
