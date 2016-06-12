<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ForumPost _ForumPost = ForumPostDS.getInstance().getById(id);

	if ( _ForumPost == null ) {
		return;
	}

	String _dataValue=  String.valueOf(_ForumPost.getData());
	String _time_createdValue=  String.valueOf(_ForumPost.getTimeCreated());
	String _time_updatedValue=  String.valueOf(_ForumPost.getTimeUpdated());
%> 

<br>
<form name="forumPostFormEdit" method="post" action="/forumPostAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>data</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="data" value="<%=WebUtil.display(_dataValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>time_created</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>time_updated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumPostFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_ForumPost.getId()%>">
</form>
