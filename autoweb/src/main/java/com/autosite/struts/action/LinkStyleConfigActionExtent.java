package com.autosite.struts.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.LinkStyleConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.LinkStyleConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class LinkStyleConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinkStyleConfigAction#xtent.beforeAdd");		
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#LinkStyleConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinkStyleConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinkStyleConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinkStyleConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#LinkStyleConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

    private static Logger m_logger = Logger.getLogger( LinkStyleConfigActionExtent.class);
    
}
