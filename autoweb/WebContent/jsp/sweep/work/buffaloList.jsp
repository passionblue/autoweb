<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepWorldcup _SweepWorldcupDefault = new SweepWorldcup();// SweepWorldcupDS.getInstance().getDeafult();

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	
%>

<ul style="display:box; margin-left:30px;">

<li >This page is prepared for buffalo gae members to easily enter Worldcup Sweepstakes that is in progress until the opening day of the event.</li>
<li >Click on your name. Password is <b> buffalo </b> for all. </li>
<li >You don't have to complete the form now. You can save as much as you do and come back for a change or completion. </li>
<li >You don't have to fill the form to enter the sweepstakes. You can simply send me the filled form in email. </li>

</ul>

<table class="mytable1" >

<tr>
	<td class="columnTitle" > Name</td>
	<td class="columnTitle" > Bets</td>
</tr>

<%
	List users = AutositeUserDS.getInstance().getAll();		

	for(Iterator iter = users.iterator();iter.hasNext();) {

		AutositeUser user = (AutositeUser) iter.next();		
		
		if (user.getOpt1() == 0) continue;
		if (user.getPassword() == null || !user.getPassword().equals("buffalo")) continue;
		
		List sweeps = SweepWorldcupDS.getInstance().getByUserId(user.getId());

		
		SweepWorldcup sweep = null;
		if (sweeps.size() > 0 ) {
			sweep = (SweepWorldcup) sweeps.get(0);
		}
		
%>

<tr>
	<td class=columnBold><%=user.getNickname() %> </td> 
	<!-- td >&nbsp;<a href="/t_sweep_access_shortcut.html?prv_email=<%=user.getUsername() %>">Enter Sweepstake ( *by <b><%= user.getNickname() %></b> only*)</a></td--> 
	
<%		if (sweep != null) { %>	
	<td >&nbsp;<a href="/t_sweep_worldcup_view.html?id=<%= sweep.getId()%>&prv_returnPage=sweep_buffalo_list"> View His Bet</a></td> 
<%		} else { %>	
	<td >&nbsp;Not Entered Yet</td> 
<%		}%>	
	
</tr>

<% 	} %>

</table>

