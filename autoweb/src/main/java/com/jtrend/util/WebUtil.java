package com.jtrend.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

//import com.sun.xml.internal.ws.server.UnsupportedMediaException;

public class WebUtil {
    
    public final static int USERAGENT_OTHERS = 0;
    public final static int USERAGENT_MSIE = 1;
    public final static int USERAGENT_FIREFOX = 2;
    public final static int USERAGENT_OPERA = 3;
    
    public final static int USERAGENT_BOT_GOOGLE = 10;
    public final static int USERAGENT_BOT_YAHOO = 11;
    public final static int USERAGENT_BOT_MSN = 12;
    
    
    private static Logger m_logger = Logger.getLogger(WebUtil.class);
    
    public final static int DATE_FORMAT_DEFAULT = 0; // 12/31/2010
    public final static int DATE_FORMAT_EU      = 1; // 31.12.2010
    public final static int DATE_FORMAT_DK      = 2; // 2000-12-31
    
    public final static int DATE_FORMAT_MEDIUM      = 3; //Dec 31, 2010
    public final static int DATE_FORMAT_LONG      = 4;  // December 31, 2010

    
    public final static int AUTOSITE_FALSE = 0; // 12/31/2010
    public final static int AUTOSITE_TRUE  = 1; // 31.12.2010
    

    public final static boolean debugSessionKeys  = false; // 
    public final static boolean debugRequestKeys  = false; // 
    public final static boolean debugHeaderKeys  = false; // 

    
    public static String displayDateOnly(Date value){
            if (value == null) return "Invalid";
            return value.toString().substring(0, 10);
    }    
    

    public static String display(Class type, Object value) {
        
        if ( value == null) return "";
        
        if ( type == type && value != null) return value.toString();

        return String.valueOf(value);
    }    
    
    public static String display(String string) {
        if( string == null) return "";
        
        return string.trim();
    }
    
    public static String display(String string, String defaultVal) {
        if ( string == null || string.trim().equals(""))
            return defaultVal;
        
        return string.trim();
    }

    
    public static String display(int value) {
        return ""+value;
    }
    
    
    public static String display(int value, int defaultVal) {
        if ( value == 0 )
            return ""+defaultVal;
        
        return ""+value;
    }

    public static String display(long value) {
        return ""+value;
    }
    
    
    public static String display(long value, long defaultVal) {
        if ( value == 0 )
            return ""+defaultVal;
        
        return ""+value;
    }

    public static String display(double value) {
        return ""+value;
    }
    
    
    public static String display(double value, double defaultVal) {
        if ( value == 0 )
            return ""+defaultVal;
        
        return ""+value;
    }
    
    
    public static String display(boolean value) {
        if (value) return "TRUE";
        return "FALSE";
    }

    /**
     * Display date only 
     * @param value
     * @return
     */

    public static String display(Date value) {
        if (value == null) return "";
        return displayDateTime(value, 0); 
    }
    public static String display(Timestamp value) {
        if (value == null) return "";
        return displayDateTime(value, 0); 
    }
    
    public static String displayDate(Date value) {
        return displayDate(value, 0);
    }
    
