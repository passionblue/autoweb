/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.surveygen.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
public class SurveyGenLoginViewAction extends LoginViewAction {
/*
    public void initApp() {
        try {
            m_logger.debug("SurveyGenCoreAction.initApp()");
            SurveyGenAppInitiator.init();
        } catch (Exception e) {
            m_logger.error(e);
        }
        
        //m_viewManager = SurveyGenViewManager.getInstance();
        m_viewManager = DefaultViewManager.getInstance();
    }
*/    
    
    private static Logger m_logger = Logger.getLogger(SurveyGenLoginViewAction.class);
}