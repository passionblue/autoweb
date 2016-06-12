<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _subjectValue= "";
	String _time_createdValue= "";
%> 

<br>
<form name="forumSubjectForm" method="post" action="/forumSubjectAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Subject</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="subject" value="<%=_subjectValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>TimeCreated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=_time_createdValue%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumSubjectForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = ForumSubjectDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumSubject _ForumSubject = (ForumSubject) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _ForumSubject.getId() %> </td>

    <td> <%= WebUtil.display(_ForumSubject.getSubject()) %></td>
    <td> <%= WebUtil.display(_ForumSubject.getTimeCreated()) %></td> 


<td>
<form name="forumSubjectForm<%=_ForumSubject.getId()%>" method="post" action="/v_forum_subject_edit.html" >
	<a href="javascript:document.forumSubjectForm<%=_ForumSubject.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumSubject.getId() %>">
</form>
</td>
<td>
<a href="/forumSubjectAction.html?del=true&id=<%=_ForumSubject.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>