    public static String displayDate(Date value, int formatType) {
        DateFormat dateFormat = null;
        switch(formatType){
        case   DATE_FORMAT_DK: dateFormat = new SimpleDateFormat("yyyy-MM-dd"); break;//2010-12-31
        case   DATE_FORMAT_EU: dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);  break; //31/07/2010
        case   DATE_FORMAT_MEDIUM: dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);  break; //31/07/2010
        case   DATE_FORMAT_LONG: dateFormat = DateFormat.getDateInstance(DateFormat.LONG);  break; //31/07/2010
        default: dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US); //7/31/2010
        }
        
        return dateFormat.format(value);
    }
    public static String displayDateTime(Date value) {
        return displayDateTime(value, 0);
    }
    public static String displayDateTime(Date value, int formatType) {
        DateFormat dateFormat = null;
        switch(formatType){
        default: dateFormat = DateFormat.getDateTimeInstance();
        }

        switch(formatType){
        case   DATE_FORMAT_DK: dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); break;//2010-12-31
        case   DATE_FORMAT_EU: dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM,Locale.UK);  break; //31/07/2010
        case   DATE_FORMAT_MEDIUM: dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);  break; //31/07/2010
        case   DATE_FORMAT_LONG: dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.MEDIUM);  break; //31/07/2010
        default: dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); //7/31/2010
        }
        
        
        return dateFormat.format(value);
    }

    public static String displayMoney(double value) {
        
        double v = (Math.round (value*(100)))/100.0;
        
        String price =  "$"+ v;
        
        int pos = price.indexOf(".");
        if ( pos <0){
            return price + ".00";
        } else if (pos == 0) {
            return "0" + price;
        } else if (pos == (price.length() -1)) {
            return price + "00";
        }else if (pos == (price.length() -2)) {
            return price + "0";
        }
        
        return price;
        
    }
    
    public static boolean isNull(String string) {
        if ( string == null || string.trim().equals("")) 
            return true;
        return false;
    }
    
    public static boolean isNotNull(String string) {
        if ( string != null && !string.trim().equals("")) 
            return true;
        return false;
    }
    

    // for the general sense but need to be addressed 
    public static boolean isNull(int val) {
        if ( val < 0 ) return true;
        return false;
    }    

    // for mostly id level. 
    public static boolean isNull(long val) {
        if ( val <= 0 ) return true;
        return false;
    }    

    public static boolean isNull(double val) {
        if ( val <= 0.0 ) return true;
        return false;
    }    
    
    
    public static int toNumericaBoolean(boolean val) {
        return val?1:0;
    }    
    
    
    public static String getUrlFormat(String string) {
        if ( string == null || string.trim().equals(""))
            return "";
        
        if (!string.startsWith("http://"))
            return "http://" + string;
        
        return string;
    }
    
    public static String getRandomHeaderImage() {
        
        int num = (int) (System.currentTimeMillis()%10);
        
        return "http://www.oxo5.com/images/header" + num + ".jpg";
        
    }
    
    // return true if string is "1"
    public static boolean isTrue(String string) {
        if (string != null && ( string.equals("1") || string.equalsIgnoreCase("true") || string.equalsIgnoreCase("yes"))) 
            return true;
        return false;
    }
    public static boolean isTrue(int val) {
        if (val == 0) 
            return false;
        return true;
    }

    public static boolean isNotTrue(int val) {
        return !isTrue(val);
    }    
    
    public static boolean isNotTrue(String string) {
        return !isTrue(string);
    }
    
    public static String displayPageTitle(String string) {
        if(string == null) return "";
        
        if( string.equals("XHOME")) return "Home";
        return string;
    }
    
    public static int covert(String val, int defaultVal) {
        
        if (val == null ) return defaultVal;
        
        int ret = defaultVal;
        
        try {
            ret = Integer.parseInt(val);
        }
        catch (NumberFormatException e) {
            
        }
        
        return ret;
    }
    
    // cut off first few words
    public static String getAbstractString(String data){
        if (data == null) return "";
        if (data.length() > 100) return data.substring(0, 100);
        return data;
    }

    public static String displayTrueFalse(int val){
        if (val == 0) return "False";
        return "True";
    }

    public static String displayYesNo(String val){
        
        if (val == null) return "No";
        if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("1") || val.equalsIgnoreCase("yes"))
            return "Yes";
        return "No";
    }
    
    public static String displayYesNo(int val){
        if (val == 0) return "No";
        return "Yes";
    }
    public static String displayYN(int val){
        if (val == 0) return "N";
        return "Y";
    }

    public static boolean checkValue(String str1, String str2){
        if (str1 == null || str2 == null) return false;
        return str1.equals(str2);
    }
    public static boolean equals(String str1, String str2){
        if (str1 == null || str2 == null) return false;
        return str1.equals(str2);
    }
    
    public static boolean isPritintable(HttpServletRequest request){
        if ( request.getParameter("fmt") != null)
            return WebParamUtil.getStringValue(request.getParameter("fmt")).equalsIgnoreCase("prt");
        return false;
    }

    public static boolean isMobilePhone(HttpServletRequest request){
        
        String userAgent = request.getHeader("user-agent");
        
        if ( userAgent == null || userAgent.isEmpty())
            return false;
        
        return (!isTablet(request)) && 
               ( userAgent.indexOf("Android")>=0 || userAgent.indexOf("iPhone")>=0) &&
               userAgent.indexOf("Mobile")>=0 
               ; 
    }
    
    public static boolean isTablet(HttpServletRequest request){
        
        String userAgent = request.getHeader("user-agent");
        
        if ( userAgent == null || userAgent.isEmpty())
            return false;
        
        return userAgent.indexOf("Mobile")>=0 && ( userAgent.indexOf("iPad") >=0); 
    }
    
    

    public static String capitalize(String str){
        if (str == null) return "";
        return StringUtils.capitalize(str);
    }
    
    public static boolean isChurSingleExpenseReport(HttpServletRequest request){
        if ( request.getParameter("fmt") != null)
            return WebParamUtil.getStringValue(request.getParameter("fmt")).equalsIgnoreCase("chur-single-expense-report");
        return false;
    }
    
    public static boolean isNoFrame(HttpServletRequest request){
        if ( request.getParameter("fmt") != null)
            return WebParamUtil.getStringValue(request.getParameter("fmt")).equalsIgnoreCase("nofrm");
        return false;
    }

    // for part display
    // fmt=getpart
    // partType=panel
    // partId=230 e.g
    
    public static boolean isPartDisplay(HttpServletRequest request){
        if ( request.getParameter("fmt") != null)
            return  WebParamUtil.getStringValue(request.getParameter("fmt")).equalsIgnoreCase("getpart") || 
                    WebParamUtil.getStringValue(request.getParameter("fmt")).equalsIgnoreCase("partition") || 
                    WebParamUtil.getStringValue(request.getParameter("fmt")).equalsIgnoreCase("get-part"); 
        return false;
    }
    
