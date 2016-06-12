<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.jtrend.stats.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");


	String filterIp = request.getParameter("filterIp");

	if (filterIp == null) filterIp="XXXX";
%> 

<jsp:include page="/jsp/admin/stats/statsMenu.jsp" />

<br/>
<h3> IP Stats </h3>
<a href="/v_admin_stats_ip.html?filterIp=ALL"> Expand All</a> | <a href="/v_admin_stats_ip.html?filterIp=NONE"> Collapse All</a> 
<table class="mytable1">
<%

	List rAddrs = StatsPool.getInstance().getRemoteAddrsInPool();	
	for(Iterator iter = rAddrs.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		
		List stats = StatsPool.getInstance().getRemoteAddrStats(key);	
		
		StatData statData = stats.size()==0?null:(StatData) stats.get(0);
		
%>
	<tr>
		<td class="columnBold">
			<a href="/v_admin_stats_ip.html?filterIp=<%= key %>"> <%= key %></a> 
		</td>
		<td colspan="10">
			<%= stats.size() %>
			[robot:<%= WebUtil.display(statData.getRobotBrand()) %>]

<% if(statData != null && WebUtil.isNotNull((String)statData.getHeaderAttributes().get("user-agent"))){ %>
		&nbsp;<a class="cttitle" href="#" title="User-Agent|<%= statData.getHeaderAttributes().get("user-agent") %>">[UA]</a>
<%	} %>

			| <%= statData.getRpcid() %>
		</td>
		
	</tr>
	
<%	
	//====================================================================================================================

	String prevSessionId = null;
	
	for(Iterator iter2 = stats.iterator(); iter2.hasNext();){
		StatData stat = (StatData) iter2.next();

		if (WebUtil.isNotNull(filterIp) && !stat.getRemoteAddress().equals(filterIp)&& !"ALL".equals(filterIp)) continue;

		String displaySessionId = "";

%>
<%
		if (!stat.getSessionId().equals(prevSessionId)){
			displaySessionId = stat.getSessionId();
			prevSessionId = stat.getSessionId();
%>								

	<tr>
		<td colspan="10">
			<%= displaySessionId %>
		</td>
	</tr>

<%
			
		}



%>
		
	<tr>
		<td>
			<%= stat.getServerName() %>
		</td>
		
		<td>
			<%= stat.getLoggedinUser() %>
		</td>
		
		<td>
			<%= stat.getRequestUri() %> 
<% if(WebUtil.isNotNull(stat.getParamString())){ %>
		<a class="cttitle" href="#" title="Params|<%= stat.getParamString().replaceAll(",", "|") %>">&nbsp;[Param]</a>
<%	} %>		
		</td>
		<td>
<% if(statData != null && WebUtil.isNotNull((String)statData.getHeaderAttributes().get("referer"))){ %>
		&nbsp;<a class="cttitle" href="#" title="Referer|<%= statData.getHeaderAttributes().get("referer") %>">[ref]</a>
<%	} %><%= statData.getHeaderAttributes().get("referer") %>
		</td>
	
		<td>
		<%= stat.getStatDate() %>
		</td>
		
	</tr>
	
	
<%
		}
	}
%>
</table>
<br/><br/>


<h3> Users </h3>
<table class="mytable1">
<%

	List keysUsers = StatsPool.getInstance().getUsersInPool();	
	for(Iterator iter = keysUsers.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		List stats = StatsPool.getInstance().getUserStats(key);			
		
%>
	<tr>
		<td >
			<%= key %>
		</td>
		<td >
			<%= stats.size() %>
		</td>
	</tr>
	
<%
		for(Iterator iter2 = stats.iterator(); iter2.hasNext();){
			StatData stat = (StatData) iter2.next();
			
%>
	<tr>
		<td>
			<%= stat.getServerName() %>
		</td>
		<td>
			<%= stat.getRequestUri() %>
		</td>
	</tr>
	
	
<%
		}
	}
%>
</table>

