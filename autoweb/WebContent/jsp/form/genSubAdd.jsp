<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _main_idValue= WebUtil.display((String)reqParams.get("mainId"));;
	String _str_keyValue= WebUtil.display((String)reqParams.get("strKey"));;
	String _sub_dataValue= WebUtil.display((String)reqParams.get("subData"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));;


%> 

<br>
<form name="genSubForm" method="post" action="/genSubAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Main Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="mainId" value="<%=WebUtil.display(_main_idValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Str Key</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="strKey" value="<%=WebUtil.display(_str_keyValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Sub Data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="subData" value="<%=WebUtil.display(_sub_dataValue)%>"/></TD>
	</TR>
									<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.genSubForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

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