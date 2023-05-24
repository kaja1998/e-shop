package shop.local.domain;
import shop.local.entities.Article;
import shop.local.entities.Event;
import shop.local.entities.User;
import java.util.ArrayList;
import java.util.List;

public class EventAdministration {
    private List<Event> events;

    public EventAdministration (){
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event){
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }
}