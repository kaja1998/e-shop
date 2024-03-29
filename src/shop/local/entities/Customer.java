package shop.local.entities;
import java.util.Objects;

/**
 * Class to represent individual customers.
 * @author Sund
 */
public class Customer extends User {

	private String email;
	private String street = "";
	private int postalCode;
	private String city = "";
	private float revenue = 0.0f;
	private ShoppingCart shoppingCart;

	public Customer(String name, String lastName, String street, int postalCode, String city, String email, String username, String password) {
		super(name, lastName, username, password);
		this.shoppingCart = new ShoppingCart();
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.email = email;
	}


	//Konstruktor, wenn Customer aus Datei ausgelesen wird ohne Warenkorb
	public Customer(int id, String name, String lastName, String street, int postalCode, String city, String email, String username, String password) {
		super(id, name, lastName, username, password);
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.email = email;
		this.shoppingCart = new ShoppingCart();
	}

	public int getPostalCode() { return postalCode; }

	public String getStreet() { return street; }

	public String getCity() { return city; }

	public String getEmail() { return email;	}

	public ShoppingCart getShoppingCart() {	return shoppingCart; }

	@Override
	public String toString() {
		return getId() + ";" + getName() + ";" + getLastName() + ";" + getStreet() + ";" + getPostalCode() + ";" +getCity() + ";" + getEmail() + ";" + getUsername() + ";" + getPassword() + ";" + shoppingCart;
	}

	//Diese Methode prüft, ob zwei Instanzen der Klasse Kunde gleich sind, indem sie ihre Attribute vergleicht
	//obj ist das Objekt, das mit dem aktuellen Kunde-Objekt verglichen wird.
	//Die equals()-Methode erhält dieses Objekt als Parameter
	@Override
	public boolean equals(Object obj) {
		//In der equals-Methode stellt der obj-Parameter das Objekt dar, das beim Aufrufen der equals-Methode als Argument übergeben wurde (Kunde k in der Klasse EShopClientCUI).
		//Innerhalb der Methode prüfen wir zunächst, ob das übergebene Objekt dasselbe Objekt ist, wie das aktuelle Objekt (mit == verglichen).
		//Wenn ja, dann sind die beiden Objekte gleich, und wir geben true zurück.
		if (obj == this) {
			return true;
		}
		//Wir verwenden instanceof, um sicherzustellen, dass das übergebene Objekt (Kunde k) auch tatsächlich eine Instanz von Kunde ist.
		if (!(obj instanceof Customer)) {
			return false;
		}
		//Wenn das übergebene Objekt (k bzw. obj) eine Instanz der Kunde-Klasse ist, können wir es mit einem Casting-Operator ((Kunde) obj) in eine Kunde-Referenz konvertieren.
		//Dies ist notwendig, weil die equals-Methode auf die Attribute des Objekts zugreifen muss, der obj-Parameter vor der Konvertierung aber vom Typ Object ist,
		//der nicht die Attribute der Kunde-Klasse hat.
		//Anschließend speichern wir die Refernez des übergebenen Objekts (obj) als other.
		Customer other = (Customer) obj;
		//Die Objects.equals()-Methode wird verwendet, um die Gleichheit der Attribute zu prüfen.
		//Wenn alle Attribute übereinstimmen = true. Wenn mindestens eines der Attribute nicht übereinstimmt = false
		return Objects.equals(getName(), other.getName())
				&& Objects.equals(getLastName(), other.getLastName())
				&& Objects.equals(this.email, other.email)
				&& Objects.equals(getUsername(), other.getUsername());
	}

}
