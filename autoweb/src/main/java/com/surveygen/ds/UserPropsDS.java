package com.surveygen.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.seox.db.User;
import com.surveygen.db.UserProps;
import com.surveygen.db.UserPropsDAO;

public class UserPropsDS implements DomainStore { 

    protected UserPropsDAO m_dao; //##
    protected Map m_idToMap; 
    protected Map m_userIdToMap;
    protected User m_user;
    
    private static DomainStore m_domainStore= new UserPropsDS(); 
    private static Logger m_logger = Logger.getLogger(UserPropsDS.class);
    
    public static DomainStore getInstance() { 
        return m_domainStore; 
    } 

    protected UserPropsDS(User user) {
        this();
        m_user = user;
    }    
    
    protected UserPropsDS() { 
        m_dao = new UserPropsDAO(); 
        m_idToMap = new HashMap(); 
        m_userIdToMap = new HashMap(); 
    } 
    
    public void loadFromDB() throws Exception { 
        m_logger.info("Loading data " + this.getClass().getName());
       
        List list  = null;
        
        if (m_user != null)
            list = m_dao.findByUserId(new Long(m_user.getUserId()));
        else
            list = m_dao.findAll(); 

        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            updateMaps(iter.next(), false);
        } 
    } 
    
    public UserProps getById(Long id) { 
        return (UserProps) m_idToMap.get(id); 
    } 
    
    public synchronized List getByUserId(Long userId)  {
        if (m_userIdToMap.containsKey(userId))
            return new ArrayList(((Map) m_userIdToMap.get(userId)).values());
        else
            return new ArrayList();
    } 
    
    public boolean delete(User user, UserProps userProps) {
        
        if (user == null || userProps == null ) {
            m_logger.warn("Passed argument is null");
            return false;
        }
        
        Long userId = new Long (user.getUserId());
                
        synchronized (this) {
            if (m_userIdToMap.containsKey(new Long(user.getUserId()))) {
                Map userObjectss = (Map)m_userIdToMap.get(userId);
                if ( !userObjectss.containsKey(new Long(userProps.getUserPropsId()))) {
                    m_logger.warn("User " + userId + " does not own note " + userProps.getUserPropsId());
                    return false;
                }
            } else {
                m_logger.warn("User not found by ID" + user.getUserId() + " un=" + user.getUsername());
                return false;
            }
        }        
        
        try {
            m_dao.delete(userProps);
        }
        catch (Exception e) {
            m_logger.error("Error while delete note from DB note " + userProps.getUserPropsId());
            return false;
        }
        updateMaps(userProps, true);
        m_logger.debug("Note deleted "+userProps.getUserPropsId());
        return true;
    }

    public boolean newObj(User user, UserProps obj) {
        
        obj.setUserId(user.getUserId());
        
        try {
            m_dao.save(obj);
        }
        catch (Exception e) {
            m_logger.error("Error while add to DB " + obj);
            return false;
        }
        
        updateMaps(obj, false);
        m_logger.debug("New Note created "+ obj);
        return true;
    }

    public boolean update(UserProps obj) {
        
        try {
            m_dao.attachDirty(obj);
        }
        catch (Exception e) {
            m_logger.error("Error while update from DB " + obj);
            return false;
        }
        m_logger.debug("Updated "+obj);
        return true;
    }    
    
    private synchronized void updateMaps(Object o, boolean del) {
        UserProps obj = (UserProps) o; 
        m_logger.info("updated map for " + obj);
        
        Long noteId = new Long(obj.getUserPropsId());
        if (del)
            m_idToMap.remove(noteId);
        else
            m_idToMap.put(noteId,obj);
        
        
        // Update UserIdMap
        Map notes = (Map) m_userIdToMap.get(new Long(obj.getUserId())); 
        if (notes == null) { 
            notes = new TreeMap();
            if (!del)
                m_userIdToMap.put(new Long(obj.getUserId()), notes);
        } 
        if (del)
            notes.remove(noteId);
        else
            notes.put(noteId, obj); 
    }

    public Object getById(String id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void reset() throws Exception {
    }

    public void print() {
        m_logger.debug(m_idToMap);
        m_logger.debug(m_userIdToMap);
    }
    
    
    public static void main(String args[]) throws Exception{
        UserPropsDS ds = (UserPropsDS)UserPropsDS.getInstance();
        ds.loadFromDB();
    }
} 
