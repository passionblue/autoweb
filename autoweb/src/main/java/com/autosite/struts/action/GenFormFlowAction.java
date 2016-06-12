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

import com.autosite.ds.GenFormFlowDS;
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
import com.autosite.struts.form.GenFormFlowForm;
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


import com.autosite.holder.GenFormFlowDataHolder;

/*
Generated: Sun Mar 15 14:31:01 EDT 2015
*/

public class GenFormFlowAction extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(GenFormFlowAction.class);

    public GenFormFlowAction(){
        m_ds = GenFormFlowDS.getInstance();
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

        GenFormFlowForm _GenFormFlowForm = (GenFormFlowForm) form;
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
            hasRequestValue(request, "add", "true")||
            hasRequestValue(request, "act", "add")||
            hasRequestValue(request, "command1", "true")||
            hasRequestValue(request, "act", "command1")||
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
        GenFormFlowDataHolder _GenFormFlow = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _GenFormFlow = m_ds.getById(cid);
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
            //GenFormFlow _GenFormFlow = m_ds.getById(cid);

            if (_GenFormFlow == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_GenFormFlow.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenFormFlow.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "TESTGROUP", "GenFormFlow");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_GenFormFlow);
            } else {
                storable.setDataHolder(_GenFormFlow);
            }
            updateSessionStorable(session, "TESTGROUP", "GenFormFlow",storable);

            try {
                checkDepedenceIntegrity(_GenFormFlow);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _GenFormFlowForm == null) {
    	            editField(request, response, _GenFormFlow);
				} else {
    	            edit(request, response, _GenFormFlowForm, _GenFormFlow);
				}
                if (returnObjects != null) returnObjects.put("target", _GenFormFlow);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                GenFormFlowDataHolder o = m_ds.getById( _GenFormFlow.getId(), true);

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
            //GenFormFlow _GenFormFlow = m_ds.getById(cid);

            if (_GenFormFlow == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenFormFlow.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenFormFlow.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            SessionStorableDataHolderWrapper storable = (SessionStorableDataHolderWrapper) getSessionStorable(session,  "TESTGROUP", "GenFormFlow");
            if (storable == null) {
                storable = new SessionStorableDataHolderWrapper(_GenFormFlow);
            } else {
                storable.setDataHolder(_GenFormFlow);
            }
            updateSessionStorable(session, "TESTGROUP", "GenFormFlow",storable);

            try{
                editField(request, response, _GenFormFlow);
                if (returnObjects != null) returnObjects.put("target", _GenFormFlow);
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
            //GenFormFlow _GenFormFlow = m_ds.getById(cid);

            if (_GenFormFlow == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_GenFormFlow.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _GenFormFlow.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            //Handle SessionStorable
            removeSessionStorable(session, "TESTGROUP", "GenFormFlow");


            try {
                m_actionExtent.beforeDelete(request, response, _GenFormFlow);
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

            m_ds.delete(_GenFormFlow); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _GenFormFlow);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _GenFormFlow);
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


            m_logger.info("Creating new GenFormFlow" );
            GenFormFlowDataHolder _GenFormFlowNew = new GenFormFlowDataHolder();   

            // Setting IDs for the object
            _GenFormFlowNew.setSiteId(site.getId());
			
            if ( _GenFormFlowForm == null) {
                setFields(request, response, _GenFormFlowNew);
            } else {

            _GenFormFlowNew.setExtString(WebParamUtil.getStringValue(_GenFormFlowForm.getExtString()));
            m_logger.debug("setting ExtString=" +_GenFormFlowForm.getExtString());


            _GenFormFlowNew.setExtInt(WebParamUtil.getIntegerValue(_GenFormFlowForm.getExtInt()));
            m_logger.debug("setting ExtInt=" +_GenFormFlowForm.getExtInt());


			}
            //Handle SessionStorable
            SessionStorableDataHolderWrapper newHolderWrapper = new SessionStorableDataHolderWrapper(_GenFormFlowNew);
            addSessionStorable(session, "TESTGROUP", "GenFormFlow", newHolderWrapper);

            try {
                checkDepedenceIntegrity(_GenFormFlowNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _GenFormFlowNew);
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
            
            if (_GenFormFlowNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_GenFormFlowNew);
            if (returnObjects != null) returnObjects.put("target", _GenFormFlowNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _GenFormFlowNew);
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
             _GenFormFlow =  _GenFormFlowNew;
            

        } else if ( hasRequestValue(request, "command1", "true")  || hasRequestValue(request, "cmd", "command1") ) {
            if (!haveAccessToCommand(session, "command1" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "command1", _GenFormFlow);
                if (returnObjects != null &&  _GenFormFlow!= null ) returnObjects.put("target", _GenFormFlow);
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
		    	m_actionExtent.processCommand(request, response, "command2", _GenFormFlow);
                if (returnObjects != null &&  _GenFormFlow!= null ) returnObjects.put("target", _GenFormFlow);
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
		    	m_actionExtent.processCommand(request, response, "command3", _GenFormFlow);
                if (returnObjects != null &&  _GenFormFlow!= null ) returnObjects.put("target", _GenFormFlow);
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
        return new GenFormFlowDataHolder();
    }

    protected AbstractDataHolder createModelDataHolder() {
        return new GenFormFlowDataHolder();
    }



    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        GenFormFlowForm _GenFormFlowForm = (GenFormFlowForm) form;
        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder) dataObject;

        m_logger.debug("Before update " + GenFormFlowDS.objectToString(_GenFormFlow));

        _GenFormFlow.setExtString(WebParamUtil.getStringValue(_GenFormFlowForm.getExtString()));


        _GenFormFlow.setExtInt(WebParamUtil.getIntegerValue(_GenFormFlowForm.getExtInt()));



        m_actionExtent.beforeUpdate(request, response, _GenFormFlow);
        m_ds.update(_GenFormFlow);
        m_actionExtent.afterUpdate(request, response, _GenFormFlow);
        m_logger.debug("After update " + GenFormFlowDS.objectToString(_GenFormFlow));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder) dataObject;

        if (!isMissing(request.getParameter("extString"))) {
            m_logger.debug("updating param extString from " +_GenFormFlow.getExtString() + "->" + request.getParameter("extString"));
            _GenFormFlow.setExtString(WebParamUtil.getStringValue(request.getParameter("extString")));

        }
        if (!isMissing(request.getParameter("extInt"))) {
            m_logger.debug("updating param extInt from " +_GenFormFlow.getExtInt() + "->" + request.getParameter("extInt"));
            _GenFormFlow.setExtInt(WebParamUtil.getIntegerValue(request.getParameter("extInt")));

        }

        m_actionExtent.beforeUpdate(request, response, _GenFormFlow);
        m_ds.update(_GenFormFlow);
        m_actionExtent.afterUpdate(request, response, _GenFormFlow);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder) dataObject;

        if (!isMissing(request.getParameter("extString"))) {
            m_logger.debug("updating param extString from " +_GenFormFlow.getExtString() + "->" + request.getParameter("extString"));
            _GenFormFlow.setExtString(WebParamUtil.getStringValue(request.getParameter("extString")));

        }
        if (!isMissing(request.getParameter("extInt"))) {
            m_logger.debug("updating param extInt from " +_GenFormFlow.getExtInt() + "->" + request.getParameter("extInt"));
            _GenFormFlow.setExtInt(WebParamUtil.getIntegerValue(request.getParameter("extInt")));

        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        GenFormFlowForm _GenFormFlowForm = (GenFormFlowForm) form;
        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder) dataObject;

            _GenFormFlow.setExtString(WebParamUtil.getStringValue(_GenFormFlowForm.getExtString()));
            m_logger.debug("setting ExtString=" +_GenFormFlowForm.getExtString());


            _GenFormFlow.setExtInt(WebParamUtil.getIntegerValue(_GenFormFlowForm.getExtInt()));
            m_logger.debug("setting ExtInt=" +_GenFormFlowForm.getExtInt());



    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        GenFormFlow _GenFormFlow = m_ds.getById(cid);
        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder) dataObject;

        if (!isMissing(request.getParameter("extString"))) {
			return  JtStringUtil.valueOf(_GenFormFlow.getExtString());
        }
        if (!isMissing(request.getParameter("extInt"))) {
			return  JtStringUtil.valueOf(_GenFormFlow.getExtInt());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, GenFormFlowDataHolder _GenFormFlow) {
        if (fieldName == null || fieldName.equals("")|| _GenFormFlow == null) return null;
        
        if (fieldName.equals("extString")) {
            return WebUtil.display(_GenFormFlow.getExtString());
        }
        if (fieldName.equals("extInt")) {
            return WebUtil.display(_GenFormFlow.getExtInt());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        GenFormFlowForm _GenFormFlowForm = (GenFormFlowForm) form;

		if(requestParams.containsKey("extString"))
			_GenFormFlowForm.setExtString((String)requestParams.get("extString"));
		if(requestParams.containsKey("extInt"))
			_GenFormFlowForm.setExtInt((String)requestParams.get("extInt"));
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
        m_viewManager.registerView(getActionName(), "gen_form_flow_home=NULL,/jsp/form/genFormFlow_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_form_flow_list=NULL,/jsp/form/genFormFlow_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_form_flow_form=NULL,/jsp/form/genFormFlow_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "gen_form_flow_ajax=NULL,/jsp/form/genFormFlow_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "gen_form_flow_home=NULL,/jsp/form/genFormFlow_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_form_flow_list=NULL,/jsp/form/genFormFlow_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_form_flow_form=NULL,/jsp/form/genFormFlow_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "gen_form_flow_ajax=NULL,/jsp/form/genFormFlow_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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
        return "TESTGROUP";
    }

	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
     	if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
//    protected GenFormFlowDS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
