package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcAnonymousUserAccount;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcAnonymousUserAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcAnonymousUserAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcAnonymousUserAccountActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousUserAccountAction#xtent.beforeAdd");		
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcAnonymousUserAccountAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousUserAccountAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousUserAccountAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousUserAccountAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAnonymousUserAccountAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount)baseDbOject;
    }

	private EcAnonymousUserAccountDS m_ds = EcAnonymousUserAccountDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcAnonymousUserAccountActionExtent.class);
    
}
