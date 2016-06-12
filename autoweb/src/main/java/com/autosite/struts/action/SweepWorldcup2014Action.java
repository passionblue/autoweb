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

import com.autosite.db.SweepWorldcup2014;
import com.autosite.ds.SweepWorldcup2014DS;
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
import com.autosite.struts.form.SweepWorldcup2014Form;
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




public class SweepWorldcup2014Action extends AutositeActionBase {

    private static Logger m_logger = LoggerFactory.getLogger(SweepWorldcup2014Action.class);

    public SweepWorldcup2014Action(){
        m_ds = SweepWorldcup2014DS.getInstance();
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

        SweepWorldcup2014Form _SweepWorldcup2014Form = (SweepWorldcup2014Form) form;
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
        SweepWorldcup2014 _SweepWorldcup2014 = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SweepWorldcup2014 = m_ds.getById(cid);
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
            //SweepWorldcup2014 _SweepWorldcup2014 = m_ds.getById(cid);

            if (_SweepWorldcup2014 == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (_SweepWorldcup2014.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepWorldcup2014.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
				setPage(session, 
				        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
				        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(_SweepWorldcup2014);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
	            if ( _SweepWorldcup2014Form == null) {
    	            editField(request, response, _SweepWorldcup2014);
				} else {
    	            edit(request, response, _SweepWorldcup2014Form, _SweepWorldcup2014);
				}
                if (returnObjects != null) returnObjects.put("target", _SweepWorldcup2014);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                SweepWorldcup2014 o = m_ds.getById( _SweepWorldcup2014.getId(), true);

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
            //SweepWorldcup2014 _SweepWorldcup2014 = m_ds.getById(cid);

            if (_SweepWorldcup2014 == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SweepWorldcup2014.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepWorldcup2014.getSiteId()); 

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, _SweepWorldcup2014);
                if (returnObjects != null) returnObjects.put("target", _SweepWorldcup2014);
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
            //SweepWorldcup2014 _SweepWorldcup2014 = m_ds.getById(cid);

            if (_SweepWorldcup2014 == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

				//Default error page but will be overridden by exception specific error page
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (_SweepWorldcup2014.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepWorldcup2014.getSiteId()); 

            	//setPage(session, getErrorPage());
	            processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, _SweepWorldcup2014);
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

            m_ds.delete(_SweepWorldcup2014); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _SweepWorldcup2014);
			//setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _SweepWorldcup2014);
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


            m_logger.info("Creating new SweepWorldcup2014" );
            SweepWorldcup2014 _SweepWorldcup2014New = new SweepWorldcup2014();   

            // Setting IDs for the object
            _SweepWorldcup2014New.setSiteId(site.getId());
			
            if ( _SweepWorldcup2014Form == null) {
                setFields(request, response, _SweepWorldcup2014New);
            } else {

            _SweepWorldcup2014New.setPlayer(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getPlayer()));
            m_logger.debug("setting Player=" +_SweepWorldcup2014Form.getPlayer());


            _SweepWorldcup2014New.setGame1(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1()));
            m_logger.debug("setting Game1=" +_SweepWorldcup2014Form.getGame1());


            _SweepWorldcup2014New.setGame1Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1Score()));
            m_logger.debug("setting Game1Score=" +_SweepWorldcup2014Form.getGame1Score());


            _SweepWorldcup2014New.setGame1ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1ScoreOpp()));
            m_logger.debug("setting Game1ScoreOpp=" +_SweepWorldcup2014Form.getGame1ScoreOpp());


            _SweepWorldcup2014New.setGame2(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2()));
            m_logger.debug("setting Game2=" +_SweepWorldcup2014Form.getGame2());


            _SweepWorldcup2014New.setGame2Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2Score()));
            m_logger.debug("setting Game2Score=" +_SweepWorldcup2014Form.getGame2Score());


            _SweepWorldcup2014New.setGame2ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2ScoreOpp()));
            m_logger.debug("setting Game2ScoreOpp=" +_SweepWorldcup2014Form.getGame2ScoreOpp());


            _SweepWorldcup2014New.setGame3(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3()));
            m_logger.debug("setting Game3=" +_SweepWorldcup2014Form.getGame3());


            _SweepWorldcup2014New.setGame3Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3Score()));
            m_logger.debug("setting Game3Score=" +_SweepWorldcup2014Form.getGame3Score());


            _SweepWorldcup2014New.setGame3ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3ScoreOpp()));
            m_logger.debug("setting Game3ScoreOpp=" +_SweepWorldcup2014Form.getGame3ScoreOpp());


            _SweepWorldcup2014New.setAdvance(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getAdvance()));
            m_logger.debug("setting Advance=" +_SweepWorldcup2014Form.getAdvance());


            _SweepWorldcup2014New.setTeam16A1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16A1()));
            m_logger.debug("setting Team16A1=" +_SweepWorldcup2014Form.getTeam16A1());


            _SweepWorldcup2014New.setTeam16A2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16A2()));
            m_logger.debug("setting Team16A2=" +_SweepWorldcup2014Form.getTeam16A2());


            _SweepWorldcup2014New.setTeam16B1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16B1()));
            m_logger.debug("setting Team16B1=" +_SweepWorldcup2014Form.getTeam16B1());


            _SweepWorldcup2014New.setTeam16B2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16B2()));
            m_logger.debug("setting Team16B2=" +_SweepWorldcup2014Form.getTeam16B2());


            _SweepWorldcup2014New.setTeam16C1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16C1()));
            m_logger.debug("setting Team16C1=" +_SweepWorldcup2014Form.getTeam16C1());


            _SweepWorldcup2014New.setTeam16C2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16C2()));
            m_logger.debug("setting Team16C2=" +_SweepWorldcup2014Form.getTeam16C2());


            _SweepWorldcup2014New.setTeam16D1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16D1()));
            m_logger.debug("setting Team16D1=" +_SweepWorldcup2014Form.getTeam16D1());


            _SweepWorldcup2014New.setTeam16D2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16D2()));
            m_logger.debug("setting Team16D2=" +_SweepWorldcup2014Form.getTeam16D2());


            _SweepWorldcup2014New.setTeam16E1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16E1()));
            m_logger.debug("setting Team16E1=" +_SweepWorldcup2014Form.getTeam16E1());


            _SweepWorldcup2014New.setTeam16E2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16E2()));
            m_logger.debug("setting Team16E2=" +_SweepWorldcup2014Form.getTeam16E2());


            _SweepWorldcup2014New.setTeam16F1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16F1()));
            m_logger.debug("setting Team16F1=" +_SweepWorldcup2014Form.getTeam16F1());


            _SweepWorldcup2014New.setTeam16F2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16F2()));
            m_logger.debug("setting Team16F2=" +_SweepWorldcup2014Form.getTeam16F2());


            _SweepWorldcup2014New.setTeam16G1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16G1()));
            m_logger.debug("setting Team16G1=" +_SweepWorldcup2014Form.getTeam16G1());


            _SweepWorldcup2014New.setTeam16G2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16G2()));
            m_logger.debug("setting Team16G2=" +_SweepWorldcup2014Form.getTeam16G2());


            _SweepWorldcup2014New.setTeam16H1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16H1()));
            m_logger.debug("setting Team16H1=" +_SweepWorldcup2014Form.getTeam16H1());


            _SweepWorldcup2014New.setTeam16H2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16H2()));
            m_logger.debug("setting Team16H2=" +_SweepWorldcup2014Form.getTeam16H2());


            _SweepWorldcup2014New.setQuarterFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal1()));
            m_logger.debug("setting QuarterFinal1=" +_SweepWorldcup2014Form.getQuarterFinal1());


            _SweepWorldcup2014New.setQuarterFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal2()));
            m_logger.debug("setting QuarterFinal2=" +_SweepWorldcup2014Form.getQuarterFinal2());


            _SweepWorldcup2014New.setQuarterFinal3(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal3()));
            m_logger.debug("setting QuarterFinal3=" +_SweepWorldcup2014Form.getQuarterFinal3());


            _SweepWorldcup2014New.setQuarterFinal4(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal4()));
            m_logger.debug("setting QuarterFinal4=" +_SweepWorldcup2014Form.getQuarterFinal4());


            _SweepWorldcup2014New.setQuarterFinal5(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal5()));
            m_logger.debug("setting QuarterFinal5=" +_SweepWorldcup2014Form.getQuarterFinal5());


            _SweepWorldcup2014New.setQuarterFinal6(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal6()));
            m_logger.debug("setting QuarterFinal6=" +_SweepWorldcup2014Form.getQuarterFinal6());


            _SweepWorldcup2014New.setQuarterFinal7(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal7()));
            m_logger.debug("setting QuarterFinal7=" +_SweepWorldcup2014Form.getQuarterFinal7());


            _SweepWorldcup2014New.setQuarterFinal8(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal8()));
            m_logger.debug("setting QuarterFinal8=" +_SweepWorldcup2014Form.getQuarterFinal8());


            _SweepWorldcup2014New.setSemiFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal1()));
            m_logger.debug("setting SemiFinal1=" +_SweepWorldcup2014Form.getSemiFinal1());


            _SweepWorldcup2014New.setSemiFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal2()));
            m_logger.debug("setting SemiFinal2=" +_SweepWorldcup2014Form.getSemiFinal2());


            _SweepWorldcup2014New.setSemiFinal3(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal3()));
            m_logger.debug("setting SemiFinal3=" +_SweepWorldcup2014Form.getSemiFinal3());


            _SweepWorldcup2014New.setSemiFinal4(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal4()));
            m_logger.debug("setting SemiFinal4=" +_SweepWorldcup2014Form.getSemiFinal4());


            _SweepWorldcup2014New.setFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getFinal1()));
            m_logger.debug("setting Final1=" +_SweepWorldcup2014Form.getFinal1());


            _SweepWorldcup2014New.setFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getFinal2()));
            m_logger.debug("setting Final2=" +_SweepWorldcup2014Form.getFinal2());


            _SweepWorldcup2014New.setChampion(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getChampion()));
            m_logger.debug("setting Champion=" +_SweepWorldcup2014Form.getChampion());


            _SweepWorldcup2014New.setFinalScoreWin(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getFinalScoreWin()));
            m_logger.debug("setting FinalScoreWin=" +_SweepWorldcup2014Form.getFinalScoreWin());


            _SweepWorldcup2014New.setFinalScoreLose(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getFinalScoreLose()));
            m_logger.debug("setting FinalScoreLose=" +_SweepWorldcup2014Form.getFinalScoreLose());


            _SweepWorldcup2014New.setTimeCreated(WebParamUtil.getTimestampValue(_SweepWorldcup2014Form.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SweepWorldcup2014Form.getTimeCreated());

        	_SweepWorldcup2014New.setTimeCreated(new TimeNow());

            _SweepWorldcup2014New.setTimeUpdated(WebParamUtil.getTimestampValue(_SweepWorldcup2014Form.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SweepWorldcup2014Form.getTimeUpdated());

        	_SweepWorldcup2014New.setTimeUpdated(new TimeNow());

			}

            try {
                checkDepedenceIntegrity(_SweepWorldcup2014New);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _SweepWorldcup2014New);
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
            
            if (_SweepWorldcup2014New.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SweepWorldcup2014New);
            if (returnObjects != null) returnObjects.put("target", _SweepWorldcup2014New);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _SweepWorldcup2014New);
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
             _SweepWorldcup2014 =  _SweepWorldcup2014New;
            

        } else if ( hasRequestValue(request, "verify", "true")  || hasRequestValue(request, "cmd", "verify") ) {
            if (!haveAccessToCommand(session, "verify" ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            try {
		    	m_actionExtent.processCommand(request, response, "verify", _SweepWorldcup2014);
                if (returnObjects != null &&  _SweepWorldcup2014!= null ) returnObjects.put("target", _SweepWorldcup2014);
			} catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

	            processPageForAction(request, "verify", "error", getErrorPage(), getSentPage(request));

	                setPage(session,
	                        ((ActionExtentException)e).getForwardPage(),
	                        pageManager.isInternalForward(getActionName(), "verify", "error"));
                return mapping.findForward("default");
            }

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");

            processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }


        if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  _SweepWorldcup2014, "cleaner-ticket" );



        return mapping.findForward("default");
    }
