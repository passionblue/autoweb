/*
 * Created on Nov 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.session;

import java.io.Serializable;

// The class that has all the information that will be used to view on the page.

public class PageView implements Serializable {

    private String m_pageTitle = null;
    private String m_alias = null;
    private String m_content = null;
    private String m_sideMenu = null;
    private String m_sideAd = null;
    private String m_errorPage = null;

    private boolean m_loginRequired;
    private boolean m_dynamicContent = false;
    private boolean m_hideMenu = false;;
    private boolean m_hideMid = true;
    private boolean m_hideAd = false;
    private boolean m_useInheritVisual = true;
    private boolean m_superAdminOnly = false;
    private boolean m_disallowDirectView = false;
    
    private String m_tentativeAlt = null;
    
    public PageView(String alias, String content, String sideMenu, String errorPage) {
        m_alias = alias;
        m_content = content;
        m_sideMenu = sideMenu;
        m_errorPage = errorPage;
        m_loginRequired = true;
    }

    public PageView(String alias, String content, String sideMenu, String errorPage, boolean loginRequired) {
        
        m_alias = alias;
        m_content = content;
        m_sideMenu = sideMenu;
        m_errorPage = errorPage;
        m_loginRequired = loginRequired;
    }

    public PageView(String displayTitle, String alias, String content, String sideMenu, String sideAd, String errorPage, boolean loginRequired, boolean dynamicCont, boolean hideMenu, boolean hideMid, boolean hideAd, boolean useInherit, boolean superAdminOnly, boolean disallowDirectView) {
        m_pageTitle = displayTitle;
        m_alias = alias;
        m_content = content;
        m_sideMenu = sideMenu;
        m_sideAd = sideAd;
        m_errorPage = errorPage;
        m_loginRequired = loginRequired;
        m_dynamicContent = dynamicCont;
        m_hideMenu = hideMenu;
        m_hideMid = hideMid;
        m_hideAd = hideAd;
        m_useInheritVisual = useInherit;
        m_superAdminOnly = superAdminOnly;
        m_disallowDirectView = disallowDirectView;
    }

    
    
    
    public static PageView copyTo(PageView pageView){
        return new PageView( 
                    pageView.m_pageTitle,
                    pageView.m_alias,
                    pageView.m_content,
                    pageView.m_sideMenu,
                    pageView.m_sideAd,
                    pageView.m_errorPage,
                    pageView.m_loginRequired,
                    pageView.m_dynamicContent,
                    pageView.m_hideMenu,
                    pageView.m_hideMid,
                    pageView.m_hideAd,
                    pageView.m_useInheritVisual,
                    pageView.m_superAdminOnly,
                    pageView.m_disallowDirectView);
    }
    
    
    
    public boolean isLoginRequired() {
        return m_loginRequired;
    }
    public void setLoginRequired(boolean loginRequired) {
        m_loginRequired = loginRequired;
    }
    public String getContentPage() {
        if (m_tentativeAlt != null && m_tentativeAlt.length() > 0 )
            return m_tentativeAlt;
        return m_content;
    }
    public void setContent(String content) {
        m_content = content;
    }
    public String getErrorPage() {
        return m_errorPage;
    }
    public void setErrorPage(String errorPage) {
        m_errorPage = errorPage;
    }
    public String getSideMenu() {
        return m_sideMenu;
    }
    public void setSideMenu(String sideMenu) {
        m_sideMenu = sideMenu;
    }

    public String getAlias() {
        return m_alias;
    }
    public boolean isHideMenu() {
        return m_hideMenu;
    }

    public void setHideMenu(boolean hideMenu) {
        m_hideMenu = hideMenu;
    }

    public boolean isHideMid() {
        return m_hideMid;
    }

    public void setHideMid(boolean hideMid) {
        m_hideMid = hideMid;
    }

    public boolean isHideAd() {
        return m_hideAd;
    }

    public void setHideAd(boolean hideAd) {
        m_hideAd = hideAd;
    }

    public String getContent() {
        return m_content;
    }

    public void setAlias(String alias) {
        m_alias = alias;
    }

    public String getTentativeAlt() {
        return m_tentativeAlt;
    }

    public void setTentativeAlt(String tentativeAlt) {
        m_tentativeAlt = tentativeAlt;
    }
    
    public String toString() {
        return "Content=" + m_content +",SideMenu=" + m_sideMenu + ",SideAd=" + m_sideAd + ",Error=" + m_errorPage + ",login=" + m_loginRequired + ",TENTATIVE=" + m_tentativeAlt;
    }
    
    public boolean isDynamicContent() {
        return m_dynamicContent;
    }

    public void setDynamicContent(boolean dynamicContent) {
        m_dynamicContent = dynamicContent;
    }

    public boolean isUseInheritVisual() {
        return m_useInheritVisual;
    }

    public void setUseInheritVisual(boolean useInheritVisual) {
        m_useInheritVisual = useInheritVisual;
    }

    public String getSideAd() {
        return m_sideAd;
    }

    public void setSideAd(String sideAd) {
        m_sideAd = sideAd;
    }

    public boolean isSuperAdminOnly() {
        return m_superAdminOnly;
    }

    public void setSuperAdminOnly(boolean superAdminOnly) {
        m_superAdminOnly = superAdminOnly;
    }
    
    
    public String getPageTitle() {
        return m_pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        m_pageTitle = pageTitle;
    }

    
    
    public boolean isDisallowDirectView() {
        return m_disallowDirectView;
    }

    public void setDisallowDirectView(boolean disallowDirectView) {
        m_disallowDirectView = disallowDirectView;
    }

    //URL from external access
    public String getPageUrl(){
        if (this.isLoginRequired())
            return  "v_" + getAlias() + ".html";
        else 
            return  "t_" + getAlias() + ".html";
    }
    
    public static PageView getDefaultPageView() {
        return new PageView("home", "/jsp/layout/home.jsp", "/jsp/sidemenus/sidemenu_home.jsp", null, false);
    }

}
