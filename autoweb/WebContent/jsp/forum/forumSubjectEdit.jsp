<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ForumSubject _ForumSubject = ForumSubjectDS.getInstance().getById(id);

	if ( _ForumSubject == null ) {
		return;
	}

	String _subjectValue=  String.valueOf(_ForumSubject.getSubject());
	String _time_createdValue=  String.valueOf(_ForumSubject.getTimeCreated());
%> 

<br>
<form name="forumSubjectFormEdit" method="post" action="/forumSubjectAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>subject</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="subject" value="<%=WebUtil.display(_subjectValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>time_created</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumSubjectFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_ForumSubject.getId()%>">
</form>
