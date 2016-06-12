package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeLoginExtent;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.AutositeLoginExtentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.AutositeLoginExtentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class AutositeLoginExtentActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeLoginExtentAction#xtent.beforeAdd");		
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent)baseDbOject;
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#AutositeLoginExtentAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeLoginExtentAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeLoginExtentAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeLoginExtentAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeLoginExtentAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        AutositeLoginExtent _AutositeLoginExtent = (AutositeLoginExtent)baseDbOject;
    }

	private AutositeLoginExtentDS m_ds = AutositeLoginExtentDS.getInstance();
    private static Logger m_logger = Logger.getLogger( AutositeLoginExtentActionExtent.class);
    
}
