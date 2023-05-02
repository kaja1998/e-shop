package bib.local.entities;

/**
 * Klasse zur Repräsentation einzelner Mitarbeiter.
 *
 * Die Klasse wird derzeit noch nicht verwendet, weil die Shop-
 * Anwendung bislang nur Artikel verwaltet.
 *
 * @author Sund
 */

    public class Mitarbeiter implements User {

    private int id;
    private String name;
    private String nachname;

    public Mitarbeiter(int mNr, String mName, String mNachname) {
        id = mNr;
        this.name = mName;
        this.nachname = mNachname;
    }

    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften,
    // z.B. getStrasse() und setStrasse()

    public int getmitarbeiterNr() { return id; }

    public String getMitarbeiterName() {
        return name;
    }

    public void setMitarbeiterName(String mName) { this.name = mName; }

    public String getMitarbeiterNachname() { return nachname; }

    public void setMitarbeiterNachname(String mNachname) { this.nachname = mNachname; }

}
