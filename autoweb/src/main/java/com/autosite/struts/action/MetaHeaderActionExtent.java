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

import com.autosite.db.MetaHeader;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.MetaHeaderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.MetaHeaderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class MetaHeaderActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#MetaHeaderAction#xtent.beforeAdd");		
        MetaHeader _MetaHeader = (MetaHeader)baseDbOject;

        if ( WebUtil.isNull(_MetaHeader.getSource())){
            m_logger.debug("source is missing in this requres " + MetaHeaderDS.objectToString(_MetaHeader));
            throw new ActionExtentException("Source is missing.", "");
        }
        if ( WebUtil.isNull(_MetaHeader.getName())){
            m_logger.debug("name is missing in this requres " + MetaHeaderDS.objectToString(_MetaHeader));
            throw new ActionExtentException("Name is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#MetaHeaderAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        MetaHeader _MetaHeader = (MetaHeader)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#MetaHeaderAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        MetaHeader _MetaHeader = (MetaHeader)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#MetaHeaderAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        MetaHeader _MetaHeader = (MetaHeader)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#MetaHeaderAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        MetaHeader _MetaHeader = (MetaHeader)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#MetaHeaderAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        MetaHeader _MetaHeader = (MetaHeader)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new HashMap();
    }

	private MetaHeaderDS m_ds = MetaHeaderDS.getInstance();
    private static Logger m_logger = Logger.getLogger( MetaHeaderActionExtent.class);
    
}
