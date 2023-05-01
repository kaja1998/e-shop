package bib.local.entities;


/**
 * Klasse zur Repräsentation einzelner Artikel.
 * 
 * @author Sund
 */
public class Artikel {

	// Attribute zur Beschreibung eines Artikels:
	private String artikelbezeichnung;
	private int nummer;
	private boolean verfuegbar; 
	
	
	public Artikel(String artikelbezeichnung, int nr) {
		this(artikelbezeichnung, nr, true);
	}

	public Artikel(String artikelbezeichnung, int nr, boolean verfuegbar) {
		nummer = nr;
		this.artikelbezeichnung = artikelbezeichnung;
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
		String verfuegbarkeit = verfuegbar ? "verfuegbar" : "ausverkauft";		// Verkürzte If-else-Anweisung. Wenn verfügbar = true, dann wird "verfügbar" ausgegeben. Wenn false, dann "ausverkauft"
		return ("Nr: " + nummer + " / Artikelbezeichnung: " + artikelbezeichnung + " / " + verfuegbarkeit);
	}

	/**
	 * Standard-Methode von Object überschrieben.
	 * Methode dient Vergleich von zwei Artikel-Objekten anhand ihrer Werte,
	 * d.h. Artikelbezeichnung und Nummer.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public boolean equals(Object andererArtikel) {
		if (andererArtikel instanceof Artikel)
			return ((this.nummer == ((Artikel) andererArtikel).nummer)
					&& (this.artikelbezeichnung.equals(((Artikel) andererArtikel).artikelbezeichnung)));
		else
			return false;
	}

	
	/*
	 * Ab hier Accessor-Methoden
	 */
	
	public int getNummer() {
		return nummer;
	}

	public String getArtikelbezeichnung() {
		return artikelbezeichnung;
	}

	public boolean isVerfuegbar() {
		return verfuegbar;
	}
}
