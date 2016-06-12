package com.jtrend.ds;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jtrend.db.DataAccessObject;
import com.jtrend.db.DataObject;
import com.jtrend.service.DomainStore;
import com.seox.db.BaseHibernateDAO;
import com.seox.db.Keyword;
import com.seox.db.KeywordDAO;
import com.seox.work.KeywordDS;

public class GenericDomainStore implements DomainStore {

    protected DataAccessObject m_dao; 
    
    protected Map m_idToMap; 
    protected Map m_keywordToMap; 
    
    private static DomainStore m_domainStore= new GenericDomainStore(); 
    
    private static Logger m_logger = Logger.getLogger(KeywordDS.class);
    
    public static synchronized DomainStore getInstance() { 
        return m_domainStore; 
    } 

    public Object getById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    protected GenericDomainStore() { 
//TODO        m_dao = new BaseHibernateDAO(); 
        m_idToMap = new HashMap(); 
        m_keywordToMap = new HashMap(); 
    } 
    
    public void loadFromDB() throws Exception { 
        List list  = m_dao.findAll(); 
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            DataObject dataObject = (DataObject) iter.next();
            m_idToMap.put(new Long(dataObject.getId()), dataObject); 
//TODO            m_keywordToMap.put(dataObject.getKeyword(), dataObject); 
        } 
    } 
    
    public DataObject getDataObject(Long id) { 
        return (DataObject) m_idToMap.get(id); 
    }

/*    
    public Keyword findKeyword(String keyword) {
        return (Keyword) m_keywordToMap.get(keyword);
    }
*/
    
    public boolean newDataObject(DataObject dataObject) {

/*        
        if ( m_keywordToMap.containsKey(keyword.getKeyword())) {
            m_logger.warn("Keyword " + keyword.getKeyword() + " already exists");
            return false;
        }
*/        
        
        try {
//            m_dao.save(dataObject);
        }
        catch (Exception e) {
            return false;
        }
        m_idToMap.put(new Long(dataObject.getId()), dataObject);
        
        return true;
    }

    public void reset() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    
    
}
