package shop.local.entities;


import java.util.List;
import java.util.Objects;

/**
 * Class to represent individual customers.
 *
 * The class is currently not used because the shop
 * Application so far only manages articles.
 *
 * @author Sund
 */
public class Customer implements User {

	private int id;
	private String name;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String street = "";
	private int postalCode;
	private String city = "";
	private float revenue = 0.0f;

    	public Customer(String name, String lastName, String street, int postalCode, String city, String email, String username, String password) {
		this.name = name;
		this.lastName = lastName;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getName() {	return name;	}

	public void setName(String name) { this.name = name; }

	public String getLastName() {	return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public float getRevenue() {	return revenue;	}

	public void setRevenue(float revenue) { this.revenue = revenue;	}

	public int getPostalCode() { return postalCode; }

	public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

	public String getStreet() { return street; }

	public void setStreet(String street) { this.street = street; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }

	public int getId() { return id;	}

	public void setId(int id) {	this.id = id; }

	public String getEmail() { return email;	}

	public void setEmail(String email) { this.email = email; }

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public static Customer login(List<Customer> existingCustomers, String username, String password) {
		//The loop iterates through each item in the useres user list and assigns it to the user variable
		for (Customer user : existingCustomers) {
			// Check if username and password are correct
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				System.out.print("You've been logged in.");
				return user;
			}
		}
		System.out.print("Invalid username or password.");
		return null;
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
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.lastName, other.lastName)
				&& Objects.equals(this.email, other.email)
				&& Objects.equals(this.username, other.username);
	}

	/*
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kunde kunde = (Kunde) o;
		return kundenNr == kunde.kundenNr && plz == kunde.plz && Objects.equals(kName, kunde.kName) && Objects.equals(kNachname, kunde.kNachname) && Objects.equals(kEmail, kunde.kEmail) && Objects.equals(kBenutzername, kunde.kBenutzername) && Objects.equals(kPasswort, kunde.kPasswort) && Objects.equals(strasse, kunde.strasse) && Objects.equals(wohnort, kunde.wohnort);
	}
	*/

	@Override
	public int hashCode() {
		return Objects.hash(id, name, lastName, email, username, password, street, postalCode, city);
	}
}
