<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ForumConfig _ForumConfig = ForumConfigDS.getInstance().getById(id);

	if ( _ForumConfig == null ) {
		return;
	}

%> 

<br>
<form name="forumConfigFormEdit" method="post" action="/forumConfigAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumConfigFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_ForumConfig.getId()%>">
</form>
