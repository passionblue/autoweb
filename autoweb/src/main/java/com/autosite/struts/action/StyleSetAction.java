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

import com.autosite.db.StyleSet;
import com.autosite.ds.StyleSetDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.StyleSetForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class StyleSetAction extends AutositeCoreAction {

    public StyleSetAction(){
        m_ds = StyleSetDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        StyleSetForm _StyleSetForm = (StyleSetForm) form;
        HttpSession session = request.getSession();

        setPage(session, "style_set_home");

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
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        StyleSet _StyleSet = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _StyleSet = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //StyleSet _StyleSet = m_ds.getById(cid);

            if (_StyleSet == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleSet.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleSet.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _StyleSetForm, _StyleSet);
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
            //StyleSet _StyleSet = m_ds.getById(cid);

            if (_StyleSet == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleSet.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleSet.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _StyleSet);
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
            //StyleSet _StyleSet = m_ds.getById(cid);

            if (_StyleSet == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_StyleSet.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _StyleSet.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _StyleSet);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_StyleSet);
            try { 
                m_actionExtent.afterDelete(request, response, _StyleSet);
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

            m_logger.info("Creating new StyleSet" );
            StyleSet _StyleSetNew = new StyleSet();   

            // Setting IDs for the object
            _StyleSetNew.setSiteId(site.getId());

            _StyleSetNew.setName(WebParamUtil.getStringValue(_StyleSetForm.getName()));
            m_logger.debug("setting Name=" +_StyleSetForm.getName());
            _StyleSetNew.setStyleId(WebParamUtil.getLongValue(_StyleSetForm.getStyleId()));
            m_logger.debug("setting StyleId=" +_StyleSetForm.getStyleId());
            _StyleSetNew.setDataStyleId(WebParamUtil.getLongValue(_StyleSetForm.getDataStyleId()));
            m_logger.debug("setting DataStyleId=" +_StyleSetForm.getDataStyleId());
            _StyleSetNew.setLinkStyleId(WebParamUtil.getLongValue(_StyleSetForm.getLinkStyleId()));
            m_logger.debug("setting LinkStyleId=" +_StyleSetForm.getLinkStyleId());
            _StyleSetNew.setTimeCreated(WebParamUtil.getDateValue(_StyleSetForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_StyleSetForm.getTimeCreated());
            _StyleSetNew.setTimeUpdated(WebParamUtil.getDateValue(_StyleSetForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_StyleSetForm.getTimeUpdated());

            try{
                m_actionExtent.beforeAdd(request, response, _StyleSetNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_StyleSetNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_StyleSetNew);
            try{
                m_actionExtent.afterAdd(request, response, _StyleSetNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "style_set_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, StyleSetForm _StyleSetForm, StyleSet _StyleSet) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleSet _StyleSet = m_ds.getById(cid);

        m_logger.debug("Before update " + StyleSetDS.objectToString(_StyleSet));

        _StyleSet.setName(WebParamUtil.getStringValue(_StyleSetForm.getName()));
        _StyleSet.setStyleId(WebParamUtil.getLongValue(_StyleSetForm.getStyleId()));
        _StyleSet.setDataStyleId(WebParamUtil.getLongValue(_StyleSetForm.getDataStyleId()));
        _StyleSet.setLinkStyleId(WebParamUtil.getLongValue(_StyleSetForm.getLinkStyleId()));
        _StyleSet.setTimeCreated(WebParamUtil.getDateValue(_StyleSetForm.getTimeCreated()));
        _StyleSet.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _StyleSet);
        m_ds.update(_StyleSet);
        m_actionExtent.afterUpdate(request, response, _StyleSet);
        m_logger.debug("After update " + StyleSetDS.objectToString(_StyleSet));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, StyleSet _StyleSet) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleSet _StyleSet = m_ds.getById(cid);

        if (!isMissing(request.getParameter("name"))) {
            m_logger.debug("updating param name from " +_StyleSet.getName() + "->" + request.getParameter("name"));
            _StyleSet.setName(WebParamUtil.getStringValue(request.getParameter("name")));
        }
        if (!isMissing(request.getParameter("styleId"))) {
            m_logger.debug("updating param styleId from " +_StyleSet.getStyleId() + "->" + request.getParameter("styleId"));
            _StyleSet.setStyleId(WebParamUtil.getLongValue(request.getParameter("styleId")));
        }
        if (!isMissing(request.getParameter("dataStyleId"))) {
            m_logger.debug("updating param dataStyleId from " +_StyleSet.getDataStyleId() + "->" + request.getParameter("dataStyleId"));
            _StyleSet.setDataStyleId(WebParamUtil.getLongValue(request.getParameter("dataStyleId")));
        }
        if (!isMissing(request.getParameter("linkStyleId"))) {
            m_logger.debug("updating param linkStyleId from " +_StyleSet.getLinkStyleId() + "->" + request.getParameter("linkStyleId"));
            _StyleSet.setLinkStyleId(WebParamUtil.getLongValue(request.getParameter("linkStyleId")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_StyleSet.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _StyleSet.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_StyleSet.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _StyleSet.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _StyleSet);
        m_ds.update(_StyleSet);
        m_actionExtent.afterUpdate(request, response, _StyleSet);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, StyleSet _StyleSet) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        StyleSet _StyleSet = m_ds.getById(cid);

        if (!isMissing(request.getParameter("name"))) {
			return String.valueOf(_StyleSet.getName());
        }
        if (!isMissing(request.getParameter("styleId"))) {
			return String.valueOf(_StyleSet.getStyleId());
        }
        if (!isMissing(request.getParameter("dataStyleId"))) {
			return String.valueOf(_StyleSet.getDataStyleId());
        }
        if (!isMissing(request.getParameter("linkStyleId"))) {
			return String.valueOf(_StyleSet.getLinkStyleId());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_StyleSet.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_StyleSet.getTimeUpdated());
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
            StyleSet _StyleSet = StyleSetDS.getInstance().getById(id);
            if (_StyleSet == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _StyleSet);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected StyleSetDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( StyleSetAction.class);
}
