package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcAutositeUserAccount;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcAutositeUserAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcAutositeUserAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcAutositeUserAccountActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAutositeUserAccountAction#xtent.beforeAdd");		
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount)baseDbOject;

        if ( WebUtil.isNull(_EcAutositeUserAccount.getFirstName())){
            m_logger.debug("firstName is missing in this requres " + EcAutositeUserAccountDS.objectToString(_EcAutositeUserAccount));
            throw new Exception("First Name is missing.");
        }
        if ( WebUtil.isNull(_EcAutositeUserAccount.getLastName())){
            m_logger.debug("lastName is missing in this requres " + EcAutositeUserAccountDS.objectToString(_EcAutositeUserAccount));
            throw new Exception("Last Name is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcAutositeUserAccountAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAutositeUserAccountAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAutositeUserAccountAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAutositeUserAccountAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcAutositeUserAccountAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount)baseDbOject;
    }

	private EcAutositeUserAccountDS m_ds = EcAutositeUserAccountDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcAutositeUserAccountActionExtent.class);
    
}
