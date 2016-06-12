package com.autosite.util;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.ds.AutositeAccountDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;

public class SiteAccountManager {


    protected SiteDS m_siteDS;
    protected AutositeAccountDS m_accountDS;
    
    // Add new site if no site found in DB/Mem
    public Site registerSite(String url) {
        return registerSite(url, true);
    }

    // Add new site if no site found in DB/Mem
    public Site registerSite(String url, boolean returnContentFowardSite) {
        if (url == null ) return null;
        
        url = getCommonUrl(url);
        
        Site ret = (Site) m_siteDS.getSiteByUrl(url);
        
        if (ret == null) {
            ret = new Site();
            ret.setSiteUrl(url);
            ret.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            m_siteDS.put(ret);
            m_logger.info("new Site persisted " + ret.getSiteUrl());
        }
        
        SiteConfig config = SiteConfigDS.getInstance().getSiteConfigBySiteId(ret.getId());
        
        if (!returnContentFowardSite || config == null || config.getContentForwardSite() == null || config.getContentForwardSite().equals("")) 
            return ret;

        String contentForwardUrl = getCommonUrl(config.getContentForwardSite());  
        
        m_logger.info("Content forward site config set to " + contentForwardUrl);

        Site retContentFoward = (Site) m_siteDS.getSiteByUrl(contentForwardUrl);
        
        if ( retContentFoward == null) {
            m_logger.warn("config forward site not found " + contentForwardUrl);
            return ret;
        }
        return retContentFoward;
    }

    public static String getCommonUrl(String url) {
        
        if (url == null) return "NULL";

        String retUrl = url.toLowerCase();

        
        if (retUrl.indexOf("www.") > -1 ) {
            retUrl =  retUrl.substring("www.".length()).toLowerCase();
        }
        
        if ( retUrl.charAt(retUrl.length()-1) == '.') {
            retUrl = retUrl.substring(0, retUrl.length()-1);
        }
        
        return retUrl; 
        //return "uxsx.com"; 
    }
    
    
    private SiteAccountManager(){
        
    }
    
    private static Logger m_logger = Logger.getLogger(DisplayOrderManager.class);
    private static SiteAccountManager m_instance = new SiteAccountManager();;
    public static boolean m_debug = AutositeGlobals.m_debug;
    
    public static AtomicInteger m_idx = new AtomicInteger();
    
    public static String createAccountNum(){
        return System.currentTimeMillis() + "-" + m_idx.incrementAndGet();
    }

    public static SiteAccountManager getInstance() {
        return m_instance;
    }
    
}
