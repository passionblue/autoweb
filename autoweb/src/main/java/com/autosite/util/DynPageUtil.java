package com.autosite.util;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.autosite.db.Page;
import com.autosite.db.Site;
import com.autosite.ds.PageDS;
import com.autosite.ds.SiteDS;
import com.jtrend.util.WebParamUtil;

public class DynPageUtil {

    public static long getCurrentPageId(HttpSession session, long siteId){
        String pageName = (String) session.getAttribute("k_page_name");
        Page dynPage = PageDS.getInstance().getBySiteIdPageName(siteId, pageName);
        long pageId  = (dynPage!=null?dynPage.getId():0);
        
        return pageId;
    }
    public static Page getCurrentPage(HttpServletRequest request){
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        return getCurrentPage(request, site == null?0:site.getId());

    }
    public static Page getCurrentPage(HttpServletRequest request, long siteId){

        if ( request.getAttribute("k_page_obj") != null){
            return (Page) request.getAttribute("k_page_obj");
        }

        HttpSession session = request.getSession();

        if ( session.getAttribute("k_page_obj") != null){
            return (Page) session.getAttribute("k_page_obj");
        }

        long dynPageId = WebParamUtil.getLongValue( (String) session.getAttribute("k_current_dyn_page"));
        Page dynPage = PageDS.getInstance().getById(dynPageId);
        
        if ( dynPage != null)
            return dynPage;
        

        // With Page Name
        String pageName = (String) request.getAttribute("k_page_name");
        
        if ( pageName != null) { 
            dynPage = PageDS.getInstance().getBySiteIdPageName(siteId, pageName);
            if ( dynPage != null ) return dynPage;
        }
        
        pageName = (String) session.getAttribute("k_page_name");
        if ( pageName != null) { 
            dynPage = PageDS.getInstance().getBySiteIdPageName(siteId, pageName);
            if ( dynPage != null ) return dynPage;
        }
        
        return null;
    }
    
    
    public static void removeCurrentPage(HttpServletRequest request, long siteId){
        HttpSession session = request.getSession();
        session.removeAttribute("k_page_obj");
        request.removeAttribute("k_page_obj");
        session.removeAttribute("k_page_name");
        request.removeAttribute("k_page_name");
    }
    
    
    public static void setCurrentPage(HttpServletRequest request, long siteId){
        HttpSession session = request.getSession();

        session.removeAttribute("k_page_obj");
        session.removeAttribute("k_page_name");
        request.removeAttribute("k_page_name");

    }
    
    public static Page createAndSaveDynPage( Site site, String pageName, String pageTitle, String underlyingPage){
        Page page = new Page();

        Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
        
        if ( dynPage != null) return dynPage;
        
        page.setPageName(pageName);
        page.setPageMenuTitle(pageTitle);
        page.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        page.setSiteUrl(site.getSiteUrl());
        page.setSiteId(site.getId());
        page.setUnderlyingPage(underlyingPage); // Better be set configurably
        
        PageDS.getInstance().put(page);

        return page;
    }
    
}
