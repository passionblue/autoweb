<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _first_nameValue= "";
	String _last_nameValue= "";
	String _emailValue= "";
	String _usernameValue= "";
	String _passwordValue= "";
	String _birth_yearValue= "";
	String _birth_monthValue= "";
	String _birth_dayValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_register_simple_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		RegisterSimple _RegisterSimple = (RegisterSimple) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _RegisterSimple.getId() %> </td>

    <td> <%= WebUtil.display(_RegisterSimple.getFirstName()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getLastName()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getEmail()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getUsername()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getPassword()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getBirthYear()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getBirthMonth()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getBirthDay()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getTimeUpdated()) %></td>


<td>
<form name="registerSimpleForm<%=_RegisterSimple.getId()%>" method="post" action="/v_register_simple_edit.html" >
	<a href="javascript:document.registerSimpleForm<%=_RegisterSimple.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _RegisterSimple.getId() %>">
</form>
</td>
<td>
<a href="/registerSimpleAction.html?del=true&id=<%=_RegisterSimple.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>