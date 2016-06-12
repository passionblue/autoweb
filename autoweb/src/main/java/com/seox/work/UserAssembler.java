package com.seox.work; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jtrend.service.Assemblee;
import com.jtrend.service.Assembler;
import com.seox.db.Category;
import com.seox.db.Keyword;
import com.seox.db.User;
import com.seox.db.UserDAO;
import com.seox.db.UserDomain;
import com.seox.db.UserKeywordMap;

public class UserAssembler implements Assembler { 

    User m_user; 
    UserBO m_userBO; 
    UserDS m_userDS; 
    KeywordDS m_keywordDS; 
    CategoryDS m_categoryDS; 

    private static Logger m_logger = Logger.getLogger(UserAssembler.class);
    
    public UserAssembler(String username) throws Exception { 
        m_userDS = UserDS.getInstance(); 
        m_keywordDS = KeywordDS.getInstance(); 
        m_user = m_userDS.getUserByUsername(username);
        
        if (m_user == null) { 
                throw new Exception("User not found by username " + username); 
        } 
        
        m_userBO = new UserBO(m_user);

        m_logger.debug("user found " + m_user.getUserId() + " by " + username);
    }    
    
    public void assemble() throws Exception{ 

        m_logger.debug("Assembling started for " + m_user.getUsername());
        Set maps = m_user.getUserKeywordMaps();
        
        List keywords = new ArrayList(); 
        Map  keywordToMap = new HashMap();
        // Adding keywords for the user 
        for(Iterator iter = maps.iterator(); iter.hasNext();) { 
            UserKeywordMap ukm = (UserKeywordMap) iter.next();
            
            if (ukm.getActive() == 0 ) {
                continue;
            }
            
            Keyword k = m_keywordDS.getKeyword(new Long(ukm.getKeywordId())); 
            m_logger.debug("Loading keyword : [" + k.getKeyword() + "]");
            
            keywords.add(k);
            keywordToMap.put(k.getKeyword(), ukm);
        } 
        
        m_logger.debug("Num of keywords = " + keywords.size() );
        
        m_userBO.m_keywords = keywords; 
        m_userBO.m_keywordToUKM = keywordToMap;
        
        
        // Loads Category
        
        List cats = CategoryDS.getInstance().getCategoriesByUser(new Long(m_user.getUserId()));
        m_userBO.m_categories = new ArrayList(cats);
        m_logger.debug("Num of Categories : " + cats.size());
        
        m_logger.debug("Finished assembling user " + m_user.getUsername() + "[" + m_user.getUserId() + "]");
    } 
    
    public Assemblee getAssemblee() { 
        return m_userBO; 
    } 
    
    public static void main(String args[]) throws Exception{ 
        
        UserDS.getInstance().loadFromDB(); 
        KeywordDS.getInstance().loadFromDB();
        
        
        
        
        CategoryDS.getInstance().loadFromDB(); 
        
        UserDAO userDAO = new UserDAO(); 
        List list = userDAO.findAll(); 

        UserBO bo = null;
        for (Iterator iter = list.iterator(); iter.hasNext();) { 
            User user = (User) iter.next(); 

            UserAssembler ass = new UserAssembler(user.getUsername()); 
            ass.assemble(); 
            
            bo = (UserBO) ass.getAssemblee(); 
        }

        if (true ) return;
        List cats = CategoryDS.getInstance().getCategoriesByUser(new Long(bo.getUserObj().getUserId()));
        
        System.out.println("num cats for " + bo.getUserObj().getUsername() + ":"+ cats.size());
        
        if ( cats.size() > 0 ) {
            
            Category cat = (Category)cats.get(0);
            UserDomain domain = new UserDomain();

            domain.setEnteredT(new Date());
            domain.setDomain("yahoo.com");
            domain.setCategoryId(cat.getCategoryId());

            bo.addDomain(domain);
        }
        
/*        
        UserDomain domain = new UserDomain();

        domain.setEnteredT(new Date());
        domain.setDomain("yahoo.com");

        bo.addDomain(domain);
        
        
        
        
        Category cat = new Category();
        cat.setCategoryName("New Cat");
        cat.setEnteredT(new Date());

        bo.addCategory(cat);
        
   */           
        Keyword k = new Keyword();
        k.setEnteredT(new Date());
        k.setKeyword("asdfasdfdsaf asffasdfasdf ddf d " + System.currentTimeMillis());
        
        bo.addKeyword(k);
        
        
    } 
} 
