<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _usernameValue= "";
	String _passwordValue= "";
	String _emailValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_user_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = UserDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		User _User = (User) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _User.getId() %> </td>

    <td> <%= WebUtil.display(_User.getUsername()) %></td>
    <td> <%= WebUtil.display(_User.getPassword()) %></td>
    <td> <%= WebUtil.display(_User.getEmail()) %></td>
    <td> <%= WebUtil.display(_User.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_User.getTimeUpdated()) %></td>


<td>
<form name="userForm<%=_User.getId()%>" method="post" action="/v_user_edit.html" >
	<a href="javascript:document.userForm<%=_User.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _User.getId() %>">
</form>
</td>
<td>
<a href="/userAction.html?del=true&id=<%=_User.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>