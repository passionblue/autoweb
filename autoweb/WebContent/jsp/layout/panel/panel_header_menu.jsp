<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.autosite.util.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
		AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if ( panel == null) return;

	
		String pageName = (String) session.getAttribute("k_page_name");
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
		List pages = PageDS.getInstance().getBySiteId(site.getId());
		
		if (pages == null) {
			return;
		} 
	
	
	    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
		if (siteConfig ==null) {
			siteConfig = SiteConfigDS.getDefaultSiteConfig();  
		}
		
		if ( siteConfig.getMenuReverse() == 1) {
			Collections.reverse(pages);
		}
		
		
		String lineColor = WebUtil.display(siteConfig.getMenuLineColor(), "#e0e0e0");
		String frontColor = WebUtil.display(siteConfig.getMenuFrontColor(), "orange");
		
		boolean isAdminLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());


		Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
		long pageId  = (dynPage!=null?dynPage.getId():0);

	//=================================================================================================
	// Admin Only Check
	
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
<% 
	if ( WebUtil.isTrue(panel.getHide()) || 
	     PanelUtil.isHideByPage(panel, dynPage) ||
	     PanelUtil.isHideByPageExclusive(panel, dynPage)) {
	     
		return;
	}
	
%>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% 	if (isAdminLogin){ %>
	<a style="font-weight: 9px" href="/v_panel_menu_reorder.html?panelId=<%=panel.getId() %>"> Reorder </a>|
	<a style="font-weight: 9px" href="/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=panelId,title,data,targetType&forcehiddenlist=panelId,targetType&panelId=<%=panel.getId()%>&targetType=1" rel="facebox"> AddLink</a>|
	<a style="font-size: 9px" href="#" rel="LoadPageAdd:ajaxMenuPageItemFormBlock3:<%=panel.getId() %>" class="popClickMenu">Page</a>|
	<a style="font-size: 9px" href="#" rel="LoadHtml:ajaxMenuPageItemFormBlock" class="popMenu"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a>|
	<a style="font-size: 9px" href="#" rel="<%=panel.getId()%> " class="refreshPanel"><img width="12px" src="/images/icons/led/arrow_refresh.png" style="border-width:0" /></a>
	
<%	} %>

<%

	}// isPartRefresh
	String panelIdSuffix = PanelUtil.getPanelMenuStyleSuffix(panel);
	String panelLinkStyleSuffix = PanelUtil.getPanelMenuLinkStyleSuffix(panel.getId());
%>

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
		String panelTitleStyleSuffix = PanelUtil.getPanelTitleStyleSuffix(panel);
%>
	<div class="panelTitle<%=panelTitleStyleSuffix %>" style="<%=panel.getTitleStyleString()%>"><%= panel.getPanelTitle() %></div>
<% } %>

<div id="panel-container-<%=panel.getId()%>" style="display:block">
<div id="header-menu<%=panelIdSuffix%>" >
	<ul id="nav<%=panelIdSuffix%>" style="overflow: auto;" >
	<%
	
		List menuItems = PanelMenuOrderUtil.resetOrderedMenuList(panel);
	
		for(Iterator iter = menuItems.iterator();iter.hasNext();) {
		    MenuItem mi = (MenuItem) iter.next();
            String activeMenuClass = "";                
			String menuTitle = "";
			String link = "";

			System.out.println("XXX");
		    JspUtil.getLogger().debug("Menu Item for " + panel.getId() + " : " + mi.getTargetType());
			
			if (mi.getTargetType() == MenuItemUtil.TYPE_PAGE){ 
				
			    //Page p = (Page) iter.next();
			    Page p = PageDS.getInstance().getById(mi.getPageId());
			    
			    
				if ( p == null) 
				    continue;
			    
			    if ( p.getPageName().equals("XHOME")) 
			    	continue;
			    	
			    if ( p.getHide() == 1 ) 
			    	continue; 	
			    
//	            if ( p.getMenuPanelId() != panel.getId() ) 
//	                continue;   
	                
				if (p.getHeaderPage() == 1)
					continue;                

				if (p.getParentId() > 0) 
					continue;
				
				if ( p.getId() == pageId) {
					activeMenuClass = "class=\"active\"";
				}  
				link = "/m_"+p.getPageName()+".html";
				menuTitle = p.getPageMenuTitle() + (isAdminLogin?"(" + p.getId() + ")":"");
				
				String pageIdDisplay = (isAdminLogin?"(" + p.getId() + ")":"");              

		    }else if ( (mi.getTargetType() == MenuItemUtil.TYPE_LINK)) {
				menuTitle = mi.getTitle()+ (isAdminLogin?"(link)":"");;
				link = mi.getData();
		    } else if ( (mi.getTargetType() == MenuItemUtil.TYPE_CONTENT)) {
		    }
			
			
	%>
		<li id="nav-item<%=panelIdSuffix%>" rel="<%=mi.getId() %>">
			<a <%=activeMenuClass %> href="<%=link%>" ><%= menuTitle %>
		<%	if (isAdminLogin){ %>			
			<img class="generalDeleteItem" style="display:inline; width:10px" src="/images/icons/led/cross.png"/>
		<%	} %>
			</a>			
		</li>
	<%
	}
	%>
	</ul>		
</div> <div class="clear"></div>
</div>
<% if (panel.getBottomSpace()>0 && !isPartRefresh ) {  %>
	<div class="gapPadding" style="height: <%=panel.getBottomSpace() %>">
	</div>	
<% } %>
