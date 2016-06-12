<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ForumCategory _ForumCategory = ForumCategoryDS.getInstance().getById(id);

	if ( _ForumCategory == null ) {
		return;
	}

	String _categoryValue=  String.valueOf(_ForumCategory.getCategory());
	String _time_createdValue=  String.valueOf(_ForumCategory.getTimeCreated());
%> 

<br>
<form name="forumCategoryFormEdit" method="post" action="/forumCategoryAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>category</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="category" value="<%=WebUtil.display(_categoryValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>time_created</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumCategoryFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_ForumCategory.getId()%>">
</form>
