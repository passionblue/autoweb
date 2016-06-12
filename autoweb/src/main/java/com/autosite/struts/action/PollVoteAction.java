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

import com.autosite.db.PollVote;
import com.autosite.ds.PollVoteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PollVoteForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class PollVoteAction extends AutositeCoreAction {

    public PollVoteAction(){
        m_ds = PollVoteDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        PollVoteForm _PollVoteForm = (PollVoteForm) form;
        HttpSession session = request.getSession();

        setPage(session, "poll_result_single");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "poll_display_default");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            setPage(session, "poll_display_default");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "poll_display_default");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        PollVote _PollVote = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PollVote = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollVote _PollVote = m_ds.getById(cid);

            if (_PollVote == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
	            setPage(session, "poll_display_default");
                return mapping.findForward("default");
            }

            if (_PollVote.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollVote.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "poll_display_default");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PollVoteForm, _PollVote);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

				//Default error page but will be overridden by exception specific error page
	            setPage(session, "poll_display_default");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

			//Default error page but will be overridden by exception specific error page
            setPage(session, "poll_display_default");
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollVote _PollVote = m_ds.getById(cid);

            if (_PollVote == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
			//Default error page but will be overridden by exception specific error page
            setPage(session, "poll_display_default");
                return mapping.findForward("default");
            }

            if (_PollVote.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollVote.getSiteId()); 
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "poll_display_default");
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PollVote);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
	            if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
			//Default error page but will be overridden by exception specific error page
            setPage(session, "poll_display_default");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            setPage(session, "poll_display_default");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PollVote _PollVote = m_ds.getById(cid);

            if (_PollVote == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "poll_display_default");
                return mapping.findForward("default");
            }

            if (_PollVote.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PollVote.getSiteId()); 
	            setPage(session, "poll_display_default");
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PollVote);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
			//Default error page but will be overridden by exception specific error page
            setPage(session, "poll_display_default");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PollVote);
            try { 
                m_actionExtent.afterDelete(request, response, _PollVote);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
				//Default error page but will be overridden by exception specific error page
	            setPage(session, "poll_display_default");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            setPage(session, "poll_center");
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new PollVote" );
            PollVote _PollVoteNew = new PollVote();   

            // Setting IDs for the object
            _PollVoteNew.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _PollVoteNew.setUserId(user.getId());
            }

            _PollVoteNew.setPollId(WebParamUtil.getLongValue(_PollVoteForm.getPollId()));
            m_logger.debug("setting PollId=" +_PollVoteForm.getPollId());
            _PollVoteNew.setAnswer(WebParamUtil.getLongValue(_PollVoteForm.getAnswer()));
            m_logger.debug("setting Answer=" +_PollVoteForm.getAnswer());
            _PollVoteNew.setMultipleAnswer(WebParamUtil.getStringValue(_PollVoteForm.getMultipleAnswer()));
            m_logger.debug("setting MultipleAnswer=" +_PollVoteForm.getMultipleAnswer());
            _PollVoteNew.setByGuest(WebParamUtil.getIntValue(_PollVoteForm.getByGuest()));
            m_logger.debug("setting ByGuest=" +_PollVoteForm.getByGuest());
            _PollVoteNew.setIpAddress(WebParamUtil.getStringValue(_PollVoteForm.getIpAddress()));
            m_logger.debug("setting IpAddress=" +_PollVoteForm.getIpAddress());
            _PollVoteNew.setPcid(WebParamUtil.getStringValue(_PollVoteForm.getPcid()));
            m_logger.debug("setting Pcid=" +_PollVoteForm.getPcid());
            _PollVoteNew.setDupCheckKey(WebParamUtil.getStringValue(_PollVoteForm.getDupCheckKey()));
            m_logger.debug("setting DupCheckKey=" +_PollVoteForm.getDupCheckKey());
            _PollVoteNew.setNote(WebParamUtil.getStringValue(_PollVoteForm.getNote()));
            m_logger.debug("setting Note=" +_PollVoteForm.getNote());
            _PollVoteNew.setOwnAnswer(WebParamUtil.getStringValue(_PollVoteForm.getOwnAnswer()));
            m_logger.debug("setting OwnAnswer=" +_PollVoteForm.getOwnAnswer());
            _PollVoteNew.setTimeCreated(WebParamUtil.getDateValue(_PollVoteForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PollVoteForm.getTimeCreated());

            try{
                m_actionExtent.beforeAdd(request, response, _PollVoteNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, "poll_display_default");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PollVoteNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PollVoteNew);
            try{
                m_actionExtent.afterAdd(request, response, _PollVoteNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
	            setPage(session, "poll_display_default");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "poll_vote_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            setPage(session, "poll_display_default");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PollVoteForm _PollVoteForm, PollVote _PollVote) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollVote _PollVote = m_ds.getById(cid);

        m_logger.debug("Before update " + PollVoteDS.objectToString(_PollVote));

        _PollVote.setPollId(WebParamUtil.getLongValue(_PollVoteForm.getPollId()));
        _PollVote.setAnswer(WebParamUtil.getLongValue(_PollVoteForm.getAnswer()));
        _PollVote.setMultipleAnswer(WebParamUtil.getStringValue(_PollVoteForm.getMultipleAnswer()));
        _PollVote.setByGuest(WebParamUtil.getIntValue(_PollVoteForm.getByGuest()));
        _PollVote.setIpAddress(WebParamUtil.getStringValue(_PollVoteForm.getIpAddress()));
        _PollVote.setPcid(WebParamUtil.getStringValue(_PollVoteForm.getPcid()));
        _PollVote.setDupCheckKey(WebParamUtil.getStringValue(_PollVoteForm.getDupCheckKey()));
        _PollVote.setNote(WebParamUtil.getStringValue(_PollVoteForm.getNote()));
        _PollVote.setOwnAnswer(WebParamUtil.getStringValue(_PollVoteForm.getOwnAnswer()));

        m_actionExtent.beforeUpdate(request, response, _PollVote);
        m_ds.update(_PollVote);
        m_actionExtent.afterUpdate(request, response, _PollVote);
        m_logger.debug("After update " + PollVoteDS.objectToString(_PollVote));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PollVote _PollVote) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollVote _PollVote = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pollId"))) {
            m_logger.debug("updating param pollId from " +_PollVote.getPollId() + "->" + request.getParameter("pollId"));
            _PollVote.setPollId(WebParamUtil.getLongValue(request.getParameter("pollId")));
        }
        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_PollVote.getUserId() + "->" + request.getParameter("userId"));
            _PollVote.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("answer"))) {
            m_logger.debug("updating param answer from " +_PollVote.getAnswer() + "->" + request.getParameter("answer"));
            _PollVote.setAnswer(WebParamUtil.getLongValue(request.getParameter("answer")));
        }
        if (!isMissing(request.getParameter("multipleAnswer"))) {
            m_logger.debug("updating param multipleAnswer from " +_PollVote.getMultipleAnswer() + "->" + request.getParameter("multipleAnswer"));
            _PollVote.setMultipleAnswer(WebParamUtil.getStringValue(request.getParameter("multipleAnswer")));
        }
        if (!isMissing(request.getParameter("byGuest"))) {
            m_logger.debug("updating param byGuest from " +_PollVote.getByGuest() + "->" + request.getParameter("byGuest"));
            _PollVote.setByGuest(WebParamUtil.getIntValue(request.getParameter("byGuest")));
        }
        if (!isMissing(request.getParameter("ipAddress"))) {
            m_logger.debug("updating param ipAddress from " +_PollVote.getIpAddress() + "->" + request.getParameter("ipAddress"));
            _PollVote.setIpAddress(WebParamUtil.getStringValue(request.getParameter("ipAddress")));
        }
        if (!isMissing(request.getParameter("pcid"))) {
            m_logger.debug("updating param pcid from " +_PollVote.getPcid() + "->" + request.getParameter("pcid"));
            _PollVote.setPcid(WebParamUtil.getStringValue(request.getParameter("pcid")));
        }
        if (!isMissing(request.getParameter("dupCheckKey"))) {
            m_logger.debug("updating param dupCheckKey from " +_PollVote.getDupCheckKey() + "->" + request.getParameter("dupCheckKey"));
            _PollVote.setDupCheckKey(WebParamUtil.getStringValue(request.getParameter("dupCheckKey")));
        }
        if (!isMissing(request.getParameter("note"))) {
            m_logger.debug("updating param note from " +_PollVote.getNote() + "->" + request.getParameter("note"));
            _PollVote.setNote(WebParamUtil.getStringValue(request.getParameter("note")));
        }
        if (!isMissing(request.getParameter("ownAnswer"))) {
            m_logger.debug("updating param ownAnswer from " +_PollVote.getOwnAnswer() + "->" + request.getParameter("ownAnswer"));
            _PollVote.setOwnAnswer(WebParamUtil.getStringValue(request.getParameter("ownAnswer")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PollVote.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PollVote.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _PollVote);
        m_ds.update(_PollVote);
        m_actionExtent.afterUpdate(request, response, _PollVote);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PollVote _PollVote) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PollVote _PollVote = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pollId"))) {
			return String.valueOf(_PollVote.getPollId());
        }
        if (!isMissing(request.getParameter("userId"))) {
			return String.valueOf(_PollVote.getUserId());
        }
        if (!isMissing(request.getParameter("answer"))) {
			return String.valueOf(_PollVote.getAnswer());
        }
        if (!isMissing(request.getParameter("multipleAnswer"))) {
			return String.valueOf(_PollVote.getMultipleAnswer());
        }
        if (!isMissing(request.getParameter("byGuest"))) {
			return String.valueOf(_PollVote.getByGuest());
        }
        if (!isMissing(request.getParameter("ipAddress"))) {
			return String.valueOf(_PollVote.getIpAddress());
        }
        if (!isMissing(request.getParameter("pcid"))) {
			return String.valueOf(_PollVote.getPcid());
        }
        if (!isMissing(request.getParameter("dupCheckKey"))) {
			return String.valueOf(_PollVote.getDupCheckKey());
        }
        if (!isMissing(request.getParameter("note"))) {
			return String.valueOf(_PollVote.getNote());
        }
        if (!isMissing(request.getParameter("ownAnswer"))) {
			return String.valueOf(_PollVote.getOwnAnswer());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_PollVote.getTimeCreated());
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
            PollVote _PollVote = PollVoteDS.getInstance().getById(id);
            if (_PollVote == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PollVote);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/pollVoteAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("pollId")) {
                String value = WebUtil.display(request.getParameter("pollId"));

                if ( forceHiddenSet.contains("pollId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Poll Id</div>");
            buf.append("<INPUT NAME=\"pollId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("answer")) {
                String value = WebUtil.display(request.getParameter("answer"));

                if ( forceHiddenSet.contains("answer")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"answer\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Answer</div>");
            buf.append("<INPUT NAME=\"answer\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("multipleAnswer")) {
                String value = WebUtil.display(request.getParameter("multipleAnswer"));

                if ( forceHiddenSet.contains("multipleAnswer")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"multipleAnswer\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Multiple Answer</div>");
            buf.append("<INPUT NAME=\"multipleAnswer\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("byGuest")) {
                String value = WebUtil.display(request.getParameter("byGuest"));

                if ( forceHiddenSet.contains("byGuest")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"byGuest\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">By Guest</div>");
            buf.append("<select name=\"byGuest\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("ipAddress")) {
                String value = WebUtil.display(request.getParameter("ipAddress"));

                if ( forceHiddenSet.contains("ipAddress")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ipAddress\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ip Address</div>");
            buf.append("<INPUT NAME=\"ipAddress\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pcid")) {
                String value = WebUtil.display(request.getParameter("pcid"));

                if ( forceHiddenSet.contains("pcid")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pcid\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Pcid</div>");
            buf.append("<INPUT NAME=\"pcid\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("dupCheckKey")) {
                String value = WebUtil.display(request.getParameter("dupCheckKey"));

                if ( forceHiddenSet.contains("dupCheckKey")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"dupCheckKey\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Dup Check Key</div>");
            buf.append("<INPUT NAME=\"dupCheckKey\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("note")) {
                String value = WebUtil.display(request.getParameter("note"));

                if ( forceHiddenSet.contains("note")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"note\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note</div>");
            buf.append("<INPUT NAME=\"note\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("ownAnswer")) {
                String value = WebUtil.display(request.getParameter("ownAnswer"));

                if ( forceHiddenSet.contains("ownAnswer")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ownAnswer\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Own Answer</div>");
            buf.append("<INPUT NAME=\"ownAnswer\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/pollVoteAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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
            PollVote _PollVote = null; 
            List list = PollVoteDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _PollVote = (PollVote) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PollVote = (PollVote) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PollVote = PollVoteDS.getInstance().getById(id);
            }

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlisthtml");
            if (!returnList) {
                list = new ArrayList();
                list.add(_PollVote);
            }


            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("answer")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Answer");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("multipleAnswer")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Multiple Answer");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("byGuest")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("By Guest");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("ipAddress")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ip Address");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pcid")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Pcid");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("dupCheckKey")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Dup Check Key");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("ownAnswer")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Own Answer");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                _PollVote = (PollVote) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("answer")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getAnswer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("multipleAnswer")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getMultipleAnswer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("byGuest")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_PollVote.getByGuest()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollVoteAction.html?ef=true&id="+ _PollVote.getId()+"&byGuest=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/pollVoteAction.html?ef=true&id="+ _PollVote.getId()+"&byGuest=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("ipAddress")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getIpAddress()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pcid")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getPcid()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("dupCheckKey")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getDupCheckKey()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("ownAnswer")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getOwnAnswer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PollVote.getTimeCreated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            PollVote _PollVote = null; 
            List list = PollVoteDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _PollVote = (PollVote) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PollVote = (PollVote) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PollVote = PollVoteDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last PollVote=" + _PollVote.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _PollVote = (PollVote) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("pollId")) 
			            json.put("pollId", ""+_PollVote.getPollId());
		            if ( ignoreFieldSet || fieldSet.contains("answer")) 
			            json.put("answer", ""+_PollVote.getAnswer());
		            if ( ignoreFieldSet || fieldSet.contains("multipleAnswer")) 
			            json.put("multipleAnswer", ""+_PollVote.getMultipleAnswer());
		            if ( ignoreFieldSet || fieldSet.contains("byGuest")) 
			            json.put("byGuest", ""+_PollVote.getByGuest());
		            if ( ignoreFieldSet || fieldSet.contains("ipAddress")) 
			            json.put("ipAddress", ""+_PollVote.getIpAddress());
		            if ( ignoreFieldSet || fieldSet.contains("pcid")) 
			            json.put("pcid", ""+_PollVote.getPcid());
		            if ( ignoreFieldSet || fieldSet.contains("dupCheckKey")) 
			            json.put("dupCheckKey", ""+_PollVote.getDupCheckKey());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_PollVote.getNote());
		            if ( ignoreFieldSet || fieldSet.contains("ownAnswer")) 
			            json.put("ownAnswer", ""+_PollVote.getOwnAnswer());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_PollVote.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonPollId = new JSONObject();
	            jsonPollId.put("name", "pollId");
	            jsonPollId.put("value", ""+_PollVote.getPollId());
	            array.put(jsonPollId);

	            JSONObject jsonAnswer = new JSONObject();
	            jsonAnswer.put("name", "answer");
	            jsonAnswer.put("value", ""+_PollVote.getAnswer());
	            array.put(jsonAnswer);

	            JSONObject jsonMultipleAnswer = new JSONObject();
	            jsonMultipleAnswer.put("name", "multipleAnswer");
	            jsonMultipleAnswer.put("value", ""+_PollVote.getMultipleAnswer());
	            array.put(jsonMultipleAnswer);

	            JSONObject jsonByGuest = new JSONObject();
	            jsonByGuest.put("name", "byGuest");
	            jsonByGuest.put("value", ""+_PollVote.getByGuest());
	            array.put(jsonByGuest);

	            JSONObject jsonIpAddress = new JSONObject();
	            jsonIpAddress.put("name", "ipAddress");
	            jsonIpAddress.put("value", ""+_PollVote.getIpAddress());
	            array.put(jsonIpAddress);

	            JSONObject jsonPcid = new JSONObject();
	            jsonPcid.put("name", "pcid");
	            jsonPcid.put("value", ""+_PollVote.getPcid());
	            array.put(jsonPcid);

	            JSONObject jsonDupCheckKey = new JSONObject();
	            jsonDupCheckKey.put("name", "dupCheckKey");
	            jsonDupCheckKey.put("value", ""+_PollVote.getDupCheckKey());
	            array.put(jsonDupCheckKey);

	            JSONObject jsonNote = new JSONObject();
	            jsonNote.put("name", "note");
	            jsonNote.put("value", ""+_PollVote.getNote());
	            array.put(jsonNote);

	            JSONObject jsonOwnAnswer = new JSONObject();
	            jsonOwnAnswer.put("name", "ownAnswer");
	            jsonOwnAnswer.put("value", ""+_PollVote.getOwnAnswer());
	            array.put(jsonOwnAnswer);


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
    
    protected PollVoteDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollVoteAction.class);
}
