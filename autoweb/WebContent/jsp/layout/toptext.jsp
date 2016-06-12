<%@ page language="java" import="java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" 	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" 	prefix="html"%>

<% 
	String topText = (String) pageContext.findAttribute("k_top_text");
	String topErrText = (String) pageContext.findAttribute("k_top_error_text");
	
	if (topErrText!=null) {
%>

<font color="red" size=2> <b><%= topErrText %> </b></font><br>

<%
	}
	if (topText!=null) {
%>
<%= topText %>
<%
	}
%>
