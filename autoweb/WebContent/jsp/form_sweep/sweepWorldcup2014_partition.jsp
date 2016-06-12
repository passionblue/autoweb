<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _playerValue= "";
    String _game_1Value= "";
    String _game_1_scoreValue= "";
    String _game_1_score_oppValue= "";
    String _game_2Value= "";
    String _game_2_scoreValue= "";
    String _game_2_score_oppValue= "";
    String _game_3Value= "";
    String _game_3_scoreValue= "";
    String _game_3_score_oppValue= "";
    String _advanceValue= "";
    String _team16_a1Value= "";
    String _team16_a2Value= "";
    String _team16_b1Value= "";
    String _team16_b2Value= "";
    String _team16_c1Value= "";
    String _team16_c2Value= "";
    String _team16_d1Value= "";
    String _team16_d2Value= "";
    String _team16_e1Value= "";
    String _team16_e2Value= "";
    String _team16_f1Value= "";
    String _team16_f2Value= "";
    String _team16_g1Value= "";
    String _team16_g2Value= "";
    String _team16_h1Value= "";
    String _team16_h2Value= "";
    String _quarter_final_1Value= "";
    String _quarter_final_2Value= "";
    String _quarter_final_3Value= "";
    String _quarter_final_4Value= "";
    String _quarter_final_5Value= "";
    String _quarter_final_6Value= "";
    String _quarter_final_7Value= "";
    String _quarter_final_8Value= "";
    String _semi_final_1Value= "";
    String _semi_final_2Value= "";
    String _semi_final_3Value= "";
    String _semi_final_4Value= "";
    String _final_1Value= "";
    String _final_2Value= "";
    String _championValue= "";
    String _final_score_winValue= "";
    String _final_score_loseValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_sweep_worldcup2014_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /SweepWorldcup2014_artition.jsp -->

	<script type="text/javascript">
		function sendForm_sweep_worldcup2014_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="sweepWorldcup2014Form_player_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_player_label" class="formLabel" >Player </div>
    <div id="sweepWorldcup2014Form_player_text" class="formFieldText" >       
        <input id="player" class="field" type="text" size="70" name="player" value="<%=WebUtil.display(_playerValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="sweepWorldcup2014Form_game1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game1_label" class="formLabel" >Game 1 </div>
    <div id="sweepWorldcup2014Form_game1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="game1" id="game1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusGame1 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_1Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_game1Score_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game1Score_label" class="formLabel" >Game 1 Score </div>
    <div id="sweepWorldcup2014Form_game1Score_text" class="formFieldText" >       
        <input id="game1Score" class="field" type="text" size="70" name="game1Score" value="<%=WebUtil.display(_game_1_scoreValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_game1ScoreOpp_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game1ScoreOpp_label" class="formLabel" >Game 1 Score Opp </div>
    <div id="sweepWorldcup2014Form_game1ScoreOpp_text" class="formFieldText" >       
        <input id="game1ScoreOpp" class="field" type="text" size="70" name="game1ScoreOpp" value="<%=WebUtil.display(_game_1_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="sweepWorldcup2014Form_game2_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game2_label" class="formLabel" >Game 2 </div>
    <div id="sweepWorldcup2014Form_game2_dropdown" class="formFieldDropDown" >       
        <select class="field" name="game2" id="game2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusGame2 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_2Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_game2Score_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game2Score_label" class="formLabel" >Game 2 Score </div>
    <div id="sweepWorldcup2014Form_game2Score_text" class="formFieldText" >       
        <input id="game2Score" class="field" type="text" size="70" name="game2Score" value="<%=WebUtil.display(_game_2_scoreValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_game2ScoreOpp_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game2ScoreOpp_label" class="formLabel" >Game 2 Score Opp </div>
    <div id="sweepWorldcup2014Form_game2ScoreOpp_text" class="formFieldText" >       
        <input id="game2ScoreOpp" class="field" type="text" size="70" name="game2ScoreOpp" value="<%=WebUtil.display(_game_2_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="sweepWorldcup2014Form_game3_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game3_label" class="formLabel" >Game 3 </div>
    <div id="sweepWorldcup2014Form_game3_dropdown" class="formFieldDropDown" >       
        <select class="field" name="game3" id="game3">
        <option value="" >- Please Select -</option>
