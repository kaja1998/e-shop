package shop.local.entities;

/**
 * Class to represent individual employees.
 *
 * The class is currently not used because the shop
 * Application so far only manages articles.
 *
 * @author Sund
 */

public class Employee extends User {

    public Employee(String name, String lastName, String username, String password) {
        super(name, lastName, username, password);
    }

    // Methods for setting and reading customer properties,
    // e.g. getStreet() and setStreet()

}
