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

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_worldcup_add2.html?prv_returnPage=sweep_worldcup_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>

    <td> 
	    <div id="sweepWorldcupForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
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
    <td> 
	    <div id="sweepWorldcupForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
    <td> 
	    <div id="sweepWorldcupForm_timeUpdated_label" style="font-size: normal normal bold 10px verdana;" >Time Updated </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepWorldcup _SweepWorldcup = (SweepWorldcup) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _SweepWorldcup.getId() %> </td>
    
	<td> <%= _SweepWorldcup.getUserId()  %> </td>
	<td> <%= _SweepWorldcup.getTeamCode()  %> </td>
	<td> <%= _SweepWorldcup.getTeamName()  %> </td>
	<td> <%= _SweepWorldcup.getGroupNum()  %> </td>
	<td> <%= _SweepWorldcup.getGame1()  %> </td>
	<td> <%= _SweepWorldcup.getGame1Score()  %> </td>
	<td> <%= _SweepWorldcup.getGame1ScoreOpp()  %> </td>
	<td> <%= _SweepWorldcup.getGame2()  %> </td>
	<td> <%= _SweepWorldcup.getGame2Score()  %> </td>
	<td> <%= _SweepWorldcup.getGame2ScoreOpp()  %> </td>
	<td> <%= _SweepWorldcup.getGame3()  %> </td>
	<td> <%= _SweepWorldcup.getGame3Score()  %> </td>
	<td> <%= _SweepWorldcup.getGame3ScoreOpp()  %> </td>
	<td> <%= _SweepWorldcup.getQuarterFinalTeams()  %> </td>
	<td> <%= _SweepWorldcup.getSemiFinalTeams()  %> </td>
	<td> <%= _SweepWorldcup.getFinalTeams()  %> </td>
	<td> <%= _SweepWorldcup.getChampion()  %> </td>
	<td> <%= _SweepWorldcup.getTimeCreated()  %> </td>
	<td> <%= _SweepWorldcup.getTimeUpdated()  %> </td>
</TR>

<%
    }
%>
</TABLE>