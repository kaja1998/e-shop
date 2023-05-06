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

    private final int id;
    private static int idCounter = 0;

    public Employee(String name, String lastName, String username, String password) {
        super(name, lastName, username, password);
        this.idCounter = ++idCounter;
        this.id = idCounter;
    }

    // Methods for setting and reading customer properties,
    // e.g. getStreet() and setStreet()

    public int getId() {
        return id;
    }

}
