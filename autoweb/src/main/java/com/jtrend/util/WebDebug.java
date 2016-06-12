package com.jtrend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class WebDebug {

    private Map m_map;
    
    private static Logger m_logger = Logger.getLogger(WebDebug.class);
    private static WebDebug m_instance = new WebDebug();;

    public static  WebDebug getInstance() {
        return m_instance;
    }

    private WebDebug(){
    }

    public void putDebug(HttpSession session, Object obj){
        List list = (List) session.getAttribute("webDebugList");
        if (list == null) {
            list = new ArrayList();
            session.setAttribute("webDebugList", list);
        }
        
        list.add("[" + obj + "]");
    }

    
    public void putDebug(HttpSession session, String name, Object obj){
        List list = (List) session.getAttribute("webDebugList");
        if (list == null) {
            list = new ArrayList();
            session.setAttribute("webDebugList", list);
        }
        
        list.add(name + "=" + obj);
    }
    
    public List getDebugs(HttpSession session){
        List list = (List) session.getAttribute("webDebugList");
        if (list == null) {
            return new ArrayList();
        }
        
        return list;
    }
    
    public void clear(HttpSession session){
        session.removeAttribute("webDebugList");
    }
    
    // @see layout.jsp
    public static boolean needsShowSimpleDebug(HttpServletRequest request){
        HttpSession session = request.getSession();
        
        if (request.getParameter("displayPageDebug") != null || request.getRemoteAddr().equalsIgnoreCase("108.27.45.7") ) {
            return true;
        }
        
        return false;
        
    }
    
    
}

