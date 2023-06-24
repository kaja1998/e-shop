package shop.local.domain.exceptions;

import shop.local.entities.Employee;

@SuppressWarnings("serial")
public class EmployeeNotFoundException extends Exception {
    private Employee employee;

    /**
     * Constructor
     *
     * @param employee          the employee that was not found
     * @param additionalMessage additional text for the error message
     */
    public EmployeeNotFoundException(Employee employee, String additionalMessage) {
        super("Employee with the username " + employee.getUsername() + " and password " + employee.getPassword() + " does not exist" + additionalMessage);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}
