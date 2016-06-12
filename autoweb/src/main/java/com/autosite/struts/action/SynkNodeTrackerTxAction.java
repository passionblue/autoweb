/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015

*/


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

import com.autosite.db.SynkNodeTrackerTx;
import com.autosite.ds.SynkNodeTrackerTxDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.AutositeDataObject;

import com.autosite.struts.action.core.AutositeActionBase;
import com.autosite.struts.form.SynkNodeTrackerTxForm;
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



/*
Generated: Sat Feb 14 00:12:25 EST 2015
*/

public class SynkNodeTrackerTxAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(SynkNodeTrackerTxAction.class);

    public SynkNodeTrackerTxAction(){
        m_ds = SynkNodeTrackerTxDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
        m_actionExtent.setAction(this);
		registerDefaultViewsForAction();
    }

/*
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        SynkNodeTrackerTxForm _SynkNodeTrackerTxForm = (SynkNodeTrackerTxForm) form;
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
        SynkNodeTrackerTx _SynkNodeTrackerTx = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SynkNodeTrackerTx = m_ds.getById(cid);
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
            //SynkNodeTrackerTx _SynkNodeTrackerTx = m_ds.getById(cid);

            if (_SynkNodeTrackerTx == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_SynkNodeTrackerTx.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNodeTrackerTx.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_SynkNodeTrackerTx);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _SynkNodeTrackerTxForm == null) {
    	            editField(request, response, _SynkNodeTrackerTx);
				} else {
    	            edit(request, response, _SynkNodeTrackerTxForm, _SynkNodeTrackerTx);
				}
                if (returnObjects != null) returnObjects.put("target", _SynkNodeTrackerTx);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                SynkNodeTrackerTx o = m_ds.getById( _SynkNodeTrackerTx.getId(), true);

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
            //SynkNodeTrackerTx _SynkNodeTrackerTx = m_ds.getById(cid);

            if (_SynkNodeTrackerTx == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SynkNodeTrackerTx.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNodeTrackerTx.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _SynkNodeTrackerTx);
                if (returnObjects != null) returnObjects.put("target", _SynkNodeTrackerTx);
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
            //SynkNodeTrackerTx _SynkNodeTrackerTx = m_ds.getById(cid);

            if (_SynkNodeTrackerTx == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SynkNodeTrackerTx.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNodeTrackerTx.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _SynkNodeTrackerTx);
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

            m_ds.delete(_SynkNodeTrackerTx); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _SynkNodeTrackerTx);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _SynkNodeTrackerTx);
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


            m_logger.info("Creating new SynkNodeTrackerTx" );
            SynkNodeTrackerTx _SynkNodeTrackerTxNew = new SynkNodeTrackerTx();   

            // Setting IDs for the object
            _SynkNodeTrackerTxNew.setSiteId(site.getId());
			
            if ( _SynkNodeTrackerTxForm == null) {
                setFields(request, response, _SynkNodeTrackerTxNew);
            } else {

            _SynkNodeTrackerTxNew.setNamespace(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getNamespace()));
            m_logger.debug("setting Namespace=" +_SynkNodeTrackerTxForm.getNamespace());


            _SynkNodeTrackerTxNew.setDeviceId(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getDeviceId()));
            m_logger.debug("setting DeviceId=" +_SynkNodeTrackerTxForm.getDeviceId());


            _SynkNodeTrackerTxNew.setTxToken(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getTxToken()));
            m_logger.debug("setting TxToken=" +_SynkNodeTrackerTxForm.getTxToken());


            _SynkNodeTrackerTxNew.setStampAcked(WebParamUtil.getLongValue(_SynkNodeTrackerTxForm.getStampAcked()));
            m_logger.debug("setting StampAcked=" +_SynkNodeTrackerTxForm.getStampAcked());


            _SynkNodeTrackerTxNew.setStampLast(WebParamUtil.getLongValue(_SynkNodeTrackerTxForm.getStampLast()));
            m_logger.debug("setting StampLast=" +_SynkNodeTrackerTxForm.getStampLast());


            _SynkNodeTrackerTxNew.setNumRecords(WebParamUtil.getIntegerValue(_SynkNodeTrackerTxForm.getNumRecords()));
            m_logger.debug("setting NumRecords=" +_SynkNodeTrackerTxForm.getNumRecords());


            _SynkNodeTrackerTxNew.setIp(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getIp()));
            m_logger.debug("setting Ip=" +_SynkNodeTrackerTxForm.getIp());


            _SynkNodeTrackerTxNew.setTimeCreated(WebParamUtil.getTimestampValue(_SynkNodeTrackerTxForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SynkNodeTrackerTxForm.getTimeCreated());

        	_SynkNodeTrackerTxNew.setTimeCreated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_SynkNodeTrackerTxNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _SynkNodeTrackerTxNew);
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
            
            if (_SynkNodeTrackerTxNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SynkNodeTrackerTxNew);
            if (returnObjects != null) returnObjects.put("target", _SynkNodeTrackerTxNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _SynkNodeTrackerTxNew);
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
             _SynkNodeTrackerTx =  _SynkNodeTrackerTxNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _SynkNodeTrackerTx, "cleaner-ticket" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new SynkNodeTrackerTx();
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        SynkNodeTrackerTxForm _SynkNodeTrackerTxForm = (SynkNodeTrackerTxForm) form;
        SynkNodeTrackerTx _SynkNodeTrackerTx = (SynkNodeTrackerTx) dataObject;

        m_logger.debug("Before update " + SynkNodeTrackerTxDS.objectToString(_SynkNodeTrackerTx));

        _SynkNodeTrackerTx.setNamespace(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getNamespace()));


        _SynkNodeTrackerTx.setDeviceId(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getDeviceId()));


        _SynkNodeTrackerTx.setTxToken(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getTxToken()));


        _SynkNodeTrackerTx.setStampAcked(WebParamUtil.getLongValue(_SynkNodeTrackerTxForm.getStampAcked()));


        _SynkNodeTrackerTx.setStampLast(WebParamUtil.getLongValue(_SynkNodeTrackerTxForm.getStampLast()));


        _SynkNodeTrackerTx.setNumRecords(WebParamUtil.getIntegerValue(_SynkNodeTrackerTxForm.getNumRecords()));


        _SynkNodeTrackerTx.setIp(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getIp()));





        m_actionExtent.beforeUpdate(request, response, _SynkNodeTrackerTx);
        m_ds.update(_SynkNodeTrackerTx);
        m_actionExtent.afterUpdate(request, response, _SynkNodeTrackerTx);
        m_logger.debug("After update " + SynkNodeTrackerTxDS.objectToString(_SynkNodeTrackerTx));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SynkNodeTrackerTx _SynkNodeTrackerTx = (SynkNodeTrackerTx) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
            m_logger.debug("updating param namespace from " +_SynkNodeTrackerTx.getNamespace() + "->" + request.getParameter("namespace"));
            _SynkNodeTrackerTx.setNamespace(WebParamUtil.getStringValue(request.getParameter("namespace")));

        }
        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_SynkNodeTrackerTx.getDeviceId() + "->" + request.getParameter("deviceId"));
            _SynkNodeTrackerTx.setDeviceId(WebParamUtil.getStringValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("txToken"))) {
            m_logger.debug("updating param txToken from " +_SynkNodeTrackerTx.getTxToken() + "->" + request.getParameter("txToken"));
            _SynkNodeTrackerTx.setTxToken(WebParamUtil.getStringValue(request.getParameter("txToken")));

        }
        if (!isMissing(request.getParameter("stampAcked"))) {
            m_logger.debug("updating param stampAcked from " +_SynkNodeTrackerTx.getStampAcked() + "->" + request.getParameter("stampAcked"));
            _SynkNodeTrackerTx.setStampAcked(WebParamUtil.getLongValue(request.getParameter("stampAcked")));

        }
        if (!isMissing(request.getParameter("stampLast"))) {
            m_logger.debug("updating param stampLast from " +_SynkNodeTrackerTx.getStampLast() + "->" + request.getParameter("stampLast"));
            _SynkNodeTrackerTx.setStampLast(WebParamUtil.getLongValue(request.getParameter("stampLast")));

        }
        if (!isMissing(request.getParameter("numRecords"))) {
            m_logger.debug("updating param numRecords from " +_SynkNodeTrackerTx.getNumRecords() + "->" + request.getParameter("numRecords"));
            _SynkNodeTrackerTx.setNumRecords(WebParamUtil.getIntegerValue(request.getParameter("numRecords")));

        }
        if (!isMissing(request.getParameter("ip"))) {
            m_logger.debug("updating param ip from " +_SynkNodeTrackerTx.getIp() + "->" + request.getParameter("ip"));
            _SynkNodeTrackerTx.setIp(WebParamUtil.getStringValue(request.getParameter("ip")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SynkNodeTrackerTx.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SynkNodeTrackerTx.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }

        m_actionExtent.beforeUpdate(request, response, _SynkNodeTrackerTx);
        m_ds.update(_SynkNodeTrackerTx);
        m_actionExtent.afterUpdate(request, response, _SynkNodeTrackerTx);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SynkNodeTrackerTx _SynkNodeTrackerTx = (SynkNodeTrackerTx) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
            m_logger.debug("updating param namespace from " +_SynkNodeTrackerTx.getNamespace() + "->" + request.getParameter("namespace"));
            _SynkNodeTrackerTx.setNamespace(WebParamUtil.getStringValue(request.getParameter("namespace")));

        }
        if (!isMissing(request.getParameter("deviceId"))) {
            m_logger.debug("updating param deviceId from " +_SynkNodeTrackerTx.getDeviceId() + "->" + request.getParameter("deviceId"));
            _SynkNodeTrackerTx.setDeviceId(WebParamUtil.getStringValue(request.getParameter("deviceId")));

        }
        if (!isMissing(request.getParameter("txToken"))) {
            m_logger.debug("updating param txToken from " +_SynkNodeTrackerTx.getTxToken() + "->" + request.getParameter("txToken"));
            _SynkNodeTrackerTx.setTxToken(WebParamUtil.getStringValue(request.getParameter("txToken")));

        }
        if (!isMissing(request.getParameter("stampAcked"))) {
            m_logger.debug("updating param stampAcked from " +_SynkNodeTrackerTx.getStampAcked() + "->" + request.getParameter("stampAcked"));
            _SynkNodeTrackerTx.setStampAcked(WebParamUtil.getLongValue(request.getParameter("stampAcked")));

        }
        if (!isMissing(request.getParameter("stampLast"))) {
            m_logger.debug("updating param stampLast from " +_SynkNodeTrackerTx.getStampLast() + "->" + request.getParameter("stampLast"));
            _SynkNodeTrackerTx.setStampLast(WebParamUtil.getLongValue(request.getParameter("stampLast")));

        }
        if (!isMissing(request.getParameter("numRecords"))) {
            m_logger.debug("updating param numRecords from " +_SynkNodeTrackerTx.getNumRecords() + "->" + request.getParameter("numRecords"));
            _SynkNodeTrackerTx.setNumRecords(WebParamUtil.getIntegerValue(request.getParameter("numRecords")));

        }
        if (!isMissing(request.getParameter("ip"))) {
            m_logger.debug("updating param ip from " +_SynkNodeTrackerTx.getIp() + "->" + request.getParameter("ip"));
            _SynkNodeTrackerTx.setIp(WebParamUtil.getStringValue(request.getParameter("ip")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SynkNodeTrackerTx.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SynkNodeTrackerTx.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        SynkNodeTrackerTxForm _SynkNodeTrackerTxForm = (SynkNodeTrackerTxForm) form;
        SynkNodeTrackerTx _SynkNodeTrackerTx = (SynkNodeTrackerTx) dataObject;

            _SynkNodeTrackerTx.setNamespace(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getNamespace()));
            m_logger.debug("setting Namespace=" +_SynkNodeTrackerTxForm.getNamespace());


            _SynkNodeTrackerTx.setDeviceId(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getDeviceId()));
            m_logger.debug("setting DeviceId=" +_SynkNodeTrackerTxForm.getDeviceId());


            _SynkNodeTrackerTx.setTxToken(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getTxToken()));
            m_logger.debug("setting TxToken=" +_SynkNodeTrackerTxForm.getTxToken());


            _SynkNodeTrackerTx.setStampAcked(WebParamUtil.getLongValue(_SynkNodeTrackerTxForm.getStampAcked()));
            m_logger.debug("setting StampAcked=" +_SynkNodeTrackerTxForm.getStampAcked());


            _SynkNodeTrackerTx.setStampLast(WebParamUtil.getLongValue(_SynkNodeTrackerTxForm.getStampLast()));
            m_logger.debug("setting StampLast=" +_SynkNodeTrackerTxForm.getStampLast());


            _SynkNodeTrackerTx.setNumRecords(WebParamUtil.getIntegerValue(_SynkNodeTrackerTxForm.getNumRecords()));
            m_logger.debug("setting NumRecords=" +_SynkNodeTrackerTxForm.getNumRecords());


            _SynkNodeTrackerTx.setIp(WebParamUtil.getStringValue(_SynkNodeTrackerTxForm.getIp()));
            m_logger.debug("setting Ip=" +_SynkNodeTrackerTxForm.getIp());


            _SynkNodeTrackerTx.setTimeCreated(WebParamUtil.getTimestampValue(_SynkNodeTrackerTxForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SynkNodeTrackerTxForm.getTimeCreated());

        	_SynkNodeTrackerTx.setTimeCreated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SynkNodeTrackerTx _SynkNodeTrackerTx = m_ds.getById(cid);
        SynkNodeTrackerTx _SynkNodeTrackerTx = (SynkNodeTrackerTx) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getNamespace());
        }
        if (!isMissing(request.getParameter("deviceId"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getDeviceId());
        }
        if (!isMissing(request.getParameter("txToken"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getTxToken());
        }
        if (!isMissing(request.getParameter("stampAcked"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getStampAcked());
        }
        if (!isMissing(request.getParameter("stampLast"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getStampLast());
        }
        if (!isMissing(request.getParameter("numRecords"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getNumRecords());
        }
        if (!isMissing(request.getParameter("ip"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getIp());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_SynkNodeTrackerTx.getTimeCreated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, SynkNodeTrackerTx _SynkNodeTrackerTx) {
        if (fieldName == null || fieldName.equals("")|| _SynkNodeTrackerTx == null) return null;
        
        if (fieldName.equals("namespace")) {
            return WebUtil.display(_SynkNodeTrackerTx.getNamespace());
        }
        if (fieldName.equals("deviceId")) {
            return WebUtil.display(_SynkNodeTrackerTx.getDeviceId());
        }
        if (fieldName.equals("txToken")) {
            return WebUtil.display(_SynkNodeTrackerTx.getTxToken());
        }
        if (fieldName.equals("stampAcked")) {
            return WebUtil.display(_SynkNodeTrackerTx.getStampAcked());
        }
        if (fieldName.equals("stampLast")) {
            return WebUtil.display(_SynkNodeTrackerTx.getStampLast());
        }
        if (fieldName.equals("numRecords")) {
            return WebUtil.display(_SynkNodeTrackerTx.getNumRecords());
        }
        if (fieldName.equals("ip")) {
            return WebUtil.display(_SynkNodeTrackerTx.getIp());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_SynkNodeTrackerTx.getTimeCreated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        SynkNodeTrackerTxForm _SynkNodeTrackerTxForm = (SynkNodeTrackerTxForm) form;

		if(requestParams.containsKey("namespace"))
			_SynkNodeTrackerTxForm.setNamespace((String)requestParams.get("namespace"));
		if(requestParams.containsKey("deviceId"))
			_SynkNodeTrackerTxForm.setDeviceId((String)requestParams.get("deviceId"));
		if(requestParams.containsKey("txToken"))
			_SynkNodeTrackerTxForm.setTxToken((String)requestParams.get("txToken"));
		if(requestParams.containsKey("stampAcked"))
			_SynkNodeTrackerTxForm.setStampAcked((String)requestParams.get("stampAcked"));
		if(requestParams.containsKey("stampLast"))
			_SynkNodeTrackerTxForm.setStampLast((String)requestParams.get("stampLast"));
		if(requestParams.containsKey("numRecords"))
			_SynkNodeTrackerTxForm.setNumRecords((String)requestParams.get("numRecords"));
		if(requestParams.containsKey("ip"))
			_SynkNodeTrackerTxForm.setIp((String)requestParams.get("ip"));
		if(requestParams.containsKey("timeCreated"))
			_SynkNodeTrackerTxForm.setTimeCreated((String)requestParams.get("timeCreated"));
    }


    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return true;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "synk_node_tracker_tx_home=NULL,/jsp/form/synkNodeTrackerTx_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_node_tracker_tx_list=NULL,/jsp/form/synkNodeTrackerTx_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_node_tracker_tx_form=NULL,/jsp/form/synkNodeTrackerTx_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_node_tracker_tx_ajax=NULL,/jsp/form/synkNodeTrackerTx_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_tx_home=NULL,/jsp/form/synkNodeTrackerTx_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_tx_list=NULL,/jsp/form/synkNodeTrackerTx_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_tx_form=NULL,/jsp/form/synkNodeTrackerTx_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_node_tracker_tx_ajax=NULL,/jsp/form/synkNodeTrackerTx_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    public String getActionGroupName(){ return "Synk";} 


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected SynkNodeTrackerTxDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
