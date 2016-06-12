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
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
 
			<html:link page="/oway/test.html" > OWAY TEST</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_jquery_test.html" > JQUERTY TEST</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_ec_product_home.html" > Ec Product</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_content_home.html" > Contents</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_page_config_home.html" > Page Configs </html:link>&nbsp;|&nbsp; 
			<html:link page="/v_link_style_config_home.html" > Link Styles</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_style_config_home.html" > Styles</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_panel_home.html" > Panel</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_forum_category.html" > VM2</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_forum_home.html" > Forum</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_fileup.html" > FileUp Test</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_page.html" > Page Manage</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_page_static_alt_home.html" > PageAlt</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_site_config.html" > Site Config</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_site_post.html" > Add Site Post</html:link>&nbsp;|&nbsp; 
			<html:link page="/logout.html" > <font color="red"  size="2"> <b> Logout </b></font></html:link>
		</TD>
	</TR>
	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
		 	[&nbsp;<html:link page="/appconfig.html?cmd=wp" > <font size="1" ><b>printer</b> </font> </html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=wf" > <font size="1" ><b>full</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=ww" > <font size="1" ><b>wider</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=wn" > <font size="1" ><b>narrower</b></font></html:link>]&nbsp;&nbsp;&nbsp;&nbsp;
			<html:link page="/cscr/main.css" > Scripts </html:link>&nbsp;|&nbsp; 
		</TD>
	</TR>
</TABLE>