//    public static String getPrintableRequest(HttpSession session){
//        m_logger.error("getPrintableRequest(session) is obsolete use getPrintableRequest(request)");
//        throw new UnsupportedMediaException();
//    }
    
    public static String getPrintableRequest(HttpServletRequest request){
        String uri = (String)request.getAttribute("k_request_uri");
        String query = (String) request.getAttribute("k_request_query");
        
        return getPrintableRequest(uri, query);
    }
    
    public static String getPrintableRequest(String uri, String query){
        
        StringBuffer buf = new StringBuffer();
        
        buf.append(uri).append("?");
        
        if (isNull(query)){
            buf.append("fmt=prt");  
        } else {
            buf.append(query).append("&").append("fmt=prt");  
        }
        return buf.toString();
    }
    
    public static String removePrintableRequest(HttpSession session){
        String uri = (String)session.getAttribute("k_request_uri");
        String query = (String) session.getAttribute("k_request_uri");
        
        return removePrintableRequest(uri, query);
    }

    public static String removePrintableRequest(HttpServletRequest request){
        return removePrintableRequest(request.getRequestURI(), request.getQueryString());
    }
    
    public static String removePrintableRequest(String uri, String query){
        
        StringBuffer buf = new StringBuffer();
        
        buf.append(uri);
        
        if (isNull(query)){
            return buf.toString();
        } else {
            
            String queryString = query;
            
            queryString = queryString.replace("&fmt=prt", "");
            queryString = queryString.replace("fmt=prt", "");
            
            if (isNull(queryString)){
                return buf.toString();
            } else {
                buf.append("?").append(queryString);
            }
        }
        return buf.toString();
    }
    public static Map getRequestParamsterAsMap(HttpServletRequest request){

        Map map = new HashMap();
        
        Enumeration names = request.getParameterNames();
        while(names.hasMoreElements()){
            String name = (String) names.nextElement();
            String val = request.getParameter(name);
            if (val != null) {
                map.put(name, val);
            }
        }

        return map;
    }
    
    // 
    public static int getUserAgentType(String agentString){
        if (agentString == null) return 0;
        
        if ( agentString.indexOf("MSIE") >= 0) return USERAGENT_MSIE;
        if ( agentString.indexOf("Firefox") >= 0) return USERAGENT_FIREFOX;
        if ( agentString.indexOf("Opera") >= 0) return USERAGENT_OPERA;
        
        if ( agentString.indexOf("Googlebot") >= 0) return USERAGENT_BOT_GOOGLE;
        if ( agentString.indexOf("msnbot") >= 0 || agentString.indexOf("MSRBOT") >=0) return USERAGENT_BOT_MSN;
        if ( agentString.indexOf("Slurp") >= 0) return USERAGENT_BOT_YAHOO;
        
        return USERAGENT_OTHERS;
    }
    
    //============================================================================================================================================
    //============================================================================================================================================
    //============================================================================================================================================
    
    
