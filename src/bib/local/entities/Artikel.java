package bib.local.entities;


/**
 * Klasse zur Repräsentation einzelner Artikel.
 * 
 * @author teschke
 */
public class Artikel {

	// Attribute zur Beschreibung eines Artikels:
	private String titel;
	private int nummer;
	private boolean verfuegbar; 
	
	
	public Artikel(String titel, int nr) {
		this(titel, nr, true);
	}

	public Artikel(String titel, int nr, boolean verfuegbar) {
		nummer = nr;
		this.titel = titel;
		this.verfuegbar = verfuegbar;
	}
	
	// --- Dienste der Artikel-Objekte ---

	/**
	 * Standard-Methode von Object überschrieben.
	 * Methode wird immer automatisch aufgerufen, wenn ein Artikel-Objekt als String
	 * benutzt wird (z.B. in println(buch);)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String verfuegbarkeit = verfuegbar ? "verfuegbar" : "ausgeliehen";
		return ("Nr: " + nummer + " / Titel: " + titel + " / " + verfuegbarkeit);
	}

	/**
	 * Standard-Methode von Object überschrieben.
	 * Methode dient Vergleich von zwei Artikel-Objekten anhand ihrer Werte,
	 * d.h. Titel und Nummer.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public boolean equals(Object andererArtikel) {
		if (andererArtikel instanceof Artikel)
			return ((this.nummer == ((Artikel) andererArtikel).nummer)
					&& (this.titel.equals(((Artikel) andererArtikel).titel)));
		else
			return false;
	}

	
	/*
	 * Ab hier Accessor-Methoden
	 */
	
	public int getNummer() {
		return nummer;
	}

	public String getTitel() {
		return titel;
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}
}
