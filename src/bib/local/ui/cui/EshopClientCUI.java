package bib.local.ui.cui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bib.local.domain.exceptions.ArtikelExistiertBereitsException;
import bib.local.domain.Shop;
import bib.local.entities.ArtikelListe;


/**
 * Klasse für sehr einfache Benutzungsschnittstelle für die Bibliothek.
 * Die Benutzungsschnittstelle basiert auf Ein- und Ausgaben auf der Kommandozeile,
 * daher der Name CUI (Command line User Interface).
 * 
 * @author teschke
 * @version 1 (Verwaltung der Artikel in verketteter Liste)
 */
public class EshopClientCUI {

	private Shop eshop;
	private BufferedReader in;
	
	public EshopClientCUI(String datei) throws IOException {
		// die Bib-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		eshop = new Shop(datei);

		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		in = new BufferedReader(new InputStreamReader(System.in));
	}

	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Ausgabe des Menüs.
	 */
	private void gibMenueAus() {
		System.out.print("Befehle: \n  Artikel ausgeben:  'a'");
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
	private void verarbeiteEingabe(String line) throws IOException {
		String nummer;
		int nr;
		String titel;
		ArtikelListe ArtikelListe;
		
		// Eingabe bearbeiten:
		switch (line) {
		case "a":
			ArtikelListe = eshop.gibAlleArtikel();
			gibArtikellisteAus(ArtikelListe);
			break;
		case "d":
			// lies die notwendigen Parameter, einzeln pro Zeile
			System.out.print("Artikelnummer > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			System.out.print("Artikeltitel  > ");
			titel = liesEingabe();
			eshop.loescheArtikel(titel, nr);
			break;
		case "e":
			// lies die notwendigen Parameter, einzeln pro Zeile
			System.out.print("Artikelnummer > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			System.out.print("Artikeltitel  > ");
			titel = liesEingabe();

			try {
				eshop.fuegeArtikelEin(titel, nr);
				System.out.println("Einfügen ok");
			} catch (ArtikelExistiertBereitsException e) {
				// Hier Fehlerbehandlung...
				System.out.println("Fehler beim Einfügen");
				e.printStackTrace();
			}
			break;
		case "f":
			System.out.print("Artikeltitel  > ");
			titel = liesEingabe();
			ArtikelListe = eshop.sucheNachTitel(titel);
			gibArtikellisteAus(ArtikelListe);
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

	/**
	 * Methode zur Ausführung der Hauptschleife:
	 * - Menü ausgeben
	 * - Eingabe des Benutzers einlesen
	 * - Eingabe verarbeiten und Ergebnis ausgeben
	 * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
	 */
	public void run() {
		// Variable für Eingaben von der Konsole
		String input = ""; 
	
		// Hauptschleife der Benutzungsschnittstelle
		do {
			gibMenueAus();
			try {
				input = liesEingabe();
				verarbeiteEingabe(input);
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
		EshopClientCUI cui;
		try {
			cui = new EshopClientCUI("BIB");
			cui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
