/*
 * Created on Oct 31, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seox.db.User;
import com.seox.db.UserDAO;

public class UserService {

    private Map m_userMapById = new HashMap();
    private Map m_userMapByUsername = new HashMap();
    
    UserDAO m_userDao = new UserDAO();
    
    private static UserService m_instance = new UserService();
    Logger m_logger = Logger.getLogger(UserService.class);
    
    public static UserService getInstance() {
        return m_instance;
    }
    
    public void loadAll() {
        List users =  m_userDao.findAll();
        
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            
            User user = (User) iter.next();
            
            m_logger.debug("User " + user.getUsername() + " is loaded");
            
            m_userMapById.put(new Long(user.getUserId()), user);
            m_userMapByUsername.put(user.getUsername(), user);
        }
    }
    
    // check memory and load from database
    public User getUserByUsername(String username) {
        
        if (!m_userMapByUsername.containsKey(username)) {
            //should load
            return null;
        }
        return (User) m_userMapByUsername.get(username);
    }
    
    public long addNewUser(User user) throws Exception {

        if (m_userMapByUsername.containsKey(user.getUsername())) {
            throw new Exception("username " + user.getUsername() + " is already being used");
        }

        m_userDao.save(user);
        m_logger.debug("user " + user.getUsername() + " saved to DB. userid= " + user.getUserId());
        return user.getUserId();
    }
    
    public void activateUser(User user) {
        
        if (user.getActivatedT() != null ) return;
        user.setActivatedT(new Date());
        m_userDao.attachDirty(user);
        
    }
    
    
    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        userService.loadAll();

        String email = "gmailssss";
        String lastname = "last";
        String firstname = "first";
        String password = "pass";
        
         
        User user = new User();
        
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setUsername(email);
        user.setRegisteredT(new Date());
        user.setGoogleKey(null);
        user.setActivatedT(null);
        userService.addNewUser(user);
        
        User user2  = userService.getUserByUsername("fasd");
        
        userService.activateUser(user2);
    }

}
