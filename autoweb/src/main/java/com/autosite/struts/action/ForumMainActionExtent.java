package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ForumMain;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.ForumMainDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ForumMainForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class ForumMainActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ForumMainAction#xtent.beforeAdd");		
        ForumMain _ForumMain = (ForumMain)baseDbOject;

        if ( WebUtil.isNull(_ForumMain.getTitle())){
            m_logger.debug("title is missing in this requres " + ForumMainDS.objectToString(_ForumMain));
            throw new Exception("Title is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#ForumMainAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        ForumMain _ForumMain = (ForumMain)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ForumMainAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        ForumMain _ForumMain = (ForumMain)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ForumMainAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        ForumMain _ForumMain = (ForumMain)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ForumMainAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        ForumMain _ForumMain = (ForumMain)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#ForumMainAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        ForumMain _ForumMain = (ForumMain)baseDbOject;
    }

	private ForumMainDS m_ds = ForumMainDS.getInstance();
    private static Logger m_logger = Logger.getLogger( ForumMainActionExtent.class);
    
}
