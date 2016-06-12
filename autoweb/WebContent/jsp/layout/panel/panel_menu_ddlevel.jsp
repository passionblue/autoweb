<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
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
//	long pageId  = (dynPage!=null?dynPage.getId():0);
	
	// Check if this panel is hidden by page view option for panel. 
//	boolean hideByPageOnlyOption = false;		
//	if (panel.getPageOnly() == 1 && panel.getPageId() != pageId){
//		hideByPageOnlyOption = true;	
//	}
	
		
%>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% if (panel.getTopSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getTopSpace()%>">
	</TD></TR>
</TABLE>
<% } %>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% if (isLogin){ %>
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">A</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">E</a>|	
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">S</a>|

<% if (panel.getHide() == 1) { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=0&ef=true">Show</a>|
<% }else{ %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
<% } %>
		<%=panel.getId()+"(" + panel.getPanelType()+")"%>
</div>			
<% } %>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% 
	// If panel is hidden 
	if (panel.getHide() == 1) return;
%>
<%	 
	long pageId  = (dynPage!=null?dynPage.getId():0);
	
	// Check if this panel is hidden by page view option for panel. 
	boolean hideByPageOnlyOption = false;		
	if (panel.getPageOnly() == 1 && panel.getPageId() != pageId){
		if (!PanelUtil.isSameGroupView(panel, dynPage.getId()))
			hideByPageOnlyOption = true;	
	}

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

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->



<%
	String panelStyleSuffix = PanelUtil.getPanelMenuStyleSuffix(panel.getId());
//	String panelNavStyleSiffix = PanelUtil.getPanelMenuNavStyleSuffix(panel.getId());
	String panelLinkStyleSuffix = PanelUtil.getPanelMenuLinkStyleSuffix(panel.getId());
	
	
	List subMenus = new ArrayList();
	
%>
<div id="ddsidemenubar<%= panel.getId()%>" class="markermenu">
	<ul >
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

			List sm = DynMenuManager.getInstance().getSubMenus(p.getId());
			String relStr = "";
			if ( sm.size()>0) {
				subMenus.add(p);
				relStr = "ddsubmenuside-" + p.getId();
			}			
	%>
		<li ><a <%=activeMenuClass %> href="/m_<%=p.getPageName()%>.html" rel="<%=relStr%>" ><%= p.getPageMenuTitle() + pageIdDisplay%></a>
		</li>
		
	<%
	}		
	%>
	</ul>		
</div>

<script type="text/javascript">
ddlevelsmenu.setup("ddsidemenubar<%= panel.getId()%>", "sidebar") //ddlevelsmenu.setup("mainmenuid", "topbar|sidebar")
</script>

<%
	// Sub menus second level
	if ( subMenus.size() >0) {
%>
<%
		for(Iterator iter2 = subMenus.iterator();iter2.hasNext();) {
		    Page sp = (Page) iter2.next();
			String pageIdDisplay = (isLogin?"(" + sp.getId() + ")":"");

			List subMenus3 = DynMenuManager.getInstance().getSubMenus(sp.getId());

%>
		<ul id="ddsubmenuside-<%=sp.getId() %>" class="ddsubmenustyle blackwhite">

<%
			// Sub menus third level
			if ( subMenus3.size() >0) {
%>
<%
				for(Iterator iter3 = subMenus3.iterator();iter3.hasNext();) {
				    Page sp3 = (Page) iter3.next();
					pageIdDisplay = (isLogin?"(" + sp3.getId() + ")":"");
					
					List subMenus4 = DynMenuManager.getInstance().getSubMenus(sp3.getId());
					
%>
					<li ><a  href="/m_<%=sp3.getPageName()%>.html"><%= sp3.getPageMenuTitle() + pageIdDisplay%></a>
					
					

	<%
				// Sub menus third level
				if ( subMenus4.size() >0) {
	%>
					<ul>
	<%
					for(Iterator iter4 = subMenus4.iterator();iter4.hasNext();) {
					    Page sp4 = (Page) iter4.next();
						pageIdDisplay = (isLogin?"(" + sp4.getId() + ")":"");
	%>
						<li ><a href="/m_<%=sp4.getPageName()%>.html"><%= sp4.getPageMenuTitle() + pageIdDisplay%></a></li>
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
<%
			}
%>			
<%
		}
%>
		</ul>
<%
	}
%>			

<% if (panel.getBottomSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getBottomSpace()%>">
	</TD></TR>
</TABLE>
<% } %>

