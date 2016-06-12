package com.surveygen.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/** 
 * MyEclipse Struts
 * Creation date: 11-04-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 * @struts.action-forward name="success" path="/jsp/layout/layout.jsp" contextRelative="true"
 */
public class SurveyGenConfigAction extends SurveyGenCoreAction {
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
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
    
        String subact = request.getParameter("subact");
        
        if (subact != null && subact.equalsIgnoreCase("ww")) {
            String pageSize = (String) session.getAttribute("k_view_pagesize");
            if (pageSize != null) {
                int size = Integer.parseInt(pageSize);
                if (size <= 1100) {
                    size += 100;
                    session.setAttribute("k_view_pagesize", String.valueOf(size));
                }
            }
            else {
                session.setAttribute("k_view_pagesize", String.valueOf(1000));
            }
            
            session.setAttribute("k_view_pagefull", "false");
        }
        else if (subact != null && subact.equalsIgnoreCase("wn")) {
            String pageSize = (String) session.getAttribute("k_view_pagesize");
            if (pageSize != null) {
                int size = Integer.parseInt(pageSize);
                if (size >= 700) {
                    size -= 100;
                    session.setAttribute("k_view_pagesize", String.valueOf(size));
                }
            }
            else {
                session.setAttribute("k_view_pagesize", String.valueOf(1000));
            }
            session.setAttribute("k_view_pagefull", "false");
        }
        else if (subact != null && subact.equalsIgnoreCase("wf")) {
            session.setAttribute("k_view_pagefull", "true");
        }
        else if (subact != null && subact.equalsIgnoreCase("wp")) {
            return mapping.findForward("printer");
        }
        
        //TODO redirect URL to the current display. how to do that??
        return mapping.findForward("default");
    }
}
