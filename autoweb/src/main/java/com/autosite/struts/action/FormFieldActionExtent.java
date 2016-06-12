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

import com.autosite.db.FormField;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.FormFieldDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.FormFieldForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class FormFieldActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormFieldAction#xtent.beforeAdd");		
        FormField _FormField = (FormField)baseDbOject;

        if ( WebUtil.isNull(_FormField.getFieldText())){
            m_logger.debug("fieldText is missing in this requres " + FormFieldDS.objectToString(_FormField));
            throw new ActionExtentException("Field Text is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#FormFieldAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        FormField _FormField = (FormField)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormFieldAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        FormField _FormField = (FormField)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormFieldAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        FormField _FormField = (FormField)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormFieldAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        FormField _FormField = (FormField)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#FormFieldAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        FormField _FormField = (FormField)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map ret = new HashMap();
        return ret;
    }

	private FormFieldDS m_ds = FormFieldDS.getInstance();
    private static Logger m_logger = Logger.getLogger( FormFieldActionExtent.class);
    
}
