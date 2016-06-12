<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _usernameValue= WebUtil.display((String)reqParams.get("username"));;
	String _passwordValue= WebUtil.display((String)reqParams.get("password"));;
	String _emailValue= WebUtil.display((String)reqParams.get("email"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));;

	String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="userForm" method="post" action="/userAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Username</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="username" value="<%=WebUtil.display(_usernameValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Password</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="password" value="<%=WebUtil.display(_passwordValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Email</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
	</TR>
									<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.userForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

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