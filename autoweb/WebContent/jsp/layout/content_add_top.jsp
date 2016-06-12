<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.content.*,java.util.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	int displaySitePostType = 5;
%>
<% 
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");

	// getSite ads
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List allAds = SitePostDS.getInstance().getByPageId(99);
	
	// get page ads
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);

	List pageAds = SitePostDS.getInstance().getByPageId(99);
	
	allAds.addAll(pageAds);
	
	if ( allAds.size() > 0 ) {
%>
<TABLE border="0"  cellpadding="0" cellspacing="0"> 
	<TR>

<%
	for(Iterator iter = allAds.iterator();iter.hasNext();) {
		SitePost sitePost =(SitePost) iter.next();
		
		if (sitePost.getPostType() != 1 ) // Only ads
			continue;
		String data = WebUtil.display(sitePost.getPostData());
		
%>
<TD>
<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="#ffffff">
	<TR>
<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
		<TD align="left" width="125"> 
			<a href="/t_edit_site_post.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[edit]</font> </a> 
			<a href="/deleteSitePost.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[del]</font> </a> 
		</TD>
<% }%>
	</TR>

	<TR>

		<TD valign="top" width="100%" ><%= data%> </TD>

	</TR>
</TABLE>
</TD>
<%
	}
%>

</TR>
</TABLE>
<%
	}
%>

