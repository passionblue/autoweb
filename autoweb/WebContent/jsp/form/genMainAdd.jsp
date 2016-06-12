<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _activeValue= WebUtil.display((String)reqParams.get("active"));;
	String _valueValue= WebUtil.display((String)reqParams.get("value"));;
	String _dataValue= WebUtil.display((String)reqParams.get("data"));;
	String _requiredValue= WebUtil.display((String)reqParams.get("required"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));;


%> 

<br>
<form name="genMainForm" method="post" action="/genMainAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Active</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="active" value="<%=WebUtil.display(_activeValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Value</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="value" value="<%=WebUtil.display(_valueValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data" value="<%=WebUtil.display(_dataValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Required</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="required" value="<%=WebUtil.display(_requiredValue)%>"/></TD>
	</TR>
									<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.genMainForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = GenMainDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		GenMain _GenMain = (GenMain) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _GenMain.getId() %> </td>

    <td> <%= WebUtil.display(_GenMain.getActive()) %></td>
    <td> <%= WebUtil.display(_GenMain.getValue()) %></td>
    <td> <%= WebUtil.display(_GenMain.getData()) %></td>
    <td> <%= WebUtil.display(_GenMain.getRequired()) %></td>
    <td> <%= WebUtil.display(_GenMain.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_GenMain.getTimeUpdated()) %></td>


<td>
<form name="genMainForm<%=_GenMain.getId()%>" method="post" action="/v_gen_main_edit.html" >
	<a href="javascript:document.genMainForm<%=_GenMain.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenMain.getId() %>">
</form>
</td>
<td>
<a href="/genMainAction.html?del=true&id=<%=_GenMain.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>