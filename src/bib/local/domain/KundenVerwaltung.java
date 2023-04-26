package bib.local.domain;

import bib.local.entities.Kunde;
import java.util.ArrayList;

public class KundenVerwaltung {

        private ArrayList<Kunde> kunden;

        public ArrayList<Kunde> getKunden() {
                return kunden;
        }

        public void setKunden(ArrayList<Kunde> kunden) {
                this.kunden = kunden;
        }

        public void fuegeKundeHinzu(Kunde kunde) {
                this.kunden.add(kunde);
        }
}
