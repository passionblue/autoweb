<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.SessionContext,com.autosite.content.*,com.autosite.db.*,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	if (siteConfig ==null) {
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}

	String imageUrl = WebUtil.getUrlFormat(siteConfig.getHeaderImage());
	
	if (WebUtil.isNull(imageUrl)) {
		imageUrl = WebUtil.getUrlFormat(WebUtil.getRandomHeaderImage()); 
	}

	//String defaultAd="<a href=\"http://www.jdoqocy.com/click-2245860-10443218\" target=\"_top\"><img src=\"http://www.ftjcfx.com/image-2245860-10443218\" width=\"120\" height=\"90\" alt=\"hotels.com\" border=\"0\"/></a>";
	String defaultAd="<a href=\"http://www.dpbolvw.net/click-2245860-10379078\" target=\"_top\"><img src=\"http://www.tqlkg.com/image-2245860-10379078\" width=\"468\" height=\"60\" alt=\"Go Daddy  $6.95 .com sale 468x60\" border=\"0\"/></a>";
%>


<TABLE border="0" bgcolor="#ffffff"  width="100%"  cellpadding="0" cellspacing="0">
	<TR>
		<% if (WebUtil.isNull(imageUrl)) { %>
			<TD bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;"> <font size="6" color="orange" face="Verdana"> <%= request.getServerName() %> </font></TD>
		<% } else { %>
			<TD bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;"> <img height="<%=WebUtil.display(siteConfig.getHeaderImageHeight(), 60) %>" width="<%= WebUtil.display(siteConfig.getHeaderImageWidth(), 532)%>" src="<%=imageUrl %>"></TD>
		<% } %>

		<TD bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="right" valign="top">
		<%= WebUtil.display(siteConfig.getHeaderAd(), defaultAd) %>  
		</TD>
	</TR>
</TABLE>

