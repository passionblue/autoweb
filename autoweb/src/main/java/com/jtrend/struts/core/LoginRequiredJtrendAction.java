/*
 * Created on Nov 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.struts.core;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.seox.work.UserBO;

abstract public class LoginRequiredJtrendAction extends JtrendAction{

    
    protected boolean loginRequired()
    {
        return true;
    }
    
    protected UserBO getUserBO(HttpSession session) {
        UserBO userBO = (UserBO) session.getAttribute("k_userbo");

        if (userBO == null ) {
            //TODO should do something. but never happened
        }
        
        return userBO;
    }
    
    
    private static Logger m_logger = Logger.getLogger(LoginRequiredJtrendAction.class);
}
