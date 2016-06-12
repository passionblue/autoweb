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

import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.util.WebParamUtil;

/**
 * 
 * 
 */
public class SessionSetAction extends AutositeCoreAction {
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
        
        
        if ( isAllThere(request, "group", "key", "value" )){

            String sessionGroup = getRequestParam(request, "group");
            String sessionKey = getRequestParam(request, "key");
            String sessionValue = getRequestParam(request, "value");
            
            m_logger.debug("Session group name = " + sessionGroup + " " + sessionKey + " " + sessionValue);
            addSessionStorable(session, sessionGroup, sessionKey, sessionValue);

            //TODO need map style session sets. Added below temporary. 
            //TODELETE
            if ( sessionGroup.equalsIgnoreCase("ChurApp")) {
                if ( isAllThere( request, "week", "year")){
                    String week = WebParamUtil.getStringValue(getRequestParam(request, "week"));
                    String year = WebParamUtil.getStringValue(getRequestParam(request, "year"));
                    
                    session.setAttribute("churapp.week", week);
                    session.setAttribute("churapp.year", year);
                    m_logger.debug("Session SetTo setting year " + year + " week " + week);
                }
            }
        
        
        } else {
            sessionErrorText(session, "Invalid request");
        }
        
        return mapping.findForward("default");
    }

//    protected boolean staticAuthenticate(String username, String password) {
//
//        if (username != null && username.trim().equals("autosite") && password != null && password.trim().equals("autosite"))
//            return true;
//        
//        
//        
//        return false;
//    }
//
//    protected AutositeUser dbAuthenticate(Site site, String username, String password) {
//        AutositeUser user = UserUtil.findUser(site, username);
//
//        if ( user == null ) {
//            m_logger.info("User object not found for " + username);
//            return null;
//        }
//        
//        if (password != null && password.equals(user.getPassword())) {
//            return user;
//        }
//        else {
//            return null;
//        }
//    }
//
//    protected boolean executeExtent (HttpServletRequest request, AutositeUser user) throws Exception{
//        
//        
//        Site site = SiteDS.getInstance().registerSite(request.getServerName());
//        
//        AutositeLoginExtent extent = AutositeLoginExtentDS.getInstance().getObjectBySiteId(site.getId());
//        
//        if (extent != null){
//            
//            try {
//                AutositeLoginExtentExecutor executor = (AutositeLoginExtentExecutor)Class.forName(extent.getClassName()).newInstance();
//                executor.execute(request, user);
//            }
//            catch (Exception e) {
//                m_logger.error(e.getMessage(),e);
//            }
//        }
//        
//        return true;
//    }
//    
//    
//    
    
    protected boolean loginRequired() {
        return false;
    }

    private static Logger m_logger = Logger.getLogger(SessionSetAction.class);
}
