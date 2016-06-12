<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _commentValue= "";
	String _time_createdValue= "";
%> 

<br>
<form name="forumCommentForm" method="post" action="/forumCommentAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Comment</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="comment" value="<%=_commentValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>TimeCreated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="time_created" value="<%=_time_createdValue%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumCommentForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<%
	if (false) return;

	List list = ForumCommentDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumComment _ForumComment = (ForumComment) iter.next();
%>

<form name="forumCommentForm<%=_ForumComment.getId()%>" method="post" action="/v_forum_comment_edit.html" >
	<%= _ForumComment.getId() %>			
	<a href="javascript:document.forumCommentForm<%=_ForumComment.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumComment.getId() %>">
</form>
<%
	}
%>