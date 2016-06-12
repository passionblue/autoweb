<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeUser user = sessionContext.getUserObject();

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepWorldcup _SweepWorldcupDefault = new SweepWorldcup();// SweepWorldcupDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_SweepWorldcupDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _team_codeValue= (reqParams.get("teamCode")==null?WebUtil.display(_SweepWorldcupDefault.getTeamCode()):WebUtil.display((String)reqParams.get("teamCode")));
    String _team_nameValue= (reqParams.get("teamName")==null?WebUtil.display(_SweepWorldcupDefault.getTeamName()):WebUtil.display((String)reqParams.get("teamName")));
    String _group_numValue= (reqParams.get("groupNum")==null?WebUtil.display(_SweepWorldcupDefault.getGroupNum()):WebUtil.display((String)reqParams.get("groupNum")));
    String _game_1Value= (reqParams.get("game1")==null?WebUtil.display(_SweepWorldcupDefault.getGame1()):WebUtil.display((String)reqParams.get("game1")));
    String _game_1_scoreValue= (reqParams.get("game1Score")==null?WebUtil.display(_SweepWorldcupDefault.getGame1Score()):WebUtil.display((String)reqParams.get("game1Score")));
    String _game_1_score_oppValue= (reqParams.get("game1ScoreOpp")==null?WebUtil.display(_SweepWorldcupDefault.getGame1ScoreOpp()):WebUtil.display((String)reqParams.get("game1ScoreOpp")));
    String _game_2Value= (reqParams.get("game2")==null?WebUtil.display(_SweepWorldcupDefault.getGame2()):WebUtil.display((String)reqParams.get("game2")));
    String _game_2_scoreValue= (reqParams.get("game2Score")==null?WebUtil.display(_SweepWorldcupDefault.getGame2Score()):WebUtil.display((String)reqParams.get("game2Score")));
    String _game_2_score_oppValue= (reqParams.get("game2ScoreOpp")==null?WebUtil.display(_SweepWorldcupDefault.getGame2ScoreOpp()):WebUtil.display((String)reqParams.get("game2ScoreOpp")));
    String _game_3Value= (reqParams.get("game3")==null?WebUtil.display(_SweepWorldcupDefault.getGame3()):WebUtil.display((String)reqParams.get("game3")));
    String _game_3_scoreValue= (reqParams.get("game3Score")==null?WebUtil.display(_SweepWorldcupDefault.getGame3Score()):WebUtil.display((String)reqParams.get("game3Score")));
    String _game_3_score_oppValue= (reqParams.get("game3ScoreOpp")==null?WebUtil.display(_SweepWorldcupDefault.getGame3ScoreOpp()):WebUtil.display((String)reqParams.get("game3ScoreOpp")));
    String _quarter_final_teamsValue= (reqParams.get("quarterFinalTeams")==null?WebUtil.display(_SweepWorldcupDefault.getQuarterFinalTeams()):WebUtil.display((String)reqParams.get("quarterFinalTeams")));
    String _semi_final_teamsValue= (reqParams.get("semiFinalTeams")==null?WebUtil.display(_SweepWorldcupDefault.getSemiFinalTeams()):WebUtil.display((String)reqParams.get("semiFinalTeams")));
    String _final_teamsValue= (reqParams.get("finalTeams")==null?WebUtil.display(_SweepWorldcupDefault.getFinalTeams()):WebUtil.display((String)reqParams.get("finalTeams")));
    String _championValue= (reqParams.get("champion")==null?WebUtil.display(_SweepWorldcupDefault.getChampion()):WebUtil.display((String)reqParams.get("champion")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SweepWorldcupDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SweepWorldcupDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<div id="sweepWorldcupForm_topArea" class="formTopArea"></div>

<p>
	<span style="font-family: verdana,geneva,sans-serif;"><span style="font-size: 20px;margin-left:50px">Welcome To 2010 Worldcup Sweepstakes</span></span></p>
	<div style="clear: both;"></div>

<p>
	<span style="font-family: verdana,geneva,sans-serif;"><span style="font-size: 20px;"><br />
	</span></span></p>
<p>
	<span style="font-family: verdana,geneva,sans-serif;"><span style="font-size: 20px;"><br />
	</span></span></p>
<p>
	<span style="font-family: verdana,geneva,sans-serif;"><span style="font-size: 18px;"><img alt="" width="24px" src="/images/icons/32px-Crystal_Clear_action_apply.png" style="margin-left: 50px; float: left;" />&nbsp;&nbsp;Risk-free Sweepstakes</span></span></p>
	<div style="clear: both;"></div>
<p>
	<span style="font-family: arial,helvetica,sans-serif;"><span style="font-size: 18px;"><img alt="" width="24px" src="/images/icons/32px-Crystal_Clear_action_apply.png" style="margin-left: 50px; float: left;" />&nbsp;&nbsp;No Survey Taking, No Annoying Questions</span></span></p>
	<div style="clear: both;"></div>
<p>
	<span style="font-family: arial,helvetica,sans-serif;"><span style="font-size: 18px;"><img alt="" width="24px" src="/images/icons/32px-Crystal_Clear_action_apply.png" style="margin-left: 50px; float: left;" />&nbsp;&nbsp;Play For Fun and Take A Chance To Win Most Wanted Gadget</span></span></p>
	<div style="clear: both;"></div>
<p>
	<span style="font-family: arial,helvetica,sans-serif;"><span style="font-size: 18px;"><img alt="" width="24px" src="/images/icons/32px-Crystal_Clear_action_apply.png" style="margin-left: 50px; float: left;" />&nbsp;&nbsp;</span></span></p>
	<div style="clear: both;"></div>

<%
	if (sessionContext.isLogin()){
 %>
<div style="clear: both; margin-left: 40px; text-align: center;">
	<a href="/m_enter_sweepstakes.html"><strong><span style="color: rgb(255, 140, 0);"><span style="font-size: 20px;">Are you Ready? Enter Sweepstakes!!<br />
</span></span></strong></a></div>
 
 <%	} else { %>
<div style="clear: both; margin-left: 40px; text-align: center;">
	<a href="/t_register_simple_add.html"><strong><span style="color: rgb(255, 140, 0);"><span style="font-size: 20px;">Register to start Sweepstakes<br />
</span></span></strong></a></div>

<%	} %>

<div id="sweepWorldcupForm_bottomArea" class="formBottomArea"></div>



