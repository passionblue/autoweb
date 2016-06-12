<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _style_idValue= "";
	String _link_style_idValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_autosite_defaults_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = AutositeDefaultsDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		AutositeDefaults _AutositeDefaults = (AutositeDefaults) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _AutositeDefaults.getId() %> </td>

    <td> <%= WebUtil.display(_AutositeDefaults.getStyleId()) %></td>
    <td> <%= WebUtil.display(_AutositeDefaults.getLinkStyleId()) %></td>
    <td> <%= WebUtil.display(_AutositeDefaults.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_AutositeDefaults.getTimeUpdated()) %></td>


<td>
<form name="autositeDefaultsForm<%=_AutositeDefaults.getId()%>" method="post" action="/v_autosite_defaults_edit.html" >
	<a href="javascript:document.autositeDefaultsForm<%=_AutositeDefaults.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeDefaults.getId() %>">
</form>
</td>
<td>
<a href="/autositeDefaultsAction.html?del=true&id=<%=_AutositeDefaults.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>