package bib.local.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import bib.local.entities.Artikel;
import bib.local.entities.Kunde;

/**
 * @author teschke
 *
 * Realisierung einer Schnittstelle zur persistenten Speicherung von
 * Daten in Dateien.
 * @see bib.local.persistence.PersistenceManager
 */
public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

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
		//Zunächst wird die Kundennummer als String eingelesen und in einen Integer umgewandelt.
		String kundenNrString = liesZeile();
		if (kundenNrString != null) {
			int kundenNrInt = Integer.parseInt(kundenNrString);

		} else {
			//Keine Daten mehr vorhanden ODER falls die Kundennummer in der Datei nicht mehr vorhanden ist, wird null zurückgegeben.
			return null;
		}
		//Daten wie Vorname, Nachname, Email, Benutzername, Passwort, Straße, PLZ und Stadt werden nacheinander aus der Datei gelesen und in Variablen gespeichert.
		// Vornamen einlesen
		String vorname = liesZeile();

		// Nachnamen einlesen
		String nachname = liesZeile();

		// Email einlesen
		String email = liesZeile();

		// Benutzernamen einlesen
		String benutzername = liesZeile();

		// Passwort einlesen
		String passwort = liesZeile();

		// Straße einlesen
		String strasse = liesZeile();

		// Kundennummer einlesen
		String plzString = liesZeile();
		int plzInt = Integer.parseInt(plzString);

		// Stadt einlesen
		String stadt = liesZeile();

		//Es wird ein neues Kunde-Objekt mit den ausgelesenen Daten erstellt und zurückgegeben.
		return new Kunde(vorname, nachname, email, benutzername, passwort);
	}

	//Die Methode speichereKunde(Kunde k) schreibt die Daten eines Kunde-Objekts in eine Datei.
	//Dabei werden nacheinander der Vorname, Nachname, Email, Benutzername, Passwort, Straße, PLZ und Stadt des Kunden geschrieben.
	//Die Methode gibt true zurück, wenn das Schreiben erfolgreich war.
	public boolean speichereKunde(Kunde k) throws IOException {
		// Vorname, Nachname, Email, Benutzername, Passwort, Straße, PLZ, Wohnort
		schreibeZeile(k.getkName());
		schreibeZeile(k.getkNachname());
		schreibeZeile(k.getkEmail());
		schreibeZeile(k.getkBenutzername());
		schreibeZeile(k.getkPasswort());
		schreibeZeile(k.getStrasse());
		schreibeZeile(k.getStrasse());
		schreibeZeile(String.valueOf(k.getPlz()));
		schreibeZeile(k.getWohnort());
		return true;
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
