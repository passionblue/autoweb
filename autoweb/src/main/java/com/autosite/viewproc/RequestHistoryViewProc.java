package com.autosite.viewproc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
/*
This is generated codes. this needs to 

*/
public class RequestHistoryViewProc implements ViewProc{

    public String getName() {
        return "RequestHistoryViewProc";
    }

    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception{
        m_logger.debug(RequestHistoryViewProc.class.getName());

    }

    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
    
    private static Logger m_logger = Logger.getLogger(RequestHistoryViewProc.class);
}
