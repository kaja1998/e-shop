package bib.local.domain;

import java.io.IOException;

import bib.local.domain.exceptions.ArtikelExistiertBereitsException;
import bib.local.entities.Artikel;
import bib.local.entities.ArtikelListe;

/**
 * Klasse zur Verwaltung einer (sehr einfachen) Bibliothek.
 * Bietet Methoden zum Zurückgeben aller Artikel im Bestand,
 * zur Suche nach Artikeln, zum Einfügen neuer Artikel
 * und zum Speichern des Bestands.
 * 
 * @author teschke
 * @version 1 (Verwaltung der Artikel in verketteter Liste)
 */
public class Shop {
	// Präfix für Namen der Dateien, in der die Bibliotheksdaten gespeichert sind
	private String datei = "";
	
	private ArtikelVerwaltung ArtikelVW;
	// private KundenVerwaltung kundenVW;
	// hier weitere Verwaltungsklassen, z.B. für Autoren oder Angestellte
	
	/**
	 * Konstruktor, der die Basisdaten (Artikel, Kunden, Autoren) aus Dateien einliest
	 * (Initialisierung der Bibliothek).
	 * 
	 * Namensmuster für Dateien:
	 *   datei+"_B.txt" ist die Datei der Artikel
	 *   datei+"_K.txt" ist die Datei der Kunden
	 * 
	 * @param datei Präfix für Dateien mit Basisdaten (Artikel, Kunden, Autoren)
	 * @throws IOException z.B. wenn eine der Dateien nicht existiert.
	 */
	public Shop(String datei) throws IOException {
		this.datei = datei;
		
		// Artikelbestand aus Datei einlesen
		ArtikelVW = new ArtikelVerwaltung();
		ArtikelVW.liesDaten(datei+"_B.txt");

//		// Kundenkartei aus Datei einlesen
//		meineKunden = new KundenVerwaltung();
//		meineKunden.liesDaten(datei+"_K.txt");
//		meineKunden.schreibeDaten(datei+"_K.txt");
	}


	/**
	 * Methode, die eine Liste aller im Bestand befindlichen Artikel zurückgibt.
	 * 
	 * @return Liste aller Artikel im Bestand der Bibliothek
	 */
	public ArtikelListe gibAlleArtikel() {
		// einfach delegieren an meineBuecher
		return ArtikelVW.getArtikelBestand();
	}

	/**
	 * Methode zum Suchen von Artikeln anhand des Titels. Es wird eine Liste von Artikeln
	 * zurückgegeben, die alle Artikel mit exakt übereinstimmendem Titel enthält.
	 * 
	 * @param titel Titel des gesuchten Artikels
	 * @return Liste der gefundenen Artikel (evtl. leer)
	 */
	public ArtikelListe sucheNachTitel(String titel) {
		// einfach delegieren an meineBuecher
		return ArtikelVW.sucheArtikel(titel);
	}

	/**
	 * Methode zum Einfügen eines neuen Artikels in den Bestand.
	 * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
	 * 
	 * @param titel Titel des Artikels
	 * @param nummer Nummer des Artikels
	 * @return Artikel-Objekt, das im Erfolgsfall eingefügt wurde
	 * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
	 */
	public Artikel fuegeArtikelEin(String titel, int nummer) throws ArtikelExistiertBereitsException {
		Artikel b = new Artikel(titel, nummer);
		ArtikelVW.einfuegen(b);
		return b;
	}

	/**
	 * Methode zum Löschen eines Artikels aus dem Bestand.
	 * Es wird nur das erste Vorkommen des Artikels gelöscht.
	 * 
	 * @param titel Titel des Artikels
	 * @param nummer Nummer des Artikels
	 */
	public void loescheArtikel(String titel, int nummer) {
		Artikel b = new Artikel(titel, nummer);
		ArtikelVW.loeschen(b);
	}
	
	/**
	 * Methode zum Speichern des Artikelbestands in einer Datei.
	 * 
	 * @throws IOException z.B. wenn Datei nicht existiert
	 */
	public void schreibeArtikel() throws IOException {
		ArtikelVW.schreibeDaten(datei+"_B.txt");
	}

	// TODO: Weitere Funktionen der Bibliotheksverwaltung, z.B. ausleihen, zurückgeben etc.
	// ...
}
