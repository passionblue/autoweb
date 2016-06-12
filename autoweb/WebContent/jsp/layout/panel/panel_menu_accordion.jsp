<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	//*** Under Construction. Not working at this moment ***


	SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
	Panel panel = (Panel) session.getAttribute("p_current_panel");
	if ( panel == null) return;

		// Show Hidden, if no showhidden attr, set it true by default. can be turned off with cmd_hideHidden cmd.  
		boolean showHidden = false;
		
		if (session.getAttribute("showHidden") == null) {
			session.setAttribute("showHidden", "true");
			showHidden = true;
		} else {
			String sh = (String) session.getAttribute("showHidden");
			if (sh.equalsIgnoreCase("true")) showHidden = true;
			else showHidden = false;		
		}		

		if (showHidden) {
			String cmdHideHidden = (String) request.getParameter("cmd_hideHidden");
			if (cmdHideHidden != null ) { 
				showHidden = false;
				session.removeAttribute("showHidden");
			}
		} else {
			String cmdShowHidden = (String) request.getParameter("cmd_showHidden");
			if (cmdShowHidden != null ) { 
				showHidden = true;
				session.setAttribute("showHidden", "true");
			}
		}
		
		
		if ( panel.getHide() == 1 && !showHidden) return;

	
	
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
	
	boolean isLogin = (sessionContext != null && sessionContext.isLogin());


	String menuStyleName = "panel-menu";
	if (panel.getHide() == 1 ) 	menuStyleName="panel-menu-hidden";
	
	
	// Page view check. 
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);
		
%>

<!-- ########################################################################################### -->
<!-- ################################# TOP SPACE ############################################### -->

<% if (panel.getTopSpace()>0) { %>
<div class="gapPadding" style="height: <%=panel.getTopSpace() %>">
</div>	
<% } %>

<!-- ########################################################################################### -->
<!-- ################################# Panel Control MENU ###################################### -->

<% if (isLogin){ %>
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">A</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">E</a>|	
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">S</a>|
		<a href="/v_link_style_config_add.html?prv_panel_id=<%=panel.getId()%>">L</a>|
		<a href="/panelAction.html?del=true&id=<%=panel.getId()%>">D</a>|

<% if (panel.getHide() == 1) { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=0&ef=true">Show</a>|
<% }else{ %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
<% } %>
		<%=panel.getId()+"(" + panel.getPanelType()+")"%>
		
</div>			
<% } %>


<!-- ########################################################################################### -->
<!-- ################################# Visbility Control ####################################### -->

<% 
	// If panel is hidden 
	if (panel.getHide() == 1) return;
%>
<%	 
	boolean hideByPageOnlyOption = PanelUtil.isHideByPage(panel, dynPage);		

	if (hideByPageOnlyOption) { 
		if (isLogin) {
%>

<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR>
		<TD> Will Show Only For Page <%= panel.getPageId() %>.</TD>
	</TR>
</TABLE>
<%
		}	 
		return;
	}
%>


<%
	String panelStyleSuffix = PanelUtil.getPanelMenuStyleSuffix(panel.getId());
//	String panelNavStyleSiffix = PanelUtil.getPanelMenuNavStyleSuffix(panel.getId());
	String panelLinkStyleSuffix = PanelUtil.getPanelMenuLinkStyleSuffix(panel.getId());
	
	
	
%>
<div id="side-menu<%=panelStyleSuffix%>">
	<ul id="nav<%=panelStyleSuffix%>">
	<%
		for(Iterator iter = pages.iterator();iter.hasNext();) {
		    Page p = (Page) iter.next();
		    
		    if ( p.getPageName().equals("XHOME")) 
		    	continue;
		    	
		    if ( p.getHide() == 1 ) 
		    	continue; 	
		    
            if ( p.getMenuPanelId() != panel.getId() ) 
                continue;   

			if (p.getHeaderPage() == 1)
				continue;                

			if (p.getParentId() > 0) 
				continue;
                
            String activeMenuClass = "";                
			if ( p.getId() == pageId) {
				activeMenuClass = "class=\"active\"";
			}  
			
			String pageIdDisplay = (isLogin?"(" + p.getId() + ")":"");
			
			List subMenus = DynMenuManager.getInstance().getSubMenus(p.getId());
	%>
		<li id="nav-item<%=panelLinkStyleSuffix %>"><a <%=activeMenuClass %> href="/m_<%=p.getPageName()%>.html"><%= p.getPageMenuTitle() + pageIdDisplay%></a>
		
	<%
		// Sub menus second level
		if ( subMenus.size() >0) {
	%>
			<ul>
	<%
			for(Iterator iter2 = subMenus.iterator();iter2.hasNext();) {
			    Page sp = (Page) iter2.next();
				pageIdDisplay = (isLogin?"(" + sp.getId() + ")":"");

				List subMenus3 = DynMenuManager.getInstance().getSubMenus(sp.getId());

	%>
				<li id="nav-item<%=panelLinkStyleSuffix %>"><a <%=activeMenuClass %> href="/m_<%=sp.getPageName()%>.html"><%= sp.getPageMenuTitle() + pageIdDisplay%></a>

	<%
				// Sub menus third level
				if ( subMenus3.size() >0) {
	%>
					<ul>
	<%
					for(Iterator iter3 = subMenus3.iterator();iter3.hasNext();) {
					    Page sp3 = (Page) iter3.next();
						pageIdDisplay = (isLogin?"(" + sp3.getId() + ")":"");
	%>
						<li id="nav-item<%=panelLinkStyleSuffix %>"><a <%=activeMenuClass %> href="/m_<%=sp3.getPageName()%>.html"><%= sp3.getPageMenuTitle() + pageIdDisplay%></a></li>
	<%
					}
	%>
					</ul>
	<%
				}
	%>			
				</li>
	<%
			}
	%>
			</ul>
	<%
		}
	%>			
		</li>
	<%
	}		
	%>
	</ul>		
</div>
 
<% if (panel.getBottomSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getBottomSpace()%>">
	</TD></TR>
</TABLE>
<% } %>

