<%@page language="java" import="com.jtrend.session.SessionContext,com.seox.work.UserBO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
		SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
		String email = "";
		if (loginContext != null && loginContext.getUsername() != null && !loginContext.getUsername().equals("")) {
			email = loginContext.getUsername();
		}
		UserBO userBO = (UserBO) session.getAttribute("k_userbo");
		
%>

<TABLE border="0" bgcolor="#e1ebfd" width="100%" height="20px" cellpadding="0" cellspacing="0" align="left">
	<TR align="left" >
		<TD > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;</TD>
		<TD width="70%" style="border-bottom : 1px #e0e0e0 solid;">
			<html:link page="/v_velocity_main.html" > VM</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_fileup.html" > File Up Test</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_page.html" > Page Manage</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_site_config.html" > Site Config</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_site_post.html" > Add Site Post</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_account.html" > My Account</html:link>&nbsp;|&nbsp; 

<% if (userBO!= null && userBO.getUserObj().getType() == 1) { %>
			<html:link page="/v_admin.html" > <b>ADMIN </b> </html:link>&nbsp;|&nbsp; 
<% }%>			
		</TD>

		<TD width="30%" align="right" style="border-bottom : 1px #e0e0e0 solid;">  Login : <b><%=email%> </b>
		</TD>
	</TR>
</TABLE>
