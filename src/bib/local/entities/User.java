package bib.local.entities;

public interface User {

        String name = null;
        String nachname = null;
        String benutzername = null;
        String passwort = null;

        default String getName() {	return name;	}

        void setName(String name);

        default String getNachname() {	return nachname; }

        void setNachname(String nachname);

        default String getBenutzername() { return benutzername; }

        void setBenutzername(String benutzername);

        default String getPasswort() { return passwort; }

        void setPasswort(String passwort);

}