<%
	List dropMenusGame3 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _game_3Value)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_game3Score_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game3Score_label" class="formLabel" >Game 3 Score </div>
    <div id="sweepWorldcup2014Form_game3Score_text" class="formFieldText" >       
        <input id="game3Score" class="field" type="text" size="70" name="game3Score" value="<%=WebUtil.display(_game_3_scoreValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_game3ScoreOpp_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game3ScoreOpp_label" class="formLabel" >Game 3 Score Opp </div>
    <div id="sweepWorldcup2014Form_game3ScoreOpp_text" class="formFieldText" >       
        <input id="game3ScoreOpp" class="field" type="text" size="70" name="game3ScoreOpp" value="<%=WebUtil.display(_game_3_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_advance_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_advance_label" class="formLabel" >Advance </div>
    <div id="sweepWorldcup2014Form_advance_text" class="formFieldText" >       
        <input id="advance" class="field" type="text" size="70" name="advance" value="<%=WebUtil.display(_advanceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="sweepWorldcup2014Form_team16A1_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_team16A1_label" class="formLabel" >Team16 A1 </div>
    <div id="sweepWorldcup2014Form_team16A1_dropdown" class="formFieldDropDown" >       
        <select class="field" name="team16A1" id="team16A1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16A1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16A2" id="team16A2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16A2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16B1" id="team16B1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16B1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16B2" id="team16B2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16B2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16C1" id="team16C1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16C1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16C2" id="team16C2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16C2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16D1" id="team16D1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16D1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16D2" id="team16D2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16D2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16E1" id="team16E1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16E1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16E2" id="team16E2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16E2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16F1" id="team16F1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16F1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16F2" id="team16F2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16F2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16G1" id="team16G1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16G1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16G2" id="team16G2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16G2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16H1" id="team16H1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16H1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="team16H2" id="team16H2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusTeam16H2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal1" id="quarterFinal1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal2" id="quarterFinal2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal3" id="quarterFinal3">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal4" id="quarterFinal4">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal5" id="quarterFinal5">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal5 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal6" id="quarterFinal6">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal6 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal7" id="quarterFinal7">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal7 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="quarterFinal8" id="quarterFinal8">
        <option value="" >- Please Select -</option>
<%
	List dropMenusQuarterFinal8 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="semiFinal1" id="semiFinal1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="semiFinal2" id="semiFinal2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="semiFinal3" id="semiFinal3">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="semiFinal4" id="semiFinal4">
        <option value="" >- Please Select -</option>
<%
	List dropMenusSemiFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="final1" id="final1">
        <option value="" >- Please Select -</option>
<%
	List dropMenusFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="final2" id="final2">
        <option value="" >- Please Select -</option>
<%
	List dropMenusFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
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
        <select class="field" name="champion" id="champion">
        <option value="" >- Please Select -</option>
<%
	List dropMenusChampion = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusChampion.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _championValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_finalScoreWin_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_finalScoreWin_label" class="formLabel" >Final Score Win </div>
    <div id="sweepWorldcup2014Form_finalScoreWin_text" class="formFieldText" >       
        <input id="finalScoreWin" class="field" type="text" size="70" name="finalScoreWin" value="<%=WebUtil.display(_final_score_winValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcup2014Form_finalScoreLose_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_finalScoreLose_label" class="formLabel" >Final Score Lose </div>
    <div id="sweepWorldcup2014Form_finalScoreLose_text" class="formFieldText" >       
        <input id="finalScoreLose" class="field" type="text" size="70" name="finalScoreLose" value="<%=WebUtil.display(_final_score_loseValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>






		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_sweep_worldcup2014_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
