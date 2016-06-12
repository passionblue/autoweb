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

import com.mchange.v2.c3p0.impl.DbAuth;
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
public class LoginAction extends SurveyGenCoreAction {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward ex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

        LoginForm loginForm = (LoginForm) form;// TODO Auto-generated method stub
        HttpSession session = request.getSession();
        
        String username = null;
        String password = null;
		
        if ( !isMissing(loginForm.getUsername()) ) {
            username = loginForm.getUsername();
        }
        if ( !isMissing(loginForm.getPassword()) ) {
            password = loginForm.getPassword();
        }

        boolean dev = false;
        
        if (dev) {
        getSessionContext(session).setLogin(true);
        getSessionContext(session).setUsername("siteadmin");
        m_logger.info("user " + username + " logged in");
        setPage(session, request.getServerName(), "home");
        return mapping.findForward("default");
        }
        
        m_logger.info("Login request usename=" + username + ",password=" + password);
        
        if (username != null) {

            boolean authenticated = false;
            if ( m_properties.getProperty("app.cfg.authentication").equalsIgnoreCase("database")) {
                authenticated = dbAuthenticate(username, password);
            } else  {
                authenticated = staticAuthenticate(username, password);
            }
            
            if (authenticated) {
                getSessionContext(session).setLogin(true);
                getSessionContext(session).setUsername(username);
                m_logger.info("user " + username + " logged in");
                setPage(session, request.getServerName(), "home");
                return mapping.findForward("default");
            }
        }
        
        session.setAttribute("k_top_error_text", "username and password are not correct");
        setPage(session, request.getServerName(), "login_form");
        return mapping.findForward("default");
	}
    
	protected boolean staticAuthenticate(String username, String password) {
	    
	    if (username != null && username.trim().equals("autosite") &&
	        password != null && password.trim().equals("autosite"))
	        return true;
	    return false;
	}
	
	protected boolean dbAuthenticate(String username, String password) {
        
        User user = UserDS.getInstance().getUserByUsername(username);
        
        if (user != null) {
            return true;
        }
	    return false;
	}
	
    private static Logger m_logger = Logger.getLogger(LoginAction.class);
}