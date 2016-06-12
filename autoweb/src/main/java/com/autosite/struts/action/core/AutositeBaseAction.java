package com.autosite.struts.action.core;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.AutositeSessionContextUtil;
import com.jtrend.session.SessionContext;
import com.surveygen.struts.action.SurveyGenCoreAction;

public class AutositeBaseAction extends SurveyGenCoreAction {

    
    public AutositeBaseAction() {
        super();
    }
    
    // For device session management. Pageless session request embeds session ID, which is persisted. 
    // This session ID might have longer life than the server. So 
    protected SessionContext getSessionContextByIdFromRequest(HttpServletRequest request) {

        if ( isThere(request, "_ctxId")){
            String contextId = request.getParameter("_ctxId");
            m_logger.debug("ContextID from request. finding with " + contextId);
            SessionContext sessionContext =  AutositeSessionContextUtil.findBySerial(contextId);
            
            if ( sessionContext != null) {
                return sessionContext;
            } else {
                // This is pageless session but seems something went bad before. this would return error code so that client can relogin/re-establish. 
                sessionContext = AutositeSessionContextUtil.createInvalidSessionContext(SessionContext.SESSION_STATUS_NOT_INVALID_LOGIN_REQUIRED);
                return sessionContext;
            }
        }

        // Not the pageless session
        return null;
    }

    protected SessionContext getSessionContext(HttpSession session)
    {
        return AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
    }    
    
    protected AutositeActionExtent getActionExtent() {
     
        String className = this.getClass().getName().replaceAll("Ajax", "") + "Extent";
        
        Class classObject = null;
        try {
            m_logger.info("Trying to create action extent " + className);
            classObject = Class.forName(className);
            return (AutositeActionExtent)classObject.newInstance();
        }
        catch (Exception e) {
            m_logger.error("Trying to create action extent " + className, e);
            return null;
        }
    }
    
    //==================================================================================================================================
    // Object getter
    //==================================================================================================================================

    protected Site getSite(HttpServletRequest request){
        return SiteDS.getInstance().registerSite(request.getServerName());
    }
    
    protected Site get(HttpServletRequest request){
        return SiteDS.getInstance().registerSite(request.getServerName());
    }

    //==================================================================================================================================
    // 
    //==================================================================================================================================

    // Action Group, if supported is unique ID set by AutoGen to represent the group of actions
    // Action group name can be set in creation level by AutoGen
    // This action group name can be used for ...
    // 1. session storables. By default, getSessionStorableGroup() returns action group name
    // 2. Anything that need to be done for the group of actions or features or app level, this can be used.
    // Action group and session Storable are different in scope. 
    // Session Storable are mostly same as action group but session storable is designed to cover across the groups. 
    
    public String getActionGroupName()          { 
        return this.getClass().getName(); 
    }
    
    //Action Name. 
    public String getActionName()          {
        
        String actionClassName = this.getClass().getSimpleName();
        
        int pos = actionClassName.lastIndexOf("AjaxAction");
        if ( pos > 0 ) {
            m_logger.debug("ACTION ANAME1 " + actionClassName.substring(0,  pos));
            return actionClassName.substring(0,  pos);
        }
        
        pos = actionClassName.lastIndexOf("Action");
        
        if ( pos > 0 ) { 
            m_logger.debug("ACTION ANAME2 " + actionClassName.substring(0,  pos));
            return actionClassName.substring(0,  pos);
        }
        m_logger.debug("ACTION ANAME3 " + actionClassName);
        return actionClassName;
    }
        
    protected String getServerForPage(HttpSession session){
        Site site = (Site) session.getAttribute("k_site");
        if ( site == null) return null;
        
        m_logger.info("##getServerForPage# return site for page " + site.getSiteUrl());
        return site.getSiteUrl();
    }

    private static Logger m_logger = Logger.getLogger(AutositeBaseAction.class);
}
