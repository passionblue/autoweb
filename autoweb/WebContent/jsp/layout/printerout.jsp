<%@ page language="java" import="com.jtrend.session.*,com.seox.work.UserBO"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<% 
	PageView pageView = (PageView) session.getAttribute("k_view_pageview");
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	String topText = (String) session.getAttribute("k_top_text");
	String pageSize = (String) session.getAttribute("k_view_pagesize");
	String pageFull = (String) session.getAttribute("k_view_pagefull");

	String width= pageSize==null?"1000px":pageSize + "px";
	int sideSize = 100;
	int adSize = 120;
	
	width = "800px";
    System.out.println("----------->>> " + pageView.getContentPage());
	boolean adminUser = false;
	UserBO userBO = (UserBO) session.getAttribute("k_userbo");
	if (userBO != null && userBO.getUserObj().getUsername().equals("user@joshua.com")) 
		adminUser = true;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
<title>Rank Tracking Tool</title>

<style>
td {font-family: verdana; margin: 0 1px 0 0; font-size: 11px;}
</style>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="rank,tracking,trend,seo,search,engine,tool,stats">
<meta http-equiv="description" content="">
<link rel="stylesheet" type="text/css" media="screen" href="main.css">

</head>

<BODY topmargin="0">

<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	<TR>
		<TD><jsp:include page="/jsp/layout/header.jsp" /></TD>
	</TR>
	<TR>
		<TD><jsp:include page="/jsp/layout/topmenu.jsp" /></TD>
	</TR>
	
<% 
	if (loginContext != null && loginContext.isLogin()) {
%>
	<TR>
		<TD><jsp:include page="/jsp/layout/submenu.jsp" /></TD>
	</TR>

<% 
	}
%>

</TABLE>

<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	<TR>

		<!-- =================================== -->
		<TD class="v_divider" valign="top" align="center">		
			<table class="v_divider" border="0" bgcolor="#e0e0e0" cellpadding="0" cellspacing="0" width=1 height="600">
				<tr><td></td></tr>
			</table>
		<TD>		
		
		<!-- ############  CONTENT COLUMN ################# -->
		<TD width="100%" valign="top">
			<TABLE border="0" width="100%" cellpadding="5" cellspacing="0" >

<%
	if (topText != null && !topText.equals(""))  {
%>
				<TR><TD valign="top">
				<jsp:include page="/jsp/layout/toptext.jsp" />
				</TD></TR>
<%
	}
%>
				<TR><TD valign="top">
				<jsp:include page="<%=pageView.getContentPage()%>" />
				</TD></TR>
			</TABLE>
		</TD>
		
		<!-- =================================== -->
		
		<TD valign="top" align="center">		
			<table class="v_divider" border="0" bgcolor="#e0e0e0" cellpadding="0" cellspacing="0" width=1 height="600">
				<tr><td> </td></tr>
			</table>
		</TD>		
	</TR>
</TABLE>

<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	<TR><TD>
	<jsp:include page="/jsp/layout/footer.jsp" />
	</TD></TR>
</TABLE>

</BODY>
</html:html>
