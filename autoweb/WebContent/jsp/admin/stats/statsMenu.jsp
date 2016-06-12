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

<table class="mytable1">
	<tr>
		<td colspan="10" class="columnBold">
			<a href="/v_admin_stats_home.html"> Test</a> 
		</td>
		<td colspan="10" class="columnBold">
			<a href="/v_admin_stats_ip.html"> IP</a> 
		</td>
		<td colspan="10" class="columnBold">
			<a href="/v_admin_stats_site.html"> Sites</a> 
		</td>
		<td colspan="10" class="columnBold">
			<a href="/v_admin_stats_session.html"> Session</a> 
		</td>
		<td colspan="10" class="columnBold">
			<a href="/v_admin_stats_robot.html"> Session</a> 
		</td>
	</tr>
</table>