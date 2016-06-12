<%@page language="java" import="com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.content.*,java.util.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	int displaySitePostType = 2; // right add
%>
<% 
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	String pageName = (String) session.getAttribute("k_page_name");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);


	List list = SitePostDS.getInstance().getByPageId(999);
	
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
