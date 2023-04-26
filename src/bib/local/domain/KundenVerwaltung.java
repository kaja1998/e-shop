//Definiert eine neue Java-Package mit dem Namen "bib.local.domain".
//Eine Package in Java ist eine Möglichkeit, Klassen logisch zu organisieren und zu strukturieren.
package bib.local.domain;

//Dieses Code-Snippet importiert die Klasse Kunde aus dem Package bib.local.entities.
//Nachdem der Import erfolgt ist, kann man Objekte der Klasse Kunde in der aktuellen Klasse erzeugen und auf deren Methoden und Eigenschaften zugreifen.
//Ohne den Import müsste man jedes Mal den vollständigen Klassennamen angeben, um die Klasse zu verwenden.
import bib.local.entities.Kunde;

//Importiert die Klasse ArrayList aus dem Paket java.util.
//Durch das Importieren dieser Klasse können Instanzen von ArrayList erstellt und alle Methoden und Eigenschaften dieser Klasse verwendet werden,
//ohne den vollständigen Klassennamen jedes Mal schreiben zu müssen.
import java.util.ArrayList;

public class KundenVerwaltung {

        //Es wird eine private Instanzvariable (Variablen des Objekts) namens kunden deklariert. Die Instanzvariable ist vom Typ ArrayList<Kunde>.
        // Das bedeutet, dass sie eine ArrayList von Kunde-Objekten enthält. Der Liste können Elemente hinzugefügt oder entfernt werden.
        // Die Variable ist private und ist somit nur innerhalb der Klasse sichtbar.
        private ArrayList<Kunde> kunden;

        // Fügt Kundenobjekt zur Liste hinzu
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
