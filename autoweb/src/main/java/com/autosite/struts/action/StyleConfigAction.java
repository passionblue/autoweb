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
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.jtrend.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.StyleConfigForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.access.AccessConfigManager;
import com.autosite.util.access.AccessDef;
import com.autosite.util.access.AccessDef.ActionType;
import com.autosite.util.access.AccessDef.SystemRole;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check




public class StyleConfigAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(StyleConfigAction.class);

    public StyleConfigAction(){
        m_ds = StyleConfigDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        StyleConfigForm _StyleConfigForm = (StyleConfigForm) form;
        HttpSession session = request.getSession();
        DefaultPageForwardManager pageManager = DefaultPageForwardManager.getInstance();
        String sentPage = getSentPage(request);

        setPage(session, getDefaultPage()); //TODO changed all setPage to  processPageForAction() in other places. dont know what to do here

        Site site = getSite(request);
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }

		// Check if needs confirmTo step
        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "XXXXXXXXXXX", "true"))) // This line is just added for template. if you dont see any command above, add command in template field
        {    
            if (forwardConfirmTo(request))
                return mapping.findForward("default");
        
        }


		// Check permissions 
        if (!haveAccessToUpdate(session) ){
            sessionErrorText(session, "Permission error occurred.");
            processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));

            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }

        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");

			//Default error page but will be overridden by exception specific error page
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        StyleConfig _StyleConfig = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _StyleConfig = m_ds.getById(cid);
		}



		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleConfig _StyleConfig = m_ds.getById(cid);

            if (_StyleConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_StyleConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleConfig.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_StyleConfig);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _StyleConfigForm, _StyleConfig);
                if (returnObjects != null) returnObjects.put("target", _StyleConfig);
            }

            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session, ((ActionExtentException)e).getForwardPage(), 
                            pageManager.isInternalForward(getActionName(), "edit", "error"));
                return mapping.findForward("default");
            }

			//Default error page but will be overridden by exception specific error page
            processPageForAction(request, "edit", "success", getAfterEditPage());

            return mapping.findForward("default");
    
		// ================== EDIT FIELD =====================================================================================
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleConfig _StyleConfig = m_ds.getById(cid);

            if (_StyleConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_StyleConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleConfig.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _StyleConfig);
                if (returnObjects != null) returnObjects.put("target", _StyleConfig);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "editfield", "error"));
                return mapping.findForward("default");
            }

            processPageForAction(request, "editfield", "success", getAfterEditPage());
            return mapping.findForward("default");

		// ================== DEL =====================================================================================
        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleConfig _StyleConfig = m_ds.getById(cid);

            if (_StyleConfig == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_StyleConfig.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleConfig.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _StyleConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "delete", "error"));

                return mapping.findForward("default");
            }

            m_ds.delete(_StyleConfig); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _StyleConfig);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _StyleConfig);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "delete", "error"));

                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }

		// ================== ADD =====================================================================================
        } else if (isActionCmdAdd(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request)) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 


            m_logger.info("Creating new StyleConfig" );
            StyleConfig _StyleConfigNew = new StyleConfig();   

            // Setting IDs for the object
            _StyleConfigNew.setSiteId(site.getId());

            _StyleConfigNew.setStyleKey(WebParamUtil.getStringValue(_StyleConfigForm.getStyleKey()));
            m_logger.debug("setting StyleKey=" +_StyleConfigForm.getStyleKey());
            _StyleConfigNew.setStyleUse(WebParamUtil.getIntegerValue(_StyleConfigForm.getStyleUse()));
            m_logger.debug("setting StyleUse=" +_StyleConfigForm.getStyleUse());
            _StyleConfigNew.setIsGlobal(WebParamUtil.getIntegerValue(_StyleConfigForm.getIsGlobal()));
            m_logger.debug("setting IsGlobal=" +_StyleConfigForm.getIsGlobal());
            _StyleConfigNew.setIdClass(WebParamUtil.getStringValue(_StyleConfigForm.getIdClass()));
            m_logger.debug("setting IdClass=" +_StyleConfigForm.getIdClass());
            _StyleConfigNew.setIsId(WebParamUtil.getIntegerValue(_StyleConfigForm.getIsId()));
            m_logger.debug("setting IsId=" +_StyleConfigForm.getIsId());
            _StyleConfigNew.setColor(WebParamUtil.getStringValue(_StyleConfigForm.getColor()));
            m_logger.debug("setting Color=" +_StyleConfigForm.getColor());
            _StyleConfigNew.setBgColor(WebParamUtil.getStringValue(_StyleConfigForm.getBgColor()));
            m_logger.debug("setting BgColor=" +_StyleConfigForm.getBgColor());
            _StyleConfigNew.setBgImage(WebParamUtil.getStringValue(_StyleConfigForm.getBgImage()));
            m_logger.debug("setting BgImage=" +_StyleConfigForm.getBgImage());
            _StyleConfigNew.setBgRepeat(WebParamUtil.getStringValue(_StyleConfigForm.getBgRepeat()));
            m_logger.debug("setting BgRepeat=" +_StyleConfigForm.getBgRepeat());
            _StyleConfigNew.setBgAttach(WebParamUtil.getStringValue(_StyleConfigForm.getBgAttach()));
            m_logger.debug("setting BgAttach=" +_StyleConfigForm.getBgAttach());
            _StyleConfigNew.setBgPosition(WebParamUtil.getStringValue(_StyleConfigForm.getBgPosition()));
            m_logger.debug("setting BgPosition=" +_StyleConfigForm.getBgPosition());
            _StyleConfigNew.setTextAlign(WebParamUtil.getStringValue(_StyleConfigForm.getTextAlign()));
            m_logger.debug("setting TextAlign=" +_StyleConfigForm.getTextAlign());
            _StyleConfigNew.setFontFamily(WebParamUtil.getStringValue(_StyleConfigForm.getFontFamily()));
            m_logger.debug("setting FontFamily=" +_StyleConfigForm.getFontFamily());
            _StyleConfigNew.setFontSize(WebParamUtil.getStringValue(_StyleConfigForm.getFontSize()));
            m_logger.debug("setting FontSize=" +_StyleConfigForm.getFontSize());
            _StyleConfigNew.setFontStyle(WebParamUtil.getStringValue(_StyleConfigForm.getFontStyle()));
            m_logger.debug("setting FontStyle=" +_StyleConfigForm.getFontStyle());
            _StyleConfigNew.setFontVariant(WebParamUtil.getStringValue(_StyleConfigForm.getFontVariant()));
            m_logger.debug("setting FontVariant=" +_StyleConfigForm.getFontVariant());
            _StyleConfigNew.setFontWeight(WebParamUtil.getStringValue(_StyleConfigForm.getFontWeight()));
            m_logger.debug("setting FontWeight=" +_StyleConfigForm.getFontWeight());
            _StyleConfigNew.setBorderDirection(WebParamUtil.getStringValue(_StyleConfigForm.getBorderDirection()));
            m_logger.debug("setting BorderDirection=" +_StyleConfigForm.getBorderDirection());
            _StyleConfigNew.setBorderWidth(WebParamUtil.getStringValue(_StyleConfigForm.getBorderWidth()));
            m_logger.debug("setting BorderWidth=" +_StyleConfigForm.getBorderWidth());
            _StyleConfigNew.setBorderStyle(WebParamUtil.getStringValue(_StyleConfigForm.getBorderStyle()));
            m_logger.debug("setting BorderStyle=" +_StyleConfigForm.getBorderStyle());
            _StyleConfigNew.setBorderColor(WebParamUtil.getStringValue(_StyleConfigForm.getBorderColor()));
            m_logger.debug("setting BorderColor=" +_StyleConfigForm.getBorderColor());
            _StyleConfigNew.setMargin(WebParamUtil.getStringValue(_StyleConfigForm.getMargin()));
            m_logger.debug("setting Margin=" +_StyleConfigForm.getMargin());
            _StyleConfigNew.setPadding(WebParamUtil.getStringValue(_StyleConfigForm.getPadding()));
            m_logger.debug("setting Padding=" +_StyleConfigForm.getPadding());
            _StyleConfigNew.setListStyleType(WebParamUtil.getStringValue(_StyleConfigForm.getListStyleType()));
            m_logger.debug("setting ListStyleType=" +_StyleConfigForm.getListStyleType());
            _StyleConfigNew.setListStylePosition(WebParamUtil.getStringValue(_StyleConfigForm.getListStylePosition()));
            m_logger.debug("setting ListStylePosition=" +_StyleConfigForm.getListStylePosition());
            _StyleConfigNew.setListStyleImage(WebParamUtil.getStringValue(_StyleConfigForm.getListStyleImage()));
            m_logger.debug("setting ListStyleImage=" +_StyleConfigForm.getListStyleImage());
            _StyleConfigNew.setFloating(WebParamUtil.getStringValue(_StyleConfigForm.getFloating()));
            m_logger.debug("setting Floating=" +_StyleConfigForm.getFloating());
            _StyleConfigNew.setExtraStyleStr(WebParamUtil.getStringValue(_StyleConfigForm.getExtraStyleStr()));
            m_logger.debug("setting ExtraStyleStr=" +_StyleConfigForm.getExtraStyleStr());
            _StyleConfigNew.setItemStyleStr(WebParamUtil.getStringValue(_StyleConfigForm.getItemStyleStr()));
            m_logger.debug("setting ItemStyleStr=" +_StyleConfigForm.getItemStyleStr());
            _StyleConfigNew.setLink(WebParamUtil.getStringValue(_StyleConfigForm.getLink()));
            m_logger.debug("setting Link=" +_StyleConfigForm.getLink());
            _StyleConfigNew.setLinkHover(WebParamUtil.getStringValue(_StyleConfigForm.getLinkHover()));
            m_logger.debug("setting LinkHover=" +_StyleConfigForm.getLinkHover());
            _StyleConfigNew.setLinkActive(WebParamUtil.getStringValue(_StyleConfigForm.getLinkActive()));
            m_logger.debug("setting LinkActive=" +_StyleConfigForm.getLinkActive());
            _StyleConfigNew.setHeight(WebParamUtil.getIntegerValue(_StyleConfigForm.getHeight()));
            m_logger.debug("setting Height=" +_StyleConfigForm.getHeight());
            _StyleConfigNew.setWidth(WebParamUtil.getIntegerValue(_StyleConfigForm.getWidth()));
            m_logger.debug("setting Width=" +_StyleConfigForm.getWidth());
            _StyleConfigNew.setIsTable(WebParamUtil.getIntegerValue(_StyleConfigForm.getIsTable()));
            m_logger.debug("setting IsTable=" +_StyleConfigForm.getIsTable());
            _StyleConfigNew.setBorderCollapse(WebParamUtil.getStringValue(_StyleConfigForm.getBorderCollapse()));
            m_logger.debug("setting BorderCollapse=" +_StyleConfigForm.getBorderCollapse());
            _StyleConfigNew.setBorderSpacing(WebParamUtil.getStringValue(_StyleConfigForm.getBorderSpacing()));
            m_logger.debug("setting BorderSpacing=" +_StyleConfigForm.getBorderSpacing());
            _StyleConfigNew.setTrStyleIds(WebParamUtil.getStringValue(_StyleConfigForm.getTrStyleIds()));
            m_logger.debug("setting TrStyleIds=" +_StyleConfigForm.getTrStyleIds());
            _StyleConfigNew.setTdStyleIds(WebParamUtil.getStringValue(_StyleConfigForm.getTdStyleIds()));
            m_logger.debug("setting TdStyleIds=" +_StyleConfigForm.getTdStyleIds());
            _StyleConfigNew.setTimeCreated(WebParamUtil.getTimestampValue(_StyleConfigForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_StyleConfigForm.getTimeCreated());
            _StyleConfigNew.setTimeUpdated(WebParamUtil.getTimestampValue(_StyleConfigForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_StyleConfigForm.getTimeUpdated());


            try {
                checkDepedenceIntegrity(_StyleConfigNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _StyleConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "add", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "add", "error"));
                return mapping.findForward("default");
            }
            
            if (_StyleConfigNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_StyleConfigNew);
            if (returnObjects != null) returnObjects.put("target", _StyleConfig);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _StyleConfigNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "add", "error", getErrorPage(), getSentPage(request));

	            if (e instanceof ActionExtentException)
	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "add", "error"));
                return mapping.findForward("default");
            }
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, StyleConfigForm _StyleConfigForm, StyleConfig _StyleConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleConfig _StyleConfig = m_ds.getById(cid);

        m_logger.debug("Before update " + StyleConfigDS.objectToString(_StyleConfig));

        _StyleConfig.setStyleKey(WebParamUtil.getStringValue(_StyleConfigForm.getStyleKey()));
        _StyleConfig.setStyleUse(WebParamUtil.getIntegerValue(_StyleConfigForm.getStyleUse()));
        _StyleConfig.setIsGlobal(WebParamUtil.getIntegerValue(_StyleConfigForm.getIsGlobal()));
        _StyleConfig.setIdClass(WebParamUtil.getStringValue(_StyleConfigForm.getIdClass()));
        _StyleConfig.setIsId(WebParamUtil.getIntegerValue(_StyleConfigForm.getIsId()));
        _StyleConfig.setColor(WebParamUtil.getStringValue(_StyleConfigForm.getColor()));
        _StyleConfig.setBgColor(WebParamUtil.getStringValue(_StyleConfigForm.getBgColor()));
        _StyleConfig.setBgImage(WebParamUtil.getStringValue(_StyleConfigForm.getBgImage()));
        _StyleConfig.setBgRepeat(WebParamUtil.getStringValue(_StyleConfigForm.getBgRepeat()));
        _StyleConfig.setBgAttach(WebParamUtil.getStringValue(_StyleConfigForm.getBgAttach()));
        _StyleConfig.setBgPosition(WebParamUtil.getStringValue(_StyleConfigForm.getBgPosition()));
        _StyleConfig.setTextAlign(WebParamUtil.getStringValue(_StyleConfigForm.getTextAlign()));
        _StyleConfig.setFontFamily(WebParamUtil.getStringValue(_StyleConfigForm.getFontFamily()));
        _StyleConfig.setFontSize(WebParamUtil.getStringValue(_StyleConfigForm.getFontSize()));
        _StyleConfig.setFontStyle(WebParamUtil.getStringValue(_StyleConfigForm.getFontStyle()));
        _StyleConfig.setFontVariant(WebParamUtil.getStringValue(_StyleConfigForm.getFontVariant()));
        _StyleConfig.setFontWeight(WebParamUtil.getStringValue(_StyleConfigForm.getFontWeight()));
        _StyleConfig.setBorderDirection(WebParamUtil.getStringValue(_StyleConfigForm.getBorderDirection()));
        _StyleConfig.setBorderWidth(WebParamUtil.getStringValue(_StyleConfigForm.getBorderWidth()));
        _StyleConfig.setBorderStyle(WebParamUtil.getStringValue(_StyleConfigForm.getBorderStyle()));
        _StyleConfig.setBorderColor(WebParamUtil.getStringValue(_StyleConfigForm.getBorderColor()));
        _StyleConfig.setMargin(WebParamUtil.getStringValue(_StyleConfigForm.getMargin()));
        _StyleConfig.setPadding(WebParamUtil.getStringValue(_StyleConfigForm.getPadding()));
        _StyleConfig.setListStyleType(WebParamUtil.getStringValue(_StyleConfigForm.getListStyleType()));
        _StyleConfig.setListStylePosition(WebParamUtil.getStringValue(_StyleConfigForm.getListStylePosition()));
        _StyleConfig.setListStyleImage(WebParamUtil.getStringValue(_StyleConfigForm.getListStyleImage()));
        _StyleConfig.setFloating(WebParamUtil.getStringValue(_StyleConfigForm.getFloating()));
        _StyleConfig.setExtraStyleStr(WebParamUtil.getStringValue(_StyleConfigForm.getExtraStyleStr()));
        _StyleConfig.setItemStyleStr(WebParamUtil.getStringValue(_StyleConfigForm.getItemStyleStr()));
        _StyleConfig.setLink(WebParamUtil.getStringValue(_StyleConfigForm.getLink()));
        _StyleConfig.setLinkHover(WebParamUtil.getStringValue(_StyleConfigForm.getLinkHover()));
        _StyleConfig.setLinkActive(WebParamUtil.getStringValue(_StyleConfigForm.getLinkActive()));
        _StyleConfig.setHeight(WebParamUtil.getIntegerValue(_StyleConfigForm.getHeight()));
        _StyleConfig.setWidth(WebParamUtil.getIntegerValue(_StyleConfigForm.getWidth()));
        _StyleConfig.setIsTable(WebParamUtil.getIntegerValue(_StyleConfigForm.getIsTable()));
        _StyleConfig.setBorderCollapse(WebParamUtil.getStringValue(_StyleConfigForm.getBorderCollapse()));
        _StyleConfig.setBorderSpacing(WebParamUtil.getStringValue(_StyleConfigForm.getBorderSpacing()));
        _StyleConfig.setTrStyleIds(WebParamUtil.getStringValue(_StyleConfigForm.getTrStyleIds()));
        _StyleConfig.setTdStyleIds(WebParamUtil.getStringValue(_StyleConfigForm.getTdStyleIds()));
        _StyleConfig.setTimeUpdated(WebParamUtil.getTimestampValue(_StyleConfigForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _StyleConfig);
        m_ds.update(_StyleConfig);
        m_actionExtent.afterUpdate(request, response, _StyleConfig);
        m_logger.debug("After update " + StyleConfigDS.objectToString(_StyleConfig));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, StyleConfig _StyleConfig) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleConfig _StyleConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("styleKey"))) {
            m_logger.debug("updating param styleKey from " +_StyleConfig.getStyleKey() + "->" + request.getParameter("styleKey"));
            _StyleConfig.setStyleKey(WebParamUtil.getStringValue(request.getParameter("styleKey")));

        }
        if (!isMissing(request.getParameter("styleUse"))) {
            m_logger.debug("updating param styleUse from " +_StyleConfig.getStyleUse() + "->" + request.getParameter("styleUse"));
            _StyleConfig.setStyleUse(WebParamUtil.getIntegerValue(request.getParameter("styleUse")));

        }
        if (!isMissing(request.getParameter("isGlobal"))) {
            m_logger.debug("updating param isGlobal from " +_StyleConfig.getIsGlobal() + "->" + request.getParameter("isGlobal"));
            _StyleConfig.setIsGlobal(WebParamUtil.getIntegerValue(request.getParameter("isGlobal")));

        }
        if (!isMissing(request.getParameter("idClass"))) {
            m_logger.debug("updating param idClass from " +_StyleConfig.getIdClass() + "->" + request.getParameter("idClass"));
            _StyleConfig.setIdClass(WebParamUtil.getStringValue(request.getParameter("idClass")));

        }
        if (!isMissing(request.getParameter("isId"))) {
            m_logger.debug("updating param isId from " +_StyleConfig.getIsId() + "->" + request.getParameter("isId"));
            _StyleConfig.setIsId(WebParamUtil.getIntegerValue(request.getParameter("isId")));

        }
        if (!isMissing(request.getParameter("color"))) {
            m_logger.debug("updating param color from " +_StyleConfig.getColor() + "->" + request.getParameter("color"));
            _StyleConfig.setColor(WebParamUtil.getStringValue(request.getParameter("color")));

        }
        if (!isMissing(request.getParameter("bgColor"))) {
            m_logger.debug("updating param bgColor from " +_StyleConfig.getBgColor() + "->" + request.getParameter("bgColor"));
            _StyleConfig.setBgColor(WebParamUtil.getStringValue(request.getParameter("bgColor")));

        }
        if (!isMissing(request.getParameter("bgImage"))) {
            m_logger.debug("updating param bgImage from " +_StyleConfig.getBgImage() + "->" + request.getParameter("bgImage"));
            _StyleConfig.setBgImage(WebParamUtil.getStringValue(request.getParameter("bgImage")));

        }
        if (!isMissing(request.getParameter("bgRepeat"))) {
            m_logger.debug("updating param bgRepeat from " +_StyleConfig.getBgRepeat() + "->" + request.getParameter("bgRepeat"));
            _StyleConfig.setBgRepeat(WebParamUtil.getStringValue(request.getParameter("bgRepeat")));

        }
        if (!isMissing(request.getParameter("bgAttach"))) {
            m_logger.debug("updating param bgAttach from " +_StyleConfig.getBgAttach() + "->" + request.getParameter("bgAttach"));
            _StyleConfig.setBgAttach(WebParamUtil.getStringValue(request.getParameter("bgAttach")));

        }
        if (!isMissing(request.getParameter("bgPosition"))) {
            m_logger.debug("updating param bgPosition from " +_StyleConfig.getBgPosition() + "->" + request.getParameter("bgPosition"));
            _StyleConfig.setBgPosition(WebParamUtil.getStringValue(request.getParameter("bgPosition")));

        }
        if (!isMissing(request.getParameter("textAlign"))) {
            m_logger.debug("updating param textAlign from " +_StyleConfig.getTextAlign() + "->" + request.getParameter("textAlign"));
            _StyleConfig.setTextAlign(WebParamUtil.getStringValue(request.getParameter("textAlign")));

        }
        if (!isMissing(request.getParameter("fontFamily"))) {
            m_logger.debug("updating param fontFamily from " +_StyleConfig.getFontFamily() + "->" + request.getParameter("fontFamily"));
            _StyleConfig.setFontFamily(WebParamUtil.getStringValue(request.getParameter("fontFamily")));

        }
        if (!isMissing(request.getParameter("fontSize"))) {
            m_logger.debug("updating param fontSize from " +_StyleConfig.getFontSize() + "->" + request.getParameter("fontSize"));
            _StyleConfig.setFontSize(WebParamUtil.getStringValue(request.getParameter("fontSize")));

        }
        if (!isMissing(request.getParameter("fontStyle"))) {
            m_logger.debug("updating param fontStyle from " +_StyleConfig.getFontStyle() + "->" + request.getParameter("fontStyle"));
            _StyleConfig.setFontStyle(WebParamUtil.getStringValue(request.getParameter("fontStyle")));

        }
        if (!isMissing(request.getParameter("fontVariant"))) {
            m_logger.debug("updating param fontVariant from " +_StyleConfig.getFontVariant() + "->" + request.getParameter("fontVariant"));
            _StyleConfig.setFontVariant(WebParamUtil.getStringValue(request.getParameter("fontVariant")));

        }
        if (!isMissing(request.getParameter("fontWeight"))) {
            m_logger.debug("updating param fontWeight from " +_StyleConfig.getFontWeight() + "->" + request.getParameter("fontWeight"));
            _StyleConfig.setFontWeight(WebParamUtil.getStringValue(request.getParameter("fontWeight")));

        }
        if (!isMissing(request.getParameter("borderDirection"))) {
            m_logger.debug("updating param borderDirection from " +_StyleConfig.getBorderDirection() + "->" + request.getParameter("borderDirection"));
            _StyleConfig.setBorderDirection(WebParamUtil.getStringValue(request.getParameter("borderDirection")));

        }
        if (!isMissing(request.getParameter("borderWidth"))) {
            m_logger.debug("updating param borderWidth from " +_StyleConfig.getBorderWidth() + "->" + request.getParameter("borderWidth"));
            _StyleConfig.setBorderWidth(WebParamUtil.getStringValue(request.getParameter("borderWidth")));

        }
        if (!isMissing(request.getParameter("borderStyle"))) {
            m_logger.debug("updating param borderStyle from " +_StyleConfig.getBorderStyle() + "->" + request.getParameter("borderStyle"));
            _StyleConfig.setBorderStyle(WebParamUtil.getStringValue(request.getParameter("borderStyle")));

        }
        if (!isMissing(request.getParameter("borderColor"))) {
            m_logger.debug("updating param borderColor from " +_StyleConfig.getBorderColor() + "->" + request.getParameter("borderColor"));
            _StyleConfig.setBorderColor(WebParamUtil.getStringValue(request.getParameter("borderColor")));

        }
        if (!isMissing(request.getParameter("margin"))) {
            m_logger.debug("updating param margin from " +_StyleConfig.getMargin() + "->" + request.getParameter("margin"));
            _StyleConfig.setMargin(WebParamUtil.getStringValue(request.getParameter("margin")));

        }
        if (!isMissing(request.getParameter("padding"))) {
            m_logger.debug("updating param padding from " +_StyleConfig.getPadding() + "->" + request.getParameter("padding"));
            _StyleConfig.setPadding(WebParamUtil.getStringValue(request.getParameter("padding")));

        }
        if (!isMissing(request.getParameter("listStyleType"))) {
            m_logger.debug("updating param listStyleType from " +_StyleConfig.getListStyleType() + "->" + request.getParameter("listStyleType"));
            _StyleConfig.setListStyleType(WebParamUtil.getStringValue(request.getParameter("listStyleType")));

        }
        if (!isMissing(request.getParameter("listStylePosition"))) {
            m_logger.debug("updating param listStylePosition from " +_StyleConfig.getListStylePosition() + "->" + request.getParameter("listStylePosition"));
            _StyleConfig.setListStylePosition(WebParamUtil.getStringValue(request.getParameter("listStylePosition")));

        }
        if (!isMissing(request.getParameter("listStyleImage"))) {
            m_logger.debug("updating param listStyleImage from " +_StyleConfig.getListStyleImage() + "->" + request.getParameter("listStyleImage"));
            _StyleConfig.setListStyleImage(WebParamUtil.getStringValue(request.getParameter("listStyleImage")));

        }
        if (!isMissing(request.getParameter("floating"))) {
            m_logger.debug("updating param floating from " +_StyleConfig.getFloating() + "->" + request.getParameter("floating"));
            _StyleConfig.setFloating(WebParamUtil.getStringValue(request.getParameter("floating")));

        }
        if (!isMissing(request.getParameter("extraStyleStr"))) {
            m_logger.debug("updating param extraStyleStr from " +_StyleConfig.getExtraStyleStr() + "->" + request.getParameter("extraStyleStr"));
            _StyleConfig.setExtraStyleStr(WebParamUtil.getStringValue(request.getParameter("extraStyleStr")));

        }
        if (!isMissing(request.getParameter("itemStyleStr"))) {
            m_logger.debug("updating param itemStyleStr from " +_StyleConfig.getItemStyleStr() + "->" + request.getParameter("itemStyleStr"));
            _StyleConfig.setItemStyleStr(WebParamUtil.getStringValue(request.getParameter("itemStyleStr")));

        }
        if (!isMissing(request.getParameter("link"))) {
            m_logger.debug("updating param link from " +_StyleConfig.getLink() + "->" + request.getParameter("link"));
            _StyleConfig.setLink(WebParamUtil.getStringValue(request.getParameter("link")));

        }
        if (!isMissing(request.getParameter("linkHover"))) {
            m_logger.debug("updating param linkHover from " +_StyleConfig.getLinkHover() + "->" + request.getParameter("linkHover"));
            _StyleConfig.setLinkHover(WebParamUtil.getStringValue(request.getParameter("linkHover")));

        }
        if (!isMissing(request.getParameter("linkActive"))) {
            m_logger.debug("updating param linkActive from " +_StyleConfig.getLinkActive() + "->" + request.getParameter("linkActive"));
            _StyleConfig.setLinkActive(WebParamUtil.getStringValue(request.getParameter("linkActive")));

        }
        if (!isMissing(request.getParameter("height"))) {
            m_logger.debug("updating param height from " +_StyleConfig.getHeight() + "->" + request.getParameter("height"));
            _StyleConfig.setHeight(WebParamUtil.getIntegerValue(request.getParameter("height")));

        }
        if (!isMissing(request.getParameter("width"))) {
            m_logger.debug("updating param width from " +_StyleConfig.getWidth() + "->" + request.getParameter("width"));
            _StyleConfig.setWidth(WebParamUtil.getIntegerValue(request.getParameter("width")));

        }
        if (!isMissing(request.getParameter("isTable"))) {
            m_logger.debug("updating param isTable from " +_StyleConfig.getIsTable() + "->" + request.getParameter("isTable"));
            _StyleConfig.setIsTable(WebParamUtil.getIntegerValue(request.getParameter("isTable")));

        }
        if (!isMissing(request.getParameter("borderCollapse"))) {
            m_logger.debug("updating param borderCollapse from " +_StyleConfig.getBorderCollapse() + "->" + request.getParameter("borderCollapse"));
            _StyleConfig.setBorderCollapse(WebParamUtil.getStringValue(request.getParameter("borderCollapse")));

        }
        if (!isMissing(request.getParameter("borderSpacing"))) {
            m_logger.debug("updating param borderSpacing from " +_StyleConfig.getBorderSpacing() + "->" + request.getParameter("borderSpacing"));
            _StyleConfig.setBorderSpacing(WebParamUtil.getStringValue(request.getParameter("borderSpacing")));

        }
        if (!isMissing(request.getParameter("trStyleIds"))) {
            m_logger.debug("updating param trStyleIds from " +_StyleConfig.getTrStyleIds() + "->" + request.getParameter("trStyleIds"));
            _StyleConfig.setTrStyleIds(WebParamUtil.getStringValue(request.getParameter("trStyleIds")));

        }
        if (!isMissing(request.getParameter("tdStyleIds"))) {
            m_logger.debug("updating param tdStyleIds from " +_StyleConfig.getTdStyleIds() + "->" + request.getParameter("tdStyleIds"));
            _StyleConfig.setTdStyleIds(WebParamUtil.getStringValue(request.getParameter("tdStyleIds")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_StyleConfig.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _StyleConfig.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_StyleConfig.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _StyleConfig.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _StyleConfig.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _StyleConfig);
        m_ds.update(_StyleConfig);
        m_actionExtent.afterUpdate(request, response, _StyleConfig);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, StyleConfig _StyleConfig) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleConfig _StyleConfig = m_ds.getById(cid);

        if (!isMissing(request.getParameter("styleKey"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getStyleKey());
        }
        if (!isMissing(request.getParameter("styleUse"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getStyleUse());
        }
        if (!isMissing(request.getParameter("isGlobal"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getIsGlobal());
        }
        if (!isMissing(request.getParameter("idClass"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getIdClass());
        }
        if (!isMissing(request.getParameter("isId"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getIsId());
        }
        if (!isMissing(request.getParameter("color"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getColor());
        }
        if (!isMissing(request.getParameter("bgColor"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBgColor());
        }
        if (!isMissing(request.getParameter("bgImage"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBgImage());
        }
        if (!isMissing(request.getParameter("bgRepeat"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBgRepeat());
        }
        if (!isMissing(request.getParameter("bgAttach"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBgAttach());
        }
        if (!isMissing(request.getParameter("bgPosition"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBgPosition());
        }
        if (!isMissing(request.getParameter("textAlign"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getTextAlign());
        }
        if (!isMissing(request.getParameter("fontFamily"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getFontFamily());
        }
        if (!isMissing(request.getParameter("fontSize"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getFontSize());
        }
        if (!isMissing(request.getParameter("fontStyle"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getFontStyle());
        }
        if (!isMissing(request.getParameter("fontVariant"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getFontVariant());
        }
        if (!isMissing(request.getParameter("fontWeight"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getFontWeight());
        }
        if (!isMissing(request.getParameter("borderDirection"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBorderDirection());
        }
        if (!isMissing(request.getParameter("borderWidth"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBorderWidth());
        }
        if (!isMissing(request.getParameter("borderStyle"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBorderStyle());
        }
        if (!isMissing(request.getParameter("borderColor"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBorderColor());
        }
        if (!isMissing(request.getParameter("margin"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getMargin());
        }
        if (!isMissing(request.getParameter("padding"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getPadding());
        }
        if (!isMissing(request.getParameter("listStyleType"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getListStyleType());
        }
        if (!isMissing(request.getParameter("listStylePosition"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getListStylePosition());
        }
        if (!isMissing(request.getParameter("listStyleImage"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getListStyleImage());
        }
        if (!isMissing(request.getParameter("floating"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getFloating());
        }
        if (!isMissing(request.getParameter("extraStyleStr"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getExtraStyleStr());
        }
        if (!isMissing(request.getParameter("itemStyleStr"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getItemStyleStr());
        }
        if (!isMissing(request.getParameter("link"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getLink());
        }
        if (!isMissing(request.getParameter("linkHover"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getLinkHover());
        }
        if (!isMissing(request.getParameter("linkActive"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getLinkActive());
        }
        if (!isMissing(request.getParameter("height"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getHeight());
        }
        if (!isMissing(request.getParameter("width"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getWidth());
        }
        if (!isMissing(request.getParameter("isTable"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getIsTable());
        }
        if (!isMissing(request.getParameter("borderCollapse"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBorderCollapse());
        }
        if (!isMissing(request.getParameter("borderSpacing"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getBorderSpacing());
        }
        if (!isMissing(request.getParameter("trStyleIds"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getTrStyleIds());
        }
        if (!isMissing(request.getParameter("tdStyleIds"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getTdStyleIds());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_StyleConfig.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(StyleConfig _StyleConfig) throws Exception {
    }

    protected String getFieldByName(String fieldName, StyleConfig _StyleConfig) {
        if (fieldName == null || fieldName.equals("")|| _StyleConfig == null) return null;
        
        if (fieldName.equals("styleKey")) {
            return WebUtil.display(_StyleConfig.getStyleKey());
        }
        if (fieldName.equals("styleUse")) {
            return WebUtil.display(_StyleConfig.getStyleUse());
        }
        if (fieldName.equals("isGlobal")) {
            return WebUtil.display(_StyleConfig.getIsGlobal());
        }
        if (fieldName.equals("idClass")) {
            return WebUtil.display(_StyleConfig.getIdClass());
        }
        if (fieldName.equals("isId")) {
            return WebUtil.display(_StyleConfig.getIsId());
        }
        if (fieldName.equals("color")) {
            return WebUtil.display(_StyleConfig.getColor());
        }
        if (fieldName.equals("bgColor")) {
            return WebUtil.display(_StyleConfig.getBgColor());
        }
        if (fieldName.equals("bgImage")) {
            return WebUtil.display(_StyleConfig.getBgImage());
        }
        if (fieldName.equals("bgRepeat")) {
            return WebUtil.display(_StyleConfig.getBgRepeat());
        }
        if (fieldName.equals("bgAttach")) {
            return WebUtil.display(_StyleConfig.getBgAttach());
        }
        if (fieldName.equals("bgPosition")) {
            return WebUtil.display(_StyleConfig.getBgPosition());
        }
        if (fieldName.equals("textAlign")) {
            return WebUtil.display(_StyleConfig.getTextAlign());
        }
        if (fieldName.equals("fontFamily")) {
            return WebUtil.display(_StyleConfig.getFontFamily());
        }
        if (fieldName.equals("fontSize")) {
            return WebUtil.display(_StyleConfig.getFontSize());
        }
        if (fieldName.equals("fontStyle")) {
            return WebUtil.display(_StyleConfig.getFontStyle());
        }
        if (fieldName.equals("fontVariant")) {
            return WebUtil.display(_StyleConfig.getFontVariant());
        }
        if (fieldName.equals("fontWeight")) {
            return WebUtil.display(_StyleConfig.getFontWeight());
        }
        if (fieldName.equals("borderDirection")) {
            return WebUtil.display(_StyleConfig.getBorderDirection());
        }
        if (fieldName.equals("borderWidth")) {
            return WebUtil.display(_StyleConfig.getBorderWidth());
        }
        if (fieldName.equals("borderStyle")) {
            return WebUtil.display(_StyleConfig.getBorderStyle());
        }
        if (fieldName.equals("borderColor")) {
            return WebUtil.display(_StyleConfig.getBorderColor());
        }
        if (fieldName.equals("margin")) {
            return WebUtil.display(_StyleConfig.getMargin());
        }
        if (fieldName.equals("padding")) {
            return WebUtil.display(_StyleConfig.getPadding());
        }
        if (fieldName.equals("listStyleType")) {
            return WebUtil.display(_StyleConfig.getListStyleType());
        }
        if (fieldName.equals("listStylePosition")) {
            return WebUtil.display(_StyleConfig.getListStylePosition());
        }
        if (fieldName.equals("listStyleImage")) {
            return WebUtil.display(_StyleConfig.getListStyleImage());
        }
        if (fieldName.equals("floating")) {
            return WebUtil.display(_StyleConfig.getFloating());
        }
        if (fieldName.equals("extraStyleStr")) {
            return WebUtil.display(_StyleConfig.getExtraStyleStr());
        }
        if (fieldName.equals("itemStyleStr")) {
            return WebUtil.display(_StyleConfig.getItemStyleStr());
        }
        if (fieldName.equals("link")) {
            return WebUtil.display(_StyleConfig.getLink());
        }
        if (fieldName.equals("linkHover")) {
            return WebUtil.display(_StyleConfig.getLinkHover());
        }
        if (fieldName.equals("linkActive")) {
            return WebUtil.display(_StyleConfig.getLinkActive());
        }
        if (fieldName.equals("height")) {
            return WebUtil.display(_StyleConfig.getHeight());
        }
        if (fieldName.equals("width")) {
            return WebUtil.display(_StyleConfig.getWidth());
        }
        if (fieldName.equals("isTable")) {
            return WebUtil.display(_StyleConfig.getIsTable());
        }
        if (fieldName.equals("borderCollapse")) {
            return WebUtil.display(_StyleConfig.getBorderCollapse());
        }
        if (fieldName.equals("borderSpacing")) {
            return WebUtil.display(_StyleConfig.getBorderSpacing());
        }
        if (fieldName.equals("trStyleIds")) {
            return WebUtil.display(_StyleConfig.getTrStyleIds());
        }
        if (fieldName.equals("tdStyleIds")) {
            return WebUtil.display(_StyleConfig.getTdStyleIds());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_StyleConfig.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_StyleConfig.getTimeUpdated());
        }
		return null;
    }



/*
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        StyleConfig target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (StyleConfig) working.get("target");
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        // ================================================================================
        // Response Processing 
        // ================================================================================

        if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // If there is no error, following message will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") )
                ret.put("__value", "Successfully deleted.");
            if (hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") )
                ret.put("__value", "Successfully created.");
            if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef"))     
                ret.put("__value", "Successfully changed.");
            if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") )
                ret.put("__value", "Successfully changed.");
            else 
                ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getcode")){
            // If there is no error, nothing will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getstatus arg = " + request.getParameter("ajaxOutArg"));

        } else if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            StyleConfig _StyleConfig = StyleConfigDS.getInstance().getById(id);
            if (_StyleConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _StyleConfig);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            StyleConfig _StyleConfig = StyleConfigDS.getInstance().getById(id);
            if ( _StyleConfig == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _StyleConfig);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();

            buf.append("<div id=\"styleConfig-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                StyleConfig _StyleConfig = (StyleConfig) iterator.next();

                buf.append("<div id=\"styleConfig-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
                    buf.append("<div id=\"styleConfig-ajax-styleKey\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getStyleKey()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
                    buf.append("<div id=\"styleConfig-ajax-styleUse\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getStyleUse()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
                    buf.append("<div id=\"styleConfig-ajax-isGlobal\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIsGlobal()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
                    buf.append("<div id=\"styleConfig-ajax-idClass\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIdClass()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isId")) {
                    buf.append("<div id=\"styleConfig-ajax-isId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIsId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("color")) {
                    buf.append("<div id=\"styleConfig-ajax-color\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
                    buf.append("<div id=\"styleConfig-ajax-bgColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
                    buf.append("<div id=\"styleConfig-ajax-bgImage\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgImage()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
                    buf.append("<div id=\"styleConfig-ajax-bgRepeat\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgRepeat()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
                    buf.append("<div id=\"styleConfig-ajax-bgAttach\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgAttach()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
                    buf.append("<div id=\"styleConfig-ajax-bgPosition\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgPosition()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
                    buf.append("<div id=\"styleConfig-ajax-textAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTextAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
                    buf.append("<div id=\"styleConfig-ajax-fontFamily\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontFamily()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
                    buf.append("<div id=\"styleConfig-ajax-fontSize\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontSize()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
                    buf.append("<div id=\"styleConfig-ajax-fontStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
                    buf.append("<div id=\"styleConfig-ajax-fontVariant\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontVariant()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
                    buf.append("<div id=\"styleConfig-ajax-fontWeight\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontWeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
                    buf.append("<div id=\"styleConfig-ajax-borderDirection\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderDirection()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
                    buf.append("<div id=\"styleConfig-ajax-borderWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
                    buf.append("<div id=\"styleConfig-ajax-borderStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
                    buf.append("<div id=\"styleConfig-ajax-borderColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("margin")) {
                    buf.append("<div id=\"styleConfig-ajax-margin\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getMargin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("padding")) {
                    buf.append("<div id=\"styleConfig-ajax-padding\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getPadding()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
                    buf.append("<div id=\"styleConfig-ajax-listStyleType\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getListStyleType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
                    buf.append("<div id=\"styleConfig-ajax-listStylePosition\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getListStylePosition()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
                    buf.append("<div id=\"styleConfig-ajax-listStyleImage\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getListStyleImage()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("floating")) {
                    buf.append("<div id=\"styleConfig-ajax-floating\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFloating()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
                    buf.append("<div id=\"styleConfig-ajax-extraStyleStr\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getExtraStyleStr()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
                    buf.append("<div id=\"styleConfig-ajax-itemStyleStr\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getItemStyleStr()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("link")) {
                    buf.append("<div id=\"styleConfig-ajax-link\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getLink()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
                    buf.append("<div id=\"styleConfig-ajax-linkHover\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getLinkHover()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
                    buf.append("<div id=\"styleConfig-ajax-linkActive\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getLinkActive()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("height")) {
                    buf.append("<div id=\"styleConfig-ajax-height\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getHeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("width")) {
                    buf.append("<div id=\"styleConfig-ajax-width\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
                    buf.append("<div id=\"styleConfig-ajax-isTable\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIsTable()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
                    buf.append("<div id=\"styleConfig-ajax-borderCollapse\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderCollapse()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
                    buf.append("<div id=\"styleConfig-ajax-borderSpacing\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderSpacing()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
                    buf.append("<div id=\"styleConfig-ajax-trStyleIds\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTrStyleIds()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
                    buf.append("<div id=\"styleConfig-ajax-tdStyleIds\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTdStyleIds()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"styleConfig-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"styleConfig-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTimeUpdated()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();
            
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
            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Style Use");
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
            if ( ignoreFieldSet || fieldSet.contains("color")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Image");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Repeat");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Attach");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Position");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Text Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Family");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Size");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Variant");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Weight");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Direction");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Color");
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
            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Style Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Style Position");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Style Image");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("floating")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Floating");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Extra Style Str");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Item Style Str");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("link")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Link");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Link Hover");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Link Active");
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
            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Table");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Collapse");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Spacing");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Tr Style Ids");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Td Style Ids");
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
                StyleConfig _StyleConfig = (StyleConfig) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getStyleKey()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getStyleUse()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIsGlobal()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIdClass()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIsId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("color")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgImage()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgRepeat()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgAttach()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgPosition()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTextAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontFamily()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontSize()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontVariant()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontWeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderDirection()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("margin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getMargin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("padding")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getPadding()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getListStyleType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getListStylePosition()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getListStyleImage()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("floating")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFloating()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getExtraStyleStr()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getItemStyleStr()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("link")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getLink()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getLinkHover()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getLinkActive()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("height")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getHeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("width")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIsTable()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderCollapse()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderSpacing()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTrStyleIds()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTdStyleIds()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTimeUpdated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                StyleConfig _StyleConfig = (StyleConfig) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("styleKey")) 
			            json.put("styleKey", ""+_StyleConfig.getStyleKey());
		            if ( ignoreFieldSet || fieldSet.contains("styleUse")) 
			            json.put("styleUse", ""+_StyleConfig.getStyleUse());
		            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) 
			            json.put("isGlobal", ""+_StyleConfig.getIsGlobal());
		            if ( ignoreFieldSet || fieldSet.contains("idClass")) 
			            json.put("idClass", ""+_StyleConfig.getIdClass());
		            if ( ignoreFieldSet || fieldSet.contains("isId")) 
			            json.put("isId", ""+_StyleConfig.getIsId());
		            if ( ignoreFieldSet || fieldSet.contains("color")) 
			            json.put("color", ""+_StyleConfig.getColor());
		            if ( ignoreFieldSet || fieldSet.contains("bgColor")) 
			            json.put("bgColor", ""+_StyleConfig.getBgColor());
		            if ( ignoreFieldSet || fieldSet.contains("bgImage")) 
			            json.put("bgImage", ""+_StyleConfig.getBgImage());
		            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) 
			            json.put("bgRepeat", ""+_StyleConfig.getBgRepeat());
		            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) 
			            json.put("bgAttach", ""+_StyleConfig.getBgAttach());
		            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) 
			            json.put("bgPosition", ""+_StyleConfig.getBgPosition());
		            if ( ignoreFieldSet || fieldSet.contains("textAlign")) 
			            json.put("textAlign", ""+_StyleConfig.getTextAlign());
		            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) 
			            json.put("fontFamily", ""+_StyleConfig.getFontFamily());
		            if ( ignoreFieldSet || fieldSet.contains("fontSize")) 
			            json.put("fontSize", ""+_StyleConfig.getFontSize());
		            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) 
			            json.put("fontStyle", ""+_StyleConfig.getFontStyle());
		            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) 
			            json.put("fontVariant", ""+_StyleConfig.getFontVariant());
		            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) 
			            json.put("fontWeight", ""+_StyleConfig.getFontWeight());
		            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) 
			            json.put("borderDirection", ""+_StyleConfig.getBorderDirection());
		            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) 
			            json.put("borderWidth", ""+_StyleConfig.getBorderWidth());
		            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) 
			            json.put("borderStyle", ""+_StyleConfig.getBorderStyle());
		            if ( ignoreFieldSet || fieldSet.contains("borderColor")) 
			            json.put("borderColor", ""+_StyleConfig.getBorderColor());
		            if ( ignoreFieldSet || fieldSet.contains("margin")) 
			            json.put("margin", ""+_StyleConfig.getMargin());
		            if ( ignoreFieldSet || fieldSet.contains("padding")) 
			            json.put("padding", ""+_StyleConfig.getPadding());
		            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) 
			            json.put("listStyleType", ""+_StyleConfig.getListStyleType());
		            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) 
			            json.put("listStylePosition", ""+_StyleConfig.getListStylePosition());
		            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) 
			            json.put("listStyleImage", ""+_StyleConfig.getListStyleImage());
		            if ( ignoreFieldSet || fieldSet.contains("floating")) 
			            json.put("floating", ""+_StyleConfig.getFloating());
		            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) 
			            json.put("extraStyleStr", ""+_StyleConfig.getExtraStyleStr());
		            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) 
			            json.put("itemStyleStr", ""+_StyleConfig.getItemStyleStr());
		            if ( ignoreFieldSet || fieldSet.contains("link")) 
			            json.put("link", ""+_StyleConfig.getLink());
		            if ( ignoreFieldSet || fieldSet.contains("linkHover")) 
			            json.put("linkHover", ""+_StyleConfig.getLinkHover());
		            if ( ignoreFieldSet || fieldSet.contains("linkActive")) 
			            json.put("linkActive", ""+_StyleConfig.getLinkActive());
		            if ( ignoreFieldSet || fieldSet.contains("height")) 
			            json.put("height", ""+_StyleConfig.getHeight());
		            if ( ignoreFieldSet || fieldSet.contains("width")) 
			            json.put("width", ""+_StyleConfig.getWidth());
		            if ( ignoreFieldSet || fieldSet.contains("isTable")) 
			            json.put("isTable", ""+_StyleConfig.getIsTable());
		            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) 
			            json.put("borderCollapse", ""+_StyleConfig.getBorderCollapse());
		            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) 
			            json.put("borderSpacing", ""+_StyleConfig.getBorderSpacing());
		            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) 
			            json.put("trStyleIds", ""+_StyleConfig.getTrStyleIds());
		            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) 
			            json.put("tdStyleIds", ""+_StyleConfig.getTdStyleIds());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                StyleConfig _StyleConfig = list.size() >=1?(StyleConfig) list.get(0): null; 

				if ( _StyleConfig != null) {
		            top.put("id", ""+_StyleConfig.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonStyleKey = new JSONObject();
		            jsonStyleKey.put("name", "styleKey");
		            jsonStyleKey.put("value", ""+_StyleConfig.getStyleKey());
		            array.put(jsonStyleKey);
		            JSONObject jsonStyleUse = new JSONObject();
		            jsonStyleUse.put("name", "styleUse");
		            jsonStyleUse.put("value", ""+_StyleConfig.getStyleUse());
		            array.put(jsonStyleUse);
		            JSONObject jsonIsGlobal = new JSONObject();
		            jsonIsGlobal.put("name", "isGlobal");
		            jsonIsGlobal.put("value", ""+_StyleConfig.getIsGlobal());
		            array.put(jsonIsGlobal);
		            JSONObject jsonIdClass = new JSONObject();
		            jsonIdClass.put("name", "idClass");
		            jsonIdClass.put("value", ""+_StyleConfig.getIdClass());
		            array.put(jsonIdClass);
		            JSONObject jsonIsId = new JSONObject();
		            jsonIsId.put("name", "isId");
		            jsonIsId.put("value", ""+_StyleConfig.getIsId());
		            array.put(jsonIsId);
		            JSONObject jsonColor = new JSONObject();
		            jsonColor.put("name", "color");
		            jsonColor.put("value", ""+_StyleConfig.getColor());
		            array.put(jsonColor);
		            JSONObject jsonBgColor = new JSONObject();
		            jsonBgColor.put("name", "bgColor");
		            jsonBgColor.put("value", ""+_StyleConfig.getBgColor());
		            array.put(jsonBgColor);
		            JSONObject jsonBgImage = new JSONObject();
		            jsonBgImage.put("name", "bgImage");
		            jsonBgImage.put("value", ""+_StyleConfig.getBgImage());
		            array.put(jsonBgImage);
		            JSONObject jsonBgRepeat = new JSONObject();
		            jsonBgRepeat.put("name", "bgRepeat");
		            jsonBgRepeat.put("value", ""+_StyleConfig.getBgRepeat());
		            array.put(jsonBgRepeat);
		            JSONObject jsonBgAttach = new JSONObject();
		            jsonBgAttach.put("name", "bgAttach");
		            jsonBgAttach.put("value", ""+_StyleConfig.getBgAttach());
		            array.put(jsonBgAttach);
		            JSONObject jsonBgPosition = new JSONObject();
		            jsonBgPosition.put("name", "bgPosition");
		            jsonBgPosition.put("value", ""+_StyleConfig.getBgPosition());
		            array.put(jsonBgPosition);
		            JSONObject jsonTextAlign = new JSONObject();
		            jsonTextAlign.put("name", "textAlign");
		            jsonTextAlign.put("value", ""+_StyleConfig.getTextAlign());
		            array.put(jsonTextAlign);
		            JSONObject jsonFontFamily = new JSONObject();
		            jsonFontFamily.put("name", "fontFamily");
		            jsonFontFamily.put("value", ""+_StyleConfig.getFontFamily());
		            array.put(jsonFontFamily);
		            JSONObject jsonFontSize = new JSONObject();
		            jsonFontSize.put("name", "fontSize");
		            jsonFontSize.put("value", ""+_StyleConfig.getFontSize());
		            array.put(jsonFontSize);
		            JSONObject jsonFontStyle = new JSONObject();
		            jsonFontStyle.put("name", "fontStyle");
		            jsonFontStyle.put("value", ""+_StyleConfig.getFontStyle());
		            array.put(jsonFontStyle);
		            JSONObject jsonFontVariant = new JSONObject();
		            jsonFontVariant.put("name", "fontVariant");
		            jsonFontVariant.put("value", ""+_StyleConfig.getFontVariant());
		            array.put(jsonFontVariant);
		            JSONObject jsonFontWeight = new JSONObject();
		            jsonFontWeight.put("name", "fontWeight");
		            jsonFontWeight.put("value", ""+_StyleConfig.getFontWeight());
		            array.put(jsonFontWeight);
		            JSONObject jsonBorderDirection = new JSONObject();
		            jsonBorderDirection.put("name", "borderDirection");
		            jsonBorderDirection.put("value", ""+_StyleConfig.getBorderDirection());
		            array.put(jsonBorderDirection);
		            JSONObject jsonBorderWidth = new JSONObject();
		            jsonBorderWidth.put("name", "borderWidth");
		            jsonBorderWidth.put("value", ""+_StyleConfig.getBorderWidth());
		            array.put(jsonBorderWidth);
		            JSONObject jsonBorderStyle = new JSONObject();
		            jsonBorderStyle.put("name", "borderStyle");
		            jsonBorderStyle.put("value", ""+_StyleConfig.getBorderStyle());
		            array.put(jsonBorderStyle);
		            JSONObject jsonBorderColor = new JSONObject();
		            jsonBorderColor.put("name", "borderColor");
		            jsonBorderColor.put("value", ""+_StyleConfig.getBorderColor());
		            array.put(jsonBorderColor);
		            JSONObject jsonMargin = new JSONObject();
		            jsonMargin.put("name", "margin");
		            jsonMargin.put("value", ""+_StyleConfig.getMargin());
		            array.put(jsonMargin);
		            JSONObject jsonPadding = new JSONObject();
		            jsonPadding.put("name", "padding");
		            jsonPadding.put("value", ""+_StyleConfig.getPadding());
		            array.put(jsonPadding);
		            JSONObject jsonListStyleType = new JSONObject();
		            jsonListStyleType.put("name", "listStyleType");
		            jsonListStyleType.put("value", ""+_StyleConfig.getListStyleType());
		            array.put(jsonListStyleType);
		            JSONObject jsonListStylePosition = new JSONObject();
		            jsonListStylePosition.put("name", "listStylePosition");
		            jsonListStylePosition.put("value", ""+_StyleConfig.getListStylePosition());
		            array.put(jsonListStylePosition);
		            JSONObject jsonListStyleImage = new JSONObject();
		            jsonListStyleImage.put("name", "listStyleImage");
		            jsonListStyleImage.put("value", ""+_StyleConfig.getListStyleImage());
		            array.put(jsonListStyleImage);
		            JSONObject jsonFloating = new JSONObject();
		            jsonFloating.put("name", "floating");
		            jsonFloating.put("value", ""+_StyleConfig.getFloating());
		            array.put(jsonFloating);
		            JSONObject jsonExtraStyleStr = new JSONObject();
		            jsonExtraStyleStr.put("name", "extraStyleStr");
		            jsonExtraStyleStr.put("value", ""+_StyleConfig.getExtraStyleStr());
		            array.put(jsonExtraStyleStr);
		            JSONObject jsonItemStyleStr = new JSONObject();
		            jsonItemStyleStr.put("name", "itemStyleStr");
		            jsonItemStyleStr.put("value", ""+_StyleConfig.getItemStyleStr());
		            array.put(jsonItemStyleStr);
		            JSONObject jsonLink = new JSONObject();
		            jsonLink.put("name", "link");
		            jsonLink.put("value", ""+_StyleConfig.getLink());
		            array.put(jsonLink);
		            JSONObject jsonLinkHover = new JSONObject();
		            jsonLinkHover.put("name", "linkHover");
		            jsonLinkHover.put("value", ""+_StyleConfig.getLinkHover());
		            array.put(jsonLinkHover);
		            JSONObject jsonLinkActive = new JSONObject();
		            jsonLinkActive.put("name", "linkActive");
		            jsonLinkActive.put("value", ""+_StyleConfig.getLinkActive());
		            array.put(jsonLinkActive);
		            JSONObject jsonHeight = new JSONObject();
		            jsonHeight.put("name", "height");
		            jsonHeight.put("value", ""+_StyleConfig.getHeight());
		            array.put(jsonHeight);
		            JSONObject jsonWidth = new JSONObject();
		            jsonWidth.put("name", "width");
		            jsonWidth.put("value", ""+_StyleConfig.getWidth());
		            array.put(jsonWidth);
		            JSONObject jsonIsTable = new JSONObject();
		            jsonIsTable.put("name", "isTable");
		            jsonIsTable.put("value", ""+_StyleConfig.getIsTable());
		            array.put(jsonIsTable);
		            JSONObject jsonBorderCollapse = new JSONObject();
		            jsonBorderCollapse.put("name", "borderCollapse");
		            jsonBorderCollapse.put("value", ""+_StyleConfig.getBorderCollapse());
		            array.put(jsonBorderCollapse);
		            JSONObject jsonBorderSpacing = new JSONObject();
		            jsonBorderSpacing.put("name", "borderSpacing");
		            jsonBorderSpacing.put("value", ""+_StyleConfig.getBorderSpacing());
		            array.put(jsonBorderSpacing);
		            JSONObject jsonTrStyleIds = new JSONObject();
		            jsonTrStyleIds.put("name", "trStyleIds");
		            jsonTrStyleIds.put("value", ""+_StyleConfig.getTrStyleIds());
		            array.put(jsonTrStyleIds);
		            JSONObject jsonTdStyleIds = new JSONObject();
		            jsonTdStyleIds.put("name", "tdStyleIds");
		            jsonTdStyleIds.put("value", ""+_StyleConfig.getTdStyleIds());
		            array.put(jsonTdStyleIds);

	    	        top.put("fields", array);
				}
			}

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuilder buf = new StringBuilder();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);


            buf.append("<script type=\"text/javascript\">");
            //buf.append("<!--");
            buf.append("function sendForm_"+ randNum + "(){");
            buf.append("sendFormAjax('/styleConfigAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/styleConfigAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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

            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
                String value = WebUtil.display(request.getParameter("styleUse"));

                if ( forceHiddenSet.contains("styleUse")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"styleUse\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Style Use</div>");
            buf.append("<select id=\"requiredField\" name=\"styleUse\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

        	buf.append("<option value=\"0\" >Default</option>");
        	buf.append("<option value=\"1\" >Custom</option>");
        	buf.append("<option value=\"2\" >TBD</option>");
        	buf.append("<option value=\"3\" >TBD</option>");
        	buf.append("<option value=\"4\" >TBD</option>");

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
                String value = WebUtil.display(request.getParameter("isGlobal"));

                if ( forceHiddenSet.contains("isGlobal")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isGlobal\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Global</div>");
            buf.append("<INPUT NAME=\"isGlobal\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            buf.append("<INPUT NAME=\"isId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
                String value = WebUtil.display(request.getParameter("bgColor"));

                if ( forceHiddenSet.contains("bgColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Color</div>");
            buf.append("<INPUT NAME=\"bgColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
                String value = WebUtil.display(request.getParameter("bgImage"));

                if ( forceHiddenSet.contains("bgImage")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgImage\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Image</div>");
            buf.append("<INPUT NAME=\"bgImage\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
                String value = WebUtil.display(request.getParameter("bgRepeat"));

                if ( forceHiddenSet.contains("bgRepeat")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgRepeat\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Repeat</div>");
            buf.append("<INPUT NAME=\"bgRepeat\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
                String value = WebUtil.display(request.getParameter("bgAttach"));

                if ( forceHiddenSet.contains("bgAttach")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgAttach\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Attach</div>");
            buf.append("<INPUT NAME=\"bgAttach\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
                String value = WebUtil.display(request.getParameter("bgPosition"));

                if ( forceHiddenSet.contains("bgPosition")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgPosition\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Position</div>");
            buf.append("<INPUT NAME=\"bgPosition\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
                String value = WebUtil.display(request.getParameter("fontFamily"));

                if ( forceHiddenSet.contains("fontFamily")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontFamily\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Family</div>");
            buf.append("<INPUT NAME=\"fontFamily\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
                String value = WebUtil.display(request.getParameter("fontSize"));

                if ( forceHiddenSet.contains("fontSize")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontSize\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Size</div>");
            buf.append("<INPUT NAME=\"fontSize\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
                String value = WebUtil.display(request.getParameter("fontStyle"));

                if ( forceHiddenSet.contains("fontStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Style</div>");
            buf.append("<INPUT NAME=\"fontStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
                String value = WebUtil.display(request.getParameter("fontVariant"));

                if ( forceHiddenSet.contains("fontVariant")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontVariant\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Variant</div>");
            buf.append("<INPUT NAME=\"fontVariant\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
                String value = WebUtil.display(request.getParameter("fontWeight"));

                if ( forceHiddenSet.contains("fontWeight")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontWeight\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Weight</div>");
            buf.append("<INPUT NAME=\"fontWeight\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
                String value = WebUtil.display(request.getParameter("borderDirection"));

                if ( forceHiddenSet.contains("borderDirection")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderDirection\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Direction</div>");
            buf.append("<INPUT NAME=\"borderDirection\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
                String value = WebUtil.display(request.getParameter("borderWidth"));

                if ( forceHiddenSet.contains("borderWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Width</div>");
            buf.append("<INPUT NAME=\"borderWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
                String value = WebUtil.display(request.getParameter("borderStyle"));

                if ( forceHiddenSet.contains("borderStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Style</div>");
            buf.append("<INPUT NAME=\"borderStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
                String value = WebUtil.display(request.getParameter("borderColor"));

                if ( forceHiddenSet.contains("borderColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Color</div>");
            buf.append("<INPUT NAME=\"borderColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
                String value = WebUtil.display(request.getParameter("listStyleType"));

                if ( forceHiddenSet.contains("listStyleType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listStyleType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Style Type</div>");
            buf.append("<INPUT NAME=\"listStyleType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
                String value = WebUtil.display(request.getParameter("listStylePosition"));

                if ( forceHiddenSet.contains("listStylePosition")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listStylePosition\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Style Position</div>");
            buf.append("<INPUT NAME=\"listStylePosition\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
                String value = WebUtil.display(request.getParameter("listStyleImage"));

                if ( forceHiddenSet.contains("listStyleImage")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listStyleImage\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Style Image</div>");
            buf.append("<INPUT NAME=\"listStyleImage\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("floating")) {
                String value = WebUtil.display(request.getParameter("floating"));

                if ( forceHiddenSet.contains("floating")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"floating\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Floating</div>");
            buf.append("<INPUT NAME=\"floating\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
                String value = WebUtil.display(request.getParameter("extraStyleStr"));

                if ( forceHiddenSet.contains("extraStyleStr")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"extraStyleStr\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Extra Style Str</div>");
            buf.append("<INPUT NAME=\"extraStyleStr\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
                String value = WebUtil.display(request.getParameter("itemStyleStr"));

                if ( forceHiddenSet.contains("itemStyleStr")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"itemStyleStr\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Item Style Str</div>");
            buf.append("<INPUT NAME=\"itemStyleStr\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("link")) {
                String value = WebUtil.display(request.getParameter("link"));

                if ( forceHiddenSet.contains("link")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"link\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Link</div>");
            buf.append("<INPUT NAME=\"link\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
                String value = WebUtil.display(request.getParameter("linkHover"));

                if ( forceHiddenSet.contains("linkHover")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"linkHover\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Link Hover</div>");
            buf.append("<INPUT NAME=\"linkHover\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
                String value = WebUtil.display(request.getParameter("linkActive"));

                if ( forceHiddenSet.contains("linkActive")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"linkActive\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Link Active</div>");
            buf.append("<INPUT NAME=\"linkActive\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
                String value = WebUtil.display(request.getParameter("isTable"));

                if ( forceHiddenSet.contains("isTable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isTable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Table</div>");
            buf.append("<INPUT NAME=\"isTable\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
                String value = WebUtil.display(request.getParameter("borderCollapse"));

                if ( forceHiddenSet.contains("borderCollapse")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderCollapse\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Collapse</div>");
            buf.append("<INPUT NAME=\"borderCollapse\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
                String value = WebUtil.display(request.getParameter("borderSpacing"));

                if ( forceHiddenSet.contains("borderSpacing")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderSpacing\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Spacing</div>");
            buf.append("<INPUT NAME=\"borderSpacing\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
                String value = WebUtil.display(request.getParameter("trStyleIds"));

                if ( forceHiddenSet.contains("trStyleIds")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"trStyleIds\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tr Style Ids</div>");
            buf.append("<INPUT NAME=\"trStyleIds\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
                String value = WebUtil.display(request.getParameter("tdStyleIds"));

                if ( forceHiddenSet.contains("tdStyleIds")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tdStyleIds\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Td Style Ids</div>");
            buf.append("<INPUT NAME=\"tdStyleIds\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            buf.append("<span id=\"ajaxSubmitResult"+randNum+"\"></span>");
            buf.append("<a id=\"ajaxSubmit"+randNum+"\" href=\"javascript:sendForm_"+ randNum + "();\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))){

			//This form is called by script such as e.g. <script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>
			// inline_script will be attached to provide functionlities. 
			// This form will be used inside the same site to provide embedded page using <script> tags. But Refer to Poll "inline_script_poll" to 
			// send no-ajax submission. General no-ajax submission is not yet supported. 

            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
	        StringBuilder buf = new StringBuilder();
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
            importedScripts += "function responseCallback_styleConfig(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayStyleConfig\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_styleConfig(){\n";
            importedScripts +=     "xmlhttpPostXX('styleConfigFormAddDis','/styleConfigAction.html', 'resultDisplayStyleConfig', '${ajax_response_fields}', responseCallback_styleConfig);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_styleConfig(){\n";
            importedScripts +=     "clearFormXX('styleConfigFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_styleConfig(){\n";
            importedScripts +=     "backToXX('styleConfigFormAddDis','resultDisplayStyleConfig');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"styleConfigFormAddDis\" method=\"POST\" action=\"/styleConfigAction.html\" id=\"styleConfigFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Style Key</div>");
        buf.append("<input class=\"field\" id=\"styleKey\" type=\"text\" size=\"70\" name=\"styleKey\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Style Use</div>");
        buf.append("<select class=\"field\" name=\"styleUse\" id=\"styleUse\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("<option value=\"0\" >Default</option>");
        buf.append("<option value=\"1\" >Custom</option>");
        buf.append("<option value=\"2\" >TBD</option>");
        buf.append("<option value=\"3\" >TBD</option>");
        buf.append("<option value=\"4\" >TBD</option>");
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Global</div>");
        buf.append("<input class=\"field\" id=\"isGlobal\" type=\"text\" size=\"70\" name=\"isGlobal\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Id Class</div>");
        buf.append("<input class=\"field\" id=\"idClass\" type=\"text\" size=\"70\" name=\"idClass\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Id</div>");
        buf.append("<input class=\"field\" id=\"isId\" type=\"text\" size=\"70\" name=\"isId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Color</div>");
        buf.append("<input class=\"field\" id=\"color\" type=\"text\" size=\"70\" name=\"color\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Color</div>");
        buf.append("<input class=\"field\" id=\"bgColor\" type=\"text\" size=\"70\" name=\"bgColor\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Image</div>");
        buf.append("<input class=\"field\" id=\"bgImage\" type=\"text\" size=\"70\" name=\"bgImage\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Repeat</div>");
        buf.append("<input class=\"field\" id=\"bgRepeat\" type=\"text\" size=\"70\" name=\"bgRepeat\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Attach</div>");
        buf.append("<input class=\"field\" id=\"bgAttach\" type=\"text\" size=\"70\" name=\"bgAttach\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Position</div>");
        buf.append("<input class=\"field\" id=\"bgPosition\" type=\"text\" size=\"70\" name=\"bgPosition\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Text Align</div>");
        buf.append("<input class=\"field\" id=\"textAlign\" type=\"text\" size=\"70\" name=\"textAlign\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Family</div>");
        buf.append("<input class=\"field\" id=\"fontFamily\" type=\"text\" size=\"70\" name=\"fontFamily\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Size</div>");
        buf.append("<input class=\"field\" id=\"fontSize\" type=\"text\" size=\"70\" name=\"fontSize\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Style</div>");
        buf.append("<input class=\"field\" id=\"fontStyle\" type=\"text\" size=\"70\" name=\"fontStyle\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Variant</div>");
        buf.append("<input class=\"field\" id=\"fontVariant\" type=\"text\" size=\"70\" name=\"fontVariant\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Weight</div>");
        buf.append("<input class=\"field\" id=\"fontWeight\" type=\"text\" size=\"70\" name=\"fontWeight\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Direction</div>");
        buf.append("<input class=\"field\" id=\"borderDirection\" type=\"text\" size=\"70\" name=\"borderDirection\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Width</div>");
        buf.append("<input class=\"field\" id=\"borderWidth\" type=\"text\" size=\"70\" name=\"borderWidth\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Style</div>");
        buf.append("<input class=\"field\" id=\"borderStyle\" type=\"text\" size=\"70\" name=\"borderStyle\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Color</div>");
        buf.append("<input class=\"field\" id=\"borderColor\" type=\"text\" size=\"70\" name=\"borderColor\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Margin</div>");
        buf.append("<input class=\"field\" id=\"margin\" type=\"text\" size=\"70\" name=\"margin\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Padding</div>");
        buf.append("<input class=\"field\" id=\"padding\" type=\"text\" size=\"70\" name=\"padding\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Style Type</div>");
        buf.append("<input class=\"field\" id=\"listStyleType\" type=\"text\" size=\"70\" name=\"listStyleType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Style Position</div>");
        buf.append("<input class=\"field\" id=\"listStylePosition\" type=\"text\" size=\"70\" name=\"listStylePosition\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Style Image</div>");
        buf.append("<input class=\"field\" id=\"listStyleImage\" type=\"text\" size=\"70\" name=\"listStyleImage\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Floating</div>");
        buf.append("<input class=\"field\" id=\"floating\" type=\"text\" size=\"70\" name=\"floating\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Extra Style Str</div>");
        buf.append("<input class=\"field\" id=\"extraStyleStr\" type=\"text\" size=\"70\" name=\"extraStyleStr\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Item Style Str</div>");
        buf.append("<input class=\"field\" id=\"itemStyleStr\" type=\"text\" size=\"70\" name=\"itemStyleStr\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Link</div>");
        buf.append("<input class=\"field\" id=\"link\" type=\"text\" size=\"70\" name=\"link\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Link Hover</div>");
        buf.append("<input class=\"field\" id=\"linkHover\" type=\"text\" size=\"70\" name=\"linkHover\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Link Active</div>");
        buf.append("<input class=\"field\" id=\"linkActive\" type=\"text\" size=\"70\" name=\"linkActive\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Height</div>");
        buf.append("<input class=\"field\" id=\"height\" type=\"text\" size=\"70\" name=\"height\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Width</div>");
        buf.append("<input class=\"field\" id=\"width\" type=\"text\" size=\"70\" name=\"width\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Table</div>");
        buf.append("<input class=\"field\" id=\"isTable\" type=\"text\" size=\"70\" name=\"isTable\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Collapse</div>");
        buf.append("<input class=\"field\" id=\"borderCollapse\" type=\"text\" size=\"70\" name=\"borderCollapse\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Spacing</div>");
        buf.append("<input class=\"field\" id=\"borderSpacing\" type=\"text\" size=\"70\" name=\"borderSpacing\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Tr Style Ids</div>");
        buf.append("<input class=\"field\" id=\"trStyleIds\" type=\"text\" size=\"70\" name=\"trStyleIds\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Td Style Ids</div>");
        buf.append("<input class=\"field\" id=\"tdStyleIds\" type=\"text\" size=\"70\" name=\"tdStyleIds\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_styleConfig()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_styleConfig()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayStyleConfig\"></span>");
			buf.append("<a href=\"javascript:showform_styleConfig()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());
            
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
    protected List prepareReturnData(HttpServletRequest request, StyleConfig target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            StyleConfig _StyleConfig = null; 
            List list = StyleConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _StyleConfig = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _StyleConfig = (StyleConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _StyleConfig = (StyleConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _StyleConfig = StyleConfigDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_StyleConfig);

        } else {
            
            List list = StyleConfigDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }
*/


    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        StyleConfigForm _StyleConfigForm = (StyleConfigForm) form;

		if(requestParams.containsKey("styleKey"))
			_StyleConfigForm.setStyleKey((String)requestParams.get("styleKey"));
		if(requestParams.containsKey("styleUse"))
			_StyleConfigForm.setStyleUse((String)requestParams.get("styleUse"));
		if(requestParams.containsKey("isGlobal"))
			_StyleConfigForm.setIsGlobal((String)requestParams.get("isGlobal"));
		if(requestParams.containsKey("idClass"))
			_StyleConfigForm.setIdClass((String)requestParams.get("idClass"));
		if(requestParams.containsKey("isId"))
			_StyleConfigForm.setIsId((String)requestParams.get("isId"));
		if(requestParams.containsKey("color"))
			_StyleConfigForm.setColor((String)requestParams.get("color"));
		if(requestParams.containsKey("bgColor"))
			_StyleConfigForm.setBgColor((String)requestParams.get("bgColor"));
		if(requestParams.containsKey("bgImage"))
			_StyleConfigForm.setBgImage((String)requestParams.get("bgImage"));
		if(requestParams.containsKey("bgRepeat"))
			_StyleConfigForm.setBgRepeat((String)requestParams.get("bgRepeat"));
		if(requestParams.containsKey("bgAttach"))
			_StyleConfigForm.setBgAttach((String)requestParams.get("bgAttach"));
		if(requestParams.containsKey("bgPosition"))
			_StyleConfigForm.setBgPosition((String)requestParams.get("bgPosition"));
		if(requestParams.containsKey("textAlign"))
			_StyleConfigForm.setTextAlign((String)requestParams.get("textAlign"));
		if(requestParams.containsKey("fontFamily"))
			_StyleConfigForm.setFontFamily((String)requestParams.get("fontFamily"));
		if(requestParams.containsKey("fontSize"))
			_StyleConfigForm.setFontSize((String)requestParams.get("fontSize"));
		if(requestParams.containsKey("fontStyle"))
			_StyleConfigForm.setFontStyle((String)requestParams.get("fontStyle"));
		if(requestParams.containsKey("fontVariant"))
			_StyleConfigForm.setFontVariant((String)requestParams.get("fontVariant"));
		if(requestParams.containsKey("fontWeight"))
			_StyleConfigForm.setFontWeight((String)requestParams.get("fontWeight"));
		if(requestParams.containsKey("borderDirection"))
			_StyleConfigForm.setBorderDirection((String)requestParams.get("borderDirection"));
		if(requestParams.containsKey("borderWidth"))
			_StyleConfigForm.setBorderWidth((String)requestParams.get("borderWidth"));
		if(requestParams.containsKey("borderStyle"))
			_StyleConfigForm.setBorderStyle((String)requestParams.get("borderStyle"));
		if(requestParams.containsKey("borderColor"))
			_StyleConfigForm.setBorderColor((String)requestParams.get("borderColor"));
		if(requestParams.containsKey("margin"))
			_StyleConfigForm.setMargin((String)requestParams.get("margin"));
		if(requestParams.containsKey("padding"))
			_StyleConfigForm.setPadding((String)requestParams.get("padding"));
		if(requestParams.containsKey("listStyleType"))
			_StyleConfigForm.setListStyleType((String)requestParams.get("listStyleType"));
		if(requestParams.containsKey("listStylePosition"))
			_StyleConfigForm.setListStylePosition((String)requestParams.get("listStylePosition"));
		if(requestParams.containsKey("listStyleImage"))
			_StyleConfigForm.setListStyleImage((String)requestParams.get("listStyleImage"));
		if(requestParams.containsKey("floating"))
			_StyleConfigForm.setFloating((String)requestParams.get("floating"));
		if(requestParams.containsKey("extraStyleStr"))
			_StyleConfigForm.setExtraStyleStr((String)requestParams.get("extraStyleStr"));
		if(requestParams.containsKey("itemStyleStr"))
			_StyleConfigForm.setItemStyleStr((String)requestParams.get("itemStyleStr"));
		if(requestParams.containsKey("link"))
			_StyleConfigForm.setLink((String)requestParams.get("link"));
		if(requestParams.containsKey("linkHover"))
			_StyleConfigForm.setLinkHover((String)requestParams.get("linkHover"));
		if(requestParams.containsKey("linkActive"))
			_StyleConfigForm.setLinkActive((String)requestParams.get("linkActive"));
		if(requestParams.containsKey("height"))
			_StyleConfigForm.setHeight((String)requestParams.get("height"));
		if(requestParams.containsKey("width"))
			_StyleConfigForm.setWidth((String)requestParams.get("width"));
		if(requestParams.containsKey("isTable"))
			_StyleConfigForm.setIsTable((String)requestParams.get("isTable"));
		if(requestParams.containsKey("borderCollapse"))
			_StyleConfigForm.setBorderCollapse((String)requestParams.get("borderCollapse"));
		if(requestParams.containsKey("borderSpacing"))
			_StyleConfigForm.setBorderSpacing((String)requestParams.get("borderSpacing"));
		if(requestParams.containsKey("trStyleIds"))
			_StyleConfigForm.setTrStyleIds((String)requestParams.get("trStyleIds"));
		if(requestParams.containsKey("tdStyleIds"))
			_StyleConfigForm.setTdStyleIds((String)requestParams.get("tdStyleIds"));
		if(requestParams.containsKey("timeCreated"))
			_StyleConfigForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_StyleConfigForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return true;
    }

	//	// Configuration Option
	//
    protected String getErrorPage(){return m_actionExtent.getErrorPage();}
    protected String getWarningPage(){return m_actionExtent.getWarningPage();}
    protected String getAfterAddPage(){return m_actionExtent.getAfterAddPage();}
    protected String getAfterEditPage(){return m_actionExtent.getAfterEditPage();}
    protected String getAfterEditFieldPage(){return m_actionExtent.getAfterEditFieldPage();}
    protected String getAfterDeletePage(){return m_actionExtent.getAfterDeletePage();}
    protected String getDefaultPage(){return m_actionExtent.getDefaultPage();}
    protected String getConfirmPage(){return m_actionExtent.getConfirmPage();}

    public String getActionGroupName(){ return "ChurApp";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected StyleConfigDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
