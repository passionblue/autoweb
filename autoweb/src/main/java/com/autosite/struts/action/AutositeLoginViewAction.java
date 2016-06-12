/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Site;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.AutositeSessionContextUtil;
import com.autosite.util.UserUtil;
import com.jtrend.session.SessionContext;
import com.jtrend.struts.core.DefaultViewManager;
import com.jtrend.struts.core.LoginViewAction;
import com.jtrend.struts.core.ViewAction;
import com.surveygen.app.SurveyGenAppInitiator;
import com.surveygen.app.SurveyGenViewManager;
import com.surveygen.db.User;
import com.surveygen.ds.UserDS;
import com.surveygen.struts.form.LoginForm;

/** 
 * MyEclipse Struts
 * Creation date: 07-14-2007
 * 
 * XDoclet definition:
 * @struts.action path="/loginFormSubmit" name="loginForm" input="/jsp/form/login.jsp" scope="request" validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp"
 */
public class AutositeLoginViewAction extends LoginViewAction {

    protected SessionContext getSessionContext(HttpSession session)
    {
        return AutositeSessionContextUtil.getSessionContextFromHttpSession(session);
    }    

    
    
    
    protected String getServerForPage(HttpSession session){
        Site site = (Site) session.getAttribute("k_site");
        m_logger.info("##getServerForPage# return site for page " + site.getSiteUrl());
        return site.getSiteUrl();
    }
    
    protected SessionContext getSessionContextByIdFromRequest(HttpServletRequest request) {

        if ( isThere(request, "_ctxId")){
            String contextId = request.getParameter("_ctxId");
            m_logger.debug("ContextID from request. finding with " + contextId);
            return AutositeSessionContextUtil.findBySerial(contextId);
        }

        return null;
    }
 
    protected boolean isSuperAdmin(HttpSession session){
        return UserUtil.isSuperAdmin(session);
    }
    
    private static Logger m_logger = Logger.getLogger(AutositeLoginViewAction.class);
}