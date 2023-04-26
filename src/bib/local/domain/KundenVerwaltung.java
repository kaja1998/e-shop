package bib.local.domain;

import bib.local.entities.Kunde;
import java.util.ArrayList;

public class KundenVerwaltung {

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
