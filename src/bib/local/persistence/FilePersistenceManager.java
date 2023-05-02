package bib.local.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import bib.local.entities.Artikel;
import bib.local.entities.Kunde;

/**
 * @author Sund
 *
 * Realisierung einer Schnittstelle zur persistenten Speicherung von
 * Daten in Dateien.
 * @see bib.local.persistence.PersistenceManager
 */
public class FilePersistenceManager implements PersistenceManager {

	//Die Variable "reader" ist vom Typ "BufferedReader" und kann verwendet werden, um Zeichen aus einer Datei zu lesen.
	private BufferedReader reader = null;
	//Die Variable "writer" ist vom Typ "PrintWriter" und kann Zeichen in eine Datei schreiben.
	// Null bedeutet nur, dass die Variable auf keinen bestimmten Speicherbereich im Computer zeigt
	private PrintWriter writer = null;

	//Die erste Methode openForReading(String datei) öffnet eine Datei zum Lesen.
	//Der Parameter datei ist der Name der Datei, die geöffnet werden soll.
	//Die Methode erstellt einen BufferedReader, der den Inhalt der Datei lesen kann.
	//Wenn die angegebene Datei nicht gefunden wird, wird eine FileNotFoundException ausgelöst.
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForReadingK(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

	//Die Methode close() scheint eine Methode zum Schließen eines Writer- und/oder Reader-Objekts zu sein, die in einem Feld writer bzw. reader gespeichert werden.
	public boolean close() {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}
		}

		return true;
	}

	/**
	 * Methode zum Einlesen der Artikeldaten aus einer externen Datenquelle.
	 * Das Verfügbarkeitsattribut ist in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @return Artikel-Objekt, wenn Einlesen erfolgreich, false null
	 */
	public Artikel ladeArtikel() throws IOException {
		// Titel einlesen
		String titel = liesZeile();
		if (titel == null) {
			// keine Daten mehr vorhanden
			return null;
		}
		// Nummer einlesen ...
		String nummerString = liesZeile();
		// ... und von String in int konvertieren
		int nummer = Integer.parseInt(nummerString);
		
		// Artikel kaufen?
		String verfuegbarCode = liesZeile();
		// Codierung des verkaufstatus in boolean umwandeln
		boolean verfuegbar = verfuegbarCode.equals("t") ? true : false;
		
		// neues Artikel-Objekt anlegen und zurückgeben
		return new Artikel(titel, nummer, verfuegbar);
	}

	/**
	 * Methode zum Schreiben Artikeldaten in eine externe Datenquelle.
	 * Das Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
	 * codiert abgelegt.
	 * 
	 * @param b Artikel-Objekt, das gespeichert werden soll
	 * @return true, wenn Schreibvorgang erfolgreich, false sonst
	 */
	public boolean speichereArtikel(Artikel b) throws IOException {
		// Titel, Nummer und Verfügbarkeit schreiben
		schreibeZeile(b.getArtikelbezeichnung());
//		schreibeZeile(Integer.valueOf(b.getNummer()).toString());
		schreibeZeile(b.getNummer() + "");
		if (b.isVerfuegbar())
			schreibeZeile("t");
		else
			schreibeZeile("f");

		return true;
	}

	public Kunde ladeKunde() throws IOException {
		// Variablen
		int kundenNrInt;

		// Zunächst wird die Kundennummer als String eingelesen und in einen Integer umgewandelt.
		String kundenNrString = liesZeile();
		if (kundenNrString != null) {
			kundenNrInt = Integer.parseInt(kundenNrString);

		} else {
			//Keine Daten mehr vorhanden ODER falls die Kundennummer in der Datei nicht mehr vorhanden ist, wird null zurückgegeben.
			return null;
		}
		// Daten wie Vorname, Nachname, Email, Benutzername, Passwort, Straße, PLZ und Stadt werden nacheinander aus der Datei gelesen und in Variablen gespeichert.
		// Vornamen einlesen
		String vorname = liesZeile();

		// Nachnamen einlesen
		String nachname = liesZeile();

		// Straße einlesen
		String strasse = liesZeile();

		// Kundennummer einlesen
		String plzString = liesZeile();
		int plzInt = Integer.parseInt(plzString);

		// Stadt einlesen
		String stadt = liesZeile();

		// Email einlesen
		String email = liesZeile();

		// Benutzernamen einlesen
		String benutzername = liesZeile();

		// Passwort einlesen
		String passwort = liesZeile();

		//Es wird ein neues Kunde-Objekt mit den ausgelesenen Daten erstellt und zurückgegeben.
		Kunde kunde = new Kunde(vorname, nachname, strasse, plzInt, stadt, email, benutzername, passwort);
		kunde.setKundenNr(kundenNrInt);
		return kunde;
	}

	//Die Methode speichereKunde(Kunde k) schreibt die Daten eines Kunde-Objekts in eine Datei.
	//Dabei werden nacheinander der Vorname, Nachname, Email, Benutzername, Passwort, Straße, PLZ und Stadt des Kunden geschrieben.
	//Die Methode gibt true zurück, wenn das Schreiben erfolgreich war.
	public boolean speichereKunde(Kunde neuerKunde, List<Kunde> bestehendeKunden) throws IOException {
		// Schreibe alle bestehenden Kunden in die Datei
		for (Kunde kunde : bestehendeKunden) {
			this.schreibeKundeInDatei(kunde);
		}

		// Schreibe neuen Kunden in die Datei
		this.schreibeKundeInDatei(neuerKunde);

		// gebe true zurück, wenn alles geklappt hat
		return true;
	}

	public void schreibeKundeInDatei(Kunde kunde) throws IOException {
		schreibeZeile(String.valueOf(kunde.getKundenNr()));
		schreibeZeile(kunde.getkName());
		schreibeZeile(kunde.getkNachname());
		schreibeZeile(kunde.getStrasse());
		schreibeZeile(String.valueOf(kunde.getPlz()));
		schreibeZeile(kunde.getWohnort());
		schreibeZeile(kunde.getkEmail());
		schreibeZeile(kunde.getkBenutzername());
		schreibeZeile(kunde.getkPasswort());
	}
	
	/*
	 * Private Hilfsmethoden
	 * die Methode liesZeile() liest eine Zeile aus einer Datei und gibt sie als String zurück.
	 */
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	//Die Methode schreibeZeile(String daten) schreibt einen String in eine Datei.
	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
}
