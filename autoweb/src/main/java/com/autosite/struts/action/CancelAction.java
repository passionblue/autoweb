/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.action;

import java.util.Date;

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

/** 
 * MyEclipse Struts
 * Creation date: 07-08-2007
 * 
 * XDoclet definition:
 * @struts.action scope="request" validate="true"
 */
public class CancelAction extends AutositeCoreAction {
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
        setPage(session, request.getServerName(), "home");
        return mapping.findForward("default");
	}
	private static Logger m_logger = Logger.getLogger(CancelAction.class);
}