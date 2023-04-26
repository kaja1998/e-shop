package bib.local.domain;

import bib.local.entities.Kunde;
import bib.local.persistence.FilePersistenceManager;
import bib.local.persistence.PersistenceManager;

import java.io.IOException;
import java.util.ArrayList;

public class KundenVerwaltung {

        private ArrayList<Kunde> kunden = new ArrayList<>();

        // Persistenz-Schnittstelle, die für die Details des Dateizugriffs verantwortlich ist
        private PersistenceManager pm = new FilePersistenceManager();



        public void fuegeKundeHinzu(Kunde kunde) {
                this.kunden.add(kunde);
        }

        /**
         * Methode zum Einlesen von Artikeldaten aus einer Datei.
         *
         * @param datei Datei, die einzulesenden Artikelbestand enthält
         * @throws IOException
         */
        public void liesDaten(String datei) throws IOException {
                // PersistenzManager für Lesevorgänge öffnen
                pm.openForReading(datei);

                Kunde kunde;
                do {
                        // Kunden-Objekt einlesen
                        kunde = pm.ladeKunde();
                        if (kunde != null) {
                                // Artikel in Liste einfügen
                                fuegeKundeHinzu(kunde);
                        }
                } while (kunde != null);

                // Persistenz-Schnittstelle wieder schließen
                pm.close();
        }

        public ArrayList<Kunde> getKunden() {
                return kunden;
        }

        public void setKunden(ArrayList<Kunde> kunden) {
                this.kunden = kunden;
        }
}
