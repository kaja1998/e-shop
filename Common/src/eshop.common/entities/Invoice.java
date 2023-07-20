package eshop.common.entities;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to represent invoices.
 * @author Sund
 */

public class Invoice {

        private ArrayList<ShoppingCartItem> positions;
        private List<ShoppingCartItem> unavailableItems;
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

        public List<ShoppingCartItem> getUnavailableItems() {
                return unavailableItems;
        }


        public double getTotal() {
                return total;
        }

        public String getFormattedDate() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                return dateFormat.format(date);
        }

        public void addPosition(ShoppingCartItem item) {
                positions.add(item);
                total += item.getQuantity() * item.getArticle().getPrice();
        }

        public void addUnavailableItems(ShoppingCartItem item) {
                unavailableItems.add(item);
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
