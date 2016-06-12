package com.autosite.util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;

public class WebLog {

    
    public static void debug(HttpSession session, String str){
        
        PageView pageView = (PageView)session.getAttribute("k_view_pageview");
        String alias = "[##########] ";
        if (pageView != null)
            alias = "[" + pageView.getAlias() + "] ";
        m_logger.debug(alias + str);
    }
    
    public static void info(HttpSession session, String str){
        
        PageView pageView = (PageView)session.getAttribute("k_view_pageview");
        String alias = "[##########] ";
        if (pageView != null)
            alias = "[" + pageView.getAlias() + "] ";
        m_logger.info(alias + str);
    }

    public static void error(HttpSession session, String str, Exception ex){
        
        PageView pageView = (PageView)session.getAttribute("k_view_pageview");
        String alias = "[##########] ";
        if (pageView != null)
            alias = "[" + pageView.getAlias() + "] ";
        m_logger.error(alias + str, ex);
    }

    
    private static Logger m_logger = Logger.getLogger(WebLog.class);
}
