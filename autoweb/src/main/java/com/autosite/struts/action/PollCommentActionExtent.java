package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PollComment;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PollCommentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.PollCommentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class PollCommentActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollCommentAction#xtent.beforeAdd");		
        PollComment _PollComment = (PollComment)baseDbOject;

        if ( WebUtil.isNull(_PollComment.getComment())){
            m_logger.debug("comment is missing in this requres " + PollCommentDS.objectToString(_PollComment));
            throw new ActionExtentException("Comment is missing.", "");
        }


        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PollCommentAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PollComment _PollComment = (PollComment)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollCommentAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PollComment _PollComment = (PollComment)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollCommentAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PollComment _PollComment = (PollComment)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollCommentAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PollComment _PollComment = (PollComment)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollCommentAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PollComment _PollComment = (PollComment)baseDbOject;
    }

	private PollCommentDS m_ds = PollCommentDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PollCommentActionExtent.class);
    
}
