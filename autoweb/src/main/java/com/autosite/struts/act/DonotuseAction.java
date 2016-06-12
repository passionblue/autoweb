/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.act;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeUser;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.UserUtil;

/** 
 * MyEclipse Struts
 * Creation date: 06-17-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp" contextRelative="true"
 */
public class DonotuseAction extends AutositeCoreAction {
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
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();


        AutositeUser user = UserUtil.getSuperAdminObject();
        try {
            setLogin(session, user);
        }
        catch (Exception e) {
            session.setAttribute("k_top_error_text", "Internal Error Occurred");
            request.setAttribute("k_top_error_text", "Internal Error Occurred");
            setPage(session, request.getServerName(), "login_form");
            m_logger.error(e.getMessage(),e);
        }
        
        setPage(session, request.getServerName(), "home");
        return mapping.findForward("default");

    }
    protected boolean loginRequired() {
        return false;
    }
    private static Logger m_logger = Logger.getLogger(DonotuseAction.class);
}