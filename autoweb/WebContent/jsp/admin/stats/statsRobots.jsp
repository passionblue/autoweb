<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.jtrend.stats.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");


	String filterRobot = request.getParameter("filterRobot");

	if (filterRobot == null) filterRobot="XXXX";
%> 

<jsp:include page="/jsp/admin/stats/statsMenu.jsp" />
<br/>

<h3> Robots Stats </h3>
<a href="/v_admin_stats_robot.html?filterRobot=ALL"> Expand All</a> | <a href="/v_admin_stats_robot.html?filterRobot=NONE"> Collapse All</a> 

<table class="mytable1">
<%

	List sites = StatsPool.getInstance().getRobotsInPool();
	for(Iterator iter = sites.iterator(); iter.hasNext();){
		String key = (String) iter.next();
		
		List robotStats = StatsPool.getInstance().getRobotStats(key);	

		if (WebUtil.isNotNull(filterRobot) && !key.equals(filterRobot)&& !"ALL".equals(filterRobot)) continue;

%>

	<tr>
		
		<td colspan="10" class="columnBold">
			<%= key %> 
		</td>
	</tr>

<%
		
	//====================================================================================================================
	for(Iterator iter2 = robotStats.iterator(); iter2.hasNext();){
		StatData stat = (StatData) iter2.next();

%>
	<tr>
	
		<td>
			<%=stat.getRemoteAddress()%>
		</td>
		<td>
			<%=stat.getServerName()%>
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
		
<% if(stat != null && WebUtil.isNotNull((String)stat.getHeaderAttributes().get("user-agent"))){ %>
&nbsp;<a class="cttitle" href="#" title="User-Agent|<%= stat.getHeaderAttributes().get("user-agent") %>">[UA]</a>
<%	} %>
		
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
%>
</table>