package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.SiteRegAccountServiceInfo;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.session.SiteRegStore;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class SiteRegAccountServiceInfoActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegAccountServiceInfoAction#xtent.beforeAdd");		
        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = (SiteRegAccountServiceInfo)baseDbOject;
        HttpSession session = request.getSession();

        SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
        if (siteRegStore == null) {
            m_logger.info("Site Reg Store is missing in session. Forwarding the site to start page");
            throw new ActionExtentException("session expires. Please start over the registration", "site_reg_start");
        }
        
        siteRegStore.setAccServiceInfo(_SiteRegAccountServiceInfo);
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#SiteRegAccountServiceInfoAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = (SiteRegAccountServiceInfo)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegAccountServiceInfoAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = (SiteRegAccountServiceInfo)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegAccountServiceInfoAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = (SiteRegAccountServiceInfo)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegAccountServiceInfoAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = (SiteRegAccountServiceInfo)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#SiteRegAccountServiceInfoAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        SiteRegAccountServiceInfo _SiteRegAccountServiceInfo = (SiteRegAccountServiceInfo)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( SiteRegAccountServiceInfoActionExtent.class);
    
}
