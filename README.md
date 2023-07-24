# eShop
Dieser eShop wurde im Rahmen der Vorlesung "Programmierung 2" an der Hochschule Bremen erstellt.
Über das Shop-System können Kunden vorhandene Artikel kaufen und weitere Aktionen durchführen. Außerdem können Mitarbeiter den Shop mit neuen Artikel befüllen, diese anpassen und mehr.

## Organisatorisches
Zu dem Projekt wird es insgesamt vier Übungsblätter geben, die abhängig vom Fortschreiten der Vorlesung ausgegeben werden und der Steuerung Ihrer Entwicklungsaktivitäten dienen sollen.
Die ersten drei Übungsblätter orientieren sich an der 3-Schichten-Architektur,
die in der folgenden Abbildung durch eine vierte vertikale „Schicht“ für die auf allen übrigen Schichten erforderlichen Datenstrukturen ergänzt wird:

<img src="img%2Fimg-1.png" height="300">

Ein viertes Übungsblatt ergänzt schließlich noch den Datenaustausch über ein Netzwerk.

Die Inhalte der einzelnen Übungsblätter sind jeweils als einzelne [Meilensteine](https://github.com/kaja1998 "Meilensteine") in GitHub angelegt.

## Ausführung

### Ausführung der „ohne Server“-Version:
1. Das Projekt einmal unter run starten (GUI oder CUI). Die Klasse in dem die Main für die GUI drin ist heißt „Main“. Die Klasse in dem die Main für die CUI drin ist heißt “EshopClientCUI“.

### Ausführung der Client/Server-Version:
1. Im Server-Modul einmal den Server starten. Die Klasse in dem die Main drin ist heißt „EshopServer“.
2. Danach den Client starten (GUI oder CUI). Die Klasse in dem die Main für die GUI drin ist heißt „Main“. Die Klasse in dem die Main für die CUI drin ist heißt “EshopClientCUI“.

## Installation IntelliJ (Nur Relevant bei der Client/Server-Version)
Das Projekt ist ein IntelliJ-Projekt, das aus drei Modulen besteht:
Client, Common und Server
Damit die Module "Client" und "Server" in IntelliJ richtig übersetzt werden können, müssen sie Zugriff auf die Klassen und Interfaces im Modul "Common" erhalten.
Dies sollte in den Configs eigentlich schon voreingestellt sein aber sollte es dennoch zu Problemen kommen schauen Sie in so einem Fall unter
Menü - File - Project Structure – Modules – Dependencies nach und geben sie den beiden Modules die benötigten Zugriffe.