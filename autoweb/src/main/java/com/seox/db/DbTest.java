/*
 * Created on Sep 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.db;

import java.util.Date;
import java.util.List;

public class DbTest {

    public static void main(String[] args) {
        KeywordDAO dao = new KeywordDAO();
        List list = dao.findAll();
        System.out.println(list);
        
    }
    public static void logingTest() {
        UserDAO dao = new UserDAO();
        List list = dao.findByEmail("ss@ss.com");
        dao.getSession().close();

    }
    public static void registerTest() {
        String email = "gmail";
        String lastname = "last";
        String firstname = "first";
        String password = "pass";
        
        UserDAO dao = new UserDAO();
        
        List list =dao.findByEmail(email);
        
        if ( list.size() > 0 ) {
            System.out.println("################### exist");
            return;
        }
        
        User user = new User();
        
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setUsername(email);
        user.setRegisteredT(new Date());
        user.setGoogleKey(null);
        user.setActivatedT(null);
        dao.save(user);
        
    }
}
