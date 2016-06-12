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

import com.autosite.db.AutositeSynchLedger;
import com.autosite.ds.AutositeSynchLedgerDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.AutositeSynchLedgerForm;
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




public class AutositeSynchLedgerAction extends AutositeCoreAction {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeSynchLedgerAction.class);

    public AutositeSynchLedgerAction(){
        m_ds = AutositeSynchLedgerDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
		registerDefaultViewsForAction();
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        AutositeSynchLedgerForm _AutositeSynchLedgerForm = (AutositeSynchLedgerForm) form;
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

		//===================================================================================================================
		// Check if needs confirmTo step
        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "XXXXXXXXXXX", "true"))) // This line is just added for template. if you dont see any command above, add command in template field
        {    
            if (forwardConfirmTo(request))
                return mapping.findForward("default");
        
        }


		//===================================================================================================================
		// Check the user has permission to run this action 
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


		//===================================================================================================================
		// Find object if parameter has "id" field
        AutositeSynchLedger _AutositeSynchLedger = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _AutositeSynchLedger = m_ds.getById(cid);
		}



        boolean addCommandRoutedToEdit = false;


		//================== EDIT =====================================================================================
        if (isActionCmdEdit(request) || addCommandRoutedToEdit) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

            if (_AutositeSynchLedger == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_AutositeSynchLedger.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeSynchLedger.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_AutositeSynchLedger);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _AutositeSynchLedgerForm == null) {
    	            editField(request, response, _AutositeSynchLedger);
				} else {
    	            edit(request, response, _AutositeSynchLedgerForm, _AutositeSynchLedger);
				}
                if (returnObjects != null) returnObjects.put("target", _AutositeSynchLedger);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                AutositeSynchLedger o = m_ds.getById( _AutositeSynchLedger.getId(), true);

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

            //return mapping.findForward("default");
    
		// ================== EDIT FIELD =====================================================================================
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

            if (_AutositeSynchLedger == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeSynchLedger.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeSynchLedger.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _AutositeSynchLedger);
                if (returnObjects != null) returnObjects.put("target", _AutositeSynchLedger);
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
            //return mapping.findForward("default");

		// ================== DEL =====================================================================================
        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

            if (_AutositeSynchLedger == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_AutositeSynchLedger.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _AutositeSynchLedger.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _AutositeSynchLedger);
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

            m_ds.delete(_AutositeSynchLedger); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _AutositeSynchLedger);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _AutositeSynchLedger);
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


            m_logger.info("Creating new AutositeSynchLedger" );
            AutositeSynchLedger _AutositeSynchLedgerNew = new AutositeSynchLedger();   

            // Setting IDs for the object
            _AutositeSynchLedgerNew.setSiteId(site.getId());
			
            if ( _AutositeSynchLedgerForm == null) {
                setFields(request, response, _AutositeSynchLedgerNew);
            } else {

            _AutositeSynchLedgerNew.setDeviceId(WebParamUtil.getLongValue(_AutositeSynchLedgerForm.getDeviceId()));
            m_logger.debug("setting DeviceId=" +_AutositeSynchLedgerForm.getDeviceId());


            _AutositeSynchLedgerNew.setOriginalLedgerId(WebParamUtil.getLongValue(_AutositeSynchLedgerForm.getOriginalLedgerId()));
            m_logger.debug("setting OriginalLedgerId=" +_AutositeSynchLedgerForm.getOriginalLedgerId());


            _AutositeSynchLedgerNew.setScope(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getScope()));
            m_logger.debug("setting Scope=" +_AutositeSynchLedgerForm.getScope());


            _AutositeSynchLedgerNew.setTarget(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getTarget()));
            m_logger.debug("setting Target=" +_AutositeSynchLedgerForm.getTarget());


            _AutositeSynchLedgerNew.setRemoteToken(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getRemoteToken()));
            m_logger.debug("setting RemoteToken=" +_AutositeSynchLedgerForm.getRemoteToken());


            _AutositeSynchLedgerNew.setObjectId(WebParamUtil.getLongValue(_AutositeSynchLedgerForm.getObjectId()));
            m_logger.debug("setting ObjectId=" +_AutositeSynchLedgerForm.getObjectId());


            _AutositeSynchLedgerNew.setSynchId(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getSynchId()));
            m_logger.debug("setting SynchId=" +_AutositeSynchLedgerForm.getSynchId());


            _AutositeSynchLedgerNew.setTimeCreated(WebParamUtil.getTimestampValue(_AutositeSynchLedgerForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_AutositeSynchLedgerForm.getTimeCreated());

        	_AutositeSynchLedgerNew.setTimeCreated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_AutositeSynchLedgerNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _AutositeSynchLedgerNew);
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
            
            if (_AutositeSynchLedgerNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_AutositeSynchLedgerNew);
            if (returnObjects != null) returnObjects.put("target", _AutositeSynchLedgerNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _AutositeSynchLedgerNew);
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
             _AutositeSynchLedger =  _AutositeSynchLedgerNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }





        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, AutositeSynchLedgerForm _AutositeSynchLedgerForm, AutositeSynchLedger _AutositeSynchLedger) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

        m_logger.debug("Before update " + AutositeSynchLedgerDS.objectToString(_AutositeSynchLedger));

        _AutositeSynchLedger.setDeviceId(WebParamUtil.getLongValue(_AutositeSynchLedgerForm.getDeviceId()));


        _AutositeSynchLedger.setOriginalLedgerId(WebParamUtil.getLongValue(_AutositeSynchLedgerForm.getOriginalLedgerId()));


        _AutositeSynchLedger.setScope(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getScope()));


        _AutositeSynchLedger.setTarget(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getTarget()));


        _AutositeSynchLedger.setRemoteToken(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getRemoteToken()));


        _AutositeSynchLedger.setObjectId(WebParamUtil.getLongValue(_AutositeSynchLedgerForm.getObjectId()));


        _AutositeSynchLedger.setSynchId(WebParamUtil.getStringValue(_AutositeSynchLedgerForm.getSynchId()));





        m_actionExtent.beforeUpdate(request, response, _AutositeSynchLedger);
        m_ds.update(_AutositeSynchLedger);
        m_actionExtent.afterUpdate(request, response, _AutositeSynchLedger);
        m_logger.debug("After update " + AutositeSynchLedgerDS.objectToString(_AutositeSynchLedger));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeSynchLedger _AutositeSynchLedger) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_AutositeSynchLedger.getDeviceId() + "->" + request.getParameter("deviceId"));
            _AutositeSynchLedger.setDeviceId(WebParamUtil.getLongValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("originalLedgerId"))) {
            m_logger.debug("updating param originalLedgerId from " +_AutositeSynchLedger.getOriginalLedgerId() + "->" + request.getParameter("originalLedgerId"));
            _AutositeSynchLedger.setOriginalLedgerId(WebParamUtil.getLongValue(request.getParameter("originalLedgerId")));

        }
        if (!isMissing(request.getParameter("scope"))) {
            m_logger.debug("updating param scope from " +_AutositeSynchLedger.getScope() + "->" + request.getParameter("scope"));
            _AutositeSynchLedger.setScope(WebParamUtil.getStringValue(request.getParameter("scope")));

        }
        if (!isMissing(request.getParameter("target"))) {
            m_logger.debug("updating param target from " +_AutositeSynchLedger.getTarget() + "->" + request.getParameter("target"));
            _AutositeSynchLedger.setTarget(WebParamUtil.getStringValue(request.getParameter("target")));

        }
        if (!isMissing(request.getParameter("remoteToken"))) {
            m_logger.debug("updating param remoteToken from " +_AutositeSynchLedger.getRemoteToken() + "->" + request.getParameter("remoteToken"));
            _AutositeSynchLedger.setRemoteToken(WebParamUtil.getStringValue(request.getParameter("remoteToken")));

        }
        if (!isMissing(request.getParameter("objectId"))) {
            m_logger.debug("updating param objectId from " +_AutositeSynchLedger.getObjectId() + "->" + request.getParameter("objectId"));
            _AutositeSynchLedger.setObjectId(WebParamUtil.getLongValue(request.getParameter("objectId")));

        }
        if (!isMissing(request.getParameter("synchId"))) {
            m_logger.debug("updating param synchId from " +_AutositeSynchLedger.getSynchId() + "->" + request.getParameter("synchId"));
            _AutositeSynchLedger.setSynchId(WebParamUtil.getStringValue(request.getParameter("synchId")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeSynchLedger.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeSynchLedger.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

        m_actionExtent.beforeUpdate(request, response, _AutositeSynchLedger);
        m_ds.update(_AutositeSynchLedger);
        m_actionExtent.afterUpdate(request, response, _AutositeSynchLedger);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeSynchLedger _AutositeSynchLedger) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_AutositeSynchLedger.getDeviceId() + "->" + request.getParameter("deviceId"));
            _AutositeSynchLedger.setDeviceId(WebParamUtil.getLongValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("originalLedgerId"))) {
            m_logger.debug("updating param originalLedgerId from " +_AutositeSynchLedger.getOriginalLedgerId() + "->" + request.getParameter("originalLedgerId"));
            _AutositeSynchLedger.setOriginalLedgerId(WebParamUtil.getLongValue(request.getParameter("originalLedgerId")));

        }
        if (!isMissing(request.getParameter("scope"))) {
            m_logger.debug("updating param scope from " +_AutositeSynchLedger.getScope() + "->" + request.getParameter("scope"));
            _AutositeSynchLedger.setScope(WebParamUtil.getStringValue(request.getParameter("scope")));

        }
        if (!isMissing(request.getParameter("target"))) {
            m_logger.debug("updating param target from " +_AutositeSynchLedger.getTarget() + "->" + request.getParameter("target"));
            _AutositeSynchLedger.setTarget(WebParamUtil.getStringValue(request.getParameter("target")));

        }
        if (!isMissing(request.getParameter("remoteToken"))) {
            m_logger.debug("updating param remoteToken from " +_AutositeSynchLedger.getRemoteToken() + "->" + request.getParameter("remoteToken"));
            _AutositeSynchLedger.setRemoteToken(WebParamUtil.getStringValue(request.getParameter("remoteToken")));

        }
        if (!isMissing(request.getParameter("objectId"))) {
            m_logger.debug("updating param objectId from " +_AutositeSynchLedger.getObjectId() + "->" + request.getParameter("objectId"));
            _AutositeSynchLedger.setObjectId(WebParamUtil.getLongValue(request.getParameter("objectId")));

        }
        if (!isMissing(request.getParameter("synchId"))) {
            m_logger.debug("updating param synchId from " +_AutositeSynchLedger.getSynchId() + "->" + request.getParameter("synchId"));
            _AutositeSynchLedger.setSynchId(WebParamUtil.getStringValue(request.getParameter("synchId")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_AutositeSynchLedger.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _AutositeSynchLedger.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeSynchLedger _AutositeSynchLedger) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        AutositeSynchLedger _AutositeSynchLedger = m_ds.getById(cid);

        if (!isMissing(request.getParameter("deviceId"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getDeviceId());
        }
        if (!isMissing(request.getParameter("originalLedgerId"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getOriginalLedgerId());
        }
        if (!isMissing(request.getParameter("scope"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getScope());
        }
        if (!isMissing(request.getParameter("target"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getTarget());
        }
        if (!isMissing(request.getParameter("remoteToken"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getRemoteToken());
        }
        if (!isMissing(request.getParameter("objectId"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getObjectId());
        }
        if (!isMissing(request.getParameter("synchId"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getSynchId());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_AutositeSynchLedger.getTimeCreated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeSynchLedger _AutositeSynchLedger) throws Exception {
    }

    protected String getFieldByName(String fieldName, AutositeSynchLedger _AutositeSynchLedger) {
        if (fieldName == null || fieldName.equals("")|| _AutositeSynchLedger == null) return null;
        
        if (fieldName.equals("deviceId")) {
            return WebUtil.display(_AutositeSynchLedger.getDeviceId());
        }
        if (fieldName.equals("originalLedgerId")) {
            return WebUtil.display(_AutositeSynchLedger.getOriginalLedgerId());
        }
        if (fieldName.equals("scope")) {
            return WebUtil.display(_AutositeSynchLedger.getScope());
        }
        if (fieldName.equals("target")) {
            return WebUtil.display(_AutositeSynchLedger.getTarget());
        }
        if (fieldName.equals("remoteToken")) {
            return WebUtil.display(_AutositeSynchLedger.getRemoteToken());
        }
        if (fieldName.equals("objectId")) {
            return WebUtil.display(_AutositeSynchLedger.getObjectId());
        }
        if (fieldName.equals("synchId")) {
            return WebUtil.display(_AutositeSynchLedger.getSynchId());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_AutositeSynchLedger.getTimeCreated());
        }
		return null;
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        AutositeSynchLedgerForm _AutositeSynchLedgerForm = (AutositeSynchLedgerForm) form;

		if(requestParams.containsKey("deviceId"))
			_AutositeSynchLedgerForm.setDeviceId((String)requestParams.get("deviceId"));
		if(requestParams.containsKey("originalLedgerId"))
			_AutositeSynchLedgerForm.setOriginalLedgerId((String)requestParams.get("originalLedgerId"));
		if(requestParams.containsKey("scope"))
			_AutositeSynchLedgerForm.setScope((String)requestParams.get("scope"));
		if(requestParams.containsKey("target"))
			_AutositeSynchLedgerForm.setTarget((String)requestParams.get("target"));
		if(requestParams.containsKey("remoteToken"))
			_AutositeSynchLedgerForm.setRemoteToken((String)requestParams.get("remoteToken"));
		if(requestParams.containsKey("objectId"))
			_AutositeSynchLedgerForm.setObjectId((String)requestParams.get("objectId"));
		if(requestParams.containsKey("synchId"))
			_AutositeSynchLedgerForm.setSynchId((String)requestParams.get("synchId"));
		if(requestParams.containsKey("timeCreated"))
			_AutositeSynchLedgerForm.setTimeCreated((String)requestParams.get("timeCreated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return false;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "autosite_synch_ledger_home=NULL,/jsp/form/autositeSynchLedger_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_synch_ledger_list=NULL,/jsp/form/autositeSynchLedger_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_synch_ledger_form=NULL,/jsp/form/autositeSynchLedger_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "autosite_synch_ledger_ajax=NULL,/jsp/form/autositeSynchLedger_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "autosite_synch_ledger_home=NULL,/jsp/form/autositeSynchLedger_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_synch_ledger_list=NULL,/jsp/form/autositeSynchLedger_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_synch_ledger_form=NULL,/jsp/form/autositeSynchLedger_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "autosite_synch_ledger_ajax=NULL,/jsp/form/autositeSynchLedger_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
    protected AutositeSynchLedgerDS m_ds;
    protected AutositeActionExtent m_actionExtent;

}
