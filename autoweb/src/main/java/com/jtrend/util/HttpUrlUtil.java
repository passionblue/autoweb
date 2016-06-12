package com.jtrend.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.session.PageView;
import com.jtrend.session.SessionRequestHandle;

public class HttpUrlUtil {
    
    /*
     * used to add suffix to passed url
     */
    public static String appendToUrl(HttpServletRequest request, String baseUrl){
        baseUrl += getFromTo(request);
        baseUrl += getRequestHandleId(request);
        return baseUrl;
    }
    public static String getCommonUrlAppends(HttpServletRequest request){
        return getCommonUrlAppends(request, true);
    }
    public static String getCommonUrlAppends(HttpServletRequest request, boolean queryDelimiter){
        String returnString = "";
        
        returnString += getFromTo(request, queryDelimiter);
        returnString += getRequestHandleId(request,queryDelimiter);
        return returnString;
    }
    
    
    public static String getFromTo(HttpServletRequest request){
        return getFromTo(request, true);
    }
    
    public static String getFromTo(HttpServletRequest request, boolean queryDelimiter){
        // Append FromTo page name
        PageView pageView = (PageView) request.getAttribute("k_view_pageview");

        if ( pageView != null) {
            return (queryDelimiter?"&":"")+"fromto=" +pageView.getAlias();
        } else {
            return "";
        }
    }
    
    public static String getRequestHandleId(HttpServletRequest request){
        return getRequestHandleId(request, true);
    }
    
    public static String getRequestHandleId(HttpServletRequest request, boolean queryDelimiter){
        SessionRequestHandle handle = (SessionRequestHandle) request.getAttribute("r_handle");
        
        if ( handle != null) {
            return (queryDelimiter?"&":"")+"_reqhid=" +handle.getId();
        } else {
            return "";
        }
    }
}
