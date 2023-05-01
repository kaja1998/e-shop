package bib.local.entities;


import java.util.Objects;

/**
 * Klasse zur Repräsentation einzelner Kunden.
 * 
 * Die Klasse wird derzeit noch nicht verwendet, weil die Shop-
 * Anwendung bislang nur Artikel verwaltet.
 * 
 * @author Sund
 */
public class Kunde {

	private int kundenNr;
	private String kName;
	private String kNachname;
	private String kEmail;
	private String kBenutzername;
	private String kPasswort;
	private String strasse = "";
	private int plz;
	private String wohnort = "";
	private float umsatz = 0.0f;

    public Kunde(String kName, String kNachname, String strasse, int plz, String wohnort, String kEmail, String kBenutzername, String kPasswort) {
		this.kName = kName;
		this.kNachname = kNachname;
		this.strasse = strasse;
		this.plz = plz;
		this.wohnort = wohnort;
		this.kEmail = kEmail;
		this.kBenutzername = kBenutzername;
		this.kPasswort = kPasswort;
	}
    
	// Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
	// z.B. getStrasse() und setStrasse()

	public String getkName() {	return kName;	}

	public void setkName(String kName) { this.kName = kName; }

	public String getkNachname() {	return kNachname; }

	public void setkNachname(String kNachname) { this.kNachname = kNachname; }

	public float getUmsatz() {	return umsatz;	}

	public void setUmsatz(float umsatz) { this.umsatz = umsatz;	}

	public int getPlz() { return plz; }

	public void setPlz(int plz) { this.plz = plz; }

	public String getStrasse() { return strasse; }

	public void setStrasse(String strasse) { this.strasse = strasse; }

	public String getWohnort() { return wohnort; }

	public void setWohnort(String wohnort) { this.wohnort = wohnort; }

	public int getKundenNr() { return kundenNr;	}

	public void setKundenNr(int kundenNr) {	this.kundenNr = kundenNr; }

	public String getkEmail() { return kEmail;	}

	public void setkEmail(String kEmail) { this.kEmail = kEmail; }

	public String getkBenutzername() { return kBenutzername; }

	public void setkBenutzername(String kBenutzername) { this.kBenutzername = kBenutzername; }

	public String getkPasswort() { return kPasswort; }

	public void setkPasswort(String kPasswort) { this.kPasswort = kPasswort; }

	public String getkStrasse() { return strasse; }

	public void setkStrasse(String strasse) { this.strasse = strasse; }

	// Weitere Dienste der Kunden-Objekte


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
		return Objects.equals(this.kName, other.kName)
				&& Objects.equals(this.kNachname, other.kNachname)
				&& Objects.equals(this.kEmail, other.kEmail)
				&& Objects.equals(this.kBenutzername, other.kBenutzername);
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
		return Objects.hash(kundenNr, kName, kNachname, kEmail, kBenutzername, kPasswort, strasse, plz, wohnort);
	}
}
