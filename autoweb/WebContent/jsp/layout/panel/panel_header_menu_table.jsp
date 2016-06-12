<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
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
%>

<% if (isLogin){ %>
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">A</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">E</a>|	
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">S</a>|
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
<!-- ########################################################################################### -->

<% 
	// If panel is hidden 
	if (panel.getHide() == 1) return;
%>
<%	 
	// Check if this panel is hidden by page view option for panel. 
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);
	boolean hideByPageOnlyOption = false;		

	if (panel.getPageOnly() == 1 && panel.getPageId() != pageId){
		hideByPageOnlyOption = true;	
	}

	if (hideByPageOnlyOption) { 
%>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR>
		<TD> Will Show Only For Page <%= panel.getPageId() %></TD>
	</TR>
</TABLE>
<%	 
		return;
	}
%>


<div id="header-menu" >
	<ul id="nav">
	<%
		for(Iterator iter = pages.iterator();iter.hasNext();) {
		    Page p = (Page) iter.next();
		    
		    if ( p.getPageName().equals("XHOME")) 
		    	continue;
		    	
		    if ( p.getHide() == 1 ) 
		    	continue; 	
		    
            if ( p.getMenuPanelId() != panel.getId() ) 
                continue;   
	%>
	
		<li id="nav-item"><a href="/m_<%=p.getPageName()%>.html" id="m-uvod">&nbsp;&nbsp;<%= p.getPageMenuTitle() %>&nbsp;&nbsp;</a></li>	

	<%
	}
	%>
	
	</ul>		

</div>
