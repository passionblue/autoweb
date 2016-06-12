<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	AutositeSessionContext ctxt = (AutositeSessionContext) session.getAttribute("k_session_context");

	List sessionContexts = SessionContext.getSessionContexts();
	System.out.println("------>" + sessionContexts.size());
%> 
	
<Table class="mytable1">
<%
	for(Iterator iter = sessionContexts.iterator(); iter.hasNext();) {
	    SessionContext sessionContext = (SessionContext) iter.next();
%>	    
	<tr>
		<td><a href="/v_session_detail.html?sessionid=<%=sessionContext.getSessionId()%>" ><%=sessionContext.getSessionId()%></a></td>
		<td><%=ctxt.getSessionId().equals(sessionContext.getSessionId())? "*":"" %></td>
		<td><%=sessionContext.getSerial() %></td>
		<td><%=sessionContext.getCreatedTime() %> </td>
		<td><%=sessionContext.getUsername() %> </td>
		<td><%=sessionContext.getServer() %> </td>
		<td><%=sessionContext.getRemoteIp() %> </td>
		<td><%=new Date(sessionContext.getLastAccess()) %> </td>
	</tr>
<%		
	}
%>
</Table>