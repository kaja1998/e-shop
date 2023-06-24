package shop.local.entities;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to represent invoices.
 * @author Sund
 */

public class Invoice {

        private ArrayList<ShoppingCartItem> positions = new ArrayList<>();
        private List<ShoppingCartItem> unavailableItems = new ArrayList<>();
        private double total;
        private Date date;

        private Customer customer;


        public Invoice() {
                this.positions = new ArrayList<>();
                this.unavailableItems = new ArrayList<>();
                this.total = 0;
                this.date = new Date();
                this.customer = null;
        }

        public ArrayList<ShoppingCartItem> getPositions() {
                return positions;
        }

//        public void setPositions(ArrayList<ShoppingCartItem> positions) {
//                this.positions = positions;
//        }

        public List<ShoppingCartItem> getUnavailableItems() {
                return unavailableItems;
        }

//        public void setUnavailableItems(List<ShoppingCartItem> unavailableItems) {
//                this.unavailableItems = unavailableItems;
//        }


        public double getTotal() {
                return total;
        }

//        public void setTotal(double total) {
//                this.total = total;
//        }
//
//        public Date getDate() {
//                return date;
//        }
//
//        public void setDate(Date date) {
//                this.date = date;
//        }

        public String getFormattedDate() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                return dateFormat.format(date);
        }

        public void addPosition(ShoppingCartItem item) {
                positions.add(item);
                total += item.getQuantity() * item.getArticle().getPrice();
        }

//        public void removePosition(ShoppingCartItem item) {
//                positions.remove(item);
//                total -= item.getQuantity() * item.getArticle().getPrice();
//        }

        public void addUnavailableItems(ShoppingCartItem item) {
                unavailableItems.add(item);
        }

//        public void removeUnavailableItems(ShoppingCartItem item) {
//                unavailableItems.remove(item);
//        }


        public Customer getCustomer() {
                return customer;
        }

        public void setCustomer(Customer customer) {
                this.customer = customer;
        }

        public String getCustomerAddress() {
                if (customer != null){
                        return customer.getName() + " " + customer.getLastName() + "\n" + customer.getStreet() + "\n" + customer.getPostalCode() + " " + customer.getCity();
                } else {
                        return "N/A";
                }
        }
}
