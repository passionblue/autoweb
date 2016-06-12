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

import com.autosite.db.TestDataDepot;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.TestDataDepotDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.TestDataDepotForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class TestDataDepotActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestDataDepotAction#xtent.beforeAdd");		
        TestDataDepot _TestDataDepot = (TestDataDepot)baseDbOject;

        if ( WebUtil.isNull(_TestDataDepot.getTitle())){
            m_logger.debug("title is missing in this requres " + TestDataDepotDS.objectToString(_TestDataDepot));
            throw new ActionExtentException("Title is missing.", "");
        }
        if ( WebUtil.isNull(_TestDataDepot.getData())){
            m_logger.debug("data is missing in this requres " + TestDataDepotDS.objectToString(_TestDataDepot));
            throw new ActionExtentException("Data is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#TestDataDepotAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        TestDataDepot _TestDataDepot = (TestDataDepot)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestDataDepotAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        TestDataDepot _TestDataDepot = (TestDataDepot)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestDataDepotAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        TestDataDepot _TestDataDepot = (TestDataDepot)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestDataDepotAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        TestDataDepot _TestDataDepot = (TestDataDepot)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#TestDataDepotAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        TestDataDepot _TestDataDepot = (TestDataDepot)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private TestDataDepotDS m_ds = TestDataDepotDS.getInstance();
    private static Logger m_logger = Logger.getLogger( TestDataDepotActionExtent.class);
    
}
