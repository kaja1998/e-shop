package bib.local.entities;

/**
 * Klasse zur Repräsentation einzelner Mitarbeiter.
 *
 * Die Klasse wird derzeit noch nicht verwendet, weil die Shop-
 * Anwendung bislang nur Artikel verwaltet.
 *
 * @author Sund
 */

    public class Mitarbeiter {

    private int mitarbeiterNr;
    private String mName;
    private String mNachname;

    public Mitarbeiter(int mNr, String mName, String mNachname) {
        mitarbeiterNr = mNr;
        this.mName = mName;
        this.mNachname = mNachname;
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public int getmitarbeiterNr() { return mitarbeiterNr; }

    public String getMitarbeiterName() {
        return mName;
    }

    public void setMitarbeiterName(String mName) { this.mName = mName; }

    public String getMitarbeiterNachname() { return mNachname; }

    public void setMitarbeiterNachname(String mNachname) { this.mNachname = mNachname; }

}