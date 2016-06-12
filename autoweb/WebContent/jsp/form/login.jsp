<%@ page language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
 
	<html:form action="/loginFormSubmit" method="post">
		<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
			<TR bgcolor="#ffffff" >
				<TD align="left"> &nbsp;&nbsp;<b>Username &nbsp;&nbsp;&nbsp;&nbsp;</b> 
				<html:text property="username"/> &nbsp;&nbsp;<b><a href="javascript:document.loginform.submit();">Login</a> </b></TD> 
			</TR>
			<TR bgcolor="#ffffff" >
				<TD align="left"> &nbsp;&nbsp;<b>Password &nbsp;&nbsp;&nbsp;&nbsp;</b> 
				 <html:text property="password"/></TD> 
			</TR>
		</TABLE>
	</html:form> <br><br>

