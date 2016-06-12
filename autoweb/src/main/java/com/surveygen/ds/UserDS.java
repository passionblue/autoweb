package com.surveygen.ds; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.surveygen.db.User;
import com.surveygen.db.UserDAO;



public class UserDS implements DomainStore{ 

    protected UserDAO m_userDAO; 
    
    protected Map m_userIdToMap; 
    protected Map m_usernameToMap; 
    
    private static UserDS m_userDS= new UserDS(); 
    private static Logger m_logger = Logger.getLogger(UserDS.class);
    
    public static UserDS getInstance() { 
        return m_userDS; 
    } 
    
    private UserDS() { 
        m_userDAO = new UserDAO(); 
        m_userIdToMap = new HashMap(); 
        m_usernameToMap = new HashMap(); 
    } 
    
    /**
     * load all users from DB. loaded users should have cascade relationship
     * to keywordmap and domains
     */
    public void loadFromDB() throws Exception { 
        m_logger.info("Loading data " + this.getClass().getName());
        List list  = m_userDAO.findAll(); 
        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            User user = (User) iter.next();

            addToMap(user);
            
        } 
    } 
    
    public User getUserByUsername(String username){ 

        if ( m_usernameToMap.containsKey(username)) {
            return(User) m_usernameToMap.get(username); 
        } else {
            List list = (List) m_userDAO.findByUsername(username);
            
            if (list.size() > 0) {
                for (Iterator iter = list.iterator(); iter.hasNext();) { 
                    User user = (User) iter.next();
                    addToMap(user);
                } 
                return (User) m_usernameToMap.get(username);
            }
            else {
                m_logger.error("User not found by username " + username);
                return null;
            }
        }
    } 
    public User getUserById(Long id){ 
        if ( m_userIdToMap.containsKey(id)) {
            return(User) m_userIdToMap.get(id); 
        } else {
            List list = (List) m_userDAO.findById(id);
            
            if (list.size() > 0) {
                for (Iterator iter = list.iterator(); iter.hasNext();) { 
                    User user = (User) iter.next();
                    addToMap(user);
                } 
                return (User) m_userIdToMap.get(id);
            }
            else {
                m_logger.error("User not found by username " + id);
                return null;
            }
        }
    } 
    
    public boolean newUser(User user) { 
        if ( m_usernameToMap.containsKey(user.getUsername())) {
            m_logger.debug("User " + user.getUserId() + " already exists");
            return false;
        }
        
        try {
            m_userDAO.save(user);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        addToMap(user);
        return true;
    } 

    public void reset() throws Exception {
        m_userIdToMap.clear();
        m_usernameToMap.clear();
        loadFromDB();
    }

    protected void addToMap(User user) {
        m_userIdToMap.put(new Long(user.getUserId()),user); 
        m_usernameToMap.put(user.getUsername(), user);
        m_logger.info("Loaded to memory. User=[" + user.getUsername() + "]");
    }
    
    
    public Object getById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) throws Exception {
        
       UserDS ds = UserDS.getInstance();
       
       ds.loadFromDB();
       
       User user = ds.getUserByUsername("joshuayoo");
       
       System.out.println(user.getUsername());
        
        
    }
} 
