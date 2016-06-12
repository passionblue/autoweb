<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _dataValue= "";
	String _data2Value= "";
	String _titleValue= "";
	String _ageValue= "";
%> 

<br>
<form name="velocityMainForm" method="post" action="/velocityMainAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data" value="<%=_dataValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>data2</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data2" value="<%=_data2Value%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>title</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="title" value="<%=_titleValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>age</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="age" value="<%=_ageValue%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.velocityMainForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<%
	if (false) return;

	List list = VelocityMainDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		VelocityMain _VelocityMain = (VelocityMain) iter.next();
%>

<form name="velocityMainForm<%=_VelocityMain.getId()%>" method="post" action="/v_velocity_main_edit.html" >
	<%= _VelocityMain.getId() %>			
	<a href="javascript:document.velocityMainForm<%=_VelocityMain.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _VelocityMain.getId() %>">
</form>
<%
	}
%>