<%@page language="java" import="com.jtrend.util.*,java.util.*,com.jtrend.util.*,com.jtrend.session.*,com.autosite.db.*,com.autosite.ds.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String returnPage = request.getParameter("returnPage");

%>

<form action="/generalStylesConfig.html" method="post" name="backgroundStyleForm"> 
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1" cellpadding="1">
	<TR bgcolor="#ffffff">
		<TD width="150" align="right"><b>Color :</b></TD>
		<TD><html:text size="70" property="bgColor" value=""/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD align="right"><b>Image Url :</b> </TD>
		<TD><html:text size="70" property="bgImage" value="" /><br/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right"> <b>Repeat :</b></TD>
		<TD>
	 		<select name="bgRepeat">
			<option value="" ></option>
			<option value="no-repeat" selected>No Repeat</option>
			<option value="repeat" >Repeat</option>
			<option value="repeat-x" >Repeat X</option>
			<option value="repeat-y" >Repeat Y</option>
			<option value="inherit" >Inherit</option>
			</select>
		</TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right"><b>Attachment :</b> </TD>
		<TD>
	 		<select name="bgAttach">
			<option value="" ></option>
			<option value="scroll"  selected>Scroll</option>
			<option value="fixed" >Fixed</option>
			<option value="inherit" >Inherit</option>
			</select>
		</TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right"><b>Position :</b> </TD>
		<TD>
	 		<select name="bgPosition">
			<option value="" ></option>
			<option value="top left" >Top Left</option>
			<option value="top center"  selected >Top Center</option>
			<option value="top right" >Top Right</option>
			<option value="center left" >Center Left</option>
			<option value="center center" >Center Center</option>
			<option value="center right" >Center Right</option>
			<option value="bottom left" >Bottom Left</option>
			<option value="bottom center" >Bottom Center</option>
			<option value="bottom right" >Bottom Right</option>
			<option value="bottom right" >Bottom Right</option>
			<option value="position" >Bottom Right</option>
			<option value="inherit" >Bottom Right</option>
			</select>
			<html:text size="10" property="bgPosition_x" value="" />
			<html:text size="10" property="bgPosition_y" value="" />
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.backgroundStyleForm.submit();">Submit</a> </b> 
		</TD>
	</TR>
</TABLE>

<INPUT TYPE="HIDDEN" NAME="act" value="updateBackground">
<INPUT TYPE="HIDDEN" NAME="styleConfigId" value="menuPanel">
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=WebUtil.display(returnPage)%>">


</form>
	