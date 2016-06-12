package com.autosite.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.app.AutoSiteInitiator;
import com.autosite.db.Page;
import com.autosite.db.PageConfig;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.StyleConfig;
import com.autosite.db.StyleSetContent;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.ds.PageConfigDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.PanelDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.ds.StyleSetContentDS;
import com.autosite.util.StyleConfigUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class PageScriptsServlet  extends AbstractAutositeServlet {
    
    protected boolean m_debug = AutositeGlobals.m_debug;

    public PageScriptsServlet() 
    {
        new AutoSiteInitiator().init();
        
        siteDS = SiteDS.getInstance();
        siteConfigDS = SiteConfigDS.getInstance();
        panelDS = PanelDS.getInstance();
        linkStyleConfigDS = LinkStyleConfigDS.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        m_logger.debug("##################################################################################################################");
        m_logger.debug("################################ START ##########################################################################");
        m_logger.debug("##################################################################################################################");

        printHeaders(request);
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            m_logger.info("Site object not returned for " + request.getServerName());
            return;
        }

        SiteConfig siteConfig = siteConfigDS.getSiteConfigBySiteId(site.getId());
        if (siteConfig == null) 
            siteConfig = siteConfigDS.getDefaultSiteConfig();
        
        String uri = request.getRequestURI();
        if (m_debug) m_logger.debug("URI=" + uri);
        
        //##############################################################################
        // 
        if (uri != null && uri.equals("/pscr/pageManual.css")){
        //##############################################################################

            // script manually typed on web page

            String pageId = request.getParameter("id");
            if (isMissing(pageId)){
                if (m_debug) m_logger.debug("Page Id is missing");
                return;
            }
            
            Page page = (Page) PageDS.getInstance().getById(pageId);
            
            if (page == null) {
                m_logger.warn("Page not found by " + pageId);
                return;
            }
            
            if (page.getSiteId() != site.getId()) {
                m_logger.warn("Site id for page " + page.getSiteId() + " different from " + site.getId() + ". request site=" + site.getSiteUrl());
                return;
            }

            PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(page.getId());
            
            if (pageConfig == null) {
                m_logger.warn("Page config not found for " + page.getId());
                return;
            }
            
            PageConfigDS.objectToLog(pageConfig, m_logger);
            
            try {
                response.setContentType("text/css");
                printPageCss(request, response, pageConfig);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                return;
            }
            
        } 
        
        //#######################################################################################################
        // script pointed by style id of pageConfig
        // /pscr/pageSctyle.css?id=xxxx
        //#######################################################################################################
        
        if (uri != null && uri.equals("/pscr/pageStyle.css")){

            String pageId = request.getParameter("id");
            if (isMissing(pageId)){
                if (m_debug) m_logger.debug("Page Id is missing");
                return;
            }
            
            Page page = (Page) PageDS.getInstance().getById(pageId);
            
            if (page == null) {
                m_logger.warn("Page not found by " + pageId);
                return;
            }
            
            if (page.getSiteId() != site.getId()) {
                m_logger.warn("Site id for page " + page.getSiteId() + " different from " + site.getId() + ". request site=" + site.getSiteUrl());
                return;
            }

            PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(page.getId());
            
            if (pageConfig == null) {
                m_logger.warn("Page config not found for " + page.getId());
                return;
            }
            
            PageConfigDS.objectToLog(pageConfig, m_logger);
            
            try {
                response.setContentType("text/css");
                printPageStyle(request, response, pageConfig, siteConfig);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                return;
            }
            
        } 

        //##############################################################################
        // manually typed javascript on webpage
        //
        //##############################################################################
        if (uri != null && uri.equals("/pscr/page.js")){

            String pageId = request.getParameter("id");
            if (isMissing(pageId)){
                if (m_debug) m_logger.debug("Page Id is missing");
                return;
            }
            
            Page page = (Page) PageDS.getInstance().getById(pageId);
            
            if (page == null) {
                m_logger.warn("Page not found by " + pageId);
                return;
            }
            
            if (page.getSiteId() != site.getId()) {
                m_logger.warn("Site id for page " + page.getSiteId() + " different from " + site.getId() + ". request site=" + site.getSiteUrl());
                return;
            }

            PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(page.getId());
            
            if (pageConfig == null) {
                m_logger.warn("Page config not found for " + page.getId());
                return;
            }
            
            PageConfigDS.objectToLog(pageConfig, m_logger);
            
            try {
                response.setContentType("text/javascript");
                printPageScript(request, response, pageConfig);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                return;
            }
            
        } 
        
        //##############################################################################
        // 
        if (uri != null && uri.equals("/pscr/cntStyle.css")){

            String styleKey = "__STYLE_CONTENT_LIST_SUBJECT-" + site.getId();
            
            String cntStyleType = request.getParameter("type");
            if (isMissing(cntStyleType)){
                if (m_debug) m_logger.debug("Page Id is missing");
                return;
            }
            response.setContentType("text/css");
            PrintWriter out = response.getWriter();
            
            if (cntStyleType.equals("cntList")) {
            
                styleKey = "__STYLE_CONTENT_LIST_SUBJECT-" + site.getId();
                StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(styleKey);
                
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentSubjectStyle();
                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( "cntListContSubject", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Subject [" + styleKey + "] " +pageStyleStr );
                }
                
                styleKey = "__STYLE_CONTENT_LIST_DATA-" + site.getId();
                styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(styleKey);
    
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentDataStyle();
                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( "cntListContData", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Data [" + styleKey + "] " +pageStyleStr );
                }

            } else if (cntStyleType.equals("cntSingle")) {
                styleKey = "__STYLE_CONTENT_SINGLE_PAGE_SUBJECT-" + site.getId();
                StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(styleKey);
                
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentSubjectStyle();
                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( "cntSingleContSubject", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Subject [" + styleKey + "] " +pageStyleStr );
                }
                
                styleKey = "__STYLE_CONTENT_SINGLE_PAGE_DATA-" + site.getId();
                styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(styleKey);
    
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentDataStyle();
                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( "cntSingleContData", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Data [" + styleKey + "] " +pageStyleStr );
                }
                
            }
            
        } 
        

        //##############################################################################
        // 
        if (uri != null && uri.equals("/pscr/cntStyleSets.css")){


            List contentStyleSets = StyleSetContentDS.getInstance().getBySiteId(site.getId());
            response.setContentType("text/css");
            PrintWriter out = response.getWriter();
            
            
            for (Iterator iterator = contentStyleSets.iterator(); iterator.hasNext();) {
                StyleSetContent ssc = (StyleSetContent) iterator.next();
                

                // Content List Subject Style
                StyleConfig styleConfig = StyleConfigDS.getInstance().getById(ssc.getListSubjectStyleId());
                
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentSubjectStyle();

                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( ssc.getIdPrefix() + "ListContSubject", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Subject [" + ssc.getIdPrefix() + "ListContSubject" + "] " +pageStyleStr );
                }
                
                // Content List Data Style
                styleConfig = StyleConfigDS.getInstance().getById(ssc.getListDataStyleId());
                
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentDataStyle();

                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( ssc.getIdPrefix() + "ListContData", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Subject [" + ssc.getIdPrefix() + "ListContData" + "] " +pageStyleStr );
                }

                // Content Subject Style
                styleConfig = StyleConfigDS.getInstance().getById(ssc.getSubjectStyleId());
                
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentSubjectStyle();

                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( ssc.getIdPrefix() + "SingleContSubject", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Subject [" + ssc.getIdPrefix() + "SingleContSubject" + "] " +pageStyleStr );
                }
 
                // Content Data Style
                styleConfig = StyleConfigDS.getInstance().getById(ssc.getDataStyleId());
                
                if (styleConfig == null) styleConfig = StyleConfigUtil.getDefaultContentDataStyle();

                if (styleConfig != null && siteConfig != null ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( ssc.getIdPrefix() + "SingleContData", styleConfig, siteConfig);
                    out.println(pageStyleStr);
                    m_logger.debug("Content Subject [" + ssc.getIdPrefix() + "SingleContData" + "] " +pageStyleStr );
                }
            }
        } 
        
        
        //##############################################################################
        // 
        if (uri != null && uri.equals("/pscr/customStyle.css")){
            response.setContentType("text/css");
            PrintWriter out = response.getWriter();

            List list = StyleConfigDS.getInstance().getBySiteIdToStyleUseList(site.getId(), StyleConfigUtil.STYLE_USE_CUSTOM); 

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                StyleConfig styleConfig = (StyleConfig) iterator.next();
                
                if (WebUtil.isNotNull(styleConfig.getIdClass()) ){
                    String pageStyleStr = StyleUtil.makeCustomStyle( styleConfig.getIdClass(), styleConfig, siteConfig, !WebParamUtil.getBooleanValue(styleConfig.getIsId()));
                    out.print(pageStyleStr);
                    out.print("\n");
                    
                    m_logger.debug("Custom Styles [" + styleConfig.getIdClass() + "] " +pageStyleStr );
                }
            }
        }        

        
        //##############################################################################
        // 
        if (uri != null && uri.equals("/pscr/anylinkMenuCnt.js")){
            response.setContentType("text/javascript");
            PrintWriter out = response.getWriter();
            
            out.print("var configMenu={divclass:'anylinkmenu', inlinestyle:'', linktarget:''}\n"); 
            out.print("configMenu.items=[");
            out.print("    [\"Dynamic Drive\", \"http://www.dynamicdrive.com/\"],");
            out.print("    [\"CSS Drive\", \"http://www.cssdrive.com/\"],");
            out.print("    [\"JavaScript Kit\", \"http://www.javascriptkit.com/\"],");
            out.print("    [\"Coding Forums\", \"http://www.codingforums.com/\"],");
            out.print("    [\"JavaScript Reference\", \"http://www.javascriptkit.com/jsref/\"]"); 
            out.print("]");
            
            
            out.print("\n");
        }        
        
        m_logger.debug("################################ END ##########################################################################");
    }
    
    private void printPageCss(HttpServletRequest request, HttpServletResponse response, PageConfig pageConfig) throws Exception {
        PrintWriter out = response.getWriter();

        if ( !WebUtil.isNull(pageConfig.getPageCss()))
            out.print(pageConfig.getPageCss());

    }

    private void printPageScript(HttpServletRequest request, HttpServletResponse response, PageConfig pageConfig) throws Exception {
        PrintWriter out = response.getWriter();

        if ( !WebUtil.isNull(pageConfig.getPageScript()))
            out.print(pageConfig.getPageScript());

    }
    
    private void printPageStyle(HttpServletRequest request, HttpServletResponse response, PageConfig pageConfig, SiteConfig siteConfig) throws Exception {
        PrintWriter out = response.getWriter();
        
        StyleConfig styleConfig = StyleConfigDS.getInstance().getById(pageConfig.getStyleId());
        if (styleConfig != null && siteConfig != null ){
            String pageStyleStr = StyleUtil.makeCustomStyle( "pageStyle", 10 , styleConfig, siteConfig);
            out.print(pageStyleStr);
        }
        
    }
    
    
    private static Logger m_logger = Logger.getLogger(PageScriptsServlet.class);
    
    
    protected SiteDS siteDS;
    protected SiteConfigDS siteConfigDS;
    protected PanelDS panelDS;
    protected LinkStyleConfigDS linkStyleConfigDS;
}
