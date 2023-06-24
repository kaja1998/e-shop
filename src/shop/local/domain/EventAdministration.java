package shop.local.domain;
import shop.local.entities.Event;
import shop.local.persistence.FilePersistenceManager;
import shop.local.persistence.PersistenceManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventAdministration {
    private List<Event> events = new ArrayList<>();

    //    public EventAdministration (){
    //    this.events = new ArrayList<>();
    //    }

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
}