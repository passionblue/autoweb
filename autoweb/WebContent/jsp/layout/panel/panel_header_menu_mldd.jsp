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
	// Visiblity for printable
	if (WebUtil.isPritintable(request)){
		if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
			return;
	} else {
		if (panel.getShowOnlyPrint() == 1)
			return;
	}	

			
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
	String panelIdSuffix = PanelUtil.getPanelMenuStyleSuffix(panel.getId());
//	String panelNavStyleSiffix = PanelUtil.getPanelMenuNavStyleSuffix(panel.getId());
	String panelLinkStyleSuffix = PanelUtil.getPanelMenuLinkStyleSuffix(panel.getId());
	String smoothMenuConfigSuffix = PanelUtil.getPanelSmoothMenuLinkStyleSuffix(panel.getId());
	smoothMenuConfigSuffix = "s" + site.getId();
	
%>

<div id="ddlMenu">

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
%>

<div class="panelTitle" style="<%=panel.getTitleStyleString()%>">
<%= panel.getPanelTitle() %>
</div>
<% } %>

	<ul id="menuh<%=smoothMenuConfigSuffix %>" class="mlddm" params="1,1,500,none,0,h,1">
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
			String classString = "";                
			if ( p.getId() == pageId) {
				classString = "class=\"active\"";
			}                
			String pageIdDisplay = (isLogin?"(" + p.getId() + ")":"");              


			DynMenuManager menuMgr = DynMenuManager.getInstance();
			List subMenus = DynMenuManager.getInstance().getSubMenus(p.getId());

			if ( subMenus.size() >0) 
				classString = "class=\"hsub\"";                
	%>
	
		<li><a <%=classString %> href="/m_<%=p.getPageName()%>.html?name=<%= p.getPageMenuTitle().replace(" ", "_")%>" ><%= p.getPageMenuTitle() + pageIdDisplay%></a>
		

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
				String activeMenuClass2 = "";
				if ( menuMgr.isInPagePath(pageId, sp.getId())) {
					activeMenuClass2 = "class=\"active\"";
				}  
				if ( subMenus3.size() >0) 
					activeMenuClass2 = "class=\"hsub\"";

	%>
				<li><a  <%=activeMenuClass2 %>  href="/m_<%=sp.getPageName()%>.html"><%= sp.getPageMenuTitle() + pageIdDisplay%></a>
	<%
				// Sub menus third level
				if ( subMenus3.size() >0) {
	%>
					<ul>
	<%
					for(Iterator iter3 = subMenus3.iterator();iter3.hasNext();) {
					    Page sp3 = (Page) iter3.next();
						pageIdDisplay = (isLogin?"(" + sp3.getId() + ")":"");
						
						String activeMenuClass3 = "";
						if ( menuMgr.isInPagePath(pageId, sp3.getId())) {
							activeMenuClass3 = "class=\"active\"";
						}  
						
	%>
						<li ><a <%=activeMenuClass3 %> href="/m_<%=sp3.getPageName()%>.html"><%= sp3.getPageMenuTitle() + pageIdDisplay%></a></li>
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

</div><div class="clear"></div>

<% if (panel.getBottomSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getBottomSpace()%>">
	</TD></TR>
</TABLE>
<% } %>

