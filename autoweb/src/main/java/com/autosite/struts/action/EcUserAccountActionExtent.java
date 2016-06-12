package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcUserAccount;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcUserAccountDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcUserAccountForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class EcUserAccountActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserAccountAction#xtent.beforeAdd");		
        EcUserAccount _EcUserAccount = (EcUserAccount)baseDbOject;

        if ( WebUtil.isNull(_EcUserAccount.getFirstName())){
            m_logger.debug("firstName is missing in this requres " + EcUserAccountDS.objectToString(_EcUserAccount));
            throw new Exception("firstName is missing.");
        }
        if ( WebUtil.isNull(_EcUserAccount.getLastName())){
            m_logger.debug("lastName is missing in this requres " + EcUserAccountDS.objectToString(_EcUserAccount));
            throw new Exception("lastName is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcUserAccountAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcUserAccount _EcUserAccount = (EcUserAccount)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserAccountAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcUserAccount _EcUserAccount = (EcUserAccount)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserAccountAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcUserAccount _EcUserAccount = (EcUserAccount)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserAccountAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcUserAccount _EcUserAccount = (EcUserAccount)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserAccountAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcUserAccount _EcUserAccount = (EcUserAccount)baseDbOject;
    }

	private EcUserAccountDS m_ds = EcUserAccountDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcUserAccountActionExtent.class);
    
}
