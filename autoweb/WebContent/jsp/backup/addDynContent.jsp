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
		    Source Name <br>
			<input type="text" size="80" name="sourceName"><br>
		    Source Url <br>
			<input type="text" size="80" name="sourceUrl"><br>
			Image Url <br>
			<input type="text" size="50" name="imageUrl">
			Image Height 
			<input type="text" size="5" name="imageHeight">
			Image Width 
			<input type="text" size="5" name="imageWidth"> <br>

			<input type="checkbox" name="html"> Content in HTML <br><br>

			<INPUT TYPE=HIDDEN NAME="pid" value="<%=pageId%>">
			
			<select name="contentType">
			<option value="0">Choose content type</option>
			<option value="1">ImageLeft</option>
			<option value="2">Product(NOT READY)</option>
			<option value="3">Ads(NOT READY)</option>
			<option value="4">Link(NOT READY)</option>
			</select> <br> 
			
			<b><a href="javascript:document.addDynContentForm.submit();">Submit</a> </b>|
			<b><a href="/home.html">Cancel</a> </b>
			
		</html:form>
	</body>
</html>

