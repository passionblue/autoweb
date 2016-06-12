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

import com.autosite.db.LinkStyleConfig;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.LinkStyleConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


public class LinkStyleConfigAction extends AutositeCoreAction {

    public LinkStyleConfigAction(){
        m_ds = LinkStyleConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        LinkStyleConfigForm _LinkStyleConfigForm = (LinkStyleConfigForm) form;
        HttpSession session = request.getSession();

        setPage(session, "link_style_config_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        
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


        LinkStyleConfig _LinkStyleConfig = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _LinkStyleConfig = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //LinkStyleConfig _LinkStyleConfig = m_ds.getById(cid);

            if (_LinkStyleConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_LinkStyleConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _LinkStyleConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_LinkStyleConfig);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _LinkStyleConfigForm, _LinkStyleConfig);
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
            //LinkStyleConfig _LinkStyleConfig = m_ds.getById(cid);

            if (_LinkStyleConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_LinkStyleConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _LinkStyleConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _LinkStyleConfig);
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
            //LinkStyleConfig _LinkStyleConfig = m_ds.getById(cid);

            if (_LinkStyleConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_LinkStyleConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _LinkStyleConfig.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _LinkStyleConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_LinkStyleConfig);
            try { 
                m_actionExtent.afterDelete(request, response, _LinkStyleConfig);
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

            m_logger.info("Creating new LinkStyleConfig" );
            LinkStyleConfig _LinkStyleConfigNew = new LinkStyleConfig();   

            // Setting IDs for the object
            _LinkStyleConfigNew.setSiteId(site.getId());

            _LinkStyleConfigNew.setStyleKey(WebParamUtil.getStringValue(_LinkStyleConfigForm.getStyleKey()));
            m_logger.debug("setting StyleKey=" +_LinkStyleConfigForm.getStyleKey());
            _LinkStyleConfigNew.setIsGlobal(WebParamUtil.getIntValue(_LinkStyleConfigForm.getIsGlobal()));
            m_logger.debug("setting IsGlobal=" +_LinkStyleConfigForm.getIsGlobal());
            _LinkStyleConfigNew.setIdClass(WebParamUtil.getStringValue(_LinkStyleConfigForm.getIdClass()));
            m_logger.debug("setting IdClass=" +_LinkStyleConfigForm.getIdClass());
            _LinkStyleConfigNew.setIsId(WebParamUtil.getIntValue(_LinkStyleConfigForm.getIsId()));
            m_logger.debug("setting IsId=" +_LinkStyleConfigForm.getIsId());
            _LinkStyleConfigNew.setHeight(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHeight()));
            m_logger.debug("setting Height=" +_LinkStyleConfigForm.getHeight());
            _LinkStyleConfigNew.setWidth(WebParamUtil.getStringValue(_LinkStyleConfigForm.getWidth()));
            m_logger.debug("setting Width=" +_LinkStyleConfigForm.getWidth());
            _LinkStyleConfigNew.setDisplay(WebParamUtil.getStringValue(_LinkStyleConfigForm.getDisplay()));
            m_logger.debug("setting Display=" +_LinkStyleConfigForm.getDisplay());
            _LinkStyleConfigNew.setBorder(WebParamUtil.getStringValue(_LinkStyleConfigForm.getBorder()));
            m_logger.debug("setting Border=" +_LinkStyleConfigForm.getBorder());
            _LinkStyleConfigNew.setBackground(WebParamUtil.getStringValue(_LinkStyleConfigForm.getBackground()));
            m_logger.debug("setting Background=" +_LinkStyleConfigForm.getBackground());
            _LinkStyleConfigNew.setColor(WebParamUtil.getStringValue(_LinkStyleConfigForm.getColor()));
            m_logger.debug("setting Color=" +_LinkStyleConfigForm.getColor());
            _LinkStyleConfigNew.setTextDecoration(WebParamUtil.getStringValue(_LinkStyleConfigForm.getTextDecoration()));
            m_logger.debug("setting TextDecoration=" +_LinkStyleConfigForm.getTextDecoration());
            _LinkStyleConfigNew.setTextAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getTextAlign()));
            m_logger.debug("setting TextAlign=" +_LinkStyleConfigForm.getTextAlign());
            _LinkStyleConfigNew.setVerticalAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getVerticalAlign()));
            m_logger.debug("setting VerticalAlign=" +_LinkStyleConfigForm.getVerticalAlign());
            _LinkStyleConfigNew.setTextIndent(WebParamUtil.getStringValue(_LinkStyleConfigForm.getTextIndent()));
            m_logger.debug("setting TextIndent=" +_LinkStyleConfigForm.getTextIndent());
            _LinkStyleConfigNew.setMargin(WebParamUtil.getStringValue(_LinkStyleConfigForm.getMargin()));
            m_logger.debug("setting Margin=" +_LinkStyleConfigForm.getMargin());
            _LinkStyleConfigNew.setPadding(WebParamUtil.getStringValue(_LinkStyleConfigForm.getPadding()));
            m_logger.debug("setting Padding=" +_LinkStyleConfigForm.getPadding());
            _LinkStyleConfigNew.setFont(WebParamUtil.getStringValue(_LinkStyleConfigForm.getFont()));
            m_logger.debug("setting Font=" +_LinkStyleConfigForm.getFont());
            _LinkStyleConfigNew.setExtraStyle(WebParamUtil.getStringValue(_LinkStyleConfigForm.getExtraStyle()));
            m_logger.debug("setting ExtraStyle=" +_LinkStyleConfigForm.getExtraStyle());
            _LinkStyleConfigNew.setHovHeight(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovHeight()));
            m_logger.debug("setting HovHeight=" +_LinkStyleConfigForm.getHovHeight());
            _LinkStyleConfigNew.setHovWidth(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovWidth()));
            m_logger.debug("setting HovWidth=" +_LinkStyleConfigForm.getHovWidth());
            _LinkStyleConfigNew.setHovDisplay(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovDisplay()));
            m_logger.debug("setting HovDisplay=" +_LinkStyleConfigForm.getHovDisplay());
            _LinkStyleConfigNew.setHovBorder(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovBorder()));
            m_logger.debug("setting HovBorder=" +_LinkStyleConfigForm.getHovBorder());
            _LinkStyleConfigNew.setHovBackground(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovBackground()));
            m_logger.debug("setting HovBackground=" +_LinkStyleConfigForm.getHovBackground());
            _LinkStyleConfigNew.setHovColor(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovColor()));
            m_logger.debug("setting HovColor=" +_LinkStyleConfigForm.getHovColor());
            _LinkStyleConfigNew.setHovTextDecoration(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovTextDecoration()));
            m_logger.debug("setting HovTextDecoration=" +_LinkStyleConfigForm.getHovTextDecoration());
            _LinkStyleConfigNew.setHovTextAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovTextAlign()));
            m_logger.debug("setting HovTextAlign=" +_LinkStyleConfigForm.getHovTextAlign());
            _LinkStyleConfigNew.setHovVerticalAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovVerticalAlign()));
            m_logger.debug("setting HovVerticalAlign=" +_LinkStyleConfigForm.getHovVerticalAlign());
            _LinkStyleConfigNew.setHovTextIndent(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovTextIndent()));
            m_logger.debug("setting HovTextIndent=" +_LinkStyleConfigForm.getHovTextIndent());
            _LinkStyleConfigNew.setHovMargin(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovMargin()));
            m_logger.debug("setting HovMargin=" +_LinkStyleConfigForm.getHovMargin());
            _LinkStyleConfigNew.setHovPadding(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovPadding()));
            m_logger.debug("setting HovPadding=" +_LinkStyleConfigForm.getHovPadding());
            _LinkStyleConfigNew.setHovFont(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovFont()));
            m_logger.debug("setting HovFont=" +_LinkStyleConfigForm.getHovFont());
            _LinkStyleConfigNew.setHovExtraStyle(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovExtraStyle()));
            m_logger.debug("setting HovExtraStyle=" +_LinkStyleConfigForm.getHovExtraStyle());
            _LinkStyleConfigNew.setActiveUse(WebParamUtil.getIntValue(_LinkStyleConfigForm.getActiveUse()));
            m_logger.debug("setting ActiveUse=" +_LinkStyleConfigForm.getActiveUse());
            _LinkStyleConfigNew.setActiveBorder(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveBorder()));
            m_logger.debug("setting ActiveBorder=" +_LinkStyleConfigForm.getActiveBorder());
            _LinkStyleConfigNew.setActiveBackground(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveBackground()));
            m_logger.debug("setting ActiveBackground=" +_LinkStyleConfigForm.getActiveBackground());
            _LinkStyleConfigNew.setActiveColor(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveColor()));
            m_logger.debug("setting ActiveColor=" +_LinkStyleConfigForm.getActiveColor());
            _LinkStyleConfigNew.setActiveTextDecoration(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveTextDecoration()));
            m_logger.debug("setting ActiveTextDecoration=" +_LinkStyleConfigForm.getActiveTextDecoration());
            _LinkStyleConfigNew.setActiveExtraStyle(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveExtraStyle()));
            m_logger.debug("setting ActiveExtraStyle=" +_LinkStyleConfigForm.getActiveExtraStyle());
            _LinkStyleConfigNew.setActiveMargin(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveMargin()));
            m_logger.debug("setting ActiveMargin=" +_LinkStyleConfigForm.getActiveMargin());
            _LinkStyleConfigNew.setActivePadding(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActivePadding()));
            m_logger.debug("setting ActivePadding=" +_LinkStyleConfigForm.getActivePadding());
            _LinkStyleConfigNew.setTimeCreated(WebParamUtil.getDateValue(_LinkStyleConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_LinkStyleConfigForm.getTimeCreated());
            _LinkStyleConfigNew.setTimeUpdated(WebParamUtil.getDateValue(_LinkStyleConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_LinkStyleConfigForm.getTimeUpdated());

            try {
                checkDepedenceIntegrity(_LinkStyleConfigNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _LinkStyleConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_LinkStyleConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_LinkStyleConfigNew);
            try{
                m_actionExtent.afterAdd(request, response, _LinkStyleConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "link_style_config_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, LinkStyleConfigForm _LinkStyleConfigForm, LinkStyleConfig _LinkStyleConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        LinkStyleConfig _LinkStyleConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + LinkStyleConfigDS.objectToString(_LinkStyleConfig));

        _LinkStyleConfig.setStyleKey(WebParamUtil.getStringValue(_LinkStyleConfigForm.getStyleKey()));
        _LinkStyleConfig.setIsGlobal(WebParamUtil.getIntValue(_LinkStyleConfigForm.getIsGlobal()));
        _LinkStyleConfig.setIdClass(WebParamUtil.getStringValue(_LinkStyleConfigForm.getIdClass()));
        _LinkStyleConfig.setIsId(WebParamUtil.getIntValue(_LinkStyleConfigForm.getIsId()));
        _LinkStyleConfig.setHeight(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHeight()));
        _LinkStyleConfig.setWidth(WebParamUtil.getStringValue(_LinkStyleConfigForm.getWidth()));
        _LinkStyleConfig.setDisplay(WebParamUtil.getStringValue(_LinkStyleConfigForm.getDisplay()));
        _LinkStyleConfig.setBorder(WebParamUtil.getStringValue(_LinkStyleConfigForm.getBorder()));
        _LinkStyleConfig.setBackground(WebParamUtil.getStringValue(_LinkStyleConfigForm.getBackground()));
        _LinkStyleConfig.setColor(WebParamUtil.getStringValue(_LinkStyleConfigForm.getColor()));
        _LinkStyleConfig.setTextDecoration(WebParamUtil.getStringValue(_LinkStyleConfigForm.getTextDecoration()));
        _LinkStyleConfig.setTextAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getTextAlign()));
        _LinkStyleConfig.setVerticalAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getVerticalAlign()));
        _LinkStyleConfig.setTextIndent(WebParamUtil.getStringValue(_LinkStyleConfigForm.getTextIndent()));
        _LinkStyleConfig.setMargin(WebParamUtil.getStringValue(_LinkStyleConfigForm.getMargin()));
        _LinkStyleConfig.setPadding(WebParamUtil.getStringValue(_LinkStyleConfigForm.getPadding()));
        _LinkStyleConfig.setFont(WebParamUtil.getStringValue(_LinkStyleConfigForm.getFont()));
        _LinkStyleConfig.setExtraStyle(WebParamUtil.getStringValue(_LinkStyleConfigForm.getExtraStyle()));
        _LinkStyleConfig.setHovHeight(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovHeight()));
        _LinkStyleConfig.setHovWidth(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovWidth()));
        _LinkStyleConfig.setHovDisplay(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovDisplay()));
        _LinkStyleConfig.setHovBorder(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovBorder()));
        _LinkStyleConfig.setHovBackground(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovBackground()));
        _LinkStyleConfig.setHovColor(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovColor()));
        _LinkStyleConfig.setHovTextDecoration(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovTextDecoration()));
        _LinkStyleConfig.setHovTextAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovTextAlign()));
        _LinkStyleConfig.setHovVerticalAlign(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovVerticalAlign()));
        _LinkStyleConfig.setHovTextIndent(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovTextIndent()));
        _LinkStyleConfig.setHovMargin(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovMargin()));
        _LinkStyleConfig.setHovPadding(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovPadding()));
        _LinkStyleConfig.setHovFont(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovFont()));
        _LinkStyleConfig.setHovExtraStyle(WebParamUtil.getStringValue(_LinkStyleConfigForm.getHovExtraStyle()));
        _LinkStyleConfig.setActiveUse(WebParamUtil.getIntValue(_LinkStyleConfigForm.getActiveUse()));
        _LinkStyleConfig.setActiveBorder(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveBorder()));
        _LinkStyleConfig.setActiveBackground(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveBackground()));
        _LinkStyleConfig.setActiveColor(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveColor()));
        _LinkStyleConfig.setActiveTextDecoration(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveTextDecoration()));
        _LinkStyleConfig.setActiveExtraStyle(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveExtraStyle()));
        _LinkStyleConfig.setActiveMargin(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActiveMargin()));
        _LinkStyleConfig.setActivePadding(WebParamUtil.getStringValue(_LinkStyleConfigForm.getActivePadding()));
        _LinkStyleConfig.setTimeUpdated(WebParamUtil.getDateValue(_LinkStyleConfigForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _LinkStyleConfig);
        m_ds.update(_LinkStyleConfig);
        m_actionExtent.afterUpdate(request, response, _LinkStyleConfig);
        m_logger.debug("After update " + LinkStyleConfigDS.objectToString(_LinkStyleConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, LinkStyleConfig _LinkStyleConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        LinkStyleConfig _LinkStyleConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("styleKey"))) {
            m_logger.debug("updating param styleKey from " +_LinkStyleConfig.getStyleKey() + "->" + request.getParameter("styleKey"));
            _LinkStyleConfig.setStyleKey(WebParamUtil.getStringValue(request.getParameter("styleKey")));
        }
        if (!isMissing(request.getParameter("isGlobal"))) {
            m_logger.debug("updating param isGlobal from " +_LinkStyleConfig.getIsGlobal() + "->" + request.getParameter("isGlobal"));
            _LinkStyleConfig.setIsGlobal(WebParamUtil.getIntValue(request.getParameter("isGlobal")));
        }
        if (!isMissing(request.getParameter("idClass"))) {
            m_logger.debug("updating param idClass from " +_LinkStyleConfig.getIdClass() + "->" + request.getParameter("idClass"));
            _LinkStyleConfig.setIdClass(WebParamUtil.getStringValue(request.getParameter("idClass")));
        }
        if (!isMissing(request.getParameter("isId"))) {
            m_logger.debug("updating param isId from " +_LinkStyleConfig.getIsId() + "->" + request.getParameter("isId"));
            _LinkStyleConfig.setIsId(WebParamUtil.getIntValue(request.getParameter("isId")));
        }
        if (!isMissing(request.getParameter("height"))) {
            m_logger.debug("updating param height from " +_LinkStyleConfig.getHeight() + "->" + request.getParameter("height"));
            _LinkStyleConfig.setHeight(WebParamUtil.getStringValue(request.getParameter("height")));
        }
        if (!isMissing(request.getParameter("width"))) {
            m_logger.debug("updating param width from " +_LinkStyleConfig.getWidth() + "->" + request.getParameter("width"));
            _LinkStyleConfig.setWidth(WebParamUtil.getStringValue(request.getParameter("width")));
        }
        if (!isMissing(request.getParameter("display"))) {
            m_logger.debug("updating param display from " +_LinkStyleConfig.getDisplay() + "->" + request.getParameter("display"));
            _LinkStyleConfig.setDisplay(WebParamUtil.getStringValue(request.getParameter("display")));
        }
        if (!isMissing(request.getParameter("border"))) {
            m_logger.debug("updating param border from " +_LinkStyleConfig.getBorder() + "->" + request.getParameter("border"));
            _LinkStyleConfig.setBorder(WebParamUtil.getStringValue(request.getParameter("border")));
        }
        if (!isMissing(request.getParameter("background"))) {
            m_logger.debug("updating param background from " +_LinkStyleConfig.getBackground() + "->" + request.getParameter("background"));
            _LinkStyleConfig.setBackground(WebParamUtil.getStringValue(request.getParameter("background")));
        }
        if (!isMissing(request.getParameter("color"))) {
            m_logger.debug("updating param color from " +_LinkStyleConfig.getColor() + "->" + request.getParameter("color"));
            _LinkStyleConfig.setColor(WebParamUtil.getStringValue(request.getParameter("color")));
        }
        if (!isMissing(request.getParameter("textDecoration"))) {
            m_logger.debug("updating param textDecoration from " +_LinkStyleConfig.getTextDecoration() + "->" + request.getParameter("textDecoration"));
            _LinkStyleConfig.setTextDecoration(WebParamUtil.getStringValue(request.getParameter("textDecoration")));
        }
        if (!isMissing(request.getParameter("textAlign"))) {
            m_logger.debug("updating param textAlign from " +_LinkStyleConfig.getTextAlign() + "->" + request.getParameter("textAlign"));
            _LinkStyleConfig.setTextAlign(WebParamUtil.getStringValue(request.getParameter("textAlign")));
        }
        if (!isMissing(request.getParameter("verticalAlign"))) {
            m_logger.debug("updating param verticalAlign from " +_LinkStyleConfig.getVerticalAlign() + "->" + request.getParameter("verticalAlign"));
            _LinkStyleConfig.setVerticalAlign(WebParamUtil.getStringValue(request.getParameter("verticalAlign")));
        }
        if (!isMissing(request.getParameter("textIndent"))) {
            m_logger.debug("updating param textIndent from " +_LinkStyleConfig.getTextIndent() + "->" + request.getParameter("textIndent"));
            _LinkStyleConfig.setTextIndent(WebParamUtil.getStringValue(request.getParameter("textIndent")));
        }
        if (!isMissing(request.getParameter("margin"))) {
            m_logger.debug("updating param margin from " +_LinkStyleConfig.getMargin() + "->" + request.getParameter("margin"));
            _LinkStyleConfig.setMargin(WebParamUtil.getStringValue(request.getParameter("margin")));
        }
        if (!isMissing(request.getParameter("padding"))) {
            m_logger.debug("updating param padding from " +_LinkStyleConfig.getPadding() + "->" + request.getParameter("padding"));
            _LinkStyleConfig.setPadding(WebParamUtil.getStringValue(request.getParameter("padding")));
        }
        if (!isMissing(request.getParameter("font"))) {
            m_logger.debug("updating param font from " +_LinkStyleConfig.getFont() + "->" + request.getParameter("font"));
            _LinkStyleConfig.setFont(WebParamUtil.getStringValue(request.getParameter("font")));
        }
        if (!isMissing(request.getParameter("extraStyle"))) {
            m_logger.debug("updating param extraStyle from " +_LinkStyleConfig.getExtraStyle() + "->" + request.getParameter("extraStyle"));
            _LinkStyleConfig.setExtraStyle(WebParamUtil.getStringValue(request.getParameter("extraStyle")));
        }
        if (!isMissing(request.getParameter("hovHeight"))) {
            m_logger.debug("updating param hovHeight from " +_LinkStyleConfig.getHovHeight() + "->" + request.getParameter("hovHeight"));
            _LinkStyleConfig.setHovHeight(WebParamUtil.getStringValue(request.getParameter("hovHeight")));
        }
        if (!isMissing(request.getParameter("hovWidth"))) {
            m_logger.debug("updating param hovWidth from " +_LinkStyleConfig.getHovWidth() + "->" + request.getParameter("hovWidth"));
            _LinkStyleConfig.setHovWidth(WebParamUtil.getStringValue(request.getParameter("hovWidth")));
        }
        if (!isMissing(request.getParameter("hovDisplay"))) {
            m_logger.debug("updating param hovDisplay from " +_LinkStyleConfig.getHovDisplay() + "->" + request.getParameter("hovDisplay"));
            _LinkStyleConfig.setHovDisplay(WebParamUtil.getStringValue(request.getParameter("hovDisplay")));
        }
        if (!isMissing(request.getParameter("hovBorder"))) {
            m_logger.debug("updating param hovBorder from " +_LinkStyleConfig.getHovBorder() + "->" + request.getParameter("hovBorder"));
            _LinkStyleConfig.setHovBorder(WebParamUtil.getStringValue(request.getParameter("hovBorder")));
        }
        if (!isMissing(request.getParameter("hovBackground"))) {
            m_logger.debug("updating param hovBackground from " +_LinkStyleConfig.getHovBackground() + "->" + request.getParameter("hovBackground"));
            _LinkStyleConfig.setHovBackground(WebParamUtil.getStringValue(request.getParameter("hovBackground")));
        }
        if (!isMissing(request.getParameter("hovColor"))) {
            m_logger.debug("updating param hovColor from " +_LinkStyleConfig.getHovColor() + "->" + request.getParameter("hovColor"));
            _LinkStyleConfig.setHovColor(WebParamUtil.getStringValue(request.getParameter("hovColor")));
        }
        if (!isMissing(request.getParameter("hovTextDecoration"))) {
            m_logger.debug("updating param hovTextDecoration from " +_LinkStyleConfig.getHovTextDecoration() + "->" + request.getParameter("hovTextDecoration"));
            _LinkStyleConfig.setHovTextDecoration(WebParamUtil.getStringValue(request.getParameter("hovTextDecoration")));
        }
        if (!isMissing(request.getParameter("hovTextAlign"))) {
            m_logger.debug("updating param hovTextAlign from " +_LinkStyleConfig.getHovTextAlign() + "->" + request.getParameter("hovTextAlign"));
            _LinkStyleConfig.setHovTextAlign(WebParamUtil.getStringValue(request.getParameter("hovTextAlign")));
        }
        if (!isMissing(request.getParameter("hovVerticalAlign"))) {
            m_logger.debug("updating param hovVerticalAlign from " +_LinkStyleConfig.getHovVerticalAlign() + "->" + request.getParameter("hovVerticalAlign"));
            _LinkStyleConfig.setHovVerticalAlign(WebParamUtil.getStringValue(request.getParameter("hovVerticalAlign")));
        }
        if (!isMissing(request.getParameter("hovTextIndent"))) {
            m_logger.debug("updating param hovTextIndent from " +_LinkStyleConfig.getHovTextIndent() + "->" + request.getParameter("hovTextIndent"));
            _LinkStyleConfig.setHovTextIndent(WebParamUtil.getStringValue(request.getParameter("hovTextIndent")));
        }
        if (!isMissing(request.getParameter("hovMargin"))) {
            m_logger.debug("updating param hovMargin from " +_LinkStyleConfig.getHovMargin() + "->" + request.getParameter("hovMargin"));
            _LinkStyleConfig.setHovMargin(WebParamUtil.getStringValue(request.getParameter("hovMargin")));
        }
        if (!isMissing(request.getParameter("hovPadding"))) {
            m_logger.debug("updating param hovPadding from " +_LinkStyleConfig.getHovPadding() + "->" + request.getParameter("hovPadding"));
            _LinkStyleConfig.setHovPadding(WebParamUtil.getStringValue(request.getParameter("hovPadding")));
        }
        if (!isMissing(request.getParameter("hovFont"))) {
            m_logger.debug("updating param hovFont from " +_LinkStyleConfig.getHovFont() + "->" + request.getParameter("hovFont"));
            _LinkStyleConfig.setHovFont(WebParamUtil.getStringValue(request.getParameter("hovFont")));
        }
        if (!isMissing(request.getParameter("hovExtraStyle"))) {
            m_logger.debug("updating param hovExtraStyle from " +_LinkStyleConfig.getHovExtraStyle() + "->" + request.getParameter("hovExtraStyle"));
            _LinkStyleConfig.setHovExtraStyle(WebParamUtil.getStringValue(request.getParameter("hovExtraStyle")));
        }
        if (!isMissing(request.getParameter("activeUse"))) {
            m_logger.debug("updating param activeUse from " +_LinkStyleConfig.getActiveUse() + "->" + request.getParameter("activeUse"));
            _LinkStyleConfig.setActiveUse(WebParamUtil.getIntValue(request.getParameter("activeUse")));
        }
        if (!isMissing(request.getParameter("activeBorder"))) {
            m_logger.debug("updating param activeBorder from " +_LinkStyleConfig.getActiveBorder() + "->" + request.getParameter("activeBorder"));
            _LinkStyleConfig.setActiveBorder(WebParamUtil.getStringValue(request.getParameter("activeBorder")));
        }
        if (!isMissing(request.getParameter("activeBackground"))) {
            m_logger.debug("updating param activeBackground from " +_LinkStyleConfig.getActiveBackground() + "->" + request.getParameter("activeBackground"));
            _LinkStyleConfig.setActiveBackground(WebParamUtil.getStringValue(request.getParameter("activeBackground")));
        }
        if (!isMissing(request.getParameter("activeColor"))) {
            m_logger.debug("updating param activeColor from " +_LinkStyleConfig.getActiveColor() + "->" + request.getParameter("activeColor"));
            _LinkStyleConfig.setActiveColor(WebParamUtil.getStringValue(request.getParameter("activeColor")));
        }
        if (!isMissing(request.getParameter("activeTextDecoration"))) {
            m_logger.debug("updating param activeTextDecoration from " +_LinkStyleConfig.getActiveTextDecoration() + "->" + request.getParameter("activeTextDecoration"));
            _LinkStyleConfig.setActiveTextDecoration(WebParamUtil.getStringValue(request.getParameter("activeTextDecoration")));
        }
        if (!isMissing(request.getParameter("activeExtraStyle"))) {
            m_logger.debug("updating param activeExtraStyle from " +_LinkStyleConfig.getActiveExtraStyle() + "->" + request.getParameter("activeExtraStyle"));
            _LinkStyleConfig.setActiveExtraStyle(WebParamUtil.getStringValue(request.getParameter("activeExtraStyle")));
        }
        if (!isMissing(request.getParameter("activeMargin"))) {
            m_logger.debug("updating param activeMargin from " +_LinkStyleConfig.getActiveMargin() + "->" + request.getParameter("activeMargin"));
            _LinkStyleConfig.setActiveMargin(WebParamUtil.getStringValue(request.getParameter("activeMargin")));
        }
        if (!isMissing(request.getParameter("activePadding"))) {
            m_logger.debug("updating param activePadding from " +_LinkStyleConfig.getActivePadding() + "->" + request.getParameter("activePadding"));
            _LinkStyleConfig.setActivePadding(WebParamUtil.getStringValue(request.getParameter("activePadding")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_LinkStyleConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _LinkStyleConfig.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_LinkStyleConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _LinkStyleConfig.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _LinkStyleConfig.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _LinkStyleConfig);
        m_ds.update(_LinkStyleConfig);
        m_actionExtent.afterUpdate(request, response, _LinkStyleConfig);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, LinkStyleConfig _LinkStyleConfig) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        LinkStyleConfig _LinkStyleConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("styleKey"))) {
			return String.valueOf(_LinkStyleConfig.getStyleKey());
        }
        if (!isMissing(request.getParameter("isGlobal"))) {
			return String.valueOf(_LinkStyleConfig.getIsGlobal());
        }
        if (!isMissing(request.getParameter("idClass"))) {
			return String.valueOf(_LinkStyleConfig.getIdClass());
        }
        if (!isMissing(request.getParameter("isId"))) {
			return String.valueOf(_LinkStyleConfig.getIsId());
        }
        if (!isMissing(request.getParameter("height"))) {
			return String.valueOf(_LinkStyleConfig.getHeight());
        }
        if (!isMissing(request.getParameter("width"))) {
			return String.valueOf(_LinkStyleConfig.getWidth());
        }
        if (!isMissing(request.getParameter("display"))) {
			return String.valueOf(_LinkStyleConfig.getDisplay());
        }
        if (!isMissing(request.getParameter("border"))) {
			return String.valueOf(_LinkStyleConfig.getBorder());
        }
        if (!isMissing(request.getParameter("background"))) {
			return String.valueOf(_LinkStyleConfig.getBackground());
        }
        if (!isMissing(request.getParameter("color"))) {
			return String.valueOf(_LinkStyleConfig.getColor());
        }
        if (!isMissing(request.getParameter("textDecoration"))) {
			return String.valueOf(_LinkStyleConfig.getTextDecoration());
        }
        if (!isMissing(request.getParameter("textAlign"))) {
			return String.valueOf(_LinkStyleConfig.getTextAlign());
        }
        if (!isMissing(request.getParameter("verticalAlign"))) {
			return String.valueOf(_LinkStyleConfig.getVerticalAlign());
        }
        if (!isMissing(request.getParameter("textIndent"))) {
			return String.valueOf(_LinkStyleConfig.getTextIndent());
        }
        if (!isMissing(request.getParameter("margin"))) {
			return String.valueOf(_LinkStyleConfig.getMargin());
        }
        if (!isMissing(request.getParameter("padding"))) {
			return String.valueOf(_LinkStyleConfig.getPadding());
        }
        if (!isMissing(request.getParameter("font"))) {
			return String.valueOf(_LinkStyleConfig.getFont());
        }
        if (!isMissing(request.getParameter("extraStyle"))) {
			return String.valueOf(_LinkStyleConfig.getExtraStyle());
        }
        if (!isMissing(request.getParameter("hovHeight"))) {
			return String.valueOf(_LinkStyleConfig.getHovHeight());
        }
        if (!isMissing(request.getParameter("hovWidth"))) {
			return String.valueOf(_LinkStyleConfig.getHovWidth());
        }
        if (!isMissing(request.getParameter("hovDisplay"))) {
			return String.valueOf(_LinkStyleConfig.getHovDisplay());
        }
        if (!isMissing(request.getParameter("hovBorder"))) {
			return String.valueOf(_LinkStyleConfig.getHovBorder());
        }
        if (!isMissing(request.getParameter("hovBackground"))) {
			return String.valueOf(_LinkStyleConfig.getHovBackground());
        }
        if (!isMissing(request.getParameter("hovColor"))) {
			return String.valueOf(_LinkStyleConfig.getHovColor());
        }
        if (!isMissing(request.getParameter("hovTextDecoration"))) {
			return String.valueOf(_LinkStyleConfig.getHovTextDecoration());
        }
        if (!isMissing(request.getParameter("hovTextAlign"))) {
			return String.valueOf(_LinkStyleConfig.getHovTextAlign());
        }
        if (!isMissing(request.getParameter("hovVerticalAlign"))) {
			return String.valueOf(_LinkStyleConfig.getHovVerticalAlign());
        }
        if (!isMissing(request.getParameter("hovTextIndent"))) {
			return String.valueOf(_LinkStyleConfig.getHovTextIndent());
        }
        if (!isMissing(request.getParameter("hovMargin"))) {
			return String.valueOf(_LinkStyleConfig.getHovMargin());
        }
        if (!isMissing(request.getParameter("hovPadding"))) {
			return String.valueOf(_LinkStyleConfig.getHovPadding());
        }
        if (!isMissing(request.getParameter("hovFont"))) {
			return String.valueOf(_LinkStyleConfig.getHovFont());
        }
        if (!isMissing(request.getParameter("hovExtraStyle"))) {
			return String.valueOf(_LinkStyleConfig.getHovExtraStyle());
        }
        if (!isMissing(request.getParameter("activeUse"))) {
			return String.valueOf(_LinkStyleConfig.getActiveUse());
        }
        if (!isMissing(request.getParameter("activeBorder"))) {
			return String.valueOf(_LinkStyleConfig.getActiveBorder());
        }
        if (!isMissing(request.getParameter("activeBackground"))) {
			return String.valueOf(_LinkStyleConfig.getActiveBackground());
        }
        if (!isMissing(request.getParameter("activeColor"))) {
			return String.valueOf(_LinkStyleConfig.getActiveColor());
        }
        if (!isMissing(request.getParameter("activeTextDecoration"))) {
			return String.valueOf(_LinkStyleConfig.getActiveTextDecoration());
        }
        if (!isMissing(request.getParameter("activeExtraStyle"))) {
			return String.valueOf(_LinkStyleConfig.getActiveExtraStyle());
        }
        if (!isMissing(request.getParameter("activeMargin"))) {
			return String.valueOf(_LinkStyleConfig.getActiveMargin());
        }
        if (!isMissing(request.getParameter("activePadding"))) {
			return String.valueOf(_LinkStyleConfig.getActivePadding());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_LinkStyleConfig.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_LinkStyleConfig.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(LinkStyleConfig _LinkStyleConfig) throws Exception {
    }

    protected String getFieldByName(String fieldName, LinkStyleConfig _LinkStyleConfig) {
        if (fieldName == null || fieldName.equals("")|| _LinkStyleConfig == null) return null;
        
        if (fieldName.equals("styleKey")) {
            return WebUtil.display(_LinkStyleConfig.getStyleKey());
        }
        if (fieldName.equals("isGlobal")) {
            return WebUtil.display(_LinkStyleConfig.getIsGlobal());
        }
        if (fieldName.equals("idClass")) {
            return WebUtil.display(_LinkStyleConfig.getIdClass());
        }
        if (fieldName.equals("isId")) {
            return WebUtil.display(_LinkStyleConfig.getIsId());
        }
        if (fieldName.equals("height")) {
            return WebUtil.display(_LinkStyleConfig.getHeight());
        }
        if (fieldName.equals("width")) {
            return WebUtil.display(_LinkStyleConfig.getWidth());
        }
        if (fieldName.equals("display")) {
            return WebUtil.display(_LinkStyleConfig.getDisplay());
        }
        if (fieldName.equals("border")) {
            return WebUtil.display(_LinkStyleConfig.getBorder());
        }
        if (fieldName.equals("background")) {
            return WebUtil.display(_LinkStyleConfig.getBackground());
        }
        if (fieldName.equals("color")) {
            return WebUtil.display(_LinkStyleConfig.getColor());
        }
        if (fieldName.equals("textDecoration")) {
            return WebUtil.display(_LinkStyleConfig.getTextDecoration());
        }
        if (fieldName.equals("textAlign")) {
            return WebUtil.display(_LinkStyleConfig.getTextAlign());
        }
        if (fieldName.equals("verticalAlign")) {
            return WebUtil.display(_LinkStyleConfig.getVerticalAlign());
        }
        if (fieldName.equals("textIndent")) {
            return WebUtil.display(_LinkStyleConfig.getTextIndent());
        }
        if (fieldName.equals("margin")) {
            return WebUtil.display(_LinkStyleConfig.getMargin());
        }
        if (fieldName.equals("padding")) {
            return WebUtil.display(_LinkStyleConfig.getPadding());
        }
        if (fieldName.equals("font")) {
            return WebUtil.display(_LinkStyleConfig.getFont());
        }
        if (fieldName.equals("extraStyle")) {
            return WebUtil.display(_LinkStyleConfig.getExtraStyle());
        }
        if (fieldName.equals("hovHeight")) {
            return WebUtil.display(_LinkStyleConfig.getHovHeight());
        }
        if (fieldName.equals("hovWidth")) {
            return WebUtil.display(_LinkStyleConfig.getHovWidth());
        }
        if (fieldName.equals("hovDisplay")) {
            return WebUtil.display(_LinkStyleConfig.getHovDisplay());
        }
        if (fieldName.equals("hovBorder")) {
            return WebUtil.display(_LinkStyleConfig.getHovBorder());
        }
        if (fieldName.equals("hovBackground")) {
            return WebUtil.display(_LinkStyleConfig.getHovBackground());
        }
        if (fieldName.equals("hovColor")) {
            return WebUtil.display(_LinkStyleConfig.getHovColor());
        }
        if (fieldName.equals("hovTextDecoration")) {
            return WebUtil.display(_LinkStyleConfig.getHovTextDecoration());
        }
        if (fieldName.equals("hovTextAlign")) {
            return WebUtil.display(_LinkStyleConfig.getHovTextAlign());
        }
        if (fieldName.equals("hovVerticalAlign")) {
            return WebUtil.display(_LinkStyleConfig.getHovVerticalAlign());
        }
        if (fieldName.equals("hovTextIndent")) {
            return WebUtil.display(_LinkStyleConfig.getHovTextIndent());
        }
        if (fieldName.equals("hovMargin")) {
            return WebUtil.display(_LinkStyleConfig.getHovMargin());
        }
        if (fieldName.equals("hovPadding")) {
            return WebUtil.display(_LinkStyleConfig.getHovPadding());
        }
        if (fieldName.equals("hovFont")) {
            return WebUtil.display(_LinkStyleConfig.getHovFont());
        }
        if (fieldName.equals("hovExtraStyle")) {
            return WebUtil.display(_LinkStyleConfig.getHovExtraStyle());
        }
        if (fieldName.equals("activeUse")) {
            return WebUtil.display(_LinkStyleConfig.getActiveUse());
        }
        if (fieldName.equals("activeBorder")) {
            return WebUtil.display(_LinkStyleConfig.getActiveBorder());
        }
        if (fieldName.equals("activeBackground")) {
            return WebUtil.display(_LinkStyleConfig.getActiveBackground());
        }
        if (fieldName.equals("activeColor")) {
            return WebUtil.display(_LinkStyleConfig.getActiveColor());
        }
        if (fieldName.equals("activeTextDecoration")) {
            return WebUtil.display(_LinkStyleConfig.getActiveTextDecoration());
        }
        if (fieldName.equals("activeExtraStyle")) {
            return WebUtil.display(_LinkStyleConfig.getActiveExtraStyle());
        }
        if (fieldName.equals("activeMargin")) {
            return WebUtil.display(_LinkStyleConfig.getActiveMargin());
        }
        if (fieldName.equals("activePadding")) {
            return WebUtil.display(_LinkStyleConfig.getActivePadding());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_LinkStyleConfig.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_LinkStyleConfig.getTimeUpdated());
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
            LinkStyleConfig _LinkStyleConfig = LinkStyleConfigDS.getInstance().getById(id);
            if (_LinkStyleConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _LinkStyleConfig);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            LinkStyleConfig _LinkStyleConfig = LinkStyleConfigDS.getInstance().getById(id);
            if ( _LinkStyleConfig == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _LinkStyleConfig);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/linkStyleConfigAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
                String value = WebUtil.display(request.getParameter("styleKey"));

                if ( forceHiddenSet.contains("styleKey")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"styleKey\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Style Key</div>");
            buf.append("<INPUT NAME=\"styleKey\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
                String value = WebUtil.display(request.getParameter("isGlobal"));

                if ( forceHiddenSet.contains("isGlobal")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isGlobal\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Global</div>");
            buf.append("<select name=\"isGlobal\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
                String value = WebUtil.display(request.getParameter("idClass"));

                if ( forceHiddenSet.contains("idClass")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"idClass\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Id Class</div>");
            buf.append("<INPUT NAME=\"idClass\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isId")) {
                String value = WebUtil.display(request.getParameter("isId"));

                if ( forceHiddenSet.contains("isId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Id</div>");
            buf.append("<select name=\"isId\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("height")) {
                String value = WebUtil.display(request.getParameter("height"));

                if ( forceHiddenSet.contains("height")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"height\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Height</div>");
            buf.append("<INPUT NAME=\"height\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("width")) {
                String value = WebUtil.display(request.getParameter("width"));

                if ( forceHiddenSet.contains("width")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"width\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Width</div>");
            buf.append("<INPUT NAME=\"width\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("display")) {
                String value = WebUtil.display(request.getParameter("display"));

                if ( forceHiddenSet.contains("display")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"display\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Display</div>");
            buf.append("<INPUT NAME=\"display\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("border")) {
                String value = WebUtil.display(request.getParameter("border"));

                if ( forceHiddenSet.contains("border")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"border\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border</div>");
            buf.append("<INPUT NAME=\"border\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("background")) {
                String value = WebUtil.display(request.getParameter("background"));

                if ( forceHiddenSet.contains("background")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"background\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Background</div>");
            buf.append("<INPUT NAME=\"background\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("color")) {
                String value = WebUtil.display(request.getParameter("color"));

                if ( forceHiddenSet.contains("color")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"color\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Color</div>");
            buf.append("<INPUT NAME=\"color\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("textDecoration")) {
                String value = WebUtil.display(request.getParameter("textDecoration"));

                if ( forceHiddenSet.contains("textDecoration")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"textDecoration\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Text Decoration</div>");
            buf.append("<INPUT NAME=\"textDecoration\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
                String value = WebUtil.display(request.getParameter("textAlign"));

                if ( forceHiddenSet.contains("textAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"textAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Text Align</div>");
            buf.append("<INPUT NAME=\"textAlign\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("verticalAlign")) {
                String value = WebUtil.display(request.getParameter("verticalAlign"));

                if ( forceHiddenSet.contains("verticalAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"verticalAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Vertical Align</div>");
            buf.append("<INPUT NAME=\"verticalAlign\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("textIndent")) {
                String value = WebUtil.display(request.getParameter("textIndent"));

                if ( forceHiddenSet.contains("textIndent")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"textIndent\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Text Indent</div>");
            buf.append("<INPUT NAME=\"textIndent\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("margin")) {
                String value = WebUtil.display(request.getParameter("margin"));

                if ( forceHiddenSet.contains("margin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"margin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Margin</div>");
            buf.append("<INPUT NAME=\"margin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("padding")) {
                String value = WebUtil.display(request.getParameter("padding"));

                if ( forceHiddenSet.contains("padding")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"padding\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Padding</div>");
            buf.append("<INPUT NAME=\"padding\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("font")) {
                String value = WebUtil.display(request.getParameter("font"));

                if ( forceHiddenSet.contains("font")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"font\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font</div>");
            buf.append("<INPUT NAME=\"font\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("extraStyle")) {
                String value = WebUtil.display(request.getParameter("extraStyle"));

                if ( forceHiddenSet.contains("extraStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"extraStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Extra Style</div>");
            buf.append("<INPUT NAME=\"extraStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovHeight")) {
                String value = WebUtil.display(request.getParameter("hovHeight"));

                if ( forceHiddenSet.contains("hovHeight")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovHeight\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Height</div>");
            buf.append("<INPUT NAME=\"hovHeight\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovWidth")) {
                String value = WebUtil.display(request.getParameter("hovWidth"));

                if ( forceHiddenSet.contains("hovWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Width</div>");
            buf.append("<INPUT NAME=\"hovWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovDisplay")) {
                String value = WebUtil.display(request.getParameter("hovDisplay"));

                if ( forceHiddenSet.contains("hovDisplay")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovDisplay\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Display</div>");
            buf.append("<INPUT NAME=\"hovDisplay\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovBorder")) {
                String value = WebUtil.display(request.getParameter("hovBorder"));

                if ( forceHiddenSet.contains("hovBorder")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovBorder\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Border</div>");
            buf.append("<INPUT NAME=\"hovBorder\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovBackground")) {
                String value = WebUtil.display(request.getParameter("hovBackground"));

                if ( forceHiddenSet.contains("hovBackground")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovBackground\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Background</div>");
            buf.append("<INPUT NAME=\"hovBackground\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovColor")) {
                String value = WebUtil.display(request.getParameter("hovColor"));

                if ( forceHiddenSet.contains("hovColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Color</div>");
            buf.append("<INPUT NAME=\"hovColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovTextDecoration")) {
                String value = WebUtil.display(request.getParameter("hovTextDecoration"));

                if ( forceHiddenSet.contains("hovTextDecoration")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovTextDecoration\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Text Decoration</div>");
            buf.append("<INPUT NAME=\"hovTextDecoration\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovTextAlign")) {
                String value = WebUtil.display(request.getParameter("hovTextAlign"));

                if ( forceHiddenSet.contains("hovTextAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovTextAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Text Align</div>");
            buf.append("<INPUT NAME=\"hovTextAlign\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovVerticalAlign")) {
                String value = WebUtil.display(request.getParameter("hovVerticalAlign"));

                if ( forceHiddenSet.contains("hovVerticalAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovVerticalAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Vertical Align</div>");
            buf.append("<INPUT NAME=\"hovVerticalAlign\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovTextIndent")) {
                String value = WebUtil.display(request.getParameter("hovTextIndent"));

                if ( forceHiddenSet.contains("hovTextIndent")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovTextIndent\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Text Indent</div>");
            buf.append("<INPUT NAME=\"hovTextIndent\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovMargin")) {
                String value = WebUtil.display(request.getParameter("hovMargin"));

                if ( forceHiddenSet.contains("hovMargin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovMargin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Margin</div>");
            buf.append("<INPUT NAME=\"hovMargin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovPadding")) {
                String value = WebUtil.display(request.getParameter("hovPadding"));

                if ( forceHiddenSet.contains("hovPadding")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovPadding\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Padding</div>");
            buf.append("<INPUT NAME=\"hovPadding\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovFont")) {
                String value = WebUtil.display(request.getParameter("hovFont"));

                if ( forceHiddenSet.contains("hovFont")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovFont\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Font</div>");
            buf.append("<INPUT NAME=\"hovFont\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hovExtraStyle")) {
                String value = WebUtil.display(request.getParameter("hovExtraStyle"));

                if ( forceHiddenSet.contains("hovExtraStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hovExtraStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hov Extra Style</div>");
            buf.append("<INPUT NAME=\"hovExtraStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeUse")) {
                String value = WebUtil.display(request.getParameter("activeUse"));

                if ( forceHiddenSet.contains("activeUse")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeUse\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Use</div>");
            buf.append("<select name=\"activeUse\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeBorder")) {
                String value = WebUtil.display(request.getParameter("activeBorder"));

                if ( forceHiddenSet.contains("activeBorder")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeBorder\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Border</div>");
            buf.append("<INPUT NAME=\"activeBorder\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeBackground")) {
                String value = WebUtil.display(request.getParameter("activeBackground"));

                if ( forceHiddenSet.contains("activeBackground")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeBackground\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Background</div>");
            buf.append("<INPUT NAME=\"activeBackground\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeColor")) {
                String value = WebUtil.display(request.getParameter("activeColor"));

                if ( forceHiddenSet.contains("activeColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Color</div>");
            buf.append("<INPUT NAME=\"activeColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeTextDecoration")) {
                String value = WebUtil.display(request.getParameter("activeTextDecoration"));

                if ( forceHiddenSet.contains("activeTextDecoration")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeTextDecoration\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Text Decoration</div>");
            buf.append("<INPUT NAME=\"activeTextDecoration\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeExtraStyle")) {
                String value = WebUtil.display(request.getParameter("activeExtraStyle"));

                if ( forceHiddenSet.contains("activeExtraStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeExtraStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Extra Style</div>");
            buf.append("<INPUT NAME=\"activeExtraStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activeMargin")) {
                String value = WebUtil.display(request.getParameter("activeMargin"));

                if ( forceHiddenSet.contains("activeMargin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activeMargin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Margin</div>");
            buf.append("<INPUT NAME=\"activeMargin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("activePadding")) {
                String value = WebUtil.display(request.getParameter("activePadding"));

                if ( forceHiddenSet.contains("activePadding")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"activePadding\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Active Padding</div>");
            buf.append("<INPUT NAME=\"activePadding\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/linkStyleConfigAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
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

            buf.append("<div id=\"linkStyleConfig-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig) iterator.next();

                buf.append("<div id=\"linkStyleConfig-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-styleKey\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getStyleKey()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-isGlobal\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getIsGlobal()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-idClass\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getIdClass()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isId")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-isId\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getIsId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("height")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-height\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("width")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-width\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("display")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-display\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getDisplay()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("border")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-border\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getBorder()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("background")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-background\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getBackground()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("color")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-color\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("textDecoration")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-textDecoration\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getTextDecoration()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-textAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getTextAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("verticalAlign")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-verticalAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getVerticalAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("textIndent")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-textIndent\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getTextIndent()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("margin")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-margin\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getMargin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("padding")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-padding\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getPadding()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("font")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-font\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getFont()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("extraStyle")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-extraStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getExtraStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovHeight")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovHeight\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovHeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovWidth")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovDisplay")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovDisplay\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovDisplay()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovBorder")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovBorder\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovBorder()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovBackground")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovBackground\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovBackground()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovColor")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovTextDecoration")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovTextDecoration\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovTextDecoration()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovTextAlign")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovTextAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovTextAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovVerticalAlign")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovVerticalAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovVerticalAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovTextIndent")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovTextIndent\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovTextIndent()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovMargin")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovMargin\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovMargin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovPadding")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovPadding\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovPadding()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovFont")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovFont\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovFont()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("hovExtraStyle")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-hovExtraStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getHovExtraStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeUse")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeUse\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveUse()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeBorder")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeBorder\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveBorder()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeBackground")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeBackground\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveBackground()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeColor")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeTextDecoration")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeTextDecoration\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveTextDecoration()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeExtraStyle")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeExtraStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveExtraStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activeMargin")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activeMargin\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActiveMargin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("activePadding")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-activePadding\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getActivePadding()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"linkStyleConfig-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_LinkStyleConfig.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Style Key");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Global");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Id Class");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("height")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Height");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("width")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("display")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Display");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("border")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("background")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Background");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("color")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("textDecoration")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Text Decoration");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Text Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("verticalAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Vertical Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("textIndent")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Text Indent");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("margin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Margin");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("padding")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Padding");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("font")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("extraStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Extra Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovHeight")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Height");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovDisplay")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Display");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovBorder")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Border");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovBackground")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Background");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovTextDecoration")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Text Decoration");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovTextAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Text Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovVerticalAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Vertical Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovTextIndent")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Text Indent");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovMargin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Margin");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovPadding")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Padding");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovFont")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Font");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hovExtraStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hov Extra Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeUse")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Use");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeBorder")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Border");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeBackground")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Background");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeTextDecoration")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Text Decoration");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeExtraStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Extra Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activeMargin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Margin");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("activePadding")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Active Padding");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Updated");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getStyleKey()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_LinkStyleConfig.getIsGlobal()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/linkStyleConfigAction.html?ef=true&id="+ _LinkStyleConfig.getId()+"&isGlobal=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/linkStyleConfigAction.html?ef=true&id="+ _LinkStyleConfig.getId()+"&isGlobal=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getIdClass()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_LinkStyleConfig.getIsId()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/linkStyleConfigAction.html?ef=true&id="+ _LinkStyleConfig.getId()+"&isId=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/linkStyleConfigAction.html?ef=true&id="+ _LinkStyleConfig.getId()+"&isId=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("height")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("width")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("display")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getDisplay()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("border")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getBorder()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("background")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getBackground()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("color")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("textDecoration")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getTextDecoration()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getTextAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("verticalAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getVerticalAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("textIndent")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getTextIndent()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("margin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getMargin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("padding")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getPadding()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("font")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getFont()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("extraStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getExtraStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovHeight")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovHeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovDisplay")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovDisplay()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovBorder")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovBorder()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovBackground")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovBackground()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovTextDecoration")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovTextDecoration()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovTextAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovTextAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovVerticalAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovVerticalAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovTextIndent")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovTextIndent()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovMargin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovMargin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovPadding")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovPadding()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovFont")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovFont()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hovExtraStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getHovExtraStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeUse")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_LinkStyleConfig.getActiveUse()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/linkStyleConfigAction.html?ef=true&id="+ _LinkStyleConfig.getId()+"&activeUse=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/linkStyleConfigAction.html?ef=true&id="+ _LinkStyleConfig.getId()+"&activeUse=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeBorder")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActiveBorder()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeBackground")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActiveBackground()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActiveColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeTextDecoration")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActiveTextDecoration()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeExtraStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActiveExtraStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activeMargin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActiveMargin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("activePadding")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getActivePadding()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_LinkStyleConfig.getTimeUpdated()));

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
            LinkStyleConfig _LinkStyleConfig = null; 
            List list = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _LinkStyleConfig = (LinkStyleConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _LinkStyleConfig = (LinkStyleConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _LinkStyleConfig = LinkStyleConfigDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last LinkStyleConfig=" + _LinkStyleConfig.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _LinkStyleConfig = (LinkStyleConfig) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("styleKey")) 
			            json.put("styleKey", ""+_LinkStyleConfig.getStyleKey());
		            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) 
			            json.put("isGlobal", ""+_LinkStyleConfig.getIsGlobal());
		            if ( ignoreFieldSet || fieldSet.contains("idClass")) 
			            json.put("idClass", ""+_LinkStyleConfig.getIdClass());
		            if ( ignoreFieldSet || fieldSet.contains("isId")) 
			            json.put("isId", ""+_LinkStyleConfig.getIsId());
		            if ( ignoreFieldSet || fieldSet.contains("height")) 
			            json.put("height", ""+_LinkStyleConfig.getHeight());
		            if ( ignoreFieldSet || fieldSet.contains("width")) 
			            json.put("width", ""+_LinkStyleConfig.getWidth());
		            if ( ignoreFieldSet || fieldSet.contains("display")) 
			            json.put("display", ""+_LinkStyleConfig.getDisplay());
		            if ( ignoreFieldSet || fieldSet.contains("border")) 
			            json.put("border", ""+_LinkStyleConfig.getBorder());
		            if ( ignoreFieldSet || fieldSet.contains("background")) 
			            json.put("background", ""+_LinkStyleConfig.getBackground());
		            if ( ignoreFieldSet || fieldSet.contains("color")) 
			            json.put("color", ""+_LinkStyleConfig.getColor());
		            if ( ignoreFieldSet || fieldSet.contains("textDecoration")) 
			            json.put("textDecoration", ""+_LinkStyleConfig.getTextDecoration());
		            if ( ignoreFieldSet || fieldSet.contains("textAlign")) 
			            json.put("textAlign", ""+_LinkStyleConfig.getTextAlign());
		            if ( ignoreFieldSet || fieldSet.contains("verticalAlign")) 
			            json.put("verticalAlign", ""+_LinkStyleConfig.getVerticalAlign());
		            if ( ignoreFieldSet || fieldSet.contains("textIndent")) 
			            json.put("textIndent", ""+_LinkStyleConfig.getTextIndent());
		            if ( ignoreFieldSet || fieldSet.contains("margin")) 
			            json.put("margin", ""+_LinkStyleConfig.getMargin());
		            if ( ignoreFieldSet || fieldSet.contains("padding")) 
			            json.put("padding", ""+_LinkStyleConfig.getPadding());
		            if ( ignoreFieldSet || fieldSet.contains("font")) 
			            json.put("font", ""+_LinkStyleConfig.getFont());
		            if ( ignoreFieldSet || fieldSet.contains("extraStyle")) 
			            json.put("extraStyle", ""+_LinkStyleConfig.getExtraStyle());
		            if ( ignoreFieldSet || fieldSet.contains("hovHeight")) 
			            json.put("hovHeight", ""+_LinkStyleConfig.getHovHeight());
		            if ( ignoreFieldSet || fieldSet.contains("hovWidth")) 
			            json.put("hovWidth", ""+_LinkStyleConfig.getHovWidth());
		            if ( ignoreFieldSet || fieldSet.contains("hovDisplay")) 
			            json.put("hovDisplay", ""+_LinkStyleConfig.getHovDisplay());
		            if ( ignoreFieldSet || fieldSet.contains("hovBorder")) 
			            json.put("hovBorder", ""+_LinkStyleConfig.getHovBorder());
		            if ( ignoreFieldSet || fieldSet.contains("hovBackground")) 
			            json.put("hovBackground", ""+_LinkStyleConfig.getHovBackground());
		            if ( ignoreFieldSet || fieldSet.contains("hovColor")) 
			            json.put("hovColor", ""+_LinkStyleConfig.getHovColor());
		            if ( ignoreFieldSet || fieldSet.contains("hovTextDecoration")) 
			            json.put("hovTextDecoration", ""+_LinkStyleConfig.getHovTextDecoration());
		            if ( ignoreFieldSet || fieldSet.contains("hovTextAlign")) 
			            json.put("hovTextAlign", ""+_LinkStyleConfig.getHovTextAlign());
		            if ( ignoreFieldSet || fieldSet.contains("hovVerticalAlign")) 
			            json.put("hovVerticalAlign", ""+_LinkStyleConfig.getHovVerticalAlign());
		            if ( ignoreFieldSet || fieldSet.contains("hovTextIndent")) 
			            json.put("hovTextIndent", ""+_LinkStyleConfig.getHovTextIndent());
		            if ( ignoreFieldSet || fieldSet.contains("hovMargin")) 
			            json.put("hovMargin", ""+_LinkStyleConfig.getHovMargin());
		            if ( ignoreFieldSet || fieldSet.contains("hovPadding")) 
			            json.put("hovPadding", ""+_LinkStyleConfig.getHovPadding());
		            if ( ignoreFieldSet || fieldSet.contains("hovFont")) 
			            json.put("hovFont", ""+_LinkStyleConfig.getHovFont());
		            if ( ignoreFieldSet || fieldSet.contains("hovExtraStyle")) 
			            json.put("hovExtraStyle", ""+_LinkStyleConfig.getHovExtraStyle());
		            if ( ignoreFieldSet || fieldSet.contains("activeUse")) 
			            json.put("activeUse", ""+_LinkStyleConfig.getActiveUse());
		            if ( ignoreFieldSet || fieldSet.contains("activeBorder")) 
			            json.put("activeBorder", ""+_LinkStyleConfig.getActiveBorder());
		            if ( ignoreFieldSet || fieldSet.contains("activeBackground")) 
			            json.put("activeBackground", ""+_LinkStyleConfig.getActiveBackground());
		            if ( ignoreFieldSet || fieldSet.contains("activeColor")) 
			            json.put("activeColor", ""+_LinkStyleConfig.getActiveColor());
		            if ( ignoreFieldSet || fieldSet.contains("activeTextDecoration")) 
			            json.put("activeTextDecoration", ""+_LinkStyleConfig.getActiveTextDecoration());
		            if ( ignoreFieldSet || fieldSet.contains("activeExtraStyle")) 
			            json.put("activeExtraStyle", ""+_LinkStyleConfig.getActiveExtraStyle());
		            if ( ignoreFieldSet || fieldSet.contains("activeMargin")) 
			            json.put("activeMargin", ""+_LinkStyleConfig.getActiveMargin());
		            if ( ignoreFieldSet || fieldSet.contains("activePadding")) 
			            json.put("activePadding", ""+_LinkStyleConfig.getActivePadding());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_LinkStyleConfig.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonStyleKey = new JSONObject();
	            jsonStyleKey.put("name", "styleKey");
	            jsonStyleKey.put("value", ""+_LinkStyleConfig.getStyleKey());
	            array.put(jsonStyleKey);

	            JSONObject jsonIsGlobal = new JSONObject();
	            jsonIsGlobal.put("name", "isGlobal");
	            jsonIsGlobal.put("value", ""+_LinkStyleConfig.getIsGlobal());
	            array.put(jsonIsGlobal);

	            JSONObject jsonIdClass = new JSONObject();
	            jsonIdClass.put("name", "idClass");
	            jsonIdClass.put("value", ""+_LinkStyleConfig.getIdClass());
	            array.put(jsonIdClass);

	            JSONObject jsonIsId = new JSONObject();
	            jsonIsId.put("name", "isId");
	            jsonIsId.put("value", ""+_LinkStyleConfig.getIsId());
	            array.put(jsonIsId);

	            JSONObject jsonHeight = new JSONObject();
	            jsonHeight.put("name", "height");
	            jsonHeight.put("value", ""+_LinkStyleConfig.getHeight());
	            array.put(jsonHeight);

	            JSONObject jsonWidth = new JSONObject();
	            jsonWidth.put("name", "width");
	            jsonWidth.put("value", ""+_LinkStyleConfig.getWidth());
	            array.put(jsonWidth);

	            JSONObject jsonDisplay = new JSONObject();
	            jsonDisplay.put("name", "display");
	            jsonDisplay.put("value", ""+_LinkStyleConfig.getDisplay());
	            array.put(jsonDisplay);

	            JSONObject jsonBorder = new JSONObject();
	            jsonBorder.put("name", "border");
	            jsonBorder.put("value", ""+_LinkStyleConfig.getBorder());
	            array.put(jsonBorder);

	            JSONObject jsonBackground = new JSONObject();
	            jsonBackground.put("name", "background");
	            jsonBackground.put("value", ""+_LinkStyleConfig.getBackground());
	            array.put(jsonBackground);

	            JSONObject jsonColor = new JSONObject();
	            jsonColor.put("name", "color");
	            jsonColor.put("value", ""+_LinkStyleConfig.getColor());
	            array.put(jsonColor);

	            JSONObject jsonTextDecoration = new JSONObject();
	            jsonTextDecoration.put("name", "textDecoration");
	            jsonTextDecoration.put("value", ""+_LinkStyleConfig.getTextDecoration());
	            array.put(jsonTextDecoration);

	            JSONObject jsonTextAlign = new JSONObject();
	            jsonTextAlign.put("name", "textAlign");
	            jsonTextAlign.put("value", ""+_LinkStyleConfig.getTextAlign());
	            array.put(jsonTextAlign);

	            JSONObject jsonVerticalAlign = new JSONObject();
	            jsonVerticalAlign.put("name", "verticalAlign");
	            jsonVerticalAlign.put("value", ""+_LinkStyleConfig.getVerticalAlign());
	            array.put(jsonVerticalAlign);

	            JSONObject jsonTextIndent = new JSONObject();
	            jsonTextIndent.put("name", "textIndent");
	            jsonTextIndent.put("value", ""+_LinkStyleConfig.getTextIndent());
	            array.put(jsonTextIndent);

	            JSONObject jsonMargin = new JSONObject();
	            jsonMargin.put("name", "margin");
	            jsonMargin.put("value", ""+_LinkStyleConfig.getMargin());
	            array.put(jsonMargin);

	            JSONObject jsonPadding = new JSONObject();
	            jsonPadding.put("name", "padding");
	            jsonPadding.put("value", ""+_LinkStyleConfig.getPadding());
	            array.put(jsonPadding);

	            JSONObject jsonFont = new JSONObject();
	            jsonFont.put("name", "font");
	            jsonFont.put("value", ""+_LinkStyleConfig.getFont());
	            array.put(jsonFont);

	            JSONObject jsonExtraStyle = new JSONObject();
	            jsonExtraStyle.put("name", "extraStyle");
	            jsonExtraStyle.put("value", ""+_LinkStyleConfig.getExtraStyle());
	            array.put(jsonExtraStyle);

	            JSONObject jsonHovHeight = new JSONObject();
	            jsonHovHeight.put("name", "hovHeight");
	            jsonHovHeight.put("value", ""+_LinkStyleConfig.getHovHeight());
	            array.put(jsonHovHeight);

	            JSONObject jsonHovWidth = new JSONObject();
	            jsonHovWidth.put("name", "hovWidth");
	            jsonHovWidth.put("value", ""+_LinkStyleConfig.getHovWidth());
	            array.put(jsonHovWidth);

	            JSONObject jsonHovDisplay = new JSONObject();
	            jsonHovDisplay.put("name", "hovDisplay");
	            jsonHovDisplay.put("value", ""+_LinkStyleConfig.getHovDisplay());
	            array.put(jsonHovDisplay);

	            JSONObject jsonHovBorder = new JSONObject();
	            jsonHovBorder.put("name", "hovBorder");
	            jsonHovBorder.put("value", ""+_LinkStyleConfig.getHovBorder());
	            array.put(jsonHovBorder);

	            JSONObject jsonHovBackground = new JSONObject();
	            jsonHovBackground.put("name", "hovBackground");
	            jsonHovBackground.put("value", ""+_LinkStyleConfig.getHovBackground());
	            array.put(jsonHovBackground);

	            JSONObject jsonHovColor = new JSONObject();
	            jsonHovColor.put("name", "hovColor");
	            jsonHovColor.put("value", ""+_LinkStyleConfig.getHovColor());
	            array.put(jsonHovColor);

	            JSONObject jsonHovTextDecoration = new JSONObject();
	            jsonHovTextDecoration.put("name", "hovTextDecoration");
	            jsonHovTextDecoration.put("value", ""+_LinkStyleConfig.getHovTextDecoration());
	            array.put(jsonHovTextDecoration);

	            JSONObject jsonHovTextAlign = new JSONObject();
	            jsonHovTextAlign.put("name", "hovTextAlign");
	            jsonHovTextAlign.put("value", ""+_LinkStyleConfig.getHovTextAlign());
	            array.put(jsonHovTextAlign);

	            JSONObject jsonHovVerticalAlign = new JSONObject();
	            jsonHovVerticalAlign.put("name", "hovVerticalAlign");
	            jsonHovVerticalAlign.put("value", ""+_LinkStyleConfig.getHovVerticalAlign());
	            array.put(jsonHovVerticalAlign);

	            JSONObject jsonHovTextIndent = new JSONObject();
	            jsonHovTextIndent.put("name", "hovTextIndent");
	            jsonHovTextIndent.put("value", ""+_LinkStyleConfig.getHovTextIndent());
	            array.put(jsonHovTextIndent);

	            JSONObject jsonHovMargin = new JSONObject();
	            jsonHovMargin.put("name", "hovMargin");
	            jsonHovMargin.put("value", ""+_LinkStyleConfig.getHovMargin());
	            array.put(jsonHovMargin);

	            JSONObject jsonHovPadding = new JSONObject();
	            jsonHovPadding.put("name", "hovPadding");
	            jsonHovPadding.put("value", ""+_LinkStyleConfig.getHovPadding());
	            array.put(jsonHovPadding);

	            JSONObject jsonHovFont = new JSONObject();
	            jsonHovFont.put("name", "hovFont");
	            jsonHovFont.put("value", ""+_LinkStyleConfig.getHovFont());
	            array.put(jsonHovFont);

	            JSONObject jsonHovExtraStyle = new JSONObject();
	            jsonHovExtraStyle.put("name", "hovExtraStyle");
	            jsonHovExtraStyle.put("value", ""+_LinkStyleConfig.getHovExtraStyle());
	            array.put(jsonHovExtraStyle);

	            JSONObject jsonActiveUse = new JSONObject();
	            jsonActiveUse.put("name", "activeUse");
	            jsonActiveUse.put("value", ""+_LinkStyleConfig.getActiveUse());
	            array.put(jsonActiveUse);

	            JSONObject jsonActiveBorder = new JSONObject();
	            jsonActiveBorder.put("name", "activeBorder");
	            jsonActiveBorder.put("value", ""+_LinkStyleConfig.getActiveBorder());
	            array.put(jsonActiveBorder);

	            JSONObject jsonActiveBackground = new JSONObject();
	            jsonActiveBackground.put("name", "activeBackground");
	            jsonActiveBackground.put("value", ""+_LinkStyleConfig.getActiveBackground());
	            array.put(jsonActiveBackground);

	            JSONObject jsonActiveColor = new JSONObject();
	            jsonActiveColor.put("name", "activeColor");
	            jsonActiveColor.put("value", ""+_LinkStyleConfig.getActiveColor());
	            array.put(jsonActiveColor);

	            JSONObject jsonActiveTextDecoration = new JSONObject();
	            jsonActiveTextDecoration.put("name", "activeTextDecoration");
	            jsonActiveTextDecoration.put("value", ""+_LinkStyleConfig.getActiveTextDecoration());
	            array.put(jsonActiveTextDecoration);

	            JSONObject jsonActiveExtraStyle = new JSONObject();
	            jsonActiveExtraStyle.put("name", "activeExtraStyle");
	            jsonActiveExtraStyle.put("value", ""+_LinkStyleConfig.getActiveExtraStyle());
	            array.put(jsonActiveExtraStyle);

	            JSONObject jsonActiveMargin = new JSONObject();
	            jsonActiveMargin.put("name", "activeMargin");
	            jsonActiveMargin.put("value", ""+_LinkStyleConfig.getActiveMargin());
	            array.put(jsonActiveMargin);

	            JSONObject jsonActivePadding = new JSONObject();
	            jsonActivePadding.put("name", "activePadding");
	            jsonActivePadding.put("value", ""+_LinkStyleConfig.getActivePadding());
	            array.put(jsonActivePadding);


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
            LinkStyleConfig _LinkStyleConfig = null; 
            List list = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _LinkStyleConfig = (LinkStyleConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _LinkStyleConfig = (LinkStyleConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _LinkStyleConfig = LinkStyleConfigDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_LinkStyleConfig);

        } else {
            
            List list = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected LinkStyleConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( LinkStyleConfigAction.class);
}
