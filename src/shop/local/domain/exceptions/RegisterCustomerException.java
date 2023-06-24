package shop.local.domain.exceptions;

import shop.local.entities.Customer;

@SuppressWarnings("serial")
public class RegisterCustomerException extends Exception {
    private Customer customer;

    /**
     * Constructor
     *
     * @param customer           the customer that was not found
     * @param additionalMessage additional text for the error message
     */
    public RegisterCustomerException(Customer customer, String additionalMessage) {
        super("Error occurred while registering customer with the username " + customer.getUsername() + additionalMessage);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
