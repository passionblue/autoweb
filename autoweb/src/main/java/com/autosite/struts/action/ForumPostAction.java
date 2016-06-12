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

import com.autosite.db.ForumPost;
import com.autosite.ds.ForumPostDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ForumPostForm;
import com.autosite.struts.action.core.AutositeCoreAction;

public class ForumPostAction extends AutositeCoreAction {

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ForumPostForm _ForumPostForm = (ForumPostForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")) {
            edit(request, _ForumPostForm);
			setPage(session, "forum_post");
            return mapping.findForward("default");
        } else if (hasRequestValue(request, "del", "true")) {

			String idString = request.getParameter("id");
        
        	if (isMissing(idString)) {
            	m_logger.warn("ID to be deleted is missing.");
            	return mapping.findForward("default");
        	}
        
        	long id = Long.parseLong(idString);
			m_logger.info("deleting for id=" +id);

			ForumPost _ForumPost = ForumPostDS.getInstance().getById(id);

			if ( _ForumPost == null ) {
	            sessionErrorText(session, "Internal error occurred. Can't delete");
				m_logger.warn("Delete failed. object not found for " + id);
		        return mapping.findForward("default");
			}

        	ForumPostDS.getInstance().delete(_ForumPost);
        	setPage(session, "forum_post");    
        }
        else {

			m_logger.info("Creating new ForumPost" );
			ForumPost _ForumPost = new ForumPost();	

			_ForumPost.setUserId(0);

            _ForumPost.setData(WebParamUtil.getStringValue(_ForumPostForm.getData()));
			m_logger.debug("setting Data=" +_ForumPostForm.getData());
            _ForumPost.setTimeCreated(WebParamUtil.getDateValue(_ForumPostForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_ForumPostForm.getTimeCreated());
            _ForumPost.setTimeUpdated(WebParamUtil.getDateValue(_ForumPostForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_ForumPostForm.getTimeUpdated());

            ForumPostDS.getInstance().put(_ForumPost);
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, ForumPostForm _ForumPostForm) {

        HttpSession session = request.getSession();

        setPage(session, "home");

        long cid = Long.parseLong(request.getParameter("id"));
            ForumPost _ForumPost = ForumPostDS.getInstance().getById(cid);

            _ForumPost.setData(WebParamUtil.getStringValue(_ForumPostForm.getData()));
            _ForumPost.setTimeCreated(WebParamUtil.getDateValue(_ForumPostForm.getTimeCreated()));
            _ForumPost.setTimeUpdated(WebParamUtil.getDateValue(_ForumPostForm.getTimeUpdated()));
           ForumPostDS.getInstance().update(_ForumPost);
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger( ForumPostAction.class);
}
