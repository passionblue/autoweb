<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	String errorMessage = (String) session.getAttribute("k_error_page_msg");
	
	ConfirmTo confirmTo = (ConfirmTo) request.getAttribute("confTo");
%>

<a href="/confirmTo.html?confTo=<%=confirmTo.getKey()%>"> Confirm This <%= confirmTo %></a>

<h1> Confirm </h1>


