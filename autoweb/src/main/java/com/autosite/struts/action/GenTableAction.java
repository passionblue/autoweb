/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015

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

import com.autosite.db.GenTable;
import com.autosite.ds.GenTableDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.AutositeDataObject;
import com.autosite.holder.AbstractDataHolder;

import com.autosite.struts.action.core.AutositeActionBase;
import com.autosite.struts.form.GenTableForm;
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


import com.autosite.holder.GenTableDataHolder;

/*
Generated: Sun Mar 15 14:31:01 EDT 2015
*/

public class GenTableAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(GenTableAction.class);

    public GenTableAction(){
        m_ds = GenTableDS.getInstance();
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

        GenTableForm _GenTableForm = (GenTableForm) form;
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
        GenTableDataHolder _GenTable = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _GenTable = m_ds.getById(cid);
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
            //GenTable _GenTable = m_ds.getById(cid);

            if (_GenTable == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_GenTable.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenTable.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "Test2", "GenTable");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_GenTable);
            } else {
                storable.setDataHolder(_GenTable);
            }
            updateSessionStorable(session, "Test2", "GenTable",storable);

            try {
                checkDepedenceIntegrity(_GenTable);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _GenTableForm == null) {
    	            editField(request, response, _GenTable);
				} else {
    	            edit(request, response, _GenTableForm, _GenTable);
				}
                if (returnObjects != null) returnObjects.put("target", _GenTable);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                GenTableDataHolder o = m_ds.getById( _GenTable.getId(), true);

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
            //GenTable _GenTable = m_ds.getById(cid);

            if (_GenTable == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenTable.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenTable.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "Test2", "GenTable");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_GenTable);
            } else {
                storable.setDataHolder(_GenTable);
            }
            updateSessionStorable(session, "Test2", "GenTable",storable);

            try{
                editField(request, response, _GenTable);
                if (returnObjects != null) returnObjects.put("target", _GenTable);
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
            //GenTable _GenTable = m_ds.getById(cid);

            if (_GenTable == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenTable.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenTable.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            removeSessionStorable(session, "Test2", "GenTable");


            try {
                m_actionExtent.beforeDelete(request, response, _GenTable);
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

            m_ds.delete(_GenTable); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _GenTable);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _GenTable);
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


            m_logger.info("Creating new GenTable" );
            GenTableDataHolder _GenTableNew = new GenTableDataHolder();   

            // Setting IDs for the object
            _GenTableNew.setSiteId(site.getId());
			
            if ( _GenTableForm == null) {
                setFields(request, response, _GenTableNew);
            } else {

            _GenTableNew.setCountry(WebParamUtil.getStringValue(_GenTableForm.getCountry()));
            m_logger.debug("setting Country=" +_GenTableForm.getCountry());


            _GenTableNew.setAge(WebParamUtil.getIntegerValue(_GenTableForm.getAge()));
            m_logger.debug("setting Age=" +_GenTableForm.getAge());


            _GenTableNew.setDisabled(WebParamUtil.getIntegerValue(_GenTableForm.getDisabled()));
            m_logger.debug("setting Disabled=" +_GenTableForm.getDisabled());


            _GenTableNew.setComments(WebParamUtil.getStringValue(_GenTableForm.getComments()));
            m_logger.debug("setting Comments=" +_GenTableForm.getComments());


            _GenTableNew.setTimeCreated(WebParamUtil.getTimestampValue(_GenTableForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_GenTableForm.getTimeCreated());

        	_GenTableNew.setTimeCreated(new TimeNow());

            _GenTableNew.setTimeUpdated(WebParamUtil.getTimestampValue(_GenTableForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_GenTableForm.getTimeUpdated());

        	_GenTableNew.setTimeUpdated(new TimeNow());

            _GenTableNew.setExtString(WebParamUtil.getStringValue(_GenTableForm.getExtString()));
            m_logger.debug("setting ExtString=" +_GenTableForm.getExtString());


            _GenTableNew.setExtInt(WebParamUtil.getIntegerValue(_GenTableForm.getExtInt()));
            m_logger.debug("setting ExtInt=" +_GenTableForm.getExtInt());


			}
            //Handle SessionStorable
            SessionStorableDataHolderWrapper newHolderWrapper = new SessionStorableDataHolderWrapper(_GenTableNew);
            addSessionStorable(session, "Test2", "GenTable", newHolderWrapper);

            try {
                checkDepedenceIntegrity(_GenTableNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _GenTableNew);
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
            
            if (_GenTableNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_GenTableNew);
            if (returnObjects != null) returnObjects.put("target", _GenTableNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _GenTableNew);
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
             _GenTable =  _GenTableNew;
            

        } else if ( hasRequestValue(request, "command1", "true")  || hasRequestValue(request, "cmd", "command1") ) {
            if (!haveAccessToCommand(session, "command1" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "command1", _GenTable);
                if (returnObjects != null &&  _GenTable!= null ) returnObjects.put("target", _GenTable);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "command1", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "command1", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "command2", "true")  || hasRequestValue(request, "cmd", "command2") ) {
            if (!haveAccessToCommand(session, "command2" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "command2", _GenTable);
                if (returnObjects != null &&  _GenTable!= null ) returnObjects.put("target", _GenTable);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "command2", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "command2", "error"));
                return mapping.findForward("default");
            }

        } else if ( hasRequestValue(request, "command3", "true")  || hasRequestValue(request, "cmd", "command3") ) {
            if (!haveAccessToCommand(session, "command3" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "command3", _GenTable);
                if (returnObjects != null &&  _GenTable!= null ) returnObjects.put("target", _GenTable);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "command3", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "command3", "error"));
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
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new GenTableDataHolder();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new GenTableDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        GenTableForm _GenTableForm = (GenTableForm) form;
        GenTableDataHolder _GenTable = (GenTableDataHolder) dataObject;

        m_logger.debug("Before update " + GenTableDS.objectToString(_GenTable));

        _GenTable.setCountry(WebParamUtil.getStringValue(_GenTableForm.getCountry()));


        _GenTable.setAge(WebParamUtil.getIntegerValue(_GenTableForm.getAge()));


        _GenTable.setDisabled(WebParamUtil.getIntegerValue(_GenTableForm.getDisabled()));


        _GenTable.setComments(WebParamUtil.getStringValue(_GenTableForm.getComments()));




        _GenTable.setTimeUpdated(WebParamUtil.getTimestampValue(_GenTableForm.getTimeUpdated()));

        _GenTable.setTimeUpdated(new TimeNow());

        _GenTable.setExtString(WebParamUtil.getStringValue(_GenTableForm.getExtString()));


        _GenTable.setExtInt(WebParamUtil.getIntegerValue(_GenTableForm.getExtInt()));



        m_actionExtent.beforeUpdate(request, response, _GenTable);
        m_ds.update(_GenTable);
        m_actionExtent.afterUpdate(request, response, _GenTable);
        m_logger.debug("After update " + GenTableDS.objectToString(_GenTable));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        GenTableDataHolder _GenTable = (GenTableDataHolder) dataObject;

        if (!isMissing(request.getParameter("country"))) {
            m_logger.debug("updating param country from " +_GenTable.getCountry() + "->" + request.getParameter("country"));
            _GenTable.setCountry(WebParamUtil.getStringValue(request.getParameter("country")));

        }
        if (!isMissing(request.getParameter("age"))) {
            m_logger.debug("updating param age from " +_GenTable.getAge() + "->" + request.getParameter("age"));
            _GenTable.setAge(WebParamUtil.getIntegerValue(request.getParameter("age")));

        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_GenTable.getDisabled() + "->" + request.getParameter("disabled"));
            _GenTable.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }
        if (!isMissing(request.getParameter("comments"))) {
            m_logger.debug("updating param comments from " +_GenTable.getComments() + "->" + request.getParameter("comments"));
            _GenTable.setComments(WebParamUtil.getStringValue(request.getParameter("comments")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_GenTable.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _GenTable.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_GenTable.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _GenTable.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _GenTable.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("extString"))) {
            m_logger.debug("updating param extString from " +_GenTable.getExtString() + "->" + request.getParameter("extString"));
            _GenTable.setExtString(WebParamUtil.getStringValue(request.getParameter("extString")));

        }
        if (!isMissing(request.getParameter("extInt"))) {
            m_logger.debug("updating param extInt from " +_GenTable.getExtInt() + "->" + request.getParameter("extInt"));
            _GenTable.setExtInt(WebParamUtil.getIntegerValue(request.getParameter("extInt")));

        }

        m_actionExtent.beforeUpdate(request, response, _GenTable);
        m_ds.update(_GenTable);
        m_actionExtent.afterUpdate(request, response, _GenTable);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        GenTableDataHolder _GenTable = (GenTableDataHolder) dataObject;

        if (!isMissing(request.getParameter("country"))) {
            m_logger.debug("updating param country from " +_GenTable.getCountry() + "->" + request.getParameter("country"));
            _GenTable.setCountry(WebParamUtil.getStringValue(request.getParameter("country")));

        }
        if (!isMissing(request.getParameter("age"))) {
            m_logger.debug("updating param age from " +_GenTable.getAge() + "->" + request.getParameter("age"));
            _GenTable.setAge(WebParamUtil.getIntegerValue(request.getParameter("age")));

        }
        if (!isMissing(request.getParameter("disabled"))) {
            m_logger.debug("updating param disabled from " +_GenTable.getDisabled() + "->" + request.getParameter("disabled"));
            _GenTable.setDisabled(WebParamUtil.getIntegerValue(request.getParameter("disabled")));

        }
        if (!isMissing(request.getParameter("comments"))) {
            m_logger.debug("updating param comments from " +_GenTable.getComments() + "->" + request.getParameter("comments"));
            _GenTable.setComments(WebParamUtil.getStringValue(request.getParameter("comments")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_GenTable.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _GenTable.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_GenTable.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _GenTable.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _GenTable.setTimeUpdated(new TimeNow());
        }
        if (!isMissing(request.getParameter("extString"))) {
            m_logger.debug("updating param extString from " +_GenTable.getExtString() + "->" + request.getParameter("extString"));
            _GenTable.setExtString(WebParamUtil.getStringValue(request.getParameter("extString")));

        }
        if (!isMissing(request.getParameter("extInt"))) {
            m_logger.debug("updating param extInt from " +_GenTable.getExtInt() + "->" + request.getParameter("extInt"));
            _GenTable.setExtInt(WebParamUtil.getIntegerValue(request.getParameter("extInt")));

        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        GenTableForm _GenTableForm = (GenTableForm) form;
        GenTableDataHolder _GenTable = (GenTableDataHolder) dataObject;

            _GenTable.setCountry(WebParamUtil.getStringValue(_GenTableForm.getCountry()));
            m_logger.debug("setting Country=" +_GenTableForm.getCountry());


            _GenTable.setAge(WebParamUtil.getIntegerValue(_GenTableForm.getAge()));
            m_logger.debug("setting Age=" +_GenTableForm.getAge());


            _GenTable.setDisabled(WebParamUtil.getIntegerValue(_GenTableForm.getDisabled()));
            m_logger.debug("setting Disabled=" +_GenTableForm.getDisabled());


            _GenTable.setComments(WebParamUtil.getStringValue(_GenTableForm.getComments()));
            m_logger.debug("setting Comments=" +_GenTableForm.getComments());


            _GenTable.setTimeCreated(WebParamUtil.getTimestampValue(_GenTableForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_GenTableForm.getTimeCreated());

        	_GenTable.setTimeCreated(new TimeNow());

            _GenTable.setTimeUpdated(WebParamUtil.getTimestampValue(_GenTableForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_GenTableForm.getTimeUpdated());

        	_GenTable.setTimeUpdated(new TimeNow());

            _GenTable.setExtString(WebParamUtil.getStringValue(_GenTableForm.getExtString()));
            m_logger.debug("setting ExtString=" +_GenTableForm.getExtString());


            _GenTable.setExtInt(WebParamUtil.getIntegerValue(_GenTableForm.getExtInt()));
            m_logger.debug("setting ExtInt=" +_GenTableForm.getExtInt());



    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        GenTable _GenTable = m_ds.getById(cid);
        GenTableDataHolder _GenTable = (GenTableDataHolder) dataObject;

        if (!isMissing(request.getParameter("country"))) {
			return  JtStringUtil.valueOf(_GenTable.getCountry());
        }
        if (!isMissing(request.getParameter("age"))) {
			return  JtStringUtil.valueOf(_GenTable.getAge());
        }
        if (!isMissing(request.getParameter("disabled"))) {
			return  JtStringUtil.valueOf(_GenTable.getDisabled());
        }
        if (!isMissing(request.getParameter("comments"))) {
			return  JtStringUtil.valueOf(_GenTable.getComments());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_GenTable.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_GenTable.getTimeUpdated());
        }
        if (!isMissing(request.getParameter("extString"))) {
			return  JtStringUtil.valueOf(_GenTable.getExtString());
        }
        if (!isMissing(request.getParameter("extInt"))) {
			return  JtStringUtil.valueOf(_GenTable.getExtInt());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, GenTableDataHolder _GenTable) {
        if (fieldName == null || fieldName.equals("")|| _GenTable == null) return null;
        
        if (fieldName.equals("country")) {
            return WebUtil.display(_GenTable.getCountry());
        }
        if (fieldName.equals("age")) {
            return WebUtil.display(_GenTable.getAge());
        }
        if (fieldName.equals("disabled")) {
            return WebUtil.display(_GenTable.getDisabled());
        }
        if (fieldName.equals("comments")) {
            return WebUtil.display(_GenTable.getComments());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_GenTable.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_GenTable.getTimeUpdated());
        }
        if (fieldName.equals("extString")) {
            return WebUtil.display(_GenTable.getExtString());
        }
        if (fieldName.equals("extInt")) {
            return WebUtil.display(_GenTable.getExtInt());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        GenTableForm _GenTableForm = (GenTableForm) form;

		if(requestParams.containsKey("country"))
			_GenTableForm.setCountry((String)requestParams.get("country"));
		if(requestParams.containsKey("age"))
			_GenTableForm.setAge((String)requestParams.get("age"));
		if(requestParams.containsKey("disabled"))
			_GenTableForm.setDisabled((String)requestParams.get("disabled"));
		if(requestParams.containsKey("comments"))
			_GenTableForm.setComments((String)requestParams.get("comments"));
		if(requestParams.containsKey("timeCreated"))
			_GenTableForm.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_GenTableForm.setTimeUpdated((String)requestParams.get("timeUpdated"));
		if(requestParams.containsKey("extString"))
			_GenTableForm.setExtString((String)requestParams.get("extString"));
		if(requestParams.containsKey("extInt"))
			_GenTableForm.setExtInt((String)requestParams.get("extInt"));
    }


    protected boolean loginRequired() {
        return true;
    }

    // SynchRequired is controlled by config file conf/synk.properties
    // take a look at add the actiongroup or action name to the list
    public boolean isSynchRequired(){

        return synkRequired;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "gen_table_home=NULL,/jsp/form/genTable_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_table_list=NULL,/jsp/form/genTable_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_table_form=NULL,/jsp/form/genTable_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_table_ajax=NULL,/jsp/form/genTable_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "gen_table_home=NULL,/jsp/form/genTable_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_table_list=NULL,/jsp/form/genTable_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_table_form=NULL,/jsp/form/genTable_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_table_ajax=NULL,/jsp/form/genTable_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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

    public String getActionGroupName(){ return "Test2";} 

    protected String getSessionStorableGroup() {
        return "Test2";
    }

	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected GenTableDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
