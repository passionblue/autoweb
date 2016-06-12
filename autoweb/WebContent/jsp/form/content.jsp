<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _page_idValue= "";
	String _sub_page_idValue= "";
	String _panel_idValue= "";
	String _content_subjectValue= "";
	String _contentValue= "";
	String _categoryValue= "";
	String _created_timeValue= "";
	String _updated_timeValue= "";
	String _content_typeValue= "";
	String _source_nameValue= "";
	String _source_urlValue= "";
	String _hideValue= "";
	String _show_homeValue= "";
	String _image_urlValue= "";
	String _image_heightValue= "";
	String _image_widthValue= "";
	String _in_htmlValue= "";
	String _tagsValue= "";
	String _extra_keywordsValue= "";
	String _shortcut_urlValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_content_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = ContentDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		Content _Content = (Content) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _Content.getId() %> </td>

    <td> <%= WebUtil.display(_Content.getPageId()) %></td>
    <td> <%= WebUtil.display(_Content.getSubPageId()) %></td>
    <td> <%= WebUtil.display(_Content.getPanelId()) %></td>
    <td> <%= WebUtil.display(_Content.getContentSubject()) %></td>
    <td> <%= WebUtil.getAbstractString(_Content.getContent()) %></td>
    <td> <%= WebUtil.display(_Content.getCategory()) %></td>
    <td> <%= WebUtil.display(_Content.getCreatedTime()) %></td>
    <td> <%= WebUtil.display(_Content.getUpdatedTime()) %></td>
    <td> <%= WebUtil.display(_Content.getContentType()) %></td>
    <td> <%= WebUtil.display(_Content.getSourceName()) %></td>
    <td> <%= WebUtil.display(_Content.getSourceUrl()) %></td>
    <td> <%= WebUtil.displayYN(_Content.getHide()) %></td>
    <td> <%= WebUtil.displayYN(_Content.getShowHome()) %></td>
    <td> <%= WebUtil.display(_Content.getImageUrl()) %></td>
    <td> <%= WebUtil.display(_Content.getImageHeight()) %></td>
    <td> <%= WebUtil.display(_Content.getImageWidth()) %></td>
    <td> <%= WebUtil.displayYN(_Content.getInHtml()) %></td>
    <td> <%= WebUtil.display(_Content.getTags()) %></td>
    <td> <%= WebUtil.display(_Content.getExtraKeywords()) %></td>
    <td> <%= WebUtil.display(_Content.getShortcutUrl()) %></td>


<td>
<form name="contentForm<%=_Content.getId()%>" method="post" action="/v_content_edit.html" >
	<a href="javascript:document.contentForm<%=_Content.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _Content.getId() %>">
</form>
</td>
<td>
<a href="/contentAction.html?del=true&id=<%=_Content.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>