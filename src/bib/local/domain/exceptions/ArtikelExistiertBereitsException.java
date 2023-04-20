package bib.local.domain.exceptions;

import bib.local.entities.Artikel;

/**
 * Exception zur Signalisierung, dass ein Buch bereits existiert (z.B. bei einem Einfügevorgang).
 * 
 * @author teschke
 */
public class ArtikelExistiertBereitsException extends Exception {

	private Artikel artikel;
	
	/**
	 * Konstruktor
	 * 
	 * @param artikel Das bereits existierende Buch
	 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
	 */
	public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		super("Buch mit Titel " + artikel.getTitel() + " und Nummer " + artikel.getNummer()
				+ " existiert bereits" + zusatzMsg);
		this.artikel = artikel;
	}

	public Artikel getBuch() {
		return artikel;
	}
}
