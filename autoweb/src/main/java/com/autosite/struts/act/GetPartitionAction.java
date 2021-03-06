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
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.access.AccessConfigManager2;
import com.autosite.util.access.AccessConfigManager3;
import com.autosite.util.access.AccessDef;
import com.autosite.util.access.AccessDef.SystemRole;
import com.autosite.util.access.AccessManager;
import com.jtrend.session.PageView;
import com.jtrend.struts.core.DefaultViewManager;

/** 
 * MyEclipse Struts
 * Creation date: 03-21-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp" contextRelative="true"
 */
public class GetPartitionAction extends AutositeCoreAction {
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
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        String url = request.getRequestURI();
        m_logger.debug(url);
        
        if ( !hasAccess(request) ){
            request.setAttribute("r_error", "true");
            request.setAttribute("r_error_text", "Internal error");
            m_logger.error("Access error occurred");
            return mapping.findForward("default");
        }
        
        return mapping.findForward("default");
    }
    
    
    protected boolean hasAccess(HttpServletRequest request){
        HttpSession session = request.getSession();
        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        
        // First check specific page 
        String partitionPageName = request.getParameter("page");
        
        AccessManager accessConfigManager = AccessConfigManager3.getInstance(context.getServer());

        if ( accessConfigManager.hasMatchForPage(partitionPageName)){
            SystemRole role = accessConfigManager.getSystemRoleByPage("*", partitionPageName, SystemRole.Super);
            if ( userHasAccess(autoUser, role) ) {
                m_logger.info("PARTITION-ACCESS: Page has outright Config : " + partitionPageName + " -> " +  role);
                return true;
            } else { 
                m_logger.info("PARTITION-ACCESS: *EXCEPTION* Page has outright Config : " + partitionPageName + " -> " +  role);
                return false;
            }
        }
        
        // 
        String sentPageName = request.getParameter("src_page");
        if ( sentPageName != null) {
            PageView pageView = DefaultViewManager.getInstance().getPageView(sentPageName);
            
            if ( pageView != null && pageView.isSuperAdminOnly() ) {
                if ( autoUser != null && autoUser.getUserType() == AccessDef.SystemRole.Super.level()){
                    m_logger.info("PARTITION-ACCESS: Source page requires Super. User is super " + autoUser.getUsername() + " id " + autoUser.getId());
                    return true;
                } else {
                    m_logger.info("PARTITION-ACCESS: *EXCEPTION* Source page requires Super. User is NOT super " + autoUser.getUsername() + " id " + autoUser.getId());
                    return false;
                }
            } else {
                //3 sent Page config check
                return haveAccessToPage(session, sentPageName);
            }
        }
        return false;
    }
    
    
    protected boolean loginRequired() {
        return false;
    }
    
    private static Logger m_logger = Logger.getLogger(GetPartitionAction.class);
}
