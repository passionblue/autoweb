/*
 * Created on Oct 31, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.service;

import com.seox.db.User;

/**
 * Object assembler pattern in ugly fashion. 
 * 
 *  1. assembles account for the user
 *  2. domains and keyword
 *  3. ranking database check 
 *  4. and so on
 */
  


public class UserAssembly {

    private User m_user;
    
    public UserAssembly(User user) {
        m_user = user;
    }
    
    public User getUser() {
        return m_user;
    }
    public void assemble() throws Exception {
        
        
    }
}
