package shop.local.domain;

import shop.local.entities.Employee;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;

import java.io.IOException;
import java.util.ArrayList;

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

        public Employee login (String username, String password) {
                for (Employee user : employees) {
                        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                                return user;
                        }
                }
                // TODO: Exception werfen
                return null;
        }

        // Adds employee objects from file to ArrayList
        public void addEmployee(Employee employee) {
                this.employees.add(employee);
        }

        // Getter und Setter
        public ArrayList<Employee> getEmployees() {
                return employees;
        }

        public void setEmployees(ArrayList<Employee> employees) {
                this.employees = this.employees;
        }

        public Employee getUserByID(int id){
           for (Employee employee : employees) {
                   if(id == employee.getId()){
                      return employee;
                   }
           }
           return null;
        }

}
