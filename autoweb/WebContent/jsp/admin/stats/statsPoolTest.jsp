<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.jtrend.stats.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");


%> 

<jsp:include page="/jsp/admin/stats/statsMenu.jsp" />
<br/>

<table class="mytable1">
	<tr>
		<td>
		</td>
		<td>
		</td>
		<td>
		</td>
	</tr>
<%

	List sessions = StatsPool.getInstance().getSessionsInPool();	
	for(Iterator iter = sessions.iterator(); iter.hasNext();){
		String sid = (String) iter.next();

		List sessStats = StatsPool.getInstance().getSessionStats(sid);
		
%>		
	<tr>
		<td>
			<b><%=sid %></b>			
		</td>
		<td>
			<%=sessStats.size() %>
		</td>
		<td>
		</td>
	</tr>
		
<%
			
		for(Iterator iter2 = sessStats.iterator(); iter2.hasNext();){
			StatData stat = (StatData) iter2.next();
			
%>

	<tr>
		<td>
			<%= stat.getServerName() %>
		</td>
		<td>
			<%= stat.getServerName() %>|<%= stat.getRequestUri() %> |<%= WebUtil.display(stat.getQueryString()) %>|<%= stat.isLogin() %>
		</td>
		<td class="bottomSmall"><%=stat.getStatDate() %></td>
	</tr>
<%
	}
%>

<%
	}
%>

</table>


<br/><br/>
<h3> IP Stats </h3>

<table class="mytable1">
	<tr>
		<td>
		</td>
		<td>
		</td>
	</tr>
<%

	List rAddrs = StatsPool.getInstance().getRemoteAddrsInPool();	
	for(Iterator iter = rAddrs.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		
		List stats = StatsPool.getInstance().getRemoteAddrStats(key);	
%>
	<tr>
		<td>
			<%= key %>
		</td>
		<td>
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
<br/><br/>
<table class="mytable1">
	<tr>
		<td>
		</td>
		<td>
		</td>
	</tr>
<%

	List sites = StatsPool.getInstance().getSitesInPool();	
	for(Iterator iter = sites.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		
		List stats = StatsPool.getInstance().getSiteStats(key);	
%>
	<tr>
		<td>
			<%= key %>
		</td>
		<td>
			<%= stats.size() %>
		</td>
	</tr>
<%
	}
%>
</table>

<h3> Sites To IP Address </h3>
<table class="mytable1">
<%

	List keysSiteToAddr = StatsPool.getInstance().getKeysSiteToAddress();	
	for(Iterator iter = keysSiteToAddr.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		List listsSiteToAddrs = StatsPool.getInstance().getSiteToAddresses(key);			
		
%>
	<tr>
		<td >
			<%= key %>
		</td>
		<td >
			<%= listsSiteToAddrs.size() %>
		</td>
	</tr>
	
<%
		for(Iterator iter2 = listsSiteToAddrs.iterator(); iter2.hasNext();){
			String ip = (String) iter2.next();
			
%>
	<tr>
		<td>
			<%= ip %>
		</td>
		<td>

		</td>
	</tr>
	
	
<%
		}
	}
%>
</table>

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

