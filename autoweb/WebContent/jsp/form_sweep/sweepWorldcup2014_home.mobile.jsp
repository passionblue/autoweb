<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	SweepWorldcup2014DS ds = SweepWorldcup2014DS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->

<h3> form displayed by script (request type getscriptform or getmodalform2 </h3>
<script type="text/javascript" src="/sweepWorldcup2014Action.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_sweep_worldcup2014_form.html?prv_returnPage=sweep_worldcup2014_home"> Add New </a> |
            <a href="v_sweep_worldcup2014_list.html?"> List Page </a> |
            <a href="v_sweep_worldcup2014_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/sweepWorldcup2014Action.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/sweepWorldcup2014Action.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/sweepWorldcup2014Action.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/sweepWorldcup2014Action.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/sweepWorldcup2014Action.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/sweepWorldcup2014Action.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/sweepWorldcup2014Action.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

        	<a href="javascript:submit_cmd_verify();">run cmd verify</a>|
		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="sweepWorldcup2014Form_player_label" style="font-size: normal normal bold 10px verdana;" >Player </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game1_label" style="font-size: normal normal bold 10px verdana;" >Game 1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game1Score_label" style="font-size: normal normal bold 10px verdana;" >Game 1 Score </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game1ScoreOpp_label" style="font-size: normal normal bold 10px verdana;" >Game 1 Score Opp </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game2_label" style="font-size: normal normal bold 10px verdana;" >Game 2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game2Score_label" style="font-size: normal normal bold 10px verdana;" >Game 2 Score </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game2ScoreOpp_label" style="font-size: normal normal bold 10px verdana;" >Game 2 Score Opp </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game3_label" style="font-size: normal normal bold 10px verdana;" >Game 3 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game3Score_label" style="font-size: normal normal bold 10px verdana;" >Game 3 Score </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_game3ScoreOpp_label" style="font-size: normal normal bold 10px verdana;" >Game 3 Score Opp </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_advance_label" style="font-size: normal normal bold 10px verdana;" >Advance </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16A1_label" style="font-size: normal normal bold 10px verdana;" >Team16 A1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16A2_label" style="font-size: normal normal bold 10px verdana;" >Team16 A2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16B1_label" style="font-size: normal normal bold 10px verdana;" >Team16 B1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16B2_label" style="font-size: normal normal bold 10px verdana;" >Team16 B2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16C1_label" style="font-size: normal normal bold 10px verdana;" >Team16 C1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16C2_label" style="font-size: normal normal bold 10px verdana;" >Team16 C2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16D1_label" style="font-size: normal normal bold 10px verdana;" >Team16 D1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16D2_label" style="font-size: normal normal bold 10px verdana;" >Team16 D2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16E1_label" style="font-size: normal normal bold 10px verdana;" >Team16 E1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16E2_label" style="font-size: normal normal bold 10px verdana;" >Team16 E2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16F1_label" style="font-size: normal normal bold 10px verdana;" >Team16 F1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16F2_label" style="font-size: normal normal bold 10px verdana;" >Team16 F2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16G1_label" style="font-size: normal normal bold 10px verdana;" >Team16 G1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16G2_label" style="font-size: normal normal bold 10px verdana;" >Team16 G2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16H1_label" style="font-size: normal normal bold 10px verdana;" >Team16 H1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_team16H2_label" style="font-size: normal normal bold 10px verdana;" >Team16 H2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal1_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal2_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal3_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 3 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal4_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 4 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal5_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 5 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal6_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 6 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal7_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 7 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_quarterFinal8_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final 8 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_semiFinal1_label" style="font-size: normal normal bold 10px verdana;" >Semi Final 1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_semiFinal2_label" style="font-size: normal normal bold 10px verdana;" >Semi Final 2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_semiFinal3_label" style="font-size: normal normal bold 10px verdana;" >Semi Final 3 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_semiFinal4_label" style="font-size: normal normal bold 10px verdana;" >Semi Final 4 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_final1_label" style="font-size: normal normal bold 10px verdana;" >Final 1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_final2_label" style="font-size: normal normal bold 10px verdana;" >Final 2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_champion_label" style="font-size: normal normal bold 10px verdana;" >Champion </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_finalScoreWin_label" style="font-size: normal normal bold 10px verdana;" >Final Score Win </div>
    </td> 
    <td> 
	    <div id="sweepWorldcup2014Form_finalScoreLose_label" style="font-size: normal normal bold 10px verdana;" >Final Score Lose </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) iter.next();
		//TODO 
        fieldString += "\"" +  _SweepWorldcup2014.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepWorldcup2014.getId() %> </td>


    <td> 
	<form name="sweepWorldcup2014FormEditField_Player_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_player_field">
	    <div id="sweepWorldcup2014Form_player_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="player" value="<%=WebUtil.display(_SweepWorldcup2014.getPlayer())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="player_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getPlayer() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Player_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'player', '<%=_SweepWorldcup2014.getPlayer()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="player">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="player">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="player">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game1_field">
	    <div id="sweepWorldcup2014Form_game1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game1" value="<%=WebUtil.display(_SweepWorldcup2014.getGame1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game1', '<%=_SweepWorldcup2014.getGame1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game1Score_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game1Score_field">
	    <div id="sweepWorldcup2014Form_game1Score_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game1Score" value="<%=WebUtil.display(_SweepWorldcup2014.getGame1Score())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game1Score_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame1Score() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game1Score_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game1Score', '<%=_SweepWorldcup2014.getGame1Score()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game1Score">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game1Score">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game1Score">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game1ScoreOpp_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game1ScoreOpp_field">
	    <div id="sweepWorldcup2014Form_game1ScoreOpp_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game1ScoreOpp" value="<%=WebUtil.display(_SweepWorldcup2014.getGame1ScoreOpp())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game1ScoreOpp_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame1ScoreOpp() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game1ScoreOpp_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game1ScoreOpp', '<%=_SweepWorldcup2014.getGame1ScoreOpp()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game1ScoreOpp">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game1ScoreOpp">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game1ScoreOpp">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game2_field">
	    <div id="sweepWorldcup2014Form_game2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game2" value="<%=WebUtil.display(_SweepWorldcup2014.getGame2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game2', '<%=_SweepWorldcup2014.getGame2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game2Score_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game2Score_field">
	    <div id="sweepWorldcup2014Form_game2Score_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game2Score" value="<%=WebUtil.display(_SweepWorldcup2014.getGame2Score())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game2Score_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame2Score() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game2Score_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game2Score', '<%=_SweepWorldcup2014.getGame2Score()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game2Score">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game2Score">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game2Score">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game2ScoreOpp_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game2ScoreOpp_field">
	    <div id="sweepWorldcup2014Form_game2ScoreOpp_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game2ScoreOpp" value="<%=WebUtil.display(_SweepWorldcup2014.getGame2ScoreOpp())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game2ScoreOpp_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame2ScoreOpp() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game2ScoreOpp_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game2ScoreOpp', '<%=_SweepWorldcup2014.getGame2ScoreOpp()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game2ScoreOpp">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game2ScoreOpp">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game2ScoreOpp">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game3_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game3_field">
	    <div id="sweepWorldcup2014Form_game3_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game3" value="<%=WebUtil.display(_SweepWorldcup2014.getGame3())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game3_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame3() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game3_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game3', '<%=_SweepWorldcup2014.getGame3()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game3">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game3">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game3">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game3Score_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game3Score_field">
	    <div id="sweepWorldcup2014Form_game3Score_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game3Score" value="<%=WebUtil.display(_SweepWorldcup2014.getGame3Score())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game3Score_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame3Score() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game3Score_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game3Score', '<%=_SweepWorldcup2014.getGame3Score()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game3Score">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game3Score">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game3Score">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Game3ScoreOpp_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_game3ScoreOpp_field">
	    <div id="sweepWorldcup2014Form_game3ScoreOpp_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="game3ScoreOpp" value="<%=WebUtil.display(_SweepWorldcup2014.getGame3ScoreOpp())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="game3ScoreOpp_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getGame3ScoreOpp() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Game3ScoreOpp_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'game3ScoreOpp', '<%=_SweepWorldcup2014.getGame3ScoreOpp()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="game3ScoreOpp">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="game3ScoreOpp">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="game3ScoreOpp">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Advance_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_advance_field">
	    <div id="sweepWorldcup2014Form_advance_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="advance" value="<%=WebUtil.display(_SweepWorldcup2014.getAdvance())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="advance_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getAdvance() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Advance_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'advance', '<%=_SweepWorldcup2014.getAdvance()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="advance">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="advance">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="advance">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16A1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16A1_field">
	    <div id="sweepWorldcup2014Form_team16A1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16A1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16A1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16A1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16A1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16A1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16A1', '<%=_SweepWorldcup2014.getTeam16A1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16A1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16A1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16A1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16A2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16A2_field">
	    <div id="sweepWorldcup2014Form_team16A2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16A2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16A2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16A2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16A2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16A2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16A2', '<%=_SweepWorldcup2014.getTeam16A2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16A2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16A2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16A2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16B1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16B1_field">
	    <div id="sweepWorldcup2014Form_team16B1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16B1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16B1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16B1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16B1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16B1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16B1', '<%=_SweepWorldcup2014.getTeam16B1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16B1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16B1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16B1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16B2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16B2_field">
	    <div id="sweepWorldcup2014Form_team16B2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16B2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16B2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16B2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16B2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16B2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16B2', '<%=_SweepWorldcup2014.getTeam16B2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16B2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16B2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16B2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16C1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16C1_field">
	    <div id="sweepWorldcup2014Form_team16C1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16C1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16C1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16C1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16C1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16C1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16C1', '<%=_SweepWorldcup2014.getTeam16C1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16C1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16C1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16C1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16C2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16C2_field">
	    <div id="sweepWorldcup2014Form_team16C2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16C2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16C2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16C2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16C2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16C2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16C2', '<%=_SweepWorldcup2014.getTeam16C2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16C2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16C2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16C2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16D1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16D1_field">
	    <div id="sweepWorldcup2014Form_team16D1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16D1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16D1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16D1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16D1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16D1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16D1', '<%=_SweepWorldcup2014.getTeam16D1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16D1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16D1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16D1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16D2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16D2_field">
	    <div id="sweepWorldcup2014Form_team16D2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16D2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16D2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16D2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16D2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16D2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16D2', '<%=_SweepWorldcup2014.getTeam16D2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16D2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16D2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16D2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16E1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16E1_field">
	    <div id="sweepWorldcup2014Form_team16E1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16E1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16E1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16E1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16E1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16E1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16E1', '<%=_SweepWorldcup2014.getTeam16E1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16E1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16E1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16E1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16E2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16E2_field">
	    <div id="sweepWorldcup2014Form_team16E2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16E2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16E2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16E2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16E2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16E2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16E2', '<%=_SweepWorldcup2014.getTeam16E2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16E2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16E2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16E2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16F1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16F1_field">
	    <div id="sweepWorldcup2014Form_team16F1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16F1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16F1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16F1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16F1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16F1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16F1', '<%=_SweepWorldcup2014.getTeam16F1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16F1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16F1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16F1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16F2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16F2_field">
	    <div id="sweepWorldcup2014Form_team16F2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16F2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16F2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16F2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16F2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16F2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16F2', '<%=_SweepWorldcup2014.getTeam16F2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16F2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16F2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16F2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16G1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16G1_field">
	    <div id="sweepWorldcup2014Form_team16G1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16G1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16G1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16G1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16G1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16G1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16G1', '<%=_SweepWorldcup2014.getTeam16G1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16G1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16G1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16G1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16G2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16G2_field">
	    <div id="sweepWorldcup2014Form_team16G2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16G2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16G2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16G2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16G2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16G2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16G2', '<%=_SweepWorldcup2014.getTeam16G2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16G2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16G2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16G2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16H1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16H1_field">
	    <div id="sweepWorldcup2014Form_team16H1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16H1" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16H1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16H1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16H1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16H1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16H1', '<%=_SweepWorldcup2014.getTeam16H1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16H1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16H1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16H1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Team16H2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_team16H2_field">
	    <div id="sweepWorldcup2014Form_team16H2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="team16H2" value="<%=WebUtil.display(_SweepWorldcup2014.getTeam16H2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="team16H2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getTeam16H2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Team16H2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'team16H2', '<%=_SweepWorldcup2014.getTeam16H2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="team16H2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="team16H2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="team16H2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal1_field">
	    <div id="sweepWorldcup2014Form_quarterFinal1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal1" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal1', '<%=_SweepWorldcup2014.getQuarterFinal1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal2_field">
	    <div id="sweepWorldcup2014Form_quarterFinal2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal2" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal2', '<%=_SweepWorldcup2014.getQuarterFinal2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal3_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal3_field">
	    <div id="sweepWorldcup2014Form_quarterFinal3_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal3" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal3())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal3_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal3() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal3_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal3', '<%=_SweepWorldcup2014.getQuarterFinal3()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal3">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal3">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal3">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal4_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal4_field">
	    <div id="sweepWorldcup2014Form_quarterFinal4_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal4" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal4())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal4_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal4() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal4_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal4', '<%=_SweepWorldcup2014.getQuarterFinal4()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal4">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal4">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal4">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal5_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal5_field">
	    <div id="sweepWorldcup2014Form_quarterFinal5_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal5" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal5())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal5_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal5() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal5_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal5', '<%=_SweepWorldcup2014.getQuarterFinal5()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal5">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal5">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal5">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal6_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal6_field">
	    <div id="sweepWorldcup2014Form_quarterFinal6_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal6" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal6())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal6_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal6() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal6_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal6', '<%=_SweepWorldcup2014.getQuarterFinal6()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal6">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal6">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal6">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal7_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal7_field">
	    <div id="sweepWorldcup2014Form_quarterFinal7_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal7" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal7())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal7_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal7() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal7_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal7', '<%=_SweepWorldcup2014.getQuarterFinal7()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal7">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal7">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal7">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_QuarterFinal8_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_quarterFinal8_field">
	    <div id="sweepWorldcup2014Form_quarterFinal8_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinal8" value="<%=WebUtil.display(_SweepWorldcup2014.getQuarterFinal8())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="quarterFinal8_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getQuarterFinal8() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_QuarterFinal8_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'quarterFinal8', '<%=_SweepWorldcup2014.getQuarterFinal8()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="quarterFinal8">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="quarterFinal8">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="quarterFinal8">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_SemiFinal1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_semiFinal1_field">
	    <div id="sweepWorldcup2014Form_semiFinal1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="semiFinal1" value="<%=WebUtil.display(_SweepWorldcup2014.getSemiFinal1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="semiFinal1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getSemiFinal1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_SemiFinal1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'semiFinal1', '<%=_SweepWorldcup2014.getSemiFinal1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="semiFinal1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="semiFinal1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="semiFinal1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_SemiFinal2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_semiFinal2_field">
	    <div id="sweepWorldcup2014Form_semiFinal2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="semiFinal2" value="<%=WebUtil.display(_SweepWorldcup2014.getSemiFinal2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="semiFinal2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getSemiFinal2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_SemiFinal2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'semiFinal2', '<%=_SweepWorldcup2014.getSemiFinal2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="semiFinal2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="semiFinal2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="semiFinal2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_SemiFinal3_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_semiFinal3_field">
	    <div id="sweepWorldcup2014Form_semiFinal3_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="semiFinal3" value="<%=WebUtil.display(_SweepWorldcup2014.getSemiFinal3())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="semiFinal3_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getSemiFinal3() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_SemiFinal3_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'semiFinal3', '<%=_SweepWorldcup2014.getSemiFinal3()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="semiFinal3">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="semiFinal3">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="semiFinal3">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_SemiFinal4_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_semiFinal4_field">
	    <div id="sweepWorldcup2014Form_semiFinal4_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="semiFinal4" value="<%=WebUtil.display(_SweepWorldcup2014.getSemiFinal4())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="semiFinal4_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getSemiFinal4() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_SemiFinal4_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'semiFinal4', '<%=_SweepWorldcup2014.getSemiFinal4()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="semiFinal4">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="semiFinal4">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="semiFinal4">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Final1_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_final1_field">
	    <div id="sweepWorldcup2014Form_final1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="final1" value="<%=WebUtil.display(_SweepWorldcup2014.getFinal1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="final1_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getFinal1() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Final1_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'final1', '<%=_SweepWorldcup2014.getFinal1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="final1">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="final1">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="final1">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Final2_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_final2_field">
	    <div id="sweepWorldcup2014Form_final2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="final2" value="<%=WebUtil.display(_SweepWorldcup2014.getFinal2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="final2_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getFinal2() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Final2_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'final2', '<%=_SweepWorldcup2014.getFinal2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="final2">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="final2">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="final2">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_Champion_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_champion_field">
	    <div id="sweepWorldcup2014Form_champion_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="champion" value="<%=WebUtil.display(_SweepWorldcup2014.getChampion())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="champion_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getChampion() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_Champion_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'champion', '<%=_SweepWorldcup2014.getChampion()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="champion">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="champion">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="champion">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_FinalScoreWin_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_finalScoreWin_field">
	    <div id="sweepWorldcup2014Form_finalScoreWin_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="finalScoreWin" value="<%=WebUtil.display(_SweepWorldcup2014.getFinalScoreWin())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="finalScoreWin_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getFinalScoreWin() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_FinalScoreWin_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'finalScoreWin', '<%=_SweepWorldcup2014.getFinalScoreWin()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="finalScoreWin">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="finalScoreWin">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="finalScoreWin">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcup2014FormEditField_FinalScoreLose_<%=_SweepWorldcup2014.getId()%>" method="get" action="/sweepWorldcup2014Action.html" >


		<div id="sweepWorldcup2014Form_finalScoreLose_field">
	    <div id="sweepWorldcup2014Form_finalScoreLose_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="finalScoreLose" value="<%=WebUtil.display(_SweepWorldcup2014.getFinalScoreLose())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="finalScoreLose_<%= _SweepWorldcup2014.getId()%>"><%=_SweepWorldcup2014.getFinalScoreLose() %></div>
            <a id="formSubmit" href="javascript:document.sweepWorldcup2014FormEditField_FinalScoreLose_<%=_SweepWorldcup2014.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_SweepWorldcup2014.getId()%>', 'finalScoreLose', '<%=_SweepWorldcup2014.getFinalScoreLose()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="finalScoreLose">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="finalScoreLose">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="finalScoreLose">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup2014.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_home">
	</form>
    
    
    </td>



    <td>
    <form name="sweepWorldcup2014Form<%=_SweepWorldcup2014.getId()%>2" method="get" action="/v_sweep_worldcup2014_form.html" >
        <a href="javascript:document.sweepWorldcup2014Form<%=_SweepWorldcup2014.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepWorldcup2014.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="sweep_worldcup2014_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_sweep_worldcup2014_form('<%=_SweepWorldcup2014.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/sweepWorldcup2014Action.html?del=true&id=<%=_SweepWorldcup2014.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_sweep_worldcup2014('<%=_SweepWorldcup2014.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<a id="partition_test_ajax" href="#" rel="extInt">	Partition Test </a><br>
<a id="partition_test_ajax2" href="#" rel="extInt">	Partition Test2 </a><br>

<div id="partitionTestResult" style="border:1px solid #666666; "> Partition test to be loaded here </div> <br>


<script type="text/javascript">

	function submit_cmd_verify(){
		location.href='/sweepWorldcup2014Action.html?verify=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}



	function edit_sweep_worldcup2014_form(target){
		location.href='/v_sweep_worldcup2014_form.html?cmd=edit&prv_returnPage=sweep_worldcup2014_home&id=' + target;
	}

	function confirm_sweep_worldcup2014(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_sweep_worldcup2014(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/sweepWorldcup2014Action.html?del=true&id="+target;
				}
			}
		});
	}
	// 20120226 
	// On the list, added a little stupid fuction to prompt the change of values
	function update_cleaner_pickup_field_dialog(targetId, targetField, targetValue){
		var txt = 'Change field value for'+targetField +':<br/> <input type="text" id="alertName" name="myname" value="'+ targetValue +'" />';
		$ .prompt(txt,{ 
			buttons:{Submit:true, Cancel:false},
			callback: function(v,m,f){
				if (v){
					if (f.myname == "") {
						alert("Enter");
						return false;
					} else {
						location.href="/sweepWorldcup2014Action.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
					}
				}
				return true;
			}
		});
	}

	// Functions to update field in the list via ajax
	// This is primitive but field "update_field_by_ajax" should be right next level of form.
	// Because it uses parent to access to id and field name 20120226
	$(document).ready(function(){

		$("a#update_field_by_ajax").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			$ .ajax({
			   type: "GET",
		   		url: "/sweepWorldcup2014Action.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		     		$("#" + _fieldName+"_"+_id).text(msg);
		   		}
	 		});
			
			return false;
		});

		$("a#update_field_by_ajax_open_reply").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/sweepWorldcup2014Action.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg);
		    		$ .prompt("Value updated Success fully",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});
		
		$("a#update_field_by_ajax_get2field").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/sweepWorldcup2014Action.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg); // Update the field
		     		var displayMsg = "return from server-> " + msg + "<br>" + "result of getSuccessData()-> "+ getSuccessData(msg);
		    		$ .prompt(displayMsg,{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});


		$("a#partition_test_ajax").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-sweep-worldcup2014",
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").html(msg);
		   		}
	 		});
			
			return false;
		});		

		// Display loader 
		$("a#partition_test_ajax2").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-sweep-worldcup2014",
		   		beforeSend: function(jqXHR, settings){
		   			
					// 1 just display loader img on the target div 		   			
		   			// $("#partitionTestResult").html("<img src=\"/images/loader/arrows32.gif\"/>");

					
					//2 
					$("#partitionTestResult").css("height","100px").html("<img src=\"/images/loader/arrows32.gif\"/>");
					
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").remove("height").html(msg);
		   		}
	 		});
			return false;
 		});
	});
