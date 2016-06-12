package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.Poll;
import com.autosite.ds.PollDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PollForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class PollAction extends AutositeCoreAction {

    public PollAction(){
        m_ds = PollDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        PollForm _PollForm = (PollForm) form;
        HttpSession session = request.getSession();

        setPage(session, "poll_home");

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
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        Poll _Poll = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _Poll = m_ds.getById(cid);
		}

		if (autoUser == null) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.warn("User object not set. ");
            return mapping.findForward("default");
        }

        if (!hasRequestValue(request, "add", "true")) {

            if ( autoUser.getUserType() != Constants.UserSuperAdmin && autoUser.getId() != _Poll.getUserId() ) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Access Exception. Delete failed. Invalid user has attempted to delete for user " + _Poll.getUserId());
                return mapping.findForward("default");
            }
		}

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Poll _Poll = m_ds.getById(cid);

            if (_Poll == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Poll.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Poll.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PollForm, _Poll);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Poll _Poll = m_ds.getById(cid);

            if (_Poll == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Poll.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Poll.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _Poll);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Poll _Poll = m_ds.getById(cid);

            if (_Poll == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Poll.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Poll.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _Poll);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_Poll);
            try { 
                m_actionExtent.afterDelete(request, response, _Poll);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new Poll" );
            Poll _PollNew = new Poll();   

            // Setting IDs for the object
            _PollNew.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _PollNew.setUserId(user.getId());
            }

            _PollNew.setType(WebParamUtil.getIntValue(_PollForm.getType()));
            m_logger.debug("setting Type=" +_PollForm.getType());
            _PollNew.setCategory(WebParamUtil.getStringValue(_PollForm.getCategory()));
            m_logger.debug("setting Category=" +_PollForm.getCategory());
            _PollNew.setTitle(WebParamUtil.getStringValue(_PollForm.getTitle()));
            m_logger.debug("setting Title=" +_PollForm.getTitle());
            _PollNew.setDescription(WebParamUtil.getStringValue(_PollForm.getDescription()));
            m_logger.debug("setting Description=" +_PollForm.getDescription());
            _PollNew.setQuestion(WebParamUtil.getStringValue(_PollForm.getQuestion()));
            m_logger.debug("setting Question=" +_PollForm.getQuestion());
            _PollNew.setTags(WebParamUtil.getStringValue(_PollForm.getTags()));
            m_logger.debug("setting Tags=" +_PollForm.getTags());
            _PollNew.setPublished(WebParamUtil.getIntValue(_PollForm.getPublished()));
            m_logger.debug("setting Published=" +_PollForm.getPublished());
            _PollNew.setHide(WebParamUtil.getIntValue(_PollForm.getHide()));
            m_logger.debug("setting Hide=" +_PollForm.getHide());
            _PollNew.setDisable(WebParamUtil.getIntValue(_PollForm.getDisable()));
            m_logger.debug("setting Disable=" +_PollForm.getDisable());
            _PollNew.setAllowMultiple(WebParamUtil.getCheckboxValue(_PollForm.getAllowMultiple()));
            m_logger.debug("setting AllowMultiple=" +_PollForm.getAllowMultiple());
            _PollNew.setAllowOwnAnswer(WebParamUtil.getCheckboxValue(_PollForm.getAllowOwnAnswer()));
            m_logger.debug("setting AllowOwnAnswer=" +_PollForm.getAllowOwnAnswer());
            _PollNew.setRandomAnswer(WebParamUtil.getCheckboxValue(_PollForm.getRandomAnswer()));
            m_logger.debug("setting RandomAnswer=" +_PollForm.getRandomAnswer());
            _PollNew.setHideComments(WebParamUtil.getCheckboxValue(_PollForm.getHideComments()));
            m_logger.debug("setting HideComments=" +_PollForm.getHideComments());
            _PollNew.setHideResults(WebParamUtil.getCheckboxValue(_PollForm.getHideResults()));
            m_logger.debug("setting HideResults=" +_PollForm.getHideResults());
            _PollNew.setHideHomeLink(WebParamUtil.getCheckboxValue(_PollForm.getHideHomeLink()));
            m_logger.debug("setting HideHomeLink=" +_PollForm.getHideHomeLink());
            _PollNew.setShowSponsor(WebParamUtil.getCheckboxValue(_PollForm.getShowSponsor()));
            m_logger.debug("setting ShowSponsor=" +_PollForm.getShowSponsor());
            _PollNew.setUseCookieForDup(WebParamUtil.getCheckboxValue(_PollForm.getUseCookieForDup()));
            m_logger.debug("setting UseCookieForDup=" +_PollForm.getUseCookieForDup());
            _PollNew.setRepeatEveryDay(WebParamUtil.getCheckboxValue(_PollForm.getRepeatEveryDay()));
            m_logger.debug("setting RepeatEveryDay=" +_PollForm.getRepeatEveryDay());
            _PollNew.setMaxRepeatVote(WebParamUtil.getIntValue(_PollForm.getMaxRepeatVote()));
            m_logger.debug("setting MaxRepeatVote=" +_PollForm.getMaxRepeatVote());
            _PollNew.setNumDaysOpen(WebParamUtil.getIntValue(_PollForm.getNumDaysOpen()));
            m_logger.debug("setting NumDaysOpen=" +_PollForm.getNumDaysOpen());
            _PollNew.setTimeCreated(WebParamUtil.getDateValue(_PollForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PollForm.getTimeCreated());
            _PollNew.setTimeUpdated(WebParamUtil.getDateValue(_PollForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PollForm.getTimeUpdated());
            _PollNew.setTimeExpired(WebParamUtil.getDateValue(_PollForm.getTimeExpired()));
            m_logger.debug("setting TimeExpired=" +_PollForm.getTimeExpired());

            try{
                m_actionExtent.beforeAdd(request, response, _PollNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PollNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PollNew);
            try{
                m_actionExtent.afterAdd(request, response, _PollNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "poll_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PollForm _PollForm, Poll _Poll) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Poll _Poll = m_ds.getById(cid);

        m_logger.debug("Before update " + PollDS.objectToString(_Poll));

        _Poll.setType(WebParamUtil.getIntValue(_PollForm.getType()));
        _Poll.setCategory(WebParamUtil.getStringValue(_PollForm.getCategory()));
        _Poll.setTitle(WebParamUtil.getStringValue(_PollForm.getTitle()));
        _Poll.setDescription(WebParamUtil.getStringValue(_PollForm.getDescription()));
        _Poll.setQuestion(WebParamUtil.getStringValue(_PollForm.getQuestion()));
        _Poll.setTags(WebParamUtil.getStringValue(_PollForm.getTags()));
        _Poll.setPublished(WebParamUtil.getIntValue(_PollForm.getPublished()));
        _Poll.setHide(WebParamUtil.getIntValue(_PollForm.getHide()));
        _Poll.setDisable(WebParamUtil.getIntValue(_PollForm.getDisable()));
        _Poll.setAllowMultiple(WebParamUtil.getCheckboxValue(_PollForm.getAllowMultiple()));
        _Poll.setAllowOwnAnswer(WebParamUtil.getCheckboxValue(_PollForm.getAllowOwnAnswer()));
        _Poll.setRandomAnswer(WebParamUtil.getCheckboxValue(_PollForm.getRandomAnswer()));
        _Poll.setHideComments(WebParamUtil.getCheckboxValue(_PollForm.getHideComments()));
        _Poll.setHideResults(WebParamUtil.getCheckboxValue(_PollForm.getHideResults()));
        _Poll.setHideHomeLink(WebParamUtil.getCheckboxValue(_PollForm.getHideHomeLink()));
        _Poll.setShowSponsor(WebParamUtil.getCheckboxValue(_PollForm.getShowSponsor()));
        _Poll.setUseCookieForDup(WebParamUtil.getCheckboxValue(_PollForm.getUseCookieForDup()));
        _Poll.setRepeatEveryDay(WebParamUtil.getCheckboxValue(_PollForm.getRepeatEveryDay()));
        _Poll.setMaxRepeatVote(WebParamUtil.getIntValue(_PollForm.getMaxRepeatVote()));
        _Poll.setNumDaysOpen(WebParamUtil.getIntValue(_PollForm.getNumDaysOpen()));
        _Poll.setTimeUpdated(new TimeNow());
        _Poll.setTimeExpired(WebParamUtil.getDateValue(_PollForm.getTimeExpired()));

        m_actionExtent.beforeUpdate(request, response, _Poll);
        m_ds.update(_Poll);
        m_actionExtent.afterUpdate(request, response, _Poll);
        m_logger.debug("After update " + PollDS.objectToString(_Poll));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, Poll _Poll) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Poll _Poll = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_Poll.getUserId() + "->" + request.getParameter("userId"));
            _Poll.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("serial"))) {
            m_logger.debug("updating param serial from " +_Poll.getSerial() + "->" + request.getParameter("serial"));
            _Poll.setSerial(WebParamUtil.getStringValue(request.getParameter("serial")));
        }
        if (!isMissing(request.getParameter("type"))) {
            m_logger.debug("updating param type from " +_Poll.getType() + "->" + request.getParameter("type"));
            _Poll.setType(WebParamUtil.getIntValue(request.getParameter("type")));
        }
        if (!isMissing(request.getParameter("category"))) {
            m_logger.debug("updating param category from " +_Poll.getCategory() + "->" + request.getParameter("category"));
            _Poll.setCategory(WebParamUtil.getStringValue(request.getParameter("category")));
        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_Poll.getTitle() + "->" + request.getParameter("title"));
            _Poll.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
        }
        if (!isMissing(request.getParameter("description"))) {
            m_logger.debug("updating param description from " +_Poll.getDescription() + "->" + request.getParameter("description"));
            _Poll.setDescription(WebParamUtil.getStringValue(request.getParameter("description")));
        }
        if (!isMissing(request.getParameter("question"))) {
            m_logger.debug("updating param question from " +_Poll.getQuestion() + "->" + request.getParameter("question"));
            _Poll.setQuestion(WebParamUtil.getStringValue(request.getParameter("question")));
        }
        if (!isMissing(request.getParameter("tags"))) {
            m_logger.debug("updating param tags from " +_Poll.getTags() + "->" + request.getParameter("tags"));
            _Poll.setTags(WebParamUtil.getStringValue(request.getParameter("tags")));
        }
        if (!isMissing(request.getParameter("published"))) {
            m_logger.debug("updating param published from " +_Poll.getPublished() + "->" + request.getParameter("published"));
            _Poll.setPublished(WebParamUtil.getIntValue(request.getParameter("published")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_Poll.getHide() + "->" + request.getParameter("hide"));
            _Poll.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("disable"))) {
            m_logger.debug("updating param disable from " +_Poll.getDisable() + "->" + request.getParameter("disable"));
            _Poll.setDisable(WebParamUtil.getIntValue(request.getParameter("disable")));
        }
        if (!isMissing(request.getParameter("allowMultiple"))) {
            m_logger.debug("updating param allowMultiple from " +_Poll.getAllowMultiple() + "->" + request.getParameter("allowMultiple"));
            _Poll.setAllowMultiple(WebParamUtil.getCheckboxValue(request.getParameter("allowMultiple")));
        }
        if (!isMissing(request.getParameter("allowOwnAnswer"))) {
            m_logger.debug("updating param allowOwnAnswer from " +_Poll.getAllowOwnAnswer() + "->" + request.getParameter("allowOwnAnswer"));
            _Poll.setAllowOwnAnswer(WebParamUtil.getCheckboxValue(request.getParameter("allowOwnAnswer")));
        }
        if (!isMissing(request.getParameter("randomAnswer"))) {
            m_logger.debug("updating param randomAnswer from " +_Poll.getRandomAnswer() + "->" + request.getParameter("randomAnswer"));
            _Poll.setRandomAnswer(WebParamUtil.getCheckboxValue(request.getParameter("randomAnswer")));
        }
        if (!isMissing(request.getParameter("hideComments"))) {
            m_logger.debug("updating param hideComments from " +_Poll.getHideComments() + "->" + request.getParameter("hideComments"));
            _Poll.setHideComments(WebParamUtil.getCheckboxValue(request.getParameter("hideComments")));
        }
        if (!isMissing(request.getParameter("hideResults"))) {
            m_logger.debug("updating param hideResults from " +_Poll.getHideResults() + "->" + request.getParameter("hideResults"));
            _Poll.setHideResults(WebParamUtil.getCheckboxValue(request.getParameter("hideResults")));
        }
        if (!isMissing(request.getParameter("hideHomeLink"))) {
            m_logger.debug("updating param hideHomeLink from " +_Poll.getHideHomeLink() + "->" + request.getParameter("hideHomeLink"));
            _Poll.setHideHomeLink(WebParamUtil.getCheckboxValue(request.getParameter("hideHomeLink")));
        }
        if (!isMissing(request.getParameter("showSponsor"))) {
            m_logger.debug("updating param showSponsor from " +_Poll.getShowSponsor() + "->" + request.getParameter("showSponsor"));
            _Poll.setShowSponsor(WebParamUtil.getCheckboxValue(request.getParameter("showSponsor")));
        }
        if (!isMissing(request.getParameter("useCookieForDup"))) {
            m_logger.debug("updating param useCookieForDup from " +_Poll.getUseCookieForDup() + "->" + request.getParameter("useCookieForDup"));
            _Poll.setUseCookieForDup(WebParamUtil.getCheckboxValue(request.getParameter("useCookieForDup")));
        }
        if (!isMissing(request.getParameter("repeatEveryDay"))) {
            m_logger.debug("updating param repeatEveryDay from " +_Poll.getRepeatEveryDay() + "->" + request.getParameter("repeatEveryDay"));
            _Poll.setRepeatEveryDay(WebParamUtil.getCheckboxValue(request.getParameter("repeatEveryDay")));
        }
        if (!isMissing(request.getParameter("maxRepeatVote"))) {
            m_logger.debug("updating param maxRepeatVote from " +_Poll.getMaxRepeatVote() + "->" + request.getParameter("maxRepeatVote"));
            _Poll.setMaxRepeatVote(WebParamUtil.getIntValue(request.getParameter("maxRepeatVote")));
        }
        if (!isMissing(request.getParameter("numDaysOpen"))) {
            m_logger.debug("updating param numDaysOpen from " +_Poll.getNumDaysOpen() + "->" + request.getParameter("numDaysOpen"));
            _Poll.setNumDaysOpen(WebParamUtil.getIntValue(request.getParameter("numDaysOpen")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_Poll.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _Poll.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_Poll.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _Poll.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        if (!isMissing(request.getParameter("timeExpired"))) {
            m_logger.debug("updating param timeExpired from " +_Poll.getTimeExpired() + "->" + request.getParameter("timeExpired"));
            _Poll.setTimeExpired(WebParamUtil.getDateValue(request.getParameter("timeExpired")));
        }

        m_actionExtent.beforeUpdate(request, response, _Poll);
        m_ds.update(_Poll);
        m_actionExtent.afterUpdate(request, response, _Poll);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, Poll _Poll) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Poll _Poll = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
			return String.valueOf(_Poll.getUserId());
        }
        if (!isMissing(request.getParameter("serial"))) {
			return String.valueOf(_Poll.getSerial());
        }
        if (!isMissing(request.getParameter("type"))) {
			return String.valueOf(_Poll.getType());
        }
        if (!isMissing(request.getParameter("category"))) {
			return String.valueOf(_Poll.getCategory());
        }
        if (!isMissing(request.getParameter("title"))) {
			return String.valueOf(_Poll.getTitle());
        }
        if (!isMissing(request.getParameter("description"))) {
			return String.valueOf(_Poll.getDescription());
        }
        if (!isMissing(request.getParameter("question"))) {
			return String.valueOf(_Poll.getQuestion());
        }
        if (!isMissing(request.getParameter("tags"))) {
			return String.valueOf(_Poll.getTags());
        }
        if (!isMissing(request.getParameter("published"))) {
			return String.valueOf(_Poll.getPublished());
        }
        if (!isMissing(request.getParameter("hide"))) {
			return String.valueOf(_Poll.getHide());
        }
        if (!isMissing(request.getParameter("disable"))) {
			return String.valueOf(_Poll.getDisable());
        }
        if (!isMissing(request.getParameter("allowMultiple"))) {
			return String.valueOf(_Poll.getAllowMultiple());
        }
        if (!isMissing(request.getParameter("allowOwnAnswer"))) {
			return String.valueOf(_Poll.getAllowOwnAnswer());
        }
        if (!isMissing(request.getParameter("randomAnswer"))) {
			return String.valueOf(_Poll.getRandomAnswer());
        }
        if (!isMissing(request.getParameter("hideComments"))) {
			return String.valueOf(_Poll.getHideComments());
        }
        if (!isMissing(request.getParameter("hideResults"))) {
			return String.valueOf(_Poll.getHideResults());
        }
        if (!isMissing(request.getParameter("hideHomeLink"))) {
			return String.valueOf(_Poll.getHideHomeLink());
        }
        if (!isMissing(request.getParameter("showSponsor"))) {
			return String.valueOf(_Poll.getShowSponsor());
        }
        if (!isMissing(request.getParameter("useCookieForDup"))) {
			return String.valueOf(_Poll.getUseCookieForDup());
        }
        if (!isMissing(request.getParameter("repeatEveryDay"))) {
			return String.valueOf(_Poll.getRepeatEveryDay());
        }
        if (!isMissing(request.getParameter("maxRepeatVote"))) {
			return String.valueOf(_Poll.getMaxRepeatVote());
        }
        if (!isMissing(request.getParameter("numDaysOpen"))) {
			return String.valueOf(_Poll.getNumDaysOpen());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_Poll.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_Poll.getTimeUpdated());
        }
        if (!isMissing(request.getParameter("timeExpired"))) {
			return String.valueOf(_Poll.getTimeExpired());
        }
		return null;
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        
        //         // Request Processing 
        // 
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                ex(mapping, form, request, response, true);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            Poll _Poll = PollDS.getInstance().getById(id);
            if (_Poll == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _Poll);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){

			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/pollAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("type")) {
                String value = WebUtil.display(request.getParameter("type"));

                if ( forceHiddenSet.contains("type")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"type\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Type</div>");
            buf.append("<select id=\"requiredField\" name=\"type\">");
            buf.append("<option value=\"\" >- Please Select -</option>");


            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("category")) {
                String value = WebUtil.display(request.getParameter("category"));

                if ( forceHiddenSet.contains("category")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"category\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Category</div>");
            buf.append("<select id=\"requiredField\" name=\"category\">");
            buf.append("<option value=\"\" >- Please Select -</option>");


            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("title")) {
                String value = WebUtil.display(request.getParameter("title"));

                if ( forceHiddenSet.contains("title")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"title\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Title</div>");
            buf.append("<INPUT NAME=\"title\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("description")) {
                String value = WebUtil.display(request.getParameter("description"));

                if ( forceHiddenSet.contains("description")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"description\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Description</div>");
            buf.append("<TEXTAREA NAME=\"description\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("question")) {
                String value = WebUtil.display(request.getParameter("question"));

                if ( forceHiddenSet.contains("question")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"question\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Question</div>");
            buf.append("<INPUT NAME=\"question\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                String value = WebUtil.display(request.getParameter("tags"));

                if ( forceHiddenSet.contains("tags")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tags\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tags</div>");
            buf.append("<INPUT NAME=\"tags\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("published")) {
                String value = WebUtil.display(request.getParameter("published"));

                if ( forceHiddenSet.contains("published")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"published\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Published</div>");
            buf.append("<INPUT NAME=\"published\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hide")) {
                String value = WebUtil.display(request.getParameter("hide"));

                if ( forceHiddenSet.contains("hide")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hide\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide</div>");
            buf.append("<select name=\"hide\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("disable")) {
                String value = WebUtil.display(request.getParameter("disable"));

                if ( forceHiddenSet.contains("disable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"disable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Disable</div>");
            buf.append("<select name=\"disable\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("allowMultiple")) {
                String value = WebUtil.display(request.getParameter("allowMultiple"));

                if ( forceHiddenSet.contains("allowMultiple")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"allowMultiple\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Allow Multiple</div>");
            buf.append("<select name=\"allowMultiple\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("allowOwnAnswer")) {
                String value = WebUtil.display(request.getParameter("allowOwnAnswer"));

                if ( forceHiddenSet.contains("allowOwnAnswer")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"allowOwnAnswer\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Allow Own Answer</div>");
            buf.append("<select name=\"allowOwnAnswer\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("randomAnswer")) {
                String value = WebUtil.display(request.getParameter("randomAnswer"));

                if ( forceHiddenSet.contains("randomAnswer")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"randomAnswer\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Random Answer</div>");
            buf.append("<select name=\"randomAnswer\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hideComments")) {
                String value = WebUtil.display(request.getParameter("hideComments"));

                if ( forceHiddenSet.contains("hideComments")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hideComments\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide Comments</div>");
            buf.append("<select name=\"hideComments\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hideResults")) {
                String value = WebUtil.display(request.getParameter("hideResults"));

                if ( forceHiddenSet.contains("hideResults")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hideResults\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide Results</div>");
            buf.append("<select name=\"hideResults\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hideHomeLink")) {
                String value = WebUtil.display(request.getParameter("hideHomeLink"));

                if ( forceHiddenSet.contains("hideHomeLink")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hideHomeLink\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide Home Link</div>");
            buf.append("<select name=\"hideHomeLink\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("showSponsor")) {
                String value = WebUtil.display(request.getParameter("showSponsor"));

                if ( forceHiddenSet.contains("showSponsor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"showSponsor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Show Sponsor</div>");
            buf.append("<select name=\"showSponsor\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("useCookieForDup")) {
                String value = WebUtil.display(request.getParameter("useCookieForDup"));

                if ( forceHiddenSet.contains("useCookieForDup")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"useCookieForDup\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Use Cookie For Dup</div>");
            buf.append("<select name=\"useCookieForDup\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("repeatEveryDay")) {
                String value = WebUtil.display(request.getParameter("repeatEveryDay"));

                if ( forceHiddenSet.contains("repeatEveryDay")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"repeatEveryDay\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Repeat Every Day</div>");
            buf.append("<select name=\"repeatEveryDay\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("maxRepeatVote")) {
                String value = WebUtil.display(request.getParameter("maxRepeatVote"));

                if ( forceHiddenSet.contains("maxRepeatVote")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"maxRepeatVote\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Max Repeat Vote</div>");
            buf.append("<INPUT NAME=\"maxRepeatVote\" type=\"text\" size=\"10\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("numDaysOpen")) {
                String value = WebUtil.display(request.getParameter("numDaysOpen"));

                if ( forceHiddenSet.contains("numDaysOpen")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"numDaysOpen\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Num Days Open</div>");
            buf.append("<INPUT NAME=\"numDaysOpen\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeExpired")) {
                String value = WebUtil.display(request.getParameter("timeExpired"));

                if ( forceHiddenSet.contains("timeExpired")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeExpired\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/pollAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getlist")){

        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            Poll _Poll = null; 
            List list = PollDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _Poll = (Poll) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _Poll = (Poll) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _Poll = PollDS.getInstance().getById(id);
            }

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlisthtml");
            if (!returnList) {
                list = new ArrayList();
                list.add(_Poll);
            }


            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("type")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("category")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Category");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("title")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Title");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("description")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Description");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("question")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Question");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("tags")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Tags");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("published")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Published");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hide")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("disable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Disable");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("allowMultiple")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Allow Multiple");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("allowOwnAnswer")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Allow Own Answer");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("randomAnswer")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Random Answer");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hideComments")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide Comments");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hideResults")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide Results");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hideHomeLink")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide Home Link");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("showSponsor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Show Sponsor");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("useCookieForDup")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Use Cookie For Dup");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("repeatEveryDay")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Repeat Every Day");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("maxRepeatVote")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Max Repeat Vote");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("numDaysOpen")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Num Days Open");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Updated");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeExpired")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Expired");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                _Poll = (Poll) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("type")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("category")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getCategory()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("title")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getTitle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("description")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getDescription()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("question")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getQuestion()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("tags")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getTags()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("published")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getPublished()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hide")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Poll.getHide()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollAction.html?ef=true&id="+ _Poll.getId()+"&hide=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollAction.html?ef=true&id="+ _Poll.getId()+"&hide=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("disable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Poll.getDisable()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollAction.html?ef=true&id="+ _Poll.getId()+"&disable=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollAction.html?ef=true&id="+ _Poll.getId()+"&disable=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("allowMultiple")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getAllowMultiple()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("allowOwnAnswer")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getAllowOwnAnswer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("randomAnswer")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getRandomAnswer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hideComments")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getHideComments()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hideResults")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getHideResults()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hideHomeLink")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getHideHomeLink()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("showSponsor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getShowSponsor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("useCookieForDup")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getUseCookieForDup()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("repeatEveryDay")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getRepeatEveryDay()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("maxRepeatVote")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getMaxRepeatVote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("numDaysOpen")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getNumDaysOpen()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getTimeUpdated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeExpired")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Poll.getTimeExpired()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjsondt")){
            
            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            String arg = request.getParameter("ajaxOutArg");
            List list = PollDS.getInstance().getAll();

            int iDisplayStart = WebParamUtil.getIntValue(request.getParameter("iDisplayStart"));
            int iDisplayLength = WebParamUtil.getIntValue(request.getParameter("iDisplayLength"));
            int iColumns = WebParamUtil.getIntValue(request.getParameter("iColumns"));
            String sSearch = WebParamUtil.getStringValue(request.getParameter("sSearch"));
            int bEscapeRegex = WebParamUtil.getIntValue(request.getParameter("bEscapeRegex"));
            int iSortingCols = WebParamUtil.getIntValue(request.getParameter("iSortingCols"));
            String sEcho = WebParamUtil.getStringValue(request.getParameter("sEcho"));
            
            

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            top.put("sEcho", sEcho);
            top.put("iTotalRecords", list.size());
            top.put("iTotalDisplayRecords", list.size());
            
            JSONArray array = new JSONArray();

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Poll _Poll = (Poll) iterator.next();

                JSONArray json = new JSONArray();

                // Fields
                if ( ignoreFieldSet || fieldSet.contains("type")) 
                    json.put(_Poll.getType());
                if ( ignoreFieldSet || fieldSet.contains("category")) 
                    json.put(_Poll.getCategory());
                if ( ignoreFieldSet || fieldSet.contains("title")) 
                    json.put(_Poll.getTitle());
                if ( ignoreFieldSet || fieldSet.contains("description")) 
                    json.put(_Poll.getDescription());
                if ( ignoreFieldSet || fieldSet.contains("question")) 
                    json.put(_Poll.getQuestion());
                if ( ignoreFieldSet || fieldSet.contains("tags")) 
                    json.put(_Poll.getTags());
                if ( ignoreFieldSet || fieldSet.contains("published")) 
                    json.put(_Poll.getPublished());
                if ( ignoreFieldSet || fieldSet.contains("hide")) 
                    json.put(_Poll.getHide());
                if ( ignoreFieldSet || fieldSet.contains("disable")) 
                    json.put(_Poll.getDisable());
                if ( ignoreFieldSet || fieldSet.contains("allowMultiple")) 
                    json.put(_Poll.getAllowMultiple());
                if ( ignoreFieldSet || fieldSet.contains("allowOwnAnswer")) 
                    json.put(_Poll.getAllowOwnAnswer());
                if ( ignoreFieldSet || fieldSet.contains("randomAnswer")) 
                    json.put(_Poll.getRandomAnswer());
                if ( ignoreFieldSet || fieldSet.contains("hideComments")) 
                    json.put(_Poll.getHideComments());
                if ( ignoreFieldSet || fieldSet.contains("hideResults")) 
                    json.put(_Poll.getHideResults());
                if ( ignoreFieldSet || fieldSet.contains("hideHomeLink")) 
                    json.put(_Poll.getHideHomeLink());
                if ( ignoreFieldSet || fieldSet.contains("showSponsor")) 
                    json.put(_Poll.getShowSponsor());
                if ( ignoreFieldSet || fieldSet.contains("useCookieForDup")) 
                    json.put(_Poll.getUseCookieForDup());
                if ( ignoreFieldSet || fieldSet.contains("repeatEveryDay")) 
                    json.put(_Poll.getRepeatEveryDay());
                if ( ignoreFieldSet || fieldSet.contains("maxRepeatVote")) 
                    json.put(_Poll.getMaxRepeatVote());
                if ( ignoreFieldSet || fieldSet.contains("numDaysOpen")) 
                    json.put(_Poll.getNumDaysOpen());
                array.put(json);
            }

            top.put("aaData", array);
            
            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
            
        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            Poll _Poll = null; 
            List list = PollDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _Poll = (Poll) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _Poll = (Poll) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _Poll = PollDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last Poll=" + _Poll.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _Poll = (Poll) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("type")) 
			            json.put("type", ""+_Poll.getType());
		            if ( ignoreFieldSet || fieldSet.contains("category")) 
			            json.put("category", ""+_Poll.getCategory());
		            if ( ignoreFieldSet || fieldSet.contains("title")) 
			            json.put("title", ""+_Poll.getTitle());
		            if ( ignoreFieldSet || fieldSet.contains("description")) 
			            json.put("description", ""+_Poll.getDescription());
		            if ( ignoreFieldSet || fieldSet.contains("question")) 
			            json.put("question", ""+_Poll.getQuestion());
		            if ( ignoreFieldSet || fieldSet.contains("tags")) 
			            json.put("tags", ""+_Poll.getTags());
		            if ( ignoreFieldSet || fieldSet.contains("published")) 
			            json.put("published", ""+_Poll.getPublished());
		            if ( ignoreFieldSet || fieldSet.contains("hide")) 
			            json.put("hide", ""+_Poll.getHide());
		            if ( ignoreFieldSet || fieldSet.contains("disable")) 
			            json.put("disable", ""+_Poll.getDisable());
		            if ( ignoreFieldSet || fieldSet.contains("allowMultiple")) 
			            json.put("allowMultiple", ""+_Poll.getAllowMultiple());
		            if ( ignoreFieldSet || fieldSet.contains("allowOwnAnswer")) 
			            json.put("allowOwnAnswer", ""+_Poll.getAllowOwnAnswer());
		            if ( ignoreFieldSet || fieldSet.contains("randomAnswer")) 
			            json.put("randomAnswer", ""+_Poll.getRandomAnswer());
		            if ( ignoreFieldSet || fieldSet.contains("hideComments")) 
			            json.put("hideComments", ""+_Poll.getHideComments());
		            if ( ignoreFieldSet || fieldSet.contains("hideResults")) 
			            json.put("hideResults", ""+_Poll.getHideResults());
		            if ( ignoreFieldSet || fieldSet.contains("hideHomeLink")) 
			            json.put("hideHomeLink", ""+_Poll.getHideHomeLink());
		            if ( ignoreFieldSet || fieldSet.contains("showSponsor")) 
			            json.put("showSponsor", ""+_Poll.getShowSponsor());
		            if ( ignoreFieldSet || fieldSet.contains("useCookieForDup")) 
			            json.put("useCookieForDup", ""+_Poll.getUseCookieForDup());
		            if ( ignoreFieldSet || fieldSet.contains("repeatEveryDay")) 
			            json.put("repeatEveryDay", ""+_Poll.getRepeatEveryDay());
		            if ( ignoreFieldSet || fieldSet.contains("maxRepeatVote")) 
			            json.put("maxRepeatVote", ""+_Poll.getMaxRepeatVote());
		            if ( ignoreFieldSet || fieldSet.contains("numDaysOpen")) 
			            json.put("numDaysOpen", ""+_Poll.getNumDaysOpen());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_Poll.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonType = new JSONObject();
	            jsonType.put("name", "type");
	            jsonType.put("value", ""+_Poll.getType());
	            array.put(jsonType);

	            JSONObject jsonCategory = new JSONObject();
	            jsonCategory.put("name", "category");
	            jsonCategory.put("value", ""+_Poll.getCategory());
	            array.put(jsonCategory);

	            JSONObject jsonTitle = new JSONObject();
	            jsonTitle.put("name", "title");
	            jsonTitle.put("value", ""+_Poll.getTitle());
	            array.put(jsonTitle);

	            JSONObject jsonDescription = new JSONObject();
	            jsonDescription.put("name", "description");
	            jsonDescription.put("value", ""+_Poll.getDescription());
	            array.put(jsonDescription);

	            JSONObject jsonQuestion = new JSONObject();
	            jsonQuestion.put("name", "question");
	            jsonQuestion.put("value", ""+_Poll.getQuestion());
	            array.put(jsonQuestion);

	            JSONObject jsonTags = new JSONObject();
	            jsonTags.put("name", "tags");
	            jsonTags.put("value", ""+_Poll.getTags());
	            array.put(jsonTags);

	            JSONObject jsonPublished = new JSONObject();
	            jsonPublished.put("name", "published");
	            jsonPublished.put("value", ""+_Poll.getPublished());
	            array.put(jsonPublished);

	            JSONObject jsonHide = new JSONObject();
	            jsonHide.put("name", "hide");
	            jsonHide.put("value", ""+_Poll.getHide());
	            array.put(jsonHide);

	            JSONObject jsonDisable = new JSONObject();
	            jsonDisable.put("name", "disable");
	            jsonDisable.put("value", ""+_Poll.getDisable());
	            array.put(jsonDisable);

	            JSONObject jsonAllowMultiple = new JSONObject();
	            jsonAllowMultiple.put("name", "allowMultiple");
	            jsonAllowMultiple.put("value", ""+_Poll.getAllowMultiple());
	            array.put(jsonAllowMultiple);

	            JSONObject jsonAllowOwnAnswer = new JSONObject();
	            jsonAllowOwnAnswer.put("name", "allowOwnAnswer");
	            jsonAllowOwnAnswer.put("value", ""+_Poll.getAllowOwnAnswer());
	            array.put(jsonAllowOwnAnswer);

	            JSONObject jsonRandomAnswer = new JSONObject();
	            jsonRandomAnswer.put("name", "randomAnswer");
	            jsonRandomAnswer.put("value", ""+_Poll.getRandomAnswer());
	            array.put(jsonRandomAnswer);

	            JSONObject jsonHideComments = new JSONObject();
	            jsonHideComments.put("name", "hideComments");
	            jsonHideComments.put("value", ""+_Poll.getHideComments());
	            array.put(jsonHideComments);

	            JSONObject jsonHideResults = new JSONObject();
	            jsonHideResults.put("name", "hideResults");
	            jsonHideResults.put("value", ""+_Poll.getHideResults());
	            array.put(jsonHideResults);

	            JSONObject jsonHideHomeLink = new JSONObject();
	            jsonHideHomeLink.put("name", "hideHomeLink");
	            jsonHideHomeLink.put("value", ""+_Poll.getHideHomeLink());
	            array.put(jsonHideHomeLink);

	            JSONObject jsonShowSponsor = new JSONObject();
	            jsonShowSponsor.put("name", "showSponsor");
	            jsonShowSponsor.put("value", ""+_Poll.getShowSponsor());
	            array.put(jsonShowSponsor);

	            JSONObject jsonUseCookieForDup = new JSONObject();
	            jsonUseCookieForDup.put("name", "useCookieForDup");
	            jsonUseCookieForDup.put("value", ""+_Poll.getUseCookieForDup());
	            array.put(jsonUseCookieForDup);

	            JSONObject jsonRepeatEveryDay = new JSONObject();
	            jsonRepeatEveryDay.put("name", "repeatEveryDay");
	            jsonRepeatEveryDay.put("value", ""+_Poll.getRepeatEveryDay());
	            array.put(jsonRepeatEveryDay);

	            JSONObject jsonMaxRepeatVote = new JSONObject();
	            jsonMaxRepeatVote.put("name", "maxRepeatVote");
	            jsonMaxRepeatVote.put("value", ""+_Poll.getMaxRepeatVote());
	            array.put(jsonMaxRepeatVote);

	            JSONObject jsonNumDaysOpen = new JSONObject();
	            jsonNumDaysOpen.put("name", "numDaysOpen");
	            jsonNumDaysOpen.put("value", ""+_Poll.getNumDaysOpen());
	            array.put(jsonNumDaysOpen);


    	        top.put("fields", array);
			}


            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return false;
    }
    
    protected PollDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollAction.class);
}
