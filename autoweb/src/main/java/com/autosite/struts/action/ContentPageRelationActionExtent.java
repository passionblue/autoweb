package com.autosite.struts.action;

import java.util.Date;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ContentPageRelation;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ContentPageRelationDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ContentPageRelationForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class ContentPageRelationActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentPageRelationAction#xtent.beforeAdd");		
        ContentPageRelation _ContentPageRelation = (ContentPageRelation)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ContentPageRelationAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ContentPageRelation _ContentPageRelation = (ContentPageRelation)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentPageRelationAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ContentPageRelation _ContentPageRelation = (ContentPageRelation)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentPageRelationAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ContentPageRelation _ContentPageRelation = (ContentPageRelation)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentPageRelationAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ContentPageRelation _ContentPageRelation = (ContentPageRelation)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentPageRelationAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ContentPageRelation _ContentPageRelation = (ContentPageRelation)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( ContentPageRelationActionExtent.class);
    
}
