package bib.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import bib.local.domain.exceptions.ArtikelExistiertBereitsException;
import bib.local.domain.Shop;
import bib.local.entities.ArtikelListe;
import bib.local.entities.Kunde;


/**
 * Klasse für sehr einfache Benutzungsschnittstelle für die Bibliothek.
 * Die Benutzungsschnittstelle basiert auf Ein- und Ausgaben auf der Kommandozeile,
 * daher der Name CUI (Command line User Interface).
 * 
 * @author Sund
 * @version 1 (Verwaltung der Artikel in verketteter Liste)
 */
public class EshopClientCUI {

	private Shop eshop;
	private BufferedReader in;
	Scanner scanner = new Scanner(System.in);							// Scanner Registrierung
	
	public EshopClientCUI(String datei) throws IOException {
		// die Bib-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		eshop = new Shop(datei);

		// Stream-Objekt für Texteingabe über Konsolenfenster erzeugen
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	private void abfrageKundeOderMitarbeiter() {
		System.out.print("Was möchtest du tun?");
		System.out.print("         \n  Ich möchte mich als Kunde einloggen: 'kl'");
		System.out.print("         \n  Ich möchte mich als Kunde registrieren: 'kr'");
		System.out.print("         \n  Ich möchte mich als Mitarbeiter einloggen: 'm'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Beenden:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	private void registriereKunde() {
		//Die Daten aus der Datei werden ausgelesen und in die ArrayList Kunden hinzugefügt
		System.out.println("Ihr Vorname: ");
		String kName = scanner.nextLine();
		System.out.println("Ihr Nachname: ");
		String kNachname = scanner.nextLine();
		System.out.println("Ihre Straße: ");
		String strasse = scanner.nextLine();
		System.out.println("Ihre PLZ: ");
		int plz = Integer.parseInt(scanner.nextLine());
		System.out.println("Ihr Wohnort: ");
		String wohnort = scanner.nextLine();
		System.out.println("Ihre E-Mail: ");
		String kEmail = scanner.nextLine();
		System.out.println("Ihr Benutzername: ");
		String kBenutzername = scanner.nextLine();
		System.out.println("Ihr Passwort: ");
		String kPasswort = scanner.nextLine();
		System.out.println("Jetzt registrieren 'ja' / 'nein': ");
		String registrierungDurchfuehren = scanner.nextLine();

		//Prüfe ob Registrierung durchführen will
		if (registrierungDurchfuehren.equals("ja")) {  //Wenn man Strings auf Gleichheit überprüfen möchten, sollten man den Operator "==" nicht verwenden. Der Operator "==" prüft, ob die beiden Variablen dieselbe Referenz auf dasselbe Objekt haben, was bei Strings oft nicht der Fall ist. Stattdessen sollte man die equals()-Methode verwenden, um Strings auf Gleichheit zu prüfen.
			//Erstelle Variable vom Typ Kunde und übergebe die Eingaben des Kunden an den Konstruktor
			Random random = new Random(System.currentTimeMillis());
			int kundennummer = random.nextInt(1, 10000);
			Kunde kunde = new Kunde(kName, kNachname, strasse, plz, wohnort, kEmail, kBenutzername, kPasswort);
			kunde.setKundenNr(kundennummer);
			boolean kundeExistiertBereits = false;

			//Prüfen, ob User schon existiert.
			//Als Erstes hole ich mir die Liste aller Kunden aus dem Shop und speichere sie in einer Instanzvariable namens Kundenliste vom Typ ArrayList<Kunde>, die ich frei in dieser (EshopClientCUI) benutzen kann.
			ArrayList<Kunde> kundenliste = eshop.getKunden();
			//Dann gehe ich mit einer for-Loop durch die Liste aller Kunden durch.
			//Die Schleife durchläuft jedes Element in der kundenliste und weist es der Variable k zu
			for (Kunde k : kundenliste) {
				//In dem Body der Schleife wird dann jedes Kunde-Objekt k mit dem kunde-Objekt verglichen.
				//Der Ausdruck kunde.equals(k) führt eine Gleichheitsprüfung zwischen kunde und k durch
				//und gibt true zurück, wenn die beiden Objekte gleich sind.
				if (kunde.equals(k)) {
					// wenn es den Kunden schon gibt, System.out.println("User mit gleichem Namen existiert bereits.");
					System.out.println("User mit gleichem Namen existiert bereits.");
					kundeExistiertBereits = true;
				}
			}
			if(!kundeExistiertBereits) {
				//Wenn kein Kunde gefunden wird, dann kann der Kunde registriert werden.
				//Kunde wird zur Liste hinzugefügt, indem das Shop-Objekt die Methode in der Klasse KundenVerwaltung aufruft
				try {
					eshop.schreibeDaten("ESHOP_K.txt", kunde);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				eshop.fuegeKundeHinzu(kunde);
				System.out.println("Registrierung erfolgreich.");
				System.out.println("Für Login 'L'");
				System.out.println("Für zurück: 'Z'");
				System.out.println("> ");
				String nextDo = scanner.nextLine();
				/*switch (nextDo) {
					case "L":
						eshop.loginKunde();
						break;
					case "Z":
						gibMitarbeiterMenueAus();
						break;
				}*/
			}
		} else {
			gibMitarbeiterMenueAus();
		}
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Ausgabe des Menüs.
	 */
	private void gibMitarbeiterMenueAus() {
		System.out.print("Befehle: \n  Artikel ausgeben:  'a'");		// \n ist ein Absatz
		System.out.print("         \n  Kunden ausgeben:  'b'");  		//FALSCH - Kaja
		System.out.print("         \n  Artikel löschen: 'd'");
		System.out.print("         \n  Artikel einfügen: 'e'");
		System.out.print("         \n  Artikel suchen:  'f'");
		System.out.print("         \n  Daten sichern:  's'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Beenden:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zum Einlesen von Benutzereingaben.
	 */
	private String liesEingabe() throws IOException {
		// einlesen von Konsole
		return in.readLine();
	}

	/* (non-Javadoc)
	 *
	 * Interne (private) Methode zur Verarbeitung von Eingaben
	 * und Ausgabe von Ergebnissen.
	 */
	private void verarbeiteEingabeEinstiegsMenue(String line) throws IOException {
		String nummer;
		int nr;

		// Eingabe bearbeiten:
		switch (line) {
			case "kr":
				registriereKunde();
				break;
			case "kl":
				System.out.println("Wird noch implementiert.");
				break;
			case "m":
				System.out.println("Wird noch implementiert.");
				break;
			// TODO implementieren
			case "q":
				eshop.schreibeArtikel();
		}
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Verarbeitung von Eingaben
	 * und Ausgabe von Ergebnissen.
	 */
	private void verarbeiteEingabeMitarbeiterMenue(String line) throws IOException {
		String nummer;
		int nr;
		String artikelbezeichnung;
		ArtikelListe artikelListe;
		ArrayList kundenliste;							//FALSCH - Kaja
		
		// Eingabe bearbeiten:
		switch (line) {
			case "a":
				artikelListe = eshop.gibAlleArtikel();		//eshop ist ein Objekt der Klasse Shop
				gibArtikellisteAus(artikelListe);
				break;
			case "b":										//FALSCH - Kaja
				kundenliste = eshop.getKunden();
				gibKundenlisteAus(kundenliste);
				break;
			case "d":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Artikelnummer > ");
				nummer = liesEingabe();
				nr = Integer.parseInt(nummer);
				System.out.print("Artikelbezeichnung  > ");
				artikelbezeichnung = liesEingabe();
				eshop.loescheArtikel(artikelbezeichnung, nr);
				break;
			case "e":
				// lies die notwendigen Parameter, einzeln pro Zeile
				System.out.print("Artikelnummer > ");
				nummer = liesEingabe();
				nr = Integer.parseInt(nummer);
				System.out.print("Artikelbezeichnung  > ");
				artikelbezeichnung = liesEingabe();

				try {
					eshop.fuegeArtikelEin(artikelbezeichnung, nr);
					System.out.println("Einfügen ok");
				} catch (ArtikelExistiertBereitsException e) {
					// Hier Fehlerbehandlung...
					System.out.println("Fehler beim Einfügen");
					e.printStackTrace();
				}
				break;
			case "f":
				System.out.print("Artikelbezeichnung  > ");
				artikelbezeichnung = liesEingabe();
				artikelListe = eshop.sucheNachArtikelbezeichnung(artikelbezeichnung);
				gibArtikellisteAus(artikelListe);
				break;
			case "s":
				eshop.schreibeArtikel();
		}
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zum Ausgeben von Artikellisten.
	 *
	 */
	private void gibArtikellisteAus(ArtikelListe liste) {
		// Einfach nur Aufruf der toString()-Methode von ArtikelListe
		System.out.print(liste);
	}

	private void gibKundenlisteAus(ArrayList kListe) {								//Neu Kaja
		// Einfach nur Aufruf der toString()-Methode von ArrayListe
		System.out.print(kListe);
	}

	/**
	 * Methode zur Ausführung der Hauptschleife:
	 * - Menü ausgeben
	 * - Eingabe des Benutzers einlesen
	 * - Eingabe verarbeiten und Ergebnis ausgeben
	 * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
	 */
	public void run() throws IOException {
		// Variable für Eingaben von der Konsole
		String input = "";

		// Abfrage, ob Kunde oder Mitarbeiter
		abfrageKundeOderMitarbeiter();
		input = liesEingabe();
		verarbeiteEingabeEinstiegsMenue(input);
	
		// Hauptschleife der Benutzungsschnittstelle
		do {
			gibMitarbeiterMenueAus();
			try {
				input = liesEingabe();
				verarbeiteEingabeMitarbeiterMenue(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!input.equals("q"));
	}

	
	/**
	 * Die main-Methode...
	 */
	public static void main(String[] args) {
		//Variable vom Typ "EshopClientCUI" wird deklariert, aber noch nicht initialisiert!
		EshopClientCUI cui;
		try {
			//Ein neues Objekt von "EshopClientCUI" wird erzeugt. Dabei wird die Datei und der String "ESHOP" als Parameter übergeben oder es wird nur die Datei namens "ESHOP" übergeben
			cui = new EshopClientCUI("ESHOP");
			//Die "run"-Methode wird mit dem "cui"-Objekt aufgerufen, um das Programm auszuführen
			cui.run();
		//Wenn währenddessen ein Fehler auftritt, wird eine "IOException" geworfen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Fehlermeldung "e.printStackTrace()" wird ausgegeben
			e.printStackTrace();
		}
	}
}
