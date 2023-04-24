package bib.local.domain.exceptions;

import bib.local.entities.Artikel;

/**
 * Exception zur Signalisierung, dass ein Artikel bereits existiert (z.B. bei einem Einfügevorgang).
 * 
 * @author teschke
 */
public class ArtikelExistiertBereitsException extends Exception {

	private Artikel artikel;
	
	/**
	 * Konstruktor
	 * 
	 * @param artikel Der bereits existierende Artikel
	 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
	 */
	public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		super("Artikel mit Artikelbezeichnung " + artikel.getArtikelbezeichnung() + " und Nummer " + artikel.getNummer()
				+ " existiert bereits" + zusatzMsg);
		this.artikel = artikel;
	}

	public Artikel getArtikel() {
		return artikel;
	}
}
