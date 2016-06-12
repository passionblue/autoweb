<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.content.*,java.util.*,com.jtrend.struts.core.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>


<% 
	PageView pageView = (PageView) session.getAttribute("k_view_pageview");
	String sitePostPositionCode = (String) session.getAttribute("p_site_post_position");
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");

	boolean loginPage = false;	
	if ( pageView != null && pageView.getContentPage().indexOf("login_form") > 0 ) 
		loginPage = true;
	
%>

<%
	int displaySitePostType = 0;
	if (sitePostPositionCode != null) 
		displaySitePostType = Integer.parseInt(sitePostPositionCode);
%>

<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		


<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="#e0e0e0">
	<TR>
		<TD> POSITION PLACE <%= displaySitePostType %></TD>
	</TR>
</TABLE>

<% } 
%>

<% 

	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	for (int idx = 0; idx <3;idx++) {
	
	
		List list = null;
		if (idx == 0) { // SITE
			list = SitePostDS.getInstance().getByPanelId(99);
		} else if (idx == 1 ) { // PAGE
			String pageName = (String) session.getAttribute("k_page_name");
			Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
			long pageId  = (dynPage!=null?dynPage.getId():0);
		
			list = SitePostDS.getInstance().getByPanelId(99);

		} else if (idx == 2 ) { // CORPORATE
		 	list = SitePostDS.getInstance().getByPanelId(99);
		 
		}
	
%>

<%
	for(Iterator iter = list.iterator();iter.hasNext();) {
		SitePost sitePost =(SitePost) iter.next();
		
		String data = "";
		
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			
			data = "&#149;&nbsp;<a href=\"" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a";
		} else if (sitePost.getPostType() == 5 ) { // LINK TYPE
			int paddingHeight = WebParamUtil.getIntValue(sitePost.getPostData());
			if ( paddingHeight <= 0 ) paddingHeight = 30;
		
		
			data = "<TABLE> <TR><TD height=\"" + paddingHeight + "\"> &nbsp; </TD></TR></TABLE>" ;
		} else {
			data = sitePost.getPostData();
		}
		
%>

<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="#ffffff">
<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
		<TR><TD align="right" > 
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

<%
	}
%>

<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="#ffffff">
	<TR>
		<TD valign="top" width="100%" style="border-bottom : 5px #e0e0e0 solid;"> &nbsp; </TD>
	</TR>
</TABLE>

