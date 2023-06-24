package shop.local.domain.exceptions;

import shop.local.entities.Employee;

@SuppressWarnings("serial")
public class RegisterEmployeeException extends Exception {
    private Employee employee;

    /**
     * Constructor
     *
     * @param employee           the employee that was not found
     * @param additionalMessage additional text for the error message
     */
    public RegisterEmployeeException(Employee employee, String additionalMessage) {
        super("Error occurred while registering employee with the username " + employee.getUsername() + additionalMessage);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
