<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _panel_idValue= WebUtil.display((String)reqParams.get("panelId"));;
	String _style_idValue= WebUtil.display((String)reqParams.get("styleId"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;

	String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="panelLinkStyleForm" method="post" action="/panelLinkStyleAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Panel Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
	</TR>
						<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.panelLinkStyleForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

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