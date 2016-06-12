package com.autosite.app;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

import org.apache.log4j.Logger;

import com.autosite.SiteConfigTransient;
import com.autosite.db.Page;
import com.autosite.db.PageConfig;
import com.autosite.db.PageStaticConfig;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.ds.BlockListDS;
import com.autosite.ds.PageConfigDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.PageStaticConfigDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.SessionWrapper;
import com.autosite.util.BlockListHandler;
import com.jtrend.session.PageView;
import com.jtrend.session.SessionKey;
import com.jtrend.struts.core.model.ActionInsert;
import com.jtrend.util.WebUtil;

public class AutositeActionInsert implements ActionInsert{

    public void executeBegining(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#AutositeActionInsert.executeBegining()");
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if ( site == null) 
            throw new Exception("site was not found " + request.getServerName());
        
        //-------------------------------------------------------------------------------------------------------------
        // Get Home page. Create one if none. 
        //-----------------------------------------------------------
        Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), "XHOME");
        
        if (dynPage == null ) {
            // Create new page 
            Page page = new Page();
            
            page.setPageName("XHOME");
            page.setPageMenuTitle("XHOME");
            page.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            page.setSiteUrl(request.getServerName());
            page.setSiteId(site.getId());
            page.setUnderlyingPage("home");

            PageDS.getInstance().put(page); 
            m_logger.info("home page created for " + site.getSiteUrl());
        }

        //-------------------------------------------------------------------------------------------------------------
        // Site Object 
        //-------------------------------------------------------------------------------------------------------------
        HttpSession session = request.getSession();
        Site existingSiteInSession = (Site) session.getAttribute("k_site");
        session.setAttribute("k_site", site);
        
        if (existingSiteInSession == null) {
            m_logger.info("Set the fresh site object for this session. site=" + site.getSiteUrl() + " session=" + session.getId());
        } else {
            if ( site.getId() != existingSiteInSession.getId()) {
                m_logger.info("**Chnage the site object for this session. new entered=" + site.getSiteUrl() + " left site=" + existingSiteInSession.getSiteUrl());
            }
        }

        
        //-------------------------------------------------------------------------------------------------------------
        // Site Object 
        //-------------------------------------------------------------------------------------------------------------
        // check if this is dynamic page 
        // set dynpage set to false. Dyn action will set it true if clicked by dyn menu
        session.setAttribute("k_is_dynpage", "false");
        request.setAttribute("k_is_dynpage", "false");
        
        // Query string does not contain all req params. and save the previous one to different attributes 
        // Seems Obsolete
        if ( session.getAttribute("k_prev_uri") != null )
            session.setAttribute("k_prev_uri",  session.getAttribute("k_prev_uri"));
        if ( session.getAttribute("k_prev_query") != null )
            session.setAttribute("k_prev_query", session.getAttribute("k_prev_query"));

        //-------------------------------------------------------------------------------------------------------------
        //
        //-------------------------------------------------------------------------------------------------------------
        String uri = request.getRequestURI();
        String query = request.getQueryString();

        if (uri != null)
            request.setAttribute("k_request_uri", uri);
        
        if (query != null)
            request.setAttribute("k_request_query", query);
        else 
            request.setAttribute("k_request_query", "");
        
    }

    public void executePreExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#AutositeActionInsert.executePreExecute()");
    }
    
    public void executePostExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#AutositeActionInsert.executePostExecute()");
        
        HttpSession session = request.getSession();
        
        //Set view page for HOME
        PageView pageView = (PageView)request.getAttribute(SessionKey.PAGE_VIEW);
        if (pageView == null) { // Originally was in session just in case some old codes put pageView into only on session
            
            pageView = (PageView)session.getAttribute(SessionKey.PAGE_VIEW);
            request.setAttribute("k_view_pageview", pageView);
        }
        
        if ( pageView != null && (pageView.getAlias().startsWith("home.")||  pageView.getAlias().equals("home")) ) {
            session.setAttribute("k_page_name", "XHOME");
            request.setAttribute("k_page_name", "XHOME");

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            if ( site != null) { 
            
                Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), "XHOME");
                if (dynPage != null ) {
                    session.setAttribute("k_current_dyn_page", String.valueOf(dynPage.getId()));
                    m_logger.debug("page id = " + dynPage.getId());
                }
            }
            m_logger.debug("set the dyn page for home");
        }
        
        // set view correct page name in case dyn content page is used for HOME by configuration. 
        if (WebUtil.isTrue((String)session.getAttribute("k_is_home"))){
            session.setAttribute("k_page_name", "XHOME");
            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), "XHOME");
            session.setAttribute("k_page_obj", dynPage);
            session.setAttribute("k_current_dyn_page", String.valueOf(dynPage.getId()));
            m_logger.debug("This is home page");

        }
        
        //Set site config for all
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

        if (siteConfig == null) {    
            siteConfig = SiteConfigDS.getDefaultSiteConfig();  
        }
        
        session.setAttribute("k_site_config", siteConfig);
    }

    public void executeEnding(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#AutositeActionInsert.executeEnding() ---------------------------------------------- dd ");
        HttpSession session = request.getSession();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

        if (siteConfig == null) {
            siteConfig = SiteConfigDS.getDefaultSiteConfig();
        }
        
        //-----------------------------------------------------------
        // set SiteConfigTransient width
        //-----------------------------------------------------------
        SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

        //-----------------------------------------------------------
        // set SiteConfigTransient Menus Visibility
        //-----------------------------------------------------------
        PageView pageView = (PageView) session.getAttribute("k_view_pageview");
        
        //### 1. Check SiteConfig
        siteConfigTrans.setHideMenu(siteConfig.getShowMenuColumn()==0);
        siteConfigTrans.setHideMid(siteConfig.getShowMidColumn()==0);
        siteConfigTrans.setHideAd(siteConfig.getShowAdColumn()==0);
        
        
        //### 2. page specific configs. Two cases. Static configs frm view.properties files. Dynamic configs are db based. 
        
        m_logger.debug("Col visibility menu (Default SiteConfig)=" + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());
        if (pageView != null) { 

            if ( pageView.isDynamicContent() || WebUtil.isTrue((String)request.getAttribute("k_is_dynpage"))) {
                
                String pageName = (String) session.getAttribute("k_page_name");
                Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
                
                if (dynPage != null){
                    PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());
                    if (pageConfig != null){
                        siteConfigTrans.setHideMenu(pageConfig.getHideMenu() == 1);
                        siteConfigTrans.setHideMid(pageConfig.getHideMid() == 1);
                        siteConfigTrans.setHideAd(pageConfig.getHideAd() == 1);
                        m_logger.debug("Col visibility menu(DynPageConfig) " + pageConfig.getId()+" = " + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());
                    } else {
                        if (! pageView.isUseInheritVisual()) {
                            siteConfigTrans.setHideMenu(pageView.isHideMenu());
                            siteConfigTrans.setHideMid(pageView.isHideMid());
                            siteConfigTrans.setHideAd(pageView.isHideAd());
                            m_logger.info("Col visibility menu(PageView)=" + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());
                        } else {
                            m_logger.debug("Using inherited visibility for this page");
                        }
                    }
                } else {
                    m_logger.warn("Page is dynamic content page but could not find Page. alias=" + pageView.getAlias());
                }
                
            } else {
                
                PageStaticConfig pageStaticConfig = PageStaticConfigDS.getInstance().getBySiteIdPageAlias(site.getId(), pageView.getAlias());
                
                if (pageStaticConfig != null){
                    siteConfigTrans.setHideMenu(pageStaticConfig.getHideMenu()==1);
                    siteConfigTrans.setHideMid(pageStaticConfig.getHideMid()==1);
                    siteConfigTrans.setHideAd(pageStaticConfig.getHideAd()==1);
                    m_logger.debug("Col visibility menu(StaticPageConfig)" + pageStaticConfig.getId()+" =" + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());

                } else {
                    if (! pageView.isUseInheritVisual()) {
                        siteConfigTrans.setHideMenu(pageView.isHideMenu());
                        siteConfigTrans.setHideMid(pageView.isHideMid());
                        siteConfigTrans.setHideAd(pageView.isHideAd());
                        m_logger.info("Col visibility menu(PageView)=" + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());
                    } else {
                        m_logger.debug("Using inherited visibility for this page");
                    }
                }
            }

            //### 3. home specific config by site configs
            if  (pageView.getAlias().startsWith("home")){
                siteConfigTrans.setHideMenu(siteConfig.getShowHomeMenu()==0);
                siteConfigTrans.setHideMid(siteConfig.getShowHomeMid()==0);
                siteConfigTrans.setHideAd(siteConfig.getShowHomeAd()==0);
                m_logger.debug("Col visibility menu(HOME From SiteConfig)=" + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());
            }
        }
        
        //### 4 Special layout
        if (WebUtil.isPritintable(request)){
            siteConfigTrans.setHideMenu(true);
            siteConfigTrans.setHideMid(true);
            siteConfigTrans.setHideAd(true);
            m_logger.debug("Col visibility menu(Printable)=" + siteConfigTrans.isHideMenu() + ",mid=" + siteConfigTrans.isHideMid() + ",ad=" + siteConfigTrans.isHideAd());
        }
        
        //-----------------------------------------------------------
        //Save session wrapper in the context but have to refresh here so that it is not reused in the next request
        //-----------------------------------------------------------
        AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
        SessionWrapper wrapper = SessionWrapper.wrapIt(request, site.getId());
        sessionContext.setSessionWrapper(wrapper);
        
        //-----------------------------------------------------------
        // at the begining, it checks only uri. incase it forwards internally, it is not the case so then checks if alias is "dynamic_.."
        //-----------------------------------------------------------
