<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _panel_idValue= "";
	String _style_idValue= "";
	String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_panel_link_style_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = PanelLinkStyleDS.getInstance().getBySiteId(site.getId());
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		PanelLinkStyle _PanelLinkStyle = (PanelLinkStyle) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _PanelLinkStyle.getId() %> </td>

    <td> <%= WebUtil.display(_PanelLinkStyle.getPanelId()) %></td>
    <td> <%= WebUtil.display(_PanelLinkStyle.getStyleId()) %></td>
    <td> <%= WebUtil.display(_PanelLinkStyle.getTimeCreated()) %></td>


<td>
<form name="panelLinkStyleForm<%=_PanelLinkStyle.getId()%>" method="post" action="/v_panel_link_style_edit.html" >
	<a href="javascript:document.panelLinkStyleForm<%=_PanelLinkStyle.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _PanelLinkStyle.getId() %>">
</form>
</td>
<td>
<a href="/panelLinkStyleAction.html?del=true&id=<%=_PanelLinkStyle.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>