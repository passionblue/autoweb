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

import com.autosite.db.StyleSet;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.StyleSetDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.StyleSetForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class StyleSetActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetAction#xtent.beforeAdd");		
        StyleSet _StyleSet = (StyleSet)baseDbOject;



        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#StyleSetAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        StyleSet _StyleSet = (StyleSet)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        StyleSet _StyleSet = (StyleSet)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        StyleSet _StyleSet = (StyleSet)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        StyleSet _StyleSet = (StyleSet)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleSetAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        StyleSet _StyleSet = (StyleSet)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

	private StyleSetDS m_ds = StyleSetDS.getInstance();
    private static Logger m_logger = Logger.getLogger( StyleSetActionExtent.class);
    
}
