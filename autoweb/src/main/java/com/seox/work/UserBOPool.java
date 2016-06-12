/*
 * Created on Nov 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.work;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

//import sun.util.logging.resources.logging;

import com.seox.db.KeywordDAO;

public class UserBOPool {

    protected KeywordDAO m_keywordDAO; 
    
    protected Map m_userPool; 
    
    private static UserBOPool m_instance= new UserBOPool(); 
    
    private static Logger m_logger = Logger.getLogger(UserBOPool.class);
    
    public static UserBOPool getInstance() { 
        return m_instance; 
    } 

    private UserBOPool() {
        m_userPool = new HashMap();
    }
    
    public synchronized UserBO getUserBO(String username) {

        if ( m_userPool.containsKey(username) ) {

            return (UserBO) m_userPool.get(username);
        }else {
            UserAssembler ass;
            try {
                ass = new UserAssembler(username); 
                ass.assemble();
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                return null;
            } 
            
            UserBO bo = (UserBO) ass.getAssemblee();
            m_userPool.put(username, bo);
            return bo;
        }
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
