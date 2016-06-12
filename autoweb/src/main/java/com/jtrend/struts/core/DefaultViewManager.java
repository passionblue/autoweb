/*
 * Created on Nov 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.struts.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.jtrend.struts.core.viewproc.ViewProcFactory;
import com.jtrend.util.WebUtil;
import com.seox.util.PropertiesLoader;

public class DefaultViewManager implements ViewManager {


    private Map m_pages = new ConcurrentHashMap();
    private List m_pageList = new CopyOnWriteArrayList();
    private List m_pageObjectList = new CopyOnWriteArrayList();
    
    private Map m_temparayAlt = new ConcurrentHashMap();
    private String m_forceAll = null;
    private boolean m_mobileView = false;
    
    protected ViewExtent m_viewExtent;

    private static Logger m_logger = Logger.getLogger(DefaultViewManager.class);
    private static ViewManager m_instance = new DefaultViewManager(false);;
    private static ViewManager m_instanceMobile = new DefaultViewManager(true);;
    private static ViewManager m_instanceTablet = new DefaultViewManager(true);;
    
    public static  ViewManager getInstance() {
        return m_instance;
    }

    public static  ViewManager getInstanceMobile() {
        return m_instanceMobile;
    }

    public static  ViewManager getInstanceTablet() {
        return m_instanceTablet;
    }

    protected DefaultViewManager() {
        m_mobileView = false;
        initPageView();
    }

    protected DefaultViewManager(boolean mobile) {
        m_mobileView = mobile;

        if ( m_mobileView) 
            initPageViewMobile();
        else 
            initPageView();
    }
    
    public void initPageViewMobile() {
        String file = PropertiesLoader.getInstance().getProperty("app.cfg.view_manager_mobile");
        initPageView(file);
    }

    public void initPageView() {
        String file = PropertiesLoader.getInstance().getProperty("app.cfg.view_manager");
        initPageView(file);
    }
    
    public void initPageView(String file) {

        m_logger.debug("## Loading view properties from " + file);
        
        InputStream ins = null;
        try {
            ins = PropertiesLoader.class.getResourceAsStream(file);
            if (ins == null)
                ins = PropertiesLoader.class.getResourceAsStream("/" + file);

            if (ins == null) {
                m_logger.error("## Could not load the file. file=" + file, new Exception());
                return;
            }

            m_logger.info("=======================================================================================================");
            m_logger.info("= LOADING VIEWS from " + file);
            m_logger.info("=======================================================================================================");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(ins));
            while (true) {
                String line = in.readLine();
                //m_logger.debug("######################### " + line);
                if (line == null)
                    break;

                int pos = line.indexOf("=");
                if (pos < 0) {
                    //m_logger.warn("Line corrupted = " + line);
                    continue;
                }

                if (line.trim().startsWith("#")) {
                    continue;
                }

                String pageAlias = line.substring(0, pos);
                String pageProp = line.substring(pos + 1);

                if (pageAlias.equals("provider")) {
                    setViewExtent(pageProp);
                    continue;
                }

                PageView pageView = makePageViewFromPropLine(pageAlias, pageProp);

                m_logger.debug("PageView is added alias=" + pageAlias + ",PageView=" + pageView);

                m_pages.put(pageAlias, pageView);
                m_pageList.add(pageAlias);
                m_pageObjectList.add(pageView);
            } //while
            
            m_logger.info("=======================================================================================================");
            m_logger.info("= ENDED LOADIND VIEWS from " + file);
            m_logger.info("=======================================================================================================");

        }
        catch (Exception e) {
            Logger.getLogger(getClass()).warn("Exception while reading propreties from file " + file + " .Default values loaded", e);
        }
        finally {
            try {
                if (ins != null)
                    ins.close();
            }
            catch (Exception e) {
            }
        }

    }

    protected PageView makePageViewFromPropLine(String alias, String line) {

        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        String displayTitle = tokenizer.nextToken();
        String contentPage = tokenizer.nextToken();
        String sitemenuPage = tokenizer.nextToken();
        String sideAdPage = tokenizer.nextToken();
        String errorPage = tokenizer.nextToken();
        String loginRequired = tokenizer.nextToken();
        String procClass = tokenizer.nextToken();
        
        String dynamicContet = "false";
        String hideMenu = "false";
        String hideMid = "true";
        String hideAd = "false";
        String useInheritVisibility = "true";
        String superAdminOnly = "false";
        String disallowDirectView = "false";
        
        if (tokenizer.hasMoreTokens())
            dynamicContet = tokenizer.nextToken();
        if (tokenizer.hasMoreTokens())
            hideMenu = tokenizer.nextToken();
        if (tokenizer.hasMoreTokens())
            hideMid = tokenizer.nextToken();
        if (tokenizer.hasMoreTokens())
            hideAd = tokenizer.nextToken();
        if (tokenizer.hasMoreTokens())
            useInheritVisibility = tokenizer.nextToken();
        if (tokenizer.hasMoreTokens())
            superAdminOnly = tokenizer.nextToken();
        if (tokenizer.hasMoreTokens())
            disallowDirectView = tokenizer.nextToken();
        

        boolean loginReq = false;

        if (displayTitle.trim().equalsIgnoreCase("NULL"))
            displayTitle = null;
        if (contentPage.trim().equalsIgnoreCase("NULL"))
            contentPage = null;
        if (sitemenuPage.trim().equalsIgnoreCase("NULL"))
            sitemenuPage = null;
        if (sideAdPage.trim().equalsIgnoreCase("NULL"))
            sideAdPage = null;
        if (errorPage.trim().equalsIgnoreCase("NULL"))
            errorPage = null;
        if (loginRequired.trim().equalsIgnoreCase("true"))
            loginReq = true;
        if (procClass.trim().equalsIgnoreCase("NULL"))
            procClass = null;

        if (procClass != null && !procClass.equalsIgnoreCase("NULL")) {

            try {
                Class theClass = Class.forName(procClass);
                ViewProc viewProc = (ViewProc) theClass.newInstance();
                ViewProcFactory.getInstance().registerViewProc(alias, viewProc);
                m_logger.info("View proc  for " + procClass + " registered. class=" + viewProc.getName());
            }
            catch (Exception e) {
                m_logger.error("", e);
            }
        }

        return new PageView(displayTitle,
                            alias, 
                            contentPage, 
                            sitemenuPage, 
                            sideAdPage, 
                            errorPage, 
                            loginReq, 
                            dynamicContet.equalsIgnoreCase("true"),
                            hideMenu.equalsIgnoreCase("true"),
                            hideMid.equalsIgnoreCase("true"),
                            hideAd.equalsIgnoreCase("true"), 
                            useInheritVisibility.equalsIgnoreCase("true"), 
                            superAdminOnly.equalsIgnoreCase("true"),
                            disallowDirectView.equalsIgnoreCase("true"));
    }

    protected void setViewExtent(String provider) {
        try {
            Class theClass = Class.forName(provider);
            m_viewExtent = (ViewExtent) theClass.newInstance();
            m_logger.info("View Extent created for " + m_viewExtent.getSiteName() + " from " + provider);
        }
        catch (Exception ex) {
            System.err.println(ex + " Interpreter class must be in class path.");
        }
    }

    // Return PageView object for alias page name, server is needed to get alternative page specific for that server
    public PageView getPageView(String pageAlias, String server) {

        // For Home only at this moment.
        m_logger.debug("Getting getPageView for " + pageAlias);
        if ( pageAlias.equals("home")){
            String extent = m_viewExtent.getAlternateFor(pageAlias, server);
            m_logger.debug("This is home. has extent " + extent);
            
            if ( extent != null) {
                PageView pageView =  (PageView) m_pages.get(pageAlias + "." + extent);
                
                if ( pageView == null) 
                    pageView =  (PageView) m_pages.get(extent);
                
                if (pageView != null) {
                    m_logger.info("PageView returned with alt extent " + extent);
                    return pageView;
                }
            }
        }
        
        // Other pages including home. 
        PageView retPageView = (PageView) m_pages.get(pageAlias);
        String altUrl = m_viewExtent.getAlternateUrl(pageAlias, server);

        if (retPageView != null ){
            retPageView.setTentativeAlt(null);
            if (!WebUtil.isNull(altUrl)) {
                m_logger.info("Tentative page used by extent class for " + pageAlias + " org=" + retPageView.getContentPage() + ", new=" + altUrl);
                
                // pageView in the memory is shread if there is server specific alternative, should return local object. 
                
                PageView newPageView = PageView.copyTo(retPageView);
                newPageView.setTentativeAlt(altUrl);
                retPageView = newPageView;
            }

            // I forgot where this temporary alternatives comes???
            if ( m_temparayAlt.containsKey(pageAlias)) {
                m_logger.info("Tentative page used by temporary map for " + pageAlias + " org=" + retPageView.getContentPage() + ", new=" + m_temparayAlt.get(pageAlias));

                PageView newPageView = PageView.copyTo(retPageView);
                newPageView.setTentativeAlt((String) m_temparayAlt.get(pageAlias));
                retPageView = newPageView;
            }
        } else {
            return null;
        }
        
        return retPageView;
    }
    
    public String getForceAll() {
        return m_forceAll;
    }

    public void setForceAll(String forceAll) {
        m_forceAll = forceAll;
    }
    
    public void addTemporaryAlt(String pageAlias, String page){
        if ( m_pages.containsKey(pageAlias)){
            m_temparayAlt.put(pageAlias, page);
            m_logger.info("Temporary Alt added for " + pageAlias + ", page = " + page);
        }
    }

    public void remoteTemporaryAlt(String pageAlias, String page){
        String removed = (String) m_temparayAlt.remove(pageAlias);
        m_logger.info("Temporary Alt removed for " + pageAlias + ", removed = " + removed);
    }
    
    
    // Return PageView object for alias page name
    public PageView getPageView(String pageAlias) {
        return getPageView(pageAlias, null);
    }

    public List getPageList() {
        return new ArrayList(m_pageList);
    }

    public List getPageObjectList() {
        return new ArrayList(m_pageObjectList);
    }

    
    
    @Override
    public boolean registerView(String actionFrom, String viewDefinition) {
        
        if ( viewDefinition == null) return false;
        
        int pos = viewDefinition.indexOf("=");
        if (pos < 0) {
            m_logger.debug("Corrupted line " + viewDefinition);
            return false;
        }
        String pageAlias = viewDefinition.substring(0, pos);
        String pageProp = viewDefinition.substring(pos + 1);

        PageView pageView = makePageViewFromPropLine(pageAlias, pageProp);

        return registerView(actionFrom, pageView);
    }

    @Override
    public boolean registerView(String actionFrom,PageView pageView) {

        if (m_pages.containsKey(pageView.getAlias())){
            PageView existing = (PageView) m_pages.get(actionFrom);
            m_logger.debug("View page existing (defined from file) So not allowing register from Action " + existing);
            return false;
        }
        
        m_pages.put(pageView.getAlias(), pageView);
        m_pageList.add(pageView.getAlias());
        m_pageObjectList.add(pageView);
        
        m_logger.debug("RegisterViewRuntime:[" + actionFrom + "] PageView Added alias=" + pageView.getAlias() + ",PageView=" + pageView);
        return true;
    }

    public static void main(String[] args) {
        PageView page = DefaultViewManager.getInstance().getPageView("page_static_alt_edit", "www.uxsx.com");
        System.out.println(page);
        
        page = DefaultViewManager.getInstanceMobile().getPageView("home");
        System.out.println(page);
        
    }
    
}
