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

    private int id;

    public Employee(int employeeNumber, String name, String lastName, String username, String password) {
        super(name, lastName, username, password);
        this.id = employeeNumber;
    }

    // Methods for setting and reading customer properties,
    // e.g. getStreet() and setStreet()

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

}
