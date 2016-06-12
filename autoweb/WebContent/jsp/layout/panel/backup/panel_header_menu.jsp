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
		
		boolean isLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());


		Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
		long pageId  = (dynPage!=null?dynPage.getId():0);

	//=================================================================================================
	// Admin Only Check
	
	if ( WebUtil.isTrue(panel.getAdminOnly()) && !sessionContext.isSuperAdminLogin()){
		return;
	}
					
					
%>

<!-- ########################################################################################### -->
<!-- ################################# TOP SPACE ############################################### -->

<% if (panel.getTopSpace()>0) { %>
<div class="gapPadding" style="height: <%=panel.getTopSpace() %>">
</div>	
<% } %>

<!-- ########################################################################################### -->
<!-- ################################# Panel Control MENU ###################################### -->

<% 	if (isLogin){ %>
<jsp:include page="/jsp/layout/panel/control/panel_control.jsp" />
<%	} %>

<!-- ########################################################################################### -->
<!-- ################################# Visbility Control ####################################### -->

<% 
	if ( WebUtil.isTrue(panel.getHide()) || 
	     PanelUtil.isHideByPage(panel, dynPage) ||
	     PanelUtil.isHideByPageExclusive(panel, dynPage)) {
	     
		return;
	}
	
%>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<%
	String panelIdSuffix = PanelUtil.getPanelMenuStyleSuffix(panel);
//	String panelNavStyleSiffix = PanelUtil.getPanelMenuNavStyleSuffix(panel.getId());
	String panelLinkStyleSuffix = PanelUtil.getPanelMenuLinkStyleSuffix(panel.getId());
%>

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
%>
	<div style="<%=panel.getTitleStyleString()%>"><%= panel.getPanelTitle() %></div>
<% } %>


<div id="header-menu<%=panelIdSuffix%>" >
	<ul id="nav<%=panelIdSuffix%>">
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
                
			String activeMenuClass = "";                
			if ( p.getId() == pageId) {
				activeMenuClass = "class=\"active\"";
			}                
			String pageIdDisplay = (isLogin?"(" + p.getId() + ")":"");              
                
	%>
	
		<li id="nav-item<%=panelIdSuffix%>"><a <%=activeMenuClass %> href="/m_<%=p.getPageName()%>.html" ><%= p.getPageMenuTitle() + pageIdDisplay%></a></li>	

	<%
	}
	%>
	
	</ul>		

</div> <div class="clear"></div>

<% if (panel.getBottomSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getBottomSpace()%>">
	</TD></TR>
</TABLE>
<% } %>
