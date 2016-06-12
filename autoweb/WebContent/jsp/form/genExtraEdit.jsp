<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	GenExtra _GenExtra = GenExtraDS.getInstance().getById(id);

	if ( _GenExtra == null ) {
		return;
	}

	String _main_idValue=  String.valueOf(_GenExtra.getMainId());
	String _sub_idValue=  String.valueOf(_GenExtra.getSubId());
	String _extra_valueValue=  String.valueOf(_GenExtra.getExtraValue());
	String _extra_dataValue=  String.valueOf(_GenExtra.getExtraData());
	String _time_createdValue=  String.valueOf(_GenExtra.getTimeCreated());
	String _time_updatedValue=  String.valueOf(_GenExtra.getTimeUpdated());
%> 

<br>
<form name="genExtraFormEdit" method="post" action="/genExtraAction.html" >
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
			<b><a href="javascript:document.genExtraFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenExtra.getId()%>">
</form>
