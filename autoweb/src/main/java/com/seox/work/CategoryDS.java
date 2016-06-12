package com.seox.work; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.seox.db.Category;
import com.seox.db.CategoryDAO;
import com.seox.db.User;


public class CategoryDS implements DomainStore { 
    protected CategoryDAO m_categoryDAO; 
    
    protected Map m_idToMap; 
    protected Map m_categoryToMap; 
    protected Map m_userIdToMap; 
    
    private static CategoryDS m_categoryDS= new CategoryDS(); 
    
    private static Logger m_logger = Logger.getLogger(CategoryDS.class);
    
    public static CategoryDS getInstance() { 
        return m_categoryDS; 
    } 

    protected CategoryDS() { 
        m_categoryDAO = new CategoryDAO(); 
        m_idToMap = new HashMap(); 
        m_categoryToMap = new HashMap(); 
        m_userIdToMap = new HashMap(); 
    } 
    
    public void loadFromDB() throws Exception { 
        m_logger.info("Loading data " + this.getClass().getName());
       List list  = m_categoryDAO.findAll(); 
        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            Category k = (Category) iter.next(); 
            m_idToMap.put(new Long(k.getCategoryId()),k); 
            
            List cats = (List) m_userIdToMap.get(new Long(k.getUserId())); 
            if (cats == null) { 
                cats = new ArrayList(); 
                m_userIdToMap.put(new Long(k.getUserId()), cats); 
            } 
            cats.add(k); 
        } 
    } 
    
    public Category getCategory(Long id) { 
        return (Category) m_idToMap.get(id); 
    } 
    
    public List getCategoriesByUser(Long id)  {
        if (m_userIdToMap.containsKey(id))
            return new ArrayList((List) m_userIdToMap.get(id));
        else
            return new ArrayList();
    } 
    
    public boolean deleteCategory(User user, Category cat) {
        
        m_categoryDAO.delete(cat);
        updateMaps(user, cat, true);
        return true;
    }

    public boolean newCategory(User user, Category cat) {
        
        cat.setUserId(user.getUserId());
        m_categoryDAO.save(cat);
        updateMaps(user, cat, false);
        m_logger.debug(""+cat.getCategoryId());
        return true;
    }
    
    public void updateMaps(User user, Category cat, boolean del) {
        
        if (del)
            m_idToMap.remove(new Long(cat.getCategoryId()));
        else
            m_idToMap.put(new Long(cat.getCategoryId()),cat);
        
        List cats = (List) m_userIdToMap.get(new Long(cat.getUserId())); 
        if (cats == null) { 
            cats = new ArrayList();
            if (!del)
                m_userIdToMap.put(new Long(cat.getUserId()), cats);
        } 
        if (del)
            cats.remove(cat);
        else
            cats.add(cat); 
    }

    public void reset() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    
} 
