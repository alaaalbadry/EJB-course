/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart.util;

import java.io.Serializable;

/**
 *
 * @author ALaa
 */
public class BookException extends Exception implements Serializable {

    public BookException(String errorMessage) {
        super(errorMessage);
    }

}
