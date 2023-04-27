package bib.local.domain;

import java.io.IOException;

import bib.local.domain.exceptions.ArtikelExistiertBereitsException;
import bib.local.persistence.FilePersistenceManager;
import bib.local.persistence.PersistenceManager;
import bib.local.entities.Artikel;
import bib.local.entities.ArtikelListe;


/**
 * Klasse zur Verwaltung von Artikeln.
 * 
 * @author teschke
 * @version 1 (Verwaltung in verketteter Liste)
 */
public class ArtikelVerwaltung {

	// Verwaltung des Artikelbestands in einer verketteten Liste
	private ArtikelListe artikelBestand = new ArtikelListe();

	// Persistenz-Schnittstelle, die für die Details des Dateizugriffs verantwortlich ist
	private PersistenceManager pm = new FilePersistenceManager();
	
	/**
	 * Methode zum Einlesen von Artikeldaten aus einer Datei.
	 * 
	 * @param datei Datei, die einzulesenden Artikelbestand enthält
	 * @throws IOException
	 */
	public void liesDaten(String datei) throws IOException {
		// PersistenzManager für Lesevorgänge öffnen
		pm.openForReading(datei);

		Artikel einArtikel;
		do {
			// Artikel-Objekt einlesen
			einArtikel = pm.ladeArtikel();
			if (einArtikel != null) {
				// Artikel in Liste einfügen
				try {
					einfuegen(einArtikel);
				} catch (ArtikelExistiertBereitsException e1) {
					// Kann hier eigentlich nicht auftreten,
					// daher auch keine Fehlerbehandlung...
				}
			}
		} while (einArtikel != null);

		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}

	/**
	 * Methode zum Schreiben der Artikeldaten in eine Datei.
	 * 
	 * @param datei Datei, in die der Artikelbestand geschrieben werden soll
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException  {
		// PersistenzManager für Schreibvorgänge öffnen
		pm.openForWriting(datei);

		ArtikelListe liste = artikelBestand;
		while (liste != null) {
			Artikel artikel = liste.gibErstenArtikel();
			if (artikel != null) {
				// speichern
				pm.speichereArtikel(artikel);
			}
			liste = liste.gibRestlicheArtikel();
		}
		
		// Persistenz-Schnittstelle wieder schließen
		pm.close();
	}
		
	/**
	 * Methode, die ein Artikel an das Ende der Artikelliste einfügt.
	 * 
	 * @param einArtikel den einzufügenden Artikel
	 * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
	 */
	public void einfuegen(Artikel einArtikel) throws ArtikelExistiertBereitsException {
		if (artikelBestand.enthaelt(einArtikel)) {
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}

		// das übernimmt die ArtikelListe:
		artikelBestand.einfuegen(einArtikel);
	}

	/**
	 * Methode zum Löschen eines Artikels aus dem Bestand.
	 * 
	 * @param einArtikel den zu löschenden Artikel
	 */
	public void loeschen(Artikel einArtikel) {
		// das übernimmt die ArtikelListe:
		artikelBestand = artikelBestand.loeschen(einArtikel);
	}

	/**
	 * Methode, die anhand eines Titels nach Artikeln sucht. Es wird eine Liste von Artikeln
	 * zurückgegeben, die alle Artikel mit exakt übereinstimmendem Titel enthält.
	 * 
	 * @param titel Titel des gesuchten Artikels
	 * @return Liste der Artikel mit gesuchtem Titel (evtl. leer)
	 */
	public ArtikelListe sucheArtikel(String titel) {
		ArtikelListe suchErg = new ArtikelListe();
		ArtikelListe aktArtikelListenElt = artikelBestand;
		while (aktArtikelListenElt != null) {
			Artikel aktArtikel = aktArtikelListenElt.gibErstenArtikel();
			if (aktArtikel.getArtikelbezeichnung().equals(titel)) {
				// gefundenen Artikel in Suchergebnis eintragen
				suchErg.einfuegen(aktArtikel);
			}
			aktArtikelListenElt = aktArtikelListenElt.gibRestlicheArtikel();
		}
		return suchErg;
	}
	
	/**
	 * Methode, die eine KOPIE des Artikelbestands zurückgibt.
	 * (Eine Kopie ist eine gute Idee, wenn ich dem Empfänger 
	 * der Daten nicht ermöglichen möchte, die Original-Daten 
	 * zu manipulieren.)
	 * ABER ACHTUNG: Die in der kopierten Artikelliste referenzierten
	 * 			sind nicht kopiert worden, d.h. ursprüngliche
	 * 			Artikelliste und ihre Kopie verweisen auf dieselben
	 * 			Artikel-Objekte. Eigentlich müssten die einzelnen Artikel-Objekte
	 * 			auch kopiert werden.
	 *
	 * @return Liste aller Artikel im Artikelbestand (Kopie)
	 */
	public ArtikelListe getArtikelBestand() {
		return new ArtikelListe(artikelBestand);
	}
	
	// TODO: Weitere Methoden, z.B. zum Auslesen und Entfernen von Artikeln
	// ...
}
