<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<%
	String pageId = (String) session.getAttribute("k_current_dyn_page");
    System.out.println("@@@ " + pageId);
%> 
 
<html> 
	<head>
		<title>JSP for AddDynContentForm form</title>
	</head>
	<body>
		<html:form action="/addDynContent">
			Category <br>
			<input type="text" size="20" name="cat"><br/>
		    Subject <br>
			<input type="text" size="95" name="title"><br/>
			Content <br>
			<TEXTAREA NAME="content" COLS=70 ROWS=20></TEXTAREA><br>
			<input type="checkbox" name="html"> Content in HTML <br> <br>
			<INPUT TYPE=HIDDEN NAME="pid" value="<%=pageId%>">
			
			<select name="cotent_type">
			<option value="0">Choose content type</option>
			<option value="1">Article</option>
			<option value="2">Ads</option>
			<option value="3">Link</option>
			</select> <br> 
			
			<b><a href="javascript:document.addDynContentForm.submit();">Submit</a> </b>|
			<b><a href="/home.html">Cancel</a> </b>
			
		</html:form>
	</body>
</html>

