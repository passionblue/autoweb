<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	GenMain _GenMain = GenMainDS.getInstance().getById(id);

	if ( _GenMain == null ) {
		return;
	}

	String _activeValue=  String.valueOf(_GenMain.getActive());
	String _valueValue=  String.valueOf(_GenMain.getValue());
	String _dataValue=  String.valueOf(_GenMain.getData());
	String _requiredValue=  String.valueOf(_GenMain.getRequired());
	String _time_createdValue=  String.valueOf(_GenMain.getTimeCreated());
	String _time_updatedValue=  String.valueOf(_GenMain.getTimeUpdated());
%> 

<br>
<form name="genMainFormEdit" method="post" action="/genMainAction.html" >
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
			<b><a href="javascript:document.genMainFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenMain.getId()%>">
</form>
