<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	GenSub _GenSub = GenSubDS.getInstance().getById(id);

	if ( _GenSub == null ) {
		return;
	}

	String _main_idValue=  String.valueOf(_GenSub.getMainId());
	String _str_keyValue=  String.valueOf(_GenSub.getStrKey());
	String _sub_dataValue=  String.valueOf(_GenSub.getSubData());
	String _time_createdValue=  String.valueOf(_GenSub.getTimeCreated());
	String _time_updatedValue=  String.valueOf(_GenSub.getTimeUpdated());
%> 

<br>
<form name="genSubFormEdit" method="post" action="/genSubAction.html" >
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
			<b><a href="javascript:document.genSubFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenSub.getId()%>">
</form>
