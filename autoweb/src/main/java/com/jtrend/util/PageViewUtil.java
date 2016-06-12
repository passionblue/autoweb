package com.jtrend.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.session.PageView;

public class PageViewUtil {

    
    public static final int PAGE_VIEW_TYPE_DEFAULT = 0;
    public static final int PAGE_VIEW_TYPE_SUMMARY = 1;
    public static final int PAGE_VIEW_TYPE_TITLEONLY = 2;
    
    public static String addCurrentViewAsInternalForward(HttpServletRequest request, String url){
        PageView pageView = getCurrentView(request);
        
        if (pageView != null && pageView.getAlias() != null && !pageView.getAlias().isEmpty()) {
            
            if ( url.indexOf("?") >= 0 ) {
                return url + "&fwdTo=" + pageView.getAlias();
                
            } else {
                return url + "?fwdTo=" + pageView.getAlias();
            }
        }
        return url;
    }
    
    public static String getCurrentViewAlias(HttpServletRequest request){
        PageView pageView = getCurrentView(request);
        
        if (pageView != null && pageView.getAlias() != null && !pageView.getAlias().isEmpty()) {
            return pageView.getAlias();
        }
        return "";
    }

    public static String getCurrentViewPage(HttpServletRequest request){
        PageView pageView = getCurrentView(request);
        
        if (pageView != null && pageView.getContentPage() != null && !pageView.getContentPage().isEmpty()) {
            return pageView.getContentPage();
        }
        return null;
    }

    public static PageView getCurrentView (HttpServletRequest request){
        PageView pageView = (PageView) request.getAttribute("k_view_pageview");
        
        if ( pageView != null) return pageView;
        
        HttpSession session = request.getSession();
        
        return (PageView) session.getAttribute("k_view_pageview");
        
        
        
    }
    
}
