package com.autosite.session;

import javax.servlet.http.HttpServletRequest;

import com.autosite.SiteConfigTransient;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;

public class AutositeRequestWrapper {

    private HttpServletRequest m_request;
    
    public AutositeRequestWrapper(HttpServletRequest request){
        m_request = request;
        
        
        
        
        
    }

    public HttpServletRequest getRequest() {
        return m_request;
    }

    public void setRequest(HttpServletRequest request) {
        m_request = request;
    }
    
    
    public SiteConfigTransient getSiteConfigTransient(){
        Site site = getSite();
        SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());
        return siteConfigTrans;
    }
    
    public Site getSite(){
        return SiteDS.getInstance().registerSite(m_request.getServerName());
    }

    public SiteConfig getSiteConfig(){
        
        Site site = getSite();
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

        if (siteConfig !=null) {    
        } else {
            siteConfig = SiteConfigDS.getDefaultSiteConfig(); 
        }
        
        return siteConfig; 
    }
    
}
