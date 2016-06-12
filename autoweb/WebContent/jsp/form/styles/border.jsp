<%@page language="java" import="com.jtrend.util.*,java.util.*,com.jtrend.util.*,com.autosite.db.*,com.autosite.ds.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<form action="/generalStylesConfig.html" method="post" name="borderStyleForm"> 
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1" cellpadding="1">
	<TR bgcolor="#ffffff">
		<TD width="150" align="right"><b>Color :</b></TD>
		<TD><html:text size="70" property="borderColor" value=""/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD align="right"><b>Width :</b> </TD>
		<TD><html:text size="10" property="borderWidth" value="" /><br/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right"> <b>Style :</b></TD>
		<TD>
	 		<select name="backgroundRepeat">
			<option value="none" selected>None</option>
			<option value="hidden" >Hidden</option>
			<option value="dotted" >Dotted</option>
			<option value="dashed" >Dashed</option>
			<option value="solid" >Solid</option>
			<option value="double" >Double</option>
			<option value="groove" >Groove</option>
			<option value="ridge" >Ridge</option>
			<option value="inset" >Inset</option>
			<option value="outset" >Outset</option>
			<option value="inherit" >Inherit</option>
			</select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.borderStyleForm.submit();">Submit</a> </b> 
		</TD>
	</TR>

</TABLE>
</form>
	