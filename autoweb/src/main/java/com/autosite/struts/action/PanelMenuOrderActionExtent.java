package com.autosite.struts.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PanelMenuOrder;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PanelMenuOrderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PanelMenuOrderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PanelMenuOrderActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelMenuOrderAction#xtent.beforeAdd");		
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PanelMenuOrderAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelMenuOrderAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelMenuOrderAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelMenuOrderAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelMenuOrderAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private PanelMenuOrderDS m_ds = PanelMenuOrderDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PanelMenuOrderActionExtent.class);
    
}
