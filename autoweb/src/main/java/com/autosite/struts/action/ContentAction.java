package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.Content;
import com.autosite.ds.ContentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.ContentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check




public class ContentAction extends AutositeCoreAction {

    public ContentAction(){
        m_ds = ContentDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        ContentForm _ContentForm = (ContentForm) form;
        HttpSession session = request.getSession();

        setPage(session, "content_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        Content _Content = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _Content = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Content _Content = m_ds.getById(cid);

            if (_Content == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Content.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Content.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_Content);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ContentForm, _Content);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Content _Content = m_ds.getById(cid);

            if (_Content == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Content.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Content.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _Content);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Content _Content = m_ds.getById(cid);

            if (_Content == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Content.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Content.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _Content);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_Content);
            try { 
                m_actionExtent.afterDelete(request, response, _Content);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
	            if (throwException) throw e;
                return mapping.findForward("default");
            }

            m_logger.info("Creating new Content" );
            Content _ContentNew = new Content();   

            // Setting IDs for the object
            _ContentNew.setSiteId(site.getId());

            _ContentNew.setPageId(WebParamUtil.getLongValue(_ContentForm.getPageId()));
            m_logger.debug("setting PageId=" +_ContentForm.getPageId());
            _ContentNew.setSubPageId(WebParamUtil.getLongValue(_ContentForm.getSubPageId()));
            m_logger.debug("setting SubPageId=" +_ContentForm.getSubPageId());
            _ContentNew.setPanelId(WebParamUtil.getLongValue(_ContentForm.getPanelId()));
            m_logger.debug("setting PanelId=" +_ContentForm.getPanelId());
            _ContentNew.setCategoryId(WebParamUtil.getLongValue(_ContentForm.getCategoryId()));
            m_logger.debug("setting CategoryId=" +_ContentForm.getCategoryId());
            _ContentNew.setUserId(WebParamUtil.getLongValue(_ContentForm.getUserId()));
            m_logger.debug("setting UserId=" +_ContentForm.getUserId());
            _ContentNew.setContentSubject(WebParamUtil.getStringValue(_ContentForm.getContentSubject()));
            m_logger.debug("setting ContentSubject=" +_ContentForm.getContentSubject());
            _ContentNew.setContentAbstract(WebParamUtil.getStringValue(_ContentForm.getContentAbstract()));
            m_logger.debug("setting ContentAbstract=" +_ContentForm.getContentAbstract());
            _ContentNew.setContent(WebParamUtil.getStringValue(_ContentForm.getContent()));
            m_logger.debug("setting Content=" +_ContentForm.getContent());
            _ContentNew.setAbstractGenerate(WebParamUtil.getIntValue(_ContentForm.getAbstractGenerate()));
            m_logger.debug("setting AbstractGenerate=" +_ContentForm.getAbstractGenerate());
            _ContentNew.setAbstractNo(WebParamUtil.getIntValue(_ContentForm.getAbstractNo()));
            m_logger.debug("setting AbstractNo=" +_ContentForm.getAbstractNo());
            _ContentNew.setUseExternalData(WebParamUtil.getIntValue(_ContentForm.getUseExternalData()));
            m_logger.debug("setting UseExternalData=" +_ContentForm.getUseExternalData());
            _ContentNew.setAuthorName(WebParamUtil.getStringValue(_ContentForm.getAuthorName()));
            m_logger.debug("setting AuthorName=" +_ContentForm.getAuthorName());
            _ContentNew.setCategory(WebParamUtil.getStringValue(_ContentForm.getCategory()));
            m_logger.debug("setting Category=" +_ContentForm.getCategory());
            _ContentNew.setCreatedTime(WebParamUtil.getDateValue(_ContentForm.getCreatedTime()));
            m_logger.debug("setting CreatedTime=" +_ContentForm.getCreatedTime());
            _ContentNew.setUpdatedTime(WebParamUtil.getDateValue(_ContentForm.getUpdatedTime()));
            m_logger.debug("setting UpdatedTime=" +_ContentForm.getUpdatedTime());
            _ContentNew.setContentType(WebParamUtil.getIntValue(_ContentForm.getContentType()));
            m_logger.debug("setting ContentType=" +_ContentForm.getContentType());
            _ContentNew.setSourceName(WebParamUtil.getStringValue(_ContentForm.getSourceName()));
            m_logger.debug("setting SourceName=" +_ContentForm.getSourceName());
            _ContentNew.setSourceUrl(WebParamUtil.getStringValue(_ContentForm.getSourceUrl()));
            m_logger.debug("setting SourceUrl=" +_ContentForm.getSourceUrl());
            _ContentNew.setHide(WebParamUtil.getIntValue(_ContentForm.getHide()));
            m_logger.debug("setting Hide=" +_ContentForm.getHide());
            _ContentNew.setShowHome(WebParamUtil.getIntValue(_ContentForm.getShowHome()));
            m_logger.debug("setting ShowHome=" +_ContentForm.getShowHome());
            _ContentNew.setImageUrl(WebParamUtil.getStringValue(_ContentForm.getImageUrl()));
            m_logger.debug("setting ImageUrl=" +_ContentForm.getImageUrl());
            _ContentNew.setImageHeight(WebParamUtil.getIntValue(_ContentForm.getImageHeight()));
            m_logger.debug("setting ImageHeight=" +_ContentForm.getImageHeight());
            _ContentNew.setImageWidth(WebParamUtil.getIntValue(_ContentForm.getImageWidth()));
            m_logger.debug("setting ImageWidth=" +_ContentForm.getImageWidth());
            _ContentNew.setInHtml(WebParamUtil.getIntValue(_ContentForm.getInHtml()));
            m_logger.debug("setting InHtml=" +_ContentForm.getInHtml());
            _ContentNew.setTags(WebParamUtil.getStringValue(_ContentForm.getTags()));
            m_logger.debug("setting Tags=" +_ContentForm.getTags());
            _ContentNew.setExtraKeywords(WebParamUtil.getStringValue(_ContentForm.getExtraKeywords()));
            m_logger.debug("setting ExtraKeywords=" +_ContentForm.getExtraKeywords());
            _ContentNew.setShortcutUrl(WebParamUtil.getStringValue(_ContentForm.getShortcutUrl()));
            m_logger.debug("setting ShortcutUrl=" +_ContentForm.getShortcutUrl());
            _ContentNew.setHeaderMeta(WebParamUtil.getStringValue(_ContentForm.getHeaderMeta()));
            m_logger.debug("setting HeaderMeta=" +_ContentForm.getHeaderMeta());
            _ContentNew.setHeaderLink(WebParamUtil.getStringValue(_ContentForm.getHeaderLink()));
            m_logger.debug("setting HeaderLink=" +_ContentForm.getHeaderLink());
            _ContentNew.setColumnNum(WebParamUtil.getIntValue(_ContentForm.getColumnNum()));
            m_logger.debug("setting ColumnNum=" +_ContentForm.getColumnNum());

