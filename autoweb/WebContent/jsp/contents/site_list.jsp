<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.SessionContext,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<h3> Registered Sites </h3>
<TABLE border="0" bgcolor="#ffffff"  width=100% height="50px" cellpadding="0" cellspacing="0">

<%
	List contents = ContentSilo.getInstance().getSites();

	TreeSet treeSet = new TreeSet(contents);


    for (Iterator iterator = treeSet.iterator(); iterator.hasNext();) {
    String url = (String) iterator.next();
    
%>    
	<TR>
	
		<TD style="border-bottom : 1px #e0e0e0 solid;"> <p><a href="http://<%= url %>" target="_blank" > <%= url %> </a></p></TD>
	</TR>
<%   
    }
%>     
</TABLE> 
     
     