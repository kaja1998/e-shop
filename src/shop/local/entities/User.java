package shop.local.entities;

public interface User {

        String name = null;
        String lastName = null;
        String username = null;
        String password = null;

        default String getName() {	return name;	}

        void setName(String name);

        default String getLastName() {	return lastName; }

        void setLastName(String lastName);

        default String getUsername() { return username; }

        void setUsername(String username);

        default String getPassword() { return password; }

        void setPassword(String password);

}
