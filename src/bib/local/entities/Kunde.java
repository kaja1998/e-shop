package bib.local.entities;


/**
 * Klasse zur Repräsentation einzelner Kunden.
 * 
 * Die Klasse wird derzeit noch nicht verwendet, weil die Bibliotheks-
 * Anwendung bisland nur Artikel verwaltet.
 * 
 * @author teschke
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

    public Kunde(String kName, String kNachname, String kEmail, String kBenutzername, String kPasswort) {
		this.kName = kName;
		this.kNachname = kNachname;
		this.kEmail = kEmail;
		this.kBenutzername = kBenutzername;
		this.kPasswort = kPasswort;
	}
    
	// Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
	// z.B. getStrasse() und setStrasse()

	public String getkName() {
		return kName;
	}

	public void setkName(String kName) {
		this.kName = kName;
	}

	public String getkNachname() {
		return kNachname;
	}

	public void setkNachname(String kNachname) {
		this.kNachname = kNachname;
	}

	public float getUmsatz() {
		return umsatz;
	}

	public void setUmsatz(float umsatz) {
		this.umsatz = umsatz;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getWohnort() {
		return wohnort;
	}

	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}

	public int getKundenNr() { return kundenNr;	}

	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}

	public String getkEmail() { return kEmail;	}

	public void setkEmail() { this.kEmail = kEmail; }

	public String getkBenutzername() { return kBenutzername; }

	public void setkBenutzername() { this.kBenutzername = kBenutzername; }

	public String getkPasswort() { return kPasswort; }

	public void setkPasswort() { this.kPasswort = kPasswort; }


	// Weitere Dienste der Kunden-Objekte
}
