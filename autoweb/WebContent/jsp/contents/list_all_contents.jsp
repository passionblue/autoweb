<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.jtrend.session.SessionContext,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<%

	AutositeSessionContext loginContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
	List allContets = ContentDS.getInstance().getBySiteId(site.getId());
	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}
	
%>     

<table class="mytable1" >

<tr>
	<td class="columnTitle" > Id</td>
	<td class="columnTitle" > Subject</td>
	<td class="columnTitle" > Time</td>
	<td class="columnTitle" > </td>
</tr>

<%
    for (Iterator iterator = allContets.iterator(); iterator.hasNext();) {
		Content c = (Content) iterator.next();
%>
	<tr>
		<td  class="columnData"><%= c.getId() %></td>
		<td  class="columnData"><%= c.getContentSubject() %></td>
		<td  class="columnData"><%= c.getCreatedTime() %></td>
		<td  class="columnData"><a href="/t_dyn_content_single.html?cid=<%=c.getId()%>">view</a></td>
		
		
	</tr>

<%
	}
%>

</table>
     