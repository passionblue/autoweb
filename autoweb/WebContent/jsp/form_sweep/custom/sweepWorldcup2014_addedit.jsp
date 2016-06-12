<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = new HashMap();
    } else {
        //isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    SweepWorldcup2014 _SweepWorldcup2014 = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SweepWorldcup2014 = SweepWorldcup2014DS.getInstance().getById(id);
		if ( _SweepWorldcup2014 == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SweepWorldcup2014 = new SweepWorldcup2014();// SweepWorldcup2014DS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _SweepWorldcup2014 == null) _SweepWorldcup2014 = new SweepWorldcup2014();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "sweep_worldcup2014_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _playerValue= (reqParams.get("player")==null?WebUtil.display(_SweepWorldcup2014.getPlayer()):WebUtil.display((String)reqParams.get("player")));
    String _game_1Value= (reqParams.get("game1")==null?WebUtil.display(_SweepWorldcup2014.getGame1()):WebUtil.display((String)reqParams.get("game1")));
    String _game_1_scoreValue= (reqParams.get("game1Score")==null?WebUtil.display(_SweepWorldcup2014.getGame1Score()):WebUtil.display((String)reqParams.get("game1Score")));
    String _game_1_score_oppValue= (reqParams.get("game1ScoreOpp")==null?WebUtil.display(_SweepWorldcup2014.getGame1ScoreOpp()):WebUtil.display((String)reqParams.get("game1ScoreOpp")));
    String _game_2Value= (reqParams.get("game2")==null?WebUtil.display(_SweepWorldcup2014.getGame2()):WebUtil.display((String)reqParams.get("game2")));
    String _game_2_scoreValue= (reqParams.get("game2Score")==null?WebUtil.display(_SweepWorldcup2014.getGame2Score()):WebUtil.display((String)reqParams.get("game2Score")));
    String _game_2_score_oppValue= (reqParams.get("game2ScoreOpp")==null?WebUtil.display(_SweepWorldcup2014.getGame2ScoreOpp()):WebUtil.display((String)reqParams.get("game2ScoreOpp")));
    String _game_3Value= (reqParams.get("game3")==null?WebUtil.display(_SweepWorldcup2014.getGame3()):WebUtil.display((String)reqParams.get("game3")));
    String _game_3_scoreValue= (reqParams.get("game3Score")==null?WebUtil.display(_SweepWorldcup2014.getGame3Score()):WebUtil.display((String)reqParams.get("game3Score")));
    String _game_3_score_oppValue= (reqParams.get("game3ScoreOpp")==null?WebUtil.display(_SweepWorldcup2014.getGame3ScoreOpp()):WebUtil.display((String)reqParams.get("game3ScoreOpp")));
    String _advanceValue= (reqParams.get("advance")==null?WebUtil.display(_SweepWorldcup2014.getAdvance()):WebUtil.display((String)reqParams.get("advance")));
    String _team16_a1Value= (reqParams.get("team16A1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16A1()):WebUtil.display((String)reqParams.get("team16A1")));
    String _team16_a2Value= (reqParams.get("team16A2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16A2()):WebUtil.display((String)reqParams.get("team16A2")));
    String _team16_b1Value= (reqParams.get("team16B1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16B1()):WebUtil.display((String)reqParams.get("team16B1")));
    String _team16_b2Value= (reqParams.get("team16B2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16B2()):WebUtil.display((String)reqParams.get("team16B2")));
    String _team16_c1Value= (reqParams.get("team16C1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16C1()):WebUtil.display((String)reqParams.get("team16C1")));
    String _team16_c2Value= (reqParams.get("team16C2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16C2()):WebUtil.display((String)reqParams.get("team16C2")));
    String _team16_d1Value= (reqParams.get("team16D1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16D1()):WebUtil.display((String)reqParams.get("team16D1")));
    String _team16_d2Value= (reqParams.get("team16D2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16D2()):WebUtil.display((String)reqParams.get("team16D2")));
    String _team16_e1Value= (reqParams.get("team16E1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16E1()):WebUtil.display((String)reqParams.get("team16E1")));
    String _team16_e2Value= (reqParams.get("team16E2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16E2()):WebUtil.display((String)reqParams.get("team16E2")));
    String _team16_f1Value= (reqParams.get("team16F1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16F1()):WebUtil.display((String)reqParams.get("team16F1")));
    String _team16_f2Value= (reqParams.get("team16F2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16F2()):WebUtil.display((String)reqParams.get("team16F2")));
    String _team16_g1Value= (reqParams.get("team16G1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16G1()):WebUtil.display((String)reqParams.get("team16G1")));
    String _team16_g2Value= (reqParams.get("team16G2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16G2()):WebUtil.display((String)reqParams.get("team16G2")));
    String _team16_h1Value= (reqParams.get("team16H1")==null?WebUtil.display(_SweepWorldcup2014.getTeam16H1()):WebUtil.display((String)reqParams.get("team16H1")));
    String _team16_h2Value= (reqParams.get("team16H2")==null?WebUtil.display(_SweepWorldcup2014.getTeam16H2()):WebUtil.display((String)reqParams.get("team16H2")));
    String _quarter_final_1Value= (reqParams.get("quarterFinal1")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal1()):WebUtil.display((String)reqParams.get("quarterFinal1")));
    String _quarter_final_2Value= (reqParams.get("quarterFinal2")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal2()):WebUtil.display((String)reqParams.get("quarterFinal2")));
    String _quarter_final_3Value= (reqParams.get("quarterFinal3")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal3()):WebUtil.display((String)reqParams.get("quarterFinal3")));
    String _quarter_final_4Value= (reqParams.get("quarterFinal4")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal4()):WebUtil.display((String)reqParams.get("quarterFinal4")));
    String _quarter_final_5Value= (reqParams.get("quarterFinal5")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal5()):WebUtil.display((String)reqParams.get("quarterFinal5")));
    String _quarter_final_6Value= (reqParams.get("quarterFinal6")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal6()):WebUtil.display((String)reqParams.get("quarterFinal6")));
    String _quarter_final_7Value= (reqParams.get("quarterFinal7")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal7()):WebUtil.display((String)reqParams.get("quarterFinal7")));
    String _quarter_final_8Value= (reqParams.get("quarterFinal8")==null?WebUtil.display(_SweepWorldcup2014.getQuarterFinal8()):WebUtil.display((String)reqParams.get("quarterFinal8")));
    String _semi_final_1Value= (reqParams.get("semiFinal1")==null?WebUtil.display(_SweepWorldcup2014.getSemiFinal1()):WebUtil.display((String)reqParams.get("semiFinal1")));
    String _semi_final_2Value= (reqParams.get("semiFinal2")==null?WebUtil.display(_SweepWorldcup2014.getSemiFinal2()):WebUtil.display((String)reqParams.get("semiFinal2")));
    String _semi_final_3Value= (reqParams.get("semiFinal3")==null?WebUtil.display(_SweepWorldcup2014.getSemiFinal3()):WebUtil.display((String)reqParams.get("semiFinal3")));
    String _semi_final_4Value= (reqParams.get("semiFinal4")==null?WebUtil.display(_SweepWorldcup2014.getSemiFinal4()):WebUtil.display((String)reqParams.get("semiFinal4")));
    String _final_1Value= (reqParams.get("final1")==null?WebUtil.display(_SweepWorldcup2014.getFinal1()):WebUtil.display((String)reqParams.get("final1")));
    String _final_2Value= (reqParams.get("final2")==null?WebUtil.display(_SweepWorldcup2014.getFinal2()):WebUtil.display((String)reqParams.get("final2")));
    String _championValue= (reqParams.get("champion")==null?WebUtil.display(_SweepWorldcup2014.getChampion()):WebUtil.display((String)reqParams.get("champion")));
    String _final_score_winValue= (reqParams.get("finalScoreWin")==null?WebUtil.display(_SweepWorldcup2014.getFinalScoreWin()):WebUtil.display((String)reqParams.get("finalScoreWin")));
    String _final_score_loseValue= (reqParams.get("finalScoreLose")==null?WebUtil.display(_SweepWorldcup2014.getFinalScoreLose()):WebUtil.display((String)reqParams.get("finalScoreLose")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SweepWorldcup2014.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SweepWorldcup2014.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "sweep_worldcup2014_" + System.nanoTime();
%> 

<br>
<div id="sweepWorldcup2014Form" class="formFrame">
<div id="sweepWorldcup2014FormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepWorldcup2014Form_Form" method="POST" action="/sweepWorldcup2014Action.html" id="sweepWorldcup2014Form_Form">





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
        <input id="game1Score" class="field" type="text" size="7" name="game1Score" value="<%=WebUtil.display(_game_1_scoreValue)%>"/> <span></span>
        <input id="game1ScoreOpp" class="field" type="text" size="7" name="game1ScoreOpp" value="<%=WebUtil.display(_game_1_score_oppValue)%>"/> <span></span>
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
        <input id="game2Score" class="field" type="text" size="7" name="game2Score" value="<%=WebUtil.display(_game_2_scoreValue)%>"/> <span></span>
        <input id="game2ScoreOpp" class="field" type="text" size="7" name="game2ScoreOpp" value="<%=WebUtil.display(_game_2_score_oppValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="sweepWorldcup2014Form_game2ScoreOpp_field" class="formFieldFrame">
    <div id="sweepWorldcup2014Form_game2ScoreOpp_label" class="formLabel" >Game 2 Score Opp </div>
    <div id="sweepWorldcup2014Form_game2ScoreOpp_text" class="formFieldText" >       
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







<div class="submitFrame">

    <div id="sweepWorldcup2014Form_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.sweepWorldcup2014Form_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="sweepWorldcup2014Form_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="sweepWorldcup2014Form_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="sweepWorldcup2014Form_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="sweepWorldcup2014Form_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="sweepWorldcup2014Form_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="sweepWorldcup2014Form_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="sweepWorldcup2014Form_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">
<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_sweep_worldcup2014_home.html">home</a> | <a href="/v_sweep_worldcup2014_home.html">home</a> | <a href="/v_sweep_worldcup2014_home.html">home</a>
<br/>
<br/>



<%
	List list_SweepWorldcup2014 = new ArrayList();
	SweepWorldcup2014DS ds_SweepWorldcup2014 = SweepWorldcup2014DS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SweepWorldcup2014 = ds_SweepWorldcup2014.getAll();
	else		
    	list_SweepWorldcup2014 = ds_SweepWorldcup2014.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_SweepWorldcup2014, numDisplayInPage, listPage);

	list_SweepWorldcup2014 = PagingUtil.getPagedList(pagingInfo, list_SweepWorldcup2014);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Player </td> 
    <td class="columnTitle">  Game 1 </td> 
    <td class="columnTitle">  Game 1 Score </td> 
    <td class="columnTitle">  Game 1 Score Opp </td> 
    <td class="columnTitle">  Game 2 </td> 
    <td class="columnTitle">  Game 2 Score </td> 
    <td class="columnTitle">  Game 2 Score Opp </td> 
    <td class="columnTitle">  Game 3 </td> 
    <td class="columnTitle">  Game 3 Score </td> 
    <td class="columnTitle">  Game 3 Score Opp </td> 
    <td class="columnTitle">  Advance </td> 
    <td class="columnTitle">  Team16 A1 </td> 
    <td class="columnTitle">  Team16 A2 </td> 
    <td class="columnTitle">  Team16 B1 </td> 
    <td class="columnTitle">  Team16 B2 </td> 
    <td class="columnTitle">  Team16 C1 </td> 
    <td class="columnTitle">  Team16 C2 </td> 
    <td class="columnTitle">  Team16 D1 </td> 
    <td class="columnTitle">  Team16 D2 </td> 
    <td class="columnTitle">  Team16 E1 </td> 
    <td class="columnTitle">  Team16 E2 </td> 
    <td class="columnTitle">  Team16 F1 </td> 
    <td class="columnTitle">  Team16 F2 </td> 
    <td class="columnTitle">  Team16 G1 </td> 
    <td class="columnTitle">  Team16 G2 </td> 
    <td class="columnTitle">  Team16 H1 </td> 
    <td class="columnTitle">  Team16 H2 </td> 
    <td class="columnTitle">  Quarter Final 1 </td> 
    <td class="columnTitle">  Quarter Final 2 </td> 
    <td class="columnTitle">  Quarter Final 3 </td> 
    <td class="columnTitle">  Quarter Final 4 </td> 
    <td class="columnTitle">  Quarter Final 5 </td> 
    <td class="columnTitle">  Quarter Final 6 </td> 
    <td class="columnTitle">  Quarter Final 7 </td> 
    <td class="columnTitle">  Quarter Final 8 </td> 
    <td class="columnTitle">  Semi Final 1 </td> 
    <td class="columnTitle">  Semi Final 2 </td> 
    <td class="columnTitle">  Semi Final 3 </td> 
    <td class="columnTitle">  Semi Final 4 </td> 
    <td class="columnTitle">  Final 1 </td> 
    <td class="columnTitle">  Final 2 </td> 
    <td class="columnTitle">  Champion </td> 
    <td class="columnTitle">  Final Score Win </td> 
    <td class="columnTitle">  Final Score Lose </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_SweepWorldcup2014.iterator();iter.hasNext();) {
        SweepWorldcup2014 o_SweepWorldcup2014 = (SweepWorldcup2014) iter.next();
%>

<TR id="tableRow<%= o_SweepWorldcup2014.getId()%>">
    <td> <%= o_SweepWorldcup2014.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_SweepWorldcup2014.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_SweepWorldcup2014.getPlayer()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame1Score()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame1ScoreOpp()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame2Score()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame2ScoreOpp()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame3()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame3Score()  %> </td>
	<td> <%= o_SweepWorldcup2014.getGame3ScoreOpp()  %> </td>
	<td> <%= o_SweepWorldcup2014.getAdvance()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16A1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16A2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16B1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16B2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16C1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16C2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16D1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16D2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16E1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16E2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16F1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16F2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16G1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16G2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16H1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTeam16H2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal3()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal4()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal5()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal6()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal7()  %> </td>
	<td> <%= o_SweepWorldcup2014.getQuarterFinal8()  %> </td>
	<td> <%= o_SweepWorldcup2014.getSemiFinal1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getSemiFinal2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getSemiFinal3()  %> </td>
	<td> <%= o_SweepWorldcup2014.getSemiFinal4()  %> </td>
	<td> <%= o_SweepWorldcup2014.getFinal1()  %> </td>
	<td> <%= o_SweepWorldcup2014.getFinal2()  %> </td>
	<td> <%= o_SweepWorldcup2014.getChampion()  %> </td>
	<td> <%= o_SweepWorldcup2014.getFinalScoreWin()  %> </td>
	<td> <%= o_SweepWorldcup2014.getFinalScoreLose()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTimeCreated()  %> </td>
	<td> <%= o_SweepWorldcup2014.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_sweep_worldcup2014_form('<%=o_SweepWorldcup2014.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/sweepWorldcup2014Action.html?del=true&id=<%=o_SweepWorldcup2014.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_SweepWorldcup2014.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_sweep_worldcup2014_form('<%=o_SweepWorldcup2014.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_sweep_worldcup2014_form(target){
		location.href='/v_sweep_worldcup2014_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_sweep_worldcup2014_form(target){
		javascript:sendFormAjaxSimple('/sweepWorldcup2014Action.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/sweepWorldcup2014Action.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_back_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_ext_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=ext<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
</script>
