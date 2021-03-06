<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.sweep.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
    long sweepId = WebParamUtil.getLongValue(request.getParameter("id"));
    SweepWorldcup _SweepWorldcupDefault = SweepWorldcupDS.getInstance().getById(sweepId);
    
	if ( _SweepWorldcupDefault == null) return;
    
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

    String _final_score_winValue= (reqParams.get("finalScoreWin")==null?WebUtil.display(_SweepWorldcupDefault.getFinalScoreWin()):WebUtil.display((String)reqParams.get("finalScoreWin")));
    String _final_score_loseValue= (reqParams.get("finalScoreLose")==null?WebUtil.display(_SweepWorldcupDefault.getFinalScoreLose()):WebUtil.display((String)reqParams.get("finalScoreLose")));

    
    String _quarter_final_teamsValue= (reqParams.get("quarterFinalTeams")==null?WebUtil.display(_SweepWorldcupDefault.getQuarterFinalTeams()):WebUtil.display((String)reqParams.get("quarterFinalTeams")));
    String _semi_final_teamsValue= (reqParams.get("semiFinalTeams")==null?WebUtil.display(_SweepWorldcupDefault.getSemiFinalTeams()):WebUtil.display((String)reqParams.get("semiFinalTeams")));
    String _final_teamsValue= (reqParams.get("finalTeams")==null?WebUtil.display(_SweepWorldcupDefault.getFinalTeams()):WebUtil.display((String)reqParams.get("finalTeams")));
    String _championValue= (reqParams.get("champion")==null?WebUtil.display(_SweepWorldcupDefault.getChampion()):WebUtil.display((String)reqParams.get("champion")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

	String quarterTeams[] = SweepWorldcupParamUtil.getBetFieldsMapInStringArray(_quarter_final_teamsValue, SweepWorldcupParamUtil.FIELD_QUARTER);
	String semiTeams[] = SweepWorldcupParamUtil.getBetFieldsMapInStringArray(_semi_final_teamsValue, SweepWorldcupParamUtil.FIELD_SEMI);
	String finalTeams[] = SweepWorldcupParamUtil.getBetFieldsMapInStringArray(_final_teamsValue, SweepWorldcupParamUtil.FIELD_FINAL);
	String champTeams[] = SweepWorldcupParamUtil.getBetFieldsMapInStringArray(_championValue, SweepWorldcupParamUtil.FIELD_CHAMP);


	// Corrects
	
	SweepWorldcup result = new SweepWorldcup();
	
	result.setGame1(1);
	result.setGame1Score(2);	
	result.setGame1ScoreOpp(0);	

	result.setGame2(2);
	result.setGame2Score(1);	
	result.setGame2ScoreOpp(4);	

	result.setGame3(3);
	result.setGame3Score(2);	
	result.setGame3ScoreOpp(2);	


	result.setQuarterFinalTeams("URU,GHA,NED,BRA,GER,ARG,PAR,ESP");
	result.setSemiFinalTeams("URU,NED,GER,ESP");
	result.setFinalTeams("NED,ESP");
	result.setChampion("ESP");

	result.setFinalScoreWin(1);	
	result.setFinalScoreLose(0);	
	
	
	int totalPoints = 0;
	totalPoints += ScoringUtil.calculateWinLosePoints(5, 0,_SweepWorldcupDefault.getGame1(), result.getGame1());
	totalPoints += ScoringUtil.calculateWinLosePoints(5, 0,_SweepWorldcupDefault.getGame2(), result.getGame2());
	totalPoints += ScoringUtil.calculateWinLosePoints(5, 0,_SweepWorldcupDefault.getGame3(), result.getGame3());
	totalPoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame1Score(), result.getGame1Score());
	totalPoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame1ScoreOpp(), result.getGame1ScoreOpp());
	totalPoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame2Score(), result.getGame2Score());
	totalPoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame2ScoreOpp(), result.getGame2ScoreOpp());
	totalPoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame3Score(), result.getGame3Score());
	totalPoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame3ScoreOpp(), result.getGame3ScoreOpp());

	int secondRafflePoints = 0;
	
	secondRafflePoints += ScoringUtil.calculateTeamsPoints(3, _SweepWorldcupDefault.getQuarterFinalTeams(), result.getQuarterFinalTeams());
	secondRafflePoints += ScoringUtil.calculateTeamsPoints(5, _SweepWorldcupDefault.getSemiFinalTeams(), result.getSemiFinalTeams());
	secondRafflePoints += ScoringUtil.calculateTeamsPoints(10, _SweepWorldcupDefault.getFinalTeams(), result.getFinalTeams());
	secondRafflePoints += ScoringUtil.calculateTeamsPoints(20, _SweepWorldcupDefault.getChampion(), result.getChampion());
	
	secondRafflePoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getFinalScoreWin(), result.getFinalScoreWin());
	secondRafflePoints += ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getFinalScoreLose(), result.getFinalScoreLose());
	
	
