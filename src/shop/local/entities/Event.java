package shop.local.entities;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    private Date date;
    private Article article;
    private int quantity;
    private User user;

    public Event(Article article, int quantity, User user) {
        this.date = new Date();
        this.article = article;
        this.quantity = quantity;
        this.user = user;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
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
       return "Date: " + getFormattedDate() + "\n" + "Article: " + getArticle() + "\n" +  "New quantity: " + getQuantity()  + "\n" + "User " + getUser()  + "\n" + "-----------------------------";
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
