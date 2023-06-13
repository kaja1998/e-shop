package shop.local.entities;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Event class
 * @author Sund
 */

public class Event {

    public enum EventType { NEU, KAUF, AUSLAGERUNG, EINLAGERUNG };


    private EventType eventType;
    private Date date;
    private Article article;
    private int quantity;
    private User user;


    public Event(EventType type, Article article, int quantity, User user) {
        this.eventType = type;
        this.date = new Date();
        this.article = article;
        this.quantity = quantity;
        this.user = user;
    }

    public Event(Article article, int quantity, User user) {
        this.date = new Date();
        this.article = article;
        this.quantity = quantity;
        this.user = user;
    }

    //Konstruktor zum Lesen aus der Datei
//    public Event(int userId, int articleId, int quantity, String date /*, ArticleAdministration articleAdministration, EmployeeAdministration employeeAdministration, CustomerAdministration customerAdministration*/) {
    public Event(EventType type, User user, Article article, int quantity, String date) {
        this(type, article, quantity, user);
        this.date = getcorrectDate(date);
    }

    //Die Methode wandelt ein String im angegebenen Format in ein Date-Objekt um.
    //String date wird übergeben und versucht, in ein Date-Objekt zu parsen. Dabei wird das Datumsformat "dd.MM.yyyy HH:mm:ss" verwendet.
    private Date getcorrectDate(String date) {
        //verwendet SimpleDateFormat Klasse aus dem Java SDK, um das Parsen durchzuführen
        //neues SimpleDateFormat-Objekt wird erstellt, das das gewünschte Datumsformat angibt.
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            //parse(date) wird aufgerufen, um den übergebenen String date in ein Date-Objekt umzuwandeln.
            //Wenn das Parsen erfolgreich ist, wird das geparste Date-Objekt zurückgegeben.
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Date-Objekt wird in String umgewandelt
    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public Date getDate(){
        return this.date;
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

    @Override
    public String toString() {
       return "Typ: " + eventType.ordinal() + "\n" + "Date: " + getFormattedDate() + "\n" + "Article: " + getArticle() + "\n" +  "quantity-change: " + getQuantity()  + "\n" + "User " + getUser()  + "\n" + "-----------------------------";
    }

    public String toFileString(){
        return user.getId() + ";" + article.getNumber() + ";" + quantity + ";" + getFormattedDate() + ";" + eventType.ordinal();
    }


    public String getUser() {
        if (user instanceof Employee){
            Employee employee = (Employee) user;
            return "(employee): " + "Nr: " + employee.getId() + " / Name: " + employee.getName() + " / LastName: " + employee.getLastName() + " / Username: " + employee.getUsername();
        } else if (user instanceof Customer) {
            Customer customer = (Customer) user;
            return "(customer): " + "Nr: " + customer.getId() + " / Name: " + customer.getName() + " / LastName: " + customer.getLastName() + " / Address: " + customer.getStreet()  + " , " + customer.getPostalCode() + " , " + customer.getCity();
        }
        return null;
    }


}
