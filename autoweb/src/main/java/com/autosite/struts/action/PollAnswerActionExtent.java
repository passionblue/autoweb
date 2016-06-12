package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PollAnswer;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PollAnswerDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PollAnswerForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PollAnswerActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAnswerAction#xtent.beforeAdd");		
        PollAnswer _PollAnswer = (PollAnswer)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PollAnswerAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PollAnswer _PollAnswer = (PollAnswer)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAnswerAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PollAnswer _PollAnswer = (PollAnswer)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAnswerAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PollAnswer _PollAnswer = (PollAnswer)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAnswerAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PollAnswer _PollAnswer = (PollAnswer)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAnswerAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PollAnswer _PollAnswer = (PollAnswer)baseDbOject;
    }

	private PollAnswerDS m_ds = PollAnswerDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PollAnswerActionExtent.class);
    
}
