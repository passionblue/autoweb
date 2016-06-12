package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PageStyle;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PageStyleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PageStyleForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PageStyleActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStyleAction#xtent.beforeAdd");		
        PageStyle _PageStyle = (PageStyle)baseDbOject;



        if (PageStyleDS.getInstance().getObjectByPageId(_PageStyle.getPageId()) != null) {
            m_logger.debug("The same value already exists." + _PageStyle.getPageId());
            throw new ActionExtentException("The same value already exists.", "linkto_add");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PageStyleAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PageStyle _PageStyle = (PageStyle)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStyleAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PageStyle _PageStyle = (PageStyle)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStyleAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PageStyle _PageStyle = (PageStyle)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStyleAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PageStyle _PageStyle = (PageStyle)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PageStyleAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PageStyle _PageStyle = (PageStyle)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

	private PageStyleDS m_ds = PageStyleDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PageStyleActionExtent.class);
    
}
