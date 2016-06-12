package com.autosite.struts.action;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PollStyle;
import com.autosite.ds.PollStyleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PollStyleForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PollStyleAction extends AutositeCoreAction {

    public PollStyleAction(){
        m_ds = PollStyleDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PollStyleForm _PollStyleForm = (PollStyleForm) form;
        HttpSession session = request.getSession();

        setPage(session, "poll_style_home");

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
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        PollStyle _PollStyle = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PollStyle = m_ds.getById(cid);
		}

		if (autoUser == null) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.warn("User object not set. ");
            return mapping.findForward("default");
        }

        if (!hasRequestValue(request, "add", "true")) {

            if ( autoUser.getUserType() != Constants.UserSuperAdmin && autoUser.getId() != _PollStyle.getUserId() ) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Access Exception. Delete failed. Invalid user has attempted to delete for user " + _PollStyle.getUserId());
                return mapping.findForward("default");
            }
		}

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollStyle _PollStyle = m_ds.getById(cid);

            if (_PollStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PollStyleForm, _PollStyle);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollStyle _PollStyle = m_ds.getById(cid);

            if (_PollStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PollStyle);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollStyle _PollStyle = m_ds.getById(cid);

            if (_PollStyle == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollStyle.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollStyle.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PollStyle);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PollStyle);
            try { 
                m_actionExtent.afterDelete(request, response, _PollStyle);
            }
            catch (Exception e) {
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
                return mapping.findForward("default");
            }

            m_logger.info("Creating new PollStyle" );
            PollStyle _PollStyleNew = new PollStyle();   

            // Setting IDs for the object
            _PollStyleNew.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _PollStyleNew.setUserId(user.getId());
            }

            _PollStyleNew.setUserId(WebParamUtil.getLongValue(_PollStyleForm.getUserId()));
            m_logger.debug("setting UserId=" +_PollStyleForm.getUserId());
            _PollStyleNew.setPollId(WebParamUtil.getLongValue(_PollStyleForm.getPollId()));
            m_logger.debug("setting PollId=" +_PollStyleForm.getPollId());
            _PollStyleNew.setColor(WebParamUtil.getStringValue(_PollStyleForm.getColor()));
            m_logger.debug("setting Color=" +_PollStyleForm.getColor());
            _PollStyleNew.setBackground(WebParamUtil.getStringValue(_PollStyleForm.getBackground()));
            m_logger.debug("setting Background=" +_PollStyleForm.getBackground());
            _PollStyleNew.setBorder(WebParamUtil.getStringValue(_PollStyleForm.getBorder()));
            m_logger.debug("setting Border=" +_PollStyleForm.getBorder());
            _PollStyleNew.setFont(WebParamUtil.getStringValue(_PollStyleForm.getFont()));
            m_logger.debug("setting Font=" +_PollStyleForm.getFont());
            _PollStyleNew.setMargin(WebParamUtil.getStringValue(_PollStyleForm.getMargin()));
            m_logger.debug("setting Margin=" +_PollStyleForm.getMargin());
            _PollStyleNew.setPadding(WebParamUtil.getStringValue(_PollStyleForm.getPadding()));
            m_logger.debug("setting Padding=" +_PollStyleForm.getPadding());
            _PollStyleNew.setFloating(WebParamUtil.getStringValue(_PollStyleForm.getFloating()));
            m_logger.debug("setting Floating=" +_PollStyleForm.getFloating());
            _PollStyleNew.setTextAlign(WebParamUtil.getStringValue(_PollStyleForm.getTextAlign()));
            m_logger.debug("setting TextAlign=" +_PollStyleForm.getTextAlign());
            _PollStyleNew.setTextIndent(WebParamUtil.getStringValue(_PollStyleForm.getTextIndent()));
            m_logger.debug("setting TextIndent=" +_PollStyleForm.getTextIndent());
            _PollStyleNew.setHeight(WebParamUtil.getStringValue(_PollStyleForm.getHeight()));
            m_logger.debug("setting Height=" +_PollStyleForm.getHeight());
            _PollStyleNew.setWidth(WebParamUtil.getStringValue(_PollStyleForm.getWidth()));
            m_logger.debug("setting Width=" +_PollStyleForm.getWidth());
            _PollStyleNew.setExtra(WebParamUtil.getStringValue(_PollStyleForm.getExtra()));
            m_logger.debug("setting Extra=" +_PollStyleForm.getExtra());
            _PollStyleNew.setTimeCreated(WebParamUtil.getDateValue(_PollStyleForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PollStyleForm.getTimeCreated());
            _PollStyleNew.setTimeUpdated(WebParamUtil.getDateValue(_PollStyleForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PollStyleForm.getTimeUpdated());

			// Checking the uniqueness by configuration. 
			if ( m_ds.getBySiteIdPollId(_PollStyleNew.getSiteId(), _PollStyleNew.getPollId()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _PollStyleNew.getPollId());
                return mapping.findForward("default");
            }
            
            try{
                m_actionExtent.beforeAdd(request, response, _PollStyleNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_PollStyleNew);
            try{
                m_actionExtent.afterAdd(request, response, _PollStyleNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "poll_style_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PollStyleForm _PollStyleForm, PollStyle _PollStyle) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollStyle _PollStyle = m_ds.getById(cid);

        m_logger.debug("Before update " + PollStyleDS.objectToString(_PollStyle));

        _PollStyle.setUserId(WebParamUtil.getLongValue(_PollStyleForm.getUserId()));
        _PollStyle.setPollId(WebParamUtil.getLongValue(_PollStyleForm.getPollId()));
        _PollStyle.setColor(WebParamUtil.getStringValue(_PollStyleForm.getColor()));
        _PollStyle.setBackground(WebParamUtil.getStringValue(_PollStyleForm.getBackground()));
        _PollStyle.setBorder(WebParamUtil.getStringValue(_PollStyleForm.getBorder()));
        _PollStyle.setFont(WebParamUtil.getStringValue(_PollStyleForm.getFont()));
        _PollStyle.setMargin(WebParamUtil.getStringValue(_PollStyleForm.getMargin()));
        _PollStyle.setPadding(WebParamUtil.getStringValue(_PollStyleForm.getPadding()));
        _PollStyle.setFloating(WebParamUtil.getStringValue(_PollStyleForm.getFloating()));
        _PollStyle.setTextAlign(WebParamUtil.getStringValue(_PollStyleForm.getTextAlign()));
        _PollStyle.setTextIndent(WebParamUtil.getStringValue(_PollStyleForm.getTextIndent()));
        _PollStyle.setHeight(WebParamUtil.getStringValue(_PollStyleForm.getHeight()));
        _PollStyle.setWidth(WebParamUtil.getStringValue(_PollStyleForm.getWidth()));
        _PollStyle.setExtra(WebParamUtil.getStringValue(_PollStyleForm.getExtra()));
        _PollStyle.setTimeCreated(WebParamUtil.getDateValue(_PollStyleForm.getTimeCreated()));
        _PollStyle.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _PollStyle);
        m_ds.update(_PollStyle);
        m_actionExtent.afterUpdate(request, response, _PollStyle);
        m_logger.debug("After update " + PollStyleDS.objectToString(_PollStyle));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PollStyle _PollStyle) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollStyle _PollStyle = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_PollStyle.getUserId() + "->" + request.getParameter("userId"));
            _PollStyle.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("pollId"))) {
            m_logger.debug("updating param pollId from " +_PollStyle.getPollId() + "->" + request.getParameter("pollId"));
            _PollStyle.setPollId(WebParamUtil.getLongValue(request.getParameter("pollId")));
        }
        if (!isMissing(request.getParameter("color"))) {
            m_logger.debug("updating param color from " +_PollStyle.getColor() + "->" + request.getParameter("color"));
            _PollStyle.setColor(WebParamUtil.getStringValue(request.getParameter("color")));
        }
        if (!isMissing(request.getParameter("background"))) {
            m_logger.debug("updating param background from " +_PollStyle.getBackground() + "->" + request.getParameter("background"));
            _PollStyle.setBackground(WebParamUtil.getStringValue(request.getParameter("background")));
        }
        if (!isMissing(request.getParameter("border"))) {
            m_logger.debug("updating param border from " +_PollStyle.getBorder() + "->" + request.getParameter("border"));
            _PollStyle.setBorder(WebParamUtil.getStringValue(request.getParameter("border")));
        }
        if (!isMissing(request.getParameter("font"))) {
            m_logger.debug("updating param font from " +_PollStyle.getFont() + "->" + request.getParameter("font"));
            _PollStyle.setFont(WebParamUtil.getStringValue(request.getParameter("font")));
        }
        if (!isMissing(request.getParameter("margin"))) {
            m_logger.debug("updating param margin from " +_PollStyle.getMargin() + "->" + request.getParameter("margin"));
            _PollStyle.setMargin(WebParamUtil.getStringValue(request.getParameter("margin")));
        }
        if (!isMissing(request.getParameter("padding"))) {
            m_logger.debug("updating param padding from " +_PollStyle.getPadding() + "->" + request.getParameter("padding"));
            _PollStyle.setPadding(WebParamUtil.getStringValue(request.getParameter("padding")));
        }
        if (!isMissing(request.getParameter("floating"))) {
            m_logger.debug("updating param floating from " +_PollStyle.getFloating() + "->" + request.getParameter("floating"));
            _PollStyle.setFloating(WebParamUtil.getStringValue(request.getParameter("floating")));
        }
        if (!isMissing(request.getParameter("textAlign"))) {
            m_logger.debug("updating param textAlign from " +_PollStyle.getTextAlign() + "->" + request.getParameter("textAlign"));
            _PollStyle.setTextAlign(WebParamUtil.getStringValue(request.getParameter("textAlign")));
        }
        if (!isMissing(request.getParameter("textIndent"))) {
            m_logger.debug("updating param textIndent from " +_PollStyle.getTextIndent() + "->" + request.getParameter("textIndent"));
            _PollStyle.setTextIndent(WebParamUtil.getStringValue(request.getParameter("textIndent")));
        }
        if (!isMissing(request.getParameter("height"))) {
            m_logger.debug("updating param height from " +_PollStyle.getHeight() + "->" + request.getParameter("height"));
            _PollStyle.setHeight(WebParamUtil.getStringValue(request.getParameter("height")));
        }
        if (!isMissing(request.getParameter("width"))) {
            m_logger.debug("updating param width from " +_PollStyle.getWidth() + "->" + request.getParameter("width"));
            _PollStyle.setWidth(WebParamUtil.getStringValue(request.getParameter("width")));
        }
        if (!isMissing(request.getParameter("extra"))) {
            m_logger.debug("updating param extra from " +_PollStyle.getExtra() + "->" + request.getParameter("extra"));
            _PollStyle.setExtra(WebParamUtil.getStringValue(request.getParameter("extra")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PollStyle.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PollStyle.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PollStyle.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PollStyle.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PollStyle);
        m_ds.update(_PollStyle);
        m_actionExtent.afterUpdate(request, response, _PollStyle);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PollStyle _PollStyle) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollStyle _PollStyle = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
			return String.valueOf(_PollStyle.getUserId());
        }
        if (!isMissing(request.getParameter("pollId"))) {
			return String.valueOf(_PollStyle.getPollId());
        }
        if (!isMissing(request.getParameter("color"))) {
			return String.valueOf(_PollStyle.getColor());
        }
        if (!isMissing(request.getParameter("background"))) {
			return String.valueOf(_PollStyle.getBackground());
        }
        if (!isMissing(request.getParameter("border"))) {
			return String.valueOf(_PollStyle.getBorder());
        }
        if (!isMissing(request.getParameter("font"))) {
			return String.valueOf(_PollStyle.getFont());
        }
        if (!isMissing(request.getParameter("margin"))) {
			return String.valueOf(_PollStyle.getMargin());
        }
        if (!isMissing(request.getParameter("padding"))) {
			return String.valueOf(_PollStyle.getPadding());
        }
        if (!isMissing(request.getParameter("floating"))) {
			return String.valueOf(_PollStyle.getFloating());
        }
        if (!isMissing(request.getParameter("textAlign"))) {
			return String.valueOf(_PollStyle.getTextAlign());
        }
        if (!isMissing(request.getParameter("textIndent"))) {
			return String.valueOf(_PollStyle.getTextIndent());
        }
        if (!isMissing(request.getParameter("height"))) {
			return String.valueOf(_PollStyle.getHeight());
        }
        if (!isMissing(request.getParameter("width"))) {
			return String.valueOf(_PollStyle.getWidth());
        }
        if (!isMissing(request.getParameter("extra"))) {
			return String.valueOf(_PollStyle.getExtra());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_PollStyle.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_PollStyle.getTimeUpdated());
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
                ex(mapping, form, request, response);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            PollStyle _PollStyle = PollStyleDS.getInstance().getById(id);
            if (_PollStyle == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PollStyle);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else{
            
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected PollStyleDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollStyleAction.class);
}