*/

    @Override
    protected AutositeDataObject createNewObject() {
        return new SweepWorldcup2014();
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm form, AutositeDataObject dataObject) throws Exception{
        SweepWorldcup2014Form _SweepWorldcup2014Form = (SweepWorldcup2014Form) form;
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) dataObject;

        m_logger.debug("Before update " + SweepWorldcup2014DS.objectToString(_SweepWorldcup2014));

        _SweepWorldcup2014.setPlayer(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getPlayer()));


        _SweepWorldcup2014.setGame1(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1()));


        _SweepWorldcup2014.setGame1Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1Score()));


        _SweepWorldcup2014.setGame1ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1ScoreOpp()));


        _SweepWorldcup2014.setGame2(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2()));


        _SweepWorldcup2014.setGame2Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2Score()));


        _SweepWorldcup2014.setGame2ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2ScoreOpp()));


        _SweepWorldcup2014.setGame3(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3()));


        _SweepWorldcup2014.setGame3Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3Score()));


        _SweepWorldcup2014.setGame3ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3ScoreOpp()));


        _SweepWorldcup2014.setAdvance(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getAdvance()));


        _SweepWorldcup2014.setTeam16A1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16A1()));


        _SweepWorldcup2014.setTeam16A2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16A2()));


        _SweepWorldcup2014.setTeam16B1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16B1()));


        _SweepWorldcup2014.setTeam16B2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16B2()));


        _SweepWorldcup2014.setTeam16C1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16C1()));


        _SweepWorldcup2014.setTeam16C2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16C2()));


        _SweepWorldcup2014.setTeam16D1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16D1()));


        _SweepWorldcup2014.setTeam16D2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16D2()));


        _SweepWorldcup2014.setTeam16E1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16E1()));


        _SweepWorldcup2014.setTeam16E2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16E2()));


        _SweepWorldcup2014.setTeam16F1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16F1()));


        _SweepWorldcup2014.setTeam16F2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16F2()));


        _SweepWorldcup2014.setTeam16G1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16G1()));


        _SweepWorldcup2014.setTeam16G2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16G2()));


        _SweepWorldcup2014.setTeam16H1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16H1()));


        _SweepWorldcup2014.setTeam16H2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16H2()));


        _SweepWorldcup2014.setQuarterFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal1()));


        _SweepWorldcup2014.setQuarterFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal2()));


        _SweepWorldcup2014.setQuarterFinal3(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal3()));


        _SweepWorldcup2014.setQuarterFinal4(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal4()));


        _SweepWorldcup2014.setQuarterFinal5(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal5()));


        _SweepWorldcup2014.setQuarterFinal6(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal6()));


        _SweepWorldcup2014.setQuarterFinal7(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal7()));


        _SweepWorldcup2014.setQuarterFinal8(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal8()));


        _SweepWorldcup2014.setSemiFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal1()));


        _SweepWorldcup2014.setSemiFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal2()));


        _SweepWorldcup2014.setSemiFinal3(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal3()));


        _SweepWorldcup2014.setSemiFinal4(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal4()));


        _SweepWorldcup2014.setFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getFinal1()));


        _SweepWorldcup2014.setFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getFinal2()));


        _SweepWorldcup2014.setChampion(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getChampion()));


        _SweepWorldcup2014.setFinalScoreWin(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getFinalScoreWin()));


        _SweepWorldcup2014.setFinalScoreLose(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getFinalScoreLose()));




        _SweepWorldcup2014.setTimeUpdated(WebParamUtil.getTimestampValue(_SweepWorldcup2014Form.getTimeUpdated()));

        _SweepWorldcup2014.setTimeUpdated(new TimeNow());


        m_actionExtent.beforeUpdate(request, response, _SweepWorldcup2014);
        m_ds.update(_SweepWorldcup2014);
        m_actionExtent.afterUpdate(request, response, _SweepWorldcup2014);
        m_logger.debug("After update " + SweepWorldcup2014DS.objectToString(_SweepWorldcup2014));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) dataObject;

        if (!isMissing(request.getParameter("player"))) {
            m_logger.debug("updating param player from " +_SweepWorldcup2014.getPlayer() + "->" + request.getParameter("player"));
            _SweepWorldcup2014.setPlayer(WebParamUtil.getStringValue(request.getParameter("player")));

        }
        if (!isMissing(request.getParameter("game1"))) {
            m_logger.debug("updating param game1 from " +_SweepWorldcup2014.getGame1() + "->" + request.getParameter("game1"));
            _SweepWorldcup2014.setGame1(WebParamUtil.getIntegerValue(request.getParameter("game1")));

        }
        if (!isMissing(request.getParameter("game1Score"))) {
            m_logger.debug("updating param game1Score from " +_SweepWorldcup2014.getGame1Score() + "->" + request.getParameter("game1Score"));
            _SweepWorldcup2014.setGame1Score(WebParamUtil.getIntegerValue(request.getParameter("game1Score")));

        }
        if (!isMissing(request.getParameter("game1ScoreOpp"))) {
            m_logger.debug("updating param game1ScoreOpp from " +_SweepWorldcup2014.getGame1ScoreOpp() + "->" + request.getParameter("game1ScoreOpp"));
            _SweepWorldcup2014.setGame1ScoreOpp(WebParamUtil.getIntegerValue(request.getParameter("game1ScoreOpp")));

        }
        if (!isMissing(request.getParameter("game2"))) {
            m_logger.debug("updating param game2 from " +_SweepWorldcup2014.getGame2() + "->" + request.getParameter("game2"));
            _SweepWorldcup2014.setGame2(WebParamUtil.getIntegerValue(request.getParameter("game2")));

        }
        if (!isMissing(request.getParameter("game2Score"))) {
            m_logger.debug("updating param game2Score from " +_SweepWorldcup2014.getGame2Score() + "->" + request.getParameter("game2Score"));
            _SweepWorldcup2014.setGame2Score(WebParamUtil.getIntegerValue(request.getParameter("game2Score")));

        }
        if (!isMissing(request.getParameter("game2ScoreOpp"))) {
            m_logger.debug("updating param game2ScoreOpp from " +_SweepWorldcup2014.getGame2ScoreOpp() + "->" + request.getParameter("game2ScoreOpp"));
            _SweepWorldcup2014.setGame2ScoreOpp(WebParamUtil.getIntegerValue(request.getParameter("game2ScoreOpp")));

        }
        if (!isMissing(request.getParameter("game3"))) {
            m_logger.debug("updating param game3 from " +_SweepWorldcup2014.getGame3() + "->" + request.getParameter("game3"));
            _SweepWorldcup2014.setGame3(WebParamUtil.getIntegerValue(request.getParameter("game3")));

        }
        if (!isMissing(request.getParameter("game3Score"))) {
            m_logger.debug("updating param game3Score from " +_SweepWorldcup2014.getGame3Score() + "->" + request.getParameter("game3Score"));
            _SweepWorldcup2014.setGame3Score(WebParamUtil.getIntegerValue(request.getParameter("game3Score")));

        }
        if (!isMissing(request.getParameter("game3ScoreOpp"))) {
            m_logger.debug("updating param game3ScoreOpp from " +_SweepWorldcup2014.getGame3ScoreOpp() + "->" + request.getParameter("game3ScoreOpp"));
            _SweepWorldcup2014.setGame3ScoreOpp(WebParamUtil.getIntegerValue(request.getParameter("game3ScoreOpp")));

        }
        if (!isMissing(request.getParameter("advance"))) {
            m_logger.debug("updating param advance from " +_SweepWorldcup2014.getAdvance() + "->" + request.getParameter("advance"));
            _SweepWorldcup2014.setAdvance(WebParamUtil.getIntegerValue(request.getParameter("advance")));

        }
        if (!isMissing(request.getParameter("team16A1"))) {
            m_logger.debug("updating param team16A1 from " +_SweepWorldcup2014.getTeam16A1() + "->" + request.getParameter("team16A1"));
            _SweepWorldcup2014.setTeam16A1(WebParamUtil.getStringValue(request.getParameter("team16A1")));

        }
        if (!isMissing(request.getParameter("team16A2"))) {
            m_logger.debug("updating param team16A2 from " +_SweepWorldcup2014.getTeam16A2() + "->" + request.getParameter("team16A2"));
            _SweepWorldcup2014.setTeam16A2(WebParamUtil.getStringValue(request.getParameter("team16A2")));

        }
        if (!isMissing(request.getParameter("team16B1"))) {
            m_logger.debug("updating param team16B1 from " +_SweepWorldcup2014.getTeam16B1() + "->" + request.getParameter("team16B1"));
            _SweepWorldcup2014.setTeam16B1(WebParamUtil.getStringValue(request.getParameter("team16B1")));

        }
        if (!isMissing(request.getParameter("team16B2"))) {
            m_logger.debug("updating param team16B2 from " +_SweepWorldcup2014.getTeam16B2() + "->" + request.getParameter("team16B2"));
            _SweepWorldcup2014.setTeam16B2(WebParamUtil.getStringValue(request.getParameter("team16B2")));

        }
        if (!isMissing(request.getParameter("team16C1"))) {
            m_logger.debug("updating param team16C1 from " +_SweepWorldcup2014.getTeam16C1() + "->" + request.getParameter("team16C1"));
            _SweepWorldcup2014.setTeam16C1(WebParamUtil.getStringValue(request.getParameter("team16C1")));

        }
        if (!isMissing(request.getParameter("team16C2"))) {
            m_logger.debug("updating param team16C2 from " +_SweepWorldcup2014.getTeam16C2() + "->" + request.getParameter("team16C2"));
            _SweepWorldcup2014.setTeam16C2(WebParamUtil.getStringValue(request.getParameter("team16C2")));

        }
        if (!isMissing(request.getParameter("team16D1"))) {
            m_logger.debug("updating param team16D1 from " +_SweepWorldcup2014.getTeam16D1() + "->" + request.getParameter("team16D1"));
            _SweepWorldcup2014.setTeam16D1(WebParamUtil.getStringValue(request.getParameter("team16D1")));

        }
        if (!isMissing(request.getParameter("team16D2"))) {
            m_logger.debug("updating param team16D2 from " +_SweepWorldcup2014.getTeam16D2() + "->" + request.getParameter("team16D2"));
            _SweepWorldcup2014.setTeam16D2(WebParamUtil.getStringValue(request.getParameter("team16D2")));

        }
        if (!isMissing(request.getParameter("team16E1"))) {
            m_logger.debug("updating param team16E1 from " +_SweepWorldcup2014.getTeam16E1() + "->" + request.getParameter("team16E1"));
            _SweepWorldcup2014.setTeam16E1(WebParamUtil.getStringValue(request.getParameter("team16E1")));

        }
        if (!isMissing(request.getParameter("team16E2"))) {
            m_logger.debug("updating param team16E2 from " +_SweepWorldcup2014.getTeam16E2() + "->" + request.getParameter("team16E2"));
            _SweepWorldcup2014.setTeam16E2(WebParamUtil.getStringValue(request.getParameter("team16E2")));

        }
        if (!isMissing(request.getParameter("team16F1"))) {
            m_logger.debug("updating param team16F1 from " +_SweepWorldcup2014.getTeam16F1() + "->" + request.getParameter("team16F1"));
            _SweepWorldcup2014.setTeam16F1(WebParamUtil.getStringValue(request.getParameter("team16F1")));

        }
        if (!isMissing(request.getParameter("team16F2"))) {
            m_logger.debug("updating param team16F2 from " +_SweepWorldcup2014.getTeam16F2() + "->" + request.getParameter("team16F2"));
            _SweepWorldcup2014.setTeam16F2(WebParamUtil.getStringValue(request.getParameter("team16F2")));

        }
        if (!isMissing(request.getParameter("team16G1"))) {
            m_logger.debug("updating param team16G1 from " +_SweepWorldcup2014.getTeam16G1() + "->" + request.getParameter("team16G1"));
            _SweepWorldcup2014.setTeam16G1(WebParamUtil.getStringValue(request.getParameter("team16G1")));

        }
        if (!isMissing(request.getParameter("team16G2"))) {
            m_logger.debug("updating param team16G2 from " +_SweepWorldcup2014.getTeam16G2() + "->" + request.getParameter("team16G2"));
            _SweepWorldcup2014.setTeam16G2(WebParamUtil.getStringValue(request.getParameter("team16G2")));

        }
        if (!isMissing(request.getParameter("team16H1"))) {
            m_logger.debug("updating param team16H1 from " +_SweepWorldcup2014.getTeam16H1() + "->" + request.getParameter("team16H1"));
            _SweepWorldcup2014.setTeam16H1(WebParamUtil.getStringValue(request.getParameter("team16H1")));

        }
        if (!isMissing(request.getParameter("team16H2"))) {
            m_logger.debug("updating param team16H2 from " +_SweepWorldcup2014.getTeam16H2() + "->" + request.getParameter("team16H2"));
            _SweepWorldcup2014.setTeam16H2(WebParamUtil.getStringValue(request.getParameter("team16H2")));

        }
        if (!isMissing(request.getParameter("quarterFinal1"))) {
            m_logger.debug("updating param quarterFinal1 from " +_SweepWorldcup2014.getQuarterFinal1() + "->" + request.getParameter("quarterFinal1"));
            _SweepWorldcup2014.setQuarterFinal1(WebParamUtil.getStringValue(request.getParameter("quarterFinal1")));

        }
        if (!isMissing(request.getParameter("quarterFinal2"))) {
            m_logger.debug("updating param quarterFinal2 from " +_SweepWorldcup2014.getQuarterFinal2() + "->" + request.getParameter("quarterFinal2"));
            _SweepWorldcup2014.setQuarterFinal2(WebParamUtil.getStringValue(request.getParameter("quarterFinal2")));

        }
        if (!isMissing(request.getParameter("quarterFinal3"))) {
            m_logger.debug("updating param quarterFinal3 from " +_SweepWorldcup2014.getQuarterFinal3() + "->" + request.getParameter("quarterFinal3"));
            _SweepWorldcup2014.setQuarterFinal3(WebParamUtil.getStringValue(request.getParameter("quarterFinal3")));

        }
        if (!isMissing(request.getParameter("quarterFinal4"))) {
            m_logger.debug("updating param quarterFinal4 from " +_SweepWorldcup2014.getQuarterFinal4() + "->" + request.getParameter("quarterFinal4"));
            _SweepWorldcup2014.setQuarterFinal4(WebParamUtil.getStringValue(request.getParameter("quarterFinal4")));

        }
        if (!isMissing(request.getParameter("quarterFinal5"))) {
            m_logger.debug("updating param quarterFinal5 from " +_SweepWorldcup2014.getQuarterFinal5() + "->" + request.getParameter("quarterFinal5"));
            _SweepWorldcup2014.setQuarterFinal5(WebParamUtil.getStringValue(request.getParameter("quarterFinal5")));

        }
        if (!isMissing(request.getParameter("quarterFinal6"))) {
            m_logger.debug("updating param quarterFinal6 from " +_SweepWorldcup2014.getQuarterFinal6() + "->" + request.getParameter("quarterFinal6"));
            _SweepWorldcup2014.setQuarterFinal6(WebParamUtil.getStringValue(request.getParameter("quarterFinal6")));

        }
        if (!isMissing(request.getParameter("quarterFinal7"))) {
            m_logger.debug("updating param quarterFinal7 from " +_SweepWorldcup2014.getQuarterFinal7() + "->" + request.getParameter("quarterFinal7"));
            _SweepWorldcup2014.setQuarterFinal7(WebParamUtil.getStringValue(request.getParameter("quarterFinal7")));

        }
        if (!isMissing(request.getParameter("quarterFinal8"))) {
            m_logger.debug("updating param quarterFinal8 from " +_SweepWorldcup2014.getQuarterFinal8() + "->" + request.getParameter("quarterFinal8"));
            _SweepWorldcup2014.setQuarterFinal8(WebParamUtil.getStringValue(request.getParameter("quarterFinal8")));

        }
        if (!isMissing(request.getParameter("semiFinal1"))) {
            m_logger.debug("updating param semiFinal1 from " +_SweepWorldcup2014.getSemiFinal1() + "->" + request.getParameter("semiFinal1"));
            _SweepWorldcup2014.setSemiFinal1(WebParamUtil.getStringValue(request.getParameter("semiFinal1")));

        }
        if (!isMissing(request.getParameter("semiFinal2"))) {
            m_logger.debug("updating param semiFinal2 from " +_SweepWorldcup2014.getSemiFinal2() + "->" + request.getParameter("semiFinal2"));
            _SweepWorldcup2014.setSemiFinal2(WebParamUtil.getStringValue(request.getParameter("semiFinal2")));

        }
        if (!isMissing(request.getParameter("semiFinal3"))) {
            m_logger.debug("updating param semiFinal3 from " +_SweepWorldcup2014.getSemiFinal3() + "->" + request.getParameter("semiFinal3"));
            _SweepWorldcup2014.setSemiFinal3(WebParamUtil.getStringValue(request.getParameter("semiFinal3")));

        }
        if (!isMissing(request.getParameter("semiFinal4"))) {
            m_logger.debug("updating param semiFinal4 from " +_SweepWorldcup2014.getSemiFinal4() + "->" + request.getParameter("semiFinal4"));
            _SweepWorldcup2014.setSemiFinal4(WebParamUtil.getStringValue(request.getParameter("semiFinal4")));

        }
        if (!isMissing(request.getParameter("final1"))) {
            m_logger.debug("updating param final1 from " +_SweepWorldcup2014.getFinal1() + "->" + request.getParameter("final1"));
            _SweepWorldcup2014.setFinal1(WebParamUtil.getStringValue(request.getParameter("final1")));

        }
        if (!isMissing(request.getParameter("final2"))) {
            m_logger.debug("updating param final2 from " +_SweepWorldcup2014.getFinal2() + "->" + request.getParameter("final2"));
            _SweepWorldcup2014.setFinal2(WebParamUtil.getStringValue(request.getParameter("final2")));

        }
        if (!isMissing(request.getParameter("champion"))) {
            m_logger.debug("updating param champion from " +_SweepWorldcup2014.getChampion() + "->" + request.getParameter("champion"));
            _SweepWorldcup2014.setChampion(WebParamUtil.getStringValue(request.getParameter("champion")));

        }
        if (!isMissing(request.getParameter("finalScoreWin"))) {
            m_logger.debug("updating param finalScoreWin from " +_SweepWorldcup2014.getFinalScoreWin() + "->" + request.getParameter("finalScoreWin"));
            _SweepWorldcup2014.setFinalScoreWin(WebParamUtil.getIntegerValue(request.getParameter("finalScoreWin")));

        }
        if (!isMissing(request.getParameter("finalScoreLose"))) {
            m_logger.debug("updating param finalScoreLose from " +_SweepWorldcup2014.getFinalScoreLose() + "->" + request.getParameter("finalScoreLose"));
            _SweepWorldcup2014.setFinalScoreLose(WebParamUtil.getIntegerValue(request.getParameter("finalScoreLose")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SweepWorldcup2014.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SweepWorldcup2014.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SweepWorldcup2014.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SweepWorldcup2014.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SweepWorldcup2014.setTimeUpdated(new TimeNow());
        }

        m_actionExtent.beforeUpdate(request, response, _SweepWorldcup2014);
        m_ds.update(_SweepWorldcup2014);
        m_actionExtent.afterUpdate(request, response, _SweepWorldcup2014);
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) throws Exception{

        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) dataObject;

        if (!isMissing(request.getParameter("player"))) {
            m_logger.debug("updating param player from " +_SweepWorldcup2014.getPlayer() + "->" + request.getParameter("player"));
            _SweepWorldcup2014.setPlayer(WebParamUtil.getStringValue(request.getParameter("player")));

        }
        if (!isMissing(request.getParameter("game1"))) {
            m_logger.debug("updating param game1 from " +_SweepWorldcup2014.getGame1() + "->" + request.getParameter("game1"));
            _SweepWorldcup2014.setGame1(WebParamUtil.getIntegerValue(request.getParameter("game1")));

        }
        if (!isMissing(request.getParameter("game1Score"))) {
            m_logger.debug("updating param game1Score from " +_SweepWorldcup2014.getGame1Score() + "->" + request.getParameter("game1Score"));
            _SweepWorldcup2014.setGame1Score(WebParamUtil.getIntegerValue(request.getParameter("game1Score")));

        }
        if (!isMissing(request.getParameter("game1ScoreOpp"))) {
            m_logger.debug("updating param game1ScoreOpp from " +_SweepWorldcup2014.getGame1ScoreOpp() + "->" + request.getParameter("game1ScoreOpp"));
            _SweepWorldcup2014.setGame1ScoreOpp(WebParamUtil.getIntegerValue(request.getParameter("game1ScoreOpp")));

        }
        if (!isMissing(request.getParameter("game2"))) {
            m_logger.debug("updating param game2 from " +_SweepWorldcup2014.getGame2() + "->" + request.getParameter("game2"));
            _SweepWorldcup2014.setGame2(WebParamUtil.getIntegerValue(request.getParameter("game2")));

        }
        if (!isMissing(request.getParameter("game2Score"))) {
            m_logger.debug("updating param game2Score from " +_SweepWorldcup2014.getGame2Score() + "->" + request.getParameter("game2Score"));
            _SweepWorldcup2014.setGame2Score(WebParamUtil.getIntegerValue(request.getParameter("game2Score")));

        }
        if (!isMissing(request.getParameter("game2ScoreOpp"))) {
            m_logger.debug("updating param game2ScoreOpp from " +_SweepWorldcup2014.getGame2ScoreOpp() + "->" + request.getParameter("game2ScoreOpp"));
            _SweepWorldcup2014.setGame2ScoreOpp(WebParamUtil.getIntegerValue(request.getParameter("game2ScoreOpp")));

        }
        if (!isMissing(request.getParameter("game3"))) {
            m_logger.debug("updating param game3 from " +_SweepWorldcup2014.getGame3() + "->" + request.getParameter("game3"));
            _SweepWorldcup2014.setGame3(WebParamUtil.getIntegerValue(request.getParameter("game3")));

        }
        if (!isMissing(request.getParameter("game3Score"))) {
            m_logger.debug("updating param game3Score from " +_SweepWorldcup2014.getGame3Score() + "->" + request.getParameter("game3Score"));
            _SweepWorldcup2014.setGame3Score(WebParamUtil.getIntegerValue(request.getParameter("game3Score")));

        }
        if (!isMissing(request.getParameter("game3ScoreOpp"))) {
            m_logger.debug("updating param game3ScoreOpp from " +_SweepWorldcup2014.getGame3ScoreOpp() + "->" + request.getParameter("game3ScoreOpp"));
            _SweepWorldcup2014.setGame3ScoreOpp(WebParamUtil.getIntegerValue(request.getParameter("game3ScoreOpp")));

        }
        if (!isMissing(request.getParameter("advance"))) {
            m_logger.debug("updating param advance from " +_SweepWorldcup2014.getAdvance() + "->" + request.getParameter("advance"));
            _SweepWorldcup2014.setAdvance(WebParamUtil.getIntegerValue(request.getParameter("advance")));

        }
        if (!isMissing(request.getParameter("team16A1"))) {
            m_logger.debug("updating param team16A1 from " +_SweepWorldcup2014.getTeam16A1() + "->" + request.getParameter("team16A1"));
            _SweepWorldcup2014.setTeam16A1(WebParamUtil.getStringValue(request.getParameter("team16A1")));

        }
        if (!isMissing(request.getParameter("team16A2"))) {
            m_logger.debug("updating param team16A2 from " +_SweepWorldcup2014.getTeam16A2() + "->" + request.getParameter("team16A2"));
            _SweepWorldcup2014.setTeam16A2(WebParamUtil.getStringValue(request.getParameter("team16A2")));

        }
        if (!isMissing(request.getParameter("team16B1"))) {
            m_logger.debug("updating param team16B1 from " +_SweepWorldcup2014.getTeam16B1() + "->" + request.getParameter("team16B1"));
            _SweepWorldcup2014.setTeam16B1(WebParamUtil.getStringValue(request.getParameter("team16B1")));

        }
        if (!isMissing(request.getParameter("team16B2"))) {
            m_logger.debug("updating param team16B2 from " +_SweepWorldcup2014.getTeam16B2() + "->" + request.getParameter("team16B2"));
            _SweepWorldcup2014.setTeam16B2(WebParamUtil.getStringValue(request.getParameter("team16B2")));

        }
        if (!isMissing(request.getParameter("team16C1"))) {
            m_logger.debug("updating param team16C1 from " +_SweepWorldcup2014.getTeam16C1() + "->" + request.getParameter("team16C1"));
            _SweepWorldcup2014.setTeam16C1(WebParamUtil.getStringValue(request.getParameter("team16C1")));

        }
        if (!isMissing(request.getParameter("team16C2"))) {
            m_logger.debug("updating param team16C2 from " +_SweepWorldcup2014.getTeam16C2() + "->" + request.getParameter("team16C2"));
            _SweepWorldcup2014.setTeam16C2(WebParamUtil.getStringValue(request.getParameter("team16C2")));

        }
        if (!isMissing(request.getParameter("team16D1"))) {
            m_logger.debug("updating param team16D1 from " +_SweepWorldcup2014.getTeam16D1() + "->" + request.getParameter("team16D1"));
            _SweepWorldcup2014.setTeam16D1(WebParamUtil.getStringValue(request.getParameter("team16D1")));

        }
        if (!isMissing(request.getParameter("team16D2"))) {
            m_logger.debug("updating param team16D2 from " +_SweepWorldcup2014.getTeam16D2() + "->" + request.getParameter("team16D2"));
            _SweepWorldcup2014.setTeam16D2(WebParamUtil.getStringValue(request.getParameter("team16D2")));

        }
        if (!isMissing(request.getParameter("team16E1"))) {
            m_logger.debug("updating param team16E1 from " +_SweepWorldcup2014.getTeam16E1() + "->" + request.getParameter("team16E1"));
            _SweepWorldcup2014.setTeam16E1(WebParamUtil.getStringValue(request.getParameter("team16E1")));

        }
        if (!isMissing(request.getParameter("team16E2"))) {
            m_logger.debug("updating param team16E2 from " +_SweepWorldcup2014.getTeam16E2() + "->" + request.getParameter("team16E2"));
            _SweepWorldcup2014.setTeam16E2(WebParamUtil.getStringValue(request.getParameter("team16E2")));

        }
        if (!isMissing(request.getParameter("team16F1"))) {
            m_logger.debug("updating param team16F1 from " +_SweepWorldcup2014.getTeam16F1() + "->" + request.getParameter("team16F1"));
            _SweepWorldcup2014.setTeam16F1(WebParamUtil.getStringValue(request.getParameter("team16F1")));

        }
        if (!isMissing(request.getParameter("team16F2"))) {
            m_logger.debug("updating param team16F2 from " +_SweepWorldcup2014.getTeam16F2() + "->" + request.getParameter("team16F2"));
            _SweepWorldcup2014.setTeam16F2(WebParamUtil.getStringValue(request.getParameter("team16F2")));

        }
        if (!isMissing(request.getParameter("team16G1"))) {
            m_logger.debug("updating param team16G1 from " +_SweepWorldcup2014.getTeam16G1() + "->" + request.getParameter("team16G1"));
            _SweepWorldcup2014.setTeam16G1(WebParamUtil.getStringValue(request.getParameter("team16G1")));

        }
        if (!isMissing(request.getParameter("team16G2"))) {
            m_logger.debug("updating param team16G2 from " +_SweepWorldcup2014.getTeam16G2() + "->" + request.getParameter("team16G2"));
            _SweepWorldcup2014.setTeam16G2(WebParamUtil.getStringValue(request.getParameter("team16G2")));

        }
        if (!isMissing(request.getParameter("team16H1"))) {
            m_logger.debug("updating param team16H1 from " +_SweepWorldcup2014.getTeam16H1() + "->" + request.getParameter("team16H1"));
            _SweepWorldcup2014.setTeam16H1(WebParamUtil.getStringValue(request.getParameter("team16H1")));

        }
        if (!isMissing(request.getParameter("team16H2"))) {
            m_logger.debug("updating param team16H2 from " +_SweepWorldcup2014.getTeam16H2() + "->" + request.getParameter("team16H2"));
            _SweepWorldcup2014.setTeam16H2(WebParamUtil.getStringValue(request.getParameter("team16H2")));

        }
        if (!isMissing(request.getParameter("quarterFinal1"))) {
            m_logger.debug("updating param quarterFinal1 from " +_SweepWorldcup2014.getQuarterFinal1() + "->" + request.getParameter("quarterFinal1"));
            _SweepWorldcup2014.setQuarterFinal1(WebParamUtil.getStringValue(request.getParameter("quarterFinal1")));

        }
        if (!isMissing(request.getParameter("quarterFinal2"))) {
            m_logger.debug("updating param quarterFinal2 from " +_SweepWorldcup2014.getQuarterFinal2() + "->" + request.getParameter("quarterFinal2"));
            _SweepWorldcup2014.setQuarterFinal2(WebParamUtil.getStringValue(request.getParameter("quarterFinal2")));

        }
        if (!isMissing(request.getParameter("quarterFinal3"))) {
            m_logger.debug("updating param quarterFinal3 from " +_SweepWorldcup2014.getQuarterFinal3() + "->" + request.getParameter("quarterFinal3"));
            _SweepWorldcup2014.setQuarterFinal3(WebParamUtil.getStringValue(request.getParameter("quarterFinal3")));

        }
        if (!isMissing(request.getParameter("quarterFinal4"))) {
            m_logger.debug("updating param quarterFinal4 from " +_SweepWorldcup2014.getQuarterFinal4() + "->" + request.getParameter("quarterFinal4"));
            _SweepWorldcup2014.setQuarterFinal4(WebParamUtil.getStringValue(request.getParameter("quarterFinal4")));

        }
        if (!isMissing(request.getParameter("quarterFinal5"))) {
            m_logger.debug("updating param quarterFinal5 from " +_SweepWorldcup2014.getQuarterFinal5() + "->" + request.getParameter("quarterFinal5"));
            _SweepWorldcup2014.setQuarterFinal5(WebParamUtil.getStringValue(request.getParameter("quarterFinal5")));

        }
        if (!isMissing(request.getParameter("quarterFinal6"))) {
            m_logger.debug("updating param quarterFinal6 from " +_SweepWorldcup2014.getQuarterFinal6() + "->" + request.getParameter("quarterFinal6"));
            _SweepWorldcup2014.setQuarterFinal6(WebParamUtil.getStringValue(request.getParameter("quarterFinal6")));

        }
        if (!isMissing(request.getParameter("quarterFinal7"))) {
            m_logger.debug("updating param quarterFinal7 from " +_SweepWorldcup2014.getQuarterFinal7() + "->" + request.getParameter("quarterFinal7"));
            _SweepWorldcup2014.setQuarterFinal7(WebParamUtil.getStringValue(request.getParameter("quarterFinal7")));

        }
        if (!isMissing(request.getParameter("quarterFinal8"))) {
            m_logger.debug("updating param quarterFinal8 from " +_SweepWorldcup2014.getQuarterFinal8() + "->" + request.getParameter("quarterFinal8"));
            _SweepWorldcup2014.setQuarterFinal8(WebParamUtil.getStringValue(request.getParameter("quarterFinal8")));

        }
        if (!isMissing(request.getParameter("semiFinal1"))) {
            m_logger.debug("updating param semiFinal1 from " +_SweepWorldcup2014.getSemiFinal1() + "->" + request.getParameter("semiFinal1"));
            _SweepWorldcup2014.setSemiFinal1(WebParamUtil.getStringValue(request.getParameter("semiFinal1")));

        }
        if (!isMissing(request.getParameter("semiFinal2"))) {
            m_logger.debug("updating param semiFinal2 from " +_SweepWorldcup2014.getSemiFinal2() + "->" + request.getParameter("semiFinal2"));
            _SweepWorldcup2014.setSemiFinal2(WebParamUtil.getStringValue(request.getParameter("semiFinal2")));

        }
        if (!isMissing(request.getParameter("semiFinal3"))) {
            m_logger.debug("updating param semiFinal3 from " +_SweepWorldcup2014.getSemiFinal3() + "->" + request.getParameter("semiFinal3"));
            _SweepWorldcup2014.setSemiFinal3(WebParamUtil.getStringValue(request.getParameter("semiFinal3")));

        }
        if (!isMissing(request.getParameter("semiFinal4"))) {
            m_logger.debug("updating param semiFinal4 from " +_SweepWorldcup2014.getSemiFinal4() + "->" + request.getParameter("semiFinal4"));
            _SweepWorldcup2014.setSemiFinal4(WebParamUtil.getStringValue(request.getParameter("semiFinal4")));

        }
        if (!isMissing(request.getParameter("final1"))) {
            m_logger.debug("updating param final1 from " +_SweepWorldcup2014.getFinal1() + "->" + request.getParameter("final1"));
            _SweepWorldcup2014.setFinal1(WebParamUtil.getStringValue(request.getParameter("final1")));

        }
        if (!isMissing(request.getParameter("final2"))) {
            m_logger.debug("updating param final2 from " +_SweepWorldcup2014.getFinal2() + "->" + request.getParameter("final2"));
            _SweepWorldcup2014.setFinal2(WebParamUtil.getStringValue(request.getParameter("final2")));

        }
        if (!isMissing(request.getParameter("champion"))) {
            m_logger.debug("updating param champion from " +_SweepWorldcup2014.getChampion() + "->" + request.getParameter("champion"));
            _SweepWorldcup2014.setChampion(WebParamUtil.getStringValue(request.getParameter("champion")));

        }
        if (!isMissing(request.getParameter("finalScoreWin"))) {
            m_logger.debug("updating param finalScoreWin from " +_SweepWorldcup2014.getFinalScoreWin() + "->" + request.getParameter("finalScoreWin"));
            _SweepWorldcup2014.setFinalScoreWin(WebParamUtil.getIntegerValue(request.getParameter("finalScoreWin")));

        }
        if (!isMissing(request.getParameter("finalScoreLose"))) {
            m_logger.debug("updating param finalScoreLose from " +_SweepWorldcup2014.getFinalScoreLose() + "->" + request.getParameter("finalScoreLose"));
            _SweepWorldcup2014.setFinalScoreLose(WebParamUtil.getIntegerValue(request.getParameter("finalScoreLose")));

        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SweepWorldcup2014.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SweepWorldcup2014.setTimeCreated(WebParamUtil.getTimestampValue(request.getParameter("timeCreated")));

        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SweepWorldcup2014.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SweepWorldcup2014.setTimeUpdated(WebParamUtil.getTimestampValue(request.getParameter("timeUpdated")));

        _SweepWorldcup2014.setTimeUpdated(new TimeNow());
        }
    }

    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm form,  AutositeDataObject dataObject) throws Exception{

        SweepWorldcup2014Form _SweepWorldcup2014Form = (SweepWorldcup2014Form) form;
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) dataObject;

            _SweepWorldcup2014.setPlayer(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getPlayer()));
            m_logger.debug("setting Player=" +_SweepWorldcup2014Form.getPlayer());


            _SweepWorldcup2014.setGame1(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1()));
            m_logger.debug("setting Game1=" +_SweepWorldcup2014Form.getGame1());


            _SweepWorldcup2014.setGame1Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1Score()));
            m_logger.debug("setting Game1Score=" +_SweepWorldcup2014Form.getGame1Score());


            _SweepWorldcup2014.setGame1ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame1ScoreOpp()));
            m_logger.debug("setting Game1ScoreOpp=" +_SweepWorldcup2014Form.getGame1ScoreOpp());


            _SweepWorldcup2014.setGame2(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2()));
            m_logger.debug("setting Game2=" +_SweepWorldcup2014Form.getGame2());


            _SweepWorldcup2014.setGame2Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2Score()));
            m_logger.debug("setting Game2Score=" +_SweepWorldcup2014Form.getGame2Score());


            _SweepWorldcup2014.setGame2ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame2ScoreOpp()));
            m_logger.debug("setting Game2ScoreOpp=" +_SweepWorldcup2014Form.getGame2ScoreOpp());


            _SweepWorldcup2014.setGame3(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3()));
            m_logger.debug("setting Game3=" +_SweepWorldcup2014Form.getGame3());


            _SweepWorldcup2014.setGame3Score(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3Score()));
            m_logger.debug("setting Game3Score=" +_SweepWorldcup2014Form.getGame3Score());


            _SweepWorldcup2014.setGame3ScoreOpp(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getGame3ScoreOpp()));
            m_logger.debug("setting Game3ScoreOpp=" +_SweepWorldcup2014Form.getGame3ScoreOpp());


            _SweepWorldcup2014.setAdvance(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getAdvance()));
            m_logger.debug("setting Advance=" +_SweepWorldcup2014Form.getAdvance());


            _SweepWorldcup2014.setTeam16A1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16A1()));
            m_logger.debug("setting Team16A1=" +_SweepWorldcup2014Form.getTeam16A1());


            _SweepWorldcup2014.setTeam16A2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16A2()));
            m_logger.debug("setting Team16A2=" +_SweepWorldcup2014Form.getTeam16A2());


            _SweepWorldcup2014.setTeam16B1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16B1()));
            m_logger.debug("setting Team16B1=" +_SweepWorldcup2014Form.getTeam16B1());


            _SweepWorldcup2014.setTeam16B2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16B2()));
            m_logger.debug("setting Team16B2=" +_SweepWorldcup2014Form.getTeam16B2());


            _SweepWorldcup2014.setTeam16C1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16C1()));
            m_logger.debug("setting Team16C1=" +_SweepWorldcup2014Form.getTeam16C1());


            _SweepWorldcup2014.setTeam16C2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16C2()));
            m_logger.debug("setting Team16C2=" +_SweepWorldcup2014Form.getTeam16C2());


            _SweepWorldcup2014.setTeam16D1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16D1()));
            m_logger.debug("setting Team16D1=" +_SweepWorldcup2014Form.getTeam16D1());


            _SweepWorldcup2014.setTeam16D2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16D2()));
            m_logger.debug("setting Team16D2=" +_SweepWorldcup2014Form.getTeam16D2());


            _SweepWorldcup2014.setTeam16E1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16E1()));
            m_logger.debug("setting Team16E1=" +_SweepWorldcup2014Form.getTeam16E1());


            _SweepWorldcup2014.setTeam16E2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16E2()));
            m_logger.debug("setting Team16E2=" +_SweepWorldcup2014Form.getTeam16E2());


            _SweepWorldcup2014.setTeam16F1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16F1()));
            m_logger.debug("setting Team16F1=" +_SweepWorldcup2014Form.getTeam16F1());


            _SweepWorldcup2014.setTeam16F2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16F2()));
            m_logger.debug("setting Team16F2=" +_SweepWorldcup2014Form.getTeam16F2());


            _SweepWorldcup2014.setTeam16G1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16G1()));
            m_logger.debug("setting Team16G1=" +_SweepWorldcup2014Form.getTeam16G1());


            _SweepWorldcup2014.setTeam16G2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16G2()));
            m_logger.debug("setting Team16G2=" +_SweepWorldcup2014Form.getTeam16G2());


            _SweepWorldcup2014.setTeam16H1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16H1()));
            m_logger.debug("setting Team16H1=" +_SweepWorldcup2014Form.getTeam16H1());


            _SweepWorldcup2014.setTeam16H2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getTeam16H2()));
            m_logger.debug("setting Team16H2=" +_SweepWorldcup2014Form.getTeam16H2());


            _SweepWorldcup2014.setQuarterFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal1()));
            m_logger.debug("setting QuarterFinal1=" +_SweepWorldcup2014Form.getQuarterFinal1());


            _SweepWorldcup2014.setQuarterFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal2()));
            m_logger.debug("setting QuarterFinal2=" +_SweepWorldcup2014Form.getQuarterFinal2());


            _SweepWorldcup2014.setQuarterFinal3(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal3()));
            m_logger.debug("setting QuarterFinal3=" +_SweepWorldcup2014Form.getQuarterFinal3());


            _SweepWorldcup2014.setQuarterFinal4(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal4()));
            m_logger.debug("setting QuarterFinal4=" +_SweepWorldcup2014Form.getQuarterFinal4());


            _SweepWorldcup2014.setQuarterFinal5(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal5()));
            m_logger.debug("setting QuarterFinal5=" +_SweepWorldcup2014Form.getQuarterFinal5());


            _SweepWorldcup2014.setQuarterFinal6(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal6()));
            m_logger.debug("setting QuarterFinal6=" +_SweepWorldcup2014Form.getQuarterFinal6());


            _SweepWorldcup2014.setQuarterFinal7(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal7()));
            m_logger.debug("setting QuarterFinal7=" +_SweepWorldcup2014Form.getQuarterFinal7());


            _SweepWorldcup2014.setQuarterFinal8(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getQuarterFinal8()));
            m_logger.debug("setting QuarterFinal8=" +_SweepWorldcup2014Form.getQuarterFinal8());


            _SweepWorldcup2014.setSemiFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal1()));
            m_logger.debug("setting SemiFinal1=" +_SweepWorldcup2014Form.getSemiFinal1());


            _SweepWorldcup2014.setSemiFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal2()));
            m_logger.debug("setting SemiFinal2=" +_SweepWorldcup2014Form.getSemiFinal2());


            _SweepWorldcup2014.setSemiFinal3(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal3()));
            m_logger.debug("setting SemiFinal3=" +_SweepWorldcup2014Form.getSemiFinal3());


            _SweepWorldcup2014.setSemiFinal4(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getSemiFinal4()));
            m_logger.debug("setting SemiFinal4=" +_SweepWorldcup2014Form.getSemiFinal4());


            _SweepWorldcup2014.setFinal1(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getFinal1()));
            m_logger.debug("setting Final1=" +_SweepWorldcup2014Form.getFinal1());


            _SweepWorldcup2014.setFinal2(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getFinal2()));
            m_logger.debug("setting Final2=" +_SweepWorldcup2014Form.getFinal2());


            _SweepWorldcup2014.setChampion(WebParamUtil.getStringValue(_SweepWorldcup2014Form.getChampion()));
            m_logger.debug("setting Champion=" +_SweepWorldcup2014Form.getChampion());


            _SweepWorldcup2014.setFinalScoreWin(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getFinalScoreWin()));
            m_logger.debug("setting FinalScoreWin=" +_SweepWorldcup2014Form.getFinalScoreWin());


            _SweepWorldcup2014.setFinalScoreLose(WebParamUtil.getIntegerValue(_SweepWorldcup2014Form.getFinalScoreLose()));
            m_logger.debug("setting FinalScoreLose=" +_SweepWorldcup2014Form.getFinalScoreLose());


            _SweepWorldcup2014.setTimeCreated(WebParamUtil.getTimestampValue(_SweepWorldcup2014Form.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SweepWorldcup2014Form.getTimeCreated());

        	_SweepWorldcup2014.setTimeCreated(new TimeNow());

            _SweepWorldcup2014.setTimeUpdated(WebParamUtil.getTimestampValue(_SweepWorldcup2014Form.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SweepWorldcup2014Form.getTimeUpdated());

        	_SweepWorldcup2014.setTimeUpdated(new TimeNow());


    }


    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject dataObject) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepWorldcup2014 _SweepWorldcup2014 = m_ds.getById(cid);
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) dataObject;

        if (!isMissing(request.getParameter("player"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getPlayer());
        }
        if (!isMissing(request.getParameter("game1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame1());
        }
        if (!isMissing(request.getParameter("game1Score"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame1Score());
        }
        if (!isMissing(request.getParameter("game1ScoreOpp"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame1ScoreOpp());
        }
        if (!isMissing(request.getParameter("game2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame2());
        }
        if (!isMissing(request.getParameter("game2Score"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame2Score());
        }
        if (!isMissing(request.getParameter("game2ScoreOpp"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame2ScoreOpp());
        }
        if (!isMissing(request.getParameter("game3"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame3());
        }
        if (!isMissing(request.getParameter("game3Score"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame3Score());
        }
        if (!isMissing(request.getParameter("game3ScoreOpp"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getGame3ScoreOpp());
        }
        if (!isMissing(request.getParameter("advance"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getAdvance());
        }
        if (!isMissing(request.getParameter("team16A1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16A1());
        }
        if (!isMissing(request.getParameter("team16A2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16A2());
        }
        if (!isMissing(request.getParameter("team16B1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16B1());
        }
        if (!isMissing(request.getParameter("team16B2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16B2());
        }
        if (!isMissing(request.getParameter("team16C1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16C1());
        }
        if (!isMissing(request.getParameter("team16C2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16C2());
        }
        if (!isMissing(request.getParameter("team16D1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16D1());
        }
        if (!isMissing(request.getParameter("team16D2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16D2());
        }
        if (!isMissing(request.getParameter("team16E1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16E1());
        }
        if (!isMissing(request.getParameter("team16E2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16E2());
        }
        if (!isMissing(request.getParameter("team16F1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16F1());
        }
        if (!isMissing(request.getParameter("team16F2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16F2());
        }
        if (!isMissing(request.getParameter("team16G1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16G1());
        }
        if (!isMissing(request.getParameter("team16G2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16G2());
        }
        if (!isMissing(request.getParameter("team16H1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16H1());
        }
        if (!isMissing(request.getParameter("team16H2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTeam16H2());
        }
        if (!isMissing(request.getParameter("quarterFinal1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal1());
        }
        if (!isMissing(request.getParameter("quarterFinal2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal2());
        }
        if (!isMissing(request.getParameter("quarterFinal3"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal3());
        }
        if (!isMissing(request.getParameter("quarterFinal4"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal4());
        }
        if (!isMissing(request.getParameter("quarterFinal5"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal5());
        }
        if (!isMissing(request.getParameter("quarterFinal6"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal6());
        }
        if (!isMissing(request.getParameter("quarterFinal7"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal7());
        }
        if (!isMissing(request.getParameter("quarterFinal8"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getQuarterFinal8());
        }
        if (!isMissing(request.getParameter("semiFinal1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getSemiFinal1());
        }
        if (!isMissing(request.getParameter("semiFinal2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getSemiFinal2());
        }
        if (!isMissing(request.getParameter("semiFinal3"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getSemiFinal3());
        }
        if (!isMissing(request.getParameter("semiFinal4"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getSemiFinal4());
        }
        if (!isMissing(request.getParameter("final1"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getFinal1());
        }
        if (!isMissing(request.getParameter("final2"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getFinal2());
        }
        if (!isMissing(request.getParameter("champion"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getChampion());
        }
        if (!isMissing(request.getParameter("finalScoreWin"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getFinalScoreWin());
        }
        if (!isMissing(request.getParameter("finalScoreLose"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getFinalScoreLose());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return  JtStringUtil.valueOf(_SweepWorldcup2014.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, SweepWorldcup2014 _SweepWorldcup2014) {
        if (fieldName == null || fieldName.equals("")|| _SweepWorldcup2014 == null) return null;
        
        if (fieldName.equals("player")) {
            return WebUtil.display(_SweepWorldcup2014.getPlayer());
        }
        if (fieldName.equals("game1")) {
            return WebUtil.display(_SweepWorldcup2014.getGame1());
        }
        if (fieldName.equals("game1Score")) {
            return WebUtil.display(_SweepWorldcup2014.getGame1Score());
        }
        if (fieldName.equals("game1ScoreOpp")) {
            return WebUtil.display(_SweepWorldcup2014.getGame1ScoreOpp());
        }
        if (fieldName.equals("game2")) {
            return WebUtil.display(_SweepWorldcup2014.getGame2());
        }
        if (fieldName.equals("game2Score")) {
            return WebUtil.display(_SweepWorldcup2014.getGame2Score());
        }
        if (fieldName.equals("game2ScoreOpp")) {
            return WebUtil.display(_SweepWorldcup2014.getGame2ScoreOpp());
        }
        if (fieldName.equals("game3")) {
            return WebUtil.display(_SweepWorldcup2014.getGame3());
        }
        if (fieldName.equals("game3Score")) {
            return WebUtil.display(_SweepWorldcup2014.getGame3Score());
        }
        if (fieldName.equals("game3ScoreOpp")) {
            return WebUtil.display(_SweepWorldcup2014.getGame3ScoreOpp());
        }
        if (fieldName.equals("advance")) {
            return WebUtil.display(_SweepWorldcup2014.getAdvance());
        }
        if (fieldName.equals("team16A1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16A1());
        }
        if (fieldName.equals("team16A2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16A2());
        }
        if (fieldName.equals("team16B1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16B1());
        }
        if (fieldName.equals("team16B2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16B2());
        }
        if (fieldName.equals("team16C1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16C1());
        }
        if (fieldName.equals("team16C2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16C2());
        }
        if (fieldName.equals("team16D1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16D1());
        }
        if (fieldName.equals("team16D2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16D2());
        }
        if (fieldName.equals("team16E1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16E1());
        }
        if (fieldName.equals("team16E2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16E2());
        }
        if (fieldName.equals("team16F1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16F1());
        }
        if (fieldName.equals("team16F2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16F2());
        }
        if (fieldName.equals("team16G1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16G1());
        }
        if (fieldName.equals("team16G2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16G2());
        }
        if (fieldName.equals("team16H1")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16H1());
        }
        if (fieldName.equals("team16H2")) {
            return WebUtil.display(_SweepWorldcup2014.getTeam16H2());
        }
        if (fieldName.equals("quarterFinal1")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal1());
        }
        if (fieldName.equals("quarterFinal2")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal2());
        }
        if (fieldName.equals("quarterFinal3")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal3());
        }
        if (fieldName.equals("quarterFinal4")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal4());
        }
        if (fieldName.equals("quarterFinal5")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal5());
        }
        if (fieldName.equals("quarterFinal6")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal6());
        }
        if (fieldName.equals("quarterFinal7")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal7());
        }
        if (fieldName.equals("quarterFinal8")) {
            return WebUtil.display(_SweepWorldcup2014.getQuarterFinal8());
        }
        if (fieldName.equals("semiFinal1")) {
            return WebUtil.display(_SweepWorldcup2014.getSemiFinal1());
        }
        if (fieldName.equals("semiFinal2")) {
            return WebUtil.display(_SweepWorldcup2014.getSemiFinal2());
        }
        if (fieldName.equals("semiFinal3")) {
            return WebUtil.display(_SweepWorldcup2014.getSemiFinal3());
        }
        if (fieldName.equals("semiFinal4")) {
            return WebUtil.display(_SweepWorldcup2014.getSemiFinal4());
        }
        if (fieldName.equals("final1")) {
            return WebUtil.display(_SweepWorldcup2014.getFinal1());
        }
        if (fieldName.equals("final2")) {
            return WebUtil.display(_SweepWorldcup2014.getFinal2());
        }
        if (fieldName.equals("champion")) {
            return WebUtil.display(_SweepWorldcup2014.getChampion());
        }
        if (fieldName.equals("finalScoreWin")) {
            return WebUtil.display(_SweepWorldcup2014.getFinalScoreWin());
        }
        if (fieldName.equals("finalScoreLose")) {
            return WebUtil.display(_SweepWorldcup2014.getFinalScoreLose());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_SweepWorldcup2014.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_SweepWorldcup2014.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(AutositeDataObject dataObject) throws Exception {
    }


   
    //this gets called when there is a follow-up action. Action may require data in a form. 
    // for example confirmTo
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        SweepWorldcup2014Form _SweepWorldcup2014Form = (SweepWorldcup2014Form) form;

		if(requestParams.containsKey("player"))
			_SweepWorldcup2014Form.setPlayer((String)requestParams.get("player"));
		if(requestParams.containsKey("game1"))
			_SweepWorldcup2014Form.setGame1((String)requestParams.get("game1"));
		if(requestParams.containsKey("game1Score"))
			_SweepWorldcup2014Form.setGame1Score((String)requestParams.get("game1Score"));
		if(requestParams.containsKey("game1ScoreOpp"))
			_SweepWorldcup2014Form.setGame1ScoreOpp((String)requestParams.get("game1ScoreOpp"));
		if(requestParams.containsKey("game2"))
			_SweepWorldcup2014Form.setGame2((String)requestParams.get("game2"));
		if(requestParams.containsKey("game2Score"))
			_SweepWorldcup2014Form.setGame2Score((String)requestParams.get("game2Score"));
		if(requestParams.containsKey("game2ScoreOpp"))
			_SweepWorldcup2014Form.setGame2ScoreOpp((String)requestParams.get("game2ScoreOpp"));
		if(requestParams.containsKey("game3"))
			_SweepWorldcup2014Form.setGame3((String)requestParams.get("game3"));
		if(requestParams.containsKey("game3Score"))
			_SweepWorldcup2014Form.setGame3Score((String)requestParams.get("game3Score"));
		if(requestParams.containsKey("game3ScoreOpp"))
			_SweepWorldcup2014Form.setGame3ScoreOpp((String)requestParams.get("game3ScoreOpp"));
		if(requestParams.containsKey("advance"))
			_SweepWorldcup2014Form.setAdvance((String)requestParams.get("advance"));
		if(requestParams.containsKey("team16A1"))
			_SweepWorldcup2014Form.setTeam16A1((String)requestParams.get("team16A1"));
		if(requestParams.containsKey("team16A2"))
			_SweepWorldcup2014Form.setTeam16A2((String)requestParams.get("team16A2"));
		if(requestParams.containsKey("team16B1"))
			_SweepWorldcup2014Form.setTeam16B1((String)requestParams.get("team16B1"));
		if(requestParams.containsKey("team16B2"))
			_SweepWorldcup2014Form.setTeam16B2((String)requestParams.get("team16B2"));
		if(requestParams.containsKey("team16C1"))
			_SweepWorldcup2014Form.setTeam16C1((String)requestParams.get("team16C1"));
		if(requestParams.containsKey("team16C2"))
			_SweepWorldcup2014Form.setTeam16C2((String)requestParams.get("team16C2"));
		if(requestParams.containsKey("team16D1"))
			_SweepWorldcup2014Form.setTeam16D1((String)requestParams.get("team16D1"));
		if(requestParams.containsKey("team16D2"))
			_SweepWorldcup2014Form.setTeam16D2((String)requestParams.get("team16D2"));
		if(requestParams.containsKey("team16E1"))
			_SweepWorldcup2014Form.setTeam16E1((String)requestParams.get("team16E1"));
		if(requestParams.containsKey("team16E2"))
			_SweepWorldcup2014Form.setTeam16E2((String)requestParams.get("team16E2"));
		if(requestParams.containsKey("team16F1"))
			_SweepWorldcup2014Form.setTeam16F1((String)requestParams.get("team16F1"));
		if(requestParams.containsKey("team16F2"))
			_SweepWorldcup2014Form.setTeam16F2((String)requestParams.get("team16F2"));
		if(requestParams.containsKey("team16G1"))
			_SweepWorldcup2014Form.setTeam16G1((String)requestParams.get("team16G1"));
		if(requestParams.containsKey("team16G2"))
			_SweepWorldcup2014Form.setTeam16G2((String)requestParams.get("team16G2"));
		if(requestParams.containsKey("team16H1"))
			_SweepWorldcup2014Form.setTeam16H1((String)requestParams.get("team16H1"));
		if(requestParams.containsKey("team16H2"))
			_SweepWorldcup2014Form.setTeam16H2((String)requestParams.get("team16H2"));
		if(requestParams.containsKey("quarterFinal1"))
			_SweepWorldcup2014Form.setQuarterFinal1((String)requestParams.get("quarterFinal1"));
		if(requestParams.containsKey("quarterFinal2"))
			_SweepWorldcup2014Form.setQuarterFinal2((String)requestParams.get("quarterFinal2"));
		if(requestParams.containsKey("quarterFinal3"))
			_SweepWorldcup2014Form.setQuarterFinal3((String)requestParams.get("quarterFinal3"));
		if(requestParams.containsKey("quarterFinal4"))
			_SweepWorldcup2014Form.setQuarterFinal4((String)requestParams.get("quarterFinal4"));
		if(requestParams.containsKey("quarterFinal5"))
			_SweepWorldcup2014Form.setQuarterFinal5((String)requestParams.get("quarterFinal5"));
		if(requestParams.containsKey("quarterFinal6"))
			_SweepWorldcup2014Form.setQuarterFinal6((String)requestParams.get("quarterFinal6"));
		if(requestParams.containsKey("quarterFinal7"))
			_SweepWorldcup2014Form.setQuarterFinal7((String)requestParams.get("quarterFinal7"));
		if(requestParams.containsKey("quarterFinal8"))
			_SweepWorldcup2014Form.setQuarterFinal8((String)requestParams.get("quarterFinal8"));
		if(requestParams.containsKey("semiFinal1"))
			_SweepWorldcup2014Form.setSemiFinal1((String)requestParams.get("semiFinal1"));
		if(requestParams.containsKey("semiFinal2"))
			_SweepWorldcup2014Form.setSemiFinal2((String)requestParams.get("semiFinal2"));
		if(requestParams.containsKey("semiFinal3"))
			_SweepWorldcup2014Form.setSemiFinal3((String)requestParams.get("semiFinal3"));
		if(requestParams.containsKey("semiFinal4"))
			_SweepWorldcup2014Form.setSemiFinal4((String)requestParams.get("semiFinal4"));
		if(requestParams.containsKey("final1"))
			_SweepWorldcup2014Form.setFinal1((String)requestParams.get("final1"));
		if(requestParams.containsKey("final2"))
			_SweepWorldcup2014Form.setFinal2((String)requestParams.get("final2"));
		if(requestParams.containsKey("champion"))
			_SweepWorldcup2014Form.setChampion((String)requestParams.get("champion"));
		if(requestParams.containsKey("finalScoreWin"))
			_SweepWorldcup2014Form.setFinalScoreWin((String)requestParams.get("finalScoreWin"));
		if(requestParams.containsKey("finalScoreLose"))
			_SweepWorldcup2014Form.setFinalScoreLose((String)requestParams.get("finalScoreLose"));
		if(requestParams.containsKey("timeCreated"))
			_SweepWorldcup2014Form.setTimeCreated((String)requestParams.get("timeCreated"));
		if(requestParams.containsKey("timeUpdated"))
			_SweepWorldcup2014Form.setTimeUpdated((String)requestParams.get("timeUpdated"));
    }


    protected boolean loginRequired() {
        return false;
    }

    public boolean isSynchRequired(){
        return true;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    protected void registerDefaultViewsForAction(){
        m_viewManager.registerView(getActionName(), "sweep_worldcup2014_home=NULL,/jsp/form_sweep/sweepWorldcup2014_home.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "sweep_worldcup2014_list=NULL,/jsp/form_sweep/sweepWorldcup2014_list.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "sweep_worldcup2014_form=NULL,/jsp/form_sweep/sweepWorldcup2014_addedit.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManager.registerView(getActionName(), "sweep_worldcup2014_ajax=NULL,/jsp/form_sweep/sweepWorldcup2014_ajax.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");

        m_viewManagerMobile.registerView(getActionName(), "sweep_worldcup2014_home=NULL,/jsp/form_sweep/sweepWorldcup2014_home.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "sweep_worldcup2014_list=NULL,/jsp/form_sweep/sweepWorldcup2014_list.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "sweep_worldcup2014_form=NULL,/jsp/form_sweep/sweepWorldcup2014_addedit.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
        m_viewManagerMobile.registerView(getActionName(), "sweep_worldcup2014_ajax=NULL,/jsp/form_sweep/sweepWorldcup2014_ajax.mobile.jsp,NULL,NULL,NULL,true,NULL,false,true,true,true,false,true,false,false");
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


	// This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
        return AccessDef.SystemRole.User;
    }
    
//    protected SweepWorldcup2014DS m_ds;
//    protected AutositeActionExtent m_actionExtent;

}
