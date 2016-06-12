package com.jtrend.util;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil
{
    private static Logger m_logger = LoggerFactory.getLogger(RequestUtil.class);
    
    public static boolean isInternallyForwardedRequest(HttpServletRequest request){
        return BooleanUtils.toBoolean((String) request.getAttribute("k_internally_forwarded"));
    }

    public static void setInternallyForwardedRequest(HttpServletRequest request, boolean value){
        request.setAttribute("k_internally_forwarded", String.valueOf(value));         
    }
    
    public static void printString(ActionForm form)
    {
        if (form == null) 
        {
            System.out.println("###");
            return;
        }
        
        Method[] methods = form.getClass().getMethods();
        
        String str = "return ";
        
        for ( int i = 0; i < methods.length; i++)
        {
            String name = methods[i].getName();
            
            if (!name.startsWith("get")) continue;
            if (name.equals("getServletWrapper"))continue;
            if (name.equals("getMultipartRequestHandler"))continue;
            if (name.equals("getClass"))continue;
            
            String varName = name.toLowerCase();
            varName = name.substring(3);

            if (!str.equals("return ")) str += "+"; 
            
            str += "\"," + varName + "=\"+" + methods[i].getName() + "()";
        }
        str +=";";
        
        System.out.println("###" + form.getClass().getName() + "###" + str);
    }
    
    static public  String getParameterString(HttpServletRequest req){
        return getParameterString(req, ",");
    }    
    
    static public  String getParameterString(HttpServletRequest req, String delimieter)
    {
        String ret = "";
        for ( Enumeration iter  = req.getParameterNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            if (!ret.equals("")) ret +=delimieter;
            ret += key + "=" + (req.getParameter(key)== null?"":req.getParameter(key));
        }
        return ret;
    }
    
    static public  String getParameterKeysString(HttpServletRequest req){
        return getParameterString(req, ",");
    }    
    
    static public  String getParameterKeysString(HttpServletRequest req, String delimieter)
    {
        StringBuilder builder = new StringBuilder();
        for ( Enumeration iter  = req.getParameterNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            builder.append(key).append(",");
        }
        return builder.toString();
    }
    
    static public  Map getParameters(HttpServletRequest req)
    {
        Map retMap = new HashMap();
        for ( Enumeration iter  = req.getParameterNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            String val = req.getParameter(key);
            
            retMap.put(key, val);
        }
        return retMap;
    }
    
    static public  Map getAttributes(HttpServletRequest req)
    {
        Map retMap = new HashMap();
        for ( Enumeration iter  = req.getAttributeNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            Object val = req.getAttribute(key);
            
            retMap.put(key, val);
        }
        return retMap;
    }
    
    static public  Map getPreservedParameters(HttpServletRequest req)
    {
        Map retMap = new HashMap();
        for ( Enumeration iter  = req.getParameterNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            String val = req.getParameter(key);
            
            if (key.startsWith("prv_")){
                String newName = key.substring(4);
                if (val != null) {
                    retMap.put(newName, val);
                }
            }
        }
        return retMap;
    }

    static public  Map getPreservedAndTransferParameters(HttpServletRequest req)
    {
        Map retMap = new HashMap();
        for ( Enumeration iter  = req.getParameterNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            String val = req.getParameter(key);
            
            if (key.startsWith("prx_")){
                String newName = key.substring(4);
                if (val != null) {
                    retMap.put(newName, val);
                }
            }
        }
        return retMap;
    }

    
    static public void printFormattedParameters(HttpServletRequest req)
    {
        m_logger.debug("----------------- Parameters -----------------------------");
        for ( Enumeration iter  = req.getParameterNames(); iter.hasMoreElements();)
        {
            String key = (String) iter.nextElement();
            String val = req.getParameter(key);
            m_logger.debug(key + " : " + val );
        }
        m_logger.debug("----------------------------------------------------------");
    }
    
    
    static public Map getHeadersAttributes(HttpServletRequest request) {
        Map retMap = new HashMap();
        
        Enumeration enu = request.getHeaderNames();
        while(enu.hasMoreElements()) {
            String headerName = (String) enu.nextElement();
            retMap.put(headerName, request.getHeader(headerName));
        }
        return retMap;
    }
    
}
