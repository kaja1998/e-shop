# eShop
Dieser eShop wurde im Rahmen der Vorlesung "Programmierung 2" an der Hochschule Bremen erstellt.
Über das Shop-System können Kunden vorhandene Artikel kaufen und weitere Aktionen durchführen. Außerdem können Mitarbeiter den Shop mit neuen Artikeln befüllen, diese anpassen und mehr.

## Anmeldedaten
1.	Customer: Username: kajasund // Password: paskaja
2.	Customer: Username: abc // Password: 123
1.	Employee: Username: kajasund // password: Pas123
2.	Employee: Username: abc // password: 123

## Ausführung

### Ausführung der „ohne Server“-Version:
1.	Das Projekt einmal unter run starten (GUI oder CUI). Die Klasse in dem die Main für die GUI drin ist heißt „Main“. Die Klasse in dem die Main für die CUI drin ist heißt “EshopClientCUI“.
2.	Falls es zu einem Fehler kommt, eventuell mal den grünen Hammer ausführen, der das Programm neu kompiliert und die Ausgabedatei ebenfalls neu erstellt.
3.	Die Module Client, Common und Server bitte ignorieren. In diesen sind keine Klassen drin. Das Programm läuft auch so. Diese sind nur da, weil ich zwei Branches in Git für das Projekt hatte (mit und ohne Server) und die Configs oder so bestimmt übernommen wurden.

### Ausführung der Client/Server-Version:
1.	Im Server-Modul einmal den Server starten. Die Klasse in dem die Main drin ist heißt „EshopServer“.
2.	Danach den Client starten (GUI oder CUI). Die Klasse in dem die Main für die GUI drin ist heißt „Main“. Die Klasse in dem die Main für die CUI drin ist heißt “EshopClientCUI“.

## Installation IntelliJ (Nur relevant bei der Client/Server-Version)
Das Projekt ist ein IntelliJ-Projekt, das aus drei Modulen besteht:
Client, Common und Server
Damit die Module "Client" und "Server" in IntelliJ richtig übersetzt werden können, müssen sie Zugriff auf die Klassen und Interfaces im Modul "Common" erhalten. 
Dies sollte in den Configs eigentlich schon voreingestellt sein aber sollte es dennoch zu Problemen kommen schauen Sie in so einem Fall unter Menü - File - Project Structure – Modules – Dependencies nach und geben sie den beiden Modules die benötigten Zugriffe.

## Projekt
Die einzelnen [Meilensteine](https://github.com/kaja1998 "Meilensteine") des Projekts sind in GitHub zu finden.