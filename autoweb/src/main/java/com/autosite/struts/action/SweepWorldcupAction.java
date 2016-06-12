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

import com.autosite.db.SweepWorldcup;
import com.autosite.ds.SweepWorldcupDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.SweepWorldcupForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class SweepWorldcupAction extends AutositeCoreAction {

    public SweepWorldcupAction(){
        m_ds = SweepWorldcupDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SweepWorldcupForm _SweepWorldcupForm = (SweepWorldcupForm) form;
        HttpSession session = request.getSession();

        setPage(session, "sweep_worldcup_home");

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
            sessionErrorText(session, e.getMessage());
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        SweepWorldcup _SweepWorldcup = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SweepWorldcup = m_ds.getById(cid);
		}

		if (autoUser == null) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.warn("User object not set. ");
            return mapping.findForward("default");
        }

        if (!hasRequestValue(request, "add", "true")) {

            if ( autoUser.getUserType() != Constants.UserSuperAdmin && autoUser.getId() != _SweepWorldcup.getUserId() ) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Access Exception. Delete failed. Invalid user has attempted to delete for user " + _SweepWorldcup.getUserId());
                return mapping.findForward("default");
            }
		}

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //SweepWorldcup _SweepWorldcup = m_ds.getById(cid);

            if (_SweepWorldcup == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SweepWorldcup.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepWorldcup.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _SweepWorldcupForm, _SweepWorldcup);
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
            //SweepWorldcup _SweepWorldcup = m_ds.getById(cid);

            if (_SweepWorldcup == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SweepWorldcup.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepWorldcup.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SweepWorldcup);
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
            //SweepWorldcup _SweepWorldcup = m_ds.getById(cid);

            if (_SweepWorldcup == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SweepWorldcup.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SweepWorldcup.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _SweepWorldcup);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_SweepWorldcup);
            try { 
                m_actionExtent.afterDelete(request, response, _SweepWorldcup);
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

            m_logger.info("Creating new SweepWorldcup" );
            SweepWorldcup _SweepWorldcupNew = new SweepWorldcup();   

            // Setting IDs for the object
            _SweepWorldcupNew.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _SweepWorldcupNew.setUserId(user.getId());
            }

            _SweepWorldcupNew.setTeamCode(WebParamUtil.getStringValue(_SweepWorldcupForm.getTeamCode()));
            m_logger.debug("setting TeamCode=" +_SweepWorldcupForm.getTeamCode());
            _SweepWorldcupNew.setTeamName(WebParamUtil.getStringValue(_SweepWorldcupForm.getTeamName()));
            m_logger.debug("setting TeamName=" +_SweepWorldcupForm.getTeamName());
            _SweepWorldcupNew.setGroupNum(WebParamUtil.getStringValue(_SweepWorldcupForm.getGroupNum()));
            m_logger.debug("setting GroupNum=" +_SweepWorldcupForm.getGroupNum());
            _SweepWorldcupNew.setGame1(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame1()));
            m_logger.debug("setting Game1=" +_SweepWorldcupForm.getGame1());
            _SweepWorldcupNew.setGame1Score(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame1Score()));
            m_logger.debug("setting Game1Score=" +_SweepWorldcupForm.getGame1Score());
            _SweepWorldcupNew.setGame1ScoreOpp(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame1ScoreOpp()));
            m_logger.debug("setting Game1ScoreOpp=" +_SweepWorldcupForm.getGame1ScoreOpp());
            _SweepWorldcupNew.setGame2(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame2()));
            m_logger.debug("setting Game2=" +_SweepWorldcupForm.getGame2());
            _SweepWorldcupNew.setGame2Score(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame2Score()));
            m_logger.debug("setting Game2Score=" +_SweepWorldcupForm.getGame2Score());
            _SweepWorldcupNew.setGame2ScoreOpp(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame2ScoreOpp()));
            m_logger.debug("setting Game2ScoreOpp=" +_SweepWorldcupForm.getGame2ScoreOpp());
            _SweepWorldcupNew.setGame3(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame3()));
            m_logger.debug("setting Game3=" +_SweepWorldcupForm.getGame3());
            _SweepWorldcupNew.setGame3Score(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame3Score()));
            m_logger.debug("setting Game3Score=" +_SweepWorldcupForm.getGame3Score());
            _SweepWorldcupNew.setGame3ScoreOpp(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame3ScoreOpp()));
            m_logger.debug("setting Game3ScoreOpp=" +_SweepWorldcupForm.getGame3ScoreOpp());
            _SweepWorldcupNew.setQuarterFinalTeams(WebParamUtil.getStringValue(_SweepWorldcupForm.getQuarterFinalTeams()));
            m_logger.debug("setting QuarterFinalTeams=" +_SweepWorldcupForm.getQuarterFinalTeams());
            _SweepWorldcupNew.setSemiFinalTeams(WebParamUtil.getStringValue(_SweepWorldcupForm.getSemiFinalTeams()));
            m_logger.debug("setting SemiFinalTeams=" +_SweepWorldcupForm.getSemiFinalTeams());
            _SweepWorldcupNew.setFinalTeams(WebParamUtil.getStringValue(_SweepWorldcupForm.getFinalTeams()));
            m_logger.debug("setting FinalTeams=" +_SweepWorldcupForm.getFinalTeams());
            _SweepWorldcupNew.setChampion(WebParamUtil.getStringValue(_SweepWorldcupForm.getChampion()));
            m_logger.debug("setting Champion=" +_SweepWorldcupForm.getChampion());
            _SweepWorldcupNew.setFinalScoreWin(WebParamUtil.getIntValue(_SweepWorldcupForm.getFinalScoreWin()));
            m_logger.debug("setting FinalScoreWin=" +_SweepWorldcupForm.getFinalScoreWin());
            _SweepWorldcupNew.setFinalScoreLose(WebParamUtil.getIntValue(_SweepWorldcupForm.getFinalScoreLose()));
            m_logger.debug("setting FinalScoreLose=" +_SweepWorldcupForm.getFinalScoreLose());
            _SweepWorldcupNew.setTimeCreated(WebParamUtil.getDateValue(_SweepWorldcupForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SweepWorldcupForm.getTimeCreated());
            _SweepWorldcupNew.setTimeUpdated(WebParamUtil.getDateValue(_SweepWorldcupForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SweepWorldcupForm.getTimeUpdated());

            
            try{
                m_actionExtent.beforeAdd(request, response, _SweepWorldcupNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.put(_SweepWorldcupNew);
            try{
                m_actionExtent.afterAdd(request, response, _SweepWorldcupNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "sweep_worldcup_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SweepWorldcupForm _SweepWorldcupForm, SweepWorldcup _SweepWorldcup) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepWorldcup _SweepWorldcup = m_ds.getById(cid);

        m_logger.debug("Before update " + SweepWorldcupDS.objectToString(_SweepWorldcup));

        _SweepWorldcup.setTeamCode(WebParamUtil.getStringValue(_SweepWorldcupForm.getTeamCode()));
        _SweepWorldcup.setGroupNum(WebParamUtil.getStringValue(_SweepWorldcupForm.getGroupNum()));
        _SweepWorldcup.setGame1(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame1()));
        _SweepWorldcup.setGame1Score(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame1Score()));
        _SweepWorldcup.setGame1ScoreOpp(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame1ScoreOpp()));
        _SweepWorldcup.setGame2(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame2()));
        _SweepWorldcup.setGame2Score(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame2Score()));
        _SweepWorldcup.setGame2ScoreOpp(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame2ScoreOpp()));
        _SweepWorldcup.setGame3(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame3()));
        _SweepWorldcup.setGame3Score(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame3Score()));
        _SweepWorldcup.setGame3ScoreOpp(WebParamUtil.getIntValue(_SweepWorldcupForm.getGame3ScoreOpp()));
        _SweepWorldcup.setQuarterFinalTeams(WebParamUtil.getStringValue(_SweepWorldcupForm.getQuarterFinalTeams()));
        _SweepWorldcup.setSemiFinalTeams(WebParamUtil.getStringValue(_SweepWorldcupForm.getSemiFinalTeams()));
        _SweepWorldcup.setFinalTeams(WebParamUtil.getStringValue(_SweepWorldcupForm.getFinalTeams()));
        _SweepWorldcup.setChampion(WebParamUtil.getStringValue(_SweepWorldcupForm.getChampion()));
        _SweepWorldcup.setFinalScoreWin(WebParamUtil.getIntValue(_SweepWorldcupForm.getFinalScoreWin()));
        _SweepWorldcup.setFinalScoreLose(WebParamUtil.getIntValue(_SweepWorldcupForm.getFinalScoreLose()));
        _SweepWorldcup.setTimeCreated(WebParamUtil.getDateValue(_SweepWorldcupForm.getTimeCreated()));
        _SweepWorldcup.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _SweepWorldcup);
        m_ds.update(_SweepWorldcup);
        m_actionExtent.afterUpdate(request, response, _SweepWorldcup);
        m_logger.debug("After update " + SweepWorldcupDS.objectToString(_SweepWorldcup));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SweepWorldcup _SweepWorldcup) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SweepWorldcup _SweepWorldcup = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_SweepWorldcup.getUserId() + "->" + request.getParameter("userId"));
            _SweepWorldcup.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("teamCode"))) {
            m_logger.debug("updating param teamCode from " +_SweepWorldcup.getTeamCode() + "->" + request.getParameter("teamCode"));
            _SweepWorldcup.setTeamCode(WebParamUtil.getStringValue(request.getParameter("teamCode")));
        }
        if (!isMissing(request.getParameter("teamName"))) {
            m_logger.debug("updating param teamName from " +_SweepWorldcup.getTeamName() + "->" + request.getParameter("teamName"));
            _SweepWorldcup.setTeamName(WebParamUtil.getStringValue(request.getParameter("teamName")));
        }
        if (!isMissing(request.getParameter("groupNum"))) {
            m_logger.debug("updating param groupNum from " +_SweepWorldcup.getGroupNum() + "->" + request.getParameter("groupNum"));
            _SweepWorldcup.setGroupNum(WebParamUtil.getStringValue(request.getParameter("groupNum")));
        }
        if (!isMissing(request.getParameter("game1"))) {
            m_logger.debug("updating param game1 from " +_SweepWorldcup.getGame1() + "->" + request.getParameter("game1"));
            _SweepWorldcup.setGame1(WebParamUtil.getIntValue(request.getParameter("game1")));
        }
        if (!isMissing(request.getParameter("game1Score"))) {
            m_logger.debug("updating param game1Score from " +_SweepWorldcup.getGame1Score() + "->" + request.getParameter("game1Score"));
            _SweepWorldcup.setGame1Score(WebParamUtil.getIntValue(request.getParameter("game1Score")));
        }
        if (!isMissing(request.getParameter("game1ScoreOpp"))) {
            m_logger.debug("updating param game1ScoreOpp from " +_SweepWorldcup.getGame1ScoreOpp() + "->" + request.getParameter("game1ScoreOpp"));
            _SweepWorldcup.setGame1ScoreOpp(WebParamUtil.getIntValue(request.getParameter("game1ScoreOpp")));
        }
        if (!isMissing(request.getParameter("game2"))) {
            m_logger.debug("updating param game2 from " +_SweepWorldcup.getGame2() + "->" + request.getParameter("game2"));
            _SweepWorldcup.setGame2(WebParamUtil.getIntValue(request.getParameter("game2")));
        }
        if (!isMissing(request.getParameter("game2Score"))) {
            m_logger.debug("updating param game2Score from " +_SweepWorldcup.getGame2Score() + "->" + request.getParameter("game2Score"));
            _SweepWorldcup.setGame2Score(WebParamUtil.getIntValue(request.getParameter("game2Score")));
        }
        if (!isMissing(request.getParameter("game2ScoreOpp"))) {
            m_logger.debug("updating param game2ScoreOpp from " +_SweepWorldcup.getGame2ScoreOpp() + "->" + request.getParameter("game2ScoreOpp"));
            _SweepWorldcup.setGame2ScoreOpp(WebParamUtil.getIntValue(request.getParameter("game2ScoreOpp")));
        }
        if (!isMissing(request.getParameter("game3"))) {
            m_logger.debug("updating param game3 from " +_SweepWorldcup.getGame3() + "->" + request.getParameter("game3"));
            _SweepWorldcup.setGame3(WebParamUtil.getIntValue(request.getParameter("game3")));
        }
        if (!isMissing(request.getParameter("game3Score"))) {
            m_logger.debug("updating param game3Score from " +_SweepWorldcup.getGame3Score() + "->" + request.getParameter("game3Score"));
            _SweepWorldcup.setGame3Score(WebParamUtil.getIntValue(request.getParameter("game3Score")));
        }
        if (!isMissing(request.getParameter("game3ScoreOpp"))) {
            m_logger.debug("updating param game3ScoreOpp from " +_SweepWorldcup.getGame3ScoreOpp() + "->" + request.getParameter("game3ScoreOpp"));
            _SweepWorldcup.setGame3ScoreOpp(WebParamUtil.getIntValue(request.getParameter("game3ScoreOpp")));
        }
        if (!isMissing(request.getParameter("quarterFinalTeams"))) {
            m_logger.debug("updating param quarterFinalTeams from " +_SweepWorldcup.getQuarterFinalTeams() + "->" + request.getParameter("quarterFinalTeams"));
            _SweepWorldcup.setQuarterFinalTeams(WebParamUtil.getStringValue(request.getParameter("quarterFinalTeams")));
        }
        if (!isMissing(request.getParameter("semiFinalTeams"))) {
            m_logger.debug("updating param semiFinalTeams from " +_SweepWorldcup.getSemiFinalTeams() + "->" + request.getParameter("semiFinalTeams"));
            _SweepWorldcup.setSemiFinalTeams(WebParamUtil.getStringValue(request.getParameter("semiFinalTeams")));
        }
        if (!isMissing(request.getParameter("finalTeams"))) {
            m_logger.debug("updating param finalTeams from " +_SweepWorldcup.getFinalTeams() + "->" + request.getParameter("finalTeams"));
            _SweepWorldcup.setFinalTeams(WebParamUtil.getStringValue(request.getParameter("finalTeams")));
        }
        if (!isMissing(request.getParameter("champion"))) {
            m_logger.debug("updating param champion from " +_SweepWorldcup.getChampion() + "->" + request.getParameter("champion"));
            _SweepWorldcup.setChampion(WebParamUtil.getStringValue(request.getParameter("champion")));
        }
        if (!isMissing(request.getParameter("finalScoreWin"))) {
            m_logger.debug("updating param finalScoreWin from " +_SweepWorldcup.getFinalScoreWin() + "->" + request.getParameter("finalScoreWin"));
            _SweepWorldcup.setFinalScoreWin(WebParamUtil.getIntValue(request.getParameter("finalScoreWin")));
        }
        if (!isMissing(request.getParameter("finalScoreLose"))) {
            m_logger.debug("updating param finalScoreLose from " +_SweepWorldcup.getFinalScoreLose() + "->" + request.getParameter("finalScoreLose"));
            _SweepWorldcup.setFinalScoreLose(WebParamUtil.getIntValue(request.getParameter("finalScoreLose")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SweepWorldcup.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SweepWorldcup.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SweepWorldcup.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SweepWorldcup.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _SweepWorldcup);
        m_ds.update(_SweepWorldcup);
        m_actionExtent.afterUpdate(request, response, _SweepWorldcup);
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected SweepWorldcupDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SweepWorldcupAction.class);
}
