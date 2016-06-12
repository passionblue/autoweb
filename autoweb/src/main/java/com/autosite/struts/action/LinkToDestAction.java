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

import com.autosite.app.AutositeLoginExtentExecutor;
import com.autosite.db.AutositeLoginExtent;
import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.ds.AutositeLoginExtentDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.LinkToUtil;
import com.autosite.util.UserUtil;
import com.surveygen.struts.form.LoginForm;

/**
 * MyEclipse Struts Creation date: 07-14-2007
 *  this is custom action to send page forward to page that saved in database by linkto action. name is similar but not  
 * 
 *  check the list of linkto /v_linkto_home.html
 *  
 *  keyword is associated with url. 
 *  
 *  then /linkTo.html?d=<keyword> will be automatically foward to
 *  this can be used to short url or hiding url purpose. 
 *  
 *  difference from moveTo.html is moveTo takes actual URL. linkTo takes url from database. 
 *  
 *  see LinkToAction.java
 *  
 *  
 */
public class LinkToDestAction extends AutositeCoreAction {
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

                                               // stub
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (isThere(request.getParameter("d"))){
            String dest = (String) request.getParameter("d");
            String target = LinkToUtil.findLinkTo(dest);
            
            if (target != null) 
                return new ActionForward(target, true);
            
        }
        
        return mapping.findForward("default");
    }

    protected boolean staticAuthenticate(String username, String password) {

        if (username != null && username.trim().equals("autosite") && password != null && password.trim().equals("autosite"))
            return true;
        return false;
    }

    protected AutositeUser dbAuthenticate(Site site, String username, String password) {
        AutositeUser user = UserUtil.findUser(site, username);

        if ( user == null ) {
            m_logger.info("User object not found for " + username);
            return null;
        }
        
        if (password != null && password.equals(user.getPassword())) {
            return user;
        }
        else {
            return null;
        }
    }

    protected boolean executeExtent (HttpServletRequest request, AutositeUser user) throws Exception{
        
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        
        AutositeLoginExtent extent = AutositeLoginExtentDS.getInstance().getObjectBySiteId(site.getId());
        
        if (extent != null){
            
            try {
                AutositeLoginExtentExecutor executor = (AutositeLoginExtentExecutor)Class.forName(extent.getClassName()).newInstance();
                executor.execute(request, user);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
            }
        }
        
        return true;
    }
    
    
    
    
    protected boolean loginRequired() {
        return false;
    }

    private static Logger m_logger = Logger.getLogger(LinkToDestAction.class);
}