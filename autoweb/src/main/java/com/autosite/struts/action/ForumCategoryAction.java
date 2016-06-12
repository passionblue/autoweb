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

import com.autosite.db.ForumCategory;
import com.autosite.ds.ForumCategoryDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.ForumCategoryForm;
import com.autosite.struts.action.core.AutositeCoreAction;

public class ForumCategoryAction extends AutositeCoreAction {

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ForumCategoryForm _ForumCategoryForm = (ForumCategoryForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")) {
            edit(request, _ForumCategoryForm);
            setPage(session, "home");
            return mapping.findForward("default");
        } else if (hasRequestValue(request, "del", "true")) {

			String idString = request.getParameter("id");
        
        	if (isMissing(idString)) {
            	m_logger.warn("ID to be deleted is missing.");
            	return mapping.findForward("default");
        	}
        
        	long id = Long.parseLong(idString);
			m_logger.info("deleting for id=" +id);

			ForumCategory _ForumCategory = ForumCategoryDS.getInstance().getById(id);
        	ForumCategoryDS.getInstance().delete(_ForumCategory);
        	setPage(session, "forum_category");    
        }
        else {

			m_logger.info("Creating new ForumCategory" );
			ForumCategory _ForumCategory = new ForumCategory();	

			_ForumCategory.setSiteId(site.getId());
            _ForumCategory.setCategory(WebParamUtil.getStringValue(_ForumCategoryForm.getCategory()));
			m_logger.debug("setting Category=" +_ForumCategoryForm.getCategory());

            _ForumCategory.setTimeCreated(WebParamUtil.getDateValue(_ForumCategoryForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_ForumCategoryForm.getTimeCreated());


            ForumCategoryDS.getInstance().put(_ForumCategory);
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, ForumCategoryForm _ForumCategoryForm) {

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
            ForumCategory _ForumCategory = ForumCategoryDS.getInstance().getById(cid);

            _ForumCategory.setCategory(WebParamUtil.getStringValue(_ForumCategoryForm.getCategory()));
            _ForumCategory.setTimeCreated(WebParamUtil.getDateValue(_ForumCategoryForm.getTimeCreated()));
           ForumCategoryDS.getInstance().update(_ForumCategory);
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger( ForumCategoryAction.class);
}
