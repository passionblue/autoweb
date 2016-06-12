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

import com.autosite.db.StyleTheme;
import com.autosite.ds.StyleThemeDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.StyleThemeForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check

import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.db.LinkStyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.db.LinkStyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.db.LinkStyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.StyleConfig;



public class StyleThemeAction extends AutositeCoreAction {

    public StyleThemeAction(){
        m_ds = StyleThemeDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        StyleThemeForm _StyleThemeForm = (StyleThemeForm) form;
        HttpSession session = request.getSession();

        setPage(session, "style_theme_home");

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


        StyleTheme _StyleTheme = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _StyleTheme = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleTheme _StyleTheme = m_ds.getById(cid);

            if (_StyleTheme == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleTheme.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleTheme.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_StyleTheme);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _StyleThemeForm, _StyleTheme);
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
            //StyleTheme _StyleTheme = m_ds.getById(cid);

            if (_StyleTheme == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleTheme.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleTheme.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _StyleTheme);
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
            //StyleTheme _StyleTheme = m_ds.getById(cid);

            if (_StyleTheme == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleTheme.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleTheme.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _StyleTheme);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_StyleTheme);
            try { 
                m_actionExtent.afterDelete(request, response, _StyleTheme);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new StyleTheme" );
            StyleTheme _StyleThemeNew = new StyleTheme();   

            // Setting IDs for the object
            _StyleThemeNew.setSiteId(site.getId());

            _StyleThemeNew.setTitle(WebParamUtil.getStringValue(_StyleThemeForm.getTitle()));
            m_logger.debug("setting Title=" +_StyleThemeForm.getTitle());
            _StyleThemeNew.setBodyWidth(WebParamUtil.getIntValue(_StyleThemeForm.getBodyWidth()));
            m_logger.debug("setting BodyWidth=" +_StyleThemeForm.getBodyWidth());
            _StyleThemeNew.setBodyAlign(WebParamUtil.getStringValue(_StyleThemeForm.getBodyAlign()));
            m_logger.debug("setting BodyAlign=" +_StyleThemeForm.getBodyAlign());
            _StyleThemeNew.setBodyBgColor(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgColor()));
            m_logger.debug("setting BodyBgColor=" +_StyleThemeForm.getBodyBgColor());
            _StyleThemeNew.setBodyBgImage(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgImage()));
            m_logger.debug("setting BodyBgImage=" +_StyleThemeForm.getBodyBgImage());
            _StyleThemeNew.setBodyBgAttach(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgAttach()));
            m_logger.debug("setting BodyBgAttach=" +_StyleThemeForm.getBodyBgAttach());
            _StyleThemeNew.setBodyBgRepeat(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgRepeat()));
            m_logger.debug("setting BodyBgRepeat=" +_StyleThemeForm.getBodyBgRepeat());
            _StyleThemeNew.setBodyBgPosition(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgPosition()));
            m_logger.debug("setting BodyBgPosition=" +_StyleThemeForm.getBodyBgPosition());
            _StyleThemeNew.setContentBgColor(WebParamUtil.getStringValue(_StyleThemeForm.getContentBgColor()));
            m_logger.debug("setting ContentBgColor=" +_StyleThemeForm.getContentBgColor());
            _StyleThemeNew.setUseAbsolute(WebParamUtil.getIntValue(_StyleThemeForm.getUseAbsolute()));
            m_logger.debug("setting UseAbsolute=" +_StyleThemeForm.getUseAbsolute());
            _StyleThemeNew.setAbsoluteTop(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteTop()));
            m_logger.debug("setting AbsoluteTop=" +_StyleThemeForm.getAbsoluteTop());
            _StyleThemeNew.setAbsoluteLeft(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteLeft()));
            m_logger.debug("setting AbsoluteLeft=" +_StyleThemeForm.getAbsoluteLeft());
            _StyleThemeNew.setAbsoluteRight(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteRight()));
            m_logger.debug("setting AbsoluteRight=" +_StyleThemeForm.getAbsoluteRight());
            _StyleThemeNew.setAbsoluteBottom(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteBottom()));
            m_logger.debug("setting AbsoluteBottom=" +_StyleThemeForm.getAbsoluteBottom());
            _StyleThemeNew.setPanelStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelStyleId()));
            m_logger.debug("setting PanelStyleId=" +_StyleThemeForm.getPanelStyleId());
            _StyleThemeNew.setPanelDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelDataStyleId()));
            m_logger.debug("setting PanelDataStyleId=" +_StyleThemeForm.getPanelDataStyleId());
            _StyleThemeNew.setPanelLinkStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelLinkStyleId()));
            m_logger.debug("setting PanelLinkStyleId=" +_StyleThemeForm.getPanelLinkStyleId());
            _StyleThemeNew.setPanelTitleStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelTitleStyleId()));
            m_logger.debug("setting PanelTitleStyleId=" +_StyleThemeForm.getPanelTitleStyleId());
            _StyleThemeNew.setMenuStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getMenuStyleId()));
            m_logger.debug("setting MenuStyleId=" +_StyleThemeForm.getMenuStyleId());
            _StyleThemeNew.setMenuLinkStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getMenuLinkStyleId()));
            m_logger.debug("setting MenuLinkStyleId=" +_StyleThemeForm.getMenuLinkStyleId());
            _StyleThemeNew.setHeaderMenuStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getHeaderMenuStyleId()));
            m_logger.debug("setting HeaderMenuStyleId=" +_StyleThemeForm.getHeaderMenuStyleId());
            _StyleThemeNew.setHeaderMenuLinkStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getHeaderMenuLinkStyleId()));
            m_logger.debug("setting HeaderMenuLinkStyleId=" +_StyleThemeForm.getHeaderMenuLinkStyleId());
            _StyleThemeNew.setListFrameStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getListFrameStyleId()));
            m_logger.debug("setting ListFrameStyleId=" +_StyleThemeForm.getListFrameStyleId());
            _StyleThemeNew.setListSubjectStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getListSubjectStyleId()));
            m_logger.debug("setting ListSubjectStyleId=" +_StyleThemeForm.getListSubjectStyleId());
            _StyleThemeNew.setListDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getListDataStyleId()));
            m_logger.debug("setting ListDataStyleId=" +_StyleThemeForm.getListDataStyleId());
            _StyleThemeNew.setSubjectStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSubjectStyleId()));
            m_logger.debug("setting SubjectStyleId=" +_StyleThemeForm.getSubjectStyleId());
            _StyleThemeNew.setDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getDataStyleId()));
            m_logger.debug("setting DataStyleId=" +_StyleThemeForm.getDataStyleId());
            _StyleThemeNew.setSingleFrameStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSingleFrameStyleId()));
            m_logger.debug("setting SingleFrameStyleId=" +_StyleThemeForm.getSingleFrameStyleId());
            _StyleThemeNew.setSingleSubjectStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSingleSubjectStyleId()));
            m_logger.debug("setting SingleSubjectStyleId=" +_StyleThemeForm.getSingleSubjectStyleId());
            _StyleThemeNew.setSingleDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSingleDataStyleId()));
            m_logger.debug("setting SingleDataStyleId=" +_StyleThemeForm.getSingleDataStyleId());
            _StyleThemeNew.setContentPanelStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getContentPanelStyleId()));
            m_logger.debug("setting ContentPanelStyleId=" +_StyleThemeForm.getContentPanelStyleId());
            _StyleThemeNew.setContentPanelTitleStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getContentPanelTitleStyleId()));
            m_logger.debug("setting ContentPanelTitleStyleId=" +_StyleThemeForm.getContentPanelTitleStyleId());
            _StyleThemeNew.setGlobal(WebParamUtil.getIntValue(_StyleThemeForm.getGlobal()));
            m_logger.debug("setting Global=" +_StyleThemeForm.getGlobal());
            _StyleThemeNew.setDisable(WebParamUtil.getIntValue(_StyleThemeForm.getDisable()));
            m_logger.debug("setting Disable=" +_StyleThemeForm.getDisable());
            _StyleThemeNew.setTimeCreated(WebParamUtil.getDateValue(_StyleThemeForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_StyleThemeForm.getTimeCreated());
            _StyleThemeNew.setTimeUpdated(WebParamUtil.getDateValue(_StyleThemeForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_StyleThemeForm.getTimeUpdated());

            try {
                checkDepedenceIntegrity(_StyleThemeNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _StyleThemeNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_StyleThemeNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_StyleThemeNew);
            try{
                m_actionExtent.afterAdd(request, response, _StyleThemeNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "style_theme_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, StyleThemeForm _StyleThemeForm, StyleTheme _StyleTheme) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleTheme _StyleTheme = m_ds.getById(cid);

        m_logger.debug("Before update " + StyleThemeDS.objectToString(_StyleTheme));

        _StyleTheme.setTitle(WebParamUtil.getStringValue(_StyleThemeForm.getTitle()));
        _StyleTheme.setBodyWidth(WebParamUtil.getIntValue(_StyleThemeForm.getBodyWidth()));
        _StyleTheme.setBodyAlign(WebParamUtil.getStringValue(_StyleThemeForm.getBodyAlign()));
        _StyleTheme.setBodyBgColor(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgColor()));
        _StyleTheme.setBodyBgImage(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgImage()));
        _StyleTheme.setBodyBgAttach(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgAttach()));
        _StyleTheme.setBodyBgRepeat(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgRepeat()));
        _StyleTheme.setBodyBgPosition(WebParamUtil.getStringValue(_StyleThemeForm.getBodyBgPosition()));
        _StyleTheme.setContentBgColor(WebParamUtil.getStringValue(_StyleThemeForm.getContentBgColor()));
        _StyleTheme.setUseAbsolute(WebParamUtil.getIntValue(_StyleThemeForm.getUseAbsolute()));
        _StyleTheme.setAbsoluteTop(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteTop()));
        _StyleTheme.setAbsoluteLeft(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteLeft()));
        _StyleTheme.setAbsoluteRight(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteRight()));
        _StyleTheme.setAbsoluteBottom(WebParamUtil.getIntValue(_StyleThemeForm.getAbsoluteBottom()));
        _StyleTheme.setPanelStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelStyleId()));
        _StyleTheme.setPanelDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelDataStyleId()));
        _StyleTheme.setPanelLinkStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelLinkStyleId()));
        _StyleTheme.setPanelTitleStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getPanelTitleStyleId()));
        _StyleTheme.setMenuStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getMenuStyleId()));
        _StyleTheme.setMenuLinkStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getMenuLinkStyleId()));
        _StyleTheme.setHeaderMenuStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getHeaderMenuStyleId()));
        _StyleTheme.setHeaderMenuLinkStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getHeaderMenuLinkStyleId()));
        _StyleTheme.setListFrameStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getListFrameStyleId()));
        _StyleTheme.setListSubjectStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getListSubjectStyleId()));
        _StyleTheme.setListDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getListDataStyleId()));
        _StyleTheme.setSubjectStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSubjectStyleId()));
        _StyleTheme.setDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getDataStyleId()));
        _StyleTheme.setSingleFrameStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSingleFrameStyleId()));
        _StyleTheme.setSingleSubjectStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSingleSubjectStyleId()));
        _StyleTheme.setSingleDataStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getSingleDataStyleId()));
        _StyleTheme.setContentPanelStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getContentPanelStyleId()));
        _StyleTheme.setContentPanelTitleStyleId(WebParamUtil.getLongValue(_StyleThemeForm.getContentPanelTitleStyleId()));
        _StyleTheme.setGlobal(WebParamUtil.getIntValue(_StyleThemeForm.getGlobal()));
        _StyleTheme.setDisable(WebParamUtil.getIntValue(_StyleThemeForm.getDisable()));
        _StyleTheme.setTimeUpdated(WebParamUtil.getDateValue(_StyleThemeForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _StyleTheme);
        m_ds.update(_StyleTheme);
        m_actionExtent.afterUpdate(request, response, _StyleTheme);
        m_logger.debug("After update " + StyleThemeDS.objectToString(_StyleTheme));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, StyleTheme _StyleTheme) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleTheme _StyleTheme = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_StyleTheme.getTitle() + "->" + request.getParameter("title"));
            _StyleTheme.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
        }
        if (!isMissing(request.getParameter("bodyWidth"))) {
            m_logger.debug("updating param bodyWidth from " +_StyleTheme.getBodyWidth() + "->" + request.getParameter("bodyWidth"));
            _StyleTheme.setBodyWidth(WebParamUtil.getIntValue(request.getParameter("bodyWidth")));
        }
        if (!isMissing(request.getParameter("bodyAlign"))) {
            m_logger.debug("updating param bodyAlign from " +_StyleTheme.getBodyAlign() + "->" + request.getParameter("bodyAlign"));
            _StyleTheme.setBodyAlign(WebParamUtil.getStringValue(request.getParameter("bodyAlign")));
        }
        if (!isMissing(request.getParameter("bodyBgColor"))) {
            m_logger.debug("updating param bodyBgColor from " +_StyleTheme.getBodyBgColor() + "->" + request.getParameter("bodyBgColor"));
            _StyleTheme.setBodyBgColor(WebParamUtil.getStringValue(request.getParameter("bodyBgColor")));
        }
        if (!isMissing(request.getParameter("bodyBgImage"))) {
            m_logger.debug("updating param bodyBgImage from " +_StyleTheme.getBodyBgImage() + "->" + request.getParameter("bodyBgImage"));
            _StyleTheme.setBodyBgImage(WebParamUtil.getStringValue(request.getParameter("bodyBgImage")));
        }
        if (!isMissing(request.getParameter("bodyBgAttach"))) {
            m_logger.debug("updating param bodyBgAttach from " +_StyleTheme.getBodyBgAttach() + "->" + request.getParameter("bodyBgAttach"));
            _StyleTheme.setBodyBgAttach(WebParamUtil.getStringValue(request.getParameter("bodyBgAttach")));
        }
        if (!isMissing(request.getParameter("bodyBgRepeat"))) {
            m_logger.debug("updating param bodyBgRepeat from " +_StyleTheme.getBodyBgRepeat() + "->" + request.getParameter("bodyBgRepeat"));
            _StyleTheme.setBodyBgRepeat(WebParamUtil.getStringValue(request.getParameter("bodyBgRepeat")));
        }
        if (!isMissing(request.getParameter("bodyBgPosition"))) {
            m_logger.debug("updating param bodyBgPosition from " +_StyleTheme.getBodyBgPosition() + "->" + request.getParameter("bodyBgPosition"));
            _StyleTheme.setBodyBgPosition(WebParamUtil.getStringValue(request.getParameter("bodyBgPosition")));
        }
        if (!isMissing(request.getParameter("contentBgColor"))) {
            m_logger.debug("updating param contentBgColor from " +_StyleTheme.getContentBgColor() + "->" + request.getParameter("contentBgColor"));
            _StyleTheme.setContentBgColor(WebParamUtil.getStringValue(request.getParameter("contentBgColor")));
        }
        if (!isMissing(request.getParameter("useAbsolute"))) {
            m_logger.debug("updating param useAbsolute from " +_StyleTheme.getUseAbsolute() + "->" + request.getParameter("useAbsolute"));
            _StyleTheme.setUseAbsolute(WebParamUtil.getIntValue(request.getParameter("useAbsolute")));
        }
        if (!isMissing(request.getParameter("absoluteTop"))) {
            m_logger.debug("updating param absoluteTop from " +_StyleTheme.getAbsoluteTop() + "->" + request.getParameter("absoluteTop"));
            _StyleTheme.setAbsoluteTop(WebParamUtil.getIntValue(request.getParameter("absoluteTop")));
        }
        if (!isMissing(request.getParameter("absoluteLeft"))) {
            m_logger.debug("updating param absoluteLeft from " +_StyleTheme.getAbsoluteLeft() + "->" + request.getParameter("absoluteLeft"));
            _StyleTheme.setAbsoluteLeft(WebParamUtil.getIntValue(request.getParameter("absoluteLeft")));
        }
        if (!isMissing(request.getParameter("absoluteRight"))) {
            m_logger.debug("updating param absoluteRight from " +_StyleTheme.getAbsoluteRight() + "->" + request.getParameter("absoluteRight"));
            _StyleTheme.setAbsoluteRight(WebParamUtil.getIntValue(request.getParameter("absoluteRight")));
        }
        if (!isMissing(request.getParameter("absoluteBottom"))) {
            m_logger.debug("updating param absoluteBottom from " +_StyleTheme.getAbsoluteBottom() + "->" + request.getParameter("absoluteBottom"));
            _StyleTheme.setAbsoluteBottom(WebParamUtil.getIntValue(request.getParameter("absoluteBottom")));
        }
        if (!isMissing(request.getParameter("panelStyleId"))) {
            m_logger.debug("updating param panelStyleId from " +_StyleTheme.getPanelStyleId() + "->" + request.getParameter("panelStyleId"));
            _StyleTheme.setPanelStyleId(WebParamUtil.getLongValue(request.getParameter("panelStyleId")));
        }
        if (!isMissing(request.getParameter("panelDataStyleId"))) {
            m_logger.debug("updating param panelDataStyleId from " +_StyleTheme.getPanelDataStyleId() + "->" + request.getParameter("panelDataStyleId"));
            _StyleTheme.setPanelDataStyleId(WebParamUtil.getLongValue(request.getParameter("panelDataStyleId")));
        }
        if (!isMissing(request.getParameter("panelLinkStyleId"))) {
            m_logger.debug("updating param panelLinkStyleId from " +_StyleTheme.getPanelLinkStyleId() + "->" + request.getParameter("panelLinkStyleId"));
            _StyleTheme.setPanelLinkStyleId(WebParamUtil.getLongValue(request.getParameter("panelLinkStyleId")));
        }
        if (!isMissing(request.getParameter("panelTitleStyleId"))) {
            m_logger.debug("updating param panelTitleStyleId from " +_StyleTheme.getPanelTitleStyleId() + "->" + request.getParameter("panelTitleStyleId"));
            _StyleTheme.setPanelTitleStyleId(WebParamUtil.getLongValue(request.getParameter("panelTitleStyleId")));
        }
        if (!isMissing(request.getParameter("menuStyleId"))) {
            m_logger.debug("updating param menuStyleId from " +_StyleTheme.getMenuStyleId() + "->" + request.getParameter("menuStyleId"));
            _StyleTheme.setMenuStyleId(WebParamUtil.getLongValue(request.getParameter("menuStyleId")));
        }
        if (!isMissing(request.getParameter("menuLinkStyleId"))) {
            m_logger.debug("updating param menuLinkStyleId from " +_StyleTheme.getMenuLinkStyleId() + "->" + request.getParameter("menuLinkStyleId"));
            _StyleTheme.setMenuLinkStyleId(WebParamUtil.getLongValue(request.getParameter("menuLinkStyleId")));
        }
        if (!isMissing(request.getParameter("headerMenuStyleId"))) {
            m_logger.debug("updating param headerMenuStyleId from " +_StyleTheme.getHeaderMenuStyleId() + "->" + request.getParameter("headerMenuStyleId"));
            _StyleTheme.setHeaderMenuStyleId(WebParamUtil.getLongValue(request.getParameter("headerMenuStyleId")));
        }
        if (!isMissing(request.getParameter("headerMenuLinkStyleId"))) {
            m_logger.debug("updating param headerMenuLinkStyleId from " +_StyleTheme.getHeaderMenuLinkStyleId() + "->" + request.getParameter("headerMenuLinkStyleId"));
            _StyleTheme.setHeaderMenuLinkStyleId(WebParamUtil.getLongValue(request.getParameter("headerMenuLinkStyleId")));
        }
        if (!isMissing(request.getParameter("listFrameStyleId"))) {
            m_logger.debug("updating param listFrameStyleId from " +_StyleTheme.getListFrameStyleId() + "->" + request.getParameter("listFrameStyleId"));
            _StyleTheme.setListFrameStyleId(WebParamUtil.getLongValue(request.getParameter("listFrameStyleId")));
        }
        if (!isMissing(request.getParameter("listSubjectStyleId"))) {
            m_logger.debug("updating param listSubjectStyleId from " +_StyleTheme.getListSubjectStyleId() + "->" + request.getParameter("listSubjectStyleId"));
            _StyleTheme.setListSubjectStyleId(WebParamUtil.getLongValue(request.getParameter("listSubjectStyleId")));
        }
        if (!isMissing(request.getParameter("listDataStyleId"))) {
            m_logger.debug("updating param listDataStyleId from " +_StyleTheme.getListDataStyleId() + "->" + request.getParameter("listDataStyleId"));
            _StyleTheme.setListDataStyleId(WebParamUtil.getLongValue(request.getParameter("listDataStyleId")));
        }
        if (!isMissing(request.getParameter("subjectStyleId"))) {
            m_logger.debug("updating param subjectStyleId from " +_StyleTheme.getSubjectStyleId() + "->" + request.getParameter("subjectStyleId"));
            _StyleTheme.setSubjectStyleId(WebParamUtil.getLongValue(request.getParameter("subjectStyleId")));
        }
        if (!isMissing(request.getParameter("dataStyleId"))) {
            m_logger.debug("updating param dataStyleId from " +_StyleTheme.getDataStyleId() + "->" + request.getParameter("dataStyleId"));
            _StyleTheme.setDataStyleId(WebParamUtil.getLongValue(request.getParameter("dataStyleId")));
        }
        if (!isMissing(request.getParameter("singleFrameStyleId"))) {
            m_logger.debug("updating param singleFrameStyleId from " +_StyleTheme.getSingleFrameStyleId() + "->" + request.getParameter("singleFrameStyleId"));
            _StyleTheme.setSingleFrameStyleId(WebParamUtil.getLongValue(request.getParameter("singleFrameStyleId")));
        }
        if (!isMissing(request.getParameter("singleSubjectStyleId"))) {
            m_logger.debug("updating param singleSubjectStyleId from " +_StyleTheme.getSingleSubjectStyleId() + "->" + request.getParameter("singleSubjectStyleId"));
            _StyleTheme.setSingleSubjectStyleId(WebParamUtil.getLongValue(request.getParameter("singleSubjectStyleId")));
        }
        if (!isMissing(request.getParameter("singleDataStyleId"))) {
            m_logger.debug("updating param singleDataStyleId from " +_StyleTheme.getSingleDataStyleId() + "->" + request.getParameter("singleDataStyleId"));
            _StyleTheme.setSingleDataStyleId(WebParamUtil.getLongValue(request.getParameter("singleDataStyleId")));
        }
        if (!isMissing(request.getParameter("contentPanelStyleId"))) {
            m_logger.debug("updating param contentPanelStyleId from " +_StyleTheme.getContentPanelStyleId() + "->" + request.getParameter("contentPanelStyleId"));
            _StyleTheme.setContentPanelStyleId(WebParamUtil.getLongValue(request.getParameter("contentPanelStyleId")));
        }
        if (!isMissing(request.getParameter("contentPanelTitleStyleId"))) {
            m_logger.debug("updating param contentPanelTitleStyleId from " +_StyleTheme.getContentPanelTitleStyleId() + "->" + request.getParameter("contentPanelTitleStyleId"));
            _StyleTheme.setContentPanelTitleStyleId(WebParamUtil.getLongValue(request.getParameter("contentPanelTitleStyleId")));
        }
        if (!isMissing(request.getParameter("global"))) {
            m_logger.debug("updating param global from " +_StyleTheme.getGlobal() + "->" + request.getParameter("global"));
            _StyleTheme.setGlobal(WebParamUtil.getIntValue(request.getParameter("global")));
        }
        if (!isMissing(request.getParameter("disable"))) {
            m_logger.debug("updating param disable from " +_StyleTheme.getDisable() + "->" + request.getParameter("disable"));
            _StyleTheme.setDisable(WebParamUtil.getIntValue(request.getParameter("disable")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_StyleTheme.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _StyleTheme.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_StyleTheme.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _StyleTheme.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _StyleTheme.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _StyleTheme);
        m_ds.update(_StyleTheme);
        m_actionExtent.afterUpdate(request, response, _StyleTheme);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, StyleTheme _StyleTheme) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleTheme _StyleTheme = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
			return String.valueOf(_StyleTheme.getTitle());
        }
        if (!isMissing(request.getParameter("bodyWidth"))) {
			return String.valueOf(_StyleTheme.getBodyWidth());
        }
        if (!isMissing(request.getParameter("bodyAlign"))) {
			return String.valueOf(_StyleTheme.getBodyAlign());
        }
        if (!isMissing(request.getParameter("bodyBgColor"))) {
			return String.valueOf(_StyleTheme.getBodyBgColor());
        }
        if (!isMissing(request.getParameter("bodyBgImage"))) {
			return String.valueOf(_StyleTheme.getBodyBgImage());
        }
        if (!isMissing(request.getParameter("bodyBgAttach"))) {
			return String.valueOf(_StyleTheme.getBodyBgAttach());
        }
        if (!isMissing(request.getParameter("bodyBgRepeat"))) {
			return String.valueOf(_StyleTheme.getBodyBgRepeat());
        }
        if (!isMissing(request.getParameter("bodyBgPosition"))) {
			return String.valueOf(_StyleTheme.getBodyBgPosition());
        }
        if (!isMissing(request.getParameter("contentBgColor"))) {
			return String.valueOf(_StyleTheme.getContentBgColor());
        }
        if (!isMissing(request.getParameter("useAbsolute"))) {
			return String.valueOf(_StyleTheme.getUseAbsolute());
        }
        if (!isMissing(request.getParameter("absoluteTop"))) {
			return String.valueOf(_StyleTheme.getAbsoluteTop());
        }
        if (!isMissing(request.getParameter("absoluteLeft"))) {
			return String.valueOf(_StyleTheme.getAbsoluteLeft());
        }
        if (!isMissing(request.getParameter("absoluteRight"))) {
			return String.valueOf(_StyleTheme.getAbsoluteRight());
        }
        if (!isMissing(request.getParameter("absoluteBottom"))) {
			return String.valueOf(_StyleTheme.getAbsoluteBottom());
        }
        if (!isMissing(request.getParameter("panelStyleId"))) {
			return String.valueOf(_StyleTheme.getPanelStyleId());
        }
        if (!isMissing(request.getParameter("panelDataStyleId"))) {
			return String.valueOf(_StyleTheme.getPanelDataStyleId());
        }
        if (!isMissing(request.getParameter("panelLinkStyleId"))) {
			return String.valueOf(_StyleTheme.getPanelLinkStyleId());
        }
        if (!isMissing(request.getParameter("panelTitleStyleId"))) {
			return String.valueOf(_StyleTheme.getPanelTitleStyleId());
        }
        if (!isMissing(request.getParameter("menuStyleId"))) {
			return String.valueOf(_StyleTheme.getMenuStyleId());
        }
        if (!isMissing(request.getParameter("menuLinkStyleId"))) {
			return String.valueOf(_StyleTheme.getMenuLinkStyleId());
        }
        if (!isMissing(request.getParameter("headerMenuStyleId"))) {
			return String.valueOf(_StyleTheme.getHeaderMenuStyleId());
        }
        if (!isMissing(request.getParameter("headerMenuLinkStyleId"))) {
			return String.valueOf(_StyleTheme.getHeaderMenuLinkStyleId());
        }
        if (!isMissing(request.getParameter("listFrameStyleId"))) {
			return String.valueOf(_StyleTheme.getListFrameStyleId());
        }
        if (!isMissing(request.getParameter("listSubjectStyleId"))) {
			return String.valueOf(_StyleTheme.getListSubjectStyleId());
        }
        if (!isMissing(request.getParameter("listDataStyleId"))) {
			return String.valueOf(_StyleTheme.getListDataStyleId());
        }
        if (!isMissing(request.getParameter("subjectStyleId"))) {
			return String.valueOf(_StyleTheme.getSubjectStyleId());
        }
        if (!isMissing(request.getParameter("dataStyleId"))) {
			return String.valueOf(_StyleTheme.getDataStyleId());
        }
        if (!isMissing(request.getParameter("singleFrameStyleId"))) {
			return String.valueOf(_StyleTheme.getSingleFrameStyleId());
        }
        if (!isMissing(request.getParameter("singleSubjectStyleId"))) {
			return String.valueOf(_StyleTheme.getSingleSubjectStyleId());
        }
        if (!isMissing(request.getParameter("singleDataStyleId"))) {
			return String.valueOf(_StyleTheme.getSingleDataStyleId());
        }
        if (!isMissing(request.getParameter("contentPanelStyleId"))) {
			return String.valueOf(_StyleTheme.getContentPanelStyleId());
        }
        if (!isMissing(request.getParameter("contentPanelTitleStyleId"))) {
			return String.valueOf(_StyleTheme.getContentPanelTitleStyleId());
        }
        if (!isMissing(request.getParameter("global"))) {
			return String.valueOf(_StyleTheme.getGlobal());
        }
        if (!isMissing(request.getParameter("disable"))) {
			return String.valueOf(_StyleTheme.getDisable());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_StyleTheme.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_StyleTheme.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(StyleTheme _StyleTheme) throws Exception {
    }

    protected String getFieldByName(String fieldName, StyleTheme _StyleTheme) {
        if (fieldName == null || fieldName.equals("")|| _StyleTheme == null) return null;
        
        if (fieldName.equals("title")) {
            return WebUtil.display(_StyleTheme.getTitle());
        }
        if (fieldName.equals("bodyWidth")) {
            return WebUtil.display(_StyleTheme.getBodyWidth());
        }
        if (fieldName.equals("bodyAlign")) {
            return WebUtil.display(_StyleTheme.getBodyAlign());
        }
        if (fieldName.equals("bodyBgColor")) {
            return WebUtil.display(_StyleTheme.getBodyBgColor());
        }
        if (fieldName.equals("bodyBgImage")) {
            return WebUtil.display(_StyleTheme.getBodyBgImage());
        }
        if (fieldName.equals("bodyBgAttach")) {
            return WebUtil.display(_StyleTheme.getBodyBgAttach());
        }
        if (fieldName.equals("bodyBgRepeat")) {
            return WebUtil.display(_StyleTheme.getBodyBgRepeat());
        }
        if (fieldName.equals("bodyBgPosition")) {
            return WebUtil.display(_StyleTheme.getBodyBgPosition());
        }
        if (fieldName.equals("contentBgColor")) {
            return WebUtil.display(_StyleTheme.getContentBgColor());
        }
        if (fieldName.equals("useAbsolute")) {
            return WebUtil.display(_StyleTheme.getUseAbsolute());
        }
        if (fieldName.equals("absoluteTop")) {
            return WebUtil.display(_StyleTheme.getAbsoluteTop());
        }
        if (fieldName.equals("absoluteLeft")) {
            return WebUtil.display(_StyleTheme.getAbsoluteLeft());
        }
        if (fieldName.equals("absoluteRight")) {
            return WebUtil.display(_StyleTheme.getAbsoluteRight());
        }
        if (fieldName.equals("absoluteBottom")) {
            return WebUtil.display(_StyleTheme.getAbsoluteBottom());
        }
        if (fieldName.equals("panelStyleId")) {
            return WebUtil.display(_StyleTheme.getPanelStyleId());
        }
        if (fieldName.equals("panelDataStyleId")) {
            return WebUtil.display(_StyleTheme.getPanelDataStyleId());
        }
        if (fieldName.equals("panelLinkStyleId")) {
            return WebUtil.display(_StyleTheme.getPanelLinkStyleId());
        }
        if (fieldName.equals("panelTitleStyleId")) {
            return WebUtil.display(_StyleTheme.getPanelTitleStyleId());
        }
        if (fieldName.equals("menuStyleId")) {
            return WebUtil.display(_StyleTheme.getMenuStyleId());
        }
        if (fieldName.equals("menuLinkStyleId")) {
            return WebUtil.display(_StyleTheme.getMenuLinkStyleId());
        }
        if (fieldName.equals("headerMenuStyleId")) {
            return WebUtil.display(_StyleTheme.getHeaderMenuStyleId());
        }
        if (fieldName.equals("headerMenuLinkStyleId")) {
            return WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId());
        }
        if (fieldName.equals("listFrameStyleId")) {
            return WebUtil.display(_StyleTheme.getListFrameStyleId());
        }
        if (fieldName.equals("listSubjectStyleId")) {
            return WebUtil.display(_StyleTheme.getListSubjectStyleId());
        }
        if (fieldName.equals("listDataStyleId")) {
            return WebUtil.display(_StyleTheme.getListDataStyleId());
        }
        if (fieldName.equals("subjectStyleId")) {
            return WebUtil.display(_StyleTheme.getSubjectStyleId());
        }
        if (fieldName.equals("dataStyleId")) {
            return WebUtil.display(_StyleTheme.getDataStyleId());
        }
        if (fieldName.equals("singleFrameStyleId")) {
            return WebUtil.display(_StyleTheme.getSingleFrameStyleId());
        }
        if (fieldName.equals("singleSubjectStyleId")) {
            return WebUtil.display(_StyleTheme.getSingleSubjectStyleId());
        }
        if (fieldName.equals("singleDataStyleId")) {
            return WebUtil.display(_StyleTheme.getSingleDataStyleId());
        }
        if (fieldName.equals("contentPanelStyleId")) {
            return WebUtil.display(_StyleTheme.getContentPanelStyleId());
        }
        if (fieldName.equals("contentPanelTitleStyleId")) {
            return WebUtil.display(_StyleTheme.getContentPanelTitleStyleId());
        }
        if (fieldName.equals("global")) {
            return WebUtil.display(_StyleTheme.getGlobal());
        }
        if (fieldName.equals("disable")) {
            return WebUtil.display(_StyleTheme.getDisable());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_StyleTheme.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_StyleTheme.getTimeUpdated());
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
            StyleTheme _StyleTheme = StyleThemeDS.getInstance().getById(id);
            if (_StyleTheme == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _StyleTheme);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            StyleTheme _StyleTheme = StyleThemeDS.getInstance().getById(id);
            if ( _StyleTheme == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _StyleTheme);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/styleThemeAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("title")) {
                String value = WebUtil.display(request.getParameter("title"));

                if ( forceHiddenSet.contains("title")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"title\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Title</div>");
            buf.append("<INPUT NAME=\"title\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
                String value = WebUtil.display(request.getParameter("bodyWidth"));

                if ( forceHiddenSet.contains("bodyWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Width</div>");
            buf.append("<INPUT NAME=\"bodyWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
                String value = WebUtil.display(request.getParameter("bodyAlign"));

                if ( forceHiddenSet.contains("bodyAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Align</div>");
            buf.append("<select id=\"requiredField\" name=\"bodyAlign\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

        	buf.append("<option value=\"top center\" >top center</option>");
        	buf.append("<option value=\"top left\" >top left</option>");
        	buf.append("<option value=\"top right\" >top right</option>");
        	buf.append("<option value=\"center center\" >center center</option>");
        	buf.append("<option value=\"center left\" >center left</option>");
        	buf.append("<option value=\"center right\" >center right</option>");
        	buf.append("<option value=\"bottom center\" >bottom center</option>");
        	buf.append("<option value=\"bottom left\" >bottom left</option>");
        	buf.append("<option value=\"bottom right\" >bottom right</option>");

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyBgColor")) {
                String value = WebUtil.display(request.getParameter("bodyBgColor"));

                if ( forceHiddenSet.contains("bodyBgColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyBgColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Bg Color</div>");
            buf.append("<INPUT NAME=\"bodyBgColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyBgImage")) {
                String value = WebUtil.display(request.getParameter("bodyBgImage"));

                if ( forceHiddenSet.contains("bodyBgImage")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyBgImage\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Bg Image</div>");
            buf.append("<INPUT NAME=\"bodyBgImage\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyBgAttach")) {
                String value = WebUtil.display(request.getParameter("bodyBgAttach"));

                if ( forceHiddenSet.contains("bodyBgAttach")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyBgAttach\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Bg Attach</div>");
            buf.append("<INPUT NAME=\"bodyBgAttach\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyBgRepeat")) {
                String value = WebUtil.display(request.getParameter("bodyBgRepeat"));

                if ( forceHiddenSet.contains("bodyBgRepeat")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyBgRepeat\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Bg Repeat</div>");
            buf.append("<INPUT NAME=\"bodyBgRepeat\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bodyBgPosition")) {
                String value = WebUtil.display(request.getParameter("bodyBgPosition"));

                if ( forceHiddenSet.contains("bodyBgPosition")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bodyBgPosition\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Body Bg Position</div>");
            buf.append("<INPUT NAME=\"bodyBgPosition\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentBgColor")) {
                String value = WebUtil.display(request.getParameter("contentBgColor"));

                if ( forceHiddenSet.contains("contentBgColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentBgColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Bg Color</div>");
            buf.append("<INPUT NAME=\"contentBgColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("useAbsolute")) {
                String value = WebUtil.display(request.getParameter("useAbsolute"));

                if ( forceHiddenSet.contains("useAbsolute")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"useAbsolute\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Use Absolute</div>");
            buf.append("<select name=\"useAbsolute\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("absoluteTop")) {
                String value = WebUtil.display(request.getParameter("absoluteTop"));

                if ( forceHiddenSet.contains("absoluteTop")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"absoluteTop\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Absolute Top</div>");
            buf.append("<INPUT NAME=\"absoluteTop\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("absoluteLeft")) {
                String value = WebUtil.display(request.getParameter("absoluteLeft"));

                if ( forceHiddenSet.contains("absoluteLeft")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"absoluteLeft\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Absolute Left</div>");
            buf.append("<INPUT NAME=\"absoluteLeft\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("absoluteRight")) {
                String value = WebUtil.display(request.getParameter("absoluteRight"));

                if ( forceHiddenSet.contains("absoluteRight")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"absoluteRight\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Absolute Right</div>");
            buf.append("<INPUT NAME=\"absoluteRight\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("absoluteBottom")) {
                String value = WebUtil.display(request.getParameter("absoluteBottom"));

                if ( forceHiddenSet.contains("absoluteBottom")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"absoluteBottom\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Absolute Bottom</div>");
            buf.append("<INPUT NAME=\"absoluteBottom\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("panelStyleId")) {
                String value = WebUtil.display(request.getParameter("panelStyleId"));

                if ( forceHiddenSet.contains("panelStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"panelStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Panel Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"panelStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemePanel Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("panelDataStyleId")) {
                String value = WebUtil.display(request.getParameter("panelDataStyleId"));

                if ( forceHiddenSet.contains("panelDataStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"panelDataStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Panel Data Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"panelDataStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemePanel Data Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("panelLinkStyleId")) {
                String value = WebUtil.display(request.getParameter("panelLinkStyleId"));

                if ( forceHiddenSet.contains("panelLinkStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"panelLinkStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Panel Link Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"panelLinkStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemePanel Link Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("panelTitleStyleId")) {
                String value = WebUtil.display(request.getParameter("panelTitleStyleId"));

                if ( forceHiddenSet.contains("panelTitleStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"panelTitleStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Panel Title Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"panelTitleStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemePanel Title Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("menuStyleId")) {
                String value = WebUtil.display(request.getParameter("menuStyleId"));

                if ( forceHiddenSet.contains("menuStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"menuStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Menu Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"menuStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeMenu Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("menuLinkStyleId")) {
                String value = WebUtil.display(request.getParameter("menuLinkStyleId"));

                if ( forceHiddenSet.contains("menuLinkStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"menuLinkStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Menu Link Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"menuLinkStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeMenu Link Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("headerMenuStyleId")) {
                String value = WebUtil.display(request.getParameter("headerMenuStyleId"));

                if ( forceHiddenSet.contains("headerMenuStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"headerMenuStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Header Menu Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"headerMenuStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeHeader Menu Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("headerMenuLinkStyleId")) {
                String value = WebUtil.display(request.getParameter("headerMenuLinkStyleId"));

                if ( forceHiddenSet.contains("headerMenuLinkStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"headerMenuLinkStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Header Menu Link Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"headerMenuLinkStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeHeader Menu Link Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
                String value = WebUtil.display(request.getParameter("listFrameStyleId"));

                if ( forceHiddenSet.contains("listFrameStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listFrameStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Frame Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"listFrameStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeList Frame Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
                String value = WebUtil.display(request.getParameter("listSubjectStyleId"));

                if ( forceHiddenSet.contains("listSubjectStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listSubjectStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Subject Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"listSubjectStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeList Subject Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
                String value = WebUtil.display(request.getParameter("listDataStyleId"));

                if ( forceHiddenSet.contains("listDataStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listDataStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Data Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"listDataStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeList Data Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
                String value = WebUtil.display(request.getParameter("subjectStyleId"));

                if ( forceHiddenSet.contains("subjectStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subjectStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subject Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"subjectStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeSubject Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
                String value = WebUtil.display(request.getParameter("dataStyleId"));

                if ( forceHiddenSet.contains("dataStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"dataStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Data Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"dataStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeData Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("singleFrameStyleId")) {
                String value = WebUtil.display(request.getParameter("singleFrameStyleId"));

                if ( forceHiddenSet.contains("singleFrameStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"singleFrameStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Single Frame Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"singleFrameStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeSingle Frame Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("singleSubjectStyleId")) {
                String value = WebUtil.display(request.getParameter("singleSubjectStyleId"));

                if ( forceHiddenSet.contains("singleSubjectStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"singleSubjectStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Single Subject Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"singleSubjectStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeSingle Subject Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("singleDataStyleId")) {
                String value = WebUtil.display(request.getParameter("singleDataStyleId"));

                if ( forceHiddenSet.contains("singleDataStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"singleDataStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Single Data Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"singleDataStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeSingle Data Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentPanelStyleId")) {
                String value = WebUtil.display(request.getParameter("contentPanelStyleId"));

                if ( forceHiddenSet.contains("contentPanelStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentPanelStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Panel Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"contentPanelStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeContent Panel Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentPanelTitleStyleId")) {
                String value = WebUtil.display(request.getParameter("contentPanelTitleStyleId"));

                if ( forceHiddenSet.contains("contentPanelTitleStyleId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentPanelTitleStyleId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Panel Title Style Id</div>");
            buf.append("<select id=\"requiredField\" name=\"contentPanelTitleStyleId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("StyleThemeContent Panel Title Style IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("global")) {
                String value = WebUtil.display(request.getParameter("global"));

                if ( forceHiddenSet.contains("global")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"global\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Global</div>");
            buf.append("<select name=\"global\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("disable")) {
                String value = WebUtil.display(request.getParameter("disable"));

                if ( forceHiddenSet.contains("disable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"disable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Disable</div>");
            buf.append("<select name=\"disable\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/styleThemeAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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
            importedScripts += "    document.getElementById(\"resultDisplayStyleTheme\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest(){\n";
            importedScripts +=     "xmlhttpPostXX('styleThemeFormAddDis','/styleThemeAction.html', 'resultDisplayStyleTheme', '${ajax_response_fields}', responseCallback);\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"styleThemeFormAddDis\" method=\"POST\" action=\"/styleThemeAction.html\" id=\"styleThemeFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Title</div>");
        buf.append("<input class=\"requiredField\" id=\"title\" type=\"text\" size=\"70\" name=\"title\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Width</div>");
        buf.append("<input class=\"field\" id=\"bodyWidth\" type=\"text\" size=\"70\" name=\"bodyWidth\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Align</div>");
        buf.append("<select class=\"field\" name=\"bodyAlign\" id=\"bodyAlign\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("<option value=\"top center\" >top center</option>");
        buf.append("<option value=\"top left\" >top left</option>");
        buf.append("<option value=\"top right\" >top right</option>");
        buf.append("<option value=\"center center\" >center center</option>");
        buf.append("<option value=\"center left\" >center left</option>");
        buf.append("<option value=\"center right\" >center right</option>");
        buf.append("<option value=\"bottom center\" >bottom center</option>");
        buf.append("<option value=\"bottom left\" >bottom left</option>");
        buf.append("<option value=\"bottom right\" >bottom right</option>");
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Bg Color</div>");
        buf.append("<input class=\"field\" id=\"bodyBgColor\" type=\"text\" size=\"70\" name=\"bodyBgColor\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Bg Image</div>");
        buf.append("<input class=\"field\" id=\"bodyBgImage\" type=\"text\" size=\"70\" name=\"bodyBgImage\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Bg Attach</div>");
        buf.append("<input class=\"field\" id=\"bodyBgAttach\" type=\"text\" size=\"70\" name=\"bodyBgAttach\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Bg Repeat</div>");
        buf.append("<input class=\"field\" id=\"bodyBgRepeat\" type=\"text\" size=\"70\" name=\"bodyBgRepeat\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Body Bg Position</div>");
        buf.append("<input class=\"field\" id=\"bodyBgPosition\" type=\"text\" size=\"70\" name=\"bodyBgPosition\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Bg Color</div>");
        buf.append("<input class=\"field\" id=\"contentBgColor\" type=\"text\" size=\"70\" name=\"contentBgColor\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Use Absolute</div>");
        buf.append("<select name=\"useAbsolute\" id=\"useAbsolute\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Absolute Top</div>");
        buf.append("<input class=\"field\" id=\"absoluteTop\" type=\"text\" size=\"70\" name=\"absoluteTop\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Absolute Left</div>");
        buf.append("<input class=\"field\" id=\"absoluteLeft\" type=\"text\" size=\"70\" name=\"absoluteLeft\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Absolute Right</div>");
        buf.append("<input class=\"field\" id=\"absoluteRight\" type=\"text\" size=\"70\" name=\"absoluteRight\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Absolute Bottom</div>");
        buf.append("<input class=\"field\" id=\"absoluteBottom\" type=\"text\" size=\"70\" name=\"absoluteBottom\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Panel Style Id</div>");
        buf.append("<select class=\"requiredField\" name=\"panelStyleId\" id=\"panelStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_panelStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\"  >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	}
        buf.append("</select> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Panel Data Style Id</div>");
        buf.append("<select class=\"requiredField\" name=\"panelDataStyleId\" id=\"panelDataStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_panelDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\"  >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	}
        buf.append("</select> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Panel Link Style Id</div>");
        buf.append("<select class=\"requiredField\" name=\"panelLinkStyleId\" id=\"panelLinkStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listLinkStyleConfig_panelLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_panelLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\"  >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	}
        buf.append("</select> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Panel Title Style Id</div>");
        buf.append("<select class=\"field\" name=\"panelTitleStyleId\" id=\"panelTitleStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_panelTitleStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelTitleStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Menu Style Id</div>");
        buf.append("<select class=\"field\" name=\"menuStyleId\" id=\"menuStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_menuStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_menuStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Menu Link Style Id</div>");
        buf.append("<select class=\"field\" name=\"menuLinkStyleId\" id=\"menuLinkStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listLinkStyleConfig_menuLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_menuLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Header Menu Style Id</div>");
        buf.append("<select class=\"field\" name=\"headerMenuStyleId\" id=\"headerMenuStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_headerMenuStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_headerMenuStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Header Menu Link Style Id</div>");
        buf.append("<select class=\"field\" name=\"headerMenuLinkStyleId\" id=\"headerMenuLinkStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listLinkStyleConfig_headerMenuLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_headerMenuLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Frame Style Id</div>");
        buf.append("<select class=\"field\" name=\"listFrameStyleId\" id=\"listFrameStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_listFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Subject Style Id</div>");
        buf.append("<select class=\"field\" name=\"listSubjectStyleId\" id=\"listSubjectStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_listSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Data Style Id</div>");
        buf.append("<select class=\"field\" name=\"listDataStyleId\" id=\"listDataStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_listDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Subject Style Id</div>");
        buf.append("<select class=\"field\" name=\"subjectStyleId\" id=\"subjectStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_subjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_subjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Data Style Id</div>");
        buf.append("<select class=\"field\" name=\"dataStyleId\" id=\"dataStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_dataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_dataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Single Frame Style Id</div>");
        buf.append("<select class=\"field\" name=\"singleFrameStyleId\" id=\"singleFrameStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_singleFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Single Subject Style Id</div>");
        buf.append("<select class=\"field\" name=\"singleSubjectStyleId\" id=\"singleSubjectStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_singleSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Single Data Style Id</div>");
        buf.append("<select class=\"field\" name=\"singleDataStyleId\" id=\"singleDataStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_singleDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Panel Style Id</div>");
        buf.append("<select class=\"field\" name=\"contentPanelStyleId\" id=\"contentPanelStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_contentPanelStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_contentPanelStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Content Panel Title Style Id</div>");
        buf.append("<select class=\"field\" name=\"contentPanelTitleStyleId\" id=\"contentPanelTitleStyleId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listStyleConfig_contentPanelTitleStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_contentPanelTitleStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getStyleKey() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Global</div>");
        buf.append("<select name=\"global\" id=\"global\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Disable</div>");
        buf.append("<select name=\"disable\" id=\"disable\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest()\">Submit</a><br>");
			buf.append("<a href=\"javascript:clearFormXX(\\'styleThemeFormAddDis\\')\">Clear Form</a><br>");
		    buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayStyleTheme\"></span>");
			buf.append("<a href=\"javascript:backToXX(\\'styleThemeFormAddDis\\',\\'resultDisplayStyleTheme\\')\">show back</a><br>");
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

            buf.append("<div id=\"styleTheme-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                StyleTheme _StyleTheme = (StyleTheme) iterator.next();

                buf.append("<div id=\"styleTheme-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("title")) {
                    buf.append("<div id=\"styleTheme-ajax-title\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getTitle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyBgColor")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyBgColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyBgColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyBgImage")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyBgImage\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyBgImage()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyBgAttach")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyBgAttach\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyBgAttach()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyBgRepeat")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyBgRepeat\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyBgRepeat()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bodyBgPosition")) {
                    buf.append("<div id=\"styleTheme-ajax-bodyBgPosition\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getBodyBgPosition()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentBgColor")) {
                    buf.append("<div id=\"styleTheme-ajax-contentBgColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getContentBgColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("useAbsolute")) {
                    buf.append("<div id=\"styleTheme-ajax-useAbsolute\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getUseAbsolute()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("absoluteTop")) {
                    buf.append("<div id=\"styleTheme-ajax-absoluteTop\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getAbsoluteTop()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("absoluteLeft")) {
                    buf.append("<div id=\"styleTheme-ajax-absoluteLeft\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getAbsoluteLeft()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("absoluteRight")) {
                    buf.append("<div id=\"styleTheme-ajax-absoluteRight\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getAbsoluteRight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("absoluteBottom")) {
                    buf.append("<div id=\"styleTheme-ajax-absoluteBottom\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getAbsoluteBottom()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("panelStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-panelStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getPanelStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("panelDataStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-panelDataStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getPanelDataStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("panelLinkStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-panelLinkStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getPanelLinkStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("panelTitleStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-panelTitleStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getPanelTitleStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("menuStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-menuStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getMenuStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("menuLinkStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-menuLinkStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getMenuLinkStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("headerMenuStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-headerMenuStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getHeaderMenuStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("headerMenuLinkStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-headerMenuLinkStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-listFrameStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getListFrameStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-listSubjectStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getListSubjectStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-listDataStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getListDataStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-subjectStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getSubjectStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-dataStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getDataStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("singleFrameStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-singleFrameStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getSingleFrameStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("singleSubjectStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-singleSubjectStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getSingleSubjectStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("singleDataStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-singleDataStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getSingleDataStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentPanelStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-contentPanelStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getContentPanelStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("contentPanelTitleStyleId")) {
                    buf.append("<div id=\"styleTheme-ajax-contentPanelTitleStyleId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getContentPanelTitleStyleId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("global")) {
                    buf.append("<div id=\"styleTheme-ajax-global\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getGlobal()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("disable")) {
                    buf.append("<div id=\"styleTheme-ajax-disable\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getDisable()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"styleTheme-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"styleTheme-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleTheme.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("title")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Title");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyBgColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Bg Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyBgImage")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Bg Image");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyBgAttach")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Bg Attach");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyBgRepeat")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Bg Repeat");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bodyBgPosition")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Body Bg Position");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentBgColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Bg Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("useAbsolute")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Use Absolute");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("absoluteTop")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Absolute Top");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("absoluteLeft")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Absolute Left");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("absoluteRight")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Absolute Right");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("absoluteBottom")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Absolute Bottom");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("panelStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Panel Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("panelDataStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Panel Data Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("panelLinkStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Panel Link Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("panelTitleStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Panel Title Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("menuStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Menu Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("menuLinkStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Menu Link Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("headerMenuStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Header Menu Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("headerMenuLinkStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Header Menu Link Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Frame Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Subject Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Data Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Subject Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Data Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("singleFrameStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Single Frame Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("singleSubjectStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Single Subject Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("singleDataStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Single Data Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentPanelStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Panel Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentPanelTitleStyleId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Panel Title Style Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("global")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Global");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("disable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Disable");
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
                StyleTheme _StyleTheme = (StyleTheme) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("title")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getTitle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyBgColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyBgColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyBgImage")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyBgImage()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyBgAttach")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyBgAttach()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyBgRepeat")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyBgRepeat()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bodyBgPosition")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getBodyBgPosition()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentBgColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getContentBgColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("useAbsolute")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_StyleTheme.getUseAbsolute()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/styleThemeAction.html?ef=true&id="+ _StyleTheme.getId()+"&useAbsolute=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/styleThemeAction.html?ef=true&id="+ _StyleTheme.getId()+"&useAbsolute=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("absoluteTop")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getAbsoluteTop()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("absoluteLeft")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getAbsoluteLeft()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("absoluteRight")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getAbsoluteRight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("absoluteBottom")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getAbsoluteBottom()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("panelStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getPanelStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("panelDataStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getPanelDataStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("panelLinkStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getPanelLinkStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("panelTitleStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getPanelTitleStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("menuStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getMenuStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("menuLinkStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getMenuLinkStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("headerMenuStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getHeaderMenuStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("headerMenuLinkStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getListFrameStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getListSubjectStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getListDataStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getSubjectStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getDataStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("singleFrameStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getSingleFrameStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("singleSubjectStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getSingleSubjectStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("singleDataStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getSingleDataStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentPanelStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getContentPanelStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentPanelTitleStyleId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getContentPanelTitleStyleId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("global")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_StyleTheme.getGlobal()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/styleThemeAction.html?ef=true&id="+ _StyleTheme.getId()+"&global=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/styleThemeAction.html?ef=true&id="+ _StyleTheme.getId()+"&global=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("disable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_StyleTheme.getDisable()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/styleThemeAction.html?ef=true&id="+ _StyleTheme.getId()+"&disable=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/styleThemeAction.html?ef=true&id="+ _StyleTheme.getId()+"&disable=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleTheme.getTimeUpdated()));

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
            StyleTheme _StyleTheme = null; 
            List list = StyleThemeDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _StyleTheme = (StyleTheme) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _StyleTheme = (StyleTheme) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _StyleTheme = StyleThemeDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last StyleTheme=" + _StyleTheme.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _StyleTheme = (StyleTheme) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("title")) 
			            json.put("title", ""+_StyleTheme.getTitle());
		            if ( ignoreFieldSet || fieldSet.contains("bodyWidth")) 
			            json.put("bodyWidth", ""+_StyleTheme.getBodyWidth());
		            if ( ignoreFieldSet || fieldSet.contains("bodyAlign")) 
			            json.put("bodyAlign", ""+_StyleTheme.getBodyAlign());
		            if ( ignoreFieldSet || fieldSet.contains("bodyBgColor")) 
			            json.put("bodyBgColor", ""+_StyleTheme.getBodyBgColor());
		            if ( ignoreFieldSet || fieldSet.contains("bodyBgImage")) 
			            json.put("bodyBgImage", ""+_StyleTheme.getBodyBgImage());
		            if ( ignoreFieldSet || fieldSet.contains("bodyBgAttach")) 
			            json.put("bodyBgAttach", ""+_StyleTheme.getBodyBgAttach());
		            if ( ignoreFieldSet || fieldSet.contains("bodyBgRepeat")) 
			            json.put("bodyBgRepeat", ""+_StyleTheme.getBodyBgRepeat());
		            if ( ignoreFieldSet || fieldSet.contains("bodyBgPosition")) 
			            json.put("bodyBgPosition", ""+_StyleTheme.getBodyBgPosition());
		            if ( ignoreFieldSet || fieldSet.contains("contentBgColor")) 
			            json.put("contentBgColor", ""+_StyleTheme.getContentBgColor());
		            if ( ignoreFieldSet || fieldSet.contains("useAbsolute")) 
			            json.put("useAbsolute", ""+_StyleTheme.getUseAbsolute());
		            if ( ignoreFieldSet || fieldSet.contains("absoluteTop")) 
			            json.put("absoluteTop", ""+_StyleTheme.getAbsoluteTop());
		            if ( ignoreFieldSet || fieldSet.contains("absoluteLeft")) 
			            json.put("absoluteLeft", ""+_StyleTheme.getAbsoluteLeft());
		            if ( ignoreFieldSet || fieldSet.contains("absoluteRight")) 
			            json.put("absoluteRight", ""+_StyleTheme.getAbsoluteRight());
		            if ( ignoreFieldSet || fieldSet.contains("absoluteBottom")) 
			            json.put("absoluteBottom", ""+_StyleTheme.getAbsoluteBottom());
		            if ( ignoreFieldSet || fieldSet.contains("panelStyleId")) 
			            json.put("panelStyleId", ""+_StyleTheme.getPanelStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("panelDataStyleId")) 
			            json.put("panelDataStyleId", ""+_StyleTheme.getPanelDataStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("panelLinkStyleId")) 
			            json.put("panelLinkStyleId", ""+_StyleTheme.getPanelLinkStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("panelTitleStyleId")) 
			            json.put("panelTitleStyleId", ""+_StyleTheme.getPanelTitleStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("menuStyleId")) 
			            json.put("menuStyleId", ""+_StyleTheme.getMenuStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("menuLinkStyleId")) 
			            json.put("menuLinkStyleId", ""+_StyleTheme.getMenuLinkStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("headerMenuStyleId")) 
			            json.put("headerMenuStyleId", ""+_StyleTheme.getHeaderMenuStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("headerMenuLinkStyleId")) 
			            json.put("headerMenuLinkStyleId", ""+_StyleTheme.getHeaderMenuLinkStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("listFrameStyleId")) 
			            json.put("listFrameStyleId", ""+_StyleTheme.getListFrameStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("listSubjectStyleId")) 
			            json.put("listSubjectStyleId", ""+_StyleTheme.getListSubjectStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("listDataStyleId")) 
			            json.put("listDataStyleId", ""+_StyleTheme.getListDataStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("subjectStyleId")) 
			            json.put("subjectStyleId", ""+_StyleTheme.getSubjectStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("dataStyleId")) 
			            json.put("dataStyleId", ""+_StyleTheme.getDataStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("singleFrameStyleId")) 
			            json.put("singleFrameStyleId", ""+_StyleTheme.getSingleFrameStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("singleSubjectStyleId")) 
			            json.put("singleSubjectStyleId", ""+_StyleTheme.getSingleSubjectStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("singleDataStyleId")) 
			            json.put("singleDataStyleId", ""+_StyleTheme.getSingleDataStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("contentPanelStyleId")) 
			            json.put("contentPanelStyleId", ""+_StyleTheme.getContentPanelStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("contentPanelTitleStyleId")) 
			            json.put("contentPanelTitleStyleId", ""+_StyleTheme.getContentPanelTitleStyleId());
		            if ( ignoreFieldSet || fieldSet.contains("global")) 
			            json.put("global", ""+_StyleTheme.getGlobal());
		            if ( ignoreFieldSet || fieldSet.contains("disable")) 
			            json.put("disable", ""+_StyleTheme.getDisable());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_StyleTheme.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonTitle = new JSONObject();
	            jsonTitle.put("name", "title");
	            jsonTitle.put("value", ""+_StyleTheme.getTitle());
	            array.put(jsonTitle);

	            JSONObject jsonBodyWidth = new JSONObject();
	            jsonBodyWidth.put("name", "bodyWidth");
	            jsonBodyWidth.put("value", ""+_StyleTheme.getBodyWidth());
	            array.put(jsonBodyWidth);

	            JSONObject jsonBodyAlign = new JSONObject();
	            jsonBodyAlign.put("name", "bodyAlign");
	            jsonBodyAlign.put("value", ""+_StyleTheme.getBodyAlign());
	            array.put(jsonBodyAlign);

	            JSONObject jsonBodyBgColor = new JSONObject();
	            jsonBodyBgColor.put("name", "bodyBgColor");
	            jsonBodyBgColor.put("value", ""+_StyleTheme.getBodyBgColor());
	            array.put(jsonBodyBgColor);

	            JSONObject jsonBodyBgImage = new JSONObject();
	            jsonBodyBgImage.put("name", "bodyBgImage");
	            jsonBodyBgImage.put("value", ""+_StyleTheme.getBodyBgImage());
	            array.put(jsonBodyBgImage);

	            JSONObject jsonBodyBgAttach = new JSONObject();
	            jsonBodyBgAttach.put("name", "bodyBgAttach");
	            jsonBodyBgAttach.put("value", ""+_StyleTheme.getBodyBgAttach());
	            array.put(jsonBodyBgAttach);

	            JSONObject jsonBodyBgRepeat = new JSONObject();
	            jsonBodyBgRepeat.put("name", "bodyBgRepeat");
	            jsonBodyBgRepeat.put("value", ""+_StyleTheme.getBodyBgRepeat());
	            array.put(jsonBodyBgRepeat);

	            JSONObject jsonBodyBgPosition = new JSONObject();
	            jsonBodyBgPosition.put("name", "bodyBgPosition");
	            jsonBodyBgPosition.put("value", ""+_StyleTheme.getBodyBgPosition());
	            array.put(jsonBodyBgPosition);

	            JSONObject jsonContentBgColor = new JSONObject();
	            jsonContentBgColor.put("name", "contentBgColor");
	            jsonContentBgColor.put("value", ""+_StyleTheme.getContentBgColor());
	            array.put(jsonContentBgColor);

	            JSONObject jsonUseAbsolute = new JSONObject();
	            jsonUseAbsolute.put("name", "useAbsolute");
	            jsonUseAbsolute.put("value", ""+_StyleTheme.getUseAbsolute());
	            array.put(jsonUseAbsolute);

	            JSONObject jsonAbsoluteTop = new JSONObject();
	            jsonAbsoluteTop.put("name", "absoluteTop");
	            jsonAbsoluteTop.put("value", ""+_StyleTheme.getAbsoluteTop());
	            array.put(jsonAbsoluteTop);

	            JSONObject jsonAbsoluteLeft = new JSONObject();
	            jsonAbsoluteLeft.put("name", "absoluteLeft");
	            jsonAbsoluteLeft.put("value", ""+_StyleTheme.getAbsoluteLeft());
	            array.put(jsonAbsoluteLeft);

	            JSONObject jsonAbsoluteRight = new JSONObject();
	            jsonAbsoluteRight.put("name", "absoluteRight");
	            jsonAbsoluteRight.put("value", ""+_StyleTheme.getAbsoluteRight());
	            array.put(jsonAbsoluteRight);

	            JSONObject jsonAbsoluteBottom = new JSONObject();
	            jsonAbsoluteBottom.put("name", "absoluteBottom");
	            jsonAbsoluteBottom.put("value", ""+_StyleTheme.getAbsoluteBottom());
	            array.put(jsonAbsoluteBottom);

	            JSONObject jsonPanelStyleId = new JSONObject();
	            jsonPanelStyleId.put("name", "panelStyleId");
	            jsonPanelStyleId.put("value", ""+_StyleTheme.getPanelStyleId());
	            array.put(jsonPanelStyleId);

	            JSONObject jsonPanelDataStyleId = new JSONObject();
	            jsonPanelDataStyleId.put("name", "panelDataStyleId");
	            jsonPanelDataStyleId.put("value", ""+_StyleTheme.getPanelDataStyleId());
	            array.put(jsonPanelDataStyleId);

	            JSONObject jsonPanelLinkStyleId = new JSONObject();
	            jsonPanelLinkStyleId.put("name", "panelLinkStyleId");
	            jsonPanelLinkStyleId.put("value", ""+_StyleTheme.getPanelLinkStyleId());
	            array.put(jsonPanelLinkStyleId);

	            JSONObject jsonPanelTitleStyleId = new JSONObject();
	            jsonPanelTitleStyleId.put("name", "panelTitleStyleId");
	            jsonPanelTitleStyleId.put("value", ""+_StyleTheme.getPanelTitleStyleId());
	            array.put(jsonPanelTitleStyleId);

	            JSONObject jsonMenuStyleId = new JSONObject();
	            jsonMenuStyleId.put("name", "menuStyleId");
	            jsonMenuStyleId.put("value", ""+_StyleTheme.getMenuStyleId());
	            array.put(jsonMenuStyleId);

	            JSONObject jsonMenuLinkStyleId = new JSONObject();
	            jsonMenuLinkStyleId.put("name", "menuLinkStyleId");
	            jsonMenuLinkStyleId.put("value", ""+_StyleTheme.getMenuLinkStyleId());
	            array.put(jsonMenuLinkStyleId);

	            JSONObject jsonHeaderMenuStyleId = new JSONObject();
	            jsonHeaderMenuStyleId.put("name", "headerMenuStyleId");
	            jsonHeaderMenuStyleId.put("value", ""+_StyleTheme.getHeaderMenuStyleId());
	            array.put(jsonHeaderMenuStyleId);

	            JSONObject jsonHeaderMenuLinkStyleId = new JSONObject();
	            jsonHeaderMenuLinkStyleId.put("name", "headerMenuLinkStyleId");
	            jsonHeaderMenuLinkStyleId.put("value", ""+_StyleTheme.getHeaderMenuLinkStyleId());
	            array.put(jsonHeaderMenuLinkStyleId);

	            JSONObject jsonListFrameStyleId = new JSONObject();
	            jsonListFrameStyleId.put("name", "listFrameStyleId");
	            jsonListFrameStyleId.put("value", ""+_StyleTheme.getListFrameStyleId());
	            array.put(jsonListFrameStyleId);

	            JSONObject jsonListSubjectStyleId = new JSONObject();
	            jsonListSubjectStyleId.put("name", "listSubjectStyleId");
	            jsonListSubjectStyleId.put("value", ""+_StyleTheme.getListSubjectStyleId());
	            array.put(jsonListSubjectStyleId);

	            JSONObject jsonListDataStyleId = new JSONObject();
	            jsonListDataStyleId.put("name", "listDataStyleId");
	            jsonListDataStyleId.put("value", ""+_StyleTheme.getListDataStyleId());
	            array.put(jsonListDataStyleId);

	            JSONObject jsonSubjectStyleId = new JSONObject();
	            jsonSubjectStyleId.put("name", "subjectStyleId");
	            jsonSubjectStyleId.put("value", ""+_StyleTheme.getSubjectStyleId());
	            array.put(jsonSubjectStyleId);

	            JSONObject jsonDataStyleId = new JSONObject();
	            jsonDataStyleId.put("name", "dataStyleId");
	            jsonDataStyleId.put("value", ""+_StyleTheme.getDataStyleId());
	            array.put(jsonDataStyleId);

	            JSONObject jsonSingleFrameStyleId = new JSONObject();
	            jsonSingleFrameStyleId.put("name", "singleFrameStyleId");
	            jsonSingleFrameStyleId.put("value", ""+_StyleTheme.getSingleFrameStyleId());
	            array.put(jsonSingleFrameStyleId);

	            JSONObject jsonSingleSubjectStyleId = new JSONObject();
	            jsonSingleSubjectStyleId.put("name", "singleSubjectStyleId");
	            jsonSingleSubjectStyleId.put("value", ""+_StyleTheme.getSingleSubjectStyleId());
	            array.put(jsonSingleSubjectStyleId);

	            JSONObject jsonSingleDataStyleId = new JSONObject();
	            jsonSingleDataStyleId.put("name", "singleDataStyleId");
	            jsonSingleDataStyleId.put("value", ""+_StyleTheme.getSingleDataStyleId());
	            array.put(jsonSingleDataStyleId);

	            JSONObject jsonContentPanelStyleId = new JSONObject();
	            jsonContentPanelStyleId.put("name", "contentPanelStyleId");
	            jsonContentPanelStyleId.put("value", ""+_StyleTheme.getContentPanelStyleId());
	            array.put(jsonContentPanelStyleId);

	            JSONObject jsonContentPanelTitleStyleId = new JSONObject();
	            jsonContentPanelTitleStyleId.put("name", "contentPanelTitleStyleId");
	            jsonContentPanelTitleStyleId.put("value", ""+_StyleTheme.getContentPanelTitleStyleId());
	            array.put(jsonContentPanelTitleStyleId);

	            JSONObject jsonGlobal = new JSONObject();
	            jsonGlobal.put("name", "global");
	            jsonGlobal.put("value", ""+_StyleTheme.getGlobal());
	            array.put(jsonGlobal);

	            JSONObject jsonDisable = new JSONObject();
	            jsonDisable.put("name", "disable");
	            jsonDisable.put("value", ""+_StyleTheme.getDisable());
	            array.put(jsonDisable);


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
            StyleTheme _StyleTheme = null; 
            List list = StyleThemeDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _StyleTheme = (StyleTheme) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _StyleTheme = (StyleTheme) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _StyleTheme = StyleThemeDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_StyleTheme);

        } else {
            
            List list = StyleThemeDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected StyleThemeDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( StyleThemeAction.class);
}
