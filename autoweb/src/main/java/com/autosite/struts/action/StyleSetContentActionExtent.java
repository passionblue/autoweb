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

import com.autosite.db.StyleSetContent;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.StyleSetContentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.StyleSetContentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class StyleSetContentActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetContentAction#xtent.beforeAdd");		
        StyleSetContent _StyleSetContent = (StyleSetContent)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#StyleSetContentAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        StyleSetContent _StyleSetContent = (StyleSetContent)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetContentAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        StyleSetContent _StyleSetContent = (StyleSetContent)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetContentAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        StyleSetContent _StyleSetContent = (StyleSetContent)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetContentAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        StyleSetContent _StyleSetContent = (StyleSetContent)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetContentAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        StyleSetContent _StyleSetContent = (StyleSetContent)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

	private StyleSetContentDS m_ds = StyleSetContentDS.getInstance();
    private static Logger m_logger = Logger.getLogger( StyleSetContentActionExtent.class);
    
}
