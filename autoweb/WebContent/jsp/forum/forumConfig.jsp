<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

%> 

<br>
<form name="forumConfigForm" method="post" action="/forumConfigAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumConfigForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<%
	if (false) return;

	List list = ForumConfigDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumConfig _ForumConfig = (ForumConfig) iter.next();
%>

<form name="forumConfigForm<%=_ForumConfig.getId()%>" method="post" action="/v_forum_config_edit.html" >
	<%= _ForumConfig.getId() %>			
	<a href="javascript:document.forumConfigForm<%=_ForumConfig.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumConfig.getId() %>">
</form>
<%
	}
%>