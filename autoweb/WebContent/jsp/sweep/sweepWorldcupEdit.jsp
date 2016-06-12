<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    SweepWorldcup _SweepWorldcup = SweepWorldcupDS.getInstance().getById(id);

    if ( _SweepWorldcup == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_SweepWorldcup.getUserId());
    String _team_codeValue=  WebUtil.display(_SweepWorldcup.getTeamCode());
    String _team_nameValue=  WebUtil.display(_SweepWorldcup.getTeamName());
    String _group_numValue=  WebUtil.display(_SweepWorldcup.getGroupNum());
    String _game_1Value=  WebUtil.display(_SweepWorldcup.getGame1());
    String _game_1_scoreValue=  WebUtil.display(_SweepWorldcup.getGame1Score());
    String _game_1_score_oppValue=  WebUtil.display(_SweepWorldcup.getGame1ScoreOpp());
    String _game_2Value=  WebUtil.display(_SweepWorldcup.getGame2());
    String _game_2_scoreValue=  WebUtil.display(_SweepWorldcup.getGame2Score());
    String _game_2_score_oppValue=  WebUtil.display(_SweepWorldcup.getGame2ScoreOpp());
    String _game_3Value=  WebUtil.display(_SweepWorldcup.getGame3());
    String _game_3_scoreValue=  WebUtil.display(_SweepWorldcup.getGame3Score());
    String _game_3_score_oppValue=  WebUtil.display(_SweepWorldcup.getGame3ScoreOpp());
    String _quarter_final_teamsValue=  WebUtil.display(_SweepWorldcup.getQuarterFinalTeams());
    String _semi_final_teamsValue=  WebUtil.display(_SweepWorldcup.getSemiFinalTeams());
    String _final_teamsValue=  WebUtil.display(_SweepWorldcup.getFinalTeams());
    String _championValue=  WebUtil.display(_SweepWorldcup.getChampion());
    String _time_createdValue=  WebUtil.display(_SweepWorldcup.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_SweepWorldcup.getTimeUpdated());
%> 

<br>
<form name="sweepWorldcupFormEdit" method="post" action="/sweepWorldcupAction.html" >
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
            <b><a href="javascript:document.sweepWorldcupFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
</form>
