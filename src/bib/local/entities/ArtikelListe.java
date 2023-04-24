package bib.local.entities;

/**
 * (Nicht ganz) abstrakter Datentyp zur Verwaltung von Artikeln in einer Liste.
 * Die Liste ist rekursiv aufgebaut: sie besteht aus einem Artikel (artikel) und einer
 * (Rest-)Liste (next).
 * 
 * @author teschke
 * @version 1 (verkettete Liste)
 */
public class ArtikelListe {

	// das Listenelement
	private Artikel artikel = null;

	// Verweis auf Rest der Liste. 
	// Der Rest der Liste ist wieder eine ArtikelListe,
	// wobei die um ein Element (nämlich der obige Artikel) kürzer ist!
	private ArtikelListe next = null;
	
	/**
	 * Default-Konstruktor
	 * (erforderlich, weil zweiter Konstruktor existiert)
	 */
	public ArtikelListe() {
		// leerer Default-Konstruktor
	}
	
	/** 
	 * Copy-Konstruktor: 
	 * Legt eine neue ArtikelListe als Kopie einer anderen Liste an.
	 * Wichtig: es wird nur das ArtikelListen-Objekt mit seinen Verweisen
	 * kopiert; die enthaltenen Artikel-Objekte werden nicht kopiert.
	 */
	public ArtikelListe(ArtikelListe original) {		// wird hier ein Objekt erzeugt -> original?
		while (original != null) {
			Artikel artikel = original.gibErstenArtikel();
			if (artikel != null) {
				this.einfuegen(artikel);
				original = original.gibRestlicheArtikel();
			} 
		}
	}
	
	/**
	 * Methode, die den ersten Artikel der Liste zurückgibt.
	 */
	public Artikel gibErstenArtikel() {
		return artikel;
	}
	
	/**
	 * Methode, die die Liste der verbleibenden Artikel zurückgibt (d.h. ohne den ersten Artikel).
	 */
	public ArtikelListe gibRestlicheArtikel() {
		return next;
	}

	/**
	 * Methode, die ein Artikel an das Ende der Artikelliste einfügt.
	 * 
	 * @param einArtikel der einzufügende Artikel
	 */
	public void einfuegen(Artikel einArtikel) {
		// Liste noch leer, d.h. auch kein erster Artikel?
		if (artikel == null) {
			artikel = einArtikel;
		}
		else {
			// Sind wir am Listenende?
			if (next == null) {
				// Ja: dann neue Restliste mit Artikel als Element erzeugen
				next = new ArtikelListe();			//Objekt Next wird erzeugt und ein Standard / leerer Konstruktor
			}
			// Artikel in existierende Restliste einfügen (rekursiver Aufruf!)
			next.einfuegen(einArtikel);				// Objekt next ruft Methode erneut auf und übergibt als Parameter "einArtikel"
		}
	}	

	/**
	 * Methode, die prüft, ob ein Artikel bereits in der Artikelliste vorhanden ist.
	 * 
	 * @param andererArtikel der gesuchte Artikel
	 */
	public boolean enthaelt(Artikel andererArtikel) {
		if (this.artikel != null && this.artikel.equals(andererArtikel)) {
			return true;
		} else {
			if (this.next != null) {
				return this.next.enthaelt(andererArtikel);
			}
		}
		return false;
	}
	
	// TODO: Weitere Methoden, z.B. zum Entfernen von Artikeln
	// ...
	/**
	 * Methode, die eine ArtikelListe zurückgibt ohne den übergebenen Artikel.
	 * 
	 * @param artikel der zu löschende Artikel
	 */
	public ArtikelListe loeschen(Artikel artikel) {
		if (this.artikel == null) {
			return this;
		}
		
		if (this.artikel.equals(artikel)) {
			return next;
		} else {
			if (next != null) {
				next = next.loeschen(artikel);
			} 
			return this;
		}
	}
	
	/** 
	 * Standard-Methode von Object überschrieben.
	 * Methode wird immer automatisch aufgerufen, wenn ein ArtikelListe-Objekt als String
	 * benutzt wird (z.B. in println(...);)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "";
		// Der Zeilenumbruch wird auf verschiedenen Betriebssystemen durch
		// unterschiedliche Zeichenketten (sog. Escape-Sequenzen) erreicht,
		// unter Windows z.B. "\n"
		String zeilenEnde = System.getProperty("line.separator");
		ArtikelListe aktArtikelElt = this;
		if (aktArtikelElt.artikel == null)
			return "Liste ist leer." + zeilenEnde;
		else {
			while (aktArtikelElt != null) {
				result += aktArtikelElt.artikel + zeilenEnde;
				aktArtikelElt = aktArtikelElt.next;
			}
		}
		return result;
	}
}
