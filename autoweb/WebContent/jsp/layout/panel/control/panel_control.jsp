<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if (panel == null) return;
		
		//=================================================================================================
		
		if ( panel.getSiteId() != site.getId() && panel.getFeedId() == 0 ) return;
		
		boolean isAdminLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());
		

		StyleConfig styleConfig = PanelUtil.getPanelStyleConfig(panel.getId());
		LinkStyleConfig linkStyleConfig = PanelUtil.getPanelLinkStyleConfig(panel.getId());
			
		//menu 
		String menuStyleName = "panel-menu";
		if (panel.getHide() == 1 ) 	menuStyleName="panel-menu-hidden";
		
		// Page view check. 
		String pageName = (String) session.getAttribute("k_page_name");
		Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
%>
<%-- ############################################################################################## --%>
<%-- ################################# Panel Control MENU ######################################### --%>
<% if (isAdminLogin){ %>
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">A</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">E</a>|	
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">S</a>|
		<a href="/v_link_style_config_add.html?prv_panel_id=<%=panel.getId()%>">L</a>|
		<a href="/panelAction.html?del=true&id=<%=panel.getId()%>">D</a>|
		<a href="#" rel="menu:<%=panel.getId() %>" class="popMenu"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a>|
		<a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="configMenu" data-image="/images/icons/arrow_57.gif" data-overimage="/images/icons/arrow_58.gif"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a>
<% if (panel.getHide() == 1) { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=0&ef=true">Show</a>|
<% } else { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
<% } %>
		<%=panel.getId()+"(T" + panel.getPanelType()+"/Sub"+panel.getPanelSubType() +"/Col"+panel.getColumnNum() + ")"%>|
<% if (styleConfig != null) { %>
		<%=styleConfig.getId()%>|
<% } %>
<% if (WebUtil.isTrue(panel.getAdminOnly())) { %>
		<b>Adm</b>|
<% } %>
</div>			
<% } %>
<%-- ############################################################################################## --%>
<%-- ################################# Visbility Control ######################################### --%>
<%	 
	if ( PanelUtil.isHideByPage(panel, dynPage) && isAdminLogin) { 
%>
	<div style="font: normal normal normal 9px verdana;">
		<p>Hide By Page Option</p>
    </div>		
<%
	}
%>
<%	 
	if ( PanelUtil.isHideByPageExclusive(panel, dynPage) && isAdminLogin) { 
%>
	<div style="font: normal normal normal 9px verdana;">
		<p>Hide By PageExclusive Option</b></p>
    </div>		
<%
	}
%>