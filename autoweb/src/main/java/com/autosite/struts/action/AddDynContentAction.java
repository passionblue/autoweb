/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.Constants;
import com.autosite.db.Content;
import com.autosite.db.ContentData;
import com.autosite.db.Page;
import com.autosite.db.Site;
import com.autosite.ds.ContentDS;
import com.autosite.ds.ContentDataDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.AddDynContentForm;
import com.autosite.util.ContentUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

/**
 * MyEclipse Struts Creation date: 03-24-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/addDynContent" name="addDynContentForm"
 *                input="/jsp/form/addDynContent.jsp" scope="request"
 *                validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp"
 *                        contextRelative="true"
 */
public class AddDynContentAction extends AutositeCoreAction {
    /*
     * Generated Methods
     */

    /**
     * Method execute
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        AddDynContentForm contentForm = (AddDynContentForm) form;// TODO
        // Auto-generated
        // method
        // stub
        HttpSession session = request.getSession();

        m_logger.debug("##### TITLE/SUBJECT= " + contentForm.getTitle());
        m_logger.debug("##### CONTENT= " + contentForm.getContent());
        m_logger.debug("##### HTML= " + request.getParameter("html"));

        // TODO check the site matches URL entered
        // Check if it was added to homepage. if, then shoudl return to home.

        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (!isMissing(request.getParameter("edit"))) {
            if (request.getParameter("edit").trim().equals("true")) {

                long cid = Long.parseLong(request.getParameter("cid"));
                Content editCont = ContentDS.getInstance().getById(cid);

                if (editCont.getSiteId() != site.getId()) {
                    sessionErrorText(session, "Invalid request. Please try again");
                    return mapping.findForward("default");
                }

                editDynContent(request, contentForm, editCont);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "append", "true")||hasRequestValue(request, "app", "true")||hasRequestValue(request, "act", "app")) {
            
            long cid = Long.parseLong(request.getParameter("cid"));
            Content editCont = ContentDS.getInstance().getById(cid);

            if (editCont.getSiteId() != site.getId()) {
                sessionErrorText(session, "Invalid request. Please try again");
                return mapping.findForward("default");
            }
            appendDynContent(request, contentForm, editCont);
            return mapping.findForward("default");

            
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            Content editCont = ContentDS.getInstance().getById(cid);
            
            if (editCont.getSiteId() != site.getId()) {
                sessionErrorText(session, "Invalid request. Please try again");
                return mapping.findForward("default");
            }

            try{
                editField(request, response, editCont);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
            
        } else {

            // ###################################################
            String paramPid = request.getParameter("pid");
            long pid = -1;
            Page page = PageDS.getInstance().getById(pid);

            // Return page
            if (page != null && page.getPageName().equals("XHOME")) {
                setPage(session, request.getServerName(), "home");
            }
            else {
                setPage(session, request.getServerName(), getStringUnderlyingPage(request));
            }

            if (!isMissing(paramPid)) {
                pid = Long.parseLong(paramPid);
            }

            if (equalsTo(contentForm.getContentType(), Constants.CONTENT_TYPE_DEFAULT) && isMissing(contentForm.getContent())) {
                sessionErrorText(session, "Error Occurred: Content is missing");
                return mapping.findForward("default");
            }

            if (equalsTo(contentForm.getContentType(), Constants.CONTENT_TYPE_IMAGELEFT) && isMissing(contentForm.getContent())) {
                sessionErrorText(session, "Error Occurred: Content is missing");
                return mapping.findForward("default");
            }

            if (equalsTo(contentForm.getContentType(), Constants.CONTENT_TYPE_LINK) && isMissing(contentForm.getTitle())) {
                sessionErrorText(session, "Error Occurred: Link should into Suject field");
                return mapping.findForward("default");
            }

            boolean html = false;
            if (!isMissing(request.getParameter("html"))) {
                html = true;
            }

            Content content = new Content();

            String contentStr = contentForm.getContent();
            content.setContent(normalizeContent(contentStr, html));

            content.setContentSubject(contentForm.getTitle());
            content.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            content.setUpdatedTime(content.getCreatedTime());
            content.setPageId(pid);
            content.setPanelId(WebParamUtil.getLongValue(contentForm.getPanelId()));
            content.setSiteId(site.getId());
            content.setCategoryId(WebParamUtil.getLongValue(contentForm.getCategoryId()));

            content.setCategory(contentForm.getCat());
            content.setSourceName(contentForm.getSourceName());
            content.setSourceUrl(contentForm.getSourceUrl());

            content.setContentType(WebParamUtil.getIntValue(contentForm.getContentType()));
            content.setImageUrl(contentForm.getImageUrl());
            content.setImageHeight(WebParamUtil.getIntValue(contentForm.getImageHeight()));
            content.setImageWidth(WebParamUtil.getIntValue(contentForm.getImageWidth()));

            content.setTags(contentForm.getTags());
            content.setExtraKeywords(contentForm.getExtraKeywords());
            content.setShortcutUrl(contentForm.getShortcutUrl());

            content.setColumnNum(WebParamUtil.getIntValue(contentForm.getColumnNum()));

            content.setShortcutUrl(ContentUtil.makeShortcutUrl(contentForm.getTitle()));

            if (html)
                content.setInHtml(1);

            if (WebUtil.isNotTrue(contentForm.getAbstractNo()) && WebUtil.isTrue(contentForm.getAbstractGenerate()) && WebUtil.isNull(contentForm.getContentAbstract()) ){
                String abs = ContentUtil.getBeginingOfContent(content, 100);
                m_logger.debug("Abstract is missing for the new content. abs=" + abs);

                content.setContentAbstract(abs);
            }

            //TODO should be based on data
            ContentData cdata = new ContentData();
            cdata.setContentId(content.getId());
            cdata.setSiteId(content.getSiteId());
            cdata.setData(content.getContent());
            
            cdata.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            cdata.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
            if ( ContentDataDS.getInstance().put(cdata)){
                content.setUseExternalData(1);
            }
            
            if ( !ContentDS.getInstance().put(content)){
                m_logger.error("Failed to save content but content_data has been saved. Need to take care of integrity.");
            }
        }
        return mapping.findForward("default");
    }

    protected void editDynContent(HttpServletRequest request, AddDynContentForm contentForm, Content editCont) {

        HttpSession session = request.getSession();

        if (!isMissing(request.getParameter("cid"))) {

            // Return page
            long editPid = editCont.getPageId();
            Page editPage = PageDS.getInstance().getById(editPid);

            if (editPage != null && editPage.getPageName().equals("XHOME")) {
                setPage(session, "home");
            }
            else {
                setPage(session, getStringUnderlyingPage(request));
            }

            if (editCont != null) {
                m_logger.info("editing content found=" + editCont.getId());

                editCont.setContentSubject(contentForm.getTitle());
                editCont.setContent(normalizeContent(contentForm.getContent(), (editCont.getInHtml() == 1)));
                editCont.setCategory(contentForm.getCat());
                editCont.setSourceName(contentForm.getSourceName());
                editCont.setSourceUrl(contentForm.getSourceUrl());
                editCont.setPanelId(WebParamUtil.getLongValue(contentForm.getPanelId()));
                editCont.setCategoryId(WebParamUtil.getLongValue(contentForm.getCategoryId()));

                editCont.setContentType(WebParamUtil.getIntValue(contentForm.getContentType()));
                editCont.setImageUrl(contentForm.getImageUrl());
                editCont.setImageHeight(WebParamUtil.getIntValue(contentForm.getImageHeight()));
                editCont.setImageWidth(WebParamUtil.getIntValue(contentForm.getImageWidth()));

                editCont.setTags(contentForm.getTags());
                editCont.setExtraKeywords(contentForm.getExtraKeywords());

                editCont.setColumnNum(WebParamUtil.getIntValue(contentForm.getColumnNum()));

                ContentDS.getInstance().update(editCont);
            }
            else {
                sessionErrorText(session, "Internal error occurred. Please try again");
            }
        }
        else {
            sessionErrorText(session, "Invalid Request");
        }

    }

    protected void appendDynContent(HttpServletRequest request, AddDynContentForm contentForm, Content editCont) {
        
        String appendStr = "";
        
        if (editCont.getInHtml() == 0) {
        
            if (contentForm.getContent() != null) {
                appendStr += contentForm.getContent();
            }
        } else {
            if (contentForm.getTitle() != null) {
                appendStr = "<p><b>" + contentForm.getTitle() + "</b></p>";
            }
            if (contentForm.getContent() != null) {
                appendStr += contentForm.getContent();
            }
        }
        
        if (appendStr.length() > 0 ) {
            editCont.setContent(editCont.getContent() + "<br/>" + appendStr);
            ContentDS.getInstance().update(editCont);
        }
    }    
    
    protected void editField(HttpServletRequest request, HttpServletResponse response, Content _Content) throws Exception {

        // long cid = WebParamUtil.getLongValue(request.getParameter("id"));
        // Content _Content = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " + _Content.getPageId() + "->" + request.getParameter("pageId"));
            _Content.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("subPageId"))) {
            m_logger.debug("updating param subPageId from " + _Content.getSubPageId() + "->" + request.getParameter("subPageId"));
            _Content.setSubPageId(WebParamUtil.getLongValue(request.getParameter("subPageId")));
        }
        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " + _Content.getPanelId() + "->" + request.getParameter("panelId"));
            _Content.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("categoryId"))) {
            m_logger.debug("updating param categoryId from " + _Content.getCategoryId() + "->" + request.getParameter("categoryId"));
            _Content.setCategoryId(WebParamUtil.getLongValue(request.getParameter("categoryId")));
        }
        if (!isMissing(request.getParameter("contentSubject"))) {
            m_logger.debug("updating param contentSubject from " + _Content.getContentSubject() + "->" + request.getParameter("contentSubject"));
            _Content.setContentSubject(WebParamUtil.getStringValue(request.getParameter("contentSubject")));
        }
        if (!isMissing(request.getParameter("content"))) {
            m_logger.debug("updating param content from " + _Content.getContent() + "->" + request.getParameter("content"));
            _Content.setContent(WebParamUtil.getStringValue(request.getParameter("content")));
        }
        if (!isMissing(request.getParameter("authorName"))) {
            m_logger.debug("updating param authorName from " + _Content.getAuthorName() + "->" + request.getParameter("authorName"));
            _Content.setAuthorName(WebParamUtil.getStringValue(request.getParameter("authorName")));
        }
        if (!isMissing(request.getParameter("category"))) {
            m_logger.debug("updating param category from " + _Content.getCategory() + "->" + request.getParameter("category"));
            _Content.setCategory(WebParamUtil.getStringValue(request.getParameter("category")));
        }
        if (!isMissing(request.getParameter("createdTime"))) {
            m_logger.debug("updating param createdTime from " + _Content.getCreatedTime() + "->" + request.getParameter("createdTime"));
            _Content.setCreatedTime(WebParamUtil.getDateValue(request.getParameter("createdTime")));
        }
        if (!isMissing(request.getParameter("updatedTime"))) {
            m_logger.debug("updating param updatedTime from " + _Content.getUpdatedTime() + "->" + request.getParameter("updatedTime"));
            _Content.setUpdatedTime(WebParamUtil.getDateValue(request.getParameter("updatedTime")));
        }
        if (!isMissing(request.getParameter("contentType"))) {
            m_logger.debug("updating param contentType from " + _Content.getContentType() + "->" + request.getParameter("contentType"));
            _Content.setContentType(WebParamUtil.getIntValue(request.getParameter("contentType")));
        }
        if (!isMissing(request.getParameter("sourceName"))) {
            m_logger.debug("updating param sourceName from " + _Content.getSourceName() + "->" + request.getParameter("sourceName"));
            _Content.setSourceName(WebParamUtil.getStringValue(request.getParameter("sourceName")));
        }
        if (!isMissing(request.getParameter("sourceUrl"))) {
            m_logger.debug("updating param sourceUrl from " + _Content.getSourceUrl() + "->" + request.getParameter("sourceUrl"));
            _Content.setSourceUrl(WebParamUtil.getStringValue(request.getParameter("sourceUrl")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " + _Content.getHide() + "->" + request.getParameter("hide"));
            _Content.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("showHome"))) {
            m_logger.debug("updating param showHome from " + _Content.getShowHome() + "->" + request.getParameter("showHome"));
            _Content.setShowHome(WebParamUtil.getIntValue(request.getParameter("showHome")));
        }
        if (!isMissing(request.getParameter("imageUrl"))) {
            m_logger.debug("updating param imageUrl from " + _Content.getImageUrl() + "->" + request.getParameter("imageUrl"));
            _Content.setImageUrl(WebParamUtil.getStringValue(request.getParameter("imageUrl")));
        }
        if (!isMissing(request.getParameter("imageHeight"))) {
            m_logger.debug("updating param imageHeight from " + _Content.getImageHeight() + "->" + request.getParameter("imageHeight"));
            _Content.setImageHeight(WebParamUtil.getIntValue(request.getParameter("imageHeight")));
        }
        if (!isMissing(request.getParameter("imageWidth"))) {
            m_logger.debug("updating param imageWidth from " + _Content.getImageWidth() + "->" + request.getParameter("imageWidth"));
            _Content.setImageWidth(WebParamUtil.getIntValue(request.getParameter("imageWidth")));
        }
        if (!isMissing(request.getParameter("inHtml"))) {
            m_logger.debug("updating param inHtml from " + _Content.getInHtml() + "->" + request.getParameter("inHtml"));
            _Content.setInHtml(WebParamUtil.getIntValue(request.getParameter("inHtml")));
        }
        if (!isMissing(request.getParameter("tags"))) {
            m_logger.debug("updating param tags from " + _Content.getTags() + "->" + request.getParameter("tags"));
            _Content.setTags(WebParamUtil.getStringValue(request.getParameter("tags")));
        }
        if (!isMissing(request.getParameter("extraKeywords"))) {
            m_logger.debug("updating param extraKeywords from " + _Content.getExtraKeywords() + "->" + request.getParameter("extraKeywords"));
            _Content.setExtraKeywords(WebParamUtil.getStringValue(request.getParameter("extraKeywords")));
        }
        if (!isMissing(request.getParameter("shortcutUrl"))) {
            m_logger.debug("updating param shortcutUrl from " + _Content.getShortcutUrl() + "->" + request.getParameter("shortcutUrl"));
            _Content.setShortcutUrl(WebParamUtil.getStringValue(request.getParameter("shortcutUrl")));
        }
        if (!isMissing(request.getParameter("columnNum"))) {
            m_logger.debug("updating param columnNum from " + _Content.getColumnNum() + "->" + request.getParameter("columnNum"));
            _Content.setColumnNum(WebParamUtil.getIntValue(request.getParameter("columnNum")));
        }

        ContentDS.getInstance().update(_Content);

    }

    protected String normalizeContent(String rawContent, boolean html) {
        if (!html) {
            rawContent = rawContent.replace("”", "&rdquo;");
            rawContent = rawContent.replace("“", "&ldquo;");
            rawContent = rawContent.replace("’", "&rsquo;");
            rawContent = rawContent.replace("‘", "&lsquo;");
            rawContent = rawContent.replace("&", "&amp;");
        }

        return rawContent;
    }

    protected boolean loginRequired() {
        return true;
    }
    private static Logger m_logger = Logger.getLogger(AddDynContentAction.class);
}
