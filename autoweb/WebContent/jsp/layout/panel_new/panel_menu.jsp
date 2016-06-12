<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	Panel panel = (Panel) session.getAttribute("p_current_panel");
	if ( panel == null) {
		JspUtil.getLogger().error("** Panel not found in session **");
		return;
	}
	
	boolean isAdminLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());

    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	if (siteConfig ==null) {
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}
	
	String lineColor = WebUtil.display(siteConfig.getMenuLineColor(), "#e0e0e0");
	String frontColor = WebUtil.display(siteConfig.getMenuFrontColor(), "orange");
	
	// Page view check. 
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);
		
	
	//=================================================================================================
	// Visiblity for printable
	//=================================================================================================
	if (WebUtil.isPritintable(request)){
		if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
			return;
	} else {
		if (panel.getShowOnlyPrint() == 1)
			return;
	}	

	//=================================================================================================
	// Admin Only Check
	//=================================================================================================
	
	if ( WebUtil.isTrue(panel.getAdminOnly()) && !sessionContext.isSuperAdminLogin()){
		return;
	}

	boolean isPartRefresh = WebUtil.isPartDisplay(request);
	if ( !isPartRefresh) {
%>
<%-- ############################################################################################## --%>
<%-- ################################# TOP SPACE ################################################## --%>
<% if (panel.getTopSpace()>0 && !isPartRefresh) { %>
	<div class="gapPadding" style="height: <%=panel.getTopSpace() %>">
	</div>	
<% } %>
<%-- ############################################################################################## --%>
<%-- ################################# PANEL CONTROL MENU ######################################### --%>
<% 	if (isAdminLogin){ %>
	<jsp:include page="/jsp/layout/panel/control/panel_control.jsp" />
<%	} %>
<%-- ############################################################################################## --%>
<%-- ################################# VISIBILITY CONTROL ######################################### --%>
<% 
	if ( WebUtil.isTrue(panel.getHide()) || 
	     PanelUtil.isHideByPage(panel, dynPage) ||
	     PanelUtil.isHideByPageExclusive(panel, dynPage)) {
	     
		return;
	}
%>
<%-- ############################################################################################## --%>
<%-- ############################################################################################## --%>
<% 	if (isAdminLogin){ %>
	<a style="font-size: 9px" href="/v_panel_menu_reorder.html?panelId=<%=panel.getId() %>"> Reorder </a>|
	<a style="font-size: 9px" href="/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=panelId,title,data,targetType&forcehiddenlist=panelId,targetType&panelId=<%=panel.getId()%>&targetType=1" rel="facebox"> Link</a>|
	<a style="font-size: 9px" href="#" rel="LoadPageAdd:ajaxMenuPageItemFormBlock3:<%=panel.getId() %>" class="popClickMenu">Page</a>|
	<a style="font-size: 9px" href="#" rel="LoadHtml:ajaxMenuPageItemFormBlock" class="popMenu"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a>|
	<a style="font-size: 9px" href="#" rel="<%=panel.getId()%> " class="refreshPanel"><img width="12px" src="/images/icons/led/arrow_refresh.png" style="border-width:0" /></a>|

<%	} %>

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
		String panelTitleStyleSuffix = PanelUtil.getPanelTitleStyleSuffix(panel);
%>
	<div id="panel-title<%=panelTitleStyleSuffix %>" style="<%=panel.getTitleStyleString()%>" class="panel-title">
	<%= panel.getPanelTitle() %>
	</div>
<% } %>


<%
	}// isPartRefresh

	String panelStyleSuffix 	= PanelUtil2.getPanelStyleSuffix(panel); 
	String panelLinkStyleSuffix = PanelUtil2.getPanelLinkStyleSuffix(panel);
	String panelTitleStyleSuffix = PanelUtil.getPanelTitleStyleSuffix(panel);
	
%>
<div id="panel-container<%="-"+panel.getId()%>" style="display:block" class="panel-container" >
<div id="side-menu<%=panelStyleSuffix%>" class="side-menu" >

<%
	PageDS pageDS = PageDS.getInstance();
	List menuItems = PanelMenuOrderUtil.resetOrderedMenuList(panel);
	if ( menuItems != null && menuItems.size()  > 0) {
%>
	<ul id="nav<%=panelStyleSuffix%>" class="nav-list">
<%
		
		for(Iterator iter = menuItems.iterator();iter.hasNext();) {

		    MenuItem mi = (MenuItem) iter.next();
            String activeMenuClass = "";                
			String menuTitle = "";
			String link = "";
			
		    if (mi.getTargetType() == MenuItemUtil.TYPE_PAGE){ 
		        Page p = pageDS.getById(mi.getPageId());
				if ( p == null) 
				    continue;
				
		    	if ( p.getPageName().equals("XHOME")) 
			    	continue;

			    if ( p.getHide() == 1 ) 
			    	continue; 	

				if (p.getHeaderPage() == 1)
					continue;                

				if (p.getParentId() > 0) 
					continue;

				if ( p.getId() == pageId) {
					activeMenuClass = "class=\"active\"";
				}  
				link = "/m_"+p.getPageName()+".html";
				menuTitle = p.getPageMenuTitle() + (isAdminLogin?"(" + p.getId() + ")":"");
				
		    }else if ( (mi.getTargetType() == MenuItemUtil.TYPE_LINK)) {
				menuTitle = mi.getTitle()+ (isAdminLogin?"(link)":"");;
				link = mi.getData();
		    } else if ( (mi.getTargetType() == MenuItemUtil.TYPE_CONTENT)) {
		    }
	%>
		<li id="nav-item<%=panelLinkStyleSuffix %>" rel="<%=mi.getId() %>" class="nav-item">
			<a <%=activeMenuClass %> href="<%=link%>"><%= menuTitle %> 
		<%	if (isAdminLogin){ %>			
				<img class="generalDeleteItem" style="display:inline; width:10px" src="/images/icons/led/cross.png"/>
		<%	} %>			
			</a>
		</li>
	<%
	}		
	%>
	</ul>		
<%
	} //if ( no menuItems)
%>	
</div>
</div>
<% if (panel.getBottomSpace()>0 && !isPartRefresh) {  %>
	<div class="gapPadding" style="height: <%=panel.getBottomSpace() %>">
	</div>	
<% } %>