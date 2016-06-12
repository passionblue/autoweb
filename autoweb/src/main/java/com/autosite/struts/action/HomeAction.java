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
import com.autosite.ds.SiteDS;
import com.autosite.session.InstantAccessToken;
import com.autosite.session.InstantAccessTokenManager;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.util.WebUtil;

/** 
 * MyEclipse Struts
 * Creation date: 07-08-2007
 * 
 * XDoclet definition:
 * @struts.action scope="request" validate="true"
 */
public class HomeAction extends AutositeCoreAction {
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

	    HttpSession session = request.getSession();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        

        if ( WebUtil.isTrue(site.getSubsite())){
            setPage(request, request.getServerName(), "home");
        } else {
            setPage(request, request.getServerName(), "home");
        }
        
        
        String tokId=request.getParameter("_instaid");
        
        InstantAccessToken token = InstantAccessTokenManager.getInstance().findAndAcceptToken(session.getId(), site.getSiteUrl(), tokId);
        
        if ( token != null && token.getAccessType() == InstantAccessToken.ACCESS_TYPE_LOGIN && !token.expired()) {

            try {
                setLogin(session, token.getAccessUser());
                m_logger.info("User " + token.getAccessUser() + " logged in by instant access token[" + token + "]");
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                setPage(request, "error");
            }
        }
        return mapping.findForward("default");
	}
	
	
	@Override
    protected boolean loginRequired() {
	    return false;
    }

    private static Logger m_logger = Logger.getLogger(HomeAction.class);
}