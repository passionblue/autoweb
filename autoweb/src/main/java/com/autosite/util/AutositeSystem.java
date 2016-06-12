package com.autosite.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.jtrend.util.WebParamUtil;
import com.seox.util.SeoxLogger;

public class AutositeSystem {

    public final static String IssueTypeInvalidRequest = "InvalidRequest";
    public final static String IssueTypeSystemError = "SystemError";


    public final static int ReportedBySystem = 0;
    public final static int ReportedBySystemAction = 1;
    public final static int ReportedBySystemView = 2;


    public static void reportInvalidRequest(long siteId, String description, Map params){
        report(IssueTypeInvalidRequest, ReportedBySystem, siteId, description, params);
    }

    public static void report(String issueType, int reportedBy, long siteId, String description, HttpServletRequest request){
        Map params = createBasicParams(request);
        report(issueType, reportedBy, siteId, description, params);
    }
    public static void report(String issueType, int reportedBy, long siteId, String description, Map params){
        StringBuffer buf = new StringBuffer();
        
        String siteName = "*UNKNOWN*";
        Site site = (Site)SiteDS.getInstance().getById(""+siteId);
        if (site != null) 
            siteName = site.getSiteUrl();
        
        String paramsStr = AutositeSystem.makeString(params, ",");
        buf.append(issueType).append("|").append(reportedBy).append("|").append("SITE=").append(siteName).append("|").append(description).append("\n");
        buf.append("\t").append("PARAMS=").append(paramsStr);
        SeoxLogger.filelog("report.log", buf.toString());
    }
    
    public static Map createBasicParams(HttpServletRequest request){
        
        Map ret = new HashMap();
        
        String referer = WebParamUtil.getStringValue(request.getHeader("referer"));
        String url = WebParamUtil.getStringValue(request.getRequestURI());
        String uri = WebParamUtil.getStringValue(request.getRequestURI());
        String query = WebParamUtil.getStringValue(request.getQueryString());
        String remoteAddress = WebParamUtil.getStringValue(request.getRemoteAddr());
        String remoteHost = WebParamUtil.getStringValue(request.getRemoteHost());
        String serverName = WebParamUtil.getStringValue(request.getServerName());

        ret.put("referer", referer);
        ret.put("url", url);
        ret.put("uri", uri);
        ret.put("query", query);
        ret.put("remoteAddress", remoteAddress);
        ret.put("remoteHost", remoteHost);
        ret.put("server", serverName);
        
        return ret;
    }
    
    public static String makeString(Map request, String separater){
        
        if (request == null) return "";
        
        StringBuffer buf = new StringBuffer();
        for (Iterator iterator = request.keySet().iterator(); iterator.hasNext();) {

            String key = (String) iterator.next();
            String value = (String) request.get(key);
            
            buf.append(key).append("=").append(value).append(separater);
        }
        return buf.toString();
    }
    
}
