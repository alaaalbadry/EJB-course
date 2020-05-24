/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import cart.util.BookException;
import cart.util.IdVerifier;
import ejb.beans.Books;
import ejb.beans.Customer;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ALaa
 */
@Stateful
public class CartBean implements CartBeanRemote {

    @PersistenceContext(unitName = "CartApp1-ejbPU")
    private EntityManager em;

    private Customer customer;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean initialize(String person) throws BookException {
        if (person == null) {
            throw new BookException("Null person not allowed.");

        } else {
//            customerName = person;
            Customer customer = new Customer();
            customer.setCustname(person);
            em.persist(customer);
            System.out.println("person : " + person);
            return true;
        }

    }

    @Override
    public boolean initialize(String person, String id) throws BookException {
        if (person == null) {
            throw new BookException("Null person not allowed.");
        } else {

            System.out.println("user with name");
            IdVerifier idChecker = new IdVerifier();
            if (idChecker.validate(id)) {
                System.out.println("person : " + person);
                System.out.println("Id : " + id);
                Query q = em.createNamedQuery("Customer.findByCustIdandname");
                q.setParameter("custId", Integer.parseInt(id));
                q.setParameter("custname", person);
                List<Customer> result = q.getResultList();
                if (result.isEmpty()) {
                    return false;
                } else {
                    customer = result.get(0);
                    return true;
                }
            } else {
                throw new BookException("Invalid id: " + id);
            }
        }

    }

    @Override
    public void addBook(String title) {
        System.out.println("tile : " + title);
        Books b = new Books();
        b.setTitle(title);
        customer.getBooksList().add(b);
        //em.merge(customer);
        b.setCustId(customer);
        em.persist(b);
        System.out.println("book added successfully");
    }

    @Override
    public void removeBook(String title) throws BookException {
        System.out.println("deltile : " + title);
        Query q = em.createNamedQuery("Books.findByTitle");
        q.setParameter("title", title);
        List result = q.getResultList();
        if (result.isEmpty()) {
            throw new BookException("\"" + title + "\" not in cart.");
        } else {
            for (int i = 0; i < result.size(); i++) {
                Books book = (Books) result.get(i);
                customer.getBooksList().remove(book);
                em.merge(customer);
                System.out.println(customer+" , "+book);
                em.remove(book);
                System.out.println(book+ " , deleted successfully");
            }

        }
    }

    @Override
    public List<Books> getContents() {
//        Query q = em.createNamedQuery("Books.findAllByCustID");
//        q.setParameter("custId", customer.getCustId());
//        List result = q.getResultList();
        List<Books> result = customer.getBooksList();
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    @Remove()
    @Override
    public void remove() {
        System.out.println("el mafrood close connection");
        customer = null;
        em.close();
    }

}
