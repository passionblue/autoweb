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

import com.autosite.db.PanelLinkStyle;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PanelLinkStyleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PanelLinkStyleForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class PanelLinkStyleActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelLinkStyleAction#xtent.beforeAdd");		
        PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PanelLinkStyleAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelLinkStyleAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelLinkStyleAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelLinkStyleAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelLinkStyleAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( PanelLinkStyleActionExtent.class);
    
}
