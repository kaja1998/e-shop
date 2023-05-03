package shop.local.entities;

/**
 * Class to represent individual employees.
 *
 * The class is currently not used because the shop
 * Application so far only manages articles.
 *
 * @author Sund
 */

    public class Employee implements User {

    private int id;
    private String name;
    private String lastName;
    private String username;
    private String password;

    public Employee(int employeeNumber, String name, String lastName) {
        this.id = employeeNumber;
        this.name = name;
        this.lastName = lastName;
    }

    // Methods for setting and reading customer properties,
    // e.g. getStreet() and setStreet()

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }

    @Override
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
