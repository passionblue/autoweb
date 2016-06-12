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

import com.autosite.db.ForumSubject;
import com.autosite.ds.ForumSubjectDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ForumSubjectForm;
import com.autosite.struts.action.core.AutositeCoreAction;

public class ForumSubjectAction extends AutositeCoreAction {

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ForumSubjectForm _ForumSubjectForm = (ForumSubjectForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")) {
            edit(request, _ForumSubjectForm);
			setPage(session, "forum_subject");
            return mapping.findForward("default");
        } else if (hasRequestValue(request, "del", "true")) {

			String idString = request.getParameter("id");
        
        	if (isMissing(idString)) {
            	m_logger.warn("ID to be deleted is missing.");
            	return mapping.findForward("default");
        	}
        
        	long id = Long.parseLong(idString);
			m_logger.info("deleting for id=" +id);

			ForumSubject _ForumSubject = ForumSubjectDS.getInstance().getById(id);

			if ( _ForumSubject == null ) {
	            sessionErrorText(session, "Internal error occurred. Can't delete");
				m_logger.warn("Delete failed. object not found for " + id);
		        return mapping.findForward("default");
			}

        	ForumSubjectDS.getInstance().delete(_ForumSubject);
        	setPage(session, "forum_subject");    
        }
        else {

			m_logger.info("Creating new ForumSubject" );
			ForumSubject _ForumSubject = new ForumSubject();	

			_ForumSubject.setSiteId(site.getId());
			_ForumSubject.setUserId(0);

            _ForumSubject.setSubject(WebParamUtil.getStringValue(_ForumSubjectForm.getSubject()));
			m_logger.debug("setting Subject=" +_ForumSubjectForm.getSubject());

            _ForumSubject.setTimeCreated(WebParamUtil.getDateValue(_ForumSubjectForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_ForumSubjectForm.getTimeCreated());


            ForumSubjectDS.getInstance().put(_ForumSubject);
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, ForumSubjectForm _ForumSubjectForm) {

        HttpSession session = request.getSession();
/*
        if (!isMissing(request.getParameter("cid"))) {

            long cid = Long.parseLong(request.getParameter("cid"));
            Content editCont = ContentDS.getInstance().getById(cid);

            // Return page
            long editPid = editCont.getPageId();
            Page editPage = PageDS.getInstance().getById(editPid);
            if (editPage != null && editPage.getPageName().equals("XHOME")) {
                setPage(session, "home");
            }
            else {
                setPage(session, "dynamic_menu_content");
            }

            if (editCont != null) {
                m_logger.info("editing content found=" + editCont.getId());

                editCont.setContentSubject(addDynContentForm.getTitle());
                editCont.setContent(addDynContentForm.getContent());

                editCont.setCategory(addDynContentForm.getCat());
                editCont.setSourceName(addDynContentForm.getSourceName());
                editCont.setSourceUrl(addDynContentForm.getSourceUrl());

                editCont.setContentType(WebParamUtil.getIntValue(addDynContentForm.getContentType()));
                editCont.setImageUrl(addDynContentForm.getImageUrl());
                editCont.setImageHeight(WebParamUtil.getIntValue(addDynContentForm.getImageHeight()));
                editCont.setImageWidth(WebParamUtil.getIntValue(addDynContentForm.getImageWidth()));

                ContentDS.getInstance().update(editCont);
            }

        }
        else {
            sessionErrorText(session, "Invalid Request");
        }
*/

            setPage(session, "home");

            long cid = Long.parseLong(request.getParameter("id"));
            ForumSubject _ForumSubject = ForumSubjectDS.getInstance().getById(cid);

            _ForumSubject.setSubject(WebParamUtil.getStringValue(_ForumSubjectForm.getSubject()));
            _ForumSubject.setTimeCreated(WebParamUtil.getDateValue(_ForumSubjectForm.getTimeCreated()));
           ForumSubjectDS.getInstance().update(_ForumSubject);
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger( ForumSubjectAction.class);
}
