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

import com.autosite.db.MetaHeader;
import com.autosite.ds.MetaHeaderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.MetaHeaderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class MetaHeaderAction extends AutositeCoreAction {

    public MetaHeaderAction(){
        m_ds = MetaHeaderDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        MetaHeaderForm _MetaHeaderForm = (MetaHeaderForm) form;
        HttpSession session = request.getSession();

        setPage(session, "meta_header_home");

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


        MetaHeader _MetaHeader = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _MetaHeader = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //MetaHeader _MetaHeader = m_ds.getById(cid);

            if (_MetaHeader == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_MetaHeader.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _MetaHeader.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _MetaHeaderForm, _MetaHeader);
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
            //MetaHeader _MetaHeader = m_ds.getById(cid);

            if (_MetaHeader == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_MetaHeader.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _MetaHeader.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _MetaHeader);
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
            //MetaHeader _MetaHeader = m_ds.getById(cid);

            if (_MetaHeader == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_MetaHeader.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _MetaHeader.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _MetaHeader);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_MetaHeader);
            try { 
                m_actionExtent.afterDelete(request, response, _MetaHeader);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new MetaHeader" );
            MetaHeader _MetaHeaderNew = new MetaHeader();   

            // Setting IDs for the object
            _MetaHeaderNew.setSiteId(site.getId());

            _MetaHeaderNew.setSource(WebParamUtil.getStringValue(_MetaHeaderForm.getSource()));
            m_logger.debug("setting Source=" +_MetaHeaderForm.getSource());
            _MetaHeaderNew.setDetailId(WebParamUtil.getLongValue(_MetaHeaderForm.getDetailId()));
            m_logger.debug("setting DetailId=" +_MetaHeaderForm.getDetailId());
            _MetaHeaderNew.setName(WebParamUtil.getStringValue(_MetaHeaderForm.getName()));
            m_logger.debug("setting Name=" +_MetaHeaderForm.getName());
            _MetaHeaderNew.setValue(WebParamUtil.getStringValue(_MetaHeaderForm.getValue()));
            m_logger.debug("setting Value=" +_MetaHeaderForm.getValue());
            _MetaHeaderNew.setHttpEquiv(WebParamUtil.getIntValue(_MetaHeaderForm.getHttpEquiv()));
            m_logger.debug("setting HttpEquiv=" +_MetaHeaderForm.getHttpEquiv());
            _MetaHeaderNew.setTimeCreated(WebParamUtil.getDateValue(_MetaHeaderForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_MetaHeaderForm.getTimeCreated());

            try{
                m_actionExtent.beforeAdd(request, response, _MetaHeaderNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_MetaHeaderNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_MetaHeaderNew);
            try{
                m_actionExtent.afterAdd(request, response, _MetaHeaderNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "meta_header_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, MetaHeaderForm _MetaHeaderForm, MetaHeader _MetaHeader) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        MetaHeader _MetaHeader = m_ds.getById(cid);

        m_logger.debug("Before update " + MetaHeaderDS.objectToString(_MetaHeader));

        _MetaHeader.setSource(WebParamUtil.getStringValue(_MetaHeaderForm.getSource()));
        _MetaHeader.setDetailId(WebParamUtil.getLongValue(_MetaHeaderForm.getDetailId()));
        _MetaHeader.setName(WebParamUtil.getStringValue(_MetaHeaderForm.getName()));
        _MetaHeader.setValue(WebParamUtil.getStringValue(_MetaHeaderForm.getValue()));
        _MetaHeader.setHttpEquiv(WebParamUtil.getIntValue(_MetaHeaderForm.getHttpEquiv()));
        _MetaHeader.setTimeCreated(WebParamUtil.getDateValue(_MetaHeaderForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _MetaHeader);
        m_ds.update(_MetaHeader);
        m_actionExtent.afterUpdate(request, response, _MetaHeader);
        m_logger.debug("After update " + MetaHeaderDS.objectToString(_MetaHeader));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, MetaHeader _MetaHeader) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        MetaHeader _MetaHeader = m_ds.getById(cid);

        if (!isMissing(request.getParameter("source"))) {
            m_logger.debug("updating param source from " +_MetaHeader.getSource() + "->" + request.getParameter("source"));
            _MetaHeader.setSource(WebParamUtil.getStringValue(request.getParameter("source")));
        }
        if (!isMissing(request.getParameter("detailId"))) {
            m_logger.debug("updating param detailId from " +_MetaHeader.getDetailId() + "->" + request.getParameter("detailId"));
            _MetaHeader.setDetailId(WebParamUtil.getLongValue(request.getParameter("detailId")));
        }
        if (!isMissing(request.getParameter("name"))) {
            m_logger.debug("updating param name from " +_MetaHeader.getName() + "->" + request.getParameter("name"));
            _MetaHeader.setName(WebParamUtil.getStringValue(request.getParameter("name")));
        }
        if (!isMissing(request.getParameter("value"))) {
            m_logger.debug("updating param value from " +_MetaHeader.getValue() + "->" + request.getParameter("value"));
            _MetaHeader.setValue(WebParamUtil.getStringValue(request.getParameter("value")));
        }
        if (!isMissing(request.getParameter("httpEquiv"))) {
            m_logger.debug("updating param httpEquiv from " +_MetaHeader.getHttpEquiv() + "->" + request.getParameter("httpEquiv"));
            _MetaHeader.setHttpEquiv(WebParamUtil.getIntValue(request.getParameter("httpEquiv")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_MetaHeader.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _MetaHeader.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _MetaHeader);
        m_ds.update(_MetaHeader);
        m_actionExtent.afterUpdate(request, response, _MetaHeader);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, MetaHeader _MetaHeader) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        MetaHeader _MetaHeader = m_ds.getById(cid);

        if (!isMissing(request.getParameter("source"))) {
			return String.valueOf(_MetaHeader.getSource());
        }
        if (!isMissing(request.getParameter("detailId"))) {
			return String.valueOf(_MetaHeader.getDetailId());
        }
        if (!isMissing(request.getParameter("name"))) {
			return String.valueOf(_MetaHeader.getName());
        }
        if (!isMissing(request.getParameter("value"))) {
			return String.valueOf(_MetaHeader.getValue());
        }
        if (!isMissing(request.getParameter("httpEquiv"))) {
			return String.valueOf(_MetaHeader.getHttpEquiv());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_MetaHeader.getTimeCreated());
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
            MetaHeader _MetaHeader = MetaHeaderDS.getInstance().getById(id);
            if (_MetaHeader == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _MetaHeader);
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
    
    protected MetaHeaderDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( MetaHeaderAction.class);
}