%> 


<br>
<div id="sweepWorldcupForm_topArea" class="formTopArea"></div>
	 <h4><%= WorldcupUtil.getTeamCountry(_team_codeValue)%></h4><img src="/images/worldcup/<%=_team_codeValue%>2.gif"/> 
<div id="sweepWorldcupForm_topArea" class="formTopArea"></div>



<div id="sweepWorldcupForm" class="formFrame">

<div id="megamenu1" class="megamenu">

<%
	String [] groupArray=new String[]{"A","B","C","D"};
	for(int i = 0; i<4;i++){
		String groupCode = groupArray[i];
%>
<div class="column">
	<h3> Group <%=groupCode %></h3>
	<ul>
<%
	List codes = WorldcupUtil.getGroupTeamList(groupCode);
	for(Iterator iter=codes.iterator();iter.hasNext();){
		String code = (String) iter.next();
		String country = WorldcupUtil.getTeamCountry(code);		
%>
	<li><div id="selectTeam" style="cursor:pointer;" code="<%=code%>" country="<%=country %>">&nbsp; <%=country%> <img style="float:left;" src="/images/worldcup/<%=code%>.gif"/> <div id="hiddenCode" style="display:none;"><%=code%></div></div></li>
<% } %>
	</ul>
</div>
<% } %>

<br style="clear: left" /> <!--Break after 3rd column. Move this if desired-->

<%
	String [] groupArray2=new String[]{"E","F","G","H"};
	for(int i = 0; i<4;i++){
		String groupCode = groupArray2[i];
%>
<div class="column">
	<h3> Group <%=groupCode %></h3>
	<ul>
<%
	List codes = WorldcupUtil.getGroupTeamList(groupCode);
	for(Iterator iter=codes.iterator();iter.hasNext();){
		String code = (String) iter.next();
		String country = WorldcupUtil.getTeamCountry(code);		
%>
	<li><div id="selectTeam" style="cursor:pointer;" code="<%=code%>" country="<%=country %>">&nbsp; <%=country%> <img style="float:left;" src="/images/worldcup/<%=code%>.gif"/> <div id="hiddenCode" style="display:none;"><%=code%></div></div></li>
<% } %>
	</ul>
</div>
<% } %>

</div><div class="clear"></div>

<h2> Your Total Point for Korean Team Raffle is <font color="red"><%= totalPoints %></font></h2>
<h2> Your Total Point for Worldcup Raffle is <font color="red"><%= secondRafflePoints %></font></h2>

