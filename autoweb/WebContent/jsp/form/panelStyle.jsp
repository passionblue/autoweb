<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

%> 

<br>

<h4> <a href="t_panel_add.html"> Add Panel</a> </h4>

<br>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = PanelStyleDS.getInstance().getBySiteId(site.getId());
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		PanelStyle _PanelStyle = (PanelStyle) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _PanelStyle.getId() %> </td>



<td>
<form name="panelStyleForm<%=_PanelStyle.getId()%>" method="post" action="/v_panel_style_edit.html" >
	<a href="javascript:document.panelStyleForm<%=_PanelStyle.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _PanelStyle.getId() %>">
</form>
</td>
<td>
<a href="/panelStyleAction.html?del=true&id=<%=_PanelStyle.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>