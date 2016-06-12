<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _main_idValue= "";
	String _str_keyValue= "";
	String _sub_dataValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_gen_sub_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = GenSubDS.getInstance().getAll();
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		GenSub _GenSub = (GenSub) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _GenSub.getId() %> </td>

    <td> <%= WebUtil.display(_GenSub.getMainId()) %></td>
    <td> <%= WebUtil.display(_GenSub.getStrKey()) %></td>
    <td> <%= WebUtil.display(_GenSub.getSubData()) %></td>
    <td> <%= WebUtil.display(_GenSub.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_GenSub.getTimeUpdated()) %></td>


<td>
<form name="genSubForm<%=_GenSub.getId()%>" method="post" action="/v_gen_sub_edit.html" >
	<a href="javascript:document.genSubForm<%=_GenSub.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenSub.getId() %>">
</form>
</td>
<td>
<a href="/genSubAction.html?del=true&id=<%=_GenSub.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>