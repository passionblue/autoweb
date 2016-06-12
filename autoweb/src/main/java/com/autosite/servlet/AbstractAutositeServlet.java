package com.autosite.servlet;

import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jtrend.util.RequestUtil;

public abstract class AbstractAutositeServlet extends HttpServlet {
    
    protected void printHeaders(HttpServletRequest request){
        m_logger.debug("--- Request Info -----------------------------------------------------------------------------");

        Enumeration enu = request.getHeaderNames();
        while(enu.hasMoreElements()) {
            String headerName = (String) enu.nextElement();
            m_logger.debug("HEADER >>" + headerName + ":" + request.getHeader(headerName));
        }
        
        m_logger.debug("getRequestURL            " + request.getRequestURL());
        m_logger.debug("getRequestURI            " + request.getRequestURI());
        m_logger.debug("getRequestedSessionId    " + request.getRequestedSessionId());
        m_logger.debug("getRemoteAddr()          " + request.getRemoteAddr());
        m_logger.debug("getRemoteHost()          " + request.getRemoteHost());
        m_logger.debug("getServerName()          " + request.getServerName());
        m_logger.debug("getServletPath()         " + request.getServletPath());
        m_logger.debug("getQueryString()         " + request.getQueryString());
        m_logger.debug("getPathInfo()            " + request.getPathInfo());
        m_logger.debug("getPathTranslated()      " + request.getPathTranslated());
        m_logger.debug("getRequestedSessionId()  " + request.getRequestedSessionId());
        m_logger.debug("ParameterString          " + RequestUtil.getParameterString(request));
        m_logger.debug("subCommand()             " + request.getParameter("subc"));
    }
    
    protected boolean isMissing(String val) {
        if ( val == null || val.trim().length() == 0) return true;
        return false;
     }

     protected boolean isThere(String val) {
         if ( val == null || val.trim().length() == 0) return false;
         return true;
      }
     
     protected boolean equalsTo(String val, int val2) {
         return equalsTo(val, ""+val2, false);
     }    
     protected boolean equalsTo(String val, int val2, int val3) {
         if ( !equalsTo(val, val2) ) return false;
         if ( !equalsTo(val, val3) ) return false;
         return true;
     }    
     
     protected boolean equalsTo(String val, String val2) {
         return equalsTo(val, val2, false);
     }

     protected boolean equalsTo(String val, String val2, String val3, boolean ignoreCase) {
         if ( !equalsTo(val, val2, ignoreCase) ) return false;
         if ( !equalsTo(val, val3, ignoreCase) ) return false;
         return true;
     }

     protected boolean equalsTo(String val, String val2, boolean ignoreCase) {
         
         if ( val == null || val2 == null) 
             return false;
         
         if (ignoreCase) {
             if ( val != null && val.trim().equalsIgnoreCase(val2)) {
                 return true;
             }
         } else {
             if ( val != null && val.trim().equals(val2)) {
                 return true;
             }
         }        
         return false;
     }

     protected boolean hasRequestValue(HttpServletRequest request, String prop, String checkValue) {
         String propValue = request.getParameter(prop);
         return equalsTo(propValue, checkValue, true);
     }     
    
    private static Logger m_logger = Logger.getLogger(AbstractAutositeServlet.class);
}
