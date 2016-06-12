<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    SweepWorldcup _SweepWorldcup = SweepWorldcupDS.getInstance().getById(id);

    if ( _SweepWorldcup == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

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
<div id="sweepWorldcupForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepWorldcupFormEdit" method="get" action="/sweepWorldcupAction.html" >




	<div id="sweepWorldcupForm_teamCode_field">
    <div id="sweepWorldcupForm_teamCode_label" class="formLabel" >Team Code </div>
    <div id="sweepWorldcupForm_teamCode_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="teamCode" value="<%=WebUtil.display(_team_codeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepWorldcupForm_teamName_field">
    <div id="sweepWorldcupForm_teamName_label" class="formLabel" >Team Name </div>
    <div id="sweepWorldcupForm_teamName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="teamName" value="<%=WebUtil.display(_team_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepWorldcupForm_groupNum_field">
    <div id="sweepWorldcupForm_groupNum_label" class="formLabel" >Group Num </div>
    <div id="sweepWorldcupForm_groupNum_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="groupNum" value="<%=WebUtil.display(_group_numValue)%>"/> <span></span>
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
    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="quarterFinalTeams" value="<%=WebUtil.display(_quarter_final_teamsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepWorldcupForm_semiFinalTeams_field">
    <div id="sweepWorldcupForm_semiFinalTeams_label" class="formLabel" >Semi Final Teams </div>
    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="semiFinalTeams" value="<%=WebUtil.display(_semi_final_teamsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepWorldcupForm_finalTeams_field">
    <div id="sweepWorldcupForm_finalTeams_label" class="formLabel" >Final Teams </div>
    <div id="sweepWorldcupForm_finalTeams_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="finalTeams" value="<%=WebUtil.display(_final_teamsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepWorldcupForm_champion_field">
    <div id="sweepWorldcupForm_champion_label" class="formLabel" >Champion </div>
    <div id="sweepWorldcupForm_champion_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="champion" value="<%=WebUtil.display(_championValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="sweepWorldcupFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