            try {
                checkDepedenceIntegrity(_ContentNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


			// Checking the uniqueness by configuration. 
			if ( m_ds.getByPageIdCategory(_ContentNew.getPageId(), _ContentNew.getCategory()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _ContentNew.getCategory());
                return mapping.findForward("default");
            }
            try{
                m_actionExtent.beforeAdd(request, response, _ContentNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_ContentNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ContentNew);
            try{
                m_actionExtent.afterAdd(request, response, _ContentNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentForm _ContentForm, Content _Content) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Content _Content = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentDS.objectToString(_Content));

        _Content.setPageId(WebParamUtil.getLongValue(_ContentForm.getPageId()));
        _Content.setSubPageId(WebParamUtil.getLongValue(_ContentForm.getSubPageId()));
        _Content.setPanelId(WebParamUtil.getLongValue(_ContentForm.getPanelId()));
        _Content.setCategoryId(WebParamUtil.getLongValue(_ContentForm.getCategoryId()));
        _Content.setUserId(WebParamUtil.getLongValue(_ContentForm.getUserId()));
        _Content.setContentSubject(WebParamUtil.getStringValue(_ContentForm.getContentSubject()));
        _Content.setContentAbstract(WebParamUtil.getStringValue(_ContentForm.getContentAbstract()));
        _Content.setContent(WebParamUtil.getStringValue(_ContentForm.getContent()));
        _Content.setAbstractGenerate(WebParamUtil.getIntValue(_ContentForm.getAbstractGenerate()));
        _Content.setAbstractNo(WebParamUtil.getIntValue(_ContentForm.getAbstractNo()));
        _Content.setUseExternalData(WebParamUtil.getIntValue(_ContentForm.getUseExternalData()));
        _Content.setAuthorName(WebParamUtil.getStringValue(_ContentForm.getAuthorName()));
        _Content.setCategory(WebParamUtil.getStringValue(_ContentForm.getCategory()));
        _Content.setCreatedTime(WebParamUtil.getDateValue(_ContentForm.getCreatedTime()));
        _Content.setUpdatedTime(WebParamUtil.getDateValue(_ContentForm.getUpdatedTime()));
        _Content.setContentType(WebParamUtil.getIntValue(_ContentForm.getContentType()));
        _Content.setSourceName(WebParamUtil.getStringValue(_ContentForm.getSourceName()));
        _Content.setSourceUrl(WebParamUtil.getStringValue(_ContentForm.getSourceUrl()));
        _Content.setHide(WebParamUtil.getIntValue(_ContentForm.getHide()));
        _Content.setShowHome(WebParamUtil.getIntValue(_ContentForm.getShowHome()));
        _Content.setImageUrl(WebParamUtil.getStringValue(_ContentForm.getImageUrl()));
        _Content.setImageHeight(WebParamUtil.getIntValue(_ContentForm.getImageHeight()));
        _Content.setImageWidth(WebParamUtil.getIntValue(_ContentForm.getImageWidth()));
        _Content.setInHtml(WebParamUtil.getIntValue(_ContentForm.getInHtml()));
        _Content.setTags(WebParamUtil.getStringValue(_ContentForm.getTags()));
        _Content.setExtraKeywords(WebParamUtil.getStringValue(_ContentForm.getExtraKeywords()));
        _Content.setShortcutUrl(WebParamUtil.getStringValue(_ContentForm.getShortcutUrl()));
        _Content.setHeaderMeta(WebParamUtil.getStringValue(_ContentForm.getHeaderMeta()));
        _Content.setHeaderLink(WebParamUtil.getStringValue(_ContentForm.getHeaderLink()));
        _Content.setColumnNum(WebParamUtil.getIntValue(_ContentForm.getColumnNum()));

        m_actionExtent.beforeUpdate(request, response, _Content);
        m_ds.update(_Content);
        m_actionExtent.afterUpdate(request, response, _Content);
        m_logger.debug("After update " + ContentDS.objectToString(_Content));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, Content _Content) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Content _Content = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_Content.getPageId() + "->" + request.getParameter("pageId"));
            _Content.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("subPageId"))) {
            m_logger.debug("updating param subPageId from " +_Content.getSubPageId() + "->" + request.getParameter("subPageId"));
            _Content.setSubPageId(WebParamUtil.getLongValue(request.getParameter("subPageId")));
        }
        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " +_Content.getPanelId() + "->" + request.getParameter("panelId"));
            _Content.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("categoryId"))) {
            m_logger.debug("updating param categoryId from " +_Content.getCategoryId() + "->" + request.getParameter("categoryId"));
            _Content.setCategoryId(WebParamUtil.getLongValue(request.getParameter("categoryId")));
        }
        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_Content.getUserId() + "->" + request.getParameter("userId"));
            _Content.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("contentSubject"))) {
            m_logger.debug("updating param contentSubject from " +_Content.getContentSubject() + "->" + request.getParameter("contentSubject"));
            _Content.setContentSubject(WebParamUtil.getStringValue(request.getParameter("contentSubject")));
        }
        if (!isMissing(request.getParameter("contentAbstract"))) {
            m_logger.debug("updating param contentAbstract from " +_Content.getContentAbstract() + "->" + request.getParameter("contentAbstract"));
            _Content.setContentAbstract(WebParamUtil.getStringValue(request.getParameter("contentAbstract")));
        }
        if (!isMissing(request.getParameter("content"))) {
            m_logger.debug("updating param content from " +_Content.getContent() + "->" + request.getParameter("content"));
            _Content.setContent(WebParamUtil.getStringValue(request.getParameter("content")));
        }
        if (!isMissing(request.getParameter("abstractGenerate"))) {
            m_logger.debug("updating param abstractGenerate from " +_Content.getAbstractGenerate() + "->" + request.getParameter("abstractGenerate"));
            _Content.setAbstractGenerate(WebParamUtil.getIntValue(request.getParameter("abstractGenerate")));
        }
        if (!isMissing(request.getParameter("abstractNo"))) {
            m_logger.debug("updating param abstractNo from " +_Content.getAbstractNo() + "->" + request.getParameter("abstractNo"));
            _Content.setAbstractNo(WebParamUtil.getIntValue(request.getParameter("abstractNo")));
        }
        if (!isMissing(request.getParameter("useExternalData"))) {
            m_logger.debug("updating param useExternalData from " +_Content.getUseExternalData() + "->" + request.getParameter("useExternalData"));
            _Content.setUseExternalData(WebParamUtil.getIntValue(request.getParameter("useExternalData")));
        }
        if (!isMissing(request.getParameter("authorName"))) {
            m_logger.debug("updating param authorName from " +_Content.getAuthorName() + "->" + request.getParameter("authorName"));
            _Content.setAuthorName(WebParamUtil.getStringValue(request.getParameter("authorName")));
        }
        if (!isMissing(request.getParameter("category"))) {
            m_logger.debug("updating param category from " +_Content.getCategory() + "->" + request.getParameter("category"));
            _Content.setCategory(WebParamUtil.getStringValue(request.getParameter("category")));
        }
        if (!isMissing(request.getParameter("createdTime"))) {
            m_logger.debug("updating param createdTime from " +_Content.getCreatedTime() + "->" + request.getParameter("createdTime"));
            _Content.setCreatedTime(WebParamUtil.getDateValue(request.getParameter("createdTime")));
        }
        if (!isMissing(request.getParameter("updatedTime"))) {
            m_logger.debug("updating param updatedTime from " +_Content.getUpdatedTime() + "->" + request.getParameter("updatedTime"));
            _Content.setUpdatedTime(WebParamUtil.getDateValue(request.getParameter("updatedTime")));
        }
        if (!isMissing(request.getParameter("contentType"))) {
            m_logger.debug("updating param contentType from " +_Content.getContentType() + "->" + request.getParameter("contentType"));
            _Content.setContentType(WebParamUtil.getIntValue(request.getParameter("contentType")));
        }
        if (!isMissing(request.getParameter("sourceName"))) {
            m_logger.debug("updating param sourceName from " +_Content.getSourceName() + "->" + request.getParameter("sourceName"));
            _Content.setSourceName(WebParamUtil.getStringValue(request.getParameter("sourceName")));
        }
        if (!isMissing(request.getParameter("sourceUrl"))) {
            m_logger.debug("updating param sourceUrl from " +_Content.getSourceUrl() + "->" + request.getParameter("sourceUrl"));
            _Content.setSourceUrl(WebParamUtil.getStringValue(request.getParameter("sourceUrl")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_Content.getHide() + "->" + request.getParameter("hide"));
            _Content.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("showHome"))) {
            m_logger.debug("updating param showHome from " +_Content.getShowHome() + "->" + request.getParameter("showHome"));
            _Content.setShowHome(WebParamUtil.getIntValue(request.getParameter("showHome")));
        }
        if (!isMissing(request.getParameter("imageUrl"))) {
            m_logger.debug("updating param imageUrl from " +_Content.getImageUrl() + "->" + request.getParameter("imageUrl"));
            _Content.setImageUrl(WebParamUtil.getStringValue(request.getParameter("imageUrl")));
        }
        if (!isMissing(request.getParameter("imageHeight"))) {
            m_logger.debug("updating param imageHeight from " +_Content.getImageHeight() + "->" + request.getParameter("imageHeight"));
            _Content.setImageHeight(WebParamUtil.getIntValue(request.getParameter("imageHeight")));
        }
        if (!isMissing(request.getParameter("imageWidth"))) {
            m_logger.debug("updating param imageWidth from " +_Content.getImageWidth() + "->" + request.getParameter("imageWidth"));
            _Content.setImageWidth(WebParamUtil.getIntValue(request.getParameter("imageWidth")));
        }
        if (!isMissing(request.getParameter("inHtml"))) {
            m_logger.debug("updating param inHtml from " +_Content.getInHtml() + "->" + request.getParameter("inHtml"));
            _Content.setInHtml(WebParamUtil.getIntValue(request.getParameter("inHtml")));
        }
        if (!isMissing(request.getParameter("tags"))) {
            m_logger.debug("updating param tags from " +_Content.getTags() + "->" + request.getParameter("tags"));
            _Content.setTags(WebParamUtil.getStringValue(request.getParameter("tags")));
        }
        if (!isMissing(request.getParameter("extraKeywords"))) {
            m_logger.debug("updating param extraKeywords from " +_Content.getExtraKeywords() + "->" + request.getParameter("extraKeywords"));
            _Content.setExtraKeywords(WebParamUtil.getStringValue(request.getParameter("extraKeywords")));
        }
        if (!isMissing(request.getParameter("shortcutUrl"))) {
            m_logger.debug("updating param shortcutUrl from " +_Content.getShortcutUrl() + "->" + request.getParameter("shortcutUrl"));
            _Content.setShortcutUrl(WebParamUtil.getStringValue(request.getParameter("shortcutUrl")));
        }
        if (!isMissing(request.getParameter("headerMeta"))) {
            m_logger.debug("updating param headerMeta from " +_Content.getHeaderMeta() + "->" + request.getParameter("headerMeta"));
            _Content.setHeaderMeta(WebParamUtil.getStringValue(request.getParameter("headerMeta")));
        }
        if (!isMissing(request.getParameter("headerLink"))) {
            m_logger.debug("updating param headerLink from " +_Content.getHeaderLink() + "->" + request.getParameter("headerLink"));
            _Content.setHeaderLink(WebParamUtil.getStringValue(request.getParameter("headerLink")));
        }
        if (!isMissing(request.getParameter("columnNum"))) {
            m_logger.debug("updating param columnNum from " +_Content.getColumnNum() + "->" + request.getParameter("columnNum"));
            _Content.setColumnNum(WebParamUtil.getIntValue(request.getParameter("columnNum")));
        }

        m_actionExtent.beforeUpdate(request, response, _Content);
        m_ds.update(_Content);
        m_actionExtent.afterUpdate(request, response, _Content);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, Content _Content) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Content _Content = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
			return String.valueOf(_Content.getPageId());
        }
        if (!isMissing(request.getParameter("subPageId"))) {
			return String.valueOf(_Content.getSubPageId());
        }
        if (!isMissing(request.getParameter("panelId"))) {
			return String.valueOf(_Content.getPanelId());
        }
        if (!isMissing(request.getParameter("categoryId"))) {
			return String.valueOf(_Content.getCategoryId());
        }
        if (!isMissing(request.getParameter("userId"))) {
			return String.valueOf(_Content.getUserId());
        }
        if (!isMissing(request.getParameter("contentSubject"))) {
			return String.valueOf(_Content.getContentSubject());
        }
        if (!isMissing(request.getParameter("contentAbstract"))) {
			return String.valueOf(_Content.getContentAbstract());
        }
        if (!isMissing(request.getParameter("content"))) {
			return String.valueOf(_Content.getContent());
        }
        if (!isMissing(request.getParameter("abstractGenerate"))) {
			return String.valueOf(_Content.getAbstractGenerate());
        }
        if (!isMissing(request.getParameter("abstractNo"))) {
			return String.valueOf(_Content.getAbstractNo());
        }
        if (!isMissing(request.getParameter("useExternalData"))) {
			return String.valueOf(_Content.getUseExternalData());
        }
        if (!isMissing(request.getParameter("authorName"))) {
			return String.valueOf(_Content.getAuthorName());
        }
        if (!isMissing(request.getParameter("category"))) {
			return String.valueOf(_Content.getCategory());
        }
        if (!isMissing(request.getParameter("createdTime"))) {
			return String.valueOf(_Content.getCreatedTime());
        }
        if (!isMissing(request.getParameter("updatedTime"))) {
			return String.valueOf(_Content.getUpdatedTime());
        }
        if (!isMissing(request.getParameter("contentType"))) {
			return String.valueOf(_Content.getContentType());
        }
        if (!isMissing(request.getParameter("sourceName"))) {
			return String.valueOf(_Content.getSourceName());
        }
        if (!isMissing(request.getParameter("sourceUrl"))) {
			return String.valueOf(_Content.getSourceUrl());
        }
        if (!isMissing(request.getParameter("hide"))) {
			return String.valueOf(_Content.getHide());
        }
        if (!isMissing(request.getParameter("showHome"))) {
			return String.valueOf(_Content.getShowHome());
        }
        if (!isMissing(request.getParameter("imageUrl"))) {
			return String.valueOf(_Content.getImageUrl());
        }
        if (!isMissing(request.getParameter("imageHeight"))) {
			return String.valueOf(_Content.getImageHeight());
        }
        if (!isMissing(request.getParameter("imageWidth"))) {
			return String.valueOf(_Content.getImageWidth());
        }
        if (!isMissing(request.getParameter("inHtml"))) {
			return String.valueOf(_Content.getInHtml());
        }
        if (!isMissing(request.getParameter("tags"))) {
			return String.valueOf(_Content.getTags());
        }
        if (!isMissing(request.getParameter("extraKeywords"))) {
			return String.valueOf(_Content.getExtraKeywords());
        }
        if (!isMissing(request.getParameter("shortcutUrl"))) {
			return String.valueOf(_Content.getShortcutUrl());
        }
        if (!isMissing(request.getParameter("headerMeta"))) {
			return String.valueOf(_Content.getHeaderMeta());
        }
        if (!isMissing(request.getParameter("headerLink"))) {
			return String.valueOf(_Content.getHeaderLink());
        }
        if (!isMissing(request.getParameter("columnNum"))) {
			return String.valueOf(_Content.getColumnNum());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(Content _Content) throws Exception {
    }

    protected String getFieldByName(String fieldName, Content _Content) {
        if (fieldName == null || fieldName.equals("")|| _Content == null) return null;
        
        if (fieldName.equals("pageId")) {
            return WebUtil.display(_Content.getPageId());
        }
        if (fieldName.equals("subPageId")) {
            return WebUtil.display(_Content.getSubPageId());
        }
        if (fieldName.equals("panelId")) {
            return WebUtil.display(_Content.getPanelId());
        }
        if (fieldName.equals("categoryId")) {
            return WebUtil.display(_Content.getCategoryId());
        }
        if (fieldName.equals("userId")) {
            return WebUtil.display(_Content.getUserId());
        }
        if (fieldName.equals("contentSubject")) {
            return WebUtil.display(_Content.getContentSubject());
        }
        if (fieldName.equals("contentAbstract")) {
            return WebUtil.display(_Content.getContentAbstract());
        }
        if (fieldName.equals("content")) {
            return WebUtil.display(_Content.getContent());
        }
        if (fieldName.equals("abstractGenerate")) {
            return WebUtil.display(_Content.getAbstractGenerate());
        }
        if (fieldName.equals("abstractNo")) {
            return WebUtil.display(_Content.getAbstractNo());
        }
        if (fieldName.equals("useExternalData")) {
            return WebUtil.display(_Content.getUseExternalData());
        }
        if (fieldName.equals("authorName")) {
            return WebUtil.display(_Content.getAuthorName());
        }
        if (fieldName.equals("category")) {
            return WebUtil.display(_Content.getCategory());
        }
        if (fieldName.equals("createdTime")) {
            return WebUtil.display(_Content.getCreatedTime());
        }
        if (fieldName.equals("updatedTime")) {
            return WebUtil.display(_Content.getUpdatedTime());
        }
        if (fieldName.equals("contentType")) {
            return WebUtil.display(_Content.getContentType());
        }
        if (fieldName.equals("sourceName")) {
            return WebUtil.display(_Content.getSourceName());
        }
        if (fieldName.equals("sourceUrl")) {
            return WebUtil.display(_Content.getSourceUrl());
        }
        if (fieldName.equals("hide")) {
            return WebUtil.display(_Content.getHide());
        }
        if (fieldName.equals("showHome")) {
            return WebUtil.display(_Content.getShowHome());
        }
        if (fieldName.equals("imageUrl")) {
            return WebUtil.display(_Content.getImageUrl());
        }
        if (fieldName.equals("imageHeight")) {
            return WebUtil.display(_Content.getImageHeight());
        }
        if (fieldName.equals("imageWidth")) {
            return WebUtil.display(_Content.getImageWidth());
        }
        if (fieldName.equals("inHtml")) {
            return WebUtil.display(_Content.getInHtml());
        }
        if (fieldName.equals("tags")) {
            return WebUtil.display(_Content.getTags());
        }
        if (fieldName.equals("extraKeywords")) {
            return WebUtil.display(_Content.getExtraKeywords());
        }
        if (fieldName.equals("shortcutUrl")) {
            return WebUtil.display(_Content.getShortcutUrl());
        }
        if (fieldName.equals("headerMeta")) {
            return WebUtil.display(_Content.getHeaderMeta());
        }
        if (fieldName.equals("headerLink")) {
            return WebUtil.display(_Content.getHeaderLink());
        }
        if (fieldName.equals("columnNum")) {
            return WebUtil.display(_Content.getColumnNum());
        }
		return null;
    }



    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        
        //         // Request Processing 
        // 
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                ex(mapping, form, request, response, true);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing gethtml getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            Content _Content = ContentDS.getInstance().getById(id);
            if (_Content == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _Content);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            Content _Content = ContentDS.getInstance().getById(id);
            if ( _Content == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _Content);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/contentAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("pageId")) {
                String value = WebUtil.display(request.getParameter("pageId"));

                if ( forceHiddenSet.contains("pageId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageId\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageId\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subPageId")) {
                String value = WebUtil.display(request.getParameter("subPageId"));

                if ( forceHiddenSet.contains("subPageId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subPageId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Sub Page Id</div>");
            buf.append("<INPUT NAME=\"subPageId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
                String value = WebUtil.display(request.getParameter("panelId"));

                if ( forceHiddenSet.contains("panelId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"panelId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Panel Id</div>");
            buf.append("<INPUT NAME=\"panelId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
                String value = WebUtil.display(request.getParameter("categoryId"));

                if ( forceHiddenSet.contains("categoryId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"categoryId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Category Id</div>");
            buf.append("<INPUT NAME=\"categoryId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("userId")) {
                String value = WebUtil.display(request.getParameter("userId"));

                if ( forceHiddenSet.contains("userId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"userId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">User Id</div>");
            buf.append("<INPUT NAME=\"userId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentSubject")) {
                String value = WebUtil.display(request.getParameter("contentSubject"));

                if ( forceHiddenSet.contains("contentSubject")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentSubject\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Subject</div>");
            buf.append("<INPUT NAME=\"contentSubject\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentAbstract")) {
                String value = WebUtil.display(request.getParameter("contentAbstract"));

                if ( forceHiddenSet.contains("contentAbstract")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentAbstract\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Abstract</div>");
            buf.append("<TEXTAREA NAME=\"contentAbstract\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("content")) {
                String value = WebUtil.display(request.getParameter("content"));

                if ( forceHiddenSet.contains("content")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"content\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content</div>");
            buf.append("<INPUT NAME=\"content\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("abstractGenerate")) {
                String value = WebUtil.display(request.getParameter("abstractGenerate"));

                if ( forceHiddenSet.contains("abstractGenerate")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"abstractGenerate\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Abstract Generate</div>");
            buf.append("<select name=\"abstractGenerate\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("abstractNo")) {
                String value = WebUtil.display(request.getParameter("abstractNo"));

                if ( forceHiddenSet.contains("abstractNo")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"abstractNo\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Abstract No</div>");
            buf.append("<select name=\"abstractNo\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("useExternalData")) {
                String value = WebUtil.display(request.getParameter("useExternalData"));

                if ( forceHiddenSet.contains("useExternalData")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"useExternalData\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Use External Data</div>");
            buf.append("<select name=\"useExternalData\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("authorName")) {
                String value = WebUtil.display(request.getParameter("authorName"));

                if ( forceHiddenSet.contains("authorName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"authorName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Author Name</div>");
            buf.append("<INPUT NAME=\"authorName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("category")) {
                String value = WebUtil.display(request.getParameter("category"));

                if ( forceHiddenSet.contains("category")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"category\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Category</div>");
            buf.append("<INPUT NAME=\"category\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
                String value = WebUtil.display(request.getParameter("createdTime"));

                if ( forceHiddenSet.contains("createdTime")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"createdTime\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("updatedTime")) {
                String value = WebUtil.display(request.getParameter("updatedTime"));

                if ( forceHiddenSet.contains("updatedTime")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"updatedTime\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Updated Time</div>");
            buf.append("<INPUT NAME=\"updatedTime\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
                String value = WebUtil.display(request.getParameter("contentType"));

                if ( forceHiddenSet.contains("contentType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Type</div>");
            buf.append("<INPUT NAME=\"contentType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("sourceName")) {
                String value = WebUtil.display(request.getParameter("sourceName"));

                if ( forceHiddenSet.contains("sourceName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"sourceName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Source Name</div>");
            buf.append("<INPUT NAME=\"sourceName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("sourceUrl")) {
                String value = WebUtil.display(request.getParameter("sourceUrl"));

                if ( forceHiddenSet.contains("sourceUrl")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"sourceUrl\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Source Url</div>");
            buf.append("<INPUT NAME=\"sourceUrl\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hide")) {
                String value = WebUtil.display(request.getParameter("hide"));

                if ( forceHiddenSet.contains("hide")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hide\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide</div>");
            buf.append("<INPUT NAME=\"hide\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("showHome")) {
                String value = WebUtil.display(request.getParameter("showHome"));

                if ( forceHiddenSet.contains("showHome")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"showHome\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Show Home</div>");
            buf.append("<INPUT NAME=\"showHome\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imageUrl")) {
                String value = WebUtil.display(request.getParameter("imageUrl"));

                if ( forceHiddenSet.contains("imageUrl")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imageUrl\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Url</div>");
            buf.append("<INPUT NAME=\"imageUrl\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imageHeight")) {
                String value = WebUtil.display(request.getParameter("imageHeight"));

                if ( forceHiddenSet.contains("imageHeight")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imageHeight\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Height</div>");
            buf.append("<INPUT NAME=\"imageHeight\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imageWidth")) {
                String value = WebUtil.display(request.getParameter("imageWidth"));

                if ( forceHiddenSet.contains("imageWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imageWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Width</div>");
            buf.append("<INPUT NAME=\"imageWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("inHtml")) {
                String value = WebUtil.display(request.getParameter("inHtml"));

                if ( forceHiddenSet.contains("inHtml")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"inHtml\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">In Html</div>");
            buf.append("<INPUT NAME=\"inHtml\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                String value = WebUtil.display(request.getParameter("tags"));

                if ( forceHiddenSet.contains("tags")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tags\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tags</div>");
            buf.append("<INPUT NAME=\"tags\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("extraKeywords")) {
                String value = WebUtil.display(request.getParameter("extraKeywords"));

                if ( forceHiddenSet.contains("extraKeywords")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"extraKeywords\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Extra Keywords</div>");
            buf.append("<INPUT NAME=\"extraKeywords\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("shortcutUrl")) {
                String value = WebUtil.display(request.getParameter("shortcutUrl"));

                if ( forceHiddenSet.contains("shortcutUrl")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"shortcutUrl\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Shortcut Url</div>");
            buf.append("<INPUT NAME=\"shortcutUrl\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
                String value = WebUtil.display(request.getParameter("headerMeta"));

                if ( forceHiddenSet.contains("headerMeta")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"headerMeta\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Header Meta</div>");
            buf.append("<INPUT NAME=\"headerMeta\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
                String value = WebUtil.display(request.getParameter("headerLink"));

                if ( forceHiddenSet.contains("headerLink")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"headerLink\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Header Link</div>");
            buf.append("<INPUT NAME=\"headerLink\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("columnNum")) {
                String value = WebUtil.display(request.getParameter("columnNum"));

                if ( forceHiddenSet.contains("columnNum")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"columnNum\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Column Num</div>");
            buf.append("<INPUT NAME=\"columnNum\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/contentAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2")){
            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
	        StringBuffer buf = new StringBuffer();
			Site site = SiteDS.getInstance().registerSite(request.getServerName());

            String importedScripts = null;
            try {
                importedScripts = FileUtil.loadCodesToString("./inline_script.txt");
                m_logger.debug(importedScripts);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                importedScripts = "";
            }

            importedScripts += "\n";
            importedScripts += "function responseCallback(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayContent\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest(){\n";
            importedScripts +=     "xmlhttpPostXX('contentFormAddDis','/contentAction.html', 'resultDisplayContent', '${ajax_response_fields}', responseCallback);\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"contentFormAddDis\" method=\"POST\" action=\"/contentAction.html\" id=\"contentFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"pageId\" NAME=\"pageId\" value=\"\" />");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Sub Page Id</div>");
        buf.append("<input class=\"field\" id=\"subPageId\" type=\"text\" size=\"70\" name=\"subPageId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Panel Id</div>");
        buf.append("<input class=\"field\" id=\"panelId\" type=\"text\" size=\"70\" name=\"panelId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Category Id</div>");
        buf.append("<input class=\"field\" id=\"categoryId\" type=\"text\" size=\"70\" name=\"categoryId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> User Id</div>");
        buf.append("<input class=\"field\" id=\"userId\" type=\"text\" size=\"70\" name=\"userId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Subject</div>");
        buf.append("<input class=\"field\" id=\"contentSubject\" type=\"text\" size=\"70\" name=\"contentSubject\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Abstract</div>");
		buf.append("<TEXTAREA id=\"contentAbstract\" class=\"field\" NAME=\"contentAbstract\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content</div>");
        buf.append("<input class=\"field\" id=\"content\" type=\"text\" size=\"70\" name=\"content\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Abstract Generate</div>");
        buf.append("<select name=\"abstractGenerate\" id=\"abstractGenerate\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Abstract No</div>");
        buf.append("<select name=\"abstractNo\" id=\"abstractNo\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Use External Data</div>");
        buf.append("<select name=\"useExternalData\" id=\"useExternalData\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Author Name</div>");
        buf.append("<input class=\"field\" id=\"authorName\" type=\"text\" size=\"70\" name=\"authorName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Category</div>");
        buf.append("<input class=\"requiredField\" id=\"category\" type=\"text\" size=\"70\" name=\"category\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Updated Time</div>");
        buf.append("<input class=\"field\" id=\"updatedTime\" type=\"text\" size=\"70\" name=\"updatedTime\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Type</div>");
        buf.append("<input class=\"field\" id=\"contentType\" type=\"text\" size=\"70\" name=\"contentType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Source Name</div>");
        buf.append("<input class=\"field\" id=\"sourceName\" type=\"text\" size=\"70\" name=\"sourceName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Source Url</div>");
        buf.append("<input class=\"field\" id=\"sourceUrl\" type=\"text\" size=\"70\" name=\"sourceUrl\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Hide</div>");
        buf.append("<input class=\"field\" id=\"hide\" type=\"text\" size=\"70\" name=\"hide\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Show Home</div>");
        buf.append("<input class=\"field\" id=\"showHome\" type=\"text\" size=\"70\" name=\"showHome\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Url</div>");
        buf.append("<input class=\"field\" id=\"imageUrl\" type=\"text\" size=\"70\" name=\"imageUrl\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Height</div>");
        buf.append("<input class=\"field\" id=\"imageHeight\" type=\"text\" size=\"70\" name=\"imageHeight\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Width</div>");
        buf.append("<input class=\"field\" id=\"imageWidth\" type=\"text\" size=\"70\" name=\"imageWidth\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> In Html</div>");
        buf.append("<input class=\"field\" id=\"inHtml\" type=\"text\" size=\"70\" name=\"inHtml\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Tags</div>");
        buf.append("<input class=\"field\" id=\"tags\" type=\"text\" size=\"70\" name=\"tags\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Extra Keywords</div>");
        buf.append("<input class=\"field\" id=\"extraKeywords\" type=\"text\" size=\"70\" name=\"extraKeywords\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Shortcut Url</div>");
        buf.append("<input class=\"field\" id=\"shortcutUrl\" type=\"text\" size=\"70\" name=\"shortcutUrl\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Header Meta</div>");
        buf.append("<input class=\"field\" id=\"headerMeta\" type=\"text\" size=\"70\" name=\"headerMeta\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Header Link</div>");
        buf.append("<input class=\"field\" id=\"headerLink\" type=\"text\" size=\"70\" name=\"headerLink\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Column Num</div>");
        buf.append("<input class=\"field\" id=\"columnNum\" type=\"text\" size=\"70\" name=\"columnNum\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest()\">Submit</a><br>");
			buf.append("<a href=\"javascript:clearFormXX(\\'contentFormAddDis\\')\">Clear Form</a><br>");
		    buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayContent\"></span>");
			buf.append("<a href=\"javascript:backToXX(\\'contentFormAddDis\\',\\'resultDisplayContent\\')\">show back</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            m_logger.debug("Ajax Processing gethtml getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();

            buf.append("<div id=\"content-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Content _Content = (Content) iterator.next();

                buf.append("<div id=\"content-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("subPageId")) {
                    buf.append("<div id=\"content-ajax-subPageId\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getSubPageId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
                    buf.append("<div id=\"content-ajax-panelId\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getPanelId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
                    buf.append("<div id=\"content-ajax-categoryId\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getCategoryId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("userId")) {
                    buf.append("<div id=\"content-ajax-userId\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getUserId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentSubject")) {
                    buf.append("<div id=\"content-ajax-contentSubject\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getContentSubject()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentAbstract")) {
                    buf.append("<div id=\"content-ajax-contentAbstract\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getContentAbstract()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("content")) {
                    buf.append("<div id=\"content-ajax-content\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getContent()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("abstractGenerate")) {
                    buf.append("<div id=\"content-ajax-abstractGenerate\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getAbstractGenerate()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("abstractNo")) {
                    buf.append("<div id=\"content-ajax-abstractNo\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getAbstractNo()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("useExternalData")) {
                    buf.append("<div id=\"content-ajax-useExternalData\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getUseExternalData()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("authorName")) {
                    buf.append("<div id=\"content-ajax-authorName\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getAuthorName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("category")) {
                    buf.append("<div id=\"content-ajax-category\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getCategory()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
                    buf.append("<div id=\"content-ajax-createdTime\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getCreatedTime()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("updatedTime")) {
                    buf.append("<div id=\"content-ajax-updatedTime\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getUpdatedTime()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
                    buf.append("<div id=\"content-ajax-contentType\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getContentType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("sourceName")) {
                    buf.append("<div id=\"content-ajax-sourceName\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getSourceName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("sourceUrl")) {
                    buf.append("<div id=\"content-ajax-sourceUrl\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getSourceUrl()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hide")) {
                    buf.append("<div id=\"content-ajax-hide\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getHide()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("showHome")) {
                    buf.append("<div id=\"content-ajax-showHome\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getShowHome()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imageUrl")) {
                    buf.append("<div id=\"content-ajax-imageUrl\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getImageUrl()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imageHeight")) {
                    buf.append("<div id=\"content-ajax-imageHeight\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getImageHeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imageWidth")) {
                    buf.append("<div id=\"content-ajax-imageWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getImageWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("inHtml")) {
                    buf.append("<div id=\"content-ajax-inHtml\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getInHtml()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                    buf.append("<div id=\"content-ajax-tags\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getTags()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("extraKeywords")) {
                    buf.append("<div id=\"content-ajax-extraKeywords\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getExtraKeywords()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("shortcutUrl")) {
                    buf.append("<div id=\"content-ajax-shortcutUrl\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getShortcutUrl()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
                    buf.append("<div id=\"content-ajax-headerMeta\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getHeaderMeta()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
                    buf.append("<div id=\"content-ajax-headerLink\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getHeaderLink()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("columnNum")) {
                    buf.append("<div id=\"content-ajax-columnNum\" "+itemClass+">");
                    buf.append(WebUtil.display(_Content.getColumnNum()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("subPageId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Sub Page Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Panel Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Category Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("userId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("User Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentSubject")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Subject");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentAbstract")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Abstract");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("content")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("abstractGenerate")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Abstract Generate");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("abstractNo")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Abstract No");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("useExternalData")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Use External Data");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("authorName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Author Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("category")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Category");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Created Time");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("updatedTime")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Updated Time");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("sourceName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Source Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("sourceUrl")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Source Url");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hide")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("showHome")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Show Home");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imageUrl")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Url");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imageHeight")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Height");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imageWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("inHtml")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("In Html");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("tags")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Tags");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("extraKeywords")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Extra Keywords");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("shortcutUrl")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Shortcut Url");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Header Meta");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Header Link");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("columnNum")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Column Num");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Content _Content = (Content) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("subPageId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getSubPageId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getPanelId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("categoryId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getCategoryId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("userId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getUserId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentSubject")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getContentSubject()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentAbstract")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getContentAbstract()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("content")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getContent()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("abstractGenerate")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Content.getAbstractGenerate()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/contentAction.html?ef=true&id="+ _Content.getId()+"&abstractGenerate=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/contentAction.html?ef=true&id="+ _Content.getId()+"&abstractGenerate=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("abstractNo")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Content.getAbstractNo()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/contentAction.html?ef=true&id="+ _Content.getId()+"&abstractNo=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/contentAction.html?ef=true&id="+ _Content.getId()+"&abstractNo=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("useExternalData")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Content.getUseExternalData()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/contentAction.html?ef=true&id="+ _Content.getId()+"&useExternalData=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/contentAction.html?ef=true&id="+ _Content.getId()+"&useExternalData=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("authorName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getAuthorName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("category")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getCategory()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getCreatedTime()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("updatedTime")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getUpdatedTime()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getContentType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("sourceName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getSourceName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("sourceUrl")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getSourceUrl()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hide")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getHide()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("showHome")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getShowHome()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imageUrl")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getImageUrl()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imageHeight")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getImageHeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imageWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getImageWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("inHtml")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getInHtml()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("tags")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getTags()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("extraKeywords")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getExtraKeywords()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("shortcutUrl")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getShortcutUrl()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getHeaderMeta()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("headerLink")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getHeaderLink()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("columnNum")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Content.getColumnNum()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            Content _Content = null; 
            List list = ContentDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _Content = (Content) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _Content = (Content) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _Content = ContentDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last Content=" + _Content.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _Content = (Content) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("pageId")) 
			            json.put("pageId", ""+_Content.getPageId());
		            if ( ignoreFieldSet || fieldSet.contains("subPageId")) 
			            json.put("subPageId", ""+_Content.getSubPageId());
		            if ( ignoreFieldSet || fieldSet.contains("panelId")) 
			            json.put("panelId", ""+_Content.getPanelId());
		            if ( ignoreFieldSet || fieldSet.contains("categoryId")) 
			            json.put("categoryId", ""+_Content.getCategoryId());
		            if ( ignoreFieldSet || fieldSet.contains("userId")) 
			            json.put("userId", ""+_Content.getUserId());
		            if ( ignoreFieldSet || fieldSet.contains("contentSubject")) 
			            json.put("contentSubject", ""+_Content.getContentSubject());
		            if ( ignoreFieldSet || fieldSet.contains("contentAbstract")) 
			            json.put("contentAbstract", ""+_Content.getContentAbstract());
		            if ( ignoreFieldSet || fieldSet.contains("content")) 
			            json.put("content", ""+_Content.getContent());
		            if ( ignoreFieldSet || fieldSet.contains("abstractGenerate")) 
			            json.put("abstractGenerate", ""+_Content.getAbstractGenerate());
		            if ( ignoreFieldSet || fieldSet.contains("abstractNo")) 
			            json.put("abstractNo", ""+_Content.getAbstractNo());
		            if ( ignoreFieldSet || fieldSet.contains("useExternalData")) 
			            json.put("useExternalData", ""+_Content.getUseExternalData());
		            if ( ignoreFieldSet || fieldSet.contains("authorName")) 
			            json.put("authorName", ""+_Content.getAuthorName());
		            if ( ignoreFieldSet || fieldSet.contains("category")) 
			            json.put("category", ""+_Content.getCategory());
		            if ( ignoreFieldSet || fieldSet.contains("updatedTime")) 
			            json.put("updatedTime", ""+_Content.getUpdatedTime());
		            if ( ignoreFieldSet || fieldSet.contains("contentType")) 
			            json.put("contentType", ""+_Content.getContentType());
		            if ( ignoreFieldSet || fieldSet.contains("sourceName")) 
			            json.put("sourceName", ""+_Content.getSourceName());
		            if ( ignoreFieldSet || fieldSet.contains("sourceUrl")) 
			            json.put("sourceUrl", ""+_Content.getSourceUrl());
		            if ( ignoreFieldSet || fieldSet.contains("hide")) 
			            json.put("hide", ""+_Content.getHide());
		            if ( ignoreFieldSet || fieldSet.contains("showHome")) 
			            json.put("showHome", ""+_Content.getShowHome());
		            if ( ignoreFieldSet || fieldSet.contains("imageUrl")) 
			            json.put("imageUrl", ""+_Content.getImageUrl());
		            if ( ignoreFieldSet || fieldSet.contains("imageHeight")) 
			            json.put("imageHeight", ""+_Content.getImageHeight());
		            if ( ignoreFieldSet || fieldSet.contains("imageWidth")) 
			            json.put("imageWidth", ""+_Content.getImageWidth());
		            if ( ignoreFieldSet || fieldSet.contains("inHtml")) 
			            json.put("inHtml", ""+_Content.getInHtml());
		            if ( ignoreFieldSet || fieldSet.contains("tags")) 
			            json.put("tags", ""+_Content.getTags());
		            if ( ignoreFieldSet || fieldSet.contains("extraKeywords")) 
			            json.put("extraKeywords", ""+_Content.getExtraKeywords());
		            if ( ignoreFieldSet || fieldSet.contains("shortcutUrl")) 
			            json.put("shortcutUrl", ""+_Content.getShortcutUrl());
		            if ( ignoreFieldSet || fieldSet.contains("headerMeta")) 
			            json.put("headerMeta", ""+_Content.getHeaderMeta());
		            if ( ignoreFieldSet || fieldSet.contains("headerLink")) 
			            json.put("headerLink", ""+_Content.getHeaderLink());
		            if ( ignoreFieldSet || fieldSet.contains("columnNum")) 
			            json.put("columnNum", ""+_Content.getColumnNum());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_Content.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonPageId = new JSONObject();
	            jsonPageId.put("name", "pageId");
	            jsonPageId.put("value", ""+_Content.getPageId());
	            array.put(jsonPageId);

	            JSONObject jsonSubPageId = new JSONObject();
	            jsonSubPageId.put("name", "subPageId");
	            jsonSubPageId.put("value", ""+_Content.getSubPageId());
	            array.put(jsonSubPageId);

	            JSONObject jsonPanelId = new JSONObject();
	            jsonPanelId.put("name", "panelId");
	            jsonPanelId.put("value", ""+_Content.getPanelId());
	            array.put(jsonPanelId);

	            JSONObject jsonCategoryId = new JSONObject();
	            jsonCategoryId.put("name", "categoryId");
	            jsonCategoryId.put("value", ""+_Content.getCategoryId());
	            array.put(jsonCategoryId);

	            JSONObject jsonUserId = new JSONObject();
	            jsonUserId.put("name", "userId");
	            jsonUserId.put("value", ""+_Content.getUserId());
	            array.put(jsonUserId);

	            JSONObject jsonContentSubject = new JSONObject();
	            jsonContentSubject.put("name", "contentSubject");
	            jsonContentSubject.put("value", ""+_Content.getContentSubject());
	            array.put(jsonContentSubject);

	            JSONObject jsonContentAbstract = new JSONObject();
	            jsonContentAbstract.put("name", "contentAbstract");
	            jsonContentAbstract.put("value", ""+_Content.getContentAbstract());
	            array.put(jsonContentAbstract);

	            JSONObject jsonContent = new JSONObject();
	            jsonContent.put("name", "content");
	            jsonContent.put("value", ""+_Content.getContent());
	            array.put(jsonContent);

	            JSONObject jsonAbstractGenerate = new JSONObject();
	            jsonAbstractGenerate.put("name", "abstractGenerate");
	            jsonAbstractGenerate.put("value", ""+_Content.getAbstractGenerate());
	            array.put(jsonAbstractGenerate);

	            JSONObject jsonAbstractNo = new JSONObject();
	            jsonAbstractNo.put("name", "abstractNo");
	            jsonAbstractNo.put("value", ""+_Content.getAbstractNo());
	            array.put(jsonAbstractNo);

	            JSONObject jsonUseExternalData = new JSONObject();
	            jsonUseExternalData.put("name", "useExternalData");
	            jsonUseExternalData.put("value", ""+_Content.getUseExternalData());
	            array.put(jsonUseExternalData);

	            JSONObject jsonAuthorName = new JSONObject();
	            jsonAuthorName.put("name", "authorName");
	            jsonAuthorName.put("value", ""+_Content.getAuthorName());
	            array.put(jsonAuthorName);

	            JSONObject jsonCategory = new JSONObject();
	            jsonCategory.put("name", "category");
	            jsonCategory.put("value", ""+_Content.getCategory());
	            array.put(jsonCategory);

	            JSONObject jsonUpdatedTime = new JSONObject();
	            jsonUpdatedTime.put("name", "updatedTime");
	            jsonUpdatedTime.put("value", ""+_Content.getUpdatedTime());
	            array.put(jsonUpdatedTime);

	            JSONObject jsonContentType = new JSONObject();
	            jsonContentType.put("name", "contentType");
	            jsonContentType.put("value", ""+_Content.getContentType());
	            array.put(jsonContentType);

	            JSONObject jsonSourceName = new JSONObject();
	            jsonSourceName.put("name", "sourceName");
	            jsonSourceName.put("value", ""+_Content.getSourceName());
	            array.put(jsonSourceName);

	            JSONObject jsonSourceUrl = new JSONObject();
	            jsonSourceUrl.put("name", "sourceUrl");
	            jsonSourceUrl.put("value", ""+_Content.getSourceUrl());
	            array.put(jsonSourceUrl);

	            JSONObject jsonHide = new JSONObject();
	            jsonHide.put("name", "hide");
	            jsonHide.put("value", ""+_Content.getHide());
	            array.put(jsonHide);

	            JSONObject jsonShowHome = new JSONObject();
	            jsonShowHome.put("name", "showHome");
	            jsonShowHome.put("value", ""+_Content.getShowHome());
	            array.put(jsonShowHome);

	            JSONObject jsonImageUrl = new JSONObject();
	            jsonImageUrl.put("name", "imageUrl");
	            jsonImageUrl.put("value", ""+_Content.getImageUrl());
	            array.put(jsonImageUrl);

	            JSONObject jsonImageHeight = new JSONObject();
	            jsonImageHeight.put("name", "imageHeight");
	            jsonImageHeight.put("value", ""+_Content.getImageHeight());
	            array.put(jsonImageHeight);

	            JSONObject jsonImageWidth = new JSONObject();
	            jsonImageWidth.put("name", "imageWidth");
	            jsonImageWidth.put("value", ""+_Content.getImageWidth());
	            array.put(jsonImageWidth);

	            JSONObject jsonInHtml = new JSONObject();
	            jsonInHtml.put("name", "inHtml");
	            jsonInHtml.put("value", ""+_Content.getInHtml());
	            array.put(jsonInHtml);

	            JSONObject jsonTags = new JSONObject();
	            jsonTags.put("name", "tags");
	            jsonTags.put("value", ""+_Content.getTags());
	            array.put(jsonTags);

	            JSONObject jsonExtraKeywords = new JSONObject();
	            jsonExtraKeywords.put("name", "extraKeywords");
	            jsonExtraKeywords.put("value", ""+_Content.getExtraKeywords());
	            array.put(jsonExtraKeywords);

	            JSONObject jsonShortcutUrl = new JSONObject();
	            jsonShortcutUrl.put("name", "shortcutUrl");
	            jsonShortcutUrl.put("value", ""+_Content.getShortcutUrl());
	            array.put(jsonShortcutUrl);

	            JSONObject jsonHeaderMeta = new JSONObject();
	            jsonHeaderMeta.put("name", "headerMeta");
	            jsonHeaderMeta.put("value", ""+_Content.getHeaderMeta());
	            array.put(jsonHeaderMeta);

	            JSONObject jsonHeaderLink = new JSONObject();
	            jsonHeaderLink.put("name", "headerLink");
	            jsonHeaderLink.put("value", ""+_Content.getHeaderLink());
	            array.put(jsonHeaderLink);

	            JSONObject jsonColumnNum = new JSONObject();
	            jsonColumnNum.put("name", "columnNum");
	            jsonColumnNum.put("value", ""+_Content.getColumnNum());
	            array.put(jsonColumnNum);


    	        top.put("fields", array);
			}


            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        return ret;
    }


	// Prepares data to be returned in ajax.
    protected List prepareReturnData(HttpServletRequest request){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && request.getParameter("ajaxOut").startsWith("getlist");

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            Content _Content = null; 
            List list = ContentDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _Content = (Content) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _Content = (Content) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _Content = ContentDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_Content);

        } else {
            
            List list = ContentDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected ContentDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentAction.class);
}
