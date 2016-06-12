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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="sweepWorldcupForm_topArea" class="formTopArea"></div>
<div id="sweepWorldcupForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="sweepWorldcupForm" method="get" action="/sweepWorldcupAction.html" >






	<div id="sweepWorldcupForm_teamCode_field">
    <div id="sweepWorldcupForm_teamCode_label" class="formLabel" >Team Code </div>
    <div id="sweepWorldcupForm_teamCode_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="teamCode" value="<%=WebUtil.display(_team_codeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_teamName_field">
    <div id="sweepWorldcupForm_teamName_label" class="formLabel" >Team Name </div>
    <div id="sweepWorldcupForm_teamName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="teamName" value="<%=WebUtil.display(_team_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_groupNum_field">
    <div id="sweepWorldcupForm_groupNum_label" class="formLabel" >Group Num </div>
    <div id="sweepWorldcupForm_groupNum_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="groupNum" value="<%=WebUtil.display(_group_numValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepWorldcupForm_game1_field">
    <div id="sweepWorldcupForm_game1_label" class="formLabel" >Game 1 </div>
    <div id="sweepWorldcupForm_game1_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game1">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_1Value)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game1Score_field">
    <div id="sweepWorldcupForm_game1Score_label" class="formLabel" >Game 1 Score </div>
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
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game1ScoreOpp_field">
    <div id="sweepWorldcupForm_game1ScoreOpp_label" class="formLabel" >Game 1 Score Opp </div>
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
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game2_field">
    <div id="sweepWorldcupForm_game2_label" class="formLabel" >Game 2 </div>
    <div id="sweepWorldcupForm_game2_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game2">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_2Value)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game2Score_field">
    <div id="sweepWorldcupForm_game2Score_label" class="formLabel" >Game 2 Score </div>
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
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game2ScoreOpp_field">
    <div id="sweepWorldcupForm_game2ScoreOpp_label" class="formLabel" >Game 2 Score Opp </div>
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
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game3_field">
    <div id="sweepWorldcupForm_game3_label" class="formLabel" >Game 3 </div>
    <div id="sweepWorldcupForm_game3_dropdown" class="formFieldDropDown" >       
        <select id="field" name="game3">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _game_3Value)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game3Score_field">
    <div id="sweepWorldcupForm_game3Score_label" class="formLabel" >Game 3 Score </div>
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
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_game3ScoreOpp_field">
    <div id="sweepWorldcupForm_game3ScoreOpp_label" class="formLabel" >Game 3 Score Opp </div>
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
	</div><div class="clear"></div>





	<div id="sweepWorldcupForm_quarterFinalTeams_field">
    <div id="sweepWorldcupForm_quarterFinalTeams_label" class="formLabel" >Quarter Final Teams </div>
    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="quarterFinalTeams" value="<%=WebUtil.display(_quarter_final_teamsValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_semiFinalTeams_field">
    <div id="sweepWorldcupForm_semiFinalTeams_label" class="formLabel" >Semi Final Teams </div>
    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="semiFinalTeams" value="<%=WebUtil.display(_semi_final_teamsValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_finalTeams_field">
    <div id="sweepWorldcupForm_finalTeams_label" class="formLabel" >Final Teams </div>
    <div id="sweepWorldcupForm_finalTeams_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="finalTeams" value="<%=WebUtil.display(_final_teamsValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepWorldcupForm_champion_field">
    <div id="sweepWorldcupForm_champion_label" class="formLabel" >Champion </div>
    <div id="sweepWorldcupForm_champion_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="champion" value="<%=WebUtil.display(_championValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="sweepWorldcupForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.sweepWorldcupForm.submit();">Cancel</a>
        </div>      

        <div id="sweepWorldcupForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepWorldcupForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="sweepWorldcupForm_bottomArea" class="formBottomArea"></div>


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
<form name="sweepWorldcupForm<%=_SweepWorldcup.getId()%>" method="get" action="/v_sweep_worldcup_edit.html" >
    <a href="javascript:document.sweepWorldcupForm<%=_SweepWorldcup.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepWorldcup.getId() %>">
</form>
<form name="sweepWorldcupForm<%=_SweepWorldcup.getId()%>2" method="get" action="/v_sweep_worldcup_edit2.html" >
    <a href="javascript:document.sweepWorldcupForm<%=_SweepWorldcup.getId()%>2.submit();">Edit2</a>           
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