<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
    Map reqParams = (Map) session.getAttribute("k_request_params");

	System.out.println("--------------- " + reqParams);
	
	SessionContext sessionContext = null;
	if (reqParams.containsKey("sessionid")) {
		sessionContext = (SessionContext) SessionContext.getSessionIdToContext().get((String)reqParams.get("sessionid"));
	} 
	
	if (sessionContext == null)
	    return;
	
	
	System.out.println("--------------- " + sessionContext);
	
	List handles = sessionContext.getRequestHandles();
	
%> 

<Table class="mytable1">
	<tr><td>Server</td>		<td><%=sessionContext.getServer() %></td></tr>
	<tr><td>Serial</td>		<td><%=sessionContext.getSerial() %></td></tr>
	<tr><td>RemoteIP</td>		<td><%=sessionContext.getRemoteIp() %></td></tr>
	<tr><td>Created</td>		<td><%=sessionContext.getCreatedTime() %></td></tr>
	<tr><td>Last Accessed</td>		<td><%=new Date(sessionContext.getLastAccess()) %></td></tr>
	<tr><td>User</td>		<td><%=sessionContext.getUsername() %></td></tr>
	<tr><td>Login</td>		<td><%=sessionContext.getLoginTime() %></td></tr>
	
<%
	for(Iterator iter = handles.iterator(); iter.hasNext();) {
	    SessionRequestHandle rh = (SessionRequestHandle) iter.next();
%>	 
	<tr><td></td>		<td><%=rh.getUri() %> | redirect=<%= rh.isRequestRedirected() %>| <%=rh.getQuery() %></td></tr>
   
<%
	}
%>	
	
</Table>

