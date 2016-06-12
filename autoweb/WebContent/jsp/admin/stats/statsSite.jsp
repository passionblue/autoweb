<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.jtrend.stats.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");


	String filterSite = request.getParameter("filterSite");

	if (filterSite == null) filterSite="XXXX";
%> 

<jsp:include page="/jsp/admin/stats/statsMenu.jsp" />
<br/>

<h3> Site Stats </h3>
<a href="/v_admin_stats_site.html?filterSite=ALL"> Expand All</a> | <a href="/v_admin_stats_site.html?filterSite=NONE"> Collapse All</a> 

<table class="mytable1">
<%

	List sites = StatsPool.getInstance().getSitesInPool();
	for(Iterator iter = sites.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		
		List addrToSites = StatsPool.getInstance().getSiteToAddresses(key);	

			if (WebUtil.isNotNull(filterSite) && !key.equals(filterSite)&& !"ALL".equals(filterSite)) continue;

%>

	<tr>
		
		<td colspan="10" class="columnBold">
			<a href="/v_admin_stats_site.html?filterSite=<%= key %>"> <%= key %></a> 
		</td>
	</tr>

<%
		
	//====================================================================================================================
	for(Iterator iter2 = addrToSites.iterator(); iter2.hasNext();){
		String add = (String) iter2.next();


		List stats = StatsPool.getInstance().getRemoteAddrStats(add);
	//====================================================================================================================
	for(Iterator iter3 = stats.iterator(); iter3.hasNext();){
		StatData stat = (StatData) iter3.next();

		if (!add.equals(stat.getRemoteAddress())) continue;
//		if (!key.equals(SiteDS.getCommonUrl(stat.getServerName()))) continue; I dont know why I put this here but this does not work.
		if (!key.equals(stat.getServerName())) continue;

%>
	<tr>
		
		<td>
			<%=add%>
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
<% if(stat != null && WebUtil.isNotNull((String)stat.getHeaderAttributes().get("referer"))){ %>
		&nbsp;<a class="cttitle" href="#" title="Referer|<%= stat.getHeaderAttributes().get("referer") %>">[ref]</a>
<%	} %>
		</td>
	
		<td>
		<%= stat.getStatDate() %>
		</td>
		
	</tr>
<%
			}
		}
	}
%>
</table>