/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart.beans.ui;

import cart.util.BookException;
import demo.CartBeanRemote;
import ejb.beans.Books;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ALaa
 */
@Named(value = "cartBeanUI")
@SessionScoped
public class CartBeanUI implements Serializable {

    @EJB
    private CartBeanRemote cartBeanRemote;

    private String name;
    private String code;
    private String title;

    public CartBeanUI() {
        try {
            cartBeanRemote = (CartBeanRemote) InitialContext.doLookup("java:global/CartApp1/CartApp1-ejb/CartBean");

        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getContents() {
        List<String> list = new ArrayList<>();
        List<Books> books = cartBeanRemote.getContents();
        if (books != null) {
            for (int i = 0; i < books.size(); i++) {
                list.add(books.get(i).getTitle());
            }
        }

        return list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String register() {
        try {
            cartBeanRemote.initialize(name);

        } catch (BookException ex) {
            ex.printStackTrace();
        }
        return "login";
    }

    public String login() {
        try {
            cartBeanRemote.initialize(name, code);
        } catch (BookException ex) {
            ex.printStackTrace();
        }
        return "cart";
    }

    public void addBook() {
        cartBeanRemote.addBook(title);
    }

    public void removeBook() {
        try {
            cartBeanRemote.removeBook(title);
        } catch (BookException ex) {
            ex.printStackTrace();
        }
    }

    public void logout() {
        cartBeanRemote.remove();
    }
}