</script>


<script type="text/javascript" charset="utf-8">
// This Javascript is granted to the public domain.

// This is the javascript array holding the function list
// The PrintJavascriptArray ASP function can be used to print this array.
//var functionlist = Array("abs",
//"acos","acosh","addcslashes","addslashes","aggregate","stream_context_create",
//"swf_startbutton","swfmovie.remove","swfmovie.save","swftext.getwidth","swftext.moveto","sybase_fetch_field","sybase_fetch_object","tanh","tempnam",
//"textdomain","time","udm_errno","udm_error",
//"unset","urldecode","urlencode","user_error","usleep","usort","utf8_decode",
//"utf8_encode","var_dump","vpopmail_error","vpopmail_passwd","vpopmail_set_user_quota","vprintf","vsprintf","xml_parser_create","xml_parser_create_ns",
//"xml_parser_free","xmlrpc_server_add_introspection_data","xmlrpc_server_call_method","xmlrpc_server_create","xmlrpc_server_destroy","xmlrpc_server_register_introspection_callback","yaz_connect","yaz_database","yaz_element",
//"yaz_errno","yp_order","zend_logo_guid","zend_version","zip_close","zip_open","zip_read");



var functionlist = Array(<%=fieldString%>);



// This is the function that refreshes the list after a keypress.
// The maximum number to show can be limited to improve performance with
// huge lists (1000s of entries).
// The function clears the list, and then does a linear search through the
// globally defined array and adds the matches back to the list.
function handleKeyUp(maxNumToShow)
{
    var selectObj, textObj, functionListLength;
    var i, searchPattern, numShown;

	if (document.getElementById('auto-complete-input') == null){
		alert("Client side Error occurred. Please try again.");
		return;
	}
    
    // Set references to the form elements
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    // Remember the function list length for loop speedup
    functionListLength = functionlist.length;

    // Set the search pattern depending
    if(document.getElementById('auto-complete-input').functionradio[0].checked == true)
    {
        searchPattern = "^"+textObj.value;
    }
    else
    {
        searchPattern = textObj.value;
    }

    // Create a regulare expression
    re = new RegExp(searchPattern,"gi");
    // Clear the options list
    selectObj.length = 0;

    // Loop through the array and re-add matching options
    numShown = 0;
    for(i = 0; i < functionListLength; i++)
    {
        if(functionlist[i].search(re) != -1)
        {
            selectObj[numShown] = new Option(functionlist[i],"");
            numShown++;
        }
        // Stop when the number to show is reached
        if(numShown == maxNumToShow)
        {
            break;
        }
    }
    // When options list whittled to one, select that entry
    if(selectObj.length == 1)
    {
        selectObj.options[0].selected = true;
    }
}

