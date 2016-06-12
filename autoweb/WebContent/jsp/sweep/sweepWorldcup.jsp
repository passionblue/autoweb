<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepWorldcupDS ds = SweepWorldcupDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_sweep_worldcup_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_sweep_worldcup_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_worldcup_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_worldcup_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

	List pageList = new ArrayList();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
		pageList.add(list.get(i));
	}
	list = pageList;
%>
	<%= prevLinkStr %>
<%
	for(int p = 0 ; p< pageIndexShortcut.length; p++){
%>
	<%=pageIndexShortcut[p] %> /
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_worldcup_add2.html?prv_returnPage=sweep_worldcup_home"> Add New 2</a>
            <a href="t_sweep_worldcup_bet.html?prv_returnPage=sweep_worldcup_home"> Bet</a>
            <a href="t_select_team.html"> Select Team</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="sweepWorldcupForm_teamCode_label" style="font-size: normal normal bold 10px verdana;" >Team Code </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_teamName_label" style="font-size: normal normal bold 10px verdana;" >Team Name </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_groupNum_label" style="font-size: normal normal bold 10px verdana;" >Group Num </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game1_label" style="font-size: normal normal bold 10px verdana;" >Game 1 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game1Score_label" style="font-size: normal normal bold 10px verdana;" >Game 1 Score </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game1ScoreOpp_label" style="font-size: normal normal bold 10px verdana;" >Game 1 Score Opp </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game2_label" style="font-size: normal normal bold 10px verdana;" >Game 2 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game2Score_label" style="font-size: normal normal bold 10px verdana;" >Game 2 Score </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game2ScoreOpp_label" style="font-size: normal normal bold 10px verdana;" >Game 2 Score Opp </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game3_label" style="font-size: normal normal bold 10px verdana;" >Game 3 </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game3Score_label" style="font-size: normal normal bold 10px verdana;" >Game 3 Score </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_game3ScoreOpp_label" style="font-size: normal normal bold 10px verdana;" >Game 3 Score Opp </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_quarterFinalTeams_label" style="font-size: normal normal bold 10px verdana;" >Quarter Final Teams </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_semiFinalTeams_label" style="font-size: normal normal bold 10px verdana;" >Semi Final Teams </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_finalTeams_label" style="font-size: normal normal bold 10px verdana;" >Final Teams </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_champion_label" style="font-size: normal normal bold 10px verdana;" >Champion </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepWorldcup _SweepWorldcup = (SweepWorldcup) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepWorldcup.getId() %> </td>



    <td> 
	<form name="sweepWorldcupFormEditField_TeamCode_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_teamCode_field">
	    <div id="sweepWorldcupForm_teamCode_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="teamCode" value="<%=WebUtil.display(_SweepWorldcup.getTeamCode())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_TeamCode_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_TeamName_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_teamName_field">
	    <div id="sweepWorldcupForm_teamName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="teamName" value="<%=WebUtil.display(_SweepWorldcup.getTeamName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_TeamName_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_GroupNum_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_groupNum_field">
	    <div id="sweepWorldcupForm_groupNum_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="groupNum" value="<%=WebUtil.display(_SweepWorldcup.getGroupNum())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_GroupNum_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game1_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game1_field">
	    <div id="sweepWorldcupForm_game1_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game1">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame1())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game1_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game1Score_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game1Score_field">
	    <div id="sweepWorldcupForm_game1Score_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game1Score">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame1Score())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game1Score_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game1ScoreOpp_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game1ScoreOpp_field">
	    <div id="sweepWorldcupForm_game1ScoreOpp_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game1ScoreOpp">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame1ScoreOpp())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game1ScoreOpp_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game2_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game2_field">
	    <div id="sweepWorldcupForm_game2_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game2">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame2())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game2_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game2Score_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game2Score_field">
	    <div id="sweepWorldcupForm_game2Score_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game2Score">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame2Score())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game2Score_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game2ScoreOpp_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game2ScoreOpp_field">
	    <div id="sweepWorldcupForm_game2ScoreOpp_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game2ScoreOpp">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame2ScoreOpp())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game2ScoreOpp_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game3_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game3_field">
	    <div id="sweepWorldcupForm_game3_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game3">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame3())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game3_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game3Score_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game3Score_field">
	    <div id="sweepWorldcupForm_game3Score_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game3Score">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame3Score())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game3Score_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Game3ScoreOpp_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >

		<div id="sweepWorldcupForm_game3ScoreOpp_field">
	    <div id="sweepWorldcupForm_game3ScoreOpp_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="game3ScoreOpp">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _SweepWorldcup.getGame3ScoreOpp())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Game3ScoreOpp_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_QuarterFinalTeams_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_quarterFinalTeams_field">
	    <div id="sweepWorldcupForm_quarterFinalTeams_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="quarterFinalTeams" value="<%=WebUtil.display(_SweepWorldcup.getQuarterFinalTeams())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_QuarterFinalTeams_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_SemiFinalTeams_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_semiFinalTeams_field">
	    <div id="sweepWorldcupForm_semiFinalTeams_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="semiFinalTeams" value="<%=WebUtil.display(_SweepWorldcup.getSemiFinalTeams())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_SemiFinalTeams_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_FinalTeams_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_finalTeams_field">
	    <div id="sweepWorldcupForm_finalTeams_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="finalTeams" value="<%=WebUtil.display(_SweepWorldcup.getFinalTeams())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_FinalTeams_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepWorldcupFormEditField_Champion_<%=_SweepWorldcup.getId()%>" method="get" action="/sweepWorldcupAction.html" >


		<div id="sweepWorldcupForm_champion_field">
	    <div id="sweepWorldcupForm_champion_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="champion" value="<%=WebUtil.display(_SweepWorldcup.getChampion())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepWorldcupFormEditField_Champion_<%=_SweepWorldcup.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepWorldcup.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup_home">
	</form>
    
    
    </td>



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