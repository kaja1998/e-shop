//Definiert eine neue Java-Package mit dem Namen "bib.local.domain".
//Eine Package in Java ist eine Möglichkeit, Klassen logisch zu organisieren und zu strukturieren.
package bib.local.domain;

//Dieses Code-Snippet importiert die Klasse Kunde aus dem Package bib.local.entities.
//Nachdem der Import erfolgt ist, kann man Objekte der Klasse Kunde in der aktuellen Klasse erzeugen und auf deren Methoden und Eigenschaften zugreifen.
//Ohne den Import müsste man jedes Mal den vollständigen Klassennamen angeben, um die Klasse zu verwenden.
import bib.local.entities.Artikel;
import bib.local.entities.ArtikelListe;
import bib.local.entities.Kunde;
import bib.local.persistence.FilePersistenceManager;
import bib.local.persistence.PersistenceManager;
import java.io.IOException;
//Importiert die Klasse ArrayList aus dem Paket java.util.
//Durch das Importieren dieser Klasse können Instanzen von ArrayList erstellt und alle Methoden und Eigenschaften dieser Klasse verwendet werden,
//ohne den vollständigen Klassennamen jedes Mal schreiben zu müssen.
import java.util.ArrayList;

public class KundenVerwaltung {

        //Es wird eine private Instanzvariable (Variablen des Objekts) namens kunden deklariert. Die Instanzvariable ist vom Typ ArrayList<Kunde>.
        // Das bedeutet, dass sie eine ArrayList von Kunde-Objekten enthält. Der Liste können Elemente hinzugefügt oder entfernt werden.
        // Die Variable ist private und ist somit nur innerhalb der Klasse sichtbar.
        private ArrayList<Kunde> kunden = new ArrayList<>();

        // Persistenz-Schnittstelle, die für die Details des Dateizugriffs verantwortlich ist
        private PersistenceManager pm = new FilePersistenceManager();

        /**
         * Die Methode liesDaten liest Kunden-Daten aus einer Datei mit dem angegebenen Dateinamen.
         *
         * @param datei Datei, die einzulesenden Artikelbestand enthält
         * @throws IOException
         */
        public void liesDaten(String datei) throws IOException {
                //PersistenceManager-Objekt öffnet den PersistenzManager für lesevorgänge mit der Methode openForReading.
                pm.openForReadingK(datei);

                Kunde kunde;
                do {
                        //Kunden-Objekt einlesen.
                        //Ruft in einer Schleife die Methode ladeKunde des PersistenzManagers auf, um jeweils einen Kunden aus der Datei zu lesen
                        kunde = pm.ladeKunde();
                        if (kunde != null) {
                                //Falls ein Kunde erfolgreich eingelesen werden konnte, wird dieser mit der Methode fuegeKundeHinzu der Kunden-Liste hinzugefügt.
                                //kunden.add(kunde);
                                fuegeKundeHinzu(kunde);
                        }
                //Die Schleife läuft so lange, bis die Methode ladeKunde null zurückgibt, was darauf hinweist, dass keine weiteren Daten mehr in der Datei vorhanden sind.
                } while (kunde != null);

                //Persistenz-Schnittstelle wird wieder geschlossen
                pm.close();
        }

        public void schreibeDaten(String datei, Kunde kunde) throws IOException  {
                // PersistenzManager für Schreibvorgänge öffnen
                pm.openForWriting(datei);
                pm.speichereKunde(kunde);
                // Persistenz-Schnittstelle wieder schließen
                pm.close();
        }

        //Fügt Kundenobjekte aus der Datei zur (Array)Liste hinzu
        public void fuegeKundeHinzu(Kunde kunde) {
                this.kunden.add(kunde);
        }

        // Getter und Setter
        public ArrayList<Kunde> getKunden() {
                return kunden;
        }

        public void setKunden(ArrayList<Kunde> kunden) {
                this.kunden = kunden;
        }
}
