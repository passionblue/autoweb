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

import com.autosite.db.ForumSection;
import com.autosite.ds.ForumSectionDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ForumSectionForm;
import com.autosite.struts.action.core.AutositeCoreAction;

public class ForumSectionAction extends AutositeCoreAction {

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ForumSectionForm _ForumSectionForm = (ForumSectionForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")) {
            edit(request, _ForumSectionForm);
			setPage(session, "forum_section");
            return mapping.findForward("default");
        } else if (hasRequestValue(request, "del", "true")) {

			String idString = request.getParameter("id");
        
        	if (isMissing(idString)) {
            	m_logger.warn("ID to be deleted is missing.");
            	return mapping.findForward("default");
        	}
        
        	long id = Long.parseLong(idString);
			m_logger.info("deleting for id=" +id);

			ForumSection _ForumSection = ForumSectionDS.getInstance().getById(id);

			if ( _ForumSection == null ) {
	            sessionErrorText(session, "Internal error occurred. Can't delete");
				m_logger.warn("Delete failed. object not found for " + id);
		        return mapping.findForward("default");
			}

        	ForumSectionDS.getInstance().delete(_ForumSection);
        	setPage(session, "forum_section");    
        }
        else {

			m_logger.info("Creating new ForumSection" );
			ForumSection _ForumSection = new ForumSection();	

			// Setting IDs for the object

            _ForumSection.setSectionTitle(WebParamUtil.getStringValue(_ForumSectionForm.getSectionTitle()));
			m_logger.debug("setting SectionTitle=" +_ForumSectionForm.getSectionTitle());
            _ForumSection.setTimeCreated(WebParamUtil.getDateValue(_ForumSectionForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_ForumSectionForm.getTimeCreated());

            ForumSectionDS.getInstance().put(_ForumSection);
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, ForumSectionForm _ForumSectionForm) {

        HttpSession session = request.getSession();

        setPage(session, "home");

        long cid = Long.parseLong(request.getParameter("id"));
            ForumSection _ForumSection = ForumSectionDS.getInstance().getById(cid);

            _ForumSection.setSectionTitle(WebParamUtil.getStringValue(_ForumSectionForm.getSectionTitle()));
            _ForumSection.setTimeCreated(WebParamUtil.getDateValue(_ForumSectionForm.getTimeCreated()));
           ForumSectionDS.getInstance().update(_ForumSection);
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger( ForumSectionAction.class);
}
