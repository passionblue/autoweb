/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.seox.struts;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jtrend.struts.core.JtrendAction;
import com.jtrend.struts.core.LoginRequiredJtrendAction;
import com.seox.db.UserDomain;
import com.seox.struts.form.DomainManageForm;
import com.seox.util.DomainNameUtil;
import com.seox.work.UserBO;
import com.seox.work.UserBOPool;

/** 
 * MyEclipse Struts
 * Creation date: 11-15-2006
 * 
 * XDoclet definition:
 * @struts.action path="/domain_manage_form_submit" name="domainmanageform" input="/jsp/model/domain_manage_form.jsp" scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/login_statics/keywords.jsp"
 */
public class DomainManageFormAction extends LoginRequiredJtrendAction {
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
    
    private static Logger m_logger = Logger.getLogger(DomainManageFormAction.class);
    
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        DomainManageForm domainmanageform = (DomainManageForm) form;// TODO Auto-generated method stub
        HttpSession session = request.getSession();
        UserBO userBO = getUserBO(session);

        if (isMissing(domainmanageform.getCmd())) {
            
        } else {
            
            if (domainmanageform.getCmd().equalsIgnoreCase("add")) {
                if (!isMissing(domainmanageform.getDomain())) {
                    
                    m_logger.debug("Requested domain to add : " + domainmanageform.getDomain());
                    String domainStr = DomainNameUtil.getRootDomain(domainmanageform.getDomain());
                    m_logger.debug("Requested domain to add : " + domainStr);
                    
                    if (domainStr == null ) {
                        session.setAttribute("k_top_error_text", "Domain " + domainmanageform.getDomain() + " was not added due to invalid format.");
                    } else {
                    
                        UserDomain domain = new UserDomain();
                        domain.setEnteredT(new Date());
                        domain.setDomain(domainStr);

                        userBO.addDomain(domain);
                        session.removeAttribute("kv_domains");
                        session.removeAttribute("kv_keywords");
                        session.setAttribute("k_top_text", "Domain " + domainmanageform.getDomain() + " was successfully added");
                    }
                }
            } else if (domainmanageform.getCmd().equalsIgnoreCase("delete")) {
                if (!isMissing(domainmanageform.getDomain())) {
                    
                    m_logger.debug("Deleting domain " + domainmanageform.getDomain());
                    String domainStr = DomainNameUtil.getRootDomain(domainmanageform.getDomain());
                    m_logger.debug("Formated " + domainStr);
                    long domainId = Long.parseLong(domainmanageform.getDomain());
                    UserDomain domain = userBO.findDomainById(new Long(domainId));
                    if (domain != null) {
                        userBO.removeDomain(domain);
                        session.removeAttribute("kv_domains");
                        session.removeAttribute("kv_keywords");
                    } else {
                        m_logger.debug("Domain not found by id " + domainId + "");
                    }
                }
            }
        }
        
        domainmanageform.setDomain(null); //TODO 
        setPage(session, "domain_manage", true);
        return mapping.findForward("success");    
    }

}