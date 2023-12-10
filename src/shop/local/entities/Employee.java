package shop.local.entities;
import java.util.Objects;

/**
 * Class to represent individual employees.
 *
 * @author Sund
 */

public class Employee extends User {

    public Employee(String name, String lastName, String username, String password) {
        super(name, lastName, username, password);
    }

    //Konstruktor, wenn Employee aus Datei auslesen wird
    public Employee(int id, String name, String lastName, String username, String password) {
        super(id, name, lastName, username, password);
    }

    // Methods for setting and reading customer properties,
    // e.g. getStreet() and setStreet()

    @Override
    public boolean equals(Object otherEmployee) {
        //In der equals-Methode stellt der otherEmployee-Parameter das Objekt dar, das beim Aufrufen der equals-Methode als Argument übergeben wurde.
        //Innerhalb der Methode prüfen wir zunächst, ob das übergebene Objekt dasselbe Objekt ist, wie das aktuelle Objekt (mit == verglichen).
        //Wenn ja, dann sind die beiden Objekte gleich, und wir geben true zurück.
        if (otherEmployee == this) {
            return true;
        }
        //Wir verwenden instanceof, um sicherzustellen, dass das übergebene Objekt (Kunde k) auch tatsächlich eine Instanz von Kunde ist.
        if (!(otherEmployee instanceof Employee)) {
            return false;
        }
        //Wenn das übergebene Objekt (otherEmployee) eine Instanz der Mitarbeiter-Klasse ist, können wir es mit einem Casting-Operator ((Employee) otherEmployee) in eine Mitarbeiter-Referenz konvertieren.
        //Dies ist notwendig, weil die equals-Methode auf die Attribute des Objekts zugreifen muss, der obj-Parameter vor der Konvertierung aber vom Typ Object ist,
        //der nicht die Attribute der Mitarbeiter-Klasse hat.
        //Anschließend speichern wir die Referenz des übergebenen Objekts (otherEmployee) als oEmployee.
        Employee oEmployee = (Employee) otherEmployee;
        //Die Objects.equals()-Methode wird verwendet, um die Gleichheit der Attribute zu prüfen.
        //Wenn alle Attribute übereinstimmen = true. Wenn mindestens eines der Attribute nicht übereinstimmt = false
        return Objects.equals(getName(), oEmployee.getName())
                && Objects.equals(getLastName(), oEmployee.getLastName())
                && Objects.equals(getUsername(), oEmployee.getUsername());
    }
}
