package shop.local.entities;

public abstract class User {

        private String name = null;
        private String lastName = null;
        private String userName = null;
        private String password = null;

        public User (String name, String lastName, String userName, String password) {
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


}