// this function gets the selected value and loads the appropriate
// php reference page in the display frame
// it can be modified to perform whatever action is needed, or nothing
function handleSelectClick()
{
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    selectedValue = selectObj.options[selectObj.selectedIndex].text;

    selectedValue = selectedValue.replace(/_/g, '-') ;
    document.location.href = 
	"http://www.php.net/manual/en/function."+selectedValue+".php";

}
function encode_utf8( string )
{
	string = string.replace(/\r\n/g,"\n");
	var utftext = "";

	for (var n = 0; n < string.length; n++) {

		var c = string.charCodeAt(n);

		if (c < 128) {
			utftext += String.fromCharCode(c);
		}
		else if((c > 127) && (c < 2048)) {
			utftext += String.fromCharCode((c >> 6) | 192);
			utftext += String.fromCharCode((c & 63) | 128);
		}
		else {
			utftext += String.fromCharCode((c >> 12) | 224);
			utftext += String.fromCharCode(((c >> 6) & 63) | 128);
			utftext += String.fromCharCode((c & 63) | 128);
		}

	}

	return utftext;
}

function decode_utf8( s )
{
  return decodeURIComponent( escape( s ) );
}
</script>

<table style="margin:auto;">
<tr>
	<td valign="top">
		<b>Search For Function Name</b>
		
		<form onSubmit="handleSelectClick();return false;" action="#" id='auto-complete-input'>

			<input type="radio" name="functionradio" checked>Starting With<br>
			<input type="radio" name="functionradio">Containing<br>
			<input  onKeyUp="handleKeyUp(20);" type="text" name="functioninput" VALUE="" style="font-size:10pt;width:34ex;"><br>
		
			<select onClick="handleSelectClick();" name="functionselect" size="20" style="font-size:10pt;width:34ex;">
			</select>
			<br>
			<input type="button" onClick="handleKeyUp(9999999);" value="Load All Matches">
		</form>
	</td>
</tr>

<tr>
	<td valign="top">
		<select>
		  <option>Volvo</option>
		  <option>Saab</option>
		  <option>Mercedes</option>
		  <option>Audi</option>
		</select>
	</td>
</tr>

</table>