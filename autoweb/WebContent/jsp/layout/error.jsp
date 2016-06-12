<%@page language="java" import="com.jtrend.util.*, com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<h1> ERROR OCCURRED </h1>

<%
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	
	String errorMessage = (String) session.getAttribute("k_error_page_msg");
%>

<%= WebUtil.display(errorMessage) %>