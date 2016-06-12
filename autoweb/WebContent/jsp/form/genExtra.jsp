<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _main_idValue= "";
	String _sub_idValue= "";
	String _extra_valueValue= "";
	String _extra_dataValue= "";
	String _time_createdValue= "";
	String _time_updatedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="t_gen_extra_add.html"> Add New</a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

	List list = GenExtraDS.getInstance().getAll();
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		GenExtra _GenExtra = (GenExtra) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _GenExtra.getId() %> </td>

    <td> <%= WebUtil.display(_GenExtra.getMainId()) %></td>
    <td> <%= WebUtil.display(_GenExtra.getSubId()) %></td>
    <td> <%= WebUtil.display(_GenExtra.getExtraValue()) %></td>
    <td> <%= WebUtil.display(_GenExtra.getExtraData()) %></td>
    <td> <%= WebUtil.display(_GenExtra.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_GenExtra.getTimeUpdated()) %></td>


<td>
<form name="genExtraForm<%=_GenExtra.getId()%>" method="post" action="/v_gen_extra_edit.html" >
	<a href="javascript:document.genExtraForm<%=_GenExtra.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenExtra.getId() %>">
</form>
</td>
<td>
<a href="/genExtraAction.html?del=true&id=<%=_GenExtra.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>