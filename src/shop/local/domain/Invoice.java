package shop.local.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

        private ArrayList<ShoppingCartItem> positions = new ArrayList<>();
        private List<ShoppingCartItem> unavailableItems = new ArrayList<>();
        private double total;
        private LocalDateTime date;

        public Invoice() {
                this.positions = new ArrayList<>();
                this.unavailableItems = new ArrayList<>();
                this.total = 0;
                this.date = LocalDateTime.now();
        }

        public ArrayList<ShoppingCartItem> getPositions() {
                return positions;
        }

        public void setPositions(ArrayList<ShoppingCartItem> positions) {
                this.positions = positions;
        }

        public List<ShoppingCartItem> getUnavailableItems() {
                return unavailableItems;
        }

        public void setUnavailableItems(List<ShoppingCartItem> unavailableItems) {
                this.unavailableItems = unavailableItems;
        }


        public double getTotal() {
                return total;
        }

        public void setTotal(double total) {
                this.total = total;
        }

        public LocalDateTime getDate() {
                return date;
        }

        public void setDate(LocalDateTime date) {
                this.date = date;
        }

        public void addPosition(ShoppingCartItem item) {
                positions.add(item);
                total += item.getQuantity() * item.getArticle().getPrice();
        }

        public void removePosition(ShoppingCartItem item) {
                positions.remove(item);
                total -= item.getQuantity() * item.getArticle().getPrice();
        }

        public void addUnavailableItems(ShoppingCartItem item) {
                unavailableItems.add(item);
        }

        public void removeUnavailableItems(ShoppingCartItem item) {
                unavailableItems.remove(item);
        }

}
