package shop.local.domain;
import shop.local.entities.Event;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class for event administration
 * @author Sund
 */

public class EventAdministration {
    private List<Event> events;

    public EventAdministration (){
        this.events = new ArrayList<>();
    }

    // Persistence interface responsible for file access details
    private PersistenceManager persistenceManager = new FilePersistenceManager();

    public void addEvent(Event event){
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

    public List<Event> getEventsbyArticleOfLast30Days(int articleID){
        List<Event> filteredEvents = new ArrayList<Event>();

    	if(!events.isEmpty()) 
    	{
    		
    	}
        
        // Durchlaufe alle Ereignisse
        for (Event event : events) {
            // Überprüfe, ob das Ereignis zum gewünschten Artikel gehört
            if (event.getArticle().getNumber() == articleID) {
                // Konvertiere das Datum des Ereignisses in ein LocalDate-Objekt
                Calendar cal = Calendar.getInstance();
                cal.setTime(event.getDate());
                LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));

                // Berechne die Anzahl der Tage zwischen dem Ereignisdatum und dem aktuellen Datum
                long tageDifferenz = ChronoUnit.DAYS.between(localDate, LocalDate.now());

                // Überprüfe, ob das Ereignis innerhalb der letzten 30 Tage liegt
                if (tageDifferenz <= 30) {
                    filteredEvents.add(event);
                }
            }
        }

        // Sortiere die Ereignisse nach dem Datum
        getDatesSorted(filteredEvents);

        // Berechne die Artikelbestände für die gefilterten Ereignisse
        List<Event> calculatedEvents = calculateArticleStocks(filteredEvents);

        // Gib die berechneten Ereignisse zurück
        return calculatedEvents;
    }

    private void getDatesSorted(List<Event> listOfEvents) {
        // Die Liste der Ereignisse nach dem Datum sortieren

        // Verwenden der Collections.sort-Methode, um die Liste listOfEvents zu sortieren.
        // Wir verwenden einen Comparator, um die Vergleichslogik für die Sortierung anzugeben.
        Collections.sort(listOfEvents, new Comparator<Event>() {
            @Override
            public int compare(Event event1, Event event2) {
                // Vergleiche die Datumswerte der beiden Ereignisse
                // Verwende die compareTo-Methode des Date-Objekts, um die Reihenfolge festzulegen
                return event1.getDate().compareTo(event2.getDate());
            }
        });
    }

    /*Method that creates a list totaling the item's stock quantity for each date
    * Doesn't work as it's supposed to. TODO: Think about the logic again
    * */
    private List<Event> calculateArticleStocks(List<Event> filteredEvents) {
        List<Event> calculatedEvents = new ArrayList<>();

        // Eine Map zum Speichern der Artikelbestände für jedes Datum
        Map<Date, Integer> articleStocks = new HashMap<>();

        // Schleife über die gefilterten Ereignisse
        for (Event event : filteredEvents) {
            Date eventDate = event.getDate();

            // Den aktuellen Bestand für das Datum aus der Map abrufen oder 0 als Standardwert verwenden
            int currentStock = articleStocks.getOrDefault(eventDate, 0);

            // Den neuen Bestand berechnen, indem die QuantityInStock des Artikels zum aktuellen Bestand addiert wird
            int newStock = currentStock + event.getArticle().getQuantityInStock();

            // Den neuen Bestand für das Datum in der Map speichern
            articleStocks.put(eventDate, newStock);
        }

        // Schleife über die Einträge in der Map, um Ereignisse mit den berechneten Beständen zu erstellen
        for (Map.Entry<Date, Integer> entry : articleStocks.entrySet()) {
            Date eventDate = entry.getKey();
            int stock = entry.getValue();

            // Ein Ereignisobjekt mit dem Datum und Bestand erstellen und zur Ergebnisliste hinzufügen
            Event event = new Event(eventDate, stock);
            calculatedEvents.add(event);
        }

        // Die Liste der berechneten Ereignisse zurückgeben
        return calculatedEvents;
    }

}