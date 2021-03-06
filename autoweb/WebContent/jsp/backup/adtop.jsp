<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.content.*,java.util.*,com.jtrend.struts.core.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>



<!-- DISPLAYING ALL SITE CONTENTS ONLY -->
<%
	int displaySitePostType = 2;
%>
<% 
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List list = null;//SitePostDS.getInstance().getSuperSitePostByPosition(displaySitePostType);
	
%>

<%
	for(Iterator iter = list.iterator();iter.hasNext();) {
		SitePost sitePost =(SitePost) iter.next();
		
		String data = "";
		
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			
			data = "<a href=\"http://" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a";
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



