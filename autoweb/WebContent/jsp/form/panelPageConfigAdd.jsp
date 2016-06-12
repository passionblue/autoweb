<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _panel_idValue= WebUtil.display((String)reqParams.get("panelId"));;
	String _page_display_summaryValue= WebUtil.display((String)reqParams.get("pageDisplaySummary"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));;

	String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="panelPageConfigForm" method="post" action="/panelPageConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Panel Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/></TD>
	</TR>
				<TR bgcolor="#ffffff">
		<TD align="right" ><b>Page Display Summary</b> &nbsp;</TD>
		<TD>&nbsp;<select name="pageDisplaySummary">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _page_display_summaryValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _page_display_summaryValue)%>>Yes</option>
            </select>
        </TD>
	</TR>
								<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.panelPageConfigForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

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