<%@ page language="java" import="com.jtrend.util.*,java.util.*" %>


<% 


	String topText = (String) pageContext.getAttribute("k_top_text");
	String topErrText = (String) pageContext.getAttribute("k_top_error_text");
	
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
