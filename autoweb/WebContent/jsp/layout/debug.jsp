<%@page language="java" import="com.jtrend.session.*,com.seox.work.UserBO"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>

<%
	PageView pageView = (PageView) session.getAttribute("k_view_pageview");
	UserBO userBO = (UserBO) session.getAttribute("k_userbo");
	

%>

<TABLE border="0" width="100%" align="center" cellpadding="0" cellspacing="0" bgcolor="#dfdfdf">
	<TR><TD>
		Content Alias=	<%= pageView.getAlias() %>	
		Content Page=	<%= pageView.getContentPage() %>	
	</TD></TR>
	<TR><TD>
	</TD></TR>
</TABLE>
