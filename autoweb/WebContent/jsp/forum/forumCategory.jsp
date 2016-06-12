<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _categoryValue= "";
	String _time_createdValue= "";
%> 

<br>
<form name="forumCategoryForm" method="post" action="/forumCategoryAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Category</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="category" value="<%=_categoryValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>TimeCreated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=_time_createdValue%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumCategoryForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = ForumCategoryDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumCategory _ForumCategory = (ForumCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">
<td> <%= _ForumCategory.getId() %> </td>
<td>
<form name="forumCategoryForm<%=_ForumCategory.getId()%>" method="post" action="/v_forum_category_edit.html" >
	<a href="javascript:document.forumCategoryForm<%=_ForumCategory.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumCategory.getId() %>">
</form>
</td>
<td>
<a href="/forumCategoryAction.html?del=true&id=<%=_ForumCategory.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>