<div id="pageType" style="display:none">EDIT</div>
<form name="sweepWorldcupForm" method="get" action="/sweepWorldcupAction.html" >



        <input type="hidden" size="30" name="teamCode" value="<%=WebUtil.display(_team_codeValue)%>"/>
        <input type="hidden" size="30" name="teamName" value="<%=WorldcupUtil.getTeamCountry(_team_codeValue)%>"/>
        <input type="hidden" size="30" name="groupNum" value="<%=WorldcupUtil.getTeamGroup(_team_codeValue)%>"/>

	<div id="sweepWorldcupForm_game1_field">
    <div id="sweepWorldcupForm_game1_label" class="formLabel" >Korea vs Greece </div>
    <div id="sweepWorldcupForm_game1_dropdown" class="formFieldDropDown" >       
        
        <select id="requiredField" name="game1">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_1Value)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_1Value)%>>WIN</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_1Value)%>>LOSE</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_1Value)%>>DRAW</option>
        </select>  <span></span>
		
    </div>      
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateWinLosePoints(5, 0,_SweepWorldcupDefault.getGame1(), result.getGame1()))%></div>
    <div id="sweepWorldcupForm_game1Score_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game1Score">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_1_scoreValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _game_1_scoreValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_1_scoreValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_1_scoreValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_1_scoreValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _game_1_scoreValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _game_1_scoreValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _game_1_scoreValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _game_1_scoreValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _game_1_scoreValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _game_1_scoreValue)%>>9</option>
        </select>  <span></span>
    </div>      

	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame1Score(), result.getGame1Score()))%></div>
    <div id="sweepWorldcupForm_game1ScoreOpp_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game1ScoreOpp">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_1_score_oppValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _game_1_score_oppValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_1_score_oppValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_1_score_oppValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_1_score_oppValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _game_1_score_oppValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _game_1_score_oppValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _game_1_score_oppValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _game_1_score_oppValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _game_1_score_oppValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _game_1_score_oppValue)%>>9</option>
        </select>  <span></span>
    </div>
    
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame1ScoreOpp(), result.getGame1ScoreOpp()))%></div>          
	</div><div class="clear"></div>

	<div id="sweepWorldcupForm_game2_field">
    <div id="sweepWorldcupForm_game2_label" class="formLabel" >Korea vs Argentina </div>
    <div id="sweepWorldcupForm_game2_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="game2">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_2Value)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_2Value)%>>WIN</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_2Value)%>>LOSE</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_2Value)%>>DRAW</option>
        </select>  <span></span>
    </div>      

	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateWinLosePoints(5, 0,_SweepWorldcupDefault.getGame2(), result.getGame2()))%></div>
    <div id="sweepWorldcupForm_game2Score_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game2Score">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_2_scoreValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _game_2_scoreValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_2_scoreValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_2_scoreValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_2_scoreValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _game_2_scoreValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _game_2_scoreValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _game_2_scoreValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _game_2_scoreValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _game_2_scoreValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _game_2_scoreValue)%>>9</option>
        </select>  <span></span>
    </div>      

	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame2Score(), result.getGame2Score()))%></div>
    <div id="sweepWorldcupForm_game2ScoreOpp_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game2ScoreOpp">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_2_score_oppValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _game_2_score_oppValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_2_score_oppValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_2_score_oppValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_2_score_oppValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _game_2_score_oppValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _game_2_score_oppValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _game_2_score_oppValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _game_2_score_oppValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _game_2_score_oppValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _game_2_score_oppValue)%>>9</option>
        </select>  <span></span>
    </div>      
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame2ScoreOpp(), result.getGame2ScoreOpp()))%></div>          

	</div><div class="clear"></div>

	<div id="sweepWorldcupForm_game3_field">
    <div id="sweepWorldcupForm_game3_label" class="formLabel" > Korea vs Nigeria</div>
    <div id="sweepWorldcupForm_game3_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="game3">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_3Value)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_3Value)%>>WIN</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_3Value)%>>LOSE</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_3Value)%>>DRAW</option>
        </select>  <span></span>
    </div>      

	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateWinLosePoints(5, 0,_SweepWorldcupDefault.getGame3(), result.getGame3()))%></div>
    <div id="sweepWorldcupForm_game3Score_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game3Score">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_3_scoreValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _game_3_scoreValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_3_scoreValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_3_scoreValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_3_scoreValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _game_3_scoreValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _game_3_scoreValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _game_3_scoreValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _game_3_scoreValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _game_3_scoreValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _game_3_scoreValue)%>>9</option>
        </select>  <span></span>
    </div>      
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame3ScoreOpp(), result.getGame3ScoreOpp()))%></div>          

    <div id="sweepWorldcupForm_game3ScoreOpp_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game3ScoreOpp">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_3_score_oppValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _game_3_score_oppValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _game_3_score_oppValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _game_3_score_oppValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _game_3_score_oppValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _game_3_score_oppValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _game_3_score_oppValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _game_3_score_oppValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _game_3_score_oppValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _game_3_score_oppValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _game_3_score_oppValue)%>>9</option>
        </select>  <span></span>
    </div>      
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getGame3ScoreOpp(), result.getGame3ScoreOpp()))%></div>          
	</div><div class="clear"></div>
	<br/><br/>

	<div id="sweepWorldcupForm_quarterFinalTeams_field">
    <div id="sweepWorldcupForm_quarterFinalTeams_label" class="formLabel" >Quarter Final Teams (3 Pts per Team) </div>
    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_1" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_1" value="<%=WebUtil.display(quarterTeams[0])%>"/>
		<div id="quarterFinalTeam_1_div" class="selectedField" code="<%=quarterTeams[0] %>" ><%= WebUtil.isNull(quarterTeams[0])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[0] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[0]) %></div>
    </div>      

    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_2" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_2" value="<%=WebUtil.display(quarterTeams[1])%>"/>
		<div id="quarterFinalTeam_2_div" class="selectedField" code="<%=quarterTeams[1] %>"><%= WebUtil.isNull(quarterTeams[1])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[1] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[1]) %></div>
    </div>      

    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_3" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_3" value="<%=WebUtil.display(quarterTeams[2])%>"/>
		<div id="quarterFinalTeam_3_div" class="selectedField"><%= WebUtil.isNull(quarterTeams[2])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[2] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[2]) %></div>
    </div>      

    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_4" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_4" value="<%=WebUtil.display(quarterTeams[3])%>"/>
		<div id="quarterFinalTeam_4_div" class="selectedField"><%= WebUtil.isNull(quarterTeams[3])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[3] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[3]) %></div>
    </div>      

	</div><div class="clear"></div>

	<div id="sweepWorldcupForm_quarterFinalTeams_field">
    <div id="sweepWorldcupForm_quarterFinalTeams_label" class="formLabel" > &nbsp; </div>
    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_5" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_5" value="<%=WebUtil.display(quarterTeams[4])%>"/>
		<div id="quarterFinalTeam_5_div" class="selectedField"><%= WebUtil.isNull(quarterTeams[4])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[4] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[4]) %></div>
    </div>      

    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_6" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_6" value="<%=WebUtil.display(quarterTeams[5])%>"/>
		<div id="quarterFinalTeam_6_div" class="selectedField"><%= WebUtil.isNull(quarterTeams[5])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[5] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[5]) %></div>
    </div>      

    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_7" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_7" value="<%=WebUtil.display(quarterTeams[6])%>"/>
		<div id="quarterFinalTeam_7_div" class="selectedField"><%= WebUtil.isNull(quarterTeams[6])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[6] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[6]) %></div>
    </div>      

    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="quarterFinalTeam_8" class="requiredField" type="hidden" size="10" name="quarterFinalTeam_8" value="<%=WebUtil.display(quarterTeams[7])%>"/>
		<div id="quarterFinalTeam_8_div" class="selectedField"><%= WebUtil.isNull(quarterTeams[7])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + quarterTeams[7] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(quarterTeams[7]) %></div>
    </div>      
	</div><div class="clear"></div>
	<div class="resultPointsDisp" style="text-indent: 150px;"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateTeamsPoints(3, _SweepWorldcupDefault.getQuarterFinalTeams(), result.getQuarterFinalTeams()))%></div>          
	
	<br/><br/>


	<div id="sweepWorldcupForm_semiFinalTeams_field">
    <div id="sweepWorldcupForm_semiFinalTeams_label" class="formLabel" >Semi Final Teams <br/>(5 Pts per Team)</div>
    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="semiFinalTeam_1" class="requiredField" type="hidden" size="10" name="semiFinalTeam_1" value="<%=WebUtil.display(semiTeams[0])%>"/>
		<div id="semiFinalTeam_1_div" class="selectedField"><%= WebUtil.isNull(semiTeams[0])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + semiTeams[0] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(semiTeams[0]) %></div>
    </div>      

    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="semiFinalTeam_2" class="requiredField" type="hidden" size="10" name="semiFinalTeam_2" value="<%=WebUtil.display(semiTeams[1])%>"/>
		<div id="semiFinalTeam_2_div" class="selectedField"><%= WebUtil.isNull(semiTeams[1])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + semiTeams[1] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(semiTeams[1]) %></div>
    </div>      

    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="semiFinalTeam_3" class="requiredField" type="hidden" size="10" name="semiFinalTeam_3" value="<%=WebUtil.display(semiTeams[2])%>"/>
		<div id="semiFinalTeam_3_div" class="selectedField"><%= WebUtil.isNull(semiTeams[2])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + semiTeams[2] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(semiTeams[2]) %></div>
    </div>      

    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="semiFinalTeam_4" class="requiredField" type="hidden" size="10" name="semiFinalTeam_4" value="<%=WebUtil.display(semiTeams[3])%>"/>
		<div id="semiFinalTeam_4_div" class="selectedField"><%= WebUtil.isNull(semiTeams[3])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + semiTeams[3] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(semiTeams[3]) %></div>
    </div>      

	</div><div class="clear"></div>
	<div class="resultPointsDisp" style="text-indent: 150px;"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateTeamsPoints(5, _SweepWorldcupDefault.getSemiFinalTeams(), result.getSemiFinalTeams()))%></div>          
	<br/><br/>


	<div id="sweepWorldcupForm_finalTeams_field">
    <div id="sweepWorldcupForm_finalTeams_label" class="formLabel" >Final Teams <br/>(10 Pts per Team)</div>
    <div id="sweepWorldcupForm_finalTeams_text" class="formFieldText" > <span></span>      
        <input id="finalTeam_1" class="requiredField" type="hidden" size="10" name="finalTeam_1" value="<%=WebUtil.display(finalTeams[0])%>"/>
		<div id="finalTeam_1_div" class="selectedField"><%= WebUtil.isNull(finalTeams[0])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + finalTeams[0] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(finalTeams[0]) %></div>
    </div>      

    <div id="sweepWorldcupForm_finalTeams_text" class="formFieldText" > <span></span>      
        <input id="finalTeam_2" class="requiredField" type="hidden" size="10" name="finalTeam_2" value="<%=WebUtil.display(finalTeams[1])%>"/>
		<div id="finalTeam_2_div" class="selectedField"><%= WebUtil.isNull(finalTeams[1])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + finalTeams[1] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(finalTeams[1]) %></div>
    </div>      

	</div><div class="clear"></div>
	<div class="resultPointsDisp" style="text-indent: 150px;"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateTeamsPoints(10, _SweepWorldcupDefault.getFinalTeams(), result.getFinalTeams()))%></div>          
	
	<div class="clear"></div>
	<br/>
	<div id="sweepWorldcupForm_finalScoreWin_field">
    <div id="sweepWorldcupForm_finalScoreWin_label" class="formLabel" >Final Score </div>
    <div id="sweepWorldcupForm_finalScoreWin_dropdown" class="formFieldDropDown" >       
        <select id="field" name="finalScoreWin">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _final_score_winValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _final_score_winValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _final_score_winValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _final_score_winValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _final_score_winValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _final_score_winValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _final_score_winValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _final_score_winValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _final_score_winValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _final_score_winValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _final_score_winValue)%>>9</option>
        </select>  <span></span>
    </div>      
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getFinalScoreWin(), result.getFinalScoreWin()))%></div>          

    <div id="sweepWorldcupForm_finalScoreLose_dropdown" class="formFieldDropDown" >       
        <select id="field" name="finalScoreLose">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _final_score_loseValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _final_score_loseValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _final_score_loseValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _final_score_loseValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _final_score_loseValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _final_score_loseValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _final_score_loseValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _final_score_loseValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _final_score_loseValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _final_score_loseValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _final_score_loseValue)%>>9</option>
        </select>  <span></span>
    </div>      
	<div class="resultPointsDisp"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateScorePoints(5, 1,_SweepWorldcupDefault.getFinalScoreLose(), result.getFinalScoreLose()))%></div>          
	</div><div class="clear"></div>
	<br/><br/>

	<div id="sweepWorldcupForm_champion_field">
    <div id="sweepWorldcupForm_champion_label" class="formLabel" >Champion <br/>(20 Pts per Team)</div>
    <div id="sweepWorldcupForm_champion_text" class="formFieldText" > <span></span>      
        <input id="championTeam_1" class="requiredField" type="hidden" size="10" name="championTeam_1" value="<%=WebUtil.display(champTeams[0])%>"/>
		<div id="championTeam_1_div" class="selectedField"><%= WebUtil.isNull(champTeams[0])?"Select Team":"<img style=\"float:left;\" src=\"/images/worldcup/" + champTeams[0] +".gif\"/> &nbsp;" + WorldcupUtil.getTeamCountry(champTeams[0]) %></div>
    </div>      

	</div><div class="clear"></div>
	<div class="resultPointsDisp" style="text-indent: 150px;"><%= ScoringUtil.pointDisplay(ScoringUtil.calculateTeamsPoints(20, _SweepWorldcupDefault.getChampion(), result.getChampion()))%></div>          
	<div class="clear"></div>
	<div>

        <div id="sweepWorldcupForm_cancel" class="formCancel" style="float:left;margin-left:0px;">       
            <a href="/t_<%=retPage%>.html">Go Back </a>
        </div>      


	</div><div class="clear"></div>

</form>
</div> <!-- form -->
<br/>
<div id="sweepWorldcupForm_bottomArea" class="formBottomArea"></div>



