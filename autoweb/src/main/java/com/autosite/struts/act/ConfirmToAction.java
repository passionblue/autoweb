/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.act;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.SessionRequestHandle;
import com.jtrend.util.RequestUtil;
import com.jtrend.util.WebUtil;

/**
 * MyEclipse Struts Creation date: 07-14-2007
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/loginFormSubmit" name="loginForm"
 *                input="/jsp/form/login.jsp" scope="request" validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp"
 * 
 * Confirm Required Flow
 * 
 * 1. Defined by confirm required action
 * 
 * 2. If it is confirm required action, the action creats confirmToObject that holds the request information. then
 * return the key to find that object and reutnr page is confirmation return page. 
 * 3. user confirms will send confirmToKey 
 * 4. Then this action will converts the confirmTo objec to request and forward the page. 
 * 
 * 
 */
public class ConfirmToAction extends AutositeCoreAction {
    /*
     * Generated Methods
     */

    /**
     * Method execute
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }
        
        String confirmTo = request.getParameter("confTo");
        String confirmToExt = request.getParameter("confToExt");
        m_logger.debug("Confirmed to " + confirmTo);

        ConfirmTo confTo = ConfirmRegisterManager.getInstance().find(session.getId(), confirmTo);
        
        if (confTo == null ) {
            setPage(session, "error");
            return mapping.findForward("default");
        }
        m_logger.debug("Confirmed to found " + confTo);
        
        if ( confTo.confirmed() ){
            m_logger.debug("This confirmTo already confirmed before. " + confirmTo);
        } else {
            confTo.setConfirmed();
            m_logger.debug("This confirmTo NOT confirmed yet. Confirming now " + confirmTo);
        }

//        Map reqParams = RequestUtil.getParameters(request);
//        confTo.setExtParams(reqParams);
        
        //String regextToRemove = "(&target=\\S+&)|(&target=\\S+$)"; // incase token is at the last
        String regextToRemove = "&_reqhid=\\w+&";
        m_logger.debug( confTo.getConfirmToRequest());
        m_logger.debug( response.encodeURL(confTo.getConfirmToRequest()));
        m_logger.debug( response.encodeRedirectURL(confTo.getConfirmToRequest()));

        m_logger.debug( confTo.getConfirmToRequest().replaceAll(regextToRemove, "&"));
        
        // due to url doesnt support international characters, params will not put into URL. 
        // Instead all params put into request handle. 
        SessionRequestHandle handle = getHandle(request);
        handle.setFollowUpParameters(confTo.getExtParams());
        handle.setHasFollowupAction(true);
        
        request.setAttribute("__fwdTo", confTo.getConfirmToRequest().replaceAll(regextToRemove, "&")); // removing previous reqhid from the previous
        m_logger.debug("Will INTERNALLY forward To " + confTo.getConfirmToRequest());
        
        return mapping.findForward("default");
    }

    private static Logger m_logger = Logger.getLogger(ConfirmToAction.class);
}
