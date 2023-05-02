package bib.local.persistence;

import java.io.IOException;
import java.util.List;

import bib.local.entities.Artikel;
import bib.local.entities.Kunde;

/**
 * @author Sund
 *
 * Allgemeine Schnittstelle für den Zugriff auf ein Speichermedium
 * (z.B. Datei oder Datenbank) zum Ablegen von beispielsweise
 * Artikel- oder Kundendaten.
 * 
 * Das Interface muss von Klassen implementiert werden, die eine
 * Persistenz-Schnittstelle realisieren wollen.
 */
public interface PersistenceManager {

	public void openForReading(String datenquelle) throws IOException;

	public void openForReadingK(String datenquelle) throws IOException;

	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();

	/**
	 * Methode zum Einlesen derArtikeldaten aus einer externen Datenquelle.
	 * 
	 * @return Artikel-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Artikel ladeArtikel() throws IOException;

	/**
	 * Methode zum Schreiben der Artikeldaten in eine externe Datenquelle.
	 * 
	 * @param a Artikel-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Artikel a) throws IOException;

	public boolean speichereKunde(Kunde kunde, List<Kunde> bestehendeKunden) throws IOException;

	/**
	 * Methode zum Einlesen der Kundendaten aus einer externen Datenquelle.
	 *
	 * @return Kunden-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Kunde ladeKunde() throws IOException;

	/**
	 * Methode zum Schreiben der Kundendaten in eine externe Datenquelle.
	 *
	 * @param k Kunden-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */

}
