package com.jtrend.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.session.PageView;

public class HttpUtil {

    
    public String extractOrgRequest(HttpSession session){

        String ret = "";
        
        String uri = (String)session.getAttribute("k_org_uri");
        String query = (String)session.getAttribute("k_org_query");

        
        if (WebUtil.isNotNull(uri)) ret += uri;
        
        if (WebUtil.isNotNull(query)) {
            ret += "?" + query;
        }

        
        return ret;
    }
    
    public static String append(HttpServletRequest request, String baseUrl){
        
        PageView pageView = (PageView) request.getAttribute("k_view_pageview");
        
        if ( pageView != null) {
            baseUrl += "&fromto=" +pageView.getAlias();
        }

        return baseUrl;
    }
}
