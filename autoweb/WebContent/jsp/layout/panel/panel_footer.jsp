<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
		SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if (panel == null) return;
		
		if ( panel.getHide() == 1) return;
		
		
		boolean isLogin = (sessionContext != null && sessionContext.isLogin());
		int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());

		List contents = ContentDS.getInstance().getByPageId(panel.getId());
		List sitePosts = SitePostDS.getInstance().getByPanelId(panel.getId());
		
		if (sitePosts.size() == 0 && !isLogin) return;
		
		String style = panel.getStyleString();
		
		if (style == null || style.length()==0) style = "border-width: 1px; border-style: solid";
		
		boolean isVerticalAlign = (panel.getAlign() == 0);
		
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
	    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

		if ( panel.getSiteId() != site.getId()) return;
	
		if (siteConfig ==null) {
			siteConfig = SiteConfigDS.getDefaultSiteConfig();  
		}
	
		String imageUrl = WebUtil.getUrlFormat(siteConfig.getHeaderImage());
		
		if (WebUtil.isNull(imageUrl)) {
			imageUrl = WebUtil.getUrlFormat(WebUtil.getRandomHeaderImage()); 
		}
	
		//String defaultAd="<a href=\"http://www.jdoqocy.com/click-2245860-10443218\" target=\"_top\"><img src=\"http://www.ftjcfx.com/image-2245860-10443218\" width=\"120\" height=\"90\" alt=\"hotels.com\" border=\"0\"/></a>";
		String defaultAd="<a href=\"http://www.dpbolvw.net/click-2245860-10379078\" target=\"_top\"><img src=\"http://www.tqlkg.com/image-2245860-10379078\" width=\"468\" height=\"60\" alt=\"Go Daddy  $6.95 .com sale 468x60\" border=\"0\"/></a>";
		
		// Visiblity for printable
		if (WebUtil.isPritintable(request)){
			if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
				return;
		} else {
			if (panel.getShowOnlyPrint() == 1)
				return;
		}	

%>


<% if (isLogin){ %>
<div id="panel-menu"  class="panel-menu">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">Add</a> | 
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">Edit</a> |	
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
		<a href="/panelAction.html?del=true&id=<%=panel.getId()%>">D</a>|
		<%=panel.getId()%>
</div>			
<% } %>

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
