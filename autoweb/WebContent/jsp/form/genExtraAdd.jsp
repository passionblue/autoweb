<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _main_idValue= WebUtil.display((String)reqParams.get("mainId"));;
	String _sub_idValue= WebUtil.display((String)reqParams.get("subId"));;
	String _extra_valueValue= WebUtil.display((String)reqParams.get("extraValue"));;
	String _extra_dataValue= WebUtil.display((String)reqParams.get("extraData"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));;


%> 

<br>
<form name="genExtraForm" method="post" action="/genExtraAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Main Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="mainId" value="<%=WebUtil.display(_main_idValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Sub Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="subId" value="<%=WebUtil.display(_sub_idValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Extra Value</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="extraValue" value="<%=WebUtil.display(_extra_valueValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Extra Data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="extraData" value="<%=WebUtil.display(_extra_dataValue)%>"/></TD>
	</TR>
									<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.genExtraForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

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