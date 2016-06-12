<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _dataValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<br>
<form name="forumPostForm" method="post" action="/forumPostAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data" value="<%=_dataValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>TimeCreated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=_time_createdValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>TimeUpdated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeUpdated" value="<%=_time_updatedValue%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumPostForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = ForumPostDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumPost _ForumPost = (ForumPost) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _ForumPost.getId() %> </td>

    <td> <%= WebUtil.display(_ForumPost.getData()) %></td>
    <td> <%= WebUtil.display(_ForumPost.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ForumPost.getTimeUpdated()) %></td>


<td>
<form name="forumPostForm<%=_ForumPost.getId()%>" method="post" action="/v_forum_post_edit.html" >
	<a href="javascript:document.forumPostForm<%=_ForumPost.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumPost.getId() %>">
</form>
</td>
<td>
<a href="/forumPostAction.html?del=true&id=<%=_ForumPost.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>