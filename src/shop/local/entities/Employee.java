package shop.local.entities;

import java.util.List;

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

    public Employee(String name, String lastName,String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
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

    public static Employee login(List<Employee> existingEmployees, String username, String password) {
        //The loop iterates through each item in the user list and assigns it to the user variable
        for (Employee user : existingEmployees) {
            // Check if username and password are correct
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.print("You've been logged in.");
                return user;
            }
        }
        System.out.print("Invalid username or password.");
        return null;
    }
}
