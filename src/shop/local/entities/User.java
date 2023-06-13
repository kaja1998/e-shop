package shop.local.entities;
import java.util.Random;

/**
 * Class to represent users.
 * @author Sund
 */

public abstract class User {

        private String name = null;
        private String lastName = null;
        private String userName = null;
        private String password = null;
        private final int Id;

        public User (String name, String lastName, String userName, String password) {
                this.Id = new Random().nextInt(0,10000);
                this.name = name;
                this.lastName = lastName;
                this.userName = userName;
                this.password = password;
        }

        public User (int id, String name, String lastName, String userName, String password) {
                this.Id = id;
                this.name = name;
                this.lastName = lastName;
                this.userName = userName;
                this.password = password;
        }

        public String getName() {  return name;  }

        public void setName(String name) {
                this.name = name;
        }

        public String getLastName() { return lastName; }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getUsername() {
                return userName;
        }

        public void setUsername(String userName) {
                this.userName = userName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) { this.password = password;  }

        public int getId() {
                return Id;
        }
}
