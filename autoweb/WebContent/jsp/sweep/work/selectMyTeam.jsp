<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepWorldcup _SweepWorldcupDefault = new SweepWorldcup();// SweepWorldcupDS.getInstance().getDeafult();


	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	
	AutositeUser user = sessionContext.getUserObject();

	List sweeps = new ArrayList();
	if ( sessionContext.isLogin() && user != null){

		sweeps = SweepWorldcupDS.getInstance().getByUserId(user.getId());
	}

	List codeList = WorldcupUtil.getTeamList();
	
	SweepUserConfig sweepUserConfig = SweepUtil.getOrCreateSweepUserConfig(user);
	
	
%>


<div style="margin:30px 30px 30px 30px; padding: 0px 0px 0px 0px; border:0px solid blue">

<%
	if ( sweeps.size() >= sweepUserConfig.getMaxSweepAllowed()) {

%>
	<h4> You already entered the allowed number of Sweepstakes. To increase chance of winning, please see below. </h4>
<% 	} else { %>

<h4> Start New Sweepstakes, Select Your Team </h4> <h6>you can enter <%=  sweepUserConfig.getMaxSweepAllowed() - sweeps.size() %> more Sweepstakes ( See below to increase chance of winning.)</h6><br/>
<%
	String [] groupArray=new String[]{"A","B","C","D", "E", "F", "G", "F"};
	for(int i = 0; i<8;i++){
		String groupCode = groupArray[i];
%>
<div class="column" style="float:left;width: 80px; font-size: 10px;font-family: verdana">
	<h4> Group <%=groupCode %></h4>
<%
	List codes = WorldcupUtil.getGroupTeamList(groupCode);
	for(Iterator iter=codes.iterator();iter.hasNext();){
		String code = (String) iter.next();
		String country = WorldcupUtil.getTeamCountry(code);		
%>
<div style="margin-bottom:10px">
<a href="/v_sweep_worldcup_bet.html?prv_returnPage=sweep_worldcup_home&prv_teamCode=<%= code %>"> <img src="/images/worldcup/<%=code%>2.gif"/> </a>
<%= WorldcupUtil.getTeamCountry(code) %>
</div>
<% } %>
</div>
<% } %>

<% 	} %>
</div><div class="clear"></div>

<div style="margin:30px 30px 30px 30px; padding: 0px 0px 0px 0px; border:0px solid blue">
<h4> Sweepstakes That You Have Entered</h4>
<h6>You entered <%= sweeps.size() %>  Sweepstakes so far</h6><br/>

<table class="mytable1">
<TR>
	<TD class="columnTitle" >Team Name </TD>
	<TD class="columnTitle" >Created Time </TD>
</TR>
<%
	for(Iterator iter = sweeps.iterator();iter.hasNext();) {

		SweepWorldcup sweep = (SweepWorldcup) iter.next();
		
		

%>
<TR>
	<TD class="columnData" >
		<%= WorldcupUtil.getTeamCountry(sweep.getTeamCode()) %> - 
		<a href="/v_sweep_worldcup_edit.html?id=<%=sweep.getId()%>">edit </a> -  
		<a href="/sweepWorldcupAction.html?id=<%=sweep.getId()%>&returnPage=select_team&del=true">delete</a> 
	</TD>
	<TD class="columnData" >
		<%= sweep.getTimeCreated() %>
	</TD>
	
</TR>
<% 	} %>

</table>
</div><div class="clear"></div>

<%
	List invits = SweepInvitationDS.getInstance().getByUserId(user.getId());
%>

<div style="margin:30px 30px 30px 30px; padding: 0px 0px 0px 0px; border:0px solid blue">
	<h4>You can increase chance of winning by <a href="/v_tell_friend.html"> Telling Your Friend</a>.</h4>
	<h6>You have sent <%= invits.size() %> invitations. You can send up to <%=SweepUtil.SWEEP_WORLDCUP_MAX_INVITATION %> invitations. </h6>
</div><div class="clear"></div>


