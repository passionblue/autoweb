package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ContentFeedRel;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ContentDS;
import com.autosite.ds.ContentFeedConfigDS;
import com.autosite.ds.ContentFeedRelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ContentFeedRelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class ContentFeedRelActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedRelAction#xtent.beforeAdd");		
        ContentFeedRel _ContentFeedRel = (ContentFeedRel)baseDbOject;

        if (ContentDS.getInstance().getById(_ContentFeedRel.getContentId()) == null){
            throw new ActionExtentException("Internal error occurred. Corresponding Content not found", "content_feed_home");
        }

        if (ContentFeedConfigDS.getInstance().getById(_ContentFeedRel.getContentFeedId()) == null){
            throw new ActionExtentException("Internal error occurred. Corresponding Content not found", "content_feed_home");
        }
    
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ContentFeedRelAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ContentFeedRel _ContentFeedRel = (ContentFeedRel)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedRelAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ContentFeedRel _ContentFeedRel = (ContentFeedRel)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedRelAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ContentFeedRel _ContentFeedRel = (ContentFeedRel)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedRelAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ContentFeedRel _ContentFeedRel = (ContentFeedRel)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ContentFeedRelAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ContentFeedRel _ContentFeedRel = (ContentFeedRel)baseDbOject;
    }

	private ContentFeedRelDS m_ds = ContentFeedRelDS.getInstance();
    private static Logger m_logger = Logger.getLogger( ContentFeedRelActionExtent.class);
    
}
