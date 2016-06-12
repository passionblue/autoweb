package com.autosite.struts.action;

import java.util.Date;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Linkto;
import com.autosite.ds.LinktoDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.LinktoForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class LinktoAction extends AutositeCoreAction {

    public LinktoAction(){
        m_ds = LinktoDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        LinktoForm _LinktoForm = (LinktoForm) form;
        HttpSession session = request.getSession();

        setPage(session, "linkto_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        if (context == null || !context.isSuperAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        Linkto _Linkto = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _Linkto = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Linkto _Linkto = m_ds.getById(cid);

            if (_Linkto == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Linkto.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Linkto.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _LinktoForm, _Linkto);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Linkto _Linkto = m_ds.getById(cid);

            if (_Linkto == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Linkto.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Linkto.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _Linkto);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Linkto _Linkto = m_ds.getById(cid);

            if (_Linkto == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Linkto.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Linkto.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _Linkto);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_Linkto);
            try { 
                m_actionExtent.afterDelete(request, response, _Linkto);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
                return mapping.findForward("default");
            }

            m_logger.info("Creating new Linkto" );
            Linkto _LinktoNew = new Linkto();   

            // Setting IDs for the object
            _LinktoNew.setSiteId(site.getId());

            _LinktoNew.setLinkKey(WebParamUtil.getStringValue(_LinktoForm.getLinkKey()));
            m_logger.debug("setting LinkKey=" +_LinktoForm.getLinkKey());
            _LinktoNew.setLinkTarget(WebParamUtil.getStringValue(_LinktoForm.getLinkTarget()));
            m_logger.debug("setting LinkTarget=" +_LinktoForm.getLinkTarget());
            _LinktoNew.setDisable(WebParamUtil.getIntValue(_LinktoForm.getDisable()));
            m_logger.debug("setting Disable=" +_LinktoForm.getDisable());
            _LinktoNew.setTimeCreated(WebParamUtil.getDateValue(_LinktoForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_LinktoForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _LinktoNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_LinktoNew);
            try{
                m_actionExtent.afterAdd(request, response, _LinktoNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "linkto_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, LinktoForm _LinktoForm, Linkto _Linkto) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Linkto _Linkto = m_ds.getById(cid);

        m_logger.debug("Before update " + LinktoDS.objectToString(_Linkto));

        _Linkto.setLinkKey(WebParamUtil.getStringValue(_LinktoForm.getLinkKey()));
        _Linkto.setLinkTarget(WebParamUtil.getStringValue(_LinktoForm.getLinkTarget()));
        _Linkto.setDisable(WebParamUtil.getIntValue(_LinktoForm.getDisable()));
        _Linkto.setTimeCreated(WebParamUtil.getDateValue(_LinktoForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _Linkto);
        m_ds.update(_Linkto);
        m_actionExtent.afterUpdate(request, response, _Linkto);
        m_logger.debug("After update " + LinktoDS.objectToString(_Linkto));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, Linkto _Linkto) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Linkto _Linkto = m_ds.getById(cid);

        if (!isMissing(request.getParameter("linkKey"))) {
            m_logger.debug("updating param linkKey from " +_Linkto.getLinkKey() + "->" + request.getParameter("linkKey"));
            _Linkto.setLinkKey(WebParamUtil.getStringValue(request.getParameter("linkKey")));
        }
        if (!isMissing(request.getParameter("linkTarget"))) {
            m_logger.debug("updating param linkTarget from " +_Linkto.getLinkTarget() + "->" + request.getParameter("linkTarget"));
            _Linkto.setLinkTarget(WebParamUtil.getStringValue(request.getParameter("linkTarget")));
        }
        if (!isMissing(request.getParameter("disable"))) {
            m_logger.debug("updating param disable from " +_Linkto.getDisable() + "->" + request.getParameter("disable"));
            _Linkto.setDisable(WebParamUtil.getIntValue(request.getParameter("disable")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_Linkto.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _Linkto.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _Linkto);
        m_ds.update(_Linkto);
        m_actionExtent.afterUpdate(request, response, _Linkto);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected LinktoDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( LinktoAction.class);
}
