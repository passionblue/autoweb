<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ForumComment _ForumComment = ForumCommentDS.getInstance().getById(id);

	if ( _ForumComment == null ) {
		return;
	}

	String _commentValue=  String.valueOf(_ForumComment.getComment());
	String _time_createdValue=  String.valueOf(_ForumComment.getTimeCreated());
%> 

<br>
<form name="forumCommentFormEdit" method="post" action="/forumCommentAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>comment</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="comment" value="<%=WebUtil.display(_commentValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>time_created</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="time_created" value="<%=WebUtil.display(_time_createdValue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumCommentFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_ForumComment.getId()%>">
</form>
