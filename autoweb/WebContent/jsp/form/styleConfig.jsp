<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _style_keyValue= "";
	String _bg_colorValue= "";
	String _bg_imageValue= "";
	String _bg_repeatValue= "";
	String _bg_attachValue= "";
	String _bg_positionValue= "";
	String _text_alignValue= "";
	String _font_familyValue= "";
	String _font_sizeValue= "";
	String _font_styleValue= "";
	String _font_variantValue= "";
	String _font_weightValue= "";
	String _border_widthValue= "";
	String _border_styleValue= "";
	String _border_colorValue= "";
	String _marginValue= "";
	String _paddingValue= "";
	String _list_style_typeValue= "";
	String _list_style_positionValue= "";
	String _list_style_imageValue= "";
	String _floatingValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
		<a href="t_style_config_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>
<br>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = StyleConfigDS.getInstance().getBySiteId(site.getId());
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		StyleConfig _StyleConfig = (StyleConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _StyleConfig.getId() %> </td>

    <td> <%= WebUtil.display(_StyleConfig.getStyleKey()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgColor()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgImage()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgRepeat()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgAttach()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBgPosition()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getTextAlign()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontFamily()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontSize()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontStyle()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontVariant()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFontWeight()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderWidth()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderStyle()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getBorderColor()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getMargin()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getPadding()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getListStyleType()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getListStylePosition()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getListStyleImage()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getFloating()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_StyleConfig.getTimeUpdated()) %></td>


<td>
<form name="styleConfigForm<%=_StyleConfig.getId()%>" method="post" action="/v_style_config_edit.html" >
	<a href="javascript:document.styleConfigForm<%=_StyleConfig.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleConfig.getId() %>">
</form>
</td>
<td>
<a href="/styleConfigAction.html?del=true&id=<%=_StyleConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>