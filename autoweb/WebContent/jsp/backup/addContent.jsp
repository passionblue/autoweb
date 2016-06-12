<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>AddContentForm form</title>
	</head>
	<body>
	    <h2> Add content</h2>
		<html:form action="/addContent">
			<TEXTAREA NAME="content" COLS=90 ROWS=20 ></TEXTAREA><br>
			<input type="checkbox" name="html"> Content in HTML <br> <br>
			<b><a href="javascript:document.addContentForm.submit();">Submit</a> </b>
		</html:form>
	</body>
</html>

