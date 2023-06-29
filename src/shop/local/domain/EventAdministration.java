package shop.local.domain;
import shop.local.entities.Article;
import shop.local.entities.Event;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class for event administration
 * @author Sund
 */

public class EventAdministration {
    private List<Event> events;

    public EventAdministration() {
        this.events = new ArrayList<>();
    }

    // Persistence interface responsible for file access details
    private PersistenceManager persistenceManager = new FilePersistenceManager();

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }


    public void readData(String file, ArticleAdministration articleAdministration, EmployeeAdministration employeeAdministration, CustomerAdministration customerAdministration) throws IOException {
        //PersistenceManager object opens the PersistenceManager for reading using the openForReading method.
        persistenceManager.openForReading(file);
        Event event;

        do {
            //Read employee object
            //Calls the loadEmployee method of the PersistenceManager in a loop to read one employee at a time from the file
            event = persistenceManager.loadEvent(articleAdministration, employeeAdministration, customerAdministration);
            if (event != null) {
                //If an event could be read in successfully, this is added to the events list
                addEvent(event);
            }
            //The loop runs until the loadEvent method returns null, indicating that there is no more data in the file.
        } while (event != null);

        //Persistence interface is closed again
        persistenceManager.close();
    }

    public void writeData(String file) throws IOException {
        // Open persistence manager for writes
        persistenceManager.openForWriting(file);
        persistenceManager.saveEvent(this.events);

        // Close the persistence interface again
        persistenceManager.close();
    }

    public Map<Date, Integer> getEventsbyArticleOfLast30Days(Article article) {
        List<Event> filteredEvents = new ArrayList<Event>();

        // Durchlaufe alle Ereignisse
        for (Event event : events) {
            // Überprüfe, ob das Ereignis zum gewünschten Artikel gehört
            if (event.getArticle().getNumber() == article.getNumber()) {
                // Konvertiere das Datum des Ereignisses in ein LocalDate-Objekt

                Instant instant = event.getDate().toInstant();
                ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                LocalDate date = zdt.toLocalDate();

                // Berechne die Anzahl der Tage zwischen dem Ereignisdatum und dem aktuellen Datum
                long tageDifferenz = ChronoUnit.DAYS.between(date, LocalDate.now());

                // Überprüfe, ob das Ereignis innerhalb der letzten 30 Tage liegt
                if (tageDifferenz <= 30) {
                    filteredEvents.add(event);
                }
            }
        }

        // Sortiere die Ereignisse nach dem Datum
        List<Event> sortedEvents = getDatesSorted(filteredEvents);

        // Berechne die Artikelbestände für die gefilterten Ereignisse
        Map<Date, Integer> stringIntegerHashMap = calculateArticleStocks(article, sortedEvents);

        // Gib die berechneten Ereignisse zurück
        return stringIntegerHashMap;
    }

    private List<Event> getDatesSorted(List<Event> filteredEvents) {
        // Die Liste der Ereignisse nach dem Datum sortieren

        // Verwenden der Collections.sort-Methode, um die Liste listOfEvents zu sortieren.
        // Wir verwenden einen Comparator, um die Vergleichslogik für die Sortierung anzugeben.
        Collections.sort(filteredEvents, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2) {
                // Vergleiche die Datumswerte der beiden Ereignisse
                // Verwende die compareTo-Methode des Date-Objekts, um die Reihenfolge festzulegen
                return event2.getDate().compareTo(event1.getDate());
            }
        });
        return filteredEvents;
        //Collections.sort(stringIntegerHashMap, (event1, event2) -> event1.getDate().compareTo(event2.getDate()));
    }

    /*Method that creates a list totaling the item's stock quantity for each date
     * */
    private Map<Date, Integer> calculateArticleStocks(Article article, List<Event> filteredEvents) {
        List<Event> calculatedEvents = new ArrayList<>();

        Map<Date, List<Event>> groupedEvents = new TreeMap<Date, List<Event>>();

        // Schleife über die filteredEvents
        for (Event event : filteredEvents) {
            //String dateString = event.getDate().getDate() + "." + event.getDate().getMonth() + "." + event.getDate().getYear();
            //Wir suchen in der Hashmap nach einem datum
            List<Event> foundEvents = groupedEvents.get(event.getDate());
            //wenn es das Datum gibt, dann wird der Key (Datum) zu dem Value (den List<Events>) hinzugefügt
            if (foundEvents != null) {
                groupedEvents.get(event.getDate()).add(event);
                //Wenn es das Datum in der Hashmap noch nicht gibt, dann wird ein neuer KeyValue erstellt (Key + Value-Paar wird erstellt)
            } else {
                List<Event> e = new ArrayList<>();
                e.add(event);
                //dateString = key, e = value
                groupedEvents.put(event.getDate(), e);
            }
        }

        //
        Map<Date, Integer> summedStockQuantity = new TreeMap<Date, Integer>();
        //Schleife über die HashMap Keys von groupedEvents (die Keys sind die Datums)
        for (Date i : groupedEvents.keySet()) {
            //In einer Liste werden die Events zu dem Datum gespeichert
            //wenn ich mit get in den Key reingehe, dann bekomme ich den Value zurück
            List<Event> foundEvents = groupedEvents.get(i);
            int summedValues = 0;
            for (Event event : foundEvents) {
                //Summiere die quantities, die verändert wurden (-10, 50 etc.) auf
                summedValues += event.getQuantity();
            }
            //Die packe ich dann in die Hashmap summedStockQuantity
            summedStockQuantity.put(i, summedValues);
        }
        return summedStockQuantity;
    }
}
