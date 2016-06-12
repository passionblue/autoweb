<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="sweepWorldcupForm" method="post" action="/sweepWorldcupAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Team Code</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="teamCode" value="<%=WebUtil.display(_team_codeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Team Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="teamName" value="<%=WebUtil.display(_team_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Group Num</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="groupNum" value="<%=WebUtil.display(_group_numValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 1</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game1" value="<%=WebUtil.display(_game_1Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 1 Score</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game1Score" value="<%=WebUtil.display(_game_1_scoreValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 1 Score Opp</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game1ScoreOpp" value="<%=WebUtil.display(_game_1_score_oppValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 2</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game2" value="<%=WebUtil.display(_game_2Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 2 Score</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game2Score" value="<%=WebUtil.display(_game_2_scoreValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 2 Score Opp</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game2ScoreOpp" value="<%=WebUtil.display(_game_2_score_oppValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 3</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game3" value="<%=WebUtil.display(_game_3Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 3 Score</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game3Score" value="<%=WebUtil.display(_game_3_scoreValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Game 3 Score Opp</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="game3ScoreOpp" value="<%=WebUtil.display(_game_3_score_oppValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Quarter Final Teams</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="quarterFinalTeams" value="<%=WebUtil.display(_quarter_final_teamsValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Semi Final Teams</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="semiFinalTeams" value="<%=WebUtil.display(_semi_final_teamsValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Final Teams</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="finalTeams" value="<%=WebUtil.display(_final_teamsValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Champion</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="champion" value="<%=WebUtil.display(_championValue)%>"/></TD>
	    </TR>
	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.sweepWorldcupForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SweepWorldcupDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepWorldcup _SweepWorldcup = (SweepWorldcup) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepWorldcup.getId() %> </td>

    <td> <%= WebUtil.display(_SweepWorldcup.getUserId()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getTeamCode()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getTeamName()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGroupNum()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame1()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame1Score()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame1ScoreOpp()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame2()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame2Score()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame2ScoreOpp()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame3()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame3Score()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getGame3ScoreOpp()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getQuarterFinalTeams()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getSemiFinalTeams()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getFinalTeams()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getChampion()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SweepWorldcup.getTimeUpdated()) %></td>


<td>
<form name="sweepWorldcupForm<%=_SweepWorldcup.getId()%>" method="post" action="/v_sweep_worldcup_edit.html" >
    <a href="javascript:document.sweepWorldcupForm<%=_SweepWorldcup.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepWorldcup.getId() %>">
</form>
</td>
<td>
<a href="/sweepWorldcupAction.html?del=true&id=<%=_SweepWorldcup.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>