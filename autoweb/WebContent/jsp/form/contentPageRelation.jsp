<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _content_idValue= "";
	String _page_idValue= "";
	String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_content_page_relation_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = ContentPageRelationDS.getInstance().getAll();
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ContentPageRelation _ContentPageRelation = (ContentPageRelation) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _ContentPageRelation.getId() %> </td>

    <td> <%= WebUtil.display(_ContentPageRelation.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentPageRelation.getPageId()) %></td>
    <td> <%= WebUtil.display(_ContentPageRelation.getTimeCreated()) %></td>


<td>
<form name="contentPageRelationForm<%=_ContentPageRelation.getId()%>" method="post" action="/v_content_page_relation_edit.html" >
	<a href="javascript:document.contentPageRelationForm<%=_ContentPageRelation.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentPageRelation.getId() %>">
</form>
</td>
<td>
<a href="/contentPageRelationAction.html?del=true&id=<%=_ContentPageRelation.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>