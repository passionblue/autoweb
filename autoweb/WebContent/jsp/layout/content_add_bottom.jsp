<%@page language="java" import="com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.content.*,java.util.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	int displaySitePostType = 6;
%>
<% 
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List list = SitePostDS.getInstance().getByPageId(99);
	
%>
<TABLE border="0"  cellpadding="0" cellspacing="10"> 
	<TR>

<%
	for(Iterator iter = list.iterator();iter.hasNext();) {
		SitePost sitePost =(SitePost) iter.next();
		
		String data = "";
		
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			
			data = "<a href=\"http://" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a";
		} else {
			data = sitePost.getPostData();
		}
		
%>
<TD>
<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="#ffffff">
	<TR>
<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
		<TD align="left" width=125> 
			<a href="/t_edit_site_post.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[edit]</font> </a> 
			<a href="/deleteSitePost.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[del]</font> </a> 
		</TD>
<% }%>
	</TR>

	<TR>

		<TD valign="top" width="100%" style="border-bottom : 1px #e0e0e0 solid;"><%= data%> </TD>

	</TR>
</TABLE>
</TD>
<%
	}
%>

</TR>
</TABLE>

<% 

	//TODO
	if (true) return;

	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);

	list = SitePostDS.getInstance().getByPageId(99);
	
%>

<%
	for(Iterator iter = list.iterator();iter.hasNext();) {
		SitePost sitePost =(SitePost) iter.next();
		
		String data = "";
		
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			
			data = "<a href=\"http://" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a";
		} else {
			data = sitePost.getPostData();
		}
		
%>

<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="#ffffff">
<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
		<TR><TD align="left" > 
			<a href="/t_edit_site_post.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[edit]</font> </a> 
			<a href="/deleteSitePost.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[del]</font> </a> 
		</TD></TR>
<% }%>
	<TR>
		<TD valign="top" width="100%" style="border-bottom : 1px #e0e0e0 solid;"><%= data%> </TD>
	</TR>
</TABLE>

<%
	}
%>


