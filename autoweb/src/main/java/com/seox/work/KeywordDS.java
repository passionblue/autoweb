package com.seox.work; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jtrend.service.DomainStore;
import com.seox.db.Keyword;
import com.seox.db.KeywordDAO;



public class KeywordDS implements DomainStore{ 
    
    protected KeywordDAO m_keywordDAO; 
    
    protected Map m_idToMap; 
    protected Map m_keywordToMap; 
    
    private static KeywordDS m_keywordDS= new KeywordDS(); 
    
    private static Logger m_logger = Logger.getLogger(KeywordDS.class);
    
    public static KeywordDS getInstance() { 
        return m_keywordDS; 
    } 
    
    protected KeywordDS() { 
        m_keywordDAO = new KeywordDAO(); 
        m_idToMap = new HashMap(); 
        m_keywordToMap = new HashMap(); 
    } 
    
    public void loadFromDB() throws Exception { 
        List list  = m_keywordDAO.findAll(); 
        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            Keyword k = (Keyword) iter.next(); 
            m_idToMap.put(new Long(k.getKeywordId()),k); 
            m_keywordToMap.put(k.getKeyword(), k); 
        } 

    } 
    
    public Keyword getKeyword(Long id) { 
        return (Keyword) m_idToMap.get(id); 
    }
    
    public Keyword findKeyword(String keyword) {
        return (Keyword) m_keywordToMap.get(keyword);
    }
    
    public boolean newKeyword(Keyword keyword) {
        
        if ( m_keywordToMap.containsKey(keyword.getKeyword())) {
            m_logger.warn("Keyword " + keyword.getKeyword() + " already exists");
            return false;
        }
        
        try {
            m_keywordDAO.save(keyword);
        }
        catch (Exception e) {
            m_logger.error("Error occurred while adding keyword " + keyword.getKeyword());
            return false;
        }
        m_idToMap.put(new Long(keyword.getKeywordId()), keyword);
        m_keywordToMap.put(keyword.getKeyword(), keyword);
        
        return true;
    }

    public void reset() throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    
} 
