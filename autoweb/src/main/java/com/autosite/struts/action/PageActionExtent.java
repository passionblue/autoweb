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

import com.autosite.db.Page;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PageDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PageForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PageActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageAction#xtent.beforeAdd");		
        Page _Page = (Page)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PageAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        Page _Page = (Page)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        Page _Page = (Page)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        Page _Page = (Page)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        Page _Page = (Page)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        Page _Page = (Page)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( PageActionExtent.class);
    
}
