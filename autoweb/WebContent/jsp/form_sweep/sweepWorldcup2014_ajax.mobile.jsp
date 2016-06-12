<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "sweep_worldcup2014_home";

    String _playerValue= WebUtil.display((String)reqParams.get("player"));
    String _game_1Value= WebUtil.display((String)reqParams.get("game1"));
    String _game_1_scoreValue= WebUtil.display((String)reqParams.get("game1Score"));
    String _game_1_score_oppValue= WebUtil.display((String)reqParams.get("game1ScoreOpp"));
    String _game_2Value= WebUtil.display((String)reqParams.get("game2"));
    String _game_2_scoreValue= WebUtil.display((String)reqParams.get("game2Score"));
    String _game_2_score_oppValue= WebUtil.display((String)reqParams.get("game2ScoreOpp"));
    String _game_3Value= WebUtil.display((String)reqParams.get("game3"));
    String _game_3_scoreValue= WebUtil.display((String)reqParams.get("game3Score"));
    String _game_3_score_oppValue= WebUtil.display((String)reqParams.get("game3ScoreOpp"));
    String _advanceValue= WebUtil.display((String)reqParams.get("advance"));
    String _team16_a1Value= WebUtil.display((String)reqParams.get("team16A1"));
    String _team16_a2Value= WebUtil.display((String)reqParams.get("team16A2"));
    String _team16_b1Value= WebUtil.display((String)reqParams.get("team16B1"));
    String _team16_b2Value= WebUtil.display((String)reqParams.get("team16B2"));
    String _team16_c1Value= WebUtil.display((String)reqParams.get("team16C1"));
    String _team16_c2Value= WebUtil.display((String)reqParams.get("team16C2"));
    String _team16_d1Value= WebUtil.display((String)reqParams.get("team16D1"));
    String _team16_d2Value= WebUtil.display((String)reqParams.get("team16D2"));
    String _team16_e1Value= WebUtil.display((String)reqParams.get("team16E1"));
    String _team16_e2Value= WebUtil.display((String)reqParams.get("team16E2"));
    String _team16_f1Value= WebUtil.display((String)reqParams.get("team16F1"));
    String _team16_f2Value= WebUtil.display((String)reqParams.get("team16F2"));
    String _team16_g1Value= WebUtil.display((String)reqParams.get("team16G1"));
    String _team16_g2Value= WebUtil.display((String)reqParams.get("team16G2"));
    String _team16_h1Value= WebUtil.display((String)reqParams.get("team16H1"));
    String _team16_h2Value= WebUtil.display((String)reqParams.get("team16H2"));
    String _quarter_final_1Value= WebUtil.display((String)reqParams.get("quarterFinal1"));
    String _quarter_final_2Value= WebUtil.display((String)reqParams.get("quarterFinal2"));
    String _quarter_final_3Value= WebUtil.display((String)reqParams.get("quarterFinal3"));
    String _quarter_final_4Value= WebUtil.display((String)reqParams.get("quarterFinal4"));
    String _quarter_final_5Value= WebUtil.display((String)reqParams.get("quarterFinal5"));
    String _quarter_final_6Value= WebUtil.display((String)reqParams.get("quarterFinal6"));
    String _quarter_final_7Value= WebUtil.display((String)reqParams.get("quarterFinal7"));
    String _quarter_final_8Value= WebUtil.display((String)reqParams.get("quarterFinal8"));
    String _semi_final_1Value= WebUtil.display((String)reqParams.get("semiFinal1"));
    String _semi_final_2Value= WebUtil.display((String)reqParams.get("semiFinal2"));
    String _semi_final_3Value= WebUtil.display((String)reqParams.get("semiFinal3"));
    String _semi_final_4Value= WebUtil.display((String)reqParams.get("semiFinal4"));
    String _final_1Value= WebUtil.display((String)reqParams.get("final1"));
    String _final_2Value= WebUtil.display((String)reqParams.get("final2"));
    String _championValue= WebUtil.display((String)reqParams.get("champion"));
    String _final_score_winValue= WebUtil.display((String)reqParams.get("finalScoreWin"));
    String _final_score_loseValue= WebUtil.display((String)reqParams.get("finalScoreLose"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_sweep_worldcup2014_home.html"> SweepWorldcup2014 Home </a>
<%
	
	List list = null;
	list = SweepWorldcup2014DS.getInstance().getBySiteId(site.getId());

%>

<div id="sweepWorldcup2014List"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) iter.next();	
%>

	<div id="sweepWorldcup2014Frame<%=_SweepWorldcup2014.getId() %>" >

		<div id="player" >
			player:<%= _SweepWorldcup2014.getPlayer() %>
		</div>
		<div id="game1" >
			game1:<%= _SweepWorldcup2014.getGame1() %>
		</div>
		<div id="game1Score" >
			game1Score:<%= _SweepWorldcup2014.getGame1Score() %>
		</div>
		<div id="game1ScoreOpp" >
			game1ScoreOpp:<%= _SweepWorldcup2014.getGame1ScoreOpp() %>
		</div>
		<div id="game2" >
			game2:<%= _SweepWorldcup2014.getGame2() %>
		</div>
		<div id="game2Score" >
			game2Score:<%= _SweepWorldcup2014.getGame2Score() %>
		</div>
		<div id="game2ScoreOpp" >
			game2ScoreOpp:<%= _SweepWorldcup2014.getGame2ScoreOpp() %>
		</div>
		<div id="game3" >
			game3:<%= _SweepWorldcup2014.getGame3() %>
		</div>
		<div id="game3Score" >
			game3Score:<%= _SweepWorldcup2014.getGame3Score() %>
		</div>
		<div id="game3ScoreOpp" >
			game3ScoreOpp:<%= _SweepWorldcup2014.getGame3ScoreOpp() %>
		</div>
		<div id="advance" >
			advance:<%= _SweepWorldcup2014.getAdvance() %>
		</div>
		<div id="team16A1" >
			team16A1:<%= _SweepWorldcup2014.getTeam16A1() %>
		</div>
		<div id="team16A2" >
			team16A2:<%= _SweepWorldcup2014.getTeam16A2() %>
		</div>
		<div id="team16B1" >
			team16B1:<%= _SweepWorldcup2014.getTeam16B1() %>
		</div>
		<div id="team16B2" >
			team16B2:<%= _SweepWorldcup2014.getTeam16B2() %>
		</div>
		<div id="team16C1" >
			team16C1:<%= _SweepWorldcup2014.getTeam16C1() %>
		</div>
		<div id="team16C2" >
			team16C2:<%= _SweepWorldcup2014.getTeam16C2() %>
		</div>
		<div id="team16D1" >
			team16D1:<%= _SweepWorldcup2014.getTeam16D1() %>
		</div>
		<div id="team16D2" >
			team16D2:<%= _SweepWorldcup2014.getTeam16D2() %>
		</div>
		<div id="team16E1" >
			team16E1:<%= _SweepWorldcup2014.getTeam16E1() %>
		</div>
		<div id="team16E2" >
			team16E2:<%= _SweepWorldcup2014.getTeam16E2() %>
		</div>
		<div id="team16F1" >
			team16F1:<%= _SweepWorldcup2014.getTeam16F1() %>
		</div>
		<div id="team16F2" >
			team16F2:<%= _SweepWorldcup2014.getTeam16F2() %>
		</div>
		<div id="team16G1" >
			team16G1:<%= _SweepWorldcup2014.getTeam16G1() %>
		</div>
		<div id="team16G2" >
			team16G2:<%= _SweepWorldcup2014.getTeam16G2() %>
		</div>
		<div id="team16H1" >
			team16H1:<%= _SweepWorldcup2014.getTeam16H1() %>
		</div>
		<div id="team16H2" >
			team16H2:<%= _SweepWorldcup2014.getTeam16H2() %>
		</div>
		<div id="quarterFinal1" >
			quarterFinal1:<%= _SweepWorldcup2014.getQuarterFinal1() %>
		</div>
		<div id="quarterFinal2" >
			quarterFinal2:<%= _SweepWorldcup2014.getQuarterFinal2() %>
		</div>
		<div id="quarterFinal3" >
			quarterFinal3:<%= _SweepWorldcup2014.getQuarterFinal3() %>
		</div>
		<div id="quarterFinal4" >
			quarterFinal4:<%= _SweepWorldcup2014.getQuarterFinal4() %>
		</div>
		<div id="quarterFinal5" >
			quarterFinal5:<%= _SweepWorldcup2014.getQuarterFinal5() %>
		</div>
		<div id="quarterFinal6" >
			quarterFinal6:<%= _SweepWorldcup2014.getQuarterFinal6() %>
		</div>
		<div id="quarterFinal7" >
			quarterFinal7:<%= _SweepWorldcup2014.getQuarterFinal7() %>
		</div>
		<div id="quarterFinal8" >
			quarterFinal8:<%= _SweepWorldcup2014.getQuarterFinal8() %>
		</div>
		<div id="semiFinal1" >
			semiFinal1:<%= _SweepWorldcup2014.getSemiFinal1() %>
		</div>
		<div id="semiFinal2" >
			semiFinal2:<%= _SweepWorldcup2014.getSemiFinal2() %>
		</div>
		<div id="semiFinal3" >
			semiFinal3:<%= _SweepWorldcup2014.getSemiFinal3() %>
		</div>
		<div id="semiFinal4" >
			semiFinal4:<%= _SweepWorldcup2014.getSemiFinal4() %>
		</div>
		<div id="final1" >
			final1:<%= _SweepWorldcup2014.getFinal1() %>
		</div>
		<div id="final2" >
			final2:<%= _SweepWorldcup2014.getFinal2() %>
		</div>
		<div id="champion" >
			champion:<%= _SweepWorldcup2014.getChampion() %>
		</div>
		<div id="finalScoreWin" >
			finalScoreWin:<%= _SweepWorldcup2014.getFinalScoreWin() %>
		</div>
		<div id="finalScoreLose" >
			finalScoreLose:<%= _SweepWorldcup2014.getFinalScoreLose() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _SweepWorldcup2014.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _SweepWorldcup2014.getTimeUpdated() %>
		</div>
		<div>
		<a id="sweepWorldcup2014DeleteButton" href="javascript:deleteThis('/sweepWorldcup2014Action.html',<%= _SweepWorldcup2014.getId()%>,'sweepWorldcup2014Frame<%=_SweepWorldcup2014.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="sweepWorldcup2014Form" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepWorldcup2014FormAdd" method="POST" action="/sweepWorldcup2014Action.html" id="sweepWorldcup2014FormAdd">

	<div id="sweepWorldcup2014Form_player_field">
    <div id="sweepWorldcup2014Form_player_label" class="formLabel" >Player </div>
    <div id="sweepWorldcup2014Form_player_text" class="formFieldText" >       
        <input class="field" id="_ffd_player" type="text" size="70" name="player" value="<%=WebUtil.display(_playerValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game1_label" class="formLabel" >Game 1 </div>
    <div id="sweepWorldcup2014Form_game1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="game1" id="_ffd_game1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusGame1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Game1Option");
	for(Iterator iterItems = dropMenusGame1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game1Score_field">
    <div id="sweepWorldcup2014Form_game1Score_label" class="formLabel" >Game 1 Score </div>
    <div id="sweepWorldcup2014Form_game1Score_text" class="formFieldText" >       
        <input class="field" id="_ffd_game1Score" type="text" size="70" name="game1Score" value="<%=WebUtil.display(_game_1_scoreValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game1ScoreOpp_field">
    <div id="sweepWorldcup2014Form_game1ScoreOpp_label" class="formLabel" >Game 1 Score Opp </div>
    <div id="sweepWorldcup2014Form_game1ScoreOpp_text" class="formFieldText" >       
        <input class="field" id="_ffd_game1ScoreOpp" type="text" size="70" name="game1ScoreOpp" value="<%=WebUtil.display(_game_1_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game2_label" class="formLabel" >Game 2 </div>
    <div id="sweepWorldcup2014Form_game2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="game2" id="_ffd_game2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusGame2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Game2Option");
	for(Iterator iterItems = dropMenusGame2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game2Score_field">
    <div id="sweepWorldcup2014Form_game2Score_label" class="formLabel" >Game 2 Score </div>
    <div id="sweepWorldcup2014Form_game2Score_text" class="formFieldText" >       
        <input class="field" id="_ffd_game2Score" type="text" size="70" name="game2Score" value="<%=WebUtil.display(_game_2_scoreValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game2ScoreOpp_field">
    <div id="sweepWorldcup2014Form_game2ScoreOpp_label" class="formLabel" >Game 2 Score Opp </div>
    <div id="sweepWorldcup2014Form_game2ScoreOpp_text" class="formFieldText" >       
        <input class="field" id="_ffd_game2ScoreOpp" type="text" size="70" name="game2ScoreOpp" value="<%=WebUtil.display(_game_2_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game3_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game3_label" class="formLabel" >Game 3 </div>
    <div id="sweepWorldcup2014Form_game3_dropdown" class="formFieldDropDown" >       
        <select class="field" name="game3" id="_ffd_game3">
        <option value="" >- Please Select -</option>
<%
	List dropMenusGame3 = DropMenuUtil.getDropMenus("SweepWorldcup2014Game3Option");
	for(Iterator iterItems = dropMenusGame3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game3Score_field">
    <div id="sweepWorldcup2014Form_game3Score_label" class="formLabel" >Game 3 Score </div>
    <div id="sweepWorldcup2014Form_game3Score_text" class="formFieldText" >       
        <input class="field" id="_ffd_game3Score" type="text" size="70" name="game3Score" value="<%=WebUtil.display(_game_3_scoreValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_game3ScoreOpp_field">
    <div id="sweepWorldcup2014Form_game3ScoreOpp_label" class="formLabel" >Game 3 Score Opp </div>
    <div id="sweepWorldcup2014Form_game3ScoreOpp_text" class="formFieldText" >       
        <input class="field" id="_ffd_game3ScoreOpp" type="text" size="70" name="game3ScoreOpp" value="<%=WebUtil.display(_game_3_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_advance_field">
    <div id="sweepWorldcup2014Form_advance_label" class="formLabel" >Advance </div>
    <div id="sweepWorldcup2014Form_advance_text" class="formFieldText" >       
        <input class="field" id="_ffd_advance" type="text" size="70" name="advance" value="<%=WebUtil.display(_advanceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16A1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16A1_label" class="formLabel" >Team16 A1 </div>
    <div id="sweepWorldcup2014Form_team16A1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16A1" id="_ffd_team16A1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16A1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16A1Option");
	for(Iterator iterItems = dropMenusTeam16A1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_a1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16A2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16A2_label" class="formLabel" >Team16 A2 </div>
    <div id="sweepWorldcup2014Form_team16A2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16A2" id="_ffd_team16A2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16A2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16A2Option");
	for(Iterator iterItems = dropMenusTeam16A2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_a2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16B1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16B1_label" class="formLabel" >Team16 B1 </div>
    <div id="sweepWorldcup2014Form_team16B1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16B1" id="_ffd_team16B1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16B1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16B1Option");
	for(Iterator iterItems = dropMenusTeam16B1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_b1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16B2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16B2_label" class="formLabel" >Team16 B2 </div>
    <div id="sweepWorldcup2014Form_team16B2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16B2" id="_ffd_team16B2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16B2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16B2Option");
	for(Iterator iterItems = dropMenusTeam16B2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_b2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16C1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16C1_label" class="formLabel" >Team16 C1 </div>
    <div id="sweepWorldcup2014Form_team16C1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16C1" id="_ffd_team16C1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16C1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16C1Option");
	for(Iterator iterItems = dropMenusTeam16C1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_c1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16C2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16C2_label" class="formLabel" >Team16 C2 </div>
    <div id="sweepWorldcup2014Form_team16C2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16C2" id="_ffd_team16C2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16C2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16C2Option");
	for(Iterator iterItems = dropMenusTeam16C2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_c2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16D1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16D1_label" class="formLabel" >Team16 D1 </div>
    <div id="sweepWorldcup2014Form_team16D1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16D1" id="_ffd_team16D1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16D1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16D1Option");
	for(Iterator iterItems = dropMenusTeam16D1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_d1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16D2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16D2_label" class="formLabel" >Team16 D2 </div>
    <div id="sweepWorldcup2014Form_team16D2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16D2" id="_ffd_team16D2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16D2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16D2Option");
	for(Iterator iterItems = dropMenusTeam16D2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_d2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16E1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16E1_label" class="formLabel" >Team16 E1 </div>
    <div id="sweepWorldcup2014Form_team16E1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16E1" id="_ffd_team16E1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16E1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16E1Option");
	for(Iterator iterItems = dropMenusTeam16E1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_e1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16E2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16E2_label" class="formLabel" >Team16 E2 </div>
    <div id="sweepWorldcup2014Form_team16E2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16E2" id="_ffd_team16E2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16E2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16E2Option");
	for(Iterator iterItems = dropMenusTeam16E2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_e2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16F1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16F1_label" class="formLabel" >Team16 F1 </div>
    <div id="sweepWorldcup2014Form_team16F1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16F1" id="_ffd_team16F1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16F1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16F1Option");
	for(Iterator iterItems = dropMenusTeam16F1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_f1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16F2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16F2_label" class="formLabel" >Team16 F2 </div>
    <div id="sweepWorldcup2014Form_team16F2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16F2" id="_ffd_team16F2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16F2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16F2Option");
	for(Iterator iterItems = dropMenusTeam16F2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_f2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16G1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16G1_label" class="formLabel" >Team16 G1 </div>
    <div id="sweepWorldcup2014Form_team16G1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16G1" id="_ffd_team16G1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16G1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16G1Option");
	for(Iterator iterItems = dropMenusTeam16G1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_g1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16G2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16G2_label" class="formLabel" >Team16 G2 </div>
    <div id="sweepWorldcup2014Form_team16G2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16G2" id="_ffd_team16G2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16G2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16G2Option");
	for(Iterator iterItems = dropMenusTeam16G2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_g2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16H1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16H1_label" class="formLabel" >Team16 H1 </div>
    <div id="sweepWorldcup2014Form_team16H1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16H1" id="_ffd_team16H1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16H1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16H1Option");
	for(Iterator iterItems = dropMenusTeam16H1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_h1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_team16H2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16H2_label" class="formLabel" >Team16 H2 </div>
    <div id="sweepWorldcup2014Form_team16H2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16H2" id="_ffd_team16H2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16H2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16H2Option");
	for(Iterator iterItems = dropMenusTeam16H2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_h2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal1_label" class="formLabel" >Quarter Final 1 </div>
    <div id="sweepWorldcup2014Form_quarterFinal1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal1" id="_ffd_quarterFinal1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal1Option");
	for(Iterator iterItems = dropMenusQuarterFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal2_label" class="formLabel" >Quarter Final 2 </div>
    <div id="sweepWorldcup2014Form_quarterFinal2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal2" id="_ffd_quarterFinal2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal2Option");
	for(Iterator iterItems = dropMenusQuarterFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal3_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal3_label" class="formLabel" >Quarter Final 3 </div>
    <div id="sweepWorldcup2014Form_quarterFinal3_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal3" id="_ffd_quarterFinal3">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal3Option");
	for(Iterator iterItems = dropMenusQuarterFinal3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal4_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal4_label" class="formLabel" >Quarter Final 4 </div>
    <div id="sweepWorldcup2014Form_quarterFinal4_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal4" id="_ffd_quarterFinal4">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal4Option");
	for(Iterator iterItems = dropMenusQuarterFinal4.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_4Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal5_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal5_label" class="formLabel" >Quarter Final 5 </div>
    <div id="sweepWorldcup2014Form_quarterFinal5_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal5" id="_ffd_quarterFinal5">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal5 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal5Option");
	for(Iterator iterItems = dropMenusQuarterFinal5.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_5Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal6_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal6_label" class="formLabel" >Quarter Final 6 </div>
    <div id="sweepWorldcup2014Form_quarterFinal6_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal6" id="_ffd_quarterFinal6">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal6 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal6Option");
	for(Iterator iterItems = dropMenusQuarterFinal6.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_6Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal7_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal7_label" class="formLabel" >Quarter Final 7 </div>
    <div id="sweepWorldcup2014Form_quarterFinal7_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal7" id="_ffd_quarterFinal7">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal7 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal7Option");
	for(Iterator iterItems = dropMenusQuarterFinal7.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_7Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_quarterFinal8_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_quarterFinal8_label" class="formLabel" >Quarter Final 8 </div>
    <div id="sweepWorldcup2014Form_quarterFinal8_dropdown" class="formFieldDropDown" >       
        <select class="field" name="quarterFinal8" id="_ffd_quarterFinal8">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal8 = DropMenuUtil.getDropMenus("SweepWorldcup2014QuarterFinal8Option");
	for(Iterator iterItems = dropMenusQuarterFinal8.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_8Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_semiFinal1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_semiFinal1_label" class="formLabel" >Semi Final 1 </div>
    <div id="sweepWorldcup2014Form_semiFinal1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="semiFinal1" id="_ffd_semiFinal1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014SemiFinal1Option");
	for(Iterator iterItems = dropMenusSemiFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_semiFinal2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_semiFinal2_label" class="formLabel" >Semi Final 2 </div>
    <div id="sweepWorldcup2014Form_semiFinal2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="semiFinal2" id="_ffd_semiFinal2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014SemiFinal2Option");
	for(Iterator iterItems = dropMenusSemiFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_semiFinal3_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_semiFinal3_label" class="formLabel" >Semi Final 3 </div>
    <div id="sweepWorldcup2014Form_semiFinal3_dropdown" class="formFieldDropDown" >       
        <select class="field" name="semiFinal3" id="_ffd_semiFinal3">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014SemiFinal3Option");
	for(Iterator iterItems = dropMenusSemiFinal3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_semiFinal4_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_semiFinal4_label" class="formLabel" >Semi Final 4 </div>
    <div id="sweepWorldcup2014Form_semiFinal4_dropdown" class="formFieldDropDown" >       
        <select class="field" name="semiFinal4" id="_ffd_semiFinal4">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014SemiFinal4Option");
	for(Iterator iterItems = dropMenusSemiFinal4.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_4Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_final1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_final1_label" class="formLabel" >Final 1 </div>
    <div id="sweepWorldcup2014Form_final1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="final1" id="_ffd_final1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014Final1Option");
	for(Iterator iterItems = dropMenusFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _final_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_final2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_final2_label" class="formLabel" >Final 2 </div>
    <div id="sweepWorldcup2014Form_final2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="final2" id="_ffd_final2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014Final2Option");
	for(Iterator iterItems = dropMenusFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _final_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_champion_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_champion_label" class="formLabel" >Champion </div>
    <div id="sweepWorldcup2014Form_champion_dropdown" class="formFieldDropDown" >       
        <select class="field" name="champion" id="_ffd_champion">
        <option value="" >- Please Select -</option>
<%
	List dropMenusChampion = DropMenuUtil.getDropMenus("SweepWorldcup2014ChampionOption");
	for(Iterator iterItems = dropMenusChampion.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _championValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_finalScoreWin_field">
    <div id="sweepWorldcup2014Form_finalScoreWin_label" class="formLabel" >Final Score Win </div>
    <div id="sweepWorldcup2014Form_finalScoreWin_text" class="formFieldText" >       
        <input class="field" id="_ffd_finalScoreWin" type="text" size="70" name="finalScoreWin" value="<%=WebUtil.display(_final_score_winValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="sweepWorldcup2014Form_finalScoreLose_field">
    <div id="sweepWorldcup2014Form_finalScoreLose_label" class="formLabel" >Final Score Lose </div>
    <div id="sweepWorldcup2014Form_finalScoreLose_text" class="formFieldText" >       
        <input class="field" id="_ffd_finalScoreLose" type="text" size="70" name="finalScoreLose" value="<%=WebUtil.display(_final_score_loseValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="sweepWorldcup2014FormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/sweepWorldcup2014Action.html', 'sweepWorldcup2014FormAdd', 'formSubmit_ajax', 'sweepWorldcup2014');">Submit</a>
        </div> 

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->

<hr>

<br/><br/><br/><br/>

<hr>
<h3> Self hiding pure javascript form</h3>

<script type="text/javascript">

function xmlhttpPostXX(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = getXX(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&ajaxOutArg=last&formfieldlist="+dispFieldList;
	
    var xmlHttpReq = false;
    var self = this;
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var strURL = target+ "?" + parms;
    //alert(strURL);
    
    self.xmlHttpReq.open('POST', target, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
        	//alert(self.xmlHttpReq.responseText);
        	fade(formName, 1000, function() {
        		if (callback == null )
        			updatepageXX(dispElementId, dispElementId, self.xmlHttpReq.responseText);
        		else
        			callback(self.xmlHttpReq.responseText);
        	});
        }
    }
    self.xmlHttpReq.send(parms);
}

function updatepageXX(eid, str){
	document.getElementById(eid).innerHTML = str;
}

function getXX(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
       
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
           		alert(obj.childNodes[i].name + "=" + obj.childNodes[i].value);
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       }   
       
       if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
       }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
       }
    }
	alert(getstr);
    return getstr;
}



function clearFormXX(name) {
	var obj = document.getElementById(name);
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "password") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "file") {
               obj.childNodes[i].value = "";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             } 
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             }
          }
       }   
       if (obj.childNodes[i].tagName == "SELECT") {
			obj.childNodes[i].selectedIndex = 0;
        }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           obj.childNodes[i].value = "";
        }
        
    }
}


function  animateFadeXX(lastTick, eid, timeToFade)
{  
  var curTick = new Date().getTime();
  var elapsedTicks = curTick - lastTick;
 
  var element = document.getElementById(eid);
 
  if(element.FadeTimeLeft <= elapsedTicks)
  {
    element.style.opacity = element.FadeState == 1 ? '1' : '0';
    element.style.filter = 'alpha(opacity = '
        + (element.FadeState == 1 ? '100' : '0') + ')';
    element.FadeState = element.FadeState == 1 ? 2 : -2;
	document.getElementById(eid).style.display = 'none';
	element.callbackAfter(element.callbackArg);
    return;
  }
 
  element.FadeTimeLeft -= elapsedTicks;
  var newOpVal = element.FadeTimeLeft/timeToFade;
  if(element.FadeState == 1)
    newOpVal = 1 - newOpVal;

  element.style.opacity = newOpVal;
  element.style.filter = 'alpha(opacity = ' + (newOpVal*100) + ')';
 
  setTimeout("animateFadeXX(" + curTick + ",'" + eid + "','" + timeToFade + "')", 33);
}


//var  TimeToFade = 1000.0;

function fadeXX(eid, timeToFade, callback, callbackArg)
{
  var element = document.getElementById(eid);
  if(element == null)
    return;
   
  if(element.FadeState == null)
  {
    if(element.style.opacity == null
        || element.style.opacity == ''
        || element.style.opacity == '1')
    {
      element.FadeState = 2;
    }
    else
    {
      element.FadeState = -2;
    }
  }
   
  if(element.FadeState == 1 || element.FadeState == -1)
  {
    element.FadeState = element.FadeState == 1 ? -1 : 1;
    element.FadeTimeLeft = timeToFade - element.FadeTimeLeft;
  }
  else
  {
    element.FadeState = element.FadeState == 2 ? -1 : 1;
    element.FadeTimeLeft = timeToFade;
	
    element.callbackAfter = callback;
    element.callbackArg = callbackArg;
    setTimeout("animateFadeXX(" + new Date().getTime() + ",'" + eid + "','" + timeToFade + "')", 33);
  }  
}

function backToXX(eid, displayFormId){
	document.getElementById(displayFormId).innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

</script>

<script type="text/javascript">

function responseCallback(data){
	document.getElementById("resultDisplaySweepWorldcup2014").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('sweepWorldcup2014FormAddDis','/sweepWorldcup2014Action.html', 'resultDisplaySweepWorldcup2014', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="sweepWorldcup2014FormAddDis" method="POST" action="/sweepWorldcup2014Action.html" id="sweepWorldcup2014FormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Player</div>
        <input class="field" id="player" type="text" size="70" name="player" value="<%=WebUtil.display(_playerValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 1</div>
        <select class="field" name="game1" id="game1">
        <option value="" >- Please Select -</option>
<%
	dropMenusGame1 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 1 Score</div>
        <input class="field" id="game1Score" type="text" size="70" name="game1Score" value="<%=WebUtil.display(_game_1_scoreValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 1 Score Opp</div>
        <input class="field" id="game1ScoreOpp" type="text" size="70" name="game1ScoreOpp" value="<%=WebUtil.display(_game_1_score_oppValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 2</div>
        <select class="field" name="game2" id="game2">
        <option value="" >- Please Select -</option>
<%
	dropMenusGame2 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 2 Score</div>
        <input class="field" id="game2Score" type="text" size="70" name="game2Score" value="<%=WebUtil.display(_game_2_scoreValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 2 Score Opp</div>
        <input class="field" id="game2ScoreOpp" type="text" size="70" name="game2ScoreOpp" value="<%=WebUtil.display(_game_2_score_oppValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 3</div>
        <select class="field" name="game3" id="game3">
        <option value="" >- Please Select -</option>
<%
	dropMenusGame3 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 3 Score</div>
        <input class="field" id="game3Score" type="text" size="70" name="game3Score" value="<%=WebUtil.display(_game_3_scoreValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Game 3 Score Opp</div>
        <input class="field" id="game3ScoreOpp" type="text" size="70" name="game3ScoreOpp" value="<%=WebUtil.display(_game_3_score_oppValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Advance</div>
        <input class="field" id="advance" type="text" size="70" name="advance" value="<%=WebUtil.display(_advanceValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 A1</div>
        <select class="field" name="team16A1" id="team16A1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16A1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16A1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_a1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 A2</div>
        <select class="field" name="team16A2" id="team16A2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16A2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16A2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_a2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 B1</div>
        <select class="field" name="team16B1" id="team16B1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16B1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16B1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_b1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 B2</div>
        <select class="field" name="team16B2" id="team16B2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16B2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16B2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_b2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 C1</div>
        <select class="field" name="team16C1" id="team16C1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16C1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16C1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_c1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 C2</div>
        <select class="field" name="team16C2" id="team16C2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16C2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16C2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_c2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 D1</div>
        <select class="field" name="team16D1" id="team16D1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16D1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16D1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_d1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 D2</div>
        <select class="field" name="team16D2" id="team16D2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16D2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16D2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_d2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 E1</div>
        <select class="field" name="team16E1" id="team16E1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16E1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16E1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_e1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 E2</div>
        <select class="field" name="team16E2" id="team16E2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16E2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16E2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_e2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 F1</div>
        <select class="field" name="team16F1" id="team16F1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16F1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16F1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_f1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 F2</div>
        <select class="field" name="team16F2" id="team16F2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16F2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16F2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_f2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 G1</div>
        <select class="field" name="team16G1" id="team16G1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16G1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16G1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_g1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 G2</div>
        <select class="field" name="team16G2" id="team16G2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16G2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16G2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_g2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 H1</div>
        <select class="field" name="team16H1" id="team16H1">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16H1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16H1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_h1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Team16 H2</div>
        <select class="field" name="team16H2" id="team16H2">
        <option value="" >- Please Select -</option>
<%
	dropMenusTeam16H2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16H2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _team16_h2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 1</div>
        <select class="field" name="quarterFinal1" id="quarterFinal1">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 2</div>
        <select class="field" name="quarterFinal2" id="quarterFinal2">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 3</div>
        <select class="field" name="quarterFinal3" id="quarterFinal3">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 4</div>
        <select class="field" name="quarterFinal4" id="quarterFinal4">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal4.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_4Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 5</div>
        <select class="field" name="quarterFinal5" id="quarterFinal5">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal5 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal5.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_5Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 6</div>
        <select class="field" name="quarterFinal6" id="quarterFinal6">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal6 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal6.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_6Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 7</div>
        <select class="field" name="quarterFinal7" id="quarterFinal7">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal7 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal7.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_7Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Quarter Final 8</div>
        <select class="field" name="quarterFinal8" id="quarterFinal8">
        <option value="" >- Please Select -</option>
<%
	dropMenusQuarterFinal8 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal8.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _quarter_final_8Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Semi Final 1</div>
        <select class="field" name="semiFinal1" id="semiFinal1">
        <option value="" >- Please Select -</option>
<%
	dropMenusSemiFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Semi Final 2</div>
        <select class="field" name="semiFinal2" id="semiFinal2">
        <option value="" >- Please Select -</option>
<%
	dropMenusSemiFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Semi Final 3</div>
        <select class="field" name="semiFinal3" id="semiFinal3">
        <option value="" >- Please Select -</option>
<%
	dropMenusSemiFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Semi Final 4</div>
        <select class="field" name="semiFinal4" id="semiFinal4">
        <option value="" >- Please Select -</option>
<%
	dropMenusSemiFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal4.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _semi_final_4Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Final 1</div>
        <select class="field" name="final1" id="final1">
        <option value="" >- Please Select -</option>
<%
	dropMenusFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _final_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Final 2</div>
        <select class="field" name="final2" id="final2">
        <option value="" >- Please Select -</option>
<%
	dropMenusFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _final_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Champion</div>
        <select class="field" name="champion" id="champion">
        <option value="" >- Please Select -</option>
<%
	dropMenusChampion = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusChampion.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _championValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Final Score Win</div>
        <input class="field" id="finalScoreWin" type="text" size="70" name="finalScoreWin" value="<%=WebUtil.display(_final_score_winValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Final Score Lose</div>
        <input class="field" id="finalScoreLose" type="text" size="70" name="finalScoreLose" value="<%=WebUtil.display(_final_score_loseValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('sweepWorldcup2014FormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplaySweepWorldcup2014"></span>
<a href="javascript:backToXX('sweepWorldcup2014FormAddDis','resultDisplaySweepWorldcup2014')">show back</a><br>
<hr/>
