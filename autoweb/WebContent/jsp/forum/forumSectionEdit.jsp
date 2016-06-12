<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	ForumSection _ForumSection = ForumSectionDS.getInstance().getById(id);

	if ( _ForumSection == null ) {
		return;
	}

	String _section_titleValue=  String.valueOf(_ForumSection.getSectionTitle());
	String _time_createdValue=  String.valueOf(_ForumSection.getTimeCreated());
%> 

<br>
<form name="forumSectionFormEdit" method="post" action="/forumSectionAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>section_title</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="sectionTitle" value="<%=WebUtil.display(_section_titleValue)%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>time_created</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumSectionFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="edit" value="true">
<INPUT TYPE=HIDDEN NAME="id" value="<%=_ForumSection.getId()%>">
</form>
