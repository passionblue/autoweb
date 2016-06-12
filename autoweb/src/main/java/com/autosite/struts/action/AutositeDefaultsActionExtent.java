package com.autosite.struts.action;

import java.util.Date;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeDefaults;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.AutositeDefaultsDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.AutositeDefaultsForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class AutositeDefaultsActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeDefaultsAction#xtent.beforeAdd");		
        AutositeDefaults _AutositeDefaults = (AutositeDefaults)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#AutositeDefaultsAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        AutositeDefaults _AutositeDefaults = (AutositeDefaults)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeDefaultsAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        AutositeDefaults _AutositeDefaults = (AutositeDefaults)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeDefaultsAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        AutositeDefaults _AutositeDefaults = (AutositeDefaults)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeDefaultsAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        AutositeDefaults _AutositeDefaults = (AutositeDefaults)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#AutositeDefaultsAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        AutositeDefaults _AutositeDefaults = (AutositeDefaults)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( AutositeDefaultsActionExtent.class);
    
}
