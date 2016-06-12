package com.seox.work; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jtrend.service.Assemblee;
import com.seox.db.Category;
import com.seox.db.Keyword;
import com.seox.db.User;
import com.seox.db.UserDomain;
import com.seox.db.UserKeywordMap;

/**
 * This is test object to provide service interface to the rest of program.
 * I may not see the benefit of this class. May rahter see the redendancy issue. 
 * So keep watching about how this class is used.
 *  
 */
public class UserBO  implements Assemblee { 

    User m_user; 
    List m_categories; 

    List m_keywords; // list of UserKeyword objects
    Map  m_keywordToUKM;
    
    private UserDS m_userDS;
    private CategoryDS m_catDS;
    private KeywordDS m_keywordDS;
    
    // Will be used to save session objects and used to restore useful session objects, when a session was expired.
    private Map m_sessionSaves;
    
    private static Logger m_logger = Logger.getLogger(UserBO.class);
    
    public UserBO(User user) { 
        m_user = user; 
        m_userDS = UserDS.getInstance();
        m_catDS = CategoryDS.getInstance();
        m_keywordDS = KeywordDS.getInstance();
        m_sessionSaves = new HashMap();
    } 
    
    public User getUserObj() { 
        return m_user; 
    } 
    
    public List getCategories() { 
        return new ArrayList(m_categories); 
    } 
    public boolean addCategory(Category cat) {
        if ( m_catDS.newCategory(m_user, cat) ) {
            m_categories.add(cat);
            return true;
        }
        return false;
    }
    
    public Category findCategoryById(Long id) {
        Category cat = m_catDS.getCategory(id);
        return cat;
    }
    public boolean deleteCategoryById(Long id) {
        Category cat = m_catDS.getCategory(id);
        if (cat == null) {
            m_logger.debug("Category not found by id " + id);
            return false;
        }
        
        if (!m_categories.contains(cat)) {
            m_logger.debug("Category for user not found by id " + id);
            return false;
        }
        
        if (m_catDS.deleteCategory(m_user, cat)) {
            m_categories.remove(cat);
            return true;
        } else {
            m_logger.warn("Failed to delete Category by = " + id);
            return false;
        }
    }

    
    
    public List getDomains() { 
        return new ArrayList(m_user.getUserDomains()); 
    } 
    
    public UserDomain findDomainById(Long id) {
        List domains = getDomains();
        for(Iterator iter = domains.iterator();iter.hasNext();) {
            UserDomain domain = (UserDomain)iter.next();
            if (id.longValue() == domain.getUserDomainId() ) {
                return domain;
            }
        }
        return null;
    }
    public void addDomain(UserDomain domain) {
        m_userDS.newDomain(m_user, domain);
    }
    public void removeDomain(UserDomain domain) {
        m_userDS.removeDomain(m_user, domain);
    }
    
    public List getKeywords() { 
        return new ArrayList(m_keywords); 
    }
    public Keyword findKeywordById(Long id) {
        List keywords = getKeywords();
        for(Iterator iter = keywords.iterator();iter.hasNext();) {
            Keyword keyword = (Keyword)iter.next();
            if (id.longValue() == keyword.getKeywordId() ) {
                return keyword;
            }
        }
        return null;
    }

    public boolean addKeyword(Keyword keyword) {
        //TODO need transaction
        
        Keyword k = m_keywordDS.findKeyword(keyword.getKeyword());
        
        if ( k == null) { 
            if (!m_keywordDS.newKeyword(keyword)) {
                m_logger.warn("Failed to add keyword " + keyword);
                return false;
            }
        } else {
            keyword = k;
        }
        
        
        // Check if user has the UKM for k.getKeywordId()
        // If yes, get out
        
        UserKeywordMap ukMap = new UserKeywordMap();
        ukMap.setKeywordId(keyword.getKeywordId());
        ukMap.setEnteredT(new Date());
        ukMap.setActive((byte)1);
        
        if ( !m_userDS.newUserKeywordMap(m_user, ukMap) ) {
            m_logger.warn("Failed to add keyword map for " + keyword + " user " + m_user.getUsername());
            return false;
        }
        
        m_keywords.add(keyword);
        m_keywordToUKM.put(keyword.getKeyword(), ukMap);
        return true;
    }
    
    public boolean deleteKeywordFromUser(Keyword keyword) {
        
        UserKeywordMap ukm = (UserKeywordMap)m_keywordToUKM.get(keyword.getKeyword());
        
        if ( !m_userDS.deleteUserKeywordMap(m_user, ukm) ) {
            return false;
        }

        m_keywords.remove(keyword);
        return true;
    }

    public void removeSessionSave(String key) {
        m_sessionSaves.remove(key);
    }
    public Object getSessionSave(String key) {
        return m_sessionSaves.get(key);
    }
    public void setSessionSave(String key, Object save) {
        m_sessionSaves.put(key, save);
    }
} 
