/*
 * Created on Nov 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.struts.core.viewproc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.seox.SeoxConst;
import com.seox.util.SeoxLogger;
import com.seox.work.UserBO;

public class AdminViewProc implements ViewProc{
    
    private static Logger m_logger = Logger.getLogger(AdminViewProc.class);
    
    public String getName() {
        return "AdminViewProc";
    }
    
    public void process(HttpServletRequest request, HttpSession session , boolean nocache){
        
        UserBO userBO = (UserBO) session.getAttribute("k_userbo");

        if (userBO == null|| userBO.getUserObj().getType() != SeoxConst.USER_TYPE_SITE_ADMIN) {
            m_logger.info("UserBO not found or not Site ADMIN before run AdminViewProc");
            session.removeAttribute("k_admin_logs");
            return;
        }
        
        List list = SeoxLogger.getLogsList("main");
        session.setAttribute("k_admin_logs", list);

        List loginList = SeoxLogger.getLogsList("login");
        session.setAttribute("k_admin_logs_login", loginList);
        
    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
    
    
}
