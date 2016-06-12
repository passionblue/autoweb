<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _column_numValue= "";
	String _panel_titleValue= "";
	String _panel_typeValue= "";
	String _panel_heightValue= "";
	String _hideValue= "";
	String _time_createdValue= "";
	String _top_spaceValue= ""; 
	String _bottom_spaceValue= "";
	String _left_spaceValue= "";
	String _right_spaceValue= "";
	String _style_stringValue= "";
	String _alignValue= "";
%> 

<br>

<h4> <a href="t_panel_add.html"> Add Panel</a> </h4>

<br>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = PanelDS.getInstance().getBySiteId(site.getId());
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		Panel _Panel = (Panel) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _Panel.getId() %> </td>

    <td> <%= WebUtil.display(_Panel.getColumnNum()) %></td>
    <td> <%= WebUtil.display(_Panel.getPanelTitle()) %></td>
    <td> <%= WebUtil.display(_Panel.getPanelType()) %></td>
    <td> <%= WebUtil.display(_Panel.getPanelHeight()) %></td>
    <td> <%= WebUtil.display(_Panel.getHide()) %></td>
    <td> <%= WebUtil.display(_Panel.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_Panel.getTopSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getBottomSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getLeftSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getRightSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getStyleString()) %></td>
    <td> <%= WebUtil.display(_Panel.getAlign()) %></td>


<td>
<form name="panelForm<%=_Panel.getId()%>" method="post" action="/v_panel_edit.html" >
	<a href="javascript:document.panelForm<%=_Panel.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _Panel.getId() %>">
</form>
</td>
<td>
<a href="/panelAction.html?del=true&id=<%=_Panel.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>