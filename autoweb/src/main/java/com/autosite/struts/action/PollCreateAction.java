package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeUser;
import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.db.Site;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollDS;
import com.autosite.ds.SiteDS;
import com.autosite.lab.WordGenerator;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.PollForm;
import com.autosite.util.PollUtil;
import com.autosite.util.poll.PollResult;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

/**
 * This class is not based on generated codes. Poll actions are quie complicated to rely on generated codes so created total customized action class. 
 * 
 * 
 *
 */

public class PollCreateAction extends AutositeCoreAction {

    public PollCreateAction(){
        m_ds = PollDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PollForm _PollForm = (PollForm) form;
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
            Poll _Poll = m_ds.getById(cid);

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
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            
            //setPage(session, "poll_home");
            return mapping.findForward("default");
    
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

            m_logger.info("Creating new Poll" );
            Poll _Poll = new Poll();   

            // Setting IDs for the object
            _Poll.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _Poll.setUserId(user.getId());
            }

            _Poll.setType(WebParamUtil.getIntValue(_PollForm.getType()));
            m_logger.debug("setting Type=" +_PollForm.getType());
            _Poll.setCategory(WebParamUtil.getStringValue(_PollForm.getCategory()));
            m_logger.debug("setting Category=" +_PollForm.getCategory());
            _Poll.setTitle(WebParamUtil.getStringValue(_PollForm.getTitle()));
            m_logger.debug("setting Title=" +_PollForm.getTitle());
            _Poll.setDescription(WebParamUtil.getStringValue(_PollForm.getDescription()));
            m_logger.debug("setting Description=" +_PollForm.getDescription());
            _Poll.setQuestion(WebParamUtil.getStringValue(_PollForm.getQuestion()));
            m_logger.debug("setting Question=" +_PollForm.getQuestion());
            _Poll.setTags(WebParamUtil.getStringValue(_PollForm.getTags()));
            m_logger.debug("setting Tags=" +_PollForm.getTags());
            _Poll.setPublished(WebParamUtil.getIntValue(_PollForm.getPublished()));
            m_logger.debug("setting Published=" +_PollForm.getPublished());
            _Poll.setHide(WebParamUtil.getIntValue(_PollForm.getHide()));
            m_logger.debug("setting Hide=" +_PollForm.getHide());
            _Poll.setDisable(WebParamUtil.getIntValue(_PollForm.getDisable()));
            m_logger.debug("setting Disable=" +_PollForm.getDisable());
            _Poll.setAllowMultiple(WebParamUtil.getCheckboxValue(_PollForm.getAllowMultiple()));
            m_logger.debug("setting AllowMultiple=" +_PollForm.getAllowMultiple());
            _Poll.setAllowOwnAnswer(WebParamUtil.getCheckboxValue(_PollForm.getAllowOwnAnswer()));
            m_logger.debug("setting AllowOwnAnswer=" +_PollForm.getAllowOwnAnswer());
            _Poll.setRandomAnswer(WebParamUtil.getCheckboxValue(_PollForm.getRandomAnswer()));
            m_logger.debug("setting RandomAnswer=" +_PollForm.getRandomAnswer());
            _Poll.setHideComments(WebParamUtil.getCheckboxValue(_PollForm.getHideComments()));
            m_logger.debug("setting HideComments=" +_PollForm.getHideComments());
            _Poll.setHideResults(WebParamUtil.getCheckboxValue(_PollForm.getHideResults()));
            m_logger.debug("setting HideResults=" +_PollForm.getHideResults());
            _Poll.setHideHomeLink(WebParamUtil.getCheckboxValue(_PollForm.getHideHomeLink()));
            m_logger.debug("setting HideHomeLink=" +_PollForm.getHideHomeLink());
            _Poll.setShowSponsor(WebParamUtil.getCheckboxValue(_PollForm.getShowSponsor()));
            m_logger.debug("setting ShowSponsor=" +_PollForm.getShowSponsor());
            _Poll.setUseCookieForDup(WebParamUtil.getCheckboxValue(_PollForm.getUseCookieForDup()));
            m_logger.debug("setting UseCookieForDup=" +_PollForm.getUseCookieForDup());
            _Poll.setRepeatEveryDay(WebParamUtil.getCheckboxValue(_PollForm.getRepeatEveryDay()));
            m_logger.debug("setting RepeatEveryDay=" +_PollForm.getRepeatEveryDay());
            _Poll.setMaxRepeatVote(WebParamUtil.getIntValue(_PollForm.getMaxRepeatVote()));
            m_logger.debug("setting MaxRepeatVote=" +_PollForm.getMaxRepeatVote());
            _Poll.setNumDaysOpen(WebParamUtil.getIntValue(_PollForm.getNumDaysOpen()));
            m_logger.debug("setting NumDaysOpen=" +_PollForm.getNumDaysOpen());
            _Poll.setTimeCreated(WebParamUtil.getDateValue(_PollForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PollForm.getTimeCreated());
            _Poll.setTimeUpdated(WebParamUtil.getDateValue(_PollForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PollForm.getTimeUpdated());
            _Poll.setTimeExpired(WebParamUtil.getDateValue(_PollForm.getTimeExpired()));
            m_logger.debug("setting TimeExpired=" +_PollForm.getTimeExpired());
            
            
            
            while(true){
                String serial = new WordGenerator(5).getRandom(20);
                if (PollDS.getInstance().getBySiteIdToSerial(site.getId(), serial) == null) {
                    _Poll.setSerial(serial);
                    break;
                } else {
                    // Try it again
                }
            }
            
            // Validate Poll Answsers
            int answerIdx = 1;
            List answers = new ArrayList();
            if ( _Poll.getType() == PollUtil.TYPE_DEFAULT) {
                while(true){
                    String answerTxt = request.getParameter("a" + answerIdx);
                    if ( WebUtil.isNull(answerTxt)) break;
                    answers.add(request.getParameter("a" + answerIdx));
                    answerIdx++;
                }
                m_logger.debug(answers);

                if ( answers.size() <= 1 ){
                    m_logger.info("Aborting because need more answer. " + answers.size());
                    sessionErrorText(session, "Poll answers must be at least 2");
                    return mapping.findForward("default");
                }
            } else if (_Poll.getType() == PollUtil.TYPE_IMAGE_ANSWERS) { 
                while(true){
                    if ( request.getParameter("a" + answerIdx) == null) break;
                    answers.add(request.getParameter("a" + answerIdx));
                    answerIdx++;
                }
            }

            m_logger.debug(answers);
            
            try{
                m_actionExtent.beforeAdd(request, response, _Poll);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_Poll);
            try{
                m_actionExtent.afterAdd(request, response, _Poll);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            // Persist Poll Answers
            
            if ( _Poll.getType() == PollUtil.TYPE_DEFAULT) {
                
                answerIdx = 1;
                PollAnswerDS ansDS = PollAnswerDS.getInstance();
                for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
                    String answerTxt = (String) iterator.next();
                    
                    String imageUrl = request.getParameter("m" + answerIdx);

                    PollAnswer pollAns = new PollAnswer();
                    pollAns.setSiteId(site.getId());
                    pollAns.setPollId(_Poll.getId());
                    pollAns.setAnswerNum(answerIdx);
                    pollAns.setText(answerTxt);
                    
                    if (!WebUtil.isNull(imageUrl)){
                        pollAns.setImageUrl(imageUrl);
                    }
                    
                    ansDS.put(pollAns);
                    m_logger.info("Answer saved " + answerTxt + " : " + answerIdx);
                    answerIdx++;
                }
                
                if ( WebUtil.isTrue(_Poll.getAllowOwnAnswer())) {

                    PollAnswer pollAns = new PollAnswer();
                    pollAns.setSiteId(site.getId());
                    pollAns.setPollId(_Poll.getId());
                    pollAns.setAnswerNum(answerIdx);
                    pollAns.setText("Own Answer");
                    pollAns.setOwnAnswer(1);
                    
                    ansDS.put(pollAns);
                    
                    m_logger.debug("OwnAnswer added with idx=" + answerIdx);
                }

            } else if ( _Poll.getType() == PollUtil.TYPE_IMAGE_ANSWERS) {
                
                answerIdx = 1;
                PollAnswerDS ansDS = PollAnswerDS.getInstance();
                m_logger.debug(answers);

                for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
                    String answerTxt = (String) iterator.next();
                    
                    String imageUrl = request.getParameter("m" + answerIdx);
                    
                    PollAnswer pollAns = new PollAnswer();
                    pollAns.setSiteId(site.getId());
                    pollAns.setPollId(_Poll.getId());
                    pollAns.setAnswerNum(answerIdx);
                    pollAns.setText(answerTxt);
                    
                    if (!WebUtil.isNull(imageUrl)){
                        pollAns.setImageUrl(imageUrl);
                    }
                    
                    ansDS.put(pollAns);
                    m_logger.info("Answer saved " + answerTxt + " : " + answerIdx);
                    answerIdx++;
                }
                
            } else if ( _Poll.getType() == PollUtil.TYPE_SCALE) {
                
                answerIdx = 1;
                PollAnswerDS ansDS = PollAnswerDS.getInstance();

                for (int i = 1; i <= 10; i++) {
                    
                    PollAnswer pollAns = new PollAnswer();
                    pollAns.setSiteId(site.getId());
                    pollAns.setPollId(_Poll.getId());
                    pollAns.setAnswerNum(answerIdx);
                    pollAns.setText(""+answerIdx);
                    
                    ansDS.put(pollAns);
                    m_logger.info("Answer saved " + answerIdx + " : " + answerIdx);
                    answerIdx++;
                }
                
            } else if ( _Poll.getType() == PollUtil.TYPE_YES_NO) {
                
                PollAnswerDS ansDS = PollAnswerDS.getInstance();
                    
                
                String yesText = "Yes";
                if ( WebUtil.isNotNull(request.getParameter("m1")))
                    yesText = request.getParameter("m1");
                
                PollAnswer pollAnsYes = new PollAnswer();
                pollAnsYes.setSiteId(site.getId());
                pollAnsYes.setPollId(_Poll.getId());
                pollAnsYes.setAnswerNum(1);
                pollAnsYes.setText(yesText);
                
                ansDS.put(pollAnsYes);
                m_logger.info("Answer saved Yes. Text=" + yesText);
                
                String noText = "Yes";
                if ( WebUtil.isNotNull(request.getParameter("m2")))
                    noText = request.getParameter("m2");

                PollAnswer pollAnsNo = new PollAnswer();
                pollAnsNo.setSiteId(site.getId());
                pollAnsNo.setPollId(_Poll.getId());
                pollAnsNo.setAnswerNum(2);
                pollAnsNo.setText(noText);
                
                ansDS.put(pollAnsNo);
                m_logger.info("Answer saved No. Text=" + noText);
                
            }

            // Expiring Time Set

            int numDaysOpen = WebParamUtil.getIntValue(_PollForm.getNumDaysOpen());
            
            _Poll.setTimeExpired(PollUtil.getExpiringTime(new TimeNow(), numDaysOpen));
            m_logger.debug("setting TimeExpired=" +_PollForm.getTimeExpired());
            
            sessionTopText(session, "New Poll has been created.");
            setPage(session, "poll_center");
            
            
            // Initiate Poll Result object;
            
            PollResult.getPollResult(_Poll.getId());
            
            
            webProc.complete();
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
        
        _Poll.setTimeExpired(PollUtil.getExpiringTime(_Poll.getTimeCreated(), WebParamUtil.getIntValue(_PollForm.getNumDaysOpen())));
        m_logger.debug("setting TimeExpired=" +_PollForm.getTimeExpired());

        _Poll.setTimeUpdated(new TimeNow());
        
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

    
    
    

    protected boolean loginRequired() {
        return true;
    }

    protected PollDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PollCreateAction.class);
}
