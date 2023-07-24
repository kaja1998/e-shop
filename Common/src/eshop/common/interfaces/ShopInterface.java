package eshop.common.interfaces;

import eshop.common.entities.Article;
import eshop.common.entities.Customer;
import eshop.common.entities.Employee;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;

import java.util.ArrayList;

public interface ShopInterface {

    /**
     * Method that returns a list of all items in inventory.
     * @return List of all items in the shop stock
    */
    public abstract ArrayList<Article> getAllArticles();


    /**
     * Method to register a new customer
     * @param name etc. of the Customer
     * @throws RegisterException
     */
    public abstract String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail, String username, String password, String registerNow) throws RegisterException;


    /**
     * Method to log in a customer
     * @param username of the customer
     * @param password of the customer
     * @throws LoginException
     */
    public abstract Customer loginCustomer(String username, String password) throws LoginException;


    /**
     *
     * Method to log in an employee
     * @param username of the employee
     * @param password of the employee
     * @throws LoginException
     */
    public abstract Employee loginEmployee(String username, String password) throws LoginException;


    /**
     * Method handles the disconnection process with a client
     */
   public abstract String disconnect();
}
