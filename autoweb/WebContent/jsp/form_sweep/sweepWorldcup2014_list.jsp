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


<a href="t_sweep_worldcup2014_form.html?prv_returnPage=sweep_worldcup2014_home"> Add New 2</a> <br>
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
		   		url: "/sweepWorldcup2014Action.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

