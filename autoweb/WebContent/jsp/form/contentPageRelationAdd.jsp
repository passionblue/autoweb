<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _content_idValue= WebUtil.display((String)reqParams.get("contentId"));;
	String _page_idValue= WebUtil.display((String)reqParams.get("pageId"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;


%> 

<br>
<form name="contentPageRelationForm" method="post" action="/contentPageRelationAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Content Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="contentId" value="<%=WebUtil.display(_content_idValue)%>"/></TD>
	</TR>
					<TR bgcolor="#ffffff">
		<TD align="right" ><b>Page Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="pageId" value="<%=WebUtil.display(_page_idValue)%>"/></TD>
	</TR>
						<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.contentPageRelationForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = ContentPageRelationDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ContentPageRelation _ContentPageRelation = (ContentPageRelation) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _ContentPageRelation.getId() %> </td>

    <td> <%= WebUtil.display(_ContentPageRelation.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentPageRelation.getPageId()) %></td>
    <td> <%= WebUtil.display(_ContentPageRelation.getTimeCreated()) %></td>


<td>
<form name="contentPageRelationForm<%=_ContentPageRelation.getId()%>" method="post" action="/v_content_page_relation_edit.html" >
	<a href="javascript:document.contentPageRelationForm<%=_ContentPageRelation.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentPageRelation.getId() %>">
</form>
</td>
<td>
<a href="/contentPageRelationAction.html?del=true&id=<%=_ContentPageRelation.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>