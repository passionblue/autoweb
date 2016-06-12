<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _menu_panel_idValue= "";
	String _parent_idValue= "";
	String _page_nameValue= "";
	String _page_menu_titleValue= "";
	String _hideValue= "";
	String _created_timeValue= "";
	String _site_urlValue= "";
	String _page_col_countValue= "";
	String _page_keywordsValue= "";
	String _page_view_typeValue= "";
	String _underlying_pageValue= "";
	String _header_pageValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_page_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = PageDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		Page _Page = (Page) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _Page.getId() %> </td>

    <td> <%= WebUtil.display(_Page.getMenuPanelId()) %></td>
    <td> <%= WebUtil.display(_Page.getParentId()) %></td>
    <td> <%= WebUtil.display(_Page.getPageName()) %></td>
    <td> <%= WebUtil.display(_Page.getPageMenuTitle()) %></td>
    <td> <%= WebUtil.display(_Page.getHide()) %></td>
    <td> <%= WebUtil.display(_Page.getCreatedTime()) %></td>
    <td> <%= WebUtil.display(_Page.getSiteUrl()) %></td>
    <td> <%= WebUtil.display(_Page.getPageColCount()) %></td>
    <td> <%= WebUtil.display(_Page.getPageKeywords()) %></td>
    <td> <%= WebUtil.display(_Page.getPageViewType()) %></td>
    <td> <%= WebUtil.display(_Page.getUnderlyingPage()) %></td>
    <td> <%= WebUtil.display(_Page.getHeaderPage()) %></td>


<td>
<form name="pageForm<%=_Page.getId()%>" method="get" action="/v_page_edit.html" >
	<a href="javascript:document.pageForm<%=_Page.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _Page.getId() %>">
</form>
</td>
<td>
<a href="/pageAction.html?del=true&id=<%=_Page.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>