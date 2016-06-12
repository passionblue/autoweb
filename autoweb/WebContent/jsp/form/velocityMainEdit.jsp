<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	VelocityMain _VelocityMain = VelocityMainDS.getInstance().getById(id);

	if ( _VelocityMain == null ) {
		return;
	}

	String _dataValue=  String.valueOf(_VelocityMain.getData());
	String _data2Value=  String.valueOf(_VelocityMain.getData2());
	String _titleValue=  String.valueOf(_VelocityMain.getTitle());
	String _ageValue=  String.valueOf(_VelocityMain.getAge());
%> 

<br>
<form name="velocityMainFormEdit" method="post" action="/velocityMainAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data" value="<%=WebUtil.display(_dataValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>data2</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data2" value="<%=WebUtil.display(_data2Value)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>title</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="title" value="<%=WebUtil.display(_titleValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>age</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="age" value="<%=WebUtil.display(_ageValue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.velocityMainFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_VelocityMain.getId()%>">
</form>
