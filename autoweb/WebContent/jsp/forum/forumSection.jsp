<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	String _section_titleValue= "";
	String _time_createdValue= "";
%> 

<br>
<form name="forumSectionForm" method="post" action="/forumSectionAction.html" >
<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>SectionTitle</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="sectionTitle" value="<%=_section_titleValue%>"/></TD>
	</TR>
	

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>TimeCreated</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="timeCreated" value="<%=_time_createdValue%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.forumSectionForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE=HIDDEN NAME="add" value="true">
</form>


<TABLE border="0" width="95%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = ForumSectionDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumSection _ForumSection = (ForumSection) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">
<td> <%= _ForumSection.getId() %> </td>
<td>
<form name="forumSectionForm<%=_ForumSection.getId()%>" method="post" action="/v_forum_section_edit.html" >
	<a href="javascript:document.forumSectionForm<%=_ForumSection.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumSection.getId() %>">
</form>
</td>
<td>
<a href="/forumSectionAction.html?del=true&id=<%=_ForumSection.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>