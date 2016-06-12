/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:16 EST 2015

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

import com.autosite.db.SynkNamespaceRecord;
import com.autosite.ds.SynkNamespaceRecordDS;
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
import com.autosite.struts.form.SynkNamespaceRecordForm;
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
Generated: Sat Feb 14 00:17:16 EST 2015
*/

public class SynkNamespaceRecordAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(SynkNamespaceRecordAction.class);

    public SynkNamespaceRecordAction(){
        m_ds = SynkNamespaceRecordDS.getInstance();
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

        SynkNamespaceRecordForm _SynkNamespaceRecordForm = (SynkNamespaceRecordForm) form;
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
        SynkNamespaceRecord _SynkNamespaceRecord = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SynkNamespaceRecord = m_ds.getById(cid);
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
            //SynkNamespaceRecord _SynkNamespaceRecord = m_ds.getById(cid);

            if (_SynkNamespaceRecord == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_SynkNamespaceRecord.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNamespaceRecord.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_SynkNamespaceRecord);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _SynkNamespaceRecordForm == null) {
    	            editField(request, response, _SynkNamespaceRecord);
				} else {
    	            edit(request, response, _SynkNamespaceRecordForm, _SynkNamespaceRecord);
				}
                if (returnObjects != null) returnObjects.put("target", _SynkNamespaceRecord);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                SynkNamespaceRecord o = m_ds.getById( _SynkNamespaceRecord.getId(), true);

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
            //SynkNamespaceRecord _SynkNamespaceRecord = m_ds.getById(cid);

            if (_SynkNamespaceRecord == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SynkNamespaceRecord.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNamespaceRecord.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _SynkNamespaceRecord);
                if (returnObjects != null) returnObjects.put("target", _SynkNamespaceRecord);
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
            //SynkNamespaceRecord _SynkNamespaceRecord = m_ds.getById(cid);

            if (_SynkNamespaceRecord == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SynkNamespaceRecord.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SynkNamespaceRecord.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _SynkNamespaceRecord);
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

            m_ds.delete(_SynkNamespaceRecord); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _SynkNamespaceRecord);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _SynkNamespaceRecord);
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


            m_logger.info("Creating new SynkNamespaceRecord" );
            SynkNamespaceRecord _SynkNamespaceRecordNew = new SynkNamespaceRecord();   

            // Setting IDs for the object
            _SynkNamespaceRecordNew.setSiteId(site.getId());
			
            if ( _SynkNamespaceRecordForm == null) {
                setFields(request, response, _SynkNamespaceRecordNew);
            } else {

            _SynkNamespaceRecordNew.setNamespace(WebParamUtil.getStringValue(_SynkNamespaceRecordForm.getNamespace()));
            m_logger.debug("setting Namespace=" +_SynkNamespaceRecordForm.getNamespace());


            _SynkNamespaceRecordNew.setRecordId(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getRecordId()));
            m_logger.debug("setting RecordId=" +_SynkNamespaceRecordForm.getRecordId());


            _SynkNamespaceRecordNew.setStamp(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getStamp()));
            m_logger.debug("setting Stamp=" +_SynkNamespaceRecordForm.getStamp());


            _SynkNamespaceRecordNew.setOrgStamp(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getOrgStamp()));
            m_logger.debug("setting OrgStamp=" +_SynkNamespaceRecordForm.getOrgStamp());


            _SynkNamespaceRecordNew.setTimeCreated(WebParamUtil.getTimestampValue(_SynkNamespaceRecordForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SynkNamespaceRecordForm.getTimeCreated());

        	_SynkNamespaceRecordNew.setTimeCreated(new TimeNow());

            _SynkNamespaceRecordNew.setTimeUpdated(WebParamUtil.getTimestampValue(_SynkNamespaceRecordForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SynkNamespaceRecordForm.getTimeUpdated());

        	_SynkNamespaceRecordNew.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_SynkNamespaceRecordNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _SynkNamespaceRecordNew);
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
            
            if (_SynkNamespaceRecordNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SynkNamespaceRecordNew);
            if (returnObjects != null) returnObjects.put("target", _SynkNamespaceRecordNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _SynkNamespaceRecordNew);
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
             _SynkNamespaceRecord =  _SynkNamespaceRecordNew;
            

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _SynkNamespaceRecord, "cleaner-ticket" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new SynkNamespaceRecord();
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        SynkNamespaceRecordForm _SynkNamespaceRecordForm = (SynkNamespaceRecordForm) form;
        SynkNamespaceRecord _SynkNamespaceRecord = (SynkNamespaceRecord) dataObject;

        m_logger.debug("Before update " + SynkNamespaceRecordDS.objectToString(_SynkNamespaceRecord));

        _SynkNamespaceRecord.setNamespace(WebParamUtil.getStringValue(_SynkNamespaceRecordForm.getNamespace()));


        _SynkNamespaceRecord.setRecordId(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getRecordId()));


        _SynkNamespaceRecord.setStamp(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getStamp()));


        _SynkNamespaceRecord.setOrgStamp(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getOrgStamp()));




        _SynkNamespaceRecord.setTimeUpdated(WebParamUtil.getTimestampValue(_SynkNamespaceRecordForm.getTimeUpdated()));

        _SynkNamespaceRecord.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _SynkNamespaceRecord);
        m_ds.update(_SynkNamespaceRecord);
        m_actionExtent.afterUpdate(request, response, _SynkNamespaceRecord);
        m_logger.debug("After update " + SynkNamespaceRecordDS.objectToString(_SynkNamespaceRecord));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SynkNamespaceRecord _SynkNamespaceRecord = (SynkNamespaceRecord) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
            m_logger.debug("updating param namespace from " +_SynkNamespaceRecord.getNamespace() + "->" + request.getParameter("namespace"));
            _SynkNamespaceRecord.setNamespace(WebParamUtil.getStringValue(request.getParameter("namespace")));

        }
        if (!isMissing(request.getParameter("recordId"))) {
            m_logger.debug("updating param recordId from " +_SynkNamespaceRecord.getRecordId() + "->" + request.getParameter("recordId"));
            _SynkNamespaceRecord.setRecordId(WebParamUtil.getLongValue(request.getParameter("recordId")));

        }
        if (!isMissing(request.getParameter("stamp"))) {
            m_logger.debug("updating param stamp from " +_SynkNamespaceRecord.getStamp() + "->" + request.getParameter("stamp"));
            _SynkNamespaceRecord.setStamp(WebParamUtil.getLongValue(request.getParameter("stamp")));

        }
        if (!isMissing(request.getParameter("orgStamp"))) {
            m_logger.debug("updating param orgStamp from " +_SynkNamespaceRecord.getOrgStamp() + "->" + request.getParameter("orgStamp"));
            _SynkNamespaceRecord.setOrgStamp(WebParamUtil.getLongValue(request.getParameter("orgStamp")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SynkNamespaceRecord.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SynkNamespaceRecord.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SynkNamespaceRecord.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SynkNamespaceRecord.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SynkNamespaceRecord.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _SynkNamespaceRecord);
        m_ds.update(_SynkNamespaceRecord);
        m_actionExtent.afterUpdate(request, response, _SynkNamespaceRecord);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SynkNamespaceRecord _SynkNamespaceRecord = (SynkNamespaceRecord) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
            m_logger.debug("updating param namespace from " +_SynkNamespaceRecord.getNamespace() + "->" + request.getParameter("namespace"));
            _SynkNamespaceRecord.setNamespace(WebParamUtil.getStringValue(request.getParameter("namespace")));

        }
        if (!isMissing(request.getParameter("recordId"))) {
            m_logger.debug("updating param recordId from " +_SynkNamespaceRecord.getRecordId() + "->" + request.getParameter("recordId"));
            _SynkNamespaceRecord.setRecordId(WebParamUtil.getLongValue(request.getParameter("recordId")));

        }
        if (!isMissing(request.getParameter("stamp"))) {
            m_logger.debug("updating param stamp from " +_SynkNamespaceRecord.getStamp() + "->" + request.getParameter("stamp"));
            _SynkNamespaceRecord.setStamp(WebParamUtil.getLongValue(request.getParameter("stamp")));

        }
        if (!isMissing(request.getParameter("orgStamp"))) {
            m_logger.debug("updating param orgStamp from " +_SynkNamespaceRecord.getOrgStamp() + "->" + request.getParameter("orgStamp"));
            _SynkNamespaceRecord.setOrgStamp(WebParamUtil.getLongValue(request.getParameter("orgStamp")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SynkNamespaceRecord.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SynkNamespaceRecord.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SynkNamespaceRecord.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SynkNamespaceRecord.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SynkNamespaceRecord.setTimeUpdated(new TimeNow());
        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        SynkNamespaceRecordForm _SynkNamespaceRecordForm = (SynkNamespaceRecordForm) form;
        SynkNamespaceRecord _SynkNamespaceRecord = (SynkNamespaceRecord) dataObject;

            _SynkNamespaceRecord.setNamespace(WebParamUtil.getStringValue(_SynkNamespaceRecordForm.getNamespace()));
            m_logger.debug("setting Namespace=" +_SynkNamespaceRecordForm.getNamespace());


            _SynkNamespaceRecord.setRecordId(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getRecordId()));
            m_logger.debug("setting RecordId=" +_SynkNamespaceRecordForm.getRecordId());


            _SynkNamespaceRecord.setStamp(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getStamp()));
            m_logger.debug("setting Stamp=" +_SynkNamespaceRecordForm.getStamp());


            _SynkNamespaceRecord.setOrgStamp(WebParamUtil.getLongValue(_SynkNamespaceRecordForm.getOrgStamp()));
            m_logger.debug("setting OrgStamp=" +_SynkNamespaceRecordForm.getOrgStamp());


            _SynkNamespaceRecord.setTimeCreated(WebParamUtil.getTimestampValue(_SynkNamespaceRecordForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SynkNamespaceRecordForm.getTimeCreated());

        	_SynkNamespaceRecord.setTimeCreated(new TimeNow());

            _SynkNamespaceRecord.setTimeUpdated(WebParamUtil.getTimestampValue(_SynkNamespaceRecordForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SynkNamespaceRecordForm.getTimeUpdated());

        	_SynkNamespaceRecord.setTimeUpdated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SynkNamespaceRecord _SynkNamespaceRecord = m_ds.getById(cid);
        SynkNamespaceRecord _SynkNamespaceRecord = (SynkNamespaceRecord) dataObject;

        if (!isMissing(request.getParameter("namespace"))) {
			return  JtStringUtil.valueOf(_SynkNamespaceRecord.getNamespace());
        }
        if (!isMissing(request.getParameter("recordId"))) {
			return  JtStringUtil.valueOf(_SynkNamespaceRecord.getRecordId());
        }
        if (!isMissing(request.getParameter("stamp"))) {
			return  JtStringUtil.valueOf(_SynkNamespaceRecord.getStamp());
        }
        if (!isMissing(request.getParameter("orgStamp"))) {
			return  JtStringUtil.valueOf(_SynkNamespaceRecord.getOrgStamp());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_SynkNamespaceRecord.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_SynkNamespaceRecord.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, SynkNamespaceRecord _SynkNamespaceRecord) {
        if (fieldName == null || fieldName.equals("")|| _SynkNamespaceRecord == null) return null;
        
        if (fieldName.equals("namespace")) {
            return WebUtil.display(_SynkNamespaceRecord.getNamespace());
        }
        if (fieldName.equals("recordId")) {
            return WebUtil.display(_SynkNamespaceRecord.getRecordId());
        }
        if (fieldName.equals("stamp")) {
            return WebUtil.display(_SynkNamespaceRecord.getStamp());
        }
        if (fieldName.equals("orgStamp")) {
            return WebUtil.display(_SynkNamespaceRecord.getOrgStamp());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_SynkNamespaceRecord.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_SynkNamespaceRecord.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        SynkNamespaceRecordForm _SynkNamespaceRecordForm = (SynkNamespaceRecordForm) form;

		if(requestParams.containsKey("namespace"))
			_SynkNamespaceRecordForm.setNamespace((String)requestParams.get("namespace"));
		if(requestParams.containsKey("recordId"))
			_SynkNamespaceRecordForm.setRecordId((String)requestParams.get("recordId"));
		if(requestParams.containsKey("stamp"))
			_SynkNamespaceRecordForm.setStamp((String)requestParams.get("stamp"));
		if(requestParams.containsKey("orgStamp"))
			_SynkNamespaceRecordForm.setOrgStamp((String)requestParams.get("orgStamp"));
		if(requestParams.containsKey("timeCreated"))
			_SynkNamespaceRecordForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_SynkNamespaceRecordForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
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
        m_viewManager.registerView(getActionName(), "synk_namespace_record_home=NULL,/jsp/form/synkNamespaceRecord_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_namespace_record_list=NULL,/jsp/form/synkNamespaceRecord_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_namespace_record_form=NULL,/jsp/form/synkNamespaceRecord_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "synk_namespace_record_ajax=NULL,/jsp/form/synkNamespaceRecord_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "synk_namespace_record_home=NULL,/jsp/form/synkNamespaceRecord_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_namespace_record_list=NULL,/jsp/form/synkNamespaceRecord_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_namespace_record_form=NULL,/jsp/form/synkNamespaceRecord_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "synk_namespace_record_ajax=NULL,/jsp/form/synkNamespaceRecord_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
    
//    protected SynkNamespaceRecordDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
