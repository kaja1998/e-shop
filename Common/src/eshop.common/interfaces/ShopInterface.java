package eshop.common.interfaces;

import eshop.common.entities.*;
import eshop.common.exceptions.LoginException;
import eshop.common.exceptions.RegisterException;

import java.util.ArrayList;

public interface ShopInterface {

    /**
     * Methode, die eine Liste aller im Bestand befindlichen Artikel zurückgibt.
     *
     * @return Liste aller Artikel im Bestand des Shops
     */
    public abstract ArrayList<Article> getAllArticles();

    /**
     * Methode, um einen neuen Kunden zu registrieren
     */
    public abstract String registerCustomer(String name, String lastName, String street, int postalCode, String city, String mail, String username, String password, String registerNow) throws RegisterException;

    /**
     * Methode, um einen Kunden einzuloggen
     *
     */
    public abstract Customer loginCustomer(String username, String password) throws LoginException;


    /**
     * Methode, um einen Mitarbeiter einzuloggen
     *
     */
    public abstract Employee loginEmployee(String username, String password) throws LoginException;

}
