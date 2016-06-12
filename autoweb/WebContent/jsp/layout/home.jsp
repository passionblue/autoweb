<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<TABLE border="0" bgcolor="#ffffff"  width=100% height="50px" cellpadding="0" cellspacing="0">
<%
	List contents = ContentSilo.getInstance().getContents(request.getServerName());

    for (Iterator iterator = contents.iterator(); iterator.hasNext();) {
    
%>    
	<TR>
	
		<TD style="border-bottom : 1px #e0e0e0 solid;"> <p><%= iterator.next() %> </p></TD>
	</TR>
<%   
    }
%>     
</TABLE> 
     
<html:link page="/t_add_content.html" > add </html:link>
     