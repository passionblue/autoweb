package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Content;
import com.autosite.db.Site;
import com.autosite.ds.ContentDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.Content2ActionForm;
import com.jtrend.util.WebParamUtil;

public class Content2Action extends AutositeCoreAction {

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Content2ActionForm _Content2ActionForm = (Content2ActionForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (hasRequestValue(request, "edit", "true")) {
            edit(request, _Content2ActionForm);
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


            Content _Content = new Content();

            String contentStr = addDynContentForm.getContent();
            if (!html) {
                contentStr = contentStr.replace("\n", "<BR>");
            }

*/
            Content _Content = new Content();   

            _Content.setContentSubject(WebParamUtil.getStringValue(_Content2ActionForm.getContentSubject()));
            _Content.setContent(WebParamUtil.getStringValue(_Content2ActionForm.getContent()));
            _Content.setCategory(WebParamUtil.getStringValue(_Content2ActionForm.getCategory()));
            _Content.setCreatedTime(WebParamUtil.getDateValue(_Content2ActionForm.getCreatedTime()));
            _Content.setUpdatedTime(WebParamUtil.getDateValue(_Content2ActionForm.getUpdatedTime()));
            _Content.setContentType(WebParamUtil.getIntValue(_Content2ActionForm.getContentType()));
            _Content.setSourceName(WebParamUtil.getStringValue(_Content2ActionForm.getSourceName()));
            _Content.setSourceUrl(WebParamUtil.getStringValue(_Content2ActionForm.getSourceUrl()));
            _Content.setHide(WebParamUtil.getIntValue(_Content2ActionForm.getHide()));
            _Content.setShowHome(WebParamUtil.getIntValue(_Content2ActionForm.getShowHome()));
            _Content.setImageUrl(WebParamUtil.getStringValue(_Content2ActionForm.getImageUrl()));
            _Content.setImageHeight(WebParamUtil.getIntValue(_Content2ActionForm.getImageHeight()));
            _Content.setImageWidth(WebParamUtil.getIntValue(_Content2ActionForm.getImageWidth()));

            ContentDS.getInstance().put(_Content);
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, Content2ActionForm _Content2ActionForm) {

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


            long cid = Long.parseLong(request.getParameter("id"));
            Content _Content = ContentDS.getInstance().getById(cid);

            _Content.setContentSubject(WebParamUtil.getStringValue(_Content2ActionForm.getContentSubject()));
            _Content.setContent(WebParamUtil.getStringValue(_Content2ActionForm.getContent()));
            _Content.setCategory(WebParamUtil.getStringValue(_Content2ActionForm.getCategory()));
            _Content.setCreatedTime(WebParamUtil.getDateValue(_Content2ActionForm.getCreatedTime()));
            _Content.setUpdatedTime(WebParamUtil.getDateValue(_Content2ActionForm.getUpdatedTime()));
            _Content.setContentType(WebParamUtil.getIntValue(_Content2ActionForm.getContentType()));
            _Content.setSourceName(WebParamUtil.getStringValue(_Content2ActionForm.getSourceName()));
            _Content.setSourceUrl(WebParamUtil.getStringValue(_Content2ActionForm.getSourceUrl()));
            _Content.setHide(WebParamUtil.getIntValue(_Content2ActionForm.getHide()));
            _Content.setShowHome(WebParamUtil.getIntValue(_Content2ActionForm.getShowHome()));
            _Content.setImageUrl(WebParamUtil.getStringValue(_Content2ActionForm.getImageUrl()));
            _Content.setImageHeight(WebParamUtil.getIntValue(_Content2ActionForm.getImageHeight()));
            _Content.setImageWidth(WebParamUtil.getIntValue(_Content2ActionForm.getImageWidth()));
           ContentDS.getInstance().update(_Content);
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger( Content2Action.class);
}
