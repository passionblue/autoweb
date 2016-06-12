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

import com.autosite.db.DevTable;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.DevTableDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.DevTableForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class DevTableActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#DevTableAction#xtent.beforeAdd");		
        DevTable _DevTable = (DevTable)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#DevTableAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        DevTable _DevTable = (DevTable)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#DevTableAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        DevTable _DevTable = (DevTable)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#DevTableAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        DevTable _DevTable = (DevTable)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#DevTableAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        DevTable _DevTable = (DevTable)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#DevTableAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        DevTable _DevTable = (DevTable)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private DevTableDS m_ds = DevTableDS.getInstance();
    private static Logger m_logger = Logger.getLogger( DevTableActionExtent.class);
    
}