/*    public static void printPageContext(PageContext pageContext){
        
        m_logger.debug("======================== PageContext(Session Scope) ==================================== "); 
        for( Enumeration en = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE) ; en.hasMoreElements() ; ){
            String str = (String) en.nextElement();
            if ( str.equalsIgnoreCase("k_request_handle")) continue;
            m_logger.debug( "Attr [" + str + "] : " + pageContext.getAttribute(str,PageContext.SESSION_SCOPE));
        }
        m_logger.debug("======================== END PageContext(Session Scope) ================================ "); 
        m_logger.debug("======================== PageContext(Request Scope) ==================================== "); 
        for( Enumeration en = pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE) ; en.hasMoreElements() ; ){
            String str = (String) en.nextElement();
            if ( str.equalsIgnoreCase("k_request_handle")) continue;
            m_logger.debug( "Attr [" + str + "] : " + pageContext.getAttribute(str,PageContext.REQUEST_SCOPE));
        }
        m_logger.debug("======================== END PageContext(Request Scope) ================================ "); 
        
    }*/
    
    public static void printRequestProperties(HttpServletRequest request){
        
        m_logger.debug("getRequestURL            " + request.getRequestURL());
        m_logger.debug("getRequestURI            " + request.getRequestURI());
        m_logger.debug("getRequestedSessionId    " + request.getRequestedSessionId());
        m_logger.debug("getRemoteAddr()          " + request.getRemoteAddr());
        m_logger.debug("getRemoteHost()          " + request.getRemoteHost());
        m_logger.debug("getServerName()          " + request.getServerName());
        m_logger.debug("getServletPath()         " + request.getServletPath());
        m_logger.debug("getQueryString()         " + request.getQueryString());
        m_logger.debug("getPathInfo()            " + request.getPathInfo());
        m_logger.debug("getPathInfo()            " + request.getCharacterEncoding());
        m_logger.debug("getPathTranslated()      " + request.getPathTranslated());
        m_logger.debug("getRequestedSessionId()  " + request.getRequestedSessionId());
        m_logger.debug("HeaderHost               " + request.getHeader("host"));
        
        RequestUtil.printFormattedParameters(request);
        
        try {
            URL url = new URL(request.getRequestURL().toString());
            
            m_logger.debug("URL getAuthority " + url.getAuthority());
            m_logger.debug("URL getDefaultPort " + url.getPort());
            m_logger.debug("URL getHost " + url.getHost());
            m_logger.debug("URL getFile " + url.getFile());
            m_logger.debug("URL getProtocol " + url.getProtocol());
            m_logger.debug("URL getQuery " + url.getQuery());
            
        }
        catch (MalformedURLException e3) {
            m_logger.error("",e3);
        }

    }    

    public static void printRequestHeader(HttpServletRequest request){
        if (!debugHeaderKeys) return;

        Enumeration enu = request.getHeaderNames();
        while(enu.hasMoreElements()) {
            String headerName = (String) enu.nextElement();
            m_logger.debug("HEADER >>" + headerName + ":" + request.getHeader(headerName));
        }
    }    
    public static void printRequest(HttpServletRequest request){
        if (!debugRequestKeys) return;

        m_logger.debug("======================== Request ==================================== "); 

        for( Enumeration en = request.getAttributeNames() ; en.hasMoreElements() ; ){
            String str = (String) en.nextElement();
            m_logger.debug( "Attri [" + str + "] : " + request.getAttribute(str));
        }
        
        for( Enumeration en = request.getParameterNames() ; en.hasMoreElements() ; ){
            String str = (String) en.nextElement();
            m_logger.debug( "Param [" + str + "] : " + request.getParameter(str));
        }
        
        m_logger.debug("======================== END Request ==================================== "); 
         
     }
     
    
    public static void printSessionKeys(HttpSession session) {
        printSessionKeys(session, false);
    }    
    public static void printSessionKeys(HttpSession session, boolean appBasedOnly) {

        if (!debugSessionKeys) return;
        
        m_logger.debug("=================== Session Keys ===================");
        
        Enumeration keysEnum = session.getAttributeNames();
        
        while(keysEnum.hasMoreElements()) {
            
            Object sessionObj = keysEnum.nextElement();
            String key = sessionObj.toString();
            
            if ( !appBasedOnly || (appBasedOnly && key.startsWith("k_") )  ){

                if ( !key.equalsIgnoreCase("k_request_handle"))
                    m_logger.debug("SESSION KEY :" + key + "=" + session.getAttribute(key));
            } 
        }
        m_logger.debug("=================== END Session Keys ===================");
    }
    

    public static void main(String[] args) {
        
        System.out.println(WebUtil.displayDate(new Date()));
        System.out.println(WebUtil.displayDate(new Date(), WebUtil.DATE_FORMAT_DK));
        System.out.println(WebUtil.displayDate(new Date(), WebUtil.DATE_FORMAT_EU));
        System.out.println(WebUtil.displayDate(new Date(), WebUtil.DATE_FORMAT_MEDIUM));
        System.out.println(WebUtil.displayDate(new Date(), WebUtil.DATE_FORMAT_LONG));
        
        System.out.println(WebUtil.displayDateTime(new Date(), 0));
        System.out.println(WebUtil.displayDateTime(new Date(), WebUtil.DATE_FORMAT_DK));
        System.out.println(WebUtil.displayDateTime(new Date(), WebUtil.DATE_FORMAT_EU));
        System.out.println(WebUtil.displayDateTime(new Date(), WebUtil.DATE_FORMAT_MEDIUM));
        System.out.println(WebUtil.displayDateTime(new Date(), WebUtil.DATE_FORMAT_LONG));
        
    }
    
}
