<%@page language="java" import="com.jtrend.session.SessionContext"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
		SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
%>

<TABLE border="0" width="100%" height="20px" cellpadding="0" cellspacing="0" align="left">
	<TR align="left" >
		<TD width="50%" align="left" align="right" style="border-bottom : 1px #e0e0e0 solid;"> 
			<a href="/home.html" > <b>Home</b></a>&nbsp;&#149;&nbsp;
			<html:link page="/t_aboutus.html" > <b>About Us</b></html:link>&nbsp;&#149;&nbsp;
			<html:link page="/t_contact.html" ><b>Contact</b></html:link> &nbsp;&#149;&nbsp;
			<html:link page="/t_open_site_list.html" ><b>Sites</b></html:link> 
			
		</TD>

		<TD width="50%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
<!-- 
		 	[&nbsp;<html:link page="/appconfig.html?subact=wp" > <font size="1" ><b>printer</b> </font> </html:link>&nbsp;|
		 	<html:link page="/appconfig.html?subact=wf" > <font size="1" ><b>full</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?subact=ww" > <font size="1" ><b>wider</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?subact=wn" > <font size="1" ><b>narrower</b></font></html:link>]&nbsp;&nbsp;&nbsp;&nbsp;
 -->		
<% if (loginContext != null && loginContext.isLogin()) { %>		
			<!-- <html:link page="/t_register_form.html" > <b>Register</b></html:link>&nbsp;&#149;&nbsp; -->
			<html:link page="/v_panel_list.html" > Panel</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_forum_category.html" > VM2</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_forum_home.html" > Forum</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_fileup.html" > FileUp Test</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_page.html" > Page Manage</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_site_config.html" > Site Config</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_site_post.html" > Add Site Post</html:link>&nbsp;|&nbsp; 
			
			<html:link page="/logout.html" > <font color="red"  size="2"> <b> Logout </b></font></html:link>
<% }else { %>		
			<html:link page="/t_register_form.html" > <b>Register</b></html:link>&nbsp;&#149;&nbsp;
			<html:link page="/t_login_form.html" > <font color="red" size="2" ><b>Login</b> </font> </html:link>
<% } %>		
		</TD>
	</TR>
</TABLE>
