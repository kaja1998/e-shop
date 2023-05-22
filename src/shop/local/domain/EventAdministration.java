package shop.local.domain;
import shop.local.entities.Article;
import shop.local.entities.User;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class EventAdministration {
    private static List<EventAdministration> events = new ArrayList<>();

    private Date date;
    private Article article;
    private int quantity;
    private User user;

    public EventAdministration(Article article, int quantity, User user) {
        this.date = new Date();
        this.article = article;
        this.quantity = quantity;
        this.user = user;
        events.add(this);
    }

    public static List<EventAdministration> getEvents() {
        return events;
    }

    public Date getDate() {
        return date;
    }

    public Article getArticle() {
        return article;
    }

    public int getQuantity() {
        return quantity;
    }

    public User getUser() {
        return user;
    }
}