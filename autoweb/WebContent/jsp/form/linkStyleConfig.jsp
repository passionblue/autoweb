<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _style_keyValue= "";
	String _heightValue= "";
	String _widthValue= "";
	String _displayValue= "";
	String _borderValue= "";
	String _backgroundValue= "";
	String _colorValue= "";
	String _text_decorationValue= "";
	String _text_alignValue= "";
	String _vertical_alignValue= "";
	String _text_indentValue= "";
	String _extra_styleValue= "";
	String _hov_heightValue= "";
	String _hov_widthValue= "";
	String _hov_displayValue= "";
	String _hov_borderValue= "";
	String _hov_backgroundValue= "";
	String _hov_colorValue= "";
	String _hov_text_decorationValue= "";
	String _hov_text_alignValue= "";
	String _hov_vertical_alignValue= "";
	String _hov_text_indentValue= "";
	String _hov_extra_styleValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_link_style_config_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		LinkStyleConfig _LinkStyleConfig = (LinkStyleConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

<td>
<form name="linkStyleConfigForm<%=_LinkStyleConfig.getId()%>" method="post" action="/v_link_style_config_edit.html" >
	<a href="javascript:document.linkStyleConfigForm<%=_LinkStyleConfig.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _LinkStyleConfig.getId() %>">
</form>
<a href="/linkStyleConfigAction.html?del=true&id=<%=_LinkStyleConfig.getId()%>"> Delete </a>
</td>

	<td> <%= _LinkStyleConfig.getId() %> </td>

    <td> <%= WebUtil.display(_LinkStyleConfig.getStyleKey()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHeight()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getWidth()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getDisplay()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getBorder()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getBackground()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getColor()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTextDecoration()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTextAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getVerticalAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTextIndent()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getExtraStyle()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovHeight()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovWidth()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovDisplay()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovBorder()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovBackground()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovColor()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovTextDecoration()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovTextAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovVerticalAlign()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovTextIndent()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getHovExtraStyle()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_LinkStyleConfig.getTimeUpdated()) %></td>

</TR>

<%
	}
%>
</TABLE>