package shop.local.domain;
import shop.local.entities.Article;
import shop.local.entities.Customer;
import shop.local.entities.Employee;
import shop.local.entities.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class EventAdministration {
    private static List<EventAdministration> events = new ArrayList<>();

    private Date date;
    private Article article;
    private int quantity;
    private User user;

    String storageRetrieval;

    public EventAdministration(Article article, String storageRetrieval, int quantity, User user) {
        this.date = new Date();
        this.article = article;
        this.storageRetrieval = storageRetrieval;
        this.quantity = quantity;
        this.user = user;
        events.add(this);
    }

    public static List<EventAdministration> getEvents() {
        return events;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public String getStorageRetrieval() {
        return storageRetrieval;
    }

    public Article getArticle() {
        return article;
    }

    public int getQuantity() {
        return quantity;
    }

//    public User getUser() {
//        return user;
//    }

    public String getUser() {
        if (user instanceof Employee){
            return "(employee): " + "Nr: " + ((Employee) user).getId() + " / Name: " + ((Employee) user).getName() + " / LastName: " + ((Employee) user).getLastName() + " / Username: " + ((Employee) user).getUsername();
        } else if (user instanceof Customer) {
            return "(customer): " + "Nr: " + ((Customer) user).getId() + " / Name: " + ((Customer) user).getName() + " / LastName: " + ((Customer) user).getLastName() + " / Address: " + ((Customer) user).getStreet()  + " , " + ((Customer) user).getPostalCode() + " , " + ((Customer) user).getCity();
        }
        return null;
    }
}