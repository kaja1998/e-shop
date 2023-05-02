package bib.local.entities;


import java.util.List;
import java.util.Objects;

/**
 * Klasse zur Repräsentation einzelner Kunden.
 * 
 * Die Klasse wird derzeit noch nicht verwendet, weil die Shop-
 * Anwendung bislang nur Artikel verwaltet.
 * 
 * @author Sund
 */
public class Kunde implements User {

	private int id;
	private String name;
	private String nachname;
	private String email;
	private String benutzername;
	private String passwort;
	private String strasse = "";
	private int plz;
	private String wohnort = "";
	private float umsatz = 0.0f;

    	public Kunde(String kName, String kNachname, String strasse, int plz, String wohnort, String kEmail, String kBenutzername, String kPasswort) {
		this.name = kName;
		this.nachname = kNachname;
		this.strasse = strasse;
		this.plz = plz;
		this.wohnort = wohnort;
		this.email = kEmail;
		this.benutzername = kBenutzername;
		this.passwort = kPasswort;
	}
    
	// Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
	// z.B. getStrasse() und setStrasse()

	public String getName() {	return name;	}

	public void setName(String name) { this.name = name; }

	public String getNachname() {	return nachname; }

	public void setNachname(String nachname) { this.nachname = nachname; }

	public float getUmsatz() {	return umsatz;	}

	public void setUmsatz(float umsatz) { this.umsatz = umsatz;	}

	public int getPlz() { return plz; }

	public void setPlz(int plz) { this.plz = plz; }

	public String getStrasse() { return strasse; }

	public void setStrasse(String strasse) { this.strasse = strasse; }

	public String getWohnort() { return wohnort; }

	public void setWohnort(String wohnort) { this.wohnort = wohnort; }

	public int getId() { return id;	}

	public void setId(int id) {	this.id = id; }

	public String getEmail() { return email;	}

	public void setEmail(String email) { this.email = email; }

	public String getBenutzername() { return benutzername; }

	public void setBenutzername(String benutzername) { this.benutzername = benutzername; }

	public String getPasswort() { return passwort; }

	public void setPasswort(String passwort) { this.passwort = passwort; }

	// Weitere Dienste der Kunden-Objekte
	public static User login(List<Kunde> bestandsKunden, String benutzername, String passwort) {
		//Die Schleife durchläuft jedes Element in der Userliste useres und weist es der Variable user zu
		for (User user : bestandsKunden) {
			// Überprüfen, ob Benutzername und Passwort korrekt sind
			if (user.getBenutzername().equals(benutzername) && user.getPasswort().equals(passwort)) {
				System.out.print("Sie wurden eingeloggt");
				return user;
			}
		}
		System.out.print("Benutzername oder Passwort falsch.");
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
		if (!(obj instanceof Kunde)) {
			return false;
		}
		//Wenn das übergebene Objekt (k bzw. obj) eine Instanz der Kunde-Klasse ist, können wir es mit einem Casting-Operator ((Kunde) obj) in eine Kunde-Referenz konvertieren.
		//Dies ist notwendig, weil die equals-Methode auf die Attribute des Objekts zugreifen muss, der obj-Parameter vor der Konvertierung aber vom Typ Object ist,
		//der nicht die Attribute der Kunde-Klasse hat.
		//Anschließend speichern wir die Refernez des übergebenen Objekts (obj) als other.
		Kunde other = (Kunde) obj;
		//Die Objects.equals()-Methode wird verwendet, um die Gleichheit der Attribute zu prüfen.
		//Wenn alle Attribute übereinstimmen = true. Wenn mindestens eines der Attribute nicht übereinstimmt = false
		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.nachname, other.nachname)
				&& Objects.equals(this.email, other.email)
				&& Objects.equals(this.benutzername, other.benutzername);
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
		return Objects.hash(id, name, nachname, email, benutzername, passwort, strasse, plz, wohnort);
	}
}
