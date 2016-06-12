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

import com.autosite.db.PollAnswer;
import com.autosite.ds.PollAnswerDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.PollAnswerForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PollAnswerAction extends AutositeCoreAction {

    public PollAnswerAction(){
        m_ds = PollAnswerDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PollAnswerForm _PollAnswerForm = (PollAnswerForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
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

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            PollAnswer _PollAnswer = m_ds.getById(cid);

            if (_PollAnswer == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollAnswer.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollAnswer.getSiteId()); 
                return mapping.findForward("default");
            }
            try{
                edit(request, response, _PollAnswerForm, _PollAnswer);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //setPage(session, "poll_answer_home");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            PollAnswer _PollAnswer = m_ds.getById(cid);

            if (_PollAnswer == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollAnswer.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollAnswer.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PollAnswer);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            //setPage(session, "poll_answer");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            PollAnswer _PollAnswer = m_ds.getById(cid);

            if (_PollAnswer == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PollAnswer.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollAnswer.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PollAnswer);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PollAnswer);
            try { 
                m_actionExtent.afterDelete(request, response, _PollAnswer);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
            }

            //setPage(session, "poll_answer_home");    
        }
        else {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
                return mapping.findForward("default");
            }

            m_logger.info("Creating new PollAnswer" );
            PollAnswer _PollAnswer = new PollAnswer();   

            // Setting IDs for the object
            _PollAnswer.setSiteId(site.getId());

            _PollAnswer.setAnswerNum(WebParamUtil.getIntValue(_PollAnswerForm.getAnswerNum()));
            m_logger.debug("setting AnswerNum=" +_PollAnswerForm.getAnswerNum());
            _PollAnswer.setText(WebParamUtil.getStringValue(_PollAnswerForm.getText()));
            m_logger.debug("setting Text=" +_PollAnswerForm.getText());
            _PollAnswer.setImageUrl(WebParamUtil.getStringValue(_PollAnswerForm.getImageUrl()));
            m_logger.debug("setting ImageUrl=" +_PollAnswerForm.getImageUrl());
            _PollAnswer.setImageOnly(WebParamUtil.getIntValue(_PollAnswerForm.getImageOnly()));
            m_logger.debug("setting ImageOnly=" +_PollAnswerForm.getImageOnly());

            
            try{
                m_actionExtent.beforeAdd(request, response, _PollAnswer);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_PollAnswer);
            try{
                m_actionExtent.afterAdd(request, response, _PollAnswer);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "poll_answer_home");

            webProc.complete();
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PollAnswerForm _PollAnswerForm, PollAnswer _PollAnswer) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollAnswer _PollAnswer = m_ds.getById(cid);

        m_logger.debug("Before update " + PollAnswerDS.objectToString(_PollAnswer));

        _PollAnswer.setAnswerNum(WebParamUtil.getIntValue(_PollAnswerForm.getAnswerNum()));
        _PollAnswer.setText(WebParamUtil.getStringValue(_PollAnswerForm.getText()));
        _PollAnswer.setImageUrl(WebParamUtil.getStringValue(_PollAnswerForm.getImageUrl()));
        _PollAnswer.setImageOnly(WebParamUtil.getIntValue(_PollAnswerForm.getImageOnly()));

        m_actionExtent.beforeUpdate(request, response, _PollAnswer);
        m_ds.update(_PollAnswer);
        m_actionExtent.afterUpdate(request, response, _PollAnswer);
        m_logger.debug("After update " + PollAnswerDS.objectToString(_PollAnswer));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PollAnswer _PollAnswer) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollAnswer _PollAnswer = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pollId"))) {
            m_logger.debug("updating param pollId from " +_PollAnswer.getPollId() + "->" + request.getParameter("pollId"));
                _PollAnswer.setPollId(WebParamUtil.getLongValue(request.getParameter("pollId")));
            }
        if (!isMissing(request.getParameter("answerNum"))) {
            m_logger.debug("updating param answerNum from " +_PollAnswer.getAnswerNum() + "->" + request.getParameter("answerNum"));
                _PollAnswer.setAnswerNum(WebParamUtil.getIntValue(request.getParameter("answerNum")));
            }
        if (!isMissing(request.getParameter("text"))) {
            m_logger.debug("updating param text from " +_PollAnswer.getText() + "->" + request.getParameter("text"));
                _PollAnswer.setText(WebParamUtil.getStringValue(request.getParameter("text")));
            }
        if (!isMissing(request.getParameter("imageUrl"))) {
            m_logger.debug("updating param imageUrl from " +_PollAnswer.getImageUrl() + "->" + request.getParameter("imageUrl"));
                _PollAnswer.setImageUrl(WebParamUtil.getStringValue(request.getParameter("imageUrl")));
            }
        if (!isMissing(request.getParameter("imageOnly"))) {
            m_logger.debug("updating param imageOnly from " +_PollAnswer.getImageOnly() + "->" + request.getParameter("imageOnly"));
                _PollAnswer.setImageOnly(WebParamUtil.getIntValue(request.getParameter("imageOnly")));
            }

        m_actionExtent.beforeUpdate(request, response, _PollAnswer);
        m_ds.update(_PollAnswer);
        m_actionExtent.afterUpdate(request, response, _PollAnswer);
    }


    protected boolean loginRequired() {
        return true;
    }

    protected PollAnswerDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollAnswerAction.class);
}
