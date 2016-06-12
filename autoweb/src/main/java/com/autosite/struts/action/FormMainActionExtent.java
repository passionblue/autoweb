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

import com.autosite.db.FormMain;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.FormMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.FormMainForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class FormMainActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormMainAction#xtent.beforeAdd");		
        FormMain _FormMain = (FormMain)baseDbOject;

        if ( WebUtil.isNull(_FormMain.getTitle())){
            m_logger.debug("title is missing in this requres " + FormMainDS.objectToString(_FormMain));
            throw new ActionExtentException("Title is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#FormMainAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        FormMain _FormMain = (FormMain)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormMainAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        FormMain _FormMain = (FormMain)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormMainAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        FormMain _FormMain = (FormMain)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormMainAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        FormMain _FormMain = (FormMain)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormMainAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        FormMain _FormMain = (FormMain)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private FormMainDS m_ds = FormMainDS.getInstance();
    private static Logger m_logger = Logger.getLogger( FormMainActionExtent.class);
    
}
