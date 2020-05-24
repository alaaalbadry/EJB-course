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
public class IdVerifier implements Serializable{
    
    public boolean  validate(String id){
//        if(id.equals("")){
//           return false;         
//        }
//        if (id.contains(" ")||id.contains(".")){
//            return false;
//        }
        return true;
    }
}
