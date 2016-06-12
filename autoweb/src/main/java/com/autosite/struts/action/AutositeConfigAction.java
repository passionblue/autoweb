package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.SiteConfigTransient;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.util.WebUtil;

/**
 * MyEclipse Struts Creation date: 11-04-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 * @struts.action-forward name="success" path="/jsp/layout/layout.jsp"
 *                        contextRelative="true"
 */
public class AutositeConfigAction extends AutositeCoreAction {
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
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }
        
        if ( WebUtil.isNull(request.getParameter("cmd"))){
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }
        
        String subact = request.getParameter("cmd");
        SiteConfigTransient configTran = SiteConfigTransient.getTransientConfig(site.getId());
        
        if (subact.equalsIgnoreCase("ww")) {
            
            int curOffset = configTran.getWidthOffset();
            configTran.setWidthOffset(curOffset + 100);
            
        }
        else if (subact != null && subact.equalsIgnoreCase("wn")) {
            int curOffset = configTran.getWidthOffset();
            configTran.setWidthOffset(curOffset - 100);
        }
        else if (subact != null && subact.equalsIgnoreCase("wf")) {
            session.setAttribute("k_view_pagefull", "true");
        }
        else if (subact != null && subact.equalsIgnoreCase("wp")) {
            return mapping.findForward("printer");
        }

        // TODO redirect URL to the current display. how to do that??
        return mapping.findForward("default");
    }
}
