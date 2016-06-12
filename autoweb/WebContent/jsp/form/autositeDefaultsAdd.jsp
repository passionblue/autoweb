<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _style_idValue= WebUtil.display((String)reqParams.get("styleId"));;
	String _link_style_idValue= WebUtil.display((String)reqParams.get("linkStyleId"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));;

	String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="autositeDefaultsForm" method="post" action="/autositeDefaultsAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Link Style Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="linkStyleId" value="<%=WebUtil.display(_link_style_idValue)%>"/></TD>
	</TR>
									<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.autositeDefaultsForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

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