<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _panel_idValue= "";
	String _page_display_summaryValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_panel_page_config_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = PanelPageConfigDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		PanelPageConfig _PanelPageConfig = (PanelPageConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _PanelPageConfig.getId() %> </td>

    <td> <%= WebUtil.display(_PanelPageConfig.getPanelId()) %></td>
    <td> <%= WebUtil.display(_PanelPageConfig.getPageDisplaySummary()) %></td>
    <td> <%= WebUtil.display(_PanelPageConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PanelPageConfig.getTimeUpdated()) %></td>


<td>
<form name="panelPageConfigForm<%=_PanelPageConfig.getId()%>" method="post" action="/v_panel_page_config_edit.html" >
	<a href="javascript:document.panelPageConfigForm<%=_PanelPageConfig.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _PanelPageConfig.getId() %>">
</form>
</td>
<td>
<a href="/panelPageConfigAction.html?del=true&id=<%=_PanelPageConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>