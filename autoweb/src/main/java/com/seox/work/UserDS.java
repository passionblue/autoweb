package com.seox.work; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.seox.db.User;
import com.seox.db.UserDAO;
import com.seox.db.UserDomain;
import com.seox.db.UserDomainDAO;
import com.seox.db.UserKeywordMap;
import com.seox.db.UserKeywordMapDAO;

public class UserDS implements DomainStore{ 

    protected UserDAO m_userDAO; 
    protected UserDomainDAO m_domainDAO; 
    protected UserKeywordMapDAO m_ukMapDAO;
    
    protected Map m_userIdToMap; 
    protected Map m_usernameToMap; 
    
    private static UserDS m_userDS= new UserDS(); 
    private static Logger m_logger = Logger.getLogger(UserDS.class);
    
    public static UserDS getInstance() { 
        return m_userDS; 
    } 
    
    private UserDS() { 
        m_userDAO = new UserDAO(); 
        m_domainDAO = new UserDomainDAO();
        m_userIdToMap = new HashMap(); 
        m_usernameToMap = new HashMap(); 
        m_ukMapDAO = new UserKeywordMapDAO();
    } 
    
    /**
     * load all users from DB. loaded users should have cascade relationship
     * to keywordmap and domains
     */
    public void loadFromDB() throws Exception { 
        m_logger.info("Loading data " + this.getClass().getName());
        List list  = m_userDAO.findAll(); 
        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            User k = (User) iter.next();
            k.getUserDomains(); // triggering load now
            k.getUserKeywordMaps(); // triggering load now
            m_userIdToMap.put(new Long(k.getUserId()),k); 
            m_usernameToMap.put(k.getUsername(), k);
            
            m_logger.info("Added " + k.getUsername());
        } 
    } 

    public User getUserByUsername(String username){ 
        //TODO should have an option to check DB if not found in memory map
        return(User) m_usernameToMap.get(username); 
    } 
    public User getUserById(Long id){ 
        //TODO should have an option to check DB if not found in memory map
        return(User) m_userIdToMap.get(id); 
    } 
    
    public boolean newUser(User user) { 
        if ( m_usernameToMap.containsKey(user.getUsername())) {
            m_logger.debug("User " + user.getUserId() + " already exists");
            return false;
        }
        
        m_userDAO.save(user);
        m_userIdToMap.put(new Long(user.getUserId()), user);
        m_usernameToMap.put(user.getUsername(),user);
        return true;
    } 
    
    public boolean newDomain(User user, UserDomain domain) {
        
        domain.setUser(user);
        user.getUserDomains().add(domain);

        m_domainDAO.save(domain);
        return true;
    }
    
    public boolean removeDomain(User user, UserDomain domain) {
        
        user.getUserDomains().remove(domain);
        m_domainDAO.delete(domain);
        return true;
    }

    
    
    public boolean newUserKeywordMap(User user, UserKeywordMap ukMap) {
        
        ukMap.setUser(user);
        user.getUserKeywordMaps().add(ukMap);

        try {
            m_ukMapDAO.save(ukMap);
        }
        catch (Exception e) {
            m_logger.error("Error while adding UserKeyword ", e);
            return false;
        }
        return true;
    }
    
    public boolean updateUserKeywordMap(UserKeywordMap ukMap) {
        m_ukMapDAO.attachDirty(ukMap);
        return true;
    }
    
    public boolean deleteUserKeywordMap(User user, UserKeywordMap ukMap) {

        user.getUserKeywordMaps().remove(ukMap);
        
        try {
            m_ukMapDAO.delete(ukMap);
        }
        catch (Exception e) {
            m_logger.error("Error while removing ukm ", e);
            return false;
        }
        return true;
    }

    public void reset() throws Exception {
    }
} 
