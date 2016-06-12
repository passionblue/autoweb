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

import com.autosite.db.PollComment;
import com.autosite.ds.PollCommentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PollCommentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PollCommentAction extends AutositeCoreAction {

    public PollCommentAction(){
        m_ds = PollCommentDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PollCommentForm _PollCommentForm = (PollCommentForm) form;
        HttpSession session = request.getSession();

        setPage(session, "poll_comment_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
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


        PollComment _PollComment = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PollComment = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollComment _PollComment = m_ds.getById(cid);

            if (_PollComment == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollComment.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollComment.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PollCommentForm, _PollComment);
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
            //PollComment _PollComment = m_ds.getById(cid);

            if (_PollComment == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollComment.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollComment.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PollComment);
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
            //PollComment _PollComment = m_ds.getById(cid);

            if (_PollComment == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollComment.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollComment.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PollComment);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PollComment);
            try { 
                m_actionExtent.afterDelete(request, response, _PollComment);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new PollComment" );
            PollComment _PollCommentNew = new PollComment();   

            // Setting IDs for the object
            _PollCommentNew.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _PollCommentNew.setUserId(user.getId());
            }

            _PollCommentNew.setPollId(WebParamUtil.getLongValue(_PollCommentForm.getPollId()));
            m_logger.debug("setting PollId=" +_PollCommentForm.getPollId());
            _PollCommentNew.setComment(WebParamUtil.getStringValue(_PollCommentForm.getComment()));
            m_logger.debug("setting Comment=" +_PollCommentForm.getComment());
            _PollCommentNew.setHide(WebParamUtil.getIntValue(_PollCommentForm.getHide()));
            m_logger.debug("setting Hide=" +_PollCommentForm.getHide());
            _PollCommentNew.setTimeCreated(WebParamUtil.getDateValue(_PollCommentForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PollCommentForm.getTimeCreated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _PollCommentNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_PollCommentNew);
            try{
                m_actionExtent.afterAdd(request, response, _PollCommentNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "poll_comment_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PollCommentForm _PollCommentForm, PollComment _PollComment) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollComment _PollComment = m_ds.getById(cid);

        m_logger.debug("Before update " + PollCommentDS.objectToString(_PollComment));

        _PollComment.setPollId(WebParamUtil.getLongValue(_PollCommentForm.getPollId()));
        _PollComment.setComment(WebParamUtil.getStringValue(_PollCommentForm.getComment()));
        _PollComment.setHide(WebParamUtil.getIntValue(_PollCommentForm.getHide()));
        _PollComment.setTimeCreated(WebParamUtil.getDateValue(_PollCommentForm.getTimeCreated()));

        m_actionExtent.beforeUpdate(request, response, _PollComment);
        m_ds.update(_PollComment);
        m_actionExtent.afterUpdate(request, response, _PollComment);
        m_logger.debug("After update " + PollCommentDS.objectToString(_PollComment));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PollComment _PollComment) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollComment _PollComment = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pollId"))) {
            m_logger.debug("updating param pollId from " +_PollComment.getPollId() + "->" + request.getParameter("pollId"));
            _PollComment.setPollId(WebParamUtil.getLongValue(request.getParameter("pollId")));
        }
        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_PollComment.getUserId() + "->" + request.getParameter("userId"));
            _PollComment.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("comment"))) {
            m_logger.debug("updating param comment from " +_PollComment.getComment() + "->" + request.getParameter("comment"));
            _PollComment.setComment(WebParamUtil.getStringValue(request.getParameter("comment")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_PollComment.getHide() + "->" + request.getParameter("hide"));
            _PollComment.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PollComment.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PollComment.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PollComment);
        m_ds.update(_PollComment);
        m_actionExtent.afterUpdate(request, response, _PollComment);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected PollCommentDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollCommentAction.class);
}
