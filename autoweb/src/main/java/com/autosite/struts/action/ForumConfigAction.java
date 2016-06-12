package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ForumConfig;
import com.autosite.db.Site;
import com.autosite.ds.ForumConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.ForumConfigForm;

public class ForumConfigAction extends AutositeCoreAction {

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ForumConfigForm _ForumConfigForm = (ForumConfigForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")) {
            edit(request, _ForumConfigForm);
            setPage(session, "home");
            return mapping.findForward("default");
        }
        else {

/*
            //             String paramPid = request.getParameter("pid");
            long pid = -1;
            Page page = PageDS.getInstance().getById(pid);

            // Return page
            if (page != null && page.getPageName().equals("XHOME")) {
                setPage(session, "home");
            }
            else {
                setPage(session, "dynamic_menu_content");
            }

            if (!isMissing(paramPid)) {
                pid = Long.parseLong(paramPid);
            }

            if (isMissing(addDynContentForm.getContent())) {
                sessionErrorText(session, "Add content");
                return mapping.findForward("default");
            }
            else if (isMissing(addDynContentForm.getContent())) {
                sessionErrorText(session, "Add content");
                return mapping.findForward("default");
            }

            boolean html = false;
            if (!isMissing(request.getParameter("html"))) {
                html = true;
            }


            ForumConfig _ForumConfig = new ForumConfig();

            String contentStr = addDynContentForm.getContent();
            if (!html) {
                contentStr = contentStr.replace("\n", "<BR>");
            }

*/
			ForumConfig _ForumConfig = new ForumConfig();	

//			_ForumConfig.setSiteId(site.getId());

            ForumConfigDS.getInstance().put(_ForumConfig);
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, ForumConfigForm _ForumConfigForm) {

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
            ForumConfig _ForumConfig = ForumConfigDS.getInstance().getById(cid);

           ForumConfigDS.getInstance().update(_ForumConfig);
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger( ForumConfigAction.class);
}
