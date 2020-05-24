/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import cart.util.BookException;
import ejb.beans.Books;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ALaa
 */
@Remote
public interface CartBeanRemote {
    public boolean initialize(String person) throws BookException;
    public boolean initialize(String person, String id) throws BookException;
    public void addBook(String title);
    public void removeBook(String title) throws BookException;
    public List<Books> getContents();
    public void remove();
}
