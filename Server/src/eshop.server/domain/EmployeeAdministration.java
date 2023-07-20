package eshop.server.domain;
import eshop.common.entities.Employee;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;
import eshop.server.persistence.FilePersistenceManager;
import eshop.server.persistence.PersistenceManager;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Class for employee administration
 * @author Sund
 */
public class EmployeeAdministration {

        // List of all employees
        private ArrayList<Employee> employees = new ArrayList<>();

        // Persistence interface responsible for file access details
        private PersistenceManager persistenceManager = new FilePersistenceManager();

        /**
         * The readData method reads employee data from a file with the specified file name.
         *
         * @param file File containing item stock to be read
         * @throws IOException
         */
        public void readData(String file) throws IOException {
                //PersistenceManager object opens the PersistenceManager for reading using the openForReading method.
                persistenceManager.openForReading(file);
                Employee employee;

                do {
                        //Read employee object
                        //Calls the loadEmployee method of the PersistenceManager in a loop to read one employee at a time from the file
                        employee = persistenceManager.loadEmployee();
                        if (employee != null) {
                                //If an employee could be read in successfully, this is added to the employee list
                                addEmployee(employee);
                        }
                        //The loop runs until the loadEmployee method returns null, indicating that there is no more data in the file.
                } while (employee != null);

                //Persistence interface is closed again
                persistenceManager.close();
        }

        public void writeData(String file, Employee employee) throws IOException  {
                // Open persistence manager for writes
                persistenceManager.openForWriting(file);
                persistenceManager.saveEmployee(employee, this.employees);

                // Close the persistence interface again
                persistenceManager.close();
        }

        public Employee login (String username, String password) throws LoginException {
                for (Employee user : employees) {
                        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                                return user;
                        }
                }
                throw new LoginException(null);
        }

        // Adds employee objects from file to ArrayList
        public void addEmployee(Employee employee) {
                this.employees.add(employee);
        }

        // Getter und Setter
        public ArrayList<Employee> getEmployees() {
                return employees;
        }

        public Employee getUserByID(int id){
           for (Employee employee : employees) {
                   if(id == employee.getId()){
                      return employee;
                   }
           }
           return null;
        }


        public String registerEmployee(String name, String lastname, String username, String password) throws RegisterException {
        	String message = "";
        	
    		//Erstelle Variable vom Typ Employee und übergebe die Eingaben des Employee an den Konstruktor
    		Employee employee = new Employee(name, lastname, username, password);

    		// Prüfe, ob Employee bereits existiert
    		boolean employeeAlreadyExists = false;
    		for (Employee currentEmployee : employees) {
    			if (employee.equals(currentEmployee)) {
    				employeeAlreadyExists = true;
    			}
    		}
    		if(!employeeAlreadyExists) {
    			//Wenn kein Employee gefunden wird, dann kann der Employee registriert werden.
    			try {
    				writeData("ESHOP_Employee.txt", employee);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
                addEmployee(employee);
    			message = "Registration successful.";
    		} else {
                    throw new RegisterException(employee, "A User with this Name already exist. Please choose another one.");
                }
                return message;
        }

}
