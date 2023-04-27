package bib.local.domain;

import java.io.IOException;
import java.util.ArrayList;

import bib.local.domain.exceptions.ArtikelExistiertBereitsException;
import bib.local.entities.Artikel;
import bib.local.entities.ArtikelListe;
import bib.local.entities.Kunde;

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
	// Präfix für Namen der Dateien, in der die Shop-Daten gespeichert sind
	private String datei = "";

	//Variable von Artikelverwaltung wird deklariert. Kann später dazu verwendet werden, um ein Objekt dieser Klasse zu erstellen
	private ArtikelVerwaltung artikelVW;

	//Variable von Kundenverwaltung wird deklariert. Kann später dazu verwendet werden, um ein Objekt dieser Klasse zu erstellen
	private KundenVerwaltung kundenVW;
	// hier weitere Verwaltungsklassen, z.B. für Autoren oder Angestellte
	
	/**
	 * Konstruktor, der die Basisdaten (Artikel, Kunden, Autoren) aus Dateien einliest
	 * (Initialisierung des Shops).
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

		//Es wird eine neue Instanz der Klasse ArtikelVerwaltung erzeugt und der Variablen artikelVW zugewiesen
		// Artikelbestand aus Datei einlesen
		artikelVW = new ArtikelVerwaltung();
		artikelVW.liesDaten(datei+"_B.txt");

		//Es wird eine neue Instanz der Klasse KundenVerwaltung erzeugt und der Variablen kundenVW zugewiesen
		// Kundenkartei aus Datei einlesen
		kundenVW = new KundenVerwaltung();
		kundenVW.liesDaten(datei+"_K.txt");
		//kundenVW.schreibeDaten(datei+"_K.txt");
	}


	/**
	 * Methode, die eine Liste aller im Bestand befindlichen Artikel zurückgibt.
	 * 
	 * @return Liste aller Artikel im Bestand des Shops
	 */
	public ArtikelListe gibAlleArtikel() {
		// einfach delegieren an meineBuecher
		return artikelVW.getArtikelBestand();		//mit "artikelVW"-Objekt wird get-Methode aufgerufen
	}

	/**
	 * Methode zum Suchen von Artikeln anhand der Artikelbezeichnung. Es wird eine Liste von Artikeln
	 * zurückgegeben, die alle Artikel mit exakt übereinstimmender Artikelbezeichnung enthält.
	 * 
	 * @param artikelbezeichnung Artikelbezeichnung des gesuchten Artikels
	 * @return Liste der gefundenen Artikel (evtl. leer)
	 */
	public ArtikelListe sucheNachArtikelbezeichnung(String artikelbezeichnung) {
		// einfach delegieren an meineBuecher
		return artikelVW.sucheArtikel(artikelbezeichnung);
	}

	/**
	 * Methode zum Einfügen eines neuen Artikels in den Bestand.
	 * Wenn der Artikel bereits im Bestand ist, wird der Bestand nicht geändert.
	 * 
	 * @param artikelbezeichnung Artikelbezeichnung des Artikels
	 * @param nummer Nummer des Artikels
	 * @return Artikel-Objekt, das im Erfolgsfall eingefügt wurde
	 * @throws ArtikelExistiertBereitsException wenn der Artikel bereits existiert
	 */
	public Artikel fuegeArtikelEin(String artikelbezeichnung, int nummer) throws ArtikelExistiertBereitsException {
		Artikel b = new Artikel(artikelbezeichnung, nummer);
		artikelVW.einfuegen(b);
		return b;
	}

	/**
	 * Methode zum Löschen eines Artikels aus dem Bestand.
	 * Es wird nur das erste Vorkommen des Artikels gelöscht.
	 * 
	 * @param artikelbezeichnung Artikelbezeichnung des Artikels
	 * @param nummer Nummer des Artikels
	 */
	public void loescheArtikel(String artikelbezeichnung, int nummer) {
		Artikel b = new Artikel(artikelbezeichnung, nummer);
		artikelVW.loeschen(b);
	}
	
	/**
	 * Methode zum Speichern des Artikelbestands in einer Datei.
	 * 
	 * @throws IOException z.B. wenn Datei nicht existiert
	 */
	public void schreibeArtikel() throws IOException {
		artikelVW.schreibeDaten(datei+"_B.txt");
	}

	/**
	 * Methode zum Hinzufügen eines Kunden zur Liste aller Kunden
	 */
	public void fuegeKundeHinzu(Kunde kunde) {
		kundenVW.fuegeKundeHinzu(kunde);
	}

	// Getter und Setter, da Klasse in KundenVerwaltung privat ist
	//public void getKunden() {
	//	kundenVW.getKunden();
	//}

	public ArrayList<Kunde> getKunden() {
		return kundenVW.getKunden();
	}

	//
	public void setKunden(ArrayList<Kunde> kunden) {		//Methodenbezeichner
		kundenVW.setKunden(kunden);							//Methodendefinition
	}
}