//        if (!wrapper.isDynPage()){
//            PageView view = wrapper.getViewPage();
//            if (view != null){
//                m_logger.info("Check if dynpage with page alias " + view.getAlias());
//                if (view.getAlias().startsWith("dynamic_menu_content")){
//                    session.setAttribute("k_is_dynpage", "true");
//                }
//            }
//        }
        
    }    
    
    public boolean abort(HttpServletRequest request, HttpServletResponse response) {
        
        String remoteAddr = request.getRemoteAddr();
        
        if ( remoteAddr != null && remoteAddr.equals("108.27.45.7")) return false;
        
        
        // check if address is already blocked previously
        if ( BlockListHandler.getInstance().blocked(remoteAddr) ) return true;
        
        // check whether block it from now. 
        
        
        
        if ( request.getRequestURI() != null && request.getRequestURI().indexOf("t_register_simple_add.html") >= 0 ) {
            BlockListHandler.getInstance().addBlock(remoteAddr, false, BlockListHandler.BLOCK_REASON_SPAM_AUTO);
            m_logger.info("Detected spam request(register). Add ip to blocking list " + remoteAddr);
            return true;
        }
        
        if ( request.getRequestURI() != null && request.getRequestURI().indexOf("t_login_form.html") >= 0 ) {
            String testParm = request.getParameter("test");
            if (testParm != null && testParm.length() > 10 ) {
                BlockListHandler.getInstance().addBlock(remoteAddr, false,BlockListHandler.BLOCK_REASON_SPAM_AUTO);
                m_logger.info("Detecgrepcdted spam request(login). Add ip to blocking list " + remoteAddr);
                return true;
            }
        }
        
        return false;
    }

    private static Logger m_logger = Logger.getLogger(AutositeActionInsert.class);
}
