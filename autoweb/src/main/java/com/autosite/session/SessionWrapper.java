package com.autosite.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import javax.servlet.jsp.PageContext;

import com.autosite.db.Page;
import com.autosite.ds.PageDS;
import com.autosite.util.DynPageUtil;
import com.jtrend.session.PageView;
import com.jtrend.session.SessionRequestHandle;
import com.jtrend.util.PageViewUtil;

public class SessionWrapper {

    private HttpSession m_session;
    private HttpServletRequest m_request;
//    private PageContext m_pageContext;

    private PageView m_pageView;
    private AutositeSessionContext m_ctx;
    private Page m_page;
    private long m_siteId;
    private String m_pageName;

    @Deprecated
    public SessionWrapper(HttpSession session, long siteId) {
        m_session = session;
        m_siteId = siteId;
    }

//    public SessionWrapper(HttpServletRequest request, long siteId, PageContext pageContext) {
//        m_request = request;
//        m_session = request.getSession();
//        m_siteId = siteId;
////        m_pageContext = pageContext;
//    }
    public SessionWrapper(HttpServletRequest request, long siteId) {
        m_request = request;
        m_session = request.getSession();
        m_siteId = siteId;
    }

    public HttpSession getSession() {
        return m_session;
    }

    public PageView getViewPage() {
        
        if (m_pageView == null) {
            PageViewUtil.getCurrentView(m_request);
            
            if ( m_request != null && m_request.getAttribute("k_view_pageview")!= null)
                m_pageView = (PageView) m_request.getAttribute("k_view_pageview");
            else
                m_pageView = (PageView) m_session.getAttribute("k_view_pageview");
        }
        
        return m_pageView;
    }
    public String getRequestHandleId(){
        if ( getRequestHandle() != null)
            return String.valueOf(getRequestHandle().getId());
        
        return null;
    }    
    public SessionRequestHandle getRequestHandle(){
        SessionRequestHandle handle = (SessionRequestHandle) m_request.getAttribute("r_handle");
        return handle;
    }

    public AutositeSessionContext getSessionCtx() {
        if (m_ctx == null)
            m_ctx = (AutositeSessionContext) m_session.getAttribute("k_session_context");
        return m_ctx;
    }

    public Page getPage() {
        return DynPageUtil.getCurrentPage(m_request, m_siteId);
//        
//        if (m_page == null) {
//            m_pageName = (String) m_session.getAttribute("k_page_name");
//            m_page = PageDS.getInstance().getBySiteIdPageName(m_siteId, m_pageName);
//        }
//        return m_page;
    }

    
    public boolean isDynMenuClicked(){
        String isDyn = (String) m_request.getAttribute("k_is_dynpage");
        
        if (isDyn !=null && isDyn.equalsIgnoreCase("true"))
            return true;
        return false;

    }
    public boolean isDynPage2(){

        PageView pView = getViewPage();
        if (pView != null) 
            return pView.isDynamicContent();

        return false;
    }
    
    public boolean isHome(){
        String isHome = (String) m_session.getAttribute("k_is_home");
        
        if (isHome !=null && isHome.equalsIgnoreCase("true"))
            return true;
        return false;

    }
//    public static SessionWrapper wrapIt(HttpServletRequest request, long siteId, PageContext pageContext) {
//        
//        SessionWrapper wrp =  (SessionWrapper) request.getAttribute("r_session_wrapper");
//        
//        if ( wrp == null) {
//            wrp = new SessionWrapper(request, siteId);
//            request.setAttribute("r_session_wrapper", wrp);
//        }
//        return wrp;
//    
//    }
    
    public static SessionWrapper wrapIt(HttpServletRequest request, long siteId) {
//        return wrapIt(request, siteId, null);
        
        SessionWrapper wrp =  (SessionWrapper) request.getAttribute("r_session_wrapper");
        
        if ( wrp == null) {
            wrp = new SessionWrapper(request, siteId);
            request.setAttribute("r_session_wrapper", wrp);
        }
        return wrp;        
    }
    
    public static SessionWrapper wrapIt(HttpSession session, long siteId) {
        return new SessionWrapper(session, siteId);
    }
